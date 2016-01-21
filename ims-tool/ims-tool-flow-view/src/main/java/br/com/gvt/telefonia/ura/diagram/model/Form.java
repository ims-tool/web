package br.com.gvt.telefonia.ura.diagram.model;

import java.util.Set;

import br.com.gvt.telefonia.ura.util.Util;

public class Form extends Entity<Form> {
	
	
	public static final int FT_ANNOUNCE = 1;
	public static final int FT_PROMPTCOLLECT = 2;
	public static final int FT_MENU = 3;
	
	public static final int FT_FLOW = 4;
	
	public static final int FT_DECISION = 5;
	public static final int FT_OPERATION = 6;
	public static final int FT_TRANSFER = 7;
	
	public static final int FT_DISCONNECT = 8;
	
	public static final int FT_FLOWINTERNAL = 9;
	
	public static final int FT_THREAD = 10;
	
	public static final int FT_CONDITION = 11;

	private long id;
	private String name = "";	
	private String description = "";
	private long formType;
	private long nextForm;
	private long formid;
	private String condition = "";
	private String tag = "";
	private String versionid = "";
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}	
	public long getFormid() {
		return formid;
	}
	public void setFormid(long formid) {
		this.formid = formid;
	}

	private long nextFormDefault;
	private Set<Form> previousForm;
	
	
	public long getNextForm() {
		return nextForm;
	}
	
	public void setNextForm(long nextForm) {
		this.nextForm = nextForm;
	}
	public long getNextFormDefault() {
		return nextFormDefault;
	}
	public void setNextFormDefault(long nextFormDefault) {
		this.nextFormDefault = nextFormDefault;
	}
	
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
	public long getFormType() {
		return formType;
	}
	public void setFormType(long formType) {
		this.formType = formType;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public String getTypeDesc()
	{
		return Util.getFormType(formType);
	}
}
