package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AudioEntity;
import br.com.ims.flow.model.PromptAudioEntity;
import br.com.ims.flow.model.PromptEntity;

@SuppressWarnings("serial")
public class AudioService extends AbstractEntityService <AudioEntity>{
	
	public List<AudioEntity> getAll() {
		
		return DAOFactory.getInstance().getAudioDAO().getAll();
	}
	
	public AudioEntity get(String id) {
		
		return DAOFactory.getInstance().getAudioDAO().get(id);
	}
	public AudioEntity getByName(String name) {
		
		return DAOFactory.getInstance().getAudioDAO().getByName(name);
	}
	
	public boolean save(AudioEntity audio) {
		return DAOFactory.getInstance().getAudioDAO().save(audio);
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
	public List<String[]> getUsed(String id) {
		List<String[]> result = new ArrayList<String[]>();
		List<PromptEntity> prompts = DAOFactory.getInstance().getPromptDAO().getAll();
		for(PromptEntity prompt :  prompts) { 
			if(prompt.getAudios() != null && prompt.getAudios().size() > 0) {
				boolean found = false;
				for(int index = 0; index < prompt.getAudios().size() && !found; index++) {
					PromptAudioEntity promptAudio = prompt.getAudios().get(index);
					if(promptAudio.getAudio().getId().equals(id)) {
						found = true;
						String [] dependence = {"Prompt",prompt.getName()};
						result.add(dependence);
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean update(AudioEntity audio) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getAudioDAO().update(audio);
		
	}

	@Override
	public boolean delete(AudioEntity audio) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getAudioDAO().delete(audio);
		
	}

}
