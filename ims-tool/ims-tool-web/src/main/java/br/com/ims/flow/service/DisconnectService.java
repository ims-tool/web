package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.DisconnectEntity;

@SuppressWarnings("serial")
public class DisconnectService extends AbstractEntityService <DisconnectEntity>{
	
	public List<DisconnectEntity> getAll() {
		
		return DAOFactory.getInstance().getDisconnectDAO().getAll();
	}
	
	public DisconnectEntity get(String id) {
		
		return DAOFactory.getInstance().getDisconnectDAO().get(id);
	}
	
	public boolean save(DisconnectEntity disconnect) {
		return DAOFactory.getInstance().getDisconnectDAO().save(disconnect);
	}
	
	public boolean isUsed(String id) {
		return true;
	}
	public List<String[]> getUsed(String id) {
		
		return null;
	}

	@Override
	public boolean update(DisconnectEntity disconnect) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getDisconnectDAO().update(disconnect);
		
	}

	@Override
	public boolean delete(DisconnectEntity disconnect) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getDisconnectDAO().delete(disconnect);
		
	}

}
