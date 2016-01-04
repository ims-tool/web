package br.com.ims.util;

import java.util.ArrayList;
import java.util.List;

public class Artifact {
	
	private Integer artifactid;
	private Integer userid;
	private Integer accesstypeid;
	private List<Integer> userprofiles;
	private Boolean access;
	
	
	public Artifact() {
		userprofiles = new ArrayList<Integer>();
	}
	public Integer getArtifactid() {
		return artifactid;
	}
	public void setArtifactid(Integer artifactid) {
		this.artifactid = artifactid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getAccesstypeid() {
		return accesstypeid;
	}
	public void setAccesstypeid(Integer accesstypeid) {
		this.accesstypeid = accesstypeid;
	}
	public List<Integer> getUserprofiles() {
		return userprofiles;
	}
	public void setUserprofiles(List<Integer> userprofiles) {
		this.userprofiles = userprofiles;
	}
	public void add(Integer profileid) {
		this.userprofiles.add(profileid);
	}
}
