package br.com.ims.flow.model;

import java.util.Date;

public class VersionEntity {
	
	private Integer id;
	private String description;	
	private Date deployDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDeployDate() {
		return deployDate;
	}
	public void setDeployDate(Date deployDate) {
		this.deployDate = deployDate;
	}
	
	
			
}
