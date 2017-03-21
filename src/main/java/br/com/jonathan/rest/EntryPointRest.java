package br.com.jonathan.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonathan.domain.EntryMessegeRequestDomain;
import br.com.jonathan.dto.EntryPointDTO;
import br.com.jonathan.service.EntryPointService;
import br.com.jonathan.service.ServiceException;
import br.com.jonathan.soap.connection.ConnectionAvailableException;

@RestController
@RequestMapping("/api/entry")
public class EntryPointRest {
	private final Logger logger = LogManager.getLogger(EntryPointRest.class);

	@Autowired
	private EntryPointService service;

	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	public ResponseEntity<EntryPointDTO> execute(HttpServletRequest request,
			@RequestParam(name = "synchronous", defaultValue = "true") Boolean synchronous,
			@RequestBody EntryPointDTO entry) {
		try {
			if (entry.isValid()) {
				EntryPointDTO dto;
				if (synchronous) {
					dto = service.executeSync(entry);
				} else {
					dto = service.executeAsync(entry);
				}
				return ResponseEntity.ok(dto);
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
			}
		} catch (ServiceException e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (ConnectionAvailableException e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}

	@RequestMapping(value = "/messages/", method = RequestMethod.GET)
	public ResponseEntity<List<EntryMessegeRequestDomain>> findAllMessages(HttpServletRequest request) {
		try {
			List<EntryMessegeRequestDomain> response = service.findAllEntryMessages();
			return ResponseEntity.ok(response);
		} catch (ServiceException e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(value = "/messages/", method = RequestMethod.DELETE)
	public ResponseEntity<Long> delete(HttpServletRequest request, @RequestBody EntryMessegeRequestDomain message) {
		try {
			if (message.isValid()) {
				Long id = service.deleteMessage(message);
				return ResponseEntity.ok(id);
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
			}
		} catch (ServiceException e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}