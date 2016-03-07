
package br.com.ims.tool.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cesar
 */
public class Access {
	
	String system;
	String artifact;
	String accessType;
	Integer userid;
	List<String> areaList;
	
	public Access() {
		this.areaList = new ArrayList<String>();
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public List<String> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	

	
}

