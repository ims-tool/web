package br.com.ims.flow.model;


@SuppressWarnings("serial")
public class DecisionChanceEntity extends AbstractFormEntity implements Comparable <DecisionChanceEntity>{
	
	private String decisionId;
	private Integer orderNum;
	private ConditionEntity condition;
	

	public String getDecisionId() {
		return decisionId;
	}




	public void setDecisionId(String decisionId) {
		this.decisionId = decisionId;
	}




	public Integer getOrderNum() {
		return orderNum;
	}




	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
