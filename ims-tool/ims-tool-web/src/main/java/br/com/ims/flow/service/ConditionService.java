package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AbstractEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.FormEntity;

public class ConditionService extends AbstractEntityService<ConditionEntity>{
	
	public List<ConditionEntity> getAll() {
		
		return DAOFactory.getInstance().getConditionDAO().getAll();
	}
	
	public ConditionEntity getByName(String name) {
		
		return DAOFactory.getInstance().getConditionDAO().getByName(name);
	}
	
	public ConditionEntity get(String id) {
		
		return DAOFactory.getInstance().getConditionDAO().get(id);
	}
	
	public void save(ConditionEntity entity) {
		DAOFactory.getInstance().getConditionDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll();
		for(FormEntity form :  forms) {
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_ANNOUNCE) ||
			   form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_PROMPT_COLLECT)) {
				if(((AbstractEntity)form.getFormId()).getId().equals(id) ) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void update(ConditionEntity object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ConditionEntity object) {
		// TODO Auto-generated method stub
		
	}

}
