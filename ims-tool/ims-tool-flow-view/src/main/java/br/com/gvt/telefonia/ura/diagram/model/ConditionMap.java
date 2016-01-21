package br.com.gvt.telefonia.ura.diagram.model;

public class ConditionMap extends Entity<ConditionMap> {
	
	private long id;
	private String name;
	private String description;
	private String type;
	private String methodreference;
	private long versionid;
	private int log_active;

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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMethodreference() {
		return methodreference;
	}
	public void setMethodreference(String methodreference) {
		this.methodreference = methodreference;
	}
	public long getVersionid() {
		return versionid;
	}
	public void setVersionid(long versionid) {
		this.versionid = versionid;
	}
	public int getLog_active() {
		return log_active;
	}
	public void setLog_active(int log_active) {
		this.log_active = log_active;
	}
}
