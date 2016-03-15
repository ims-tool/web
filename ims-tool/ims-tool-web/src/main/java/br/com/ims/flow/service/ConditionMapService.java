package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.ConditionGroupEntity;
import br.com.ims.flow.model.ConditionMapEntity;

public class ConditionMapService extends AbstractEntityService<ConditionMapEntity>{
	
	public List<ConditionMapEntity> getAll() {
		
		return DAOFactory.getInstance().getConditionMapDAO().getAll();
	}
	
	public ConditionMapEntity get(String id) {
		
		return DAOFactory.getInstance().getConditionMapDAO().get(id);
	}
	
	public boolean save(ConditionMapEntity entity) {
		return DAOFactory.getInstance().getConditionMapDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		
		List<ConditionEntity> conditions = ServicesFactory.getInstance().getConditionService().getAll();
		
		for(ConditionEntity condition :  conditions) {
			List<ConditionGroupEntity> groups = condition.getListConditionGroup();
			for(ConditionGroupEntity group : groups) {
				if(group.getConditionMap().getId().equals(id)) {
					return true;
				}
			}
			
		}
		return false;
	}

	@Override
	public boolean update(ConditionMapEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getConditionMapDAO().update(entity);
	}

	@Override
	public boolean delete(ConditionMapEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getConditionMapDAO().update(entity);
		
	}

}
