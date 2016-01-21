package br.com.gvt.telefonia.ura.diagram.dao;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.DecisionChance;

public class DecisionChanceDAO extends DAO<DecisionChance> {
	
	
	
	public List<DecisionChance> findByGroup(String id){
		
		return findAllBy(" decisiongroupid = '"+id+"' order by ordernum");
	}
	
	@Override
	public String getClassName() {
		return DecisionChance.class.getSimpleName();
	}

}
