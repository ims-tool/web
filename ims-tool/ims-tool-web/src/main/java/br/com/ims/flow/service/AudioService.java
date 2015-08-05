package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AudioEntity;
import br.com.ims.flow.model.PromptAudioEntity;
import br.com.ims.flow.model.PromptEntity;

public class AudioService extends AbstractEntityService <AudioEntity>{
	
	public List<AudioEntity> getAll() {
		
		return DAOFactory.getInstance().getAudioDAO().getAll();
	}
	
	public AudioEntity get(String id) {
		
		return DAOFactory.getInstance().getAudioDAO().get(id);
	}
	
	public void save(AudioEntity audio) {
		DAOFactory.getInstance().getAudioDAO().save(audio);
	}
	
	public boolean isUsed(String id) {
		List<PromptEntity> prompts = DAOFactory.getInstance().getPromptDAO().getAll();
		for(PromptEntity prompt :  prompts) { 
			if(prompt.getAudios() != null && prompt.getAudios().size() > 0) {
				for(PromptAudioEntity promptAudio : prompt.getAudios()) {
					if(promptAudio.getAudio().getId().equals(id)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void update(AudioEntity object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(AudioEntity object) {
		// TODO Auto-generated method stub
		
	}

}
