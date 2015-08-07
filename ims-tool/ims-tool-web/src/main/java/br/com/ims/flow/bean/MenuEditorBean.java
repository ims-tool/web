package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AnnounceEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.PromptEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "menuEditorView")
@ViewScoped
public class MenuEditorBean extends AbstractBean {
     
	
	
	
	private MenuEntity menu;
	private FormEntity form;
	private LogicalFlow flow;
	
    public MenuEditorBean() {
    	//init();
    }
    
    public void init() {
    	this.form = ServicesFactory.getInstance().getFlowEditorService().getForm();
    	this.flow = ServicesFactory.getInstance().getFlowEditorService().getFlow();
    	this.menu = (MenuEntity)this.form.getObject();
    	
    	
    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    }
   
    

	public FormEntity getForm() {
		return form;
	}

	public void setForm(FormEntity form) {
		this.form = form;
	}

	

	public MenuEntity getMenu() {
		return menu;
	}

	public void setMenu(MenuEntity menu) {
		this.menu = menu;
	}

	public LogicalFlow getFlow() {
		return flow;
	}

	public void setFlow(LogicalFlow flow) {
		this.flow = flow;
	}

	public void update(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean formUpdated = true;
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Menu",this.menu.getName()+" - Updated!");
		 
		ServicesFactory.getInstance().getFlowEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("formUpdated", formUpdated);		
		flow.validateNodes();

    }
	
	public void addPrompt(ActionEvent event) {
		
				
		ServicesFactory.getInstance().getFlowEditorService().getBean().setComplementPageEditor("/pages/complement/Prompt.xhtml");
		
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Announce","Add Prompt Clicked!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		

    }

	@Override
	public void save(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
    
}