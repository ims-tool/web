package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class GrammarDto implements Serializable {
	private static final long serialVersionUID = 4097653851489662238L;
	private long id;
	private String name;
	private String description;
	private String type;
	private long sizeMax;
	private long sizeMin;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSizeMax() {
		return sizeMax;
	}
	public void setSizeMax(long sizeMax) {
		this.sizeMax = sizeMax;
	}
	public long getSizeMin() {
		return sizeMin;
	}
	public void setSizeMin(long sizeMin) {
		this.sizeMin = sizeMin;
	}
	@Override
	public String toString() {
		return "GrammarDto [id=" + id + ", name=" + name + ", description="
				+ description + ", type=" + type + ", sizeMax=" + sizeMax
				+ ", sizeMin=" + sizeMin + "]";
	}
}
