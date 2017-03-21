package br.com.jonathan.soap.parse;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;

import com.google.gson.Gson;
import com.predic8.schema.TypeDefinition;
import com.predic8.wsdl.Binding;
import com.predic8.wsdl.BindingOperation;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Message;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Part;
import com.predic8.wsdl.Port;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.Service;
import com.predic8.wsdl.WSDLParser;
import com.predic8.wstool.creator.RequestTemplateCreator;
import com.predic8.wstool.creator.SOARequestCreator;

import br.com.jonathan.dto.SoapRequestDTO;
import br.com.jonathan.soap.ConnectionAvailable;
import br.com.jonathan.soap.ConnectionAvailableException;
import groovy.xml.MarkupBuilder;

public class SoapParser implements ISoapParser {
	private final Logger logger = LogManager.getLogger(SoapParser.class);
	private final Pattern PATTERN_PARAMETERS = Pattern.compile("(<..*>)(\\?XXX\\?)(<.*>)");
	private final List<String> SUPPORTS = Arrays.asList("SOAP11", "SOAP12");

	@Override
	public List<SoapRequestDTO> parse(String wsdl) throws ParserException, ConnectionAvailableException {
		ConnectionAvailable.available(wsdl);
		try {
			WSDLParser parser = new WSDLParser();
			Definitions definitions = parser.parse(wsdl);

			Function<Service, List<SoapRequestDTO>> serviceFunction = service -> getRequestByService(service,
					definitions, wsdl);
			
			return definitions.getServices().stream()
					.map(serviceFunction)
					.flatMap(List::stream)
					.filter(SoapRequestDTO::isValid)
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error(e);
			throw new ParserException(e);
		}
	}

	@Async
	private List<SoapRequestDTO> getRequestByService(Service service, Definitions definitions, String wsdl) {
		
		Predicate<Binding> bindingPredicate = binding -> SUPPORTS.contains(binding.getProtocol());
		Function<Port, Binding> portFunction = port -> definitions.getBinding(port.getBinding().getName());
		Function<Binding, List<SoapRequestDTO>> bindingFunction = binding -> getRequestByBinding(binding, definitions,
				wsdl); 
		
		return service.getPorts().stream()
				.map(portFunction)
				.filter(bindingPredicate)
				.map(bindingFunction)
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}

	@Async
	private List<SoapRequestDTO> getRequestByBinding(Binding binding, Definitions definitions, String wsdl) {
		
		Function<BindingOperation, SoapRequestDTO> operationFunction = operation -> getRequestDTO(definitions, wsdl,
				operation, definitions.getPortType(binding.getType()), binding);
		
		return binding.getOperations().stream()
				.map(operationFunction)
				.collect(Collectors.toList());
	}

	@Async
	private SoapRequestDTO getRequestDTO(Definitions definitions, String wsdl, BindingOperation bindingOperation,
			PortType type, Binding binding) throws ParserRuntimeException {
		
		String targetNamespace = definitions.getTargetNamespace();
		final String url = StringUtils.removeIgnoreCase(wsdl, "?wsdl");
		String operationName = bindingOperation.getQName().getLocalPart();
		Operation operation = type.getOperation(operationName);
		SoapRequestDTO dto = new SoapRequestDTO();
		
		dto.setWsdl(wsdl);
		dto.setUrl(url);
		
		String request = getRequestMessage(definitions, type.getName(), operationName, binding.getName());
		dto.setRequest(request);
		String response = getResponse(operation.getOutput().getMessage());
		dto.setResponse(response);
		String action = getAction(targetNamespace, operationName);
		dto.setAction(action);
		
		List<String> parameters = getParameters(request);
		dto.setParameters(parameters);
		return dto;
	}

	@Async
	private List<String> getParameters(String request) {
		List<String> parameters = new ArrayList<>();
		if (StringUtils.isNotEmpty(request)) {
			Matcher matcher = PATTERN_PARAMETERS.matcher(request);
			while (matcher.find()) {
				String group = matcher.group(1);
				if (StringUtils.isNotEmpty(group)) {
					String id = getParameterId(group);
					parameters.add(id);
				}
			}
		}
		return parameters;
	}
	
	@Async
	private String getParameterId(String group) {
		String id = StringUtils.remove(group, "<");
		return StringUtils.remove(id, ">").trim();
	}

	@Async
	private String getAction(String target, String operation) {
		return target + operation;
	}

	@Async
	private String getResponse(Message output) throws ParserRuntimeException {
		try {
			List<String> responses = output.getParts().stream()
					.map(this::getResponse)
					.collect(Collectors.toList());

			if (CollectionUtils.isNotEmpty(responses) && responses.size() == 1) {
				return responses.get(0);
			} else {
				String response = new Gson().toJson(responses);
				return response;
			}
		} catch (Exception e) {
			logger.error(e);
			throw new ParserRuntimeException(e);
		}
	}

	@Async
	private String getResponse(Part part) {
		TypeDefinition definition = part.getType();
		if (Objects.isNull(definition)) {
			try {
				return part.getElement().getAsJson();
			} catch (Exception e) {
				return part.getElement().getAsString();
			}
		} else {
			return definition.getQname().getLocalPart();
		}
	}

	@Async
	private String getRequestMessage(Definitions definitions, String port, String operation, String binding)
			throws ParserRuntimeException {
		
		SOAPConnection connection = null;
		try {
			StringWriter writer = new StringWriter();
			RequestTemplateCreator template = new RequestTemplateCreator();
			MarkupBuilder builder = new MarkupBuilder(writer);
			SOARequestCreator creator = new SOARequestCreator(definitions, template, builder);
			SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();

			connection = factory.createConnection();
			creator.createRequest(port, operation, binding);
			return writer.toString();
		} catch (Exception e) {
			logger.error(e);
			throw new ParserRuntimeException(e);
		} finally {
			if (Objects.nonNull(connection)) {
				try {
					connection.close();
				} catch (SOAPException e) {
					logger.error(e);
				}
			}
		}
	}

}