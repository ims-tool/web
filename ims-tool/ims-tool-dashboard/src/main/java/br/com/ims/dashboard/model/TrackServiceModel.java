package br.com.ims.dashboard.model;

public class TrackServiceModel {

	private String methodService;
	private String parametersIn;
	private String resultCall;
	private Integer errorCodeId;
	private String description;
	
	private TagModel tag;
	
	public TrackServiceModel() {
		tag = new TagModel();
	}
	
	public String getMethodService() {
		return methodService;
	}
	public void setMethodService(String methodService) {
		this.methodService = methodService;
	}
	public String getParametersIn() {
		return parametersIn;
	}
	public void setParametersIn(String parametersIn) {
		this.parametersIn = parametersIn;
	}
	public String getResultCall() {
		return resultCall;
	}
	public void setResultCall(String resultCall) {
		this.resultCall = resultCall;
	}
	public Integer getErrorCodeId() {
		return errorCodeId;
	}
	public void setErrorCodeId(Integer errorCodeId) {
		this.errorCodeId = errorCodeId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TagModel getTag() {
		return tag;
	}
	public void setTag(TagModel tag) {
		this.tag = tag;
	}
	
	
	
	
	
}
