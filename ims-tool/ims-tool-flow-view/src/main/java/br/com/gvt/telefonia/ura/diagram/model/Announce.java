package br.com.gvt.telefonia.ura.diagram.model;

public class Announce extends Entity<Announce> {

	private long id;
	private String name;	
	private String description;
	private String flushprompt;
	private long prompt;
	private long nextform;
	private String tag;
	private String versionid;
	
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public String getFlushprompt() {
		return flushprompt;
	}
	public void setFlushprompt(String flushprompt) {
		this.flushprompt = flushprompt;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
	public long getNextform() {
		return nextform;
	}
	public void setNextform(long nextform) {
		this.nextform = nextform;
	}
}
