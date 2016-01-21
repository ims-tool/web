package br.com.gvt.telefonia.ura.diagram.dao;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.OperationGroup;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class OperationGroupDAO extends DAO<OperationGroup> {

	@Override
	public String getClassName() {
		return OperationGroup.class.getSimpleName();
	}
	
	public List<OperationGroup> findAllByOperation(String id){
		
		return findAllBy(" operationid= '"+id+"' order by ordernum asc");
	}

	public void deleteAllByOperationId(String id) {
		SingletonDAO.getInstance();
		List<OperationGroup> list = SingletonDAO.getOperationGroupDAOInstance().findAllByOperation(id);
		
		for(OperationGroup group : list){
			SingletonDAO.getOperationGroupDAOInstance().delete(" id = '"+group.getId()+"' ");
			SingletonDAO.getOperationParameterDAOInstance().delete(" operationgroupid = '"+group.getId()+"' ");
		}
		
		delete(" operationid = '"+id+"' ");
	}

}
