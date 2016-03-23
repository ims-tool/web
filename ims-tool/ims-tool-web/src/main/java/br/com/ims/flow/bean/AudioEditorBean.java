package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.Util;
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
    	this.audio.setId(Util.getUID());
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
	private boolean validateFields() {
		if(this.audio.getName() == null || this.audio.getName().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Please,inform the Name!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.audio.getDescription() == null || this.audio.getDescription().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Please,inform the Description!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.audio.getPath() == null || this.audio.getPath().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Please,inform the Path!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		AudioEntity tmp = ServicesFactory.getInstance().getAudioService().getByName(this.audio.getName()) ;
		
		if(tmp != null && 
				tmp.getName().equalsIgnoreCase(this.audio.getName()) &&
				!tmp.getId().equals(audio.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Audio with name '"+this.audio.getName()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion() == null) {
			ServicesFactory.getInstance().getIvrEditorService().getBean().requestVersion(true);
			return false;
		}
		this.audio.setVersionId(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion());
		return true;
	}
	public void newAudio(ActionEvent event) {
		this.audio = new AudioEntity();
		this.audio.setId(Util.getUID());
    	this.insert = true;
	}

	public void save(ActionEvent event) {
		
		
		if(validateFields()) {	
			
			if(ServicesFactory.getInstance().getAudioService().save(this.audio)) {
			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Audio",this.audio.getName()+" - Saved!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init();
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prompt","Error on Save "+this.audio.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
    }
	public void typeChange() {
		System.out.println(audio.getType());
		/**
		 * fazer o esquema de reconhecer variavel
		 * 
		 * Tipos: paramname | paramvalue
		 * currencytype | reaiscentavos
		 * datefmt | wdmy
		 * datefmt | dmy
		 * datefmt | dm
	     * formatname | currency
	     * formatname | tts
		 * formatname | number
	     * formatname | time
		 * formatname | digits-500ms
	     * formatname | date
		 * gender | masculine
	     * inflection | falling
		 * timetype | 24
 | 

		 */
	}

	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		if(validateFields()) {	
			
			if(ServicesFactory.getInstance().getAudioService().update(this.audio)) {
			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Audio",this.audio.getName()+" - Updated!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init();
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prompt","Error on Update "+this.audio.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	
	@Override
	protected void updateExternalsBean() {
		// TODO Auto-generated method stub
		if(this.promptBean != null) {
			this.promptBean.setAudioId(this.audio.getId());
		}
		
	}

	@Override
	public void edit(String id) {
		this.audio = ServicesFactory.getInstance().getAudioService().get(id);
		
		this.insert= false;
		
		
	}
	
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.audio = ServicesFactory.getInstance().getAudioService().get(id);
		if(ServicesFactory.getInstance().getAudioService().delete(this.audio)) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Audio",this.audio.getName()+" - Deleted!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			updateExternalsBean();
			
			init();
			
			RequestContext context = RequestContext.getCurrentInstance();
			boolean saved = true;
			context.addCallbackParam("saved", saved);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Audio","Error on Delete "+this.audio.getName()+", please contact your support.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void viewDependence(String id, String name) {
		ServicesFactory.getInstance().getDependenceEditorService().getBean().setObject(Constants.DEPENDENCE_OBJECT_TYPE_AUDIO,id, name);
	}
	
	
    
}