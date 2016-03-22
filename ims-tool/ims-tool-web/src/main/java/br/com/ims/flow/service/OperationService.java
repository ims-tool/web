package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.OperationEntity;

public class OperationService extends AbstractEntityService<OperationEntity>{
	
	public List<OperationEntity> getAll() {
		
		return DAOFactory.getInstance().getOperationDAO().getAll();
	}
	
	
	public OperationEntity get(String id) {
		
		return DAOFactory.getInstance().getOperationDAO().get(id);
	}
	
	public boolean save(OperationEntity entity) {
		return DAOFactory.getInstance().getOperationDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		
		return true;
	}

	@Override
	public boolean update(OperationEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getOperationDAO().update(entity);
		
	}

	@Override
	public boolean delete(OperationEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getOperationDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		
		return null;
	}

}
