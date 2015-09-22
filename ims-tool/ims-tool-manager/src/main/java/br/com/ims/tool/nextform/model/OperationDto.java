package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Collection;

public class OperationDto  implements Serializable {

	private static final long serialVersionUID = -5512759549516226439L;
	
	private long id;
	private String name;
	private String description;
	private long nextform;
	private Collection<OperationGroupDto> listaOperationGroup;
	
	private long tag;
	
	public long getTag() {
		return tag;
	}
	public void setTag(long tag) {
		this.tag = tag;
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
	public long getNextform() {
		return nextform;
	}
	public void setNextform(long nextform) {
		this.nextform = nextform;
	}
	public Collection<OperationGroupDto> getListaOperationGroup() {
		return listaOperationGroup;
	}
	public void setListaOperationGroup(
			Collection<OperationGroupDto> listaOperationGroup) {
		this.listaOperationGroup = listaOperationGroup;
	}
	@Override
	public String toString() {
		return "OperationDto [id=" + id + ", name=" + name + ", description="
				+ description + ", nextform=" + nextform
				+ ", listaOperationGroup=" + listaOperationGroup + "]";
	}
}
