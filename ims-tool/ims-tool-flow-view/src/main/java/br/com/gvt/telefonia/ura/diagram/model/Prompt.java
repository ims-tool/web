package br.com.gvt.telefonia.ura.diagram.model;

public class Prompt extends Entity<Prompt> {
	
	private long id;
	private String name;
	private String versionid;
	
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
	
	
}
