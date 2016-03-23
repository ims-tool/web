package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.VersionEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "versionEditorView")
@ViewScoped
public class VersionEditorBean extends AbstractBean {
     
	//continuar!!
	private VersionEntity version;
	private List<VersionEntity> versions;
	
	
    public VersionEditorBean() {
    	init();
    }
    
    public void init() {
    	super.init();
    	this.version = new VersionEntity();
    	this.version.setId(Util.getVERSIONID());
    	this.version.setSystem_user(System.getProperty("user.name"));
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

	public void update(ActionEvent event) {
		
//		this.announce.setPrompt(ServicesFactory.getInstance().getPromptService().get(this.promptId));
		
	//	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Announce",this.announce.getName()+" - Updated!");
		 
		ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		//FacesContext.getCurrentInstance().addMessage(null, msg);
				
		logicalFlow.validateNodes();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);

    }
	
	
	@Override
	public void save(ActionEvent event) {
			 
		ServicesFactory.getInstance().getVersionService().save(version);
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setVersion(version);
		
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
		ServicesFactory.getInstance().getIvrEditorService().getBean().setVersion(this.version);
		
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
    
}