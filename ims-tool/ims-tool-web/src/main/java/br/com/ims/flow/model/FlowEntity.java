package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class FlowEntity extends AbstractFormEntity{
	
	private String flowName;
	private TagEntity tag;
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public TagEntity getTag() {
		return tag;
	}
	public void setTag(TagEntity tag) {
		this.tag = tag;
	}
		
	
		
		
}
