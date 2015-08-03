package br.com.ims.flow.model;

import java.io.Serializable;

import br.com.ims.flow.common.Util;

@SuppressWarnings("serial")
public abstract class AbstractEntity implements Serializable{
	
	protected String id;
	
	public AbstractEntity() {
		this.id = Util.getUID();
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
}
