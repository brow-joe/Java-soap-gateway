package br.com.jonathan.rest;

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
import br.com.jonathan.domain.DealDomain;
import br.com.jonathan.domain.WSDLDomain;

@RestController
@RequestMapping("/api/deal")
public class DealRest {
	private final Logger logger = LogManager.getLogger(DealRest.class);

	@Autowired
	private WsdlDealComponent component;

	@RequestMapping(value = "/findByWsdl", method = RequestMethod.POST)
	public ResponseEntity<DealDomain> save(HttpServletRequest request, @RequestBody WSDLDomain wsdl) {
		try {
			if (wsdl.isValid()) {
				DealDomain deal = component.findDealByWsdl(wsdl);
				return ResponseEntity.ok(deal);
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
			}
		} catch (ComponentException e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}