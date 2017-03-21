package br.com.jonathan.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.jonathan.domain.WSDLDomain;
import br.com.jonathan.repository.WSDLRepository;

@Service
public class WSDLService {
	private final Logger logger = LogManager.getLogger(WSDLService.class);

	@Autowired
	private WSDLRepository repository;

	@Async
	public List<WSDLDomain> findAll() throws ServiceException {
		try {
			return repository.findAll();
		} catch (Exception e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Async
	public Long delete(WSDLDomain domain) throws ServiceException {
		try {
			Long id = domain.getId();
			repository.delete(domain);
			return id;
		} catch (Exception e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Async
	public WSDLDomain save(WSDLDomain domain) throws ServiceException {
		try {
			if (!StringUtils.endsWithIgnoreCase(domain.getUrl(), "?wsdl")) {
				domain.setUrl(domain.getUrl() + "?wsdl");
			}
			return repository.save(domain);
		} catch (Exception e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Async
	public WSDLDomain findOne(Example<WSDLDomain> example) throws ServiceException {
		try {
			return repository.findOne(example);
		} catch (Exception e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

}