package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.TransferEntity;

public class TransferService extends AbstractEntityService <TransferEntity>{
	
	public List<TransferEntity> getAll() {
		
		return DAOFactory.getInstance().getTransferDAO().getAll();
	}
	
	public TransferEntity get(String id) {
		
		return DAOFactory.getInstance().getTransferDAO().get(id);
	}
	
	public boolean save(TransferEntity entity) {
		return DAOFactory.getInstance().getTransferDAO().save(entity);
	}
	
	public boolean isUsed(String id) {
		return true;
	}
	public List<String[]> getUsed(String id) {
		
		return null;
	}

	@Override
	public boolean update(TransferEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getTransferDAO().update(entity);
		
	}

	@Override
	public boolean delete(TransferEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getTransferDAO().delete(entity);
		
	}

}
