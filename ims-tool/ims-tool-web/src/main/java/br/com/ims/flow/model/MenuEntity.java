package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class MenuEntity extends AbstractFormEntity{
	
	
	private PromptEntity prompt;
	private NoMatchInputEntity noInput;
	private NoMatchInputEntity noMatch;
	private String fetchTimeOut;
	private String terminatingTimeOut;
	private String terminatingCharacter;	
	
	private List<ChoiceEntity> choices;
	
	
	public PromptEntity getPrompt() {
		return prompt;
	}
	public void setPrompt(PromptEntity prompt) {
		this.prompt = prompt;
	}
	public NoMatchInputEntity getNoInput() {
		return noInput;
	}
	public void setNoInput(NoMatchInputEntity noInput) {
		this.noInput = noInput;
	}
	public NoMatchInputEntity getNoMatch() {
		return noMatch;
	}
	public void setNoMatch(NoMatchInputEntity noMatch) {
		this.noMatch = noMatch;
	}
	public String getFetchTimeOut() {
		return fetchTimeOut;
	}
	public void setFetchTimeOut(String fetchTimeOut) {
		this.fetchTimeOut = fetchTimeOut;
	}
	public String getTerminatingTimeOut() {
		return terminatingTimeOut;
	}
	public void setTerminatingTimeOut(String terminatingTimeOut) {
		this.terminatingTimeOut = terminatingTimeOut;
	}
	public String getTerminatingCharacter() {
		return terminatingCharacter;
	}
	public void setTerminatingCharacter(String terminatingCharacter) {
		this.terminatingCharacter = terminatingCharacter;
	}
	public List<ChoiceEntity> getChoices() {
		return choices;
	}
	public void setChoices(List<ChoiceEntity> choices) {
		this.choices = choices;
	}
	
	

}
