package br.com.gvt.telefonia.ura.diagram.model;

public class PromptAudio extends Entity<PromptAudio> {
	
	private long prompt;
	private long audio;
	private long ordernum;
	private String condition;
	private String versionid;
	
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public long getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(long ordernum) {
		this.ordernum = ordernum;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public long getPrompt() {
		return prompt;
	}
	public void setPrompt(long prompt) {
		this.prompt = prompt;
	}
	public long getAudio() {
		return audio;
	}
	public void setAudio(long audio) {
		this.audio = audio;
	}
	
}
