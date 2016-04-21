
package br.com.ims.tool.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cesar
 */
@XmlRootElement
public class ReportLog {
	
	
	Integer id;
	String rowdate;
	String login;
	String type;
	String description;
	String artifact;
	String originalValue;
	Integer valueid;
	Integer artifactid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRowdate() {
		return rowdate;
	}
	public void setRowdate(String rowdate) {
		this.rowdate = rowdate;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Integer getValueid() {
		return valueid;
	}
	public void setValueid(Integer valueid) {
		this.valueid = valueid;
	}
	public Integer getArtifactid() {
		return artifactid;
	}
	public void setArtifactid(Integer artifactid) {
		this.artifactid = artifactid;
	}
	
	
}

