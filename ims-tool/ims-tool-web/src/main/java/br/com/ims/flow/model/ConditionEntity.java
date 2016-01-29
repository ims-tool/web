package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class ConditionEntity extends AbstractEntity{
	
	private String name;
	private String description;
	private TagEntity tag;	
	private List<ConditionGroupEntity> listConditionGroup;
	
	
	
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
	public List<ConditionGroupEntity> getListConditionGroup() {
		return listConditionGroup;
	}
	public void setListConditionGroup(List<ConditionGroupEntity> listConditionGroup) {
		this.listConditionGroup = listConditionGroup;
	}

	
	

	
	
}
