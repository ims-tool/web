package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.ConditionGroupEntity;
import br.com.ims.flow.model.ConditionParameterEntity;
import br.com.ims.flow.model.ConditionValueEntity;

public class ConditionDAO extends AbstractDAO<ConditionEntity> {
	private List<ConditionEntity> listConditions =  null;
	private static ConditionDAO instance = null;
	private ConditionDAO() {
		listConditions = new ArrayList<ConditionEntity>(); 			
	}
	
	public static ConditionDAO getInstance() {
		if(instance == null) {
			instance = new ConditionDAO();
		}
		return instance;
	}
	
	public List<ConditionEntity> getAll() {
		
		return this.listConditions;
		/*List<PromptEntity> result = new ArrayList<PromptEntity>();
		
		PromptEntity promptEntity = new PromptEntity();
		promptEntity.setId("10");
		promptEntity.setName("PromptAnnounce - "+promptEntity.getId());
		result.add(promptEntity);
		
		promptEntity = new PromptEntity();
		promptEntity.setId("20");
		promptEntity.setName("PromptAnnounce - "+promptEntity.getId());
		result.add(promptEntity);
		

		return result;*/
	}
	public ConditionEntity get(String id) {
		for(ConditionEntity condition : this.listConditions) {
			if(condition.getId().equals(id)) {
				return condition;
			}
		}
		return null;
	}
	
	public ConditionEntity getByName(String name) {
		for(ConditionEntity condition : this.listConditions) {
			if(condition.getName().equalsIgnoreCase(name)) {
				return condition;
			}
		}
		return null;
	}
	
	public boolean save(ConditionEntity entity) {
		this.listConditions.add(entity);
		return true;
	}

	@Override
	public boolean update(ConditionEntity entity) {
		// TODO Auto-generated method stub
		return true;
		
	}

	@Override
	public boolean delete(ConditionEntity entity) {
		return true;
		// TODO Auto-generated method stub
		
	}

}
