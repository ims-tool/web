package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.NoMatchInputEntity;

public class NoMatchInputService extends AbstractEntityService<NoMatchInputEntity>{
	
	public List<NoMatchInputEntity> getAll() {
		
		return DAOFactory.getInstance().getNoMatchInputDAO().getAll();
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
	
	public void save(NoMatchInputEntity entity) {
		DAOFactory.getInstance().getNoMatchInputDAO().save(entity);		
	}


	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll();
		for(FormEntity form :  forms) {
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_MENU)) {
				
				if(((MenuEntity)form.getFormId()).getNoInput().getId().equals(id) ||
					((MenuEntity)form.getFormId()).getNoMatch().getId().equals(id)) {
					return true;
				} 
			}
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_PROMPT_COLLECT)) {
				
				if(((MenuEntity)form.getFormId()).getNoInput().getId().equals(id) ||
					((MenuEntity)form.getFormId()).getNoMatch().getId().equals(id)) {
					return true;
				} 
			}
		}
		return false;
	}

	@Override
	public void update(NoMatchInputEntity object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(NoMatchInputEntity object) {
		// TODO Auto-generated method stub
		
	}

}
