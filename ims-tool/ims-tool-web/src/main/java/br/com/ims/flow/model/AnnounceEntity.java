package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class AnnounceEntity extends AbstractFormEntity{
	
	private Integer flushprompt;
	private PromptEntity prompt;
		
	
	public Integer getFlushprompt() {
		return flushprompt;
	}
	public void setFlushprompt(Integer flushprompt) {
		this.flushprompt = flushprompt;
	}
	public PromptEntity getPrompt() {
		return prompt;
	}
	public void setPrompt(PromptEntity prompt) {
		this.prompt = prompt;
	}
	
	
	
	
	
		
}
