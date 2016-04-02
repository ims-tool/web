package br.com.ims.tool.nextform.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestInvokeMethod {

	private String context;
	private Boolean active;
	private String methodName;
	private Integer timeout;
	private Boolean internalService;
	private String parameters;
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public Boolean getInternalService() {
		return internalService;
	}
	public void setInternalService(Boolean internalService) {
		this.internalService = internalService;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
}
