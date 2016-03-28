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
	private String noInput_NextForm;
	private String noMatch_NextForm;
	
	private TagEntity noInput_Tag;
	private TagEntity noMatch_Tag;
	
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
	public String getNoInput_NextForm() {
		return noInput_NextForm;
	}
	public void setNoInput_NextForm(String noInput_NextForm) {
		this.noInput_NextForm = noInput_NextForm;
	}
	public String getNoMatch_NextForm() {
		return noMatch_NextForm;
	}
	public void setNoMatch_NextForm(String noMatch_NextForm) {
		this.noMatch_NextForm = noMatch_NextForm;
	}
	public TagEntity getNoInput_Tag() {
		return noInput_Tag;
	}
	public void setNoInput_Tag(TagEntity noInput_Tag) {
		this.noInput_Tag = noInput_Tag;
	}
	public TagEntity getNoMatch_Tag() {
		return noMatch_Tag;
	}
	public void setNoMatch_Tag(TagEntity noMatch_Tag) {
		this.noMatch_Tag = noMatch_Tag;
	}
	
	
	
	

}
