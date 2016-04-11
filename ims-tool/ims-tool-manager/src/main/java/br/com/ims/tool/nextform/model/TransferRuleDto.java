package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class TransferRuleDto implements Serializable {

	private static final long serialVersionUID = -414740474138231349L;
	
	private long id;
	private int order;
	private long transferId;
	private long condition;
	private long prompt;
	private String number;
	private long tag;
	
	
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}




	public int getOrder() {
		return order;
	}



	public void setOrder(int order) {
		this.order = order;
	}



	public long getTransferId() {
		return transferId;
	}



	public void setTransferId(long transferId) {
		this.transferId = transferId;
	}



	public long getCondition() {
		return condition;
	}



	public void setCondition(long condition) {
		this.condition = condition;
	}



	
	public long getPrompt() {
		return prompt;
	}



	public void setPrompt(long prompt) {
		this.prompt = prompt;
	}



	public String getNumber() {
		return number;
	}



	public void setNumber(String number) {
		this.number = number;
	}



	public long getTag() {
		return tag;
	}



	public void setTag(long tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return "TransferRuleDto [id=" + id + ", order=" + order + ", transferId=" + transferId 
				+ ", condition=" + condition + ", prompt=" +prompt 
				+ ", number=" + number +  "]";
	}

}
