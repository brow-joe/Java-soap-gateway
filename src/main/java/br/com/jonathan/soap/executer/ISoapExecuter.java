package br.com.jonathan.soap.executer;

import br.com.jonathan.dto.EntryPointDTO;

@FunctionalInterface
public interface ISoapExecuter {

	public String execute(EntryPointDTO request) throws SoapExecuterException;

}