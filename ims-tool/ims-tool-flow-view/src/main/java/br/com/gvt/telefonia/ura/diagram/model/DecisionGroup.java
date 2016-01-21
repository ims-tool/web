package br.com.gvt.telefonia.ura.diagram.model;

public class DecisionGroup extends Entity<DecisionGroup> {

	private long id;
	private long decisionid;
	private long ordernum;
	private long decisionmapid;
	private String description;
	private long versionid;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDecisionid() {
		return decisionid;
	}
	public void setDecisionid(long decisionid) {
		this.decisionid = decisionid;
	}
	public long getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(long ordernum) {
		this.ordernum = ordernum;
	}
	public long getDecisionmapid() {
		return decisionmapid;
	}
	public void setDecisionmapid(long decisionmapid) {
		this.decisionmapid = decisionmapid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getVersionid() {
		return versionid;
	}
	public void setVersionid(long versionid) {
		this.versionid = versionid;
	}
}
