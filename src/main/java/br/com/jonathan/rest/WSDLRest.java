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
import org.springframework.web.bind.annotation.RestController;

import br.com.jonathan.components.ComponentException;
import br.com.jonathan.components.WsdlDealComponent;
import br.com.jonathan.domain.WSDLDomain;
import br.com.jonathan.service.ServiceException;
import br.com.jonathan.service.WSDLService;

@RestController
@RequestMapping("/api/wsdl")
public class WSDLRest {
	private final Logger logger = LogManager.getLogger(WSDLRest.class);

	@Autowired
	private WSDLService service;
	@Autowired
	private WsdlDealComponent component;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<WSDLDomain>> findAll(HttpServletRequest request) {
		try {
			List<WSDLDomain> response = service.findAll();
			return ResponseEntity.ok(response);
		} catch (ServiceException e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<Long> delete(HttpServletRequest request, @RequestBody WSDLDomain wsdl) {
		try {
			if (wsdl.isValid()) {
				Long id = service.delete(wsdl);
				return ResponseEntity.ok(id);
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
			}
		} catch (ServiceException e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<WSDLDomain> save(HttpServletRequest request, @RequestBody WSDLDomain wsdl) {
		try {
			if (wsdl.isValid()) {
				WSDLDomain domain = component.saveWsdl(wsdl);
				return ResponseEntity.ok(domain);
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
			}
		} catch (ComponentException e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<WSDLDomain> update(HttpServletRequest request, @RequestBody WSDLDomain wsdl) {
		try {
			if (wsdl.isValid()) {
				WSDLDomain domain = service.save(wsdl);
				return ResponseEntity.ok(domain);
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
			}
		} catch (ServiceException e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}