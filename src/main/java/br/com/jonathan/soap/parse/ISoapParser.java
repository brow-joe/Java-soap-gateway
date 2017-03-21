package br.com.jonathan.soap.parse;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import br.com.jonathan.dto.SoapRequestDTO;
import br.com.jonathan.soap.ConnectionAvailableException;

@FunctionalInterface
public interface ISoapParser {

	@Async
	public List<SoapRequestDTO> parse(String url) throws ParserException, ConnectionAvailableException;

}