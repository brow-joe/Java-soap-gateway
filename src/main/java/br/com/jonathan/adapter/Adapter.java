package br.com.jonathan.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;

import br.com.jonathan.domain.DealDomain;
import br.com.jonathan.domain.EntryPointDomain;
import br.com.jonathan.dto.ParameterDTO;
import br.com.jonathan.dto.SoapRequestDTO;

public class Adapter implements IAdapter {

	@Override
	public List<EntryPointDomain> entryAdapter(List<SoapRequestDTO> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream()
					.map(SoapRequestDTO::getCoveredEntry)
					.filter(EntryPointDomain::isValid)
					.collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public DealDomain dealAdapter(List<SoapRequestDTO> list) {
		DealDomain deal = null;
		if (CollectionUtils.isNotEmpty(list)) {
			deal = new DealDomain();
			String url = list.get(0).getUrl();

			deal.setUrl(url);
			deal.setEntryPoints(entryAdapter(list));
		}
		return deal;
	}

	@Override
	public String requestAdapter(String request, List<ParameterDTO> parameters) {
		for (ParameterDTO parameter : parameters) {
			String id = parameter.getId();
			String value = getParameterValue(parameter.getValue());
			if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(value)) {
				String lastValue = id + ">?XXX?";
				String newValue = id + ">" + value;
				request = StringUtils.replace(request, lastValue, newValue);
			}
		}
		return request;
	}

	@Async
	private String getParameterValue(String group) {
		String value = StringUtils.remove(group, "<");
		return StringUtils.remove(value, ">").trim();
	}

}