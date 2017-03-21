package br.com.jonathan.soap.parse;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import br.com.jonathan.dto.SoapRequestDTO;

@FunctionalInterface
public interface ISoapParser {

	@Async
	public List<SoapRequestDTO> parse(String url) throws ParserException;

}