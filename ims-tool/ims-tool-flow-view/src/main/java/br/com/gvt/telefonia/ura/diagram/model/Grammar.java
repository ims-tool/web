package br.com.gvt.telefonia.ura.diagram.model;

public class Grammar extends Entity<Grammar> {
	
	private long id;
	private String name;
	private String description;
	private String type;
	private String sizemax;
	private String sizemin;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSizemax() {
		return sizemax;
	}
	public void setSizemax(String sizemax) {
		this.sizemax = sizemax;
	}
	public String getSizemin() {
		return sizemin;
	}
	public void setSizemin(String sizemin) {
		this.sizemin = sizemin;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
}
