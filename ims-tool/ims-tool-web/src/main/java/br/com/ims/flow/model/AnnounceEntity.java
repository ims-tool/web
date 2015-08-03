package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class AnnounceEntity extends AbstractFormEntity{
	
	private Integer flushpront;
	private PromptEntity prompt;
	private FormEntity nextform;
	private TagEntity tag;
	private VersionEntity versionId;
	
	
		
	public Integer getFlushpront() {
		return flushpront;
	}
	public void setFlushpront(Integer flushpront) {
		this.flushpront = flushpront;
	}
	
	public PromptEntity getPrompt() {
		return prompt;
	}
	public void setPrompt(PromptEntity prompt) {
		this.prompt = prompt;
	}
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
