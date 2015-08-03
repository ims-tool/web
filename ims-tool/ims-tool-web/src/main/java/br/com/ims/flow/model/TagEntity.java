package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class TagEntity extends AbstractEntity{
	
		
	private String description;	
	private VersionEntity versionId;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public VersionEntity getVersionId() {
		return versionId;
	}
	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}
	
			
}
