package br.com.jonathan.dto;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;

import com.google.gson.Gson;

import br.com.jonathan.domain.EntryPointDomain;

public class SoapRequestDTO {

	private String wsdl;
	private String url;
	private String request;
	private String response;
	private String action;
	private List<String> parameters;

	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public boolean isValid() {
		return StringUtils.isNotEmpty(wsdl) && StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(action);
	}

	@Transient
	public EntryPointDomain getCoveredEntry() {
		EntryPointDomain entry = new EntryPointDomain();
		entry.setResponse(response);
		entry.setRequest(request);
		entry.setMethod(action);
		entry.setParametersArrayJson(new Gson().toJson(parameters));
		return entry;
	}

}