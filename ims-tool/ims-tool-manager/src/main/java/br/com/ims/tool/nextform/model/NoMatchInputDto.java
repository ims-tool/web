package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class NoMatchInputDto implements Serializable {
	private static final long serialVersionUID = -6024241026072510182L;
	private long id;
	private String type;
	private long threshold;
	private long prompt;
	private long nextForm;
	private PromptDto promptDto;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getThreshold() {
		return threshold;
	}

	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}

	public long getPrompt() {
		return prompt;
	}

	public void setPrompt(long prompt) {
		this.prompt = prompt;
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

	@Override
	public String toString() {
		return "NoMatchInputDto [id=" + id + ", type=" + type
				+ ", threshold=" + threshold + ", prompt=" + prompt
				+ ", nextForm=" + nextForm + ", promptDto="
				+ promptDto + "]";
	}
}
