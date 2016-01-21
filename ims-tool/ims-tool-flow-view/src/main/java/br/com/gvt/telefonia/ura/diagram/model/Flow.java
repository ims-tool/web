package br.com.gvt.telefonia.ura.diagram.model;


public class Flow extends Entity<Flow> {
	
	private long id;
	private String name;
	private String description;
	private String flowname;
	private String nextForm;
	private String tag;
	private String versionid;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
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
	public String getFlowname() {
		return flowname;
	}
	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNextForm() {
		return nextForm;
	}
	public void setNextForm(String nextForm) {
		this.nextForm = nextForm;
	}
}
