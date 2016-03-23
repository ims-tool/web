package br.com.ims.flow.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractEntity implements Serializable{
	
	protected String id;
	private VersionEntity versionId;
	
	
	public AbstractEntity() {
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public VersionEntity getVersionId() {
		return versionId;
	}


	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}
	
	
	
}
