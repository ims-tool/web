package br.com.gvt.telefonia.ura.diagram.model;

public class Choice extends Entity<Choice>{

	private long id;
	private String name;	
	private long menu;
	private String dtmf;
	private String nextform;
	private String condition;
	private String tag;
	private String versionid;
	
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getDtmf() {
		return dtmf;
	}
	public void setDtmf(String dtmf) {
		this.dtmf = dtmf;
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
	public long getMenu() {
		return menu;
	}
	public void setMenu(long menu) {
		this.menu = menu;
	}
	public String getNextform() {
		return nextform;
	}
	public void setNextform(String nextform) {
		this.nextform = nextform;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
