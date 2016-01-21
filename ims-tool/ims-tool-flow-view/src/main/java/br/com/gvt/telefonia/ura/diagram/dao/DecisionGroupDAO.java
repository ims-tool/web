package br.com.gvt.telefonia.ura.diagram.dao;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.DecisionGroup;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class DecisionGroupDAO extends DAO<DecisionGroup> {

	public DecisionGroup findByDecision(String id){
		
		return findBy(" decisionid= '"+id+"' order by ordernum asc ");
	}
	
	public DecisionGroup findByDecision(String id,String ordernum){
		
		return findBy(" decisionid= '"+id+"' and ordernum ='"+ordernum+"' ");
	}
	
	public DecisionGroup findFirstByDecision(String id){
		
		return findBy(" decisionid= '"+id+"' and rownum <= 1 order by ordernum asc");
	}
	
	public List<DecisionGroup> findAllByDecision(String id){
		
		return findAllBy(" decisionid= '"+id+"' order by ordernum asc");
	}
	
	public void deleteAllByDecisionId(String id)
	{
		SingletonDAO.getInstance();
		List<DecisionGroup> list = SingletonDAO.getDecisionGroupDAOInstance().findAllByDecision(id);
		for(DecisionGroup group : list)
		{
			SingletonDAO.getDecisionChanceDAOInstance().delete(" decisiongroupid = '"+group.getId()+"' ");
			SingletonDAO.getDecisionParametersDAOInstance().delete(" decisiongroupid = '"+group.getId()+"' ");	
		}
		
		delete(" decisionid = '"+id+"' ");
	}

	@Override
	public String getClassName() {
		return DecisionGroup.class.getSimpleName();
	}

}
