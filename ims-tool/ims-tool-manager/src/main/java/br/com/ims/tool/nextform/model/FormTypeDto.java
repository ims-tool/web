package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class FormTypeDto implements Serializable {
	private static final long serialVersionUID = -6684775225543755556L;
	private long id;
	private String name;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "FormTypeDto [id=" + this.id + ", name=" + this.name + "]";
	}
}
