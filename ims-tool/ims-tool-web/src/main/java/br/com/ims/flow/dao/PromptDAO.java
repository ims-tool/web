package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.PromptEntity;

public class PromptDAO extends AbstractDAO<PromptEntity> {
	private List<PromptEntity> listPrompts =  null;
	private static PromptDAO instance = null;
	private PromptDAO() {
		listPrompts = new ArrayList<PromptEntity>(); 			
	}
	
	public static PromptDAO getInstance() {
		if(instance == null) {
			instance = new PromptDAO();
		}
		return instance;
	}
	
	public List<PromptEntity> getAll() {
		
		return this.listPrompts;
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
	public PromptEntity get(String id) {
		for(PromptEntity prompt : this.listPrompts) {
			if(prompt.getId().equals(id)) {
				return prompt;
			}
		}
		return null;
	}
	public void save(PromptEntity prompt) {
		this.listPrompts.add(prompt);
	}

	@Override
	public void update(PromptEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(PromptEntity entity) {
		// TODO Auto-generated method stub
		
	}

}
