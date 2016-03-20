package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class PromptCollectEntity extends AbstractFormEntity {
	
	private GrammarEntity grammar;
	private Integer flushprompt;
	private PromptEntity prompt;
	private NoMatchInputEntity noInput;
	private NoMatchInputEntity noMatch;
	private String fetchTimeout;
	private String interDigitTimeout;
	private String terminatingTimeout;
	private String terminatingCharacter;
	private TagEntity tag;
	
	
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
	public String getFetchTimeout() {
		return fetchTimeout;
	}
	public void setFetchTimeout(String fetchTimeout) {
		this.fetchTimeout = fetchTimeout;
	}
	public String getInterDigitTimeout() {
		return interDigitTimeout;
	}
	public void setInterDigitTimeout(String interDigitTimeout) {
		this.interDigitTimeout = interDigitTimeout;
	}
	public String getTerminatingTimeout() {
		return terminatingTimeout;
	}
	public void setTerminatingTimeout(String terminatingTimeout) {
		this.terminatingTimeout = terminatingTimeout;
	}
	public String getTerminatingCharacter() {
		return terminatingCharacter;
	}
	public void setTerminatingCharacter(String terminatingCharacter) {
		this.terminatingCharacter = terminatingCharacter;
	}
	public TagEntity getTag() {
		return tag;
	}
	public void setTag(TagEntity tag) {
		this.tag = tag;
	}
	



		
}
