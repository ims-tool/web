package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Date;

public class LogDetailDto implements Serializable {

	private static final long serialVersionUID = -2456190703512238704L;
	
	private long id;
	private long logId;
	private Date rowDate; 
	private String paramName;
	private String paramValue;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLogId() {
		return logId;
	}
	public void setLogId(long logId) {
		this.logId = logId;
	}
	public Date getRowDate() {
		return rowDate;
	}
	public void setRowDate(Date rowDate) {
		this.rowDate = rowDate;
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
		return "LogDetailDto [id=" + id + ", logId=" + logId + ", rowDate=" + rowDate + ", paramName=" + paramName + ", paramValue=" + paramValue + "]";
	}	

}
