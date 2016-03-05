package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class OperationMapEntity extends AbstractEntity {
	
	private String name;
	private String description;
	private String methodReference;
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
	
	
		
}
