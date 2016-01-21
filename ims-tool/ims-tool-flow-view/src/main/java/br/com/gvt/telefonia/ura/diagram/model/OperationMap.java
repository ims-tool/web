package br.com.gvt.telefonia.ura.diagram.model;


public class OperationMap extends Entity<OperationMap> {
	
	private long id;
	private String name;	
	private String description;
	private String methodreference;
	private String versionid;
	private String log_active;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMethodreference() {
		return methodreference;
	}
	public void setMethodreference(String methodreference) {
		this.methodreference = methodreference;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public String getLog_active() {
		return log_active;
	}
	public void setLog_active(String log_active) {
		this.log_active = log_active;
	}
}
