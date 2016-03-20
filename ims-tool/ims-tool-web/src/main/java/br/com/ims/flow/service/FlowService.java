package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.FlowEntity;

public class FlowService extends AbstractEntityService <FlowEntity>{
	
	public List<FlowEntity> getAll() {
		
		return DAOFactory.getInstance().getFlowDAO().getAll();
	}
	
	public FlowEntity get(String id) {
		
		return DAOFactory.getInstance().getFlowDAO().get(id);
	}
	
	public boolean save(FlowEntity answer) {
		return DAOFactory.getInstance().getFlowDAO().save(answer);
	}
	
	public boolean isUsed(String id) {
		return true;
	}
	public List<String[]> getUsed(String id) {
		
		return null;
	}

	@Override
	public boolean update(FlowEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getFlowDAO().update(entity);
		
	}

	@Override
	public boolean delete(FlowEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getFlowDAO().delete(entity);
		
	}

}
