package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class ControlPanelEntity extends AbstractEntity {
	
	private String methodname;
	private String description;
	private String owner;
	private String referencedBy;
	private String status;
	private String loginid;
	private String startdate;
	private String versionId;
	private int timeout;
	private String internalService;
	
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getReferencedBy() {
		return referencedBy;
	}
	public void setReferencedBy(String referencedBy) {
		this.referencedBy = referencedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	
	
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getInternalService() {
		return internalService;
	}
	public void setInternalService(String internalService) {
		this.internalService = internalService;
	}
	
		
		
}
