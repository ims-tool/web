package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.OperationMapEntity;

public class OperationMapDAO extends AbstractDAO<OperationMapEntity> {
	private List<OperationMapEntity> listOperationMaps =  null;
	private static OperationMapDAO instance = null;
	private OperationMapDAO() {
		listOperationMaps = new ArrayList<OperationMapEntity>(); 			
	}
	
	public static OperationMapDAO getInstance() {
		if(instance == null) {
			instance = new OperationMapDAO();
		}
		return instance;
	}
	
	public List<OperationMapEntity> getAll() {
		
		return this.listOperationMaps;
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
	public OperationMapEntity get(String id) {
		for(OperationMapEntity conditionMap : this.listOperationMaps) {
			if(conditionMap.getId().equals(id)) {
				return conditionMap;
			}
		}
		return null;
	}
	
	public void save(OperationMapEntity entity) {
		this.listOperationMaps.add(entity);
	}

	@Override
	public void update(OperationMapEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(OperationMapEntity entity) {
		// TODO Auto-generated method stub
		
	}

}
