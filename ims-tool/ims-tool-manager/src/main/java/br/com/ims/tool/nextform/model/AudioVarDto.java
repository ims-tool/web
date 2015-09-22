package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class AudioVarDto implements Serializable {
	
	private static final long serialVersionUID = 5533435981244745082L;
	
	private long id;
	private long audioId;
	private String paramName;
	private String paramValue;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAudioId() {
		return audioId;
	}
	public void setAudioId(long audioId) {
		this.audioId = audioId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	@Override
	public String toString() {
		return "AudioVarDto [id=" + id + ", audioId=" + audioId
				+ ", paramName=" + paramName + ", paramValue=" + paramValue
				+ "]";
	}

}
