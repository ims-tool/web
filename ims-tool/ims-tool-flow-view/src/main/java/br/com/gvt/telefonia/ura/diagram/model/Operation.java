package br.com.gvt.telefonia.ura.diagram.model;


public class Operation extends Entity<Operation> {
	
	private long id;
	private String name;	
	private String description;
	private long nextFormid;
	private long tag;
	private String versionid;
	
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
	public long getNextFormid() {
		return nextFormid;
	}
	public void setNextFormid(long nextFormid) {
		this.nextFormid = nextFormid;
	}
	public long getTag() {
		return tag;
	}
	public void setTag(long tag) {
		this.tag = tag;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}	
}
