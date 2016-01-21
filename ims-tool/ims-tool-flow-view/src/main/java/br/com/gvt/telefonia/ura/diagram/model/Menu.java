package br.com.gvt.telefonia.ura.diagram.model;


public class Menu  extends Entity<Menu> {

	private long id;
	private String name;	
	private String description;
	private long prompt;
	private long noinput;
	private long nomatch;
	private String fetchtimeout;
	private String terminatingtimeout;
	private String terminatingcharacter;
	private String versionid;
	
	public String getFetchtimeout() {
		return fetchtimeout;
	}
	public void setFetchtimeout(String fetchtimeout) {
		this.fetchtimeout = fetchtimeout;
	}
	public String getTerminatingtimeout() {
		return terminatingtimeout;
	}
	public void setTerminatingtimeout(String terminatingtimeout) {
		this.terminatingtimeout = terminatingtimeout;
	}
	public String getTerminatingcharacter() {
		return terminatingcharacter;
	}
	public void setTerminatingcharacter(String terminatingcharacter) {
		this.terminatingcharacter = terminatingcharacter;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}	
	public long getNomatch() {
		return nomatch;
	}
	public void setNomatch(long nomatch) {
		this.nomatch = nomatch;
	}
	public long getNoinput() {
		return noinput;
	}
	public void setNoinput(long noinput) {
		this.noinput = noinput;
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
	public long getPrompt() {
		return prompt;
	}
	public void setPrompt(long prompt) {
		this.prompt = prompt;
	}	
}
