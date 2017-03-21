package br.com.jonathan.soap.executer;

import br.com.jonathan.dto.EntryPointDTO;
import br.com.jonathan.soap.connection.ConnectionAvailableException;

@FunctionalInterface
public interface ISoapExecuter {

	public String execute(EntryPointDTO request) throws SoapExecuterException, ConnectionAvailableException;

}