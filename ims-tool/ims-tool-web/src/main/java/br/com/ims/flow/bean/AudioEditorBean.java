package br.com.ims.flow.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AudioEntity;
import br.com.ims.flow.model.AudioVarEntity;
import br.com.ims.flow.model.VersionEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "audioEditorView")
@ViewScoped
public class AudioEditorBean extends AbstractBean {
    
	private AudioEntity audio;
	
	private List<AudioEntity> audios;
	
	private PromptEditorBean promptBean;
	
	private String formatName;
	private String formatParameter;
	private String formatValue;
	
	
	private List<String> listFormatParameter;
	private List<String> listFormatValue;

	private VersionEntity version;
	
    public AudioEditorBean() {
    	init();
    }
    
    public void init() {
    	this.audio = new AudioEntity();
    	this.audio.setId(Util.getUID());
    	this.audio.setAudioVar(new ArrayList<AudioVarEntity>());
    	
    	this.listFormatParameter = new ArrayList<String>();
    	this.listFormatValue = new ArrayList<String>();
    	this.formatName = "";
    	this.formatParameter = "";
    	this.formatValue = "";
    	
    	this.promptBean = null;
    	
    	this.insert = true;
    	
    }
   
    public void requestVersion(boolean save) {
		if(save) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "You have to assign the Version to save changes.",
	                "Audio");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		ServicesFactory.getInstance().getVersionEditorService().getBean().setAudioEditorBean(this);
		
		RequestContext context = RequestContext.getCurrentInstance();
    	context.execute("PF('settingAdminDlg').show();");
        context.update("settingAdminDlgId");
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
		if(this.audio.getType().equalsIgnoreCase("WAV") || this.audio.getType().equalsIgnoreCase("V_WAV")) {
			if(this.audio.getPath() == null || this.audio.getPath().length() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Please,inform the Path!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return false;
			}
		}
		if(this.audio.getType().equalsIgnoreCase("VAR") || this.audio.getType().equalsIgnoreCase("V_WAV")) {
			if(this.audio.getContext() == null || this.audio.getContext().length() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Please,inform the Context!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return false;
			}
		}
		if(this.audio.getType().equalsIgnoreCase("VAR")) {
			if(this.audio.getAudioVar().size() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Please, you must configure the VAR PARAMETERS!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return false;
			} else {
				if(this.getListFormatParameter().size() > 0) {
					String text = "Pleanse, configure VAR PARAMETER: "+this.getListFormatParameter().get(0);
					for(int index  = 1; index < this.getListFormatParameter().size();index++) {
						text+= ", "+this.getListFormatParameter().get(index);
					}
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio",text);
					 
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return false;
				}
			}			
		}
		AudioEntity tmp = ServicesFactory.getInstance().getAudioService().getByName(this.audio.getName()) ;
		
