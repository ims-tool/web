package br.com.gvt.telefonia.ura.diagram.model;

public class NoMatchInput extends Entity<NoMatchInput> {
	
	private long id;
	private String type;
	private String threshold;
	private long prompt;
	private long nextform;
	private String name;
	private String tag;
	private String versionid;
	
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
