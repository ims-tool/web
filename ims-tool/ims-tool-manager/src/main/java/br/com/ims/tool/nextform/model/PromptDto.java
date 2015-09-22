package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Collection;

public class PromptDto implements Serializable {
	private static final long serialVersionUID = 8266189818241066002L;
	private long id;
	private String name;
	private Collection<PromptAudioDto> listaPromptAudio;

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

	public Collection<PromptAudioDto> getListaPromptAudio() {
		return this.listaPromptAudio;
	}

	public void setListaPromptAudio(Collection<PromptAudioDto> listaPromptAudio) {
		this.listaPromptAudio = listaPromptAudio;
	}

	public String toString() {
		return "PromptDto [id=" + this.id + ", name=" + this.name
				+ ", listaPromptAudio=" + this.listaPromptAudio + "]";
	}
}
