package br.com.gvt.telefonia.ura.diagram.model;

public class ConditionGroup extends Entity<ConditionGroup> {
	
	private long id;
	private long conditionid;
	private int ordernum;
	private long conditionmapid;
	private String description;
	private long versionid;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getConditionid() {
		return conditionid;
	}
	public void setConditionid(long conditionid) {
		this.conditionid = conditionid;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public long getConditionmapid() {
		return conditionmapid;
	}
	public void setConditionmapid(long conditionmapid) {
		this.conditionmapid = conditionmapid;
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
