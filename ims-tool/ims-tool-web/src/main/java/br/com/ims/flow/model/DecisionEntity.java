package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class DecisionEntity extends AbstractFormEntity{
	private TagEntity tag;
	private List<DecisionChanceEntity> listDecisionChance;
	
	public List<DecisionChanceEntity> getListDecisionChance() {
		return listDecisionChance;
	}
	public void setListDecisionChance(List<DecisionChanceEntity> listDecisionChance) {
		this.listDecisionChance = listDecisionChance;
	}
	public TagEntity getTag() {
		return tag;
	}
	public void setTag(TagEntity tag) {
		this.tag = tag;
	}
	
	
	
	
}
