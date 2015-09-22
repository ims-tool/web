package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class DisconnectDto implements Serializable {

	private static final long serialVersionUID = 2929404896025054142L;
	
	private long id;
	private String name;
	private String description;
	private long tag;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getTag() {
		return tag;
	}
	public void setTag(long tag) {
		this.tag = tag;
	}
	@Override
	public String toString() {
		return "DisconnectDto [id=" + id + ", name=" + name + ", description="
				+ description + ", tag=" + tag + "]";
	}
}
