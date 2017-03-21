package br.com.jonathan.components;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.jonathan.adapter.IAdapter;
import br.com.jonathan.domain.DealDomain;
import br.com.jonathan.domain.WSDLDomain;
import br.com.jonathan.service.ServiceException;
import br.com.jonathan.service.WSDLService;
import br.com.jonathan.soap.ConnectionAvailableException;
import br.com.jonathan.soap.parse.ISoapParser;
import br.com.jonathan.soap.parse.ParserException;

@Component
public class WsdlDealComponent {
	private final Logger logger = LogManager.getLogger(WsdlDealComponent.class);

	@Autowired
	private WSDLService wsdlService;
	@Autowired
	private ISoapParser parser;
	@Autowired
	private IAdapter adapter;

	@Async
	public DealDomain findDealByWsdl(WSDLDomain wsdl) throws ComponentException, ConnectionAvailableException {
		try {
			if (wsdl.notExistsDealEntry() || Objects.isNull(wsdl.getId())) {
				return searchDealByWsdl(wsdl);
			} else {
				WSDLDomain domain = wsdlService.findOne(Example.of(wsdl));
				return domain.getDeal();
			}
		} catch (ServiceException e) {
			logger.error(e);
			throw new ComponentException(e);
		} catch (ConnectionAvailableException e) {
			logger.error(e);
			throw new ConnectionAvailableException(e);
		}
	}

	@Async
	public WSDLDomain saveWsdl(WSDLDomain wsdl) throws ComponentException, ConnectionAvailableException {
		try {
			if (wsdl.notExistsDealEntry()) {
				DealDomain deal = searchDealByWsdl(wsdl);
				wsdl.setDeal(deal);
			}
			return wsdlService.save(wsdl);
		} catch (ServiceException e) {
			logger.error(e);
			throw new ComponentException(e);
		} catch (ConnectionAvailableException e) {
			logger.error(e);
			throw new ConnectionAvailableException(e);
		}
	}

	@Async
	public DealDomain searchDealByWsdl(WSDLDomain wsdl) throws ComponentException, ConnectionAvailableException {
		DealDomain deal = null;
		try {
			if (!StringUtils.endsWithIgnoreCase(wsdl.getUrl(), "?wsdl")) {
				wsdl.setUrl(wsdl.getUrl() + "?wsdl");
			}
			deal = adapter.dealAdapter(parser.parse(wsdl.getUrl()));
		} catch (ParserException e) {
			logger.error(e);
			throw new ComponentException(e);
		}
		return deal;
	}

}