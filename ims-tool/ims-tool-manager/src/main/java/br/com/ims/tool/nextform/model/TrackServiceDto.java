package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class TrackServiceDto implements Serializable{

	private static final long serialVersionUID = 8373528902507760277L;
	
	private long id;
	private long trackid;
	private long groupid;
	private String method_service;
	private String result_call;
	private String parameters_in;
	private long errorCode;
	private String description;
	private double time_exec;
	
	private long logId;
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTrackid() {
		return this.trackid;
	}
	public void setTrackid(long trackid) {
		this.trackid = trackid;
	}
	public long getGroupid() {
		return this.groupid;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}
	public String getMethod_service() {
		return this.method_service;
	}
	public void setMethod_service(String method_service) {
		this.method_service = method_service;
	}
	public String getResult_call() {
		return this.result_call;
	}
	public void setResult_call(String result_call) {
		this.result_call = result_call;
	}
	public String getParameters_in() {
		return this.parameters_in;
	}
	public void setParameters_in(String parameters_in) {
		this.parameters_in = parameters_in;
	}
	public long getErrorCode() {
		return this.errorCode;
	}
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getLogId() {
		return logId;
	}
	public void setLogId(long logId) {
		this.logId = logId;
	}
	
	public double getTime_exec() {
		return time_exec;
	}
	public void setTime_exec(double time_exec) {
		this.time_exec = time_exec;
	}
	@Override
	public String toString() {
		return "TrackServiceDto [id=" + id + ", trackid=" + trackid
				+ ", groupid=" + groupid + ", method_service=" + method_service
				+ ", result_call=" + result_call + ", parameters_in="
				+ parameters_in + ", errorCode=" + errorCode + ", description="
				+ description + ", time_exec=" + time_exec + ", logId=" + logId
				+ "]";
	}
	
	

}