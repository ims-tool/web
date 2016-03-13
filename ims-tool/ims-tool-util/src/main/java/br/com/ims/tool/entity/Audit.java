
package br.com.ims.tool.entity;

import java.util.Date;

/**
 *
 * @author Cesar
 */
public class Audit {
	
	private Integer id;
	private Integer userid;
	private Integer typeid;
	private Date rowdate;
	private String userLogin;
	private String description;
	private String artifact;
	private String originalValue;
	private Integer artifactid;
	private Integer valueid;
	
	
	
	public Integer getArtifactid() {
		return artifactid;
	}
	public void setArtifactid(Integer artifactid) {
		this.artifactid = artifactid;
	}
	public Integer getValueid() {
		return valueid;
	}
	public void setValueid(Integer valueid) {
		this.valueid = valueid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public Date getRowdate() {
		return rowdate;
	}
	public void setRowdate(Date rowdate) {
		this.rowdate = rowdate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getArtifact() {
		return artifact;
	}
	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}
	public String getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	
	
}

