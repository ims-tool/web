package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class MenuEntity extends AbstractFormEntity{
	
	
	private PromptEntity prompt;
	private NoMatchInputEntity noInput;
	private NoMatchInputEntity noMatch;
	private Integer fetchTimeOut;
	private Integer terminatingTimeOut;
	private String terminatingCharacter;	
	private VersionEntity versionId;
	
	
	
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
	public Integer getFetchTimeOut() {
		return fetchTimeOut;
	}
	public void setFetchTimeOut(Integer fetchTimeOut) {
		this.fetchTimeOut = fetchTimeOut;
	}
	public Integer getTerminatingTimeOut() {
		return terminatingTimeOut;
	}
	public void setTerminatingTimeOut(Integer terminatingTimeOut) {
		this.terminatingTimeOut = terminatingTimeOut;
	}
	public String getTerminatingCharacter() {
		return terminatingCharacter;
	}
	public void setTerminatingCharacter(String terminatingCharacter) {
		this.terminatingCharacter = terminatingCharacter;
	}
	public VersionEntity getVersionId() {
		return versionId;
	}
	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}
	
	
			
}
