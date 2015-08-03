package br.com.ims.flow.service;

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
	
	public void save(PromptEntity prompt) {
		DAOFactory.getInstance().getPromptDAO().save(prompt);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll();
		for(FormEntity form :  forms) {
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_ANNOUNCE) ||
			   form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_PROMPT_COLLECT)) {
				if(((AbstractEntity)form.getObject()).getId().equals(id) ) {
					return true;
				}
			}
		}
		return false;
	}

}
