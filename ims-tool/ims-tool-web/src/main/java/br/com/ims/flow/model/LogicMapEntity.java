package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class LogicMapEntity extends AbstractEntity {
	
	private String name;
	private String description;
	private MapTypeEntity mapType;
	private String methodReference;
	private String returnType;
	private Integer logActive;
		
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
	
	public String getMethodReference() {
		return methodReference;
	}
	public void setMethodReference(String methodReference) {
		this.methodReference = methodReference;
	}
	public Integer getLogActive() {
		return logActive;
	}
	public void setLogActive(Integer logActive) {
		this.logActive = logActive;
	}
	
	public MapTypeEntity getMapType() {
		return mapType;
	}
	public void setMapType(MapTypeEntity mapType) {
		this.mapType = mapType;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	
		
}
