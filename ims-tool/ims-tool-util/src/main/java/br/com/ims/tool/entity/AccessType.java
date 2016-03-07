
package br.com.ims.tool.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cesar
 */
public class AccessType {
	
	List<String> system;
	List<String> artifact;
	List<String> accessType;
	List<String> area;
	
	public AccessType() {
		this.system = new ArrayList<String>();
		this.artifact = new ArrayList<String>();
		this.accessType = new ArrayList<String>();
		this.area = new ArrayList<String>();
	}

	public List<String> getSystem() {
		return system;
	}

	public void setSystem(List<String> system) {
		this.system = system;
	}

	public List<String> getArtifact() {
		return artifact;
	}

	public void setArtifact(List<String> artifact) {
		this.artifact = artifact;
	}




	public List<String> getAccessType() {
		return accessType;
	}

	public void setAccessType(List<String> accessType) {
		this.accessType = accessType;
	}

	public List<String> getArea() {
		return area;
	}

	public void setArea(List<String> area) {
		this.area = area;
	}

}

