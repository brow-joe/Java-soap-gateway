package br.com.jonathan.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "wsdl")
@XmlRootElement
public class WSDLDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "url")
	@NotBlank(message = "Url é uma informação obrigatória.")
	private String url;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "wsdl_deal", joinColumns = {
			@JoinColumn(name = "wsdl_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "deal_id", referencedColumnName = "id") })
	private DealDomain deal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DealDomain getDeal() {
		return deal;
	}

	public void setDeal(DealDomain deal) {
		this.deal = deal;
	}

	public boolean isValid() {
		boolean dealValid = true;
		if (Objects.nonNull(deal)) {
			dealValid = deal.isValid();
		}
		return StringUtils.isNotEmpty(url) && dealValid;
	}

	public boolean notExistsDealEntry() {
		return Objects.isNull(deal) || CollectionUtils.isEmpty(deal.getEntryPoints());
	}

}