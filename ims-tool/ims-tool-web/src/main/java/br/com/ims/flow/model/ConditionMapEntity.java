package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class ConditionMapEntity extends AbstractEntity {
	
	private String name;
	private String description;
	private String type;
	private String methodReference;
	private Integer logActive;
	private VersionEntity versionId;
	
	
	
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
	public Integer getLogActive() {
		return logActive;
	}
	public void setLogActive(Integer logActive) {
		this.logActive = logActive;
	}
	public VersionEntity getVersionId() {
		return versionId;
	}
	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}
	
		
}
