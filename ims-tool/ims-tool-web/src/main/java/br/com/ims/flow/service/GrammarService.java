package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.GrammarEntity;
import br.com.ims.flow.model.PromptCollectEntity;

@SuppressWarnings("serial")
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
	public boolean save(GrammarEntity entity) {
		return DAOFactory.getInstance().getGrammarDAO().save(entity);		
	}


	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<PromptCollectEntity> promptsCollect = DAOFactory.getInstance().getPromptCollectDAO().getAll(true);
		for(PromptCollectEntity promptCollect :  promptsCollect) {
			if(promptCollect.getGrammar() != null && promptCollect.getGrammar().getId().equals(id)) {
				return true;
			} 
			
		}
		
		return false;
	}

	@Override
	public boolean update(GrammarEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getGrammarDAO().update(entity);
		
	}


	@Override
	public boolean delete(GrammarEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getGrammarDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		// TODO Auto-generated method stub
		List<String[]> result = new ArrayList<String[]>();
		List<PromptCollectEntity> promptsCollect = DAOFactory.getInstance().getPromptCollectDAO().getAll(true);
		for(PromptCollectEntity promptCollect :  promptsCollect) {
			if(promptCollect.getGrammar() != null && promptCollect.getGrammar().getId().equals(id)) {
				String [] obj = {"PromptCollect",promptCollect.getName()};
				
				result.add(obj);
			} 
			
		}
		return result;
	}

	
}
