package br.com.ims.flow.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.ReturnEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "returnEditorView")
@ViewScoped
public class ReturnEditorBean extends AbstractBean {
     
	
	private ReturnEntity _return;
	
	
    public ReturnEditorBean() {
    	//init();
    }
    
    public void init() {
    
    	super.init();
    	
    	this._return = (ReturnEntity)this.form.getFormId();
    	    	
    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    }
   

	public FormEntity getForm() {
		return form;
	}

	public void setForm(FormEntity form) {
		this.form = form;
	}


	public void update(ActionEvent event) {
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Return",this._return.getName()+" - Updated!");
		 
		ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
				
		logicalFlow.validateNodes();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);

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
	
    
}