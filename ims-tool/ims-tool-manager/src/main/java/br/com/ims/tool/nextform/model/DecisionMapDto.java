package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class DecisionMapDto implements Serializable {

	private static final long serialVersionUID = 3955655108085498747L;
	
	private long id;
	private String name;
	private String description;
	private String type;
	private String methodReference;
	private int logActive;
	
	private int timeout;
	private boolean isActive;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public String getMethodReference() {
		return methodReference;
	}
	public void setMethodReference(String methodReference) {
		this.methodReference = methodReference;
	}
	public int getLogActive() {
		return logActive;
	}
	public void setLogActive(int logActive) {
		this.logActive = logActive;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "DecisionMapDto [id=" + id + ", name=" + name + ", description="
				+ description + ", type=" + type + ", methodReference="
				+ methodReference + ", logActive=" + logActive + ", timeout="
				+ timeout + ", isActive=" + isActive + "]";
	}

}
