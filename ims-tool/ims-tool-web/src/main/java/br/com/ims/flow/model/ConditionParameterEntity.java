package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class ConditionParameterEntity extends AbstractEntity {
	
	
	private Integer conditionGroupId;
	private String paramName;
	private String paramValue;
	
	
	public Integer getConditionGroupId() {
		return conditionGroupId;
	}

	public void setConditionGroupId(Integer conditionGroupId) {
		this.conditionGroupId = conditionGroupId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
		
}
