package br.com.jonathan.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "deal")
@XmlRootElement
public class DealDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "url")
	@NotBlank(message = "URL é uma informação obrigatória.")
	private String url;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "deal_entry", joinColumns = {
			@JoinColumn(name = "deal_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "point_id", referencedColumnName = "id") })
	private List<EntryPointDomain> entryPoints;

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

	public List<EntryPointDomain> getEntryPoints() {
		return entryPoints;
	}

	public void setEntryPoints(List<EntryPointDomain> entryPoints) {
		this.entryPoints = entryPoints;
	}

	public boolean isValid() {
		boolean entryPointsValid = true;
		if (CollectionUtils.isNotEmpty(entryPoints)) {
			for (EntryPointDomain point : entryPoints) {
				if (!point.isValid()) {
					entryPointsValid = false;
					break;
				}
			}
		}
		return StringUtils.isNotEmpty(url) && entryPointsValid;
	}

}