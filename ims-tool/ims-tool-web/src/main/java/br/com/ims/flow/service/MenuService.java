package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.MenuEntity;

@SuppressWarnings("serial")
public class MenuService extends AbstractEntityService <MenuEntity>{
	
	public List<MenuEntity> getAll() {
		
		return DAOFactory.getInstance().getMenuDAO().getAll();
	}
	
	public MenuEntity get(String id) {
		
		return DAOFactory.getInstance().getMenuDAO().get(id);
	}
	public ChoiceEntity getChoice(String id) {
		return DAOFactory.getInstance().getMenuDAO().getChoice(id);
	}
	
	public boolean save(MenuEntity entity) {
		return DAOFactory.getInstance().getMenuDAO().save(entity);
	}
	
	public boolean isUsed(String id) {
		return true;
	}
	public List<String[]> getUsed(String id) {
		
		return null;
	}

	@Override
	public boolean update(MenuEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getMenuDAO().update(entity);
		
	}

	@Override
	public boolean delete(MenuEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getMenuDAO().delete(entity);
		
	}

}
