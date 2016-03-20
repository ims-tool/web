package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class TransferRuleEntity extends AbstractEntity implements Comparable <TransferRuleEntity> {
	
	private Integer orderNum;
	private String transferId;
	private ConditionEntity condition;
	private TagEntity tag;
	private PromptEntity prompt;
	private String number;
		
	
	
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



	public TagEntity getTag() {
		return tag;
	}



	public void setTag(TagEntity tag) {
		this.tag = tag;
	}


	
	
	public PromptEntity getPrompt() {
		return prompt;
	}



	public void setPrompt(PromptEntity prompt) {
		this.prompt = prompt;
	}



	public String getNumber() {
		return number;
	}



	public void setNumber(String number) {
		this.number = number;
	}



	@Override
	public int compareTo(TransferRuleEntity arg0) {
		// TODO Auto-generated method stub
		if( this.orderNum < arg0.getOrderNum()) {
			return -1;
		} else if(this.orderNum > arg0.getOrderNum()) {
			return 1;
		}
		return 0;
	}
	
	public String getConditionName() {
		if(this.condition != null )
			return this.condition.getName();
		return "N/A";
	}
	public String getTagName() {
		if(this.tag != null) {
			return this.tag.getId()+" - "+this.tag.getDescription();
		}
		return "N/A";
	}
	public String getPromptName() {
		if(this.prompt != null) {
			return this.prompt.getName();
		}
		return "N/A";
		
	}



	public String getTransferId() {
		return transferId;
	}



	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	
}
