package br.com.ims.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class UserSystemArtifactPermission implements Serializable {
	/**
	 * 
	 */
	
	
	private String system;
	private String artifact;
	private String permission;
	
	
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
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	
}
