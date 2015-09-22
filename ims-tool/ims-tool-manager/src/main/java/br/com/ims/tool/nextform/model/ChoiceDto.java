package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class ChoiceDto implements Serializable {
	private static final long serialVersionUID = 3671079318368490228L;
	private long id;
	private String name;
	private String dtmf;
	private long nextForm;
	private long menu;
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
	public String getDtmf() {
		return dtmf;
	}
	public void setDtmf(String dtmf) {
		this.dtmf = dtmf;
	}
	public long getNextForm() {
		return nextForm;
	}
	public void setNextForm(long nextForm) {
		this.nextForm = nextForm;
	}
	public long getMenu() {
		return menu;
	}
	public void setMenu(long menu) {
		this.menu = menu;
	}
	public long getTag() {
		return tag;
	}
	public void setTag(long tag) {
		this.tag = tag;
	}
	@Override
	public String toString() {
		return "ChoiceDto [id=" + id + ", name=" + name + ", dtmf=" + dtmf
				+ ", nextForm=" + nextForm + ", menu=" + menu + ", tag=" + tag
				+ "]";
	}
	
}
