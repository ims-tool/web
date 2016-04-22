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
		if(id == null || id.length() ==0) {
			return false;
		}
		List<PromptAudioEntity> promptaudios = DAOFactory.getInstance().getPromptDAO().getPromptAudio("WHERE pa.audio ='" + id + "' ",true);
	    if (promptaudios.size() > 0) {
	      return true;
	    }
	    return false;
	}
	public List<String[]> getUsed(String id) {
		List<String[]> result = new ArrayList<String[]>();
		if(id == null || id.length() == 0) {
			return result;
		}
		List<String> promptIds = new ArrayList();
	    List<PromptAudioEntity> promptaudios = DAOFactory.getInstance().getPromptDAO().getPromptAudio("WHERE pa.audio ='" + id + "' ",true);
	    if (promptaudios.size() > 0) {
	      for (PromptAudioEntity pa : promptaudios) {
	        if (!promptIds.contains(pa.getPromptId()))
	        {
	          promptIds.add(pa.getPromptId());
	          PromptEntity prompt = DAOFactory.getInstance().getPromptDAO().get(pa.getPromptId(), true);
	          if (prompt != null)
	          {
	            String[] dependence = { "Prompt", prompt.getName() };
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
