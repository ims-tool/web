package br.com.gvt.telefonia.ura.diagram.model;

public class Transfer extends Entity<Transfer> {
	
	public long id;
	public String name;
	public String description;
	public String transferruleid;
	public String tag;
	public String versionid;
	
	public String getTransferruleid() {
		return transferruleid;
	}
	public void setTransferruleid(String transferruleid) {
		this.transferruleid = transferruleid;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
