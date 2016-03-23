package br.com.ims.flow.service;

import java.util.ArrayList;
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
	public ConditionMapEntity getByName(String name) {
		
		return DAOFactory.getInstance().getConditionMapDAO().getByName(name);
	}
	public ConditionMapEntity getByMethodReference(String method) {
		
		return DAOFactory.getInstance().getConditionMapDAO().getByMethodReference(method);
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
		return DAOFactory.getInstance().getConditionMapDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		
		
		List<String[]> result = new ArrayList<String[]>();
		
		List<ConditionEntity> conditions = ServicesFactory.getInstance().getConditionService().getAll();
		
		for(ConditionEntity condition :  conditions) {
			List<ConditionGroupEntity> groups = condition.getListConditionGroup();
			
			boolean found = false;
			for(int index = 0; index < groups.size() && !found; index++) {
			
				ConditionGroupEntity group = groups.get(index);
				if(group.getConditionMap().getId().equals(id)) {
					found = true;
					String [] dependence = {"Condition",condition.getName()};
					result.add(dependence);
				}
			}
			
		}
		
		
		return result;
	}

}
