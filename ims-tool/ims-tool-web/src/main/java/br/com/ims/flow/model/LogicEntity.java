package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class LogicEntity extends AbstractEntity{
	
	private String name;
	private String description;
	private TagEntity tag;	
	private List<LogicNodeEntity> listLogicNode;
	
	
	
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
	public List<LogicNodeEntity> getListLogicNode() {
		return listLogicNode;
	}
	public void setListLogicNode(List<LogicNodeEntity> listLogicNode) {
		this.listLogicNode = listLogicNode;
	}

	
	

	
	
}
