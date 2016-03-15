package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.FlowEntity;
import br.com.ims.flow.model.FormEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "flowEditorView")
@ViewScoped
public class FlowEditorBean extends AbstractBean {
     
	private List<FormEntity> forms;
	
	private String formId;
	
	private FlowEntity flow;
	
	
    public FlowEditorBean() {
    	//init();
    }
    
    public void init() {
    	super.init();
    	this.flow= (FlowEntity)this.form.getFormId();
    	
    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    }
   
    public List<FormEntity> getForms() {
		this.forms = ServicesFactory.getInstance().getFormService().getByFormTypeName(Constants.FORM_TYPE_ANSWER);
		return this.forms;
	}

	public void setForms(List<FormEntity> forms) {
		this.forms = forms;
	}

	public FormEntity getForm() {
		return form;
	}

	public void setForm(FormEntity form) {
		this.form = form;
	}


	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public FlowEntity getFlow() {
		return flow;
	}

	public void setFlow(FlowEntity flow) {
		this.flow = flow;
	}

	public void update(ActionEvent event) {
		
		this.flow.setFlowName(ServicesFactory.getInstance().getFormService().get(this.formId).getName());
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Flow",this.flow.getName()+" - Updated!");
		 
		ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
				
		logicalFlow.validateNodes();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);

    }
	
	public void addNewFlow(ActionEvent event) {
		
		this.collect();
		ServicesFactory.getInstance().getIvrEditorService().getBean().addNewTabFlow();

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
	
	protected void collect() {
		super.collect();
		this.formId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:form_flow_input").toString();
						
	}
    
}