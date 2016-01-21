package br.com.gvt.telefonia.ura.diagram.model;


public class OperationGroup extends Entity<OperationGroup> {
	
	private long id;
	private long operationid;
	private int ordernum;
	private long operationmapid;
	private String 	description;
	private String versionid;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOperationid() {
		return operationid;
	}
	public void setOperationid(long operationid) {
		this.operationid = operationid;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public long getOperationmapid() {
		return operationmapid;
	}
	public void setOperationmapid(long operationmapid) {
		this.operationmapid = operationmapid;
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
