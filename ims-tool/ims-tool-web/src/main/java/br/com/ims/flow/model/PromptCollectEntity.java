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
	private String noInput_NextForm;
	private String noMatch_NextForm;
	private TagEntity noInput_Tag;
	private TagEntity noMatch_Tag;
	
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
