package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AudioEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "audioEditorView")
@ViewScoped
public class AudioEditorBean extends AbstractBean {
     
	
	
	private AudioEntity audio;
	private List<AudioEntity> audios;
	private PromptEditorBean promptBean;

	
	
    public AudioEditorBean() {
    	init();
    }
    
    public void init() {
    	this.audio = new AudioEntity();
    	this.promptBean = null;
    	this.insert = true;
    	
    }
   
   
    
	public AudioEntity getAudio() {
		return audio;
	}

	public void setAudio(AudioEntity audio) {
		this.audio = audio;
	}

	public PromptEditorBean getPromptBean() {
		return promptBean;
	}

	public void setPromptBean(PromptEditorBean promptBean) {
		this.promptBean = promptBean;
	}

	
	public List<AudioEntity> getAudios() {
		this.audios = ServicesFactory.getInstance().getAudioService().getAll();
		return audios;
	}
	
	public boolean isUsed(String id) {
		return ServicesFactory.getInstance().getAudioService().isUsed(id) ;
	}

	public void setAudios(List<AudioEntity> audios) {
		this.audios = audios;
	}

	public void save(ActionEvent event) {
		
		ServicesFactory.getInstance().getAudioService().save(this.audio);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Prompt",this.audio.getName()+" - Saved!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		if(this.promptBean != null) {
			this.promptBean.setAudioId(this.audio.getId());
		}
		
		init();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean auxiliarSaved = true;
		context.addCallbackParam("auxiliarSaved", auxiliarSaved);
		
    }

	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}   
	
	
    
}