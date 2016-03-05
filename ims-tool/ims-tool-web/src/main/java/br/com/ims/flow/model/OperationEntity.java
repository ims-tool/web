package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class OperationEntity extends AbstractFormEntity{
	
	private TagEntity tag;	
	private List<OperationGroupEntity> listOperationGroup;
	
	
	
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
	public TagEntity getTag() {
		return tag;
	}
	public void setTag(TagEntity tag) {
		this.tag = tag;
	}
	public List<OperationGroupEntity> getListOperationGroup() {
		return listOperationGroup;
	}
	public void setListOperationGroup(List<OperationGroupEntity> listOperationGroup) {
		this.listOperationGroup = listOperationGroup;
	}

	

	
	

	
	
}
