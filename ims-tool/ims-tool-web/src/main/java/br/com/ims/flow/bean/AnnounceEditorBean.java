package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

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
	
	
    public AnnounceEditorBean() {
    	//init();
    }
    
    public void init() {
    	super.init();
    	this.announce = (AnnounceEntity)this.form.getFormId();
    	if(this.announce.getPrompt() != null)
    		this.promptId = this.announce.getPrompt().getId();
    	
    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    }
   
    public List<PromptEntity> getPrompts() {
		this.prompts = ServicesFactory.getInstance().getPromptService().getAll();
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
		
		
		
		this.announce.setPrompt(ServicesFactory.getInstance().getPromptService().get(this.promptId));
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Announce",this.announce.getName()+" - Updated!");
		 
		ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
				
		logicalFlow.validateNodes();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);

    }
	
	public void addPrompt(ActionEvent event) {
		
		this.collect();
				
		ServicesFactory.getInstance().getIvrEditorService().getBean().setComplementPageEditor("/pages/complement/Prompt.xhtml");
		
		ServicesFactory.getInstance().getPromptEditorService().getBean().setAnnounceBean(this);
		

    }

	@Override
	public void save(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
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
		
		this.promptId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_prompt_input").toString();
		this.announce.setFlushprompt(Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_flush_prompt_input")));
						
	}
    
}