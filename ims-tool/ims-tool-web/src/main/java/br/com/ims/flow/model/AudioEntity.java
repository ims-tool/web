package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class AudioEntity extends AbstractEntity{
	

	private String type;
	private String name;
	private String description;
	private String path;
	private String property;
	private String context;
	private List<AudioVarEntity> audioVar;
		
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
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public List<AudioVarEntity> getAudioVar() {
		return audioVar;
	}
	public void setAudioVar(List<AudioVarEntity> audioVar) {
		this.audioVar = audioVar;
	}
		
}
