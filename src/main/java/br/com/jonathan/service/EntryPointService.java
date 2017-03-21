package br.com.jonathan.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.jonathan.adapter.IAdapter;
import br.com.jonathan.domain.EntryMessegeRequestDomain;
import br.com.jonathan.dto.EntryPointDTO;
import br.com.jonathan.repository.EntryMessegeRepository;
import br.com.jonathan.soap.ConnectionAvailableException;
import br.com.jonathan.soap.executer.ISoapExecuter;
import br.com.jonathan.soap.executer.SoapExecuterException;
import br.com.jonathan.util.JSONUtil;
import rx.Observable;
import rx.schedulers.Schedulers;

@Service
public class EntryPointService {
	private final Logger logger = LogManager.getLogger(EntryPointService.class);

	@Autowired
	private ISoapExecuter executer;
	@Autowired
	private EntryMessegeRepository repository;
	@Autowired
	private IAdapter adapter;

	@Async
	public EntryPointDTO executeSync(EntryPointDTO entry) throws ServiceException, ConnectionAvailableException {
		return execute(entry);
	}

	@Async
	public EntryPointDTO executeAsync(EntryPointDTO entry) throws ServiceException {
		Observable.from(Arrays.asList(entry)).subscribeOn(Schedulers.io()).subscribe(e -> {
			try {
				executeEntryToMessage(e);
			} catch (ConnectionAvailableException ex) {
				logger.error(ex);
				saveErroConnectionMessage(entry, ex);
			}
		});

		return entry;
	}

	@Async
	public EntryPointDTO execute(EntryPointDTO entry) throws ServiceException, ConnectionAvailableException {
		try {
			String request = updateRequest(entry);
			entry.setRequest(request);
			String xml = executer.execute(entry);
			String json = JSONUtil.xmlToJson(xml);
			entry.setResult(json);
			return entry;
		} catch (SoapExecuterException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (ConnectionAvailableException e) {
			logger.error(e);
			throw new ConnectionAvailableException(e);
		}
	}

	@Async
	private String updateRequest(EntryPointDTO entry) {
		String request = entry.getRequest();
		if (StringUtils.isNotEmpty(request) && CollectionUtils.isNotEmpty(entry.getParametersArrayJson())) {
			request = adapter.requestAdapter(request, entry.getParametersArrayJson());
		}
		return request;
	}

	@Async
	public List<EntryMessegeRequestDomain> findAllEntryMessages() throws ServiceException {
		try {
			return repository.findAll();
		} catch (Exception e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Async
	private void saveErroConnectionMessage(EntryPointDTO dto, ConnectionAvailableException e) {
		String json = new Gson().toJson("Connection: " + e.getMessage());
		dto.setResult(json);
		EntryMessegeRequestDomain message = dto.getCoveredEntry();
		repository.save(message);
	}

	@Async
	public Long deleteMessage(EntryMessegeRequestDomain message) throws ServiceException {
		try {
			Long id = message.getId();
			repository.delete(message);
			return id;
		} catch (Exception e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Async
	private void executeEntryToMessage(EntryPointDTO entryPoint) throws ConnectionAvailableException {
		try {
			EntryMessegeRequestDomain message = execute(entryPoint).getCoveredEntry();
			repository.save(message);
			logger.info("message post " + message.getId());
		} catch (ServiceException e) {
			logger.error(e);
		} catch (ConnectionAvailableException e) {
			logger.error(e);
			throw new ConnectionAvailableException(e);
		}
	}

}