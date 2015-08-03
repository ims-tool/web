package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.businessDelegate.FlowEditorBusinessDelegate;
import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AnnounceEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.PromptEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "announceEditorView")
@ViewScoped
public class AnnounceEditorBean extends AbstractBean {
     
	
	
	private List<PromptEntity> prompts;
	
	private String promptId;
	
	private AnnounceEntity announce;
	private FormEntity form;
	private LogicalFlow flow;
	
    public AnnounceEditorBean() {
    	//init();
    }
    
    public void init() {
    	this.form = ServicesFactory.getInstance().getFlowEditorService().getForm();
    	this.flow = ServicesFactory.getInstance().getFlowEditorService().getFlow();
    	this.announce = (AnnounceEntity)this.form.getObject();
    	if(this.announce.getPrompt() != null)
    		this.promptId = this.announce.getPrompt().getId();
    	
    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    }
   
    public List<PromptEntity> getPrompts() {
		this.prompts = FlowEditorBusinessDelegate.getAllPrompt();
		return prompts;
	}

	public void setPrompts(List<PromptEntity> prompts) {
		this.prompts = prompts;
	}

	public String getPromptId() {
		return promptId;
	}

	public void setPromptId(String promptId) {
		this.promptId = promptId;
	}

	public FormEntity getForm() {
		return form;
	}

	public void setForm(FormEntity form) {
		this.form = form;
	}

	public AnnounceEntity getAnnounce() {
		return announce;
	}

	public void setAnnounce(AnnounceEntity announce) {
		this.announce = announce;
	}

	public void update(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean formUpdated = true;
		
		this.announce.setPrompt(ServicesFactory.getInstance().getPromptService().get(this.promptId));
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Announce",this.announce.getName()+" - Updated!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("formUpdated", formUpdated);		
		flow.validateNodes();

    }
	
	public void addPrompt(ActionEvent event) {
		
				
		ServicesFactory.getInstance().getFlowEditorService().getBean().setComplementPageEditor("/pages/complement/Prompt.xhtml");
		
		ServicesFactory.getInstance().getPromptEditorService().getBean().setAnnounceBean(this);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Announce","Add Prompt Clicked!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		

    }
	
    
}