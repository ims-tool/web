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
import br.com.ims.flow.model.VersionEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "versionEditorView")
@ViewScoped
public class VersionEditorBean extends AbstractBean {
     
	private VersionEntity version;
	private List<VersionEntity> versions;
	
	private IvrEditorBean ivrEditorBean;
	private AudioEditorBean audioEditorBean;
	private PromptEditorBean promptEditorBean;
	private ConditionEditorBean conditionEditorBean;
	private ConditionMapEditorBean conditionMapEditorBean;
	private TagEditorBean tagEditorBean;
	private GrammarEditorBean grammarEditorBean;
	private NoMatchInputEditorBean noMatchInputEditorBean;
    public VersionEditorBean() {
    	init();
    }
    
    public void init() {
    	//super.init();
    	this.version = new VersionEntity();
    	this.version.setId(Util.getVERSIONID());
    	//this.version.setSystem_user(System.getProperty("user.name"));
    	this.version.setSystem_user(Util.getUserName());
    	
    	this.ivrEditorBean = null;
    	this.audioEditorBean = null;
    	this.promptEditorBean = null;
    	
    	this.conditionEditorBean = null;
    	this.grammarEditorBean = null;
    	this.noMatchInputEditorBean = null;
    	
    }
    
    
    
    public VersionEntity getVersion() {
		return version;
	}

	public void setVersion(VersionEntity version) {
		this.version = version;
	}

	public List<VersionEntity> getVersions() {
    	this.versions = ServicesFactory.getInstance().getVersionService().getAll();
    	return this.versions;
    }
   	public void setVersions(List<VersionEntity> versions) {
		this.versions = versions;
	}


	public IvrEditorBean getIvrEditorBean() {
		return ivrEditorBean;
	}

	public void setIvrEditorBean(IvrEditorBean ivrEditorBean) {
		this.ivrEditorBean = ivrEditorBean;
	}
	public AudioEditorBean getAudioEditorBean() {
		return audioEditorBean;
	}

	public void setAudioEditorBean(AudioEditorBean audioEditorBean) {
		this.audioEditorBean = audioEditorBean;
	}
	public PromptEditorBean getPromptEditorBean() {
		return promptEditorBean;
	}

	public void setPromptEditorBean(PromptEditorBean promptEditorBean) {
		this.promptEditorBean = promptEditorBean;
	}
	public ConditionEditorBean getConditionEditorBean() {
		return conditionEditorBean;
	}

	public void setConditionEditorBean(ConditionEditorBean conditionEditorBean) {
		this.conditionEditorBean = conditionEditorBean;
	}
   	public void update(ActionEvent event) {
		
//		this.announce.setPrompt(ServicesFactory.getInstance().getPromptService().get(this.promptId));
		
	//	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Announce",this.announce.getName()+" - Updated!");
		 
		//ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		//FacesContext.getCurrentInstance().addMessage(null, msg);
				
		logicalFlow.validateNodes();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);

    }
	
	
	@Override
	public void save(ActionEvent event) {
			 
		version.setDescription(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAdmin:version_description").toString());
		if(version.getDescription() == null || version.getDescription().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Version","Please, inform the Description");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		ServicesFactory.getInstance().getVersionService().save(version);
		
		updateExternalsBean();
		

		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	
	public void select(String id) {
		// TODO Auto-generated method stub
		this.version = ServicesFactory.getInstance().getVersionService().get(id);
		
		updateExternalsBean();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return ServicesFactory.getInstance().getVersionService().isUsed(id);
	}

	@Override
	protected void updateExternalsBean() {
		if(this.ivrEditorBean != null) {
			this.ivrEditorBean.setVersion(this.version);
			this.ivrEditorBean = null;
		}
		if(this.audioEditorBean != null) {
			this.audioEditorBean.setVersion(this.version);
			this.audioEditorBean = null;
		}
		if(this.promptEditorBean != null) {
			this.promptEditorBean.setVersion(this.version);
			this.promptEditorBean = null;
		}
		if(this.conditionEditorBean != null) {
			this.conditionEditorBean.setVersion(this.version);
			this.conditionEditorBean = null;
		}
		if(this.conditionMapEditorBean != null) {
			this.conditionMapEditorBean.setVersion(this.version);
			this.conditionMapEditorBean = null;
		}
		if(this.tagEditorBean != null) {
			this.tagEditorBean.setVersion(this.version);
			this.tagEditorBean = null;
		}
		if(this.grammarEditorBean != null) {
			this.grammarEditorBean.setVersion(this.version);
			this.grammarEditorBean = null;
		}
		if(this.noMatchInputEditorBean != null) {
			this.noMatchInputEditorBean.setVersion(this.version);
			this.noMatchInputEditorBean = null;
		}
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		
	}
	
	protected void collect() {
		super.collect();
		
	//	this.promptId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_prompt_input").toString();
	//	this.announce.setFlushprompt(Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_flush_prompt_input")));
						
	}
	public void viewDependence(String id, String name) {
		ServicesFactory.getInstance().getDependenceEditorService().getBean().setObject(Constants.DEPENDENCE_OBJECT_TYPE_VERSION,id, name);
	}

	public ConditionMapEditorBean getConditionMapEditorBean() {
		return conditionMapEditorBean;
	}

	public void setConditionMapEditorBean(ConditionMapEditorBean conditionMapEditorBean) {
		this.conditionMapEditorBean = conditionMapEditorBean;
	}

	public TagEditorBean getTagEditorBean() {
		return tagEditorBean;
	}

	public void setTagEditorBean(TagEditorBean tagEditorBean) {
		this.tagEditorBean = tagEditorBean;
	}

	public GrammarEditorBean getGrammarEditorBean() {
		return grammarEditorBean;
	}

	public void setGrammarEditorBean(GrammarEditorBean grammarEditorBean) {
		this.grammarEditorBean = grammarEditorBean;
	}

	public NoMatchInputEditorBean getNoMatchInputEditorBean() {
		return noMatchInputEditorBean;
	}

	public void setNoMatchInputEditorBean(NoMatchInputEditorBean noMatchInputEditorBean) {
		this.noMatchInputEditorBean = noMatchInputEditorBean;
	} 
    
}