package br.com.jonathan.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "point")
@XmlRootElement
public class EntryPointDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "method")
	@NotBlank(message = "Method é uma informação obrigatória.")
	private String method;

	@Lob
	@Column(columnDefinition = "clob")
	private String request;

	@Lob
	@Column(columnDefinition = "clob")
	private String response;

	@Lob
	@Column(columnDefinition = "clob")
	private String parametersArrayJson;

	private Boolean isSynchronous = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Boolean isSynchronous() {
		return isSynchronous;
	}

	public void setSynchronous(Boolean isSynchronous) {
		this.isSynchronous = isSynchronous;
	}

	public String getParametersArrayJson() {
		return parametersArrayJson;
	}

	public void setParametersArrayJson(String parametersArrayJson) {
		this.parametersArrayJson = parametersArrayJson;
	}

	public boolean isValid() {
		return StringUtils.isNotEmpty(method) && StringUtils.isNotEmpty(request) && StringUtils.isNotEmpty(response);
	}

}