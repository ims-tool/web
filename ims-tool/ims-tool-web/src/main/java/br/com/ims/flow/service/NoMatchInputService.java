package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptCollectEntity;

@SuppressWarnings("serial")
public class NoMatchInputService extends AbstractEntityService<NoMatchInputEntity>{
	
	public List<NoMatchInputEntity> getAll() {
		
		return DAOFactory.getInstance().getNoMatchInputDAO().getAll();
	}
	public List<NoMatchInputEntity> getAll(boolean lazy) {
		
		return DAOFactory.getInstance().getNoMatchInputDAO().getAll(lazy);
	}
	
	public List<NoMatchInputEntity> getNoMatchAll() {		
	
		List<NoMatchInputEntity> noMatchAll = new ArrayList<NoMatchInputEntity>();
		for(NoMatchInputEntity entity : DAOFactory.getInstance().getNoMatchInputDAO().getAll()) {
			if(entity.getType().equalsIgnoreCase("NOMATCH")) {
				noMatchAll.add(entity);
			}
		}
		return noMatchAll;		
		
	}
	public List<NoMatchInputEntity> getNoInputAll() {		
		
		List<NoMatchInputEntity> noInputAll = new ArrayList<NoMatchInputEntity>();
		for(NoMatchInputEntity entity : DAOFactory.getInstance().getNoMatchInputDAO().getAll()) {
			if(entity.getType().equalsIgnoreCase("NOINPUT")) {
				noInputAll.add(entity);
			}
		}
		return noInputAll;		
		
	}
	
	public NoMatchInputEntity get(String id) {
		
		return DAOFactory.getInstance().getNoMatchInputDAO().get(id);
	}
	public NoMatchInputEntity get(String id,boolean lazy) {
		
		return DAOFactory.getInstance().getNoMatchInputDAO().get(id,lazy);
	}
	public NoMatchInputEntity getByName(String name) {
		
		return DAOFactory.getInstance().getNoMatchInputDAO().getByName(name);
	}
	
	public boolean save(NoMatchInputEntity entity) {
		return DAOFactory.getInstance().getNoMatchInputDAO().save(entity);		
	}


	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<MenuEntity> menus = DAOFactory.getInstance().getMenuDAO().getAll(true);
		for(MenuEntity menu :  menus) {
			if(menu.getNoInput() != null && menu.getNoInput().getId().equals(id) ||
					menu.getNoMatch() != null && menu.getNoMatch().getId().equals(id)) {
				return true;
			}
			
		}
		List<PromptCollectEntity> promptCollects = DAOFactory.getInstance().getPromptCollectDAO().getAll(true);
		for(PromptCollectEntity promptCollect :  promptCollects) {
			if(promptCollect.getNoInput() !=null && promptCollect.getNoInput().getId().equals(id) ||
					promptCollect.getNoMatch() !=null && promptCollect.getNoMatch().getId().equals(id)) {
				return true;
			}
			
		}

		return false;
	}

	@Override
	public boolean update(NoMatchInputEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getNoMatchInputDAO().update(entity);
		
	}

	@Override
	public boolean delete(NoMatchInputEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getNoMatchInputDAO().delete(entity);
	}


	@Override
	public List<String[]> getUsed(String id) {
		List<String[]> result = new ArrayList<String[]>();
		List<MenuEntity> menus = DAOFactory.getInstance().getMenuDAO().getAll(true);
		for(MenuEntity menu :  menus) {
			if(menu.getNoInput() != null && menu.getNoInput().getId().equals(id) ||
					menu.getNoMatch() != null && menu.getNoMatch().getId().equals(id)) {
				String[] obj = {"Menu",menu.getName()};
				result.add(obj);
				
			}
			
		}
		List<PromptCollectEntity> promptCollects = DAOFactory.getInstance().getPromptCollectDAO().getAll(true);
		for(PromptCollectEntity promptCollect :  promptCollects) {
			if(promptCollect.getNoInput() !=null && promptCollect.getNoInput().getId().equals(id) ||
					promptCollect.getNoMatch() !=null && promptCollect.getNoMatch().getId().equals(id)) {
				String[] obj = {"PromptCollect",promptCollect.getName()};
				result.add(obj);
			}
			
		}


		return result;
	}

}
	