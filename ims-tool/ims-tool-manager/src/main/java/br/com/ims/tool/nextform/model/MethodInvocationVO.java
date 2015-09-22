package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class MethodInvocationVO implements Serializable {

	private static final long serialVersionUID = 3727390900408678399L;
	
	private String jsonContext;
	private String value;
	private long errorCode;
	
	private double timeService;
	
	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public static MethodInvocationVO getInstance() {
		return new MethodInvocationVO();
	}
	
	public static MethodInvocationVO getInstance(String jsonContext) {
		return new MethodInvocationVO(jsonContext, null);
	}

	public static MethodInvocationVO getInstance(String jsonContext, String value) {
		return new MethodInvocationVO(jsonContext, value);
	}
	
	public static MethodInvocationVO getInstance(String jsonContext, String value, long errorCode) {
		return new MethodInvocationVO(jsonContext, value, errorCode);
	}
	
	public double getTimeService() {
		return timeService;
	}

	public void setTimeService(double timeService) {
		this.timeService = timeService;
	}

	private MethodInvocationVO() {
		errorCode = 0;
	}
	
	private MethodInvocationVO(String jsonContext, String value) {
		this.jsonContext = jsonContext;
		this.value = value;
		errorCode = 0;
	}
	
	private MethodInvocationVO(String jsonContext, String value, long errorCode) {
		this.jsonContext = jsonContext;
		this.value = value;
		this.errorCode = errorCode;
	}
	
	public String getJsonContext() {
		return jsonContext;
	}
	public void setJsonContext(String jsonContext) {
		this.jsonContext = jsonContext;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return jsonContext;
	}
	
}
