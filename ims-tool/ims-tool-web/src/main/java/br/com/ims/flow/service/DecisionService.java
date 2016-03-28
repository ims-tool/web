package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.DecisionChanceEntity;
import br.com.ims.flow.model.DecisionEntity;

public class DecisionService extends AbstractEntityService<DecisionEntity>{
	
	public List<DecisionEntity> getAll() {
		
		return DAOFactory.getInstance().getDecisionDAO().getAll();
	}
	
	public DecisionEntity get(String id) {
		
		return DAOFactory.getInstance().getDecisionDAO().get(id);
	}
	public DecisionChanceEntity getChance(String id) {
		return DAOFactory.getInstance().getDecisionDAO().getChance(id);
	}
	
	public boolean save(DecisionEntity entity) {
		return DAOFactory.getInstance().getDecisionDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean update(DecisionEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getDecisionDAO().update(entity);
		
	}

	@Override
	public boolean delete(DecisionEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getDecisionDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
