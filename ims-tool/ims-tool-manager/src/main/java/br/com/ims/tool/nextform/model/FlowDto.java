package br.com.ims.tool.nextform.model;

import java.io.Serializable;

public class FlowDto implements Serializable {

	private static final long serialVersionUID = -1453072735002439513L;
	
	private long id;
	private String name;
	private String description;
	private String flowName;
	private long nextForm;
	private long tag;
	
	private long flowFirstForm;
	
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
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public long getNextForm() {
		return nextForm;
	}
	public void setNextForm(long nextForm) {
		this.nextForm = nextForm;
	}
	public long getTag() {
		return tag;
	}
	public void setTag(long tag) {
		this.tag = tag;
	}
	public long getFlowFirstForm() {
		return flowFirstForm;
	}
	public void setFlowFirstForm(long flowFirstForm) {
		this.flowFirstForm = flowFirstForm;
	}
	@Override
	public String toString() {
		return "FlowDto [id=" + id + ", name=" + name + ", description=" + description + ", flowName=" + flowName + ", nextForm=" + nextForm + ", tag=" + tag + ", flowFirstForm=" + flowFirstForm + "]";
	}
	

}
