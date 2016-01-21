package br.com.gvt.telefonia.ura.diagram.model;

public class Router extends Entity<Router> {
	
	private String dnis;
	private String description;
	private String formname;
	private String context;
	
	public String getDnis() {
		return dnis;
	}
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	
}
