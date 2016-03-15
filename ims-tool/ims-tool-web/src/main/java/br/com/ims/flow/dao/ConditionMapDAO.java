package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.ConditionMapEntity;

public class ConditionMapDAO extends AbstractDAO<ConditionMapEntity> {
	private List<ConditionMapEntity> listConditionMaps =  null;
	private static ConditionMapDAO instance = null;
	private ConditionMapDAO() {
		listConditionMaps = new ArrayList<ConditionMapEntity>(); 			
	}
	
	public static ConditionMapDAO getInstance() {
		if(instance == null) {
			instance = new ConditionMapDAO();
		}
		return instance;
	}
	
	public List<ConditionMapEntity> getAll() {
		
		return this.listConditionMaps;
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
	public ConditionMapEntity get(String id) {
		for(ConditionMapEntity conditionMap : this.listConditionMaps) {
			if(conditionMap.getId().equals(id)) {
				return conditionMap;
			}
		}
		return null;
	}
	
	public boolean save(ConditionMapEntity entity) {
		this.listConditionMaps.add(entity);
		return true;
	}

	@Override
	public boolean update(ConditionMapEntity entity) {
		// TODO Auto-generated method stub
		return true;
		
	}

	@Override
	public boolean delete(ConditionMapEntity entity) {
		return true;
		// TODO Auto-generated method stub
		
	}

}
