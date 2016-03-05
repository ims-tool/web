package br.com.ims.dashboard.model;

import java.util.List;

public class TrackDetailModel {

	private Integer id;
	private Long formId;
	private String formName;
	private Long trackId;
	private String rowdate;
	private String startdate;
	private String stopdate;
	private Integer tagId;
	private String description;
	private Integer formTypeId;
	private String formTypeName;
	
	List<TrackServiceModel> service;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}

	

	public Long getTrackId() {
		return trackId;
	}
	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}
	public String getRowdate() {
		return rowdate;
	}
	public void setRowdate(String rowdate) {
		this.rowdate = rowdate;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getStopdate() {
		return stopdate;
	}
	public void setStopdate(String stopdate) {
		this.stopdate = stopdate;
	}
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	

	public Integer getFormTypeId() {
		return formTypeId;
	}
	public void setFormTypeId(Integer formTypeId) {
		this.formTypeId = formTypeId;
	}
	public String getFormTypeName() {
		return formTypeName;
	}
	public void setFormTypeName(String formTypeName) {
		this.formTypeName = formTypeName;
	}
	public List<TrackServiceModel> getService() {
		return service;
	}
	public void setService(List<TrackServiceModel> service) {
		this.service = service;
	}

	
	

	
	
}
