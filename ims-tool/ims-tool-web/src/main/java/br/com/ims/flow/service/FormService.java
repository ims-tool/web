package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AbstractFormEntity;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.DecisionChanceEntity;
import br.com.ims.flow.model.DecisionEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.PromptCollectEntity;

@SuppressWarnings("serial")
public class FormService extends AbstractEntityService<FormEntity> {

	@Override
	public List<FormEntity> getAll() {
		return DAOFactory.getInstance().getFormDAO().getAll();
	}
	public List<FormEntity> getAll(boolean lazy) {
		return DAOFactory.getInstance().getFormDAO().getAll(lazy);
	}
	public List<FormEntity> getByFormTypeName(String formTypeName) {
		return DAOFactory.getInstance().getFormDAO().getByTypeName(formTypeName);
	}
	public Object getObject(FormEntity form) {
	    if (form.getFormId() == null) {
	      return null;
	    }
	    return DAOFactory.getInstance().getFormDAO().getObject(form.getFormType(), ((AbstractFormEntity)form.getFormId()).getId());
	}

	@Override
	public FormEntity get(String id) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getFormDAO().get(id);
	}
	public FormEntity getByName(String name,boolean lazy) {

		return DAOFactory.getInstance().getFormDAO().getByName(name, lazy);
	}
	public FormEntity get(String id,boolean lazy) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getFormDAO().get(id,lazy);
	}
	
	

	@Override
	public boolean save(FormEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getFormDAO().save(object);
		
	}
	public boolean saveObj(FormEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getFormDAO().saveObj(object);
		
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		List<FormEntity> list = DAOFactory.getInstance().getFormDAO().getAll();
		for(FormEntity form : list) {
			
			AbstractFormEntity abs = (AbstractFormEntity)form.getFormId();
			
			if(abs.getNextForm() != null &&
					abs.getNextForm().equals(id)) {
				return true;
			}
			if(form.getFormType().getName().equals(Constants.FORM_TYPE_MENU)) {
				MenuEntity menu = (MenuEntity)form.getFormId();
				if(menu.getChoices() != null) {
					for(ChoiceEntity choice : menu.getChoices()) {
						if(choice.getNextForm() != null && choice.getNextForm().equals(id)) {
							return true;
						}
					}
				}
				if(menu.getNoInput() != null &&
						menu.getNoInput().getNextForm() !=null && 
						menu.getNoInput().getNextForm().equals(id)) {
					return true;
				}
				if(menu.getNoMatch() != null && 
						menu.getNoMatch().getNextForm() != null && 
						menu.getNoMatch().getNextForm().equals(id)){
					return true;
				}
			}
			if(form.getFormType().getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT)) {
				PromptCollectEntity pc = (PromptCollectEntity)form.getFormId();
				if(pc.getNoInput() != null &&
						pc.getNoInput().getNextForm() !=null &&
						pc.getNoInput().getNextForm().equals(id)) {
					return true;
				}
				if(pc.getNoMatch() !=null &&
						pc.getNoMatch().getNextForm() != null &&
						pc.getNoMatch().getNextForm().equals(id)) {
					return true;
					
				}
				
			}
			if(form.getFormType().getName().equals(Constants.FORM_TYPE_DECISION)) {
				DecisionEntity decision = (DecisionEntity)form.getFormId();
				if(decision.getListDecisionChance() != null) {
					
					for(DecisionChanceEntity chance : decision.getListDecisionChance()) {
						if(chance.getNextForm() != null &&
								chance.getNextForm().equals(id)) {
							return true;							
						}
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean update(FormEntity object) {
		
		return DAOFactory.getInstance().getFormDAO().update(object);
		
	}
	public boolean updateObj(FormEntity object) {
		
		return DAOFactory.getInstance().getFormDAO().updateObj(object);
		
	}
	public boolean deleteObj(FormEntity object) {
		
		return DAOFactory.getInstance().getFormDAO().deleteObj(object);
		
	}

	@Override
	public boolean delete(FormEntity object) {
		return DAOFactory.getInstance().getFormDAO().delete(object);
		
		
	}
	@Override
	public List<String[]> getUsed(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<FormEntity> getByFilter(String where, boolean lazy) {
		return DAOFactory.getInstance().getFormDAO().getByFilter(where, lazy);
	}
	

}
