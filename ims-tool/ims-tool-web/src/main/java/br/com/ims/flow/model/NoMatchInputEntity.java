package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class NoMatchInputEntity extends AbstractEntity{
	
	
	private String type;		
	private Integer threshold;
	private PromptEntity prompt;
	private FormEntity nextForm;
	private TagEntity tag;
	private VersionEntity versionId;
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public PromptEntity getPrompt() {
		return prompt;
	}
	public void setPrompt(PromptEntity prompt) {
		this.prompt = prompt;
	}
	public FormEntity getNextForm() {
		return nextForm;
	}
	public void setNextForm(FormEntity nextForm) {
		this.nextForm = nextForm;
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
