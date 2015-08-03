package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class ConditionGroupEntity extends AbstractEntity{
	
	
	private Integer conditionGroupId;
	private Integer orderNum;
	private ConditionMapEntity conditionMap;
	private String description;	
	private List<ConditionParameterEntity> conditionParameters;
	private List<ConditionValueEntity> conditionValues;
	

	public Integer getConditionGroupId() {
		return conditionGroupId;
	}
	public void setConditionGroupId(Integer conditionGroupId) {
		this.conditionGroupId = conditionGroupId;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public ConditionMapEntity getConditionMap() {
		return conditionMap;
	}
	public void setConditionMap(ConditionMapEntity conditionMap) {
		this.conditionMap = conditionMap;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<ConditionParameterEntity> getConditionParameters() {
		return conditionParameters;
	}
	public void setConditionParameters(
			List<ConditionParameterEntity> conditionParameters) {
		this.conditionParameters = conditionParameters;
	}
	public List<ConditionValueEntity> getConditionValues() {
		return conditionValues;
	}
	public void setConditionValues(List<ConditionValueEntity> conditionValues) {
		this.conditionValues = conditionValues;
	}
	
	
	
}
