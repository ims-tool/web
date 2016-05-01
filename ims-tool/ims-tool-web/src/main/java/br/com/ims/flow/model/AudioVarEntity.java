package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class AudioVarEntity extends AbstractEntity{
	

	private String audioId;
	private String formatName;
	private String formatParameter;
	private String formatValue;
	public String getFormatName() {
		return formatName;
	}
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	public String getFormatParameter() {
		return formatParameter;
	}
	public void setFormatParameter(String formatParameter) {
		this.formatParameter = formatParameter;
	}
	public String getFormatValue() {
		return formatValue;
	}
	public void setFormatValue(String formatValue) {
		this.formatValue = formatValue;
	}
	
	public String getAudioId() {
		return audioId;
	}
	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}
		
}
