package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class OperationParametersDto implements Serializable {

	private static final long serialVersionUID = 2270420456688755188L;
	
	private long id;
	private long operationGroupId;
	private String paramName;
	private String paramValue;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOperationGroupId() {
		return operationGroupId;
	}
	public void setOperationGroupId(long operationGroupId) {
		this.operationGroupId = operationGroupId;
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
		return "OperationParametersDto [id=" + id + ", operationGroupId="
				+ operationGroupId + ", paramName=" + paramName
				+ ", paramValue=" + paramValue + "]";
	}

}
