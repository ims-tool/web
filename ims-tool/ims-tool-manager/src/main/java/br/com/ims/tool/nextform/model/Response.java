package br.com.ims.tool.nextform.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {

	private String context;
	private NextFormDto form;
	
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public NextFormDto getNext() {
		return form;
	}
	public void setNext(NextFormDto next) {
		this.form = next;
	}
	
}
