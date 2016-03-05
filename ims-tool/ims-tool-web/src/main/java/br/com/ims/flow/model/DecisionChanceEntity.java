package br.com.ims.flow.model;


@SuppressWarnings("serial")
public class DecisionChanceEntity extends AbstractFormEntity implements Comparable <DecisionChanceEntity>{
	
	private Integer decisionGroupId;
	private Integer orderNum;
	private ConditionEntity condition;
	private FormEntity nextForm;
	private TagEntity tag;
	
	
	public Integer getDecisionGroupId() {
		return decisionGroupId;
	}




	public void setDecisionGroupId(Integer decisionGroupId) {
		this.decisionGroupId = decisionGroupId;
	}




	public Integer getOrderNum() {
		return orderNum;
	}




	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}


	public FormEntity getNextForm() {
		return nextForm;
	}




	public void setNextForm(FormEntity nextForm) {
		this.nextForm = nextForm;
	}




	public TagEntity getTag() {
		return tag;
	}




	public void setTag(TagEntity tag) {
		this.tag = tag;
	}

	


	public ConditionEntity getCondition() {
		return condition;
	}




	public void setCondition(ConditionEntity condition) {
		this.condition = condition;
	}




	@Override
	public int compareTo(DecisionChanceEntity arg0) {
		// TODO Auto-generated method stub
		if( this.orderNum < arg0.getOrderNum()) {
			return -1;
		} else if(this.orderNum > arg0.getOrderNum()) {
			return 1;
		}
		return 0;
	}
	
	
	
	
}
