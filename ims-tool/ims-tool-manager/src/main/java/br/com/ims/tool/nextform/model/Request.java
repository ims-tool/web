package br.com.ims.tool.nextform.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Request {

	private String context;
	private Long  nextId;
	
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Long getNextId() {
		return nextId;
	}
	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}
}
