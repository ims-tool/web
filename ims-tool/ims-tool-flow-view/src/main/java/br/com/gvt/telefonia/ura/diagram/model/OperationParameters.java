package br.com.gvt.telefonia.ura.diagram.model;


public class OperationParameters extends Entity<OperationParameters> {
	
	private long id;
	private String operationgroupid;
	private String paramname;
	private String paramvalue;
	private String versionid;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOperationgroupid() {
		return operationgroupid;
	}
	public void setOperationgroupid(String operationgroupid) {
		this.operationgroupid = operationgroupid;
	}
	public String getParamname() {
		return paramname;
	}
	public void setParamname(String paramname) {
		this.paramname = paramname;
	}
	public String getParamvalue() {
		return paramvalue;
	}
	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}	
}
