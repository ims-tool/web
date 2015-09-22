package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class DecisionParametersDto implements Serializable {

	private static final long serialVersionUID = 5030954004735368951L;
	
	private long id;
	private long decisionGroupId;
	private String paramName;
	private String paramValue;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDecisionGroupId() {
		return decisionGroupId;
	}
	public void setDecisionGroupId(long decisionGroupId) {
		this.decisionGroupId = decisionGroupId;
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
		return "DecisionParametersDto [id=" + id + ", decisionGroupId="
				+ decisionGroupId + ", paramName=" + paramName
				+ ", paramValue=" + paramValue + "]";
	}
	
}
