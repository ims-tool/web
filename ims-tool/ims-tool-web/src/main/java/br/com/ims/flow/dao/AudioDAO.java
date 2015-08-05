package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.AudioEntity;

public class AudioDAO extends AbstractDAO<AudioEntity>{
	private List<AudioEntity> listAudios =  null;
	private static AudioDAO instance = null;
	private AudioDAO() {
		listAudios = new ArrayList<AudioEntity>(); 			
	}
	
	public static AudioDAO getInstance() {
		if(instance == null) {
			instance = new AudioDAO();
		}
		return instance;
	}
	
	public List<AudioEntity> getAll() {
		
		return this.listAudios;
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
	public AudioEntity get(String id) {
		for(AudioEntity audio : this.listAudios) {
			if(audio.getId().equals(id)) {
				return audio;
			}
		}
		return null;
	}
	public void save(AudioEntity audio) {
		this.listAudios.add(audio);
	}

	@Override
	public void update(AudioEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(AudioEntity entity) {
		// TODO Auto-generated method stub
		
	}

}
