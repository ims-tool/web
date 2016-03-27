package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class NoMatchInputEntity extends AbstractFormEntity{
	
	private String type;		
	private Integer threshold;
	private PromptEntity prompt;
	
	
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
	
	

		
}
