package br.com.jonathan.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;

import com.google.gson.Gson;

import br.com.jonathan.domain.EntryMessegeRequestDomain;

@XmlRootElement
public class EntryPointDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String method;
	private String request;
	private String response;
	private String wsdl;
	private String url;
	private String result;
	private Long wsdlId;
	private List<ParameterDTO> parametersArrayJson;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getWsdlId() {
		return wsdlId;
	}

	public void setWsdlId(Long wsdlId) {
		this.wsdlId = wsdlId;
	}

	public List<ParameterDTO> getParametersArrayJson() {
		return parametersArrayJson;
	}

	public void setParametersArrayJson(List<ParameterDTO> parametersArrayJson) {
		this.parametersArrayJson = parametersArrayJson;
	}

	public boolean isValid() {
		return StringUtils.isNotEmpty(method) && StringUtils.isNotEmpty(request) && StringUtils.isNotEmpty(response)
				&& StringUtils.isNotEmpty(wsdl) && StringUtils.isNotEmpty(url);
	}

	@Transient
	public EntryMessegeRequestDomain getCoveredEntry() {
		EntryMessegeRequestDomain domain = new EntryMessegeRequestDomain();
		String json = new Gson().toJson(this);
		domain.setJson(json);
		return domain;
	}

}