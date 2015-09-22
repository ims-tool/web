package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class AnnounceDto implements Serializable {
	private static final long serialVersionUID = -7322747825405792464L;
	private long id;
	private String name;
	private String description;
	private long flushprompt;
	private long prompt;
	private long nextForm;
	private long tag;
	private PromptDto promptDto;
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
	public long getFlushprompt() {
		return flushprompt;
	}
	public void setFlushprompt(long flushprompt) {
		this.flushprompt = flushprompt;
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
	public long getTag() {
		return tag;
	}
	public void setTag(long tag) {
		this.tag = tag;
	}
	public PromptDto getPromptDto() {
		return promptDto;
	}
	public void setPromptDto(PromptDto promptDto) {
		this.promptDto = promptDto;
	}
	@Override
	public String toString() {
		return "AnnounceDto [id=" + id + ", name=" + name + ", description="
				+ description + ", flushprompt=" + flushprompt + ", prompt="
				+ prompt + ", nextForm=" + nextForm + ", tag=" + tag
				+ ", promptDto=" + promptDto + "]";
	}

}
