package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class DisconnectEntity extends AbstractFormEntity{
	
	
	private FormEntity nextform;
	private TagEntity tag;
	private VersionEntity versionId;
	
	public FormEntity getNextform() {
		return nextform;
	}
	public void setNextform(FormEntity nextform) {
		this.nextform = nextform;
	}
	public TagEntity getTag() {
		return tag;
	}
	public void setTag(TagEntity tag) {
		this.tag = tag;
	}
	public VersionEntity getVersionId() {
		return versionId;
	}
	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}
	
	
	
		
}
