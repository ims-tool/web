package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class PromptCollectDto implements Serializable {
	private static final long serialVersionUID = -674056101895277323L;
	private long id;
	private String name;
	private String description;
	private long grammar;
	private boolean flushPrompt;
	private long prompt;
	private long noInput;
	private long noMatch;
	private long fetchTimeout;
	private long interdigitTimeout;
	private long terminatingTimeout;
	private String terminatingCharacter;
	private long nextForm;
	private PromptDto promptDto;
	private GrammarDto grammarDto;
	private NoMatchInputDto noMatchDto;
	private NoMatchInputDto noInputDto;
	
	private long tag;

	public long getTag() {
		return tag;
	}

	public void setTag(long tag) {
		this.tag = tag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getGrammar() {
		return grammar;
	}

	public void setGrammar(long grammar) {
		this.grammar = grammar;
	}

	public boolean isFlushPrompt() {
		return flushPrompt;
	}

	public void setFlushPrompt(boolean flushPrompt) {
		this.flushPrompt = flushPrompt;
	}

	public long getPrompt() {
		return prompt;
	}

	public void setPrompt(long prompt) {
		this.prompt = prompt;
	}

	public long getNoInput() {
		return noInput;
	}

	public void setNoInput(long noInput) {
		this.noInput = noInput;
	}

	public long getNoMatch() {
		return noMatch;
	}

	public void setNoMatch(long noMatch) {
		this.noMatch = noMatch;
	}

	public long getFetchTimeout() {
		return fetchTimeout;
	}

	public void setFetchTimeout(long fetchTimeout) {
		this.fetchTimeout = fetchTimeout;
	}

	public long getInterdigitTimeout() {
		return interdigitTimeout;
	}

	public void setInterdigitTimeout(long interdigitTimeout) {
		this.interdigitTimeout = interdigitTimeout;
	}

	public long getTerminatingTimeout() {
		return terminatingTimeout;
	}

	public void setTerminatingTimeout(long terminatingTimeout) {
		this.terminatingTimeout = terminatingTimeout;
	}

	public String getTerminatingCharacter() {
		return terminatingCharacter;
	}

	public void setTerminatingCharacter(String terminatingCharacter) {
		this.terminatingCharacter = terminatingCharacter;
	}

	public long getNextForm() {
		return nextForm;
	}

	public void setNextForm(long nextForm) {
		this.nextForm = nextForm;
	}

	public PromptDto getPromptDto() {
		return promptDto;
	}

	public void setPromptDto(PromptDto promptDto) {
		this.promptDto = promptDto;
	}

	public GrammarDto getGrammarDto() {
		return grammarDto;
	}

	public void setGrammarDto(GrammarDto grammarDto) {
		this.grammarDto = grammarDto;
	}

	public NoMatchInputDto getNoMatchDto() {
		return noMatchDto;
	}

	public void setNoMatchDto(NoMatchInputDto noMatchDto) {
		this.noMatchDto = noMatchDto;
	}

	public NoMatchInputDto getNoInputDto() {
		return noInputDto;
	}

	public void setNoInputDto(NoMatchInputDto noInputDto) {
		this.noInputDto = noInputDto;
	}

	@Override
	public String toString() {
		return "PromptCollectDto [id=" + id + ", name=" + name
				+ ", description=" + description + ", grammar="
				+ grammar + ", flushPrompt=" + flushPrompt
				+ ", prompt=" + prompt + ", noInput=" + noInput
				+ ", noMatch=" + noMatch + ", fetchTimeout="
				+ fetchTimeout + ", interdigitTimeout="
				+ interdigitTimeout + ", terminatingTimeout="
				+ terminatingTimeout + ", terminatingCharacter="
				+ terminatingCharacter + ", nextForm=" + nextForm
				+ ", promptDto=" + promptDto + ", grammarDto="
				+ grammarDto + ", noMatchDto=" + noMatchDto
				+ ", noInputDto=" + noInputDto + "]";
	}
}
