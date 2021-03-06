package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class PromptAudioEntity extends AbstractEntity {
	
	
	private String promptId;
	private AudioEntity audio;
	private Integer orderNum;
	private ConditionEntity condition;	
	
	
	
	public String getPromptId() {
		return promptId;
	}
	public void setPromptId(String promptId) {
		this.promptId = promptId;
	}
	public AudioEntity getAudio() {
		return audio;
	}
	public void setAudio(AudioEntity audio) {
		this.audio = audio;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public ConditionEntity getCondition() {
		return condition;
	}
	public void setCondition(ConditionEntity condition) {
		this.condition = condition;
	}
	
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	
	
}
