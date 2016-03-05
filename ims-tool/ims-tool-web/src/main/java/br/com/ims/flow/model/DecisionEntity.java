package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class DecisionEntity extends AbstractFormEntity{
	
	private List<DecisionChanceEntity> listDecisionChance;
	
	public List<DecisionChanceEntity> getListDecisionChance() {
		return listDecisionChance;
	}
	public void setListDecisionChance(List<DecisionChanceEntity> listDecisionChance) {
		this.listDecisionChance = listDecisionChance;
	}
	
	
	
}
