package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class DecisionChanceDto implements Serializable {

	private static final long serialVersionUID = 3585822742115926003L;
	
	private long id;
	private long decisionId;
	private long order;
	private long condition;
	private long nextForm;
	
	private long tag;
	
	public long getTag() {
		return tag;
	}
	public void setTag(long tag) {
		this.tag = tag;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getDecisionId() {
		return decisionId;
	}
	public void setDecisionId(long decisionId) {
		this.decisionId = decisionId;
	}
	public long getOrder() {
		return order;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	
	
	public long getCondition() {
		return condition;
	}
	public void setCondition(long condition) {
		this.condition = condition;
	}
	public long getNextForm() {
		return nextForm;
	}
	public void setNextForm(long nextForm) {
		this.nextForm = nextForm;
	}
	@Override
	public String toString() {
		return "DecisionChoiceDto [id=" + id + ", decisionId="
				+ decisionId + ", order=" + order  
				+ ", nextForm=" + nextForm + "]";
	}

}
