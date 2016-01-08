package br.com.ims.flow.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AnswerEntity;
import br.com.ims.flow.model.FormEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "answerEditorView")
@ViewScoped
public class AnswerEditorBean extends AbstractBean {
     
	
	private AnswerEntity answer;
	private FormEntity form;
	private LogicalFlow flow;
	
    public AnswerEditorBean() {
    	//init();
    }
    
    public void init() {
    	this.form = ServicesFactory.getInstance().getFlowEditorService().getForm();
    	this.answer = (AnswerEntity)this.form.getFormId();
    	this.flow = ServicesFactory.getInstance().getFlowEditorService().getFlow();
    	
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
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Answer",this.answer.getName()+" - Updated!");
		 
		ServicesFactory.getInstance().getFlowEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
				
		flow.validateNodes();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);

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