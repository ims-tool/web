package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class LogicNodeParameterEntity extends AbstractEntity {
	
	
	private String logicNodeId;
	private String paramName;
	private String paramValue;
	
	
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

	public String getLogicNodeId() {
		return logicNodeId;
	}

	public void setLogicNodeId(String logicNodeId) {
		this.logicNodeId = logicNodeId;
	}
	
		
}