		if(tmp != null && 
				tmp.getName().equalsIgnoreCase(this.audio.getName()) &&
				!tmp.getId().equals(audio.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Audio with name '"+this.audio.getName()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
	
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("internalPage") == null) {
			if(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion() == null) {
				ServicesFactory.getInstance().getIvrEditorService().getBean().requestVersion(true);
				return false;
			}
			this.version = ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion();
			
		} else {
			if(this.version == null) {
				this.requestVersion(true);
				return false;
			}
		}
		this.audio.setVersionId(version.getId());
		
		return true;
	}
	public void newAudio(ActionEvent event) {
		this.audio = new AudioEntity();
		this.audio.setId(Util.getUID());
		this.audio.setAudioVar(new ArrayList<AudioVarEntity>());
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
	
	
	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public String getFormatParameter() {
		return formatParameter;
	}

	public void setFormatParameter(String formatParameter) {
		this.formatParameter = formatParameter;
	}

	public List<String> getListFormatParameter() {
		listFormatParameter = new ArrayList<String>();
		List<AudioVarEntity> list = this.audio.getAudioVar();
		if(this.formatName.equalsIgnoreCase("number")) {
			listFormatParameter.add("numbertype");
			listFormatParameter.add("gender");
			listFormatParameter.add("inflection");
		} else if(this.formatName.equalsIgnoreCase("date")) {
			listFormatParameter.add("datefmt");
			listFormatParameter.add("inflection");
		} else if(this.formatName.equalsIgnoreCase("time")) {
			listFormatParameter.add("timetype");
			listFormatParameter.add("inflection");
		} else if(this.formatName.equalsIgnoreCase("currency")) {
			listFormatParameter.add("currencytype");
			listFormatParameter.add("inflection");
		} else if(this.formatName.equalsIgnoreCase("digits")) {
			listFormatParameter.add("inflection");
		}
		for(int index = 0; index < listFormatParameter.size(); index++) {
			String parameter = listFormatParameter.get(index);
			for(AudioVarEntity var : list) {
				if(var.getFormatName().equalsIgnoreCase(this.formatName)) {
					if(var.getFormatParameter().equalsIgnoreCase(parameter)) {
						listFormatParameter.remove(index);
						index = -1;
					}
				}
			}
		}
		
		return listFormatParameter;
	}

	public void setListFormatParameter(List<String> listFormatParameter) {
		this.listFormatParameter = listFormatParameter;
	}

	public List<String> getListFormatValue() {
		listFormatValue = new ArrayList<String>();
		if(this.formatParameter.equalsIgnoreCase("numbertype")) {
			listFormatValue.add("integer");
			listFormatValue.add("decimal");
		} else if(this.formatParameter.equalsIgnoreCase("gender")) {
			listFormatValue.add("masculine");
			listFormatValue.add("feminine");	
		} else if(this.formatParameter.equalsIgnoreCase("inflection")) {
			if(this.formatName.equalsIgnoreCase("date")) {
				listFormatValue.add("neutral");
				listFormatValue.add("falling");
			} else {
				listFormatValue.add("neutral");
				listFormatValue.add("raising");
				listFormatValue.add("falling");
			}				
		} else if(this.formatParameter.equalsIgnoreCase("datefmt")) {
			listFormatValue.add("wdmy");
			listFormatValue.add("dmy");
			listFormatValue.add("dm");
		} else if(this.formatParameter.equalsIgnoreCase("timetype")) {
			listFormatValue.add("12");
			listFormatValue.add("24");
		} else if(this.formatParameter.equalsIgnoreCase("currencytype")) {
			listFormatValue.add("reais");
			listFormatValue.add("reaiscentavos");
		}
		
		return listFormatValue;
	}

	public void setListFormatValue(List<String> listFormatValue) {
		this.listFormatValue = listFormatValue;
	}

	public String getFormatValue() {
		return formatValue;
	}

	public void setFormatValue(String formatValue) {
		this.formatValue = formatValue;
	}

	public void addFormatParameter(ActionEvent event) {
		this.formatName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_formatName_input").toString();
		this.formatParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_formatParameter_input").toString();
		this.formatValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_formatValue_input").toString();
		
		
		if(this.formatName == null || this.formatName.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Audio","Please, select the Format Type!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		/*
		 * 
		 * <f:selectItem itemLabel="TTS" itemValue="tts" />
	 					<f:selectItem itemLabel="Digits-200ms" itemValue="digits-200ms" />
	 					<f:selectItem itemLabel="Digits-500ms" itemValue="digits-500ms" />
	 					<f:selectItem itemLabel="Digits-1s" itemValue="digits-1s" />	
	 				
		 */
		
		if(!this.formatName.equalsIgnoreCase("tts") && 
				!this.formatName.equalsIgnoreCase("digits-200ms") &&
				!this.formatName.equalsIgnoreCase("digits-500ms") &&
				!this.formatName.equalsIgnoreCase("digits-1s")) {
			if(this.formatParameter == null || this.formatParameter.length() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Audio","Please, select the Format Parameter!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
			if(this.formatValue == null || this.formatValue.length() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Audio","Please, select the Format Value!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		AudioVarEntity audioVar = new AudioVarEntity();
		audioVar.setId(Util.getUID());
		audioVar.setAudioId(this.audio.getId());
		audioVar.setFormatName(this.formatName);
		audioVar.setFormatParameter(this.formatParameter);
		audioVar.setFormatValue(this.formatValue);
		
		this.audio.getAudioVar().add(audioVar);
		this.formatParameter = "";
		this.formatValue = "";
	}

	public VersionEntity getVersion() {
		return version;
	}
	public void setVersion(VersionEntity version) {
		this.version = version;
	}


	public void formatNameChange() {
		this.audio.getAudioVar().clear();
		this.formatParameter = "";
		this.formatValue = "";
	}
	public void formatParameterChange() {
		this.formatValue = "";
	}
	
	
	public void typeChange() {
		this.formatName = "";
		this.formatParameter = "";
		this.formatValue = "";
		
		this.audio.setName(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_name").toString());
		this.audio.setDescription(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_description").toString());
		this.audio.setPath("");
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_path") != null) {
			this.audio.setPath(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_path").toString());
		}
		this.audio.setContext("");
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_context") !=null) {
			this.audio.setContext(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_context").toString());
		}


	}
	
	public void handleFileUpload(FileUploadEvent event) {
		String path = "";
		String description = "";

		description = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_description");
		path = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:complement_audio_path");
		
		if(path.length() == 0) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Audio - Upload","Path is empty");
			FacesContext.getCurrentInstance().addMessage(null, msg);	
			return;
        }
        File file = new File(path);
        if(!file.exists()) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Audio - Upload","Path is invalid");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
        }
        String fileName = event.getFile().getFileName().replace(".mp3","").replace(".wav", "");
        
        this.audio.setName(fileName);
        this.audio.setPath(path);
        
        if(description.length() == 0){
        	this.audio.setDescription(fileName);
        }
        String destFile = path+"/"+event.getFile().getFileName();
        
        
        try {
			InputStream uploadedInputStream = event.getFile().getInputstream();
			Util.writeToFile(uploadedInputStream,destFile);
			
			FacesMessage message = new FacesMessage("Audio - Upload", event.getFile().getFileName() + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        
	        AudioEntity entity = ServicesFactory.getInstance().getAudioService().getByName(fileName);
	        if(entity != null) {
	        	this.audio = entity;
	        	this.audio.setPath(path);
	        	if(description.length() == 0){
	            	this.audio.setDescription(fileName);
	            }
	        	this.insert = false;	        	
	        }  
	        
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Audio - Upload","Error on write file "+event.getFile().getFileName()+", error: "+e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
        
        
        
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
			this.promptBean.setVersion(this.version);
		}
		
	}

	@Override
	public void edit(String id) {
		this.audio = ServicesFactory.getInstance().getAudioService().get(id);
		
		this.insert= false;
		
		
	}
	
	public void deleteVar(String id) {
		
		for(AudioVarEntity audioVar : this.audio.getAudioVar()) {
			if(audioVar.getId().equals(id)) {
				this.audio.getAudioVar().remove(audioVar);
				break;
			}
		}
	}
	
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.audio = ServicesFactory.getInstance().getAudioService().get(id);
		this.insert = false;
		if(this.isUsed(id)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","You cannot delete Audio '"+this.audio.getName()+"' because there are dependences.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		if(ServicesFactory.getInstance().getAudioService().delete(this.audio)) {
			this.insert = true;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Audio",this.audio.getName()+" - Deleted!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Audio","Error on Delete "+this.audio.getName()+", please contact your support.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void viewDependence(String id, String name) {
		ServicesFactory.getInstance().getDependenceEditorService().getBean().setObject(Constants.DEPENDENCE_OBJECT_TYPE_AUDIO,id, name);
	}
	
	
    
}