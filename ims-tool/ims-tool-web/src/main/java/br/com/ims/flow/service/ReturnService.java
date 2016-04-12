package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.ReturnEntity;

@SuppressWarnings("serial")
public class ReturnService extends AbstractEntityService <ReturnEntity>{
	
	public List<ReturnEntity> getAll() {
		
		return DAOFactory.getInstance().getReturnDAO().getAll();
	}
	
	public ReturnEntity get(String id) {
		
		return DAOFactory.getInstance().getReturnDAO().get(id);
	}
	
	public boolean save(ReturnEntity _return) {
		return DAOFactory.getInstance().getReturnDAO().save(_return);
	}
	
	public boolean isUsed(String id) {
		return true;
	}
	public List<String[]> getUsed(String id) {
		
		return null;
	}

	@Override
	public boolean update(ReturnEntity _return) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getReturnDAO().update(_return);
		
	}

	@Override
	public boolean delete(ReturnEntity _return) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getReturnDAO().delete(_return);
		
	}

}
