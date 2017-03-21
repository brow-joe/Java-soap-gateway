package br.com.jonathan.soap.executer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;

import br.com.jonathan.dto.EntryPointDTO;
import br.com.jonathan.soap.ConnectionAvailable;
import br.com.jonathan.soap.ConnectionAvailableException;

public class SoapExecuter implements ISoapExecuter {
	private final Logger logger = LogManager.getLogger(SoapExecuter.class);

	@Override
	public String execute(EntryPointDTO request) throws SoapExecuterException, ConnectionAvailableException {
		ConnectionAvailable.available(request.getUrl());
		SOAPConnection connection = null;
		try {
			SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
			connection = factory.createConnection();
			return execute(connection, request);
		} catch (Exception e) {
			logger.error(e);
			throw new SoapExecuterException(e);
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

	@Async
	private String execute(SOAPConnection connection, EntryPointDTO entryPoint) throws Exception {
		InputStream is = new ByteArrayInputStream(entryPoint.getRequest().getBytes());
		SOAPMessage requestMessage = MessageFactory.newInstance().createMessage(null, is);

		MimeHeaders headers = requestMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", entryPoint.getMethod());

		requestMessage.saveChanges();

		SOAPMessage soapResponse = connection.call(requestMessage, entryPoint.getUrl());
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		soapResponse.writeTo(writer);
		return new String(writer.toByteArray());
	}

}