package br.com.gvt.telefonia.ura.diagram.model;

public class ConditionParameters extends Entity<ConditionParameters> {
	
	private long id;
	private long conditiongroupid;
	private String paramname;
	private String paramvalue;
	private String versionid;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getConditiongroupid() {
		return conditiongroupid;
	}
	public void setConditiongroupid(long conditiongroupid) {
		this.conditiongroupid = conditiongroupid;
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

