package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Collection;

public class MenuDto implements Serializable {
	private static final long serialVersionUID = -6853200937734032987L;
	private long id;
	private String name;
	private String description;
	private long prompt;
	private long noInput;
	private long noMatch;
	private long fetchTimeout;
	private long terminatingTimeout;
	private String terminatingCharacter;
	private PromptDto promptDto;
	private NoMatchInputDto noMatchDto;
	private NoMatchInputDto noInputDto;
	private Collection<ChoiceDto> listaChoiceDto;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPrompt() {
		return this.prompt;
	}

	public void setPrompt(long prompt) {
		this.prompt = prompt;
	}

	public long getNoInput() {
		return this.noInput;
	}

	public void setNoInput(long noInput) {
		this.noInput = noInput;
	}

	public long getNoMatch() {
		return this.noMatch;
	}

	public void setNoMatch(long noMatch) {
		this.noMatch = noMatch;
	}

	public long getFetchTimeout() {
		return this.fetchTimeout;
	}

	public void setFetchTimeout(long fetchTimeout) {
		this.fetchTimeout = fetchTimeout;
	}

	public long getTerminatingTimeout() {
		return this.terminatingTimeout;
	}

	public void setTerminatingTimeout(long terminatingTimeout) {
		this.terminatingTimeout = terminatingTimeout;
	}

	public String getTerminatingCharacter() {
		return this.terminatingCharacter;
	}

	public void setTerminatingCharacter(String terminatingCharacter) {
		this.terminatingCharacter = terminatingCharacter;
	}

	public PromptDto getPromptDto() {
		return this.promptDto;
	}

	public void setPromptDto(PromptDto promptDto) {
		this.promptDto = promptDto;
	}

	public NoMatchInputDto getNoMatchDto() {
		return this.noMatchDto;
	}

	public void setNoMatchDto(NoMatchInputDto noMatchDto) {
		this.noMatchDto = noMatchDto;
	}

	public NoMatchInputDto getNoInputDto() {
		return this.noInputDto;
	}

	public void setNoInputDto(NoMatchInputDto noInputDto) {
		this.noInputDto = noInputDto;
	}

	public Collection<ChoiceDto> getListaChoiceDto() {
		return this.listaChoiceDto;
	}

	public void setListaChoiceDto(Collection<ChoiceDto> listaChoiceDto) {
		this.listaChoiceDto = listaChoiceDto;
	}

	public String toString() {
		return "MenuDto [id=" + this.id + ", name=" + this.name
				+ ", description=" + this.description + ", prompt="
				+ this.prompt + ", noInput=" + this.noInput + ", noMatch="
				+ this.noMatch + ", fetchTimeout=" + this.fetchTimeout
				+ ", terminatingTimeout=" + this.terminatingTimeout
				+ ", terminatingCharacter=" + this.terminatingCharacter
				+ ", promptDto=" + this.promptDto + ", noMatchDto="
				+ this.noMatchDto + ", noInputDto=" + this.noInputDto
				+ ", listaChoiceDto=" + this.listaChoiceDto + "]";
	}
}
