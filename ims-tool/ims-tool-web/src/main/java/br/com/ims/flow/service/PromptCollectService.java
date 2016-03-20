package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.PromptCollectEntity;

public class PromptCollectService extends AbstractEntityService <PromptCollectEntity>{
	
	public List<PromptCollectEntity> getAll() {
		
		return DAOFactory.getInstance().getPromptCollectDAO().getAll();
	}
	
	public PromptCollectEntity get(String id) {
		
		return DAOFactory.getInstance().getPromptCollectDAO().get(id);
	}
	
	public boolean save(PromptCollectEntity entity) {
		return DAOFactory.getInstance().getPromptCollectDAO().save(entity);
	}
	
	public boolean isUsed(String id) {
		return true;
	}
	public List<String[]> getUsed(String id) {
		
		return null;
	}

	@Override
	public boolean update(PromptCollectEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getPromptCollectDAO().update(entity);
		
	}

	@Override
	public boolean delete(PromptCollectEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getPromptCollectDAO().delete(entity);
		
	}

}
