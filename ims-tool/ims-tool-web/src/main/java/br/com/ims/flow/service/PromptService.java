package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AbstractEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.PromptAudioEntity;
import br.com.ims.flow.model.PromptEntity;

public class PromptService extends AbstractEntityService<PromptEntity>{
	
	public List<PromptEntity> getAll() {
		
		return DAOFactory.getInstance().getPromptDAO().getAll();
	}
	
	public PromptEntity get(String id) {
		
		return DAOFactory.getInstance().getPromptDAO().get(id);
	}
	
	public boolean save(PromptEntity prompt) {
		return DAOFactory.getInstance().getPromptDAO().save(prompt);
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
	public List<String[]> getUsed(String id) {
		List<String[]> result = new ArrayList<String[]>();
		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll();
		for(FormEntity form :  forms) {
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_ANNOUNCE) ||
			   form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_PROMPT_COLLECT)) {
				if(((AbstractEntity)form.getFormId()).getId().equals(id) ) {
					String [] dependence = {form.getFormType().getName(),form.getName()};
					result.add(dependence);
				}
			}
		}
		
		return result;
	}

	@Override
	public boolean update(PromptEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getPromptDAO().update(object);
	}

	@Override
	public boolean delete(PromptEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getPromptDAO().delete(object);
	}

}
