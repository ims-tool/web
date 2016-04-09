package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class ConditionParametersDto implements Serializable {

	private static final long serialVersionUID = 6025232247106308451L;
	
	private long id;
	private long conditionGroupId;
	private String paramName;
	private String paramValue;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getConditionGroupId() {
		return conditionGroupId;
	}
	public void setConditionGroupId(long conditionGroupId) {
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
	@Override
	public String toString() {
		return "ConditionParametersDto [id=" + id + ", conditionGroupId="
				+ conditionGroupId + ", paramName=" + paramName
				+ ", paramValue=" + paramValue + "]";
	}

}
