package br.com.gvt.telefonia.ura.diagram.dao;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.ConditionGroup;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class ConditionGroupDAO extends DAO<ConditionGroup> {

	@Override
	public String getClassName() {
		return ConditionGroup.class.getSimpleName();
	}
	
public List<ConditionGroup> findAllByCondition(String id){
		
		return findAllBy(" conditionid= '"+id+"' order by ordernum asc");
	}
	
	public void deleteAllByConditionId(String id)
	{
		SingletonDAO.getInstance();
		List<ConditionGroup> list = SingletonDAO.getConditionGroupDAOInstance().findAllByCondition(id);
		for(ConditionGroup group : list)
		{
			SingletonDAO.getConditionValueDAOInstance().delete(" conditiongroupid = '"+group.getId()+"' ");
			SingletonDAO.getConditionParametersDAOInstance().delete(" conditiongroupid = '"+group.getId()+"' ");	
		}
		
		delete(" conditionid = '"+id+"' ");
	}

}
