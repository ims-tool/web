package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.ControlPanelEntity;

@SuppressWarnings("serial")
public class ControlPanelService extends AbstractEntityService<ControlPanelEntity>{
	
	public List<ControlPanelEntity> getAll() {
		
		return DAOFactory.getInstance().getControlPanelDAO().getAll();
	}
	
	public ControlPanelEntity getByMethod(String name) {
		
		return DAOFactory.getInstance().getControlPanelDAO().getByMethod(name);
	}
	
	public ControlPanelEntity get(String id) {
		
		return DAOFactory.getInstance().getControlPanelDAO().get(id);
	}
	
	public boolean save(ControlPanelEntity entity) {
		return DAOFactory.getInstance().getControlPanelDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		return false;
	}

	@Override
	public boolean update(ControlPanelEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getControlPanelDAO().update(entity);
		
	}

	@Override
	public boolean delete(ControlPanelEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getControlPanelDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
