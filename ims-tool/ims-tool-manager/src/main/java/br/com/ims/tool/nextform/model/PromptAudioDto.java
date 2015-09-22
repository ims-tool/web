package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class PromptAudioDto implements Serializable {
	private static final long serialVersionUID = 1825454648396584039L;
	private long prompt;
	private long audio;
	private long promptOrder;
	private AudioDto audioDto;

	public long getPrompt() {
		return this.prompt;
	}

	public void setPrompt(long prompt) {
		this.prompt = prompt;
	}

	public long getAudio() {
		return this.audio;
	}

	public void setAudio(long audio) {
		this.audio = audio;
	}

	public long getPromptOrder() {
		return this.promptOrder;
	}

	public void setPromptOrder(long promptOrder) {
		this.promptOrder = promptOrder;
	}

	public AudioDto getAudioDto() {
		return this.audioDto;
	}

	public void setAudioDto(AudioDto audioDto) {
		this.audioDto = audioDto;
	}

	public String toString() {
		return "PromptAudioDto [prompt=" + this.prompt + ", audio="
				+ this.audio + ", promptOrder=" + this.promptOrder
				+ ", audioDto=" + this.audioDto + "]";
	}
}
