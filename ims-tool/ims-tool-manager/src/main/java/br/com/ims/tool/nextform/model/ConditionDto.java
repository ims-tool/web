package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Collection;

public class ConditionDto implements Serializable {

	private static final long serialVersionUID = -2426675012128303715L;
	
	private long id;
	private String name;
	private String description;
	private long tag;
	private Collection<ConditionGroupDto> listaConditionGroup;
	
	
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
	public Collection<ConditionGroupDto> getListaConditionGroup() {
		return listaConditionGroup;
	}
	public void setListaConditionGroup(
			Collection<ConditionGroupDto> listaConditionGroup) {
		this.listaConditionGroup = listaConditionGroup;
	}
	@Override
	public String toString() {
		return "ConditionDto [id=" + id + ", name=" + name + ", description="
				+ description + ", tag=" + tag + ", listaConditionGroup="
				+ listaConditionGroup + "]";
	}
}
