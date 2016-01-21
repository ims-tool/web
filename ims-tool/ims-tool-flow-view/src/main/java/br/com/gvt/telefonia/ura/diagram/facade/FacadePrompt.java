package br.com.gvt.telefonia.ura.diagram.facade;

import br.com.gvt.telefonia.ura.diagram.model.Prompt;
import br.com.gvt.telefonia.ura.diagram.model.PromptAudio;
import br.com.gvt.telefonia.ura.util.SingletonDAO;


public class FacadePrompt {

	private String id;
	private String name;
	private String[] audio;
	private String versionid;
	
	public String getVersionid() {
		return versionid;
	}

	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getAudio() {
		return audio;
	}

	public void setAudio(String[] audio) {
		this.audio = audio;
	}
	
	public String save()
	{
		String promptId = "";
		Prompt prompt = null;
		PromptAudio promptAudio = null;
		
		if(id != null){
			prompt = SingletonDAO.getPromptDAOInstance().findByPk(id);
			if(prompt != null)
				SingletonDAO.getPromptAudioDAOInstance().delete("prompt = '"+id+"'");
		} else
			prompt = new Prompt();
		
		prompt.setName(name);
		prompt.setVersionid(versionid);
		
		promptId = SingletonDAO.getPromptDAOInstance().save(prompt);
		
		//boolean delete = SingletonDAO.getPromptAudioDAOInstance().delete("prompt = '"+promptId+"'");
		/*
		if(delete)
		{*/
			String[] audioElement;
			for( int x = 0; x < audio.length; x++ )
			{
				audioElement = audio[x].split(":");
				promptAudio = new PromptAudio();
				promptAudio.setPrompt(Long.parseLong(promptId));
				promptAudio.setOrdernum(x);
				promptAudio.setAudio(Long.parseLong(audioElement[0]));
				if(audioElement.length > 1)
				promptAudio.setCondition((audioElement[1]));
				promptAudio.setVersionid(versionid);
				SingletonDAO.getPromptAudioDAOInstance().insert(promptAudio);
			}
		/*}*/
			
		return promptId;
	}
}
