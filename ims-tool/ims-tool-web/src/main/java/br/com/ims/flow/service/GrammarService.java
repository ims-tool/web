package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.GrammarEntity;
import br.com.ims.flow.model.PromptCollectEntity;

public class GrammarService extends AbstractEntityService<GrammarEntity>{
	
	public List<GrammarEntity> getAll() {
		
		return DAOFactory.getInstance().getGrammarDAO().getAll();
	}
		
	public GrammarEntity get(String id) {
		
		return DAOFactory.getInstance().getGrammarDAO().get(id);
	}
	
	public GrammarEntity getByName(String name) {
		return DAOFactory.getInstance().getGrammarDAO().getByName(name);
	}
	public void save(GrammarEntity entity) {
		DAOFactory.getInstance().getGrammarDAO().save(entity);		
	}


	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll();
		for(FormEntity form :  forms) {
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_PROMPT_COLLECT)) {
				
				if(((PromptCollectEntity)form.getFormId()).getGrammar().getId().equals(id)) {
					return true;
				} 
			}
		}
		return false;
	}

	@Override
	public void update(GrammarEntity entity) {
		// TODO Auto-generated method stub
		DAOFactory.getInstance().getGrammarDAO().update(entity);
		
	}


	@Override
	public void delete(GrammarEntity entity) {
		// TODO Auto-generated method stub
		DAOFactory.getInstance().getGrammarDAO().delete(entity);
		
	}

	
}
