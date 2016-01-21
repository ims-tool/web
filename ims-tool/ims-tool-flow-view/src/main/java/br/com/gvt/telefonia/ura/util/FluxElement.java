package br.com.gvt.telefonia.ura.util;

public class FluxElement implements Cloneable {

	public long id;
	public String type;
	public String name;
	public String description;
	public long next;
	public long nextFormDefault;
	public String formName = "";
	public FluxElement parent;
	public String tag;
	public long form;
	public String aux = "";
	
	public String getAux() {
		return aux;
	}
	public void setAux(String aux) {
		this.aux = aux;
	}
	public long getForm() {
		return form;
	}
	public void setForm(long form) {
		this.form = form;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public FluxElement getParent() {
		return parent;
	}
	public void setParent(FluxElement parent) {
		this.parent = parent;
	}
	public long getNextFormDefault() {
		return nextFormDefault;
	}
	public void setNextFormDefault(long nextFormDefault) {
		this.nextFormDefault = nextFormDefault;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public long getId() {
		return id;
	}
	
	public String getUniqueId() {
		return type+id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
	public long getNext() {
		return next;
	}
	public void setNext(long next) {
		this.next = next;
	}
		
	public String toString()
	{
		String parentString = "";
		if(this.parent != null)
			parentString = " (" + this.parent.getId() + " " + this.parent.getDescription() + ") ";
		
		return 
		"id: " + id +
		"type: " + type +
		"name: " + name +
		"description: " + description +
		"next: " + next +
		"nextFormDefault: " + nextFormDefault +
		"parent: " + parentString +
		"tag: " + tag;
	}
	
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
