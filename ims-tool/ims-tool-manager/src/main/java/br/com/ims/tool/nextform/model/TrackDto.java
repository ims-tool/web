package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Date;

public class TrackDto implements Serializable {

	private static final long serialVersionUID = -7722563585522618739L;
	
	private long id;
	private long logId;
	private Date rowDate;
	private long formId;
	private long tagId;
	private String logType;
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLogId() {
		return this.logId;
	}
	public void setLogId(long logId) {
		this.logId = logId;
	}
	public Date getRowDate() {
		return this.rowDate;
	}
	public void setRowDate(Date rowDate) {
		this.rowDate = rowDate;
	}
	public long getFormId() {
		return this.formId;
	}
	public void setFormId(long formId) {
		this.formId = formId;
	}
	public long getTagId() {
		return this.tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
	public String getLogType() {
		return this.logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	@Override
	public String toString() {
		return "TrackDto [id=" + this.id + ", logId=" + this.logId + ", rowDate=" + this.rowDate + ", formId="
				+ this.formId + ", tagId=" + this.tagId + ", logType=" + this.logType + "]";
	}

}
