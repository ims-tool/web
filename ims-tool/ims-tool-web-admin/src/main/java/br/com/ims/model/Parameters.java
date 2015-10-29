package br.com.ims.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Parameters implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2274733515360250231L;
	
	private Long id;
	private String name;
	private String description;
	private String type;
	private String value;
	private String loginid;
	private Date startdate;
	private String owner;
	/*private String validation;*/
	private long versionid;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/*public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}*/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public long getVersionid() {
		return versionid;
	}
	public void setVersionid(long versionid) {
		this.versionid = versionid;
	}
	
	@Override
	public String toString() {
		return "ParametersDto [id=" + id + ", name=" + name + "]";
	}
}
