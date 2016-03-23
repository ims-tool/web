package br.com.ims.flow.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ConditionGroupEntity extends AbstractEntity{
	
	private Integer orderNum;
	private String conditionId;
	private ConditionMapEntity conditionMap;
	private String description;	
	private List<ConditionParameterEntity> listConditionParameters;
	private List<ConditionValueEntity> listConditionValues;
	
	public ConditionGroupEntity() {
		listConditionParameters = new ArrayList<ConditionParameterEntity>();
		listConditionValues = new ArrayList<ConditionValueEntity>();
	}

	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
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
	public List<ConditionParameterEntity> getListConditionParameters() {
		return listConditionParameters;
	}
	public void setListConditionParameters(List<ConditionParameterEntity> listConditionParameters) {
		this.listConditionParameters = listConditionParameters;
	}
	public List<ConditionValueEntity> getListConditionValues() {
		return listConditionValues;
	}
	public void setListConditionValues(List<ConditionValueEntity> listConditionValues) {
		this.listConditionValues = listConditionValues;
	}
	
	
	
}
