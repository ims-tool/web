package br.com.ims.flow.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AudioEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.PromptAudioEntity;
import br.com.ims.flow.model.PromptEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "promptEditorView")
@ViewScoped
public class PromptEditorBean extends AbstractBean {
     
	
	
	private PromptEntity prompt;
	private AnnounceEditorBean announceBean;
	private MenuEditorBean menuBean;
	private PromptCollectEditorBean promptCollectorBean;
	private NoMatchInputEditorBean noMatchInputBean;
	private TransferEditorBean transferBean;
	
	private List<AudioEntity> audios;
	private List<ConditionEntity> conditions;
	private String conditionId;
	private String audioId;
	private String audioOrder;
	private List<PromptEntity> prompts;
	
	
	
    public PromptEditorBean() {
    	init();
    }
    
    public void init() {
    	this.prompt = new PromptEntity();
    	this.prompt.setAudios(new ArrayList<PromptAudioEntity>());    	
    	this.insert = true;
    	
    	this.announceBean = null;
    	this.menuBean = null;
    	this.noMatchInputBean = null;
    	this.promptCollectorBean = null;
    	this.transferBean = null;
    	
    	
    }
    
    
   
    public PromptEntity getPrompt() {
		return prompt;
	}

	public void setPrompt(PromptEntity prompt) {
		this.prompt = prompt;
	}
	
	
	public AnnounceEditorBean getAnnounceBean() {
		return announceBean;
	}

	public void setAnnounceBean(AnnounceEditorBean announceBean) {
		this.announceBean = announceBean;
	}

	
	
	public MenuEditorBean getMenuBean() {
		return menuBean;
	}

	public void setMenuBean(MenuEditorBean menuBean) {
		this.menuBean = menuBean;
	}
	
	public NoMatchInputEditorBean getNoMatchInputBean() {
		return noMatchInputBean;
	}

	public void setNoMatchInputBean(NoMatchInputEditorBean noMatchInputBean) {
		this.noMatchInputBean = noMatchInputBean;
	}

	
	
	public PromptCollectEditorBean getPromptCollectorBean() {
		return promptCollectorBean;
	}

	public void setPromptCollectorBean(PromptCollectEditorBean promptCollectorBean) {
		this.promptCollectorBean = promptCollectorBean;
	}

	
	
	public TransferEditorBean getTransferBean() {
		return transferBean;
	}

	public void setTransferBean(TransferEditorBean transferBean) {
		this.transferBean = transferBean;
	}

	protected void updateExternalsBean() {
    
		
		if(this.announceBean != null) {
			this.announceBean.setPromptId(this.prompt.getId());
		}
		
		if(this.menuBean != null) {
			this.menuBean.setPromptId(this.prompt.getId());
		}
		
		if(this.noMatchInputBean != null) {
			this.noMatchInputBean.setPromptId(this.prompt.getId());
		}
		
		if(this.promptCollectorBean != null) {
			this.getPromptCollectorBean().setPromptId(this.prompt.getId());
		}
		if(this.transferBean != null) {
			this.transferBean.setConditionId(this.prompt.getId());
		}
    }
	
	public void save(ActionEvent event) {
		
		if(this.prompt.getAudios().size() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prompt",this.prompt.getName()+" Missing audio!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		ServicesFactory.getInstance().getPromptService().save(this.prompt);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Prompt",this.prompt.getName()+" - Saved!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		
		updateExternalsBean();
		
		
		init();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);
		
    }   
	
	public List<AudioEntity> getAudios() {
		this.audios = ServicesFactory.getInstance().getAudioService().getAll();
		return this.audios;
	}

	
	
	public List<ConditionEntity> getConditions() {
		this.conditions = ServicesFactory.getInstance().getConditionService().getAll();
		return conditions;
	}

	

	public String getAudioId() {
		return audioId;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	public void setAudios(List<AudioEntity> audios) {
		this.audios = audios;
	}
	
	

	public String getAudioOrder() {
		return audioOrder;
	}

	public void setAudioOrder(String audioOrder) {
		this.audioOrder = audioOrder;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public boolean isUsed(String id) {
		return ServicesFactory.getInstance().getPromptService().isUsed(id) ;
	}
	
	
	public List<PromptEntity> getPrompts() {
		this.prompts = ServicesFactory.getInstance().getPromptService().getAll();
		return prompts;
	}

	

	public void addNewAudio(ActionEvent event) {
		
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setAuxiliarPageEditor("/pages/auxiliar/Audio.xhtml");
		
		ServicesFactory.getInstance().getAudioEditorService().getBean().setPromptBean(this);
	}
	
	public void addNewCondition(ActionEvent event) {
		
		this.collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setAuxiliarPageEditor("/pages/auxiliar/Condition.xhtml");
		
		ServicesFactory.getInstance().getConditionEditorService().getBean().setPromptBean(this);
	}

	
	
	public void addAudioToPrompt(ActionEvent event) {
		
		List<PromptAudioEntity> listAudios =  prompt.getAudios();	
		
		this.audioId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:complement_prompt_audio_input").toString();
		this.audioOrder = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:complement_prompt_audio_order").toString();
		this.conditionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:complement_prompt_condition_input").toString();
		
		
		if(this.audioId == null || this.audioId.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prompt","Please, select an audio!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		if(audioOrder == null || audioOrder.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prompt","Please, inform audio order!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		for(PromptAudioEntity promptAudio : listAudios) {
			if(promptAudio.getOrderNum() != null && promptAudio.getOrderNum().toString().equals(this.audioOrder)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prompt","Audio Order: "+audioOrder+" already exists!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		PromptAudioEntity promptAudio = new PromptAudioEntity(); 
		promptAudio.setAudio(ServicesFactory.getInstance().getAudioService().get(this.audioId));
		promptAudio.setOrderNum(Integer.valueOf(this.audioOrder));
		promptAudio.setPromptId(this.prompt.getId());
		if(this.conditionId != null && this.conditionId.length() > 0) {
			promptAudio.setCondition(ServicesFactory.getInstance().getConditionService().get(this.conditionId));
		}
		listAudios.add(promptAudio);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Prompt","Audio added");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}

	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	protected void collect() {
		this.prompt.setName(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:complement_prompt_name").toString());
		
		this.audioId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:complement_prompt_audio_input").toString();
		this.audioOrder = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:complement_prompt_audio_order").toString();
		this.conditionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:complement_prompt_condition_input").toString();
	}
	
    
}