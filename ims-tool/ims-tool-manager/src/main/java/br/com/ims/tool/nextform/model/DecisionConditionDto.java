package br.com.ims.tool.nextform.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class DecisionConditionDto implements Serializable {
	private static final long serialVersionUID = 5656420580110308160L;
	
	private String jsonContext;
	private Boolean condition;
	
	public String getJsonContext() {
		return jsonContext;
	}
	public void setJsonContext(String jsonContext) {
		this.jsonContext = jsonContext;
	}
	public Boolean getCondition() {
		return condition;
	}
	public void setCondition(Boolean condition) {
		this.condition = condition;
	}
	
	
	
}
