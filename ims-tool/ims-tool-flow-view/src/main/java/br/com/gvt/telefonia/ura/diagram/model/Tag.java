package br.com.gvt.telefonia.ura.diagram.model;

public class Tag extends Entity<Tag> {
	
	private long id;
	private String tagtypeid;
	private String description;
	private String versionid;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTagtypeid() {
		return tagtypeid;
	}
	public void setTagtypeid(String tagtypeid) {
		this.tagtypeid = tagtypeid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
}
