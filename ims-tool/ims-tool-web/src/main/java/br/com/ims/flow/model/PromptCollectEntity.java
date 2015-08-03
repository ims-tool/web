package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class PromptCollectEntity extends AbstractFormEntity {
	
	private GrammarEntity grammar;
	private Integer flushprompt;
	private PromptEntity prompt;
	private NoMatchInputEntity noInput;
	private NoMatchInputEntity noMatch;
	private Integer fetchTimeout;
	private Integer interDigitTimeout;
	private Integer terminatingTimeout;
	private String terminatingCharacter;
	private FormEntity nextform;
	private TagEntity tag;
	private VersionEntity versionId;
	
	
	public GrammarEntity getGrammar() {
		return grammar;
	}
	public void setGrammar(GrammarEntity grammar) {
		this.grammar = grammar;
	}
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
	public Integer getFetchTimeout() {
		return fetchTimeout;
	}
	public void setFetchTimeout(Integer fetchTimeout) {
		this.fetchTimeout = fetchTimeout;
	}
	public Integer getInterDigitTimeout() {
		return interDigitTimeout;
	}
	public void setInterDigitTimeout(Integer interDigitTimeout) {
		this.interDigitTimeout = interDigitTimeout;
	}
	public Integer getTerminatingTimeout() {
		return terminatingTimeout;
	}
	public void setTerminatingTimeout(Integer terminatingTimeout) {
		this.terminatingTimeout = terminatingTimeout;
	}
	public String getTerminatingCharacter() {
		return terminatingCharacter;
	}
	public void setTerminatingCharacter(String terminatingCharacter) {
		this.terminatingCharacter = terminatingCharacter;
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
