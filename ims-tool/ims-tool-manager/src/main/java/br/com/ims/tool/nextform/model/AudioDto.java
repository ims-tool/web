package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Collection;

public class AudioDto implements Serializable {
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}


	private static final long serialVersionUID = -4323198614637990232L;
	
	private long id;
	private String type;
	private String name;
	private String description;
	private String path;
	private String context;
	
	private String value;
	
	private Collection<AudioVarDto> listAudioVar;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Collection<AudioVarDto> getListAudioVar() {
		return listAudioVar;
	}

	public void setListAudioVar(Collection<AudioVarDto> listAudioVar) {
		this.listAudioVar = listAudioVar;
	}
	
	
	@Override
	public String toString() {
		return "AudioDto [id=" + id + ", type=" + type + ", name=" + name
				+ ", description=" + description + ",context="+context +", path=" + path
				+ ", value=" + value + ", listAudioVar=" + listAudioVar + "]";
	}
	
}
