package br.com.gvt.telefonia.ura.diagram.model;

public class Version extends Entity<Version>{
	
	private long id;
	private String description;
	private String deploydate;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeploydate() {
		return deploydate;
	}
	public void setDeploydate(String deploydate) {
		this.deploydate = deploydate;
	}

	
}
