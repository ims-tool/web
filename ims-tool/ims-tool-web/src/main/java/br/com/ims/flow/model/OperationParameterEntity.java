package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class OperationParameterEntity extends AbstractEntity {
	
	
	private String operationGroupId;
	private String paramName;
	private String paramValue;
	
	
	
	public String getOperationGroupId() {
		return operationGroupId;
	}

	public void setOperationGroupId(String operationGroupId) {
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
	
		
}
