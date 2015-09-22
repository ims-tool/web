package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class TransferDto implements Serializable {

	private static final long serialVersionUID = -414740474138231349L;
	
	private long id;
	private String name;
	private String description;
	private long transferRuleId;
	private long tag;
	
	private PromptDto prompt;
	private String vdn;
	private String UUI;
	
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
	public long getTransferRuleId() {
		return transferRuleId;
	}
	public void setTransferRuleId(long transferRuleId) {
		this.transferRuleId = transferRuleId;
	}
	public long getTag() {
		return tag;
	}
	public void setTag(long tag) {
		this.tag = tag;
	}
	public PromptDto getPrompt() {
		return prompt;
	}
	public void setPrompt(PromptDto prompt) {
		this.prompt = prompt;
	}
	public String getVdn() {
		return vdn;
	}
	public void setVdn(String vdn) {
		this.vdn = vdn;
	}
	public String getUUI() {
		return UUI;
	}
	public void setUUI(String uUI) {
		UUI = uUI;
	}
	@Override
	public String toString() {
		return "TransferDto [id=" + id + ", name=" + name + ", description=" + description 
				+ ", transferRuleId=" + transferRuleId + ", tag=" + tag + ", prompt=" + prompt 
				+ ", vdn=" + vdn + ", UUI=" + UUI + "]";
	}

}
