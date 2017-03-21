package br.com.jonathan.adapter;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import br.com.jonathan.domain.DealDomain;
import br.com.jonathan.domain.EntryPointDomain;
import br.com.jonathan.dto.ParameterDTO;
import br.com.jonathan.dto.SoapRequestDTO;

public interface IAdapter {

	@Async
	public List<EntryPointDomain> entryAdapter(List<SoapRequestDTO> list);

	@Async
	public DealDomain dealAdapter(List<SoapRequestDTO> list);

	@Async
	public String requestAdapter(String request, List<ParameterDTO> parameters);

}