package br.com.ims.flow.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.FormEntity;
 
@SuppressWarnings("serial")
public abstract class AbstractBean implements Serializable {
     
	protected boolean insert;
	protected FormEntity form;
	protected LogicalFlow flow;
	
	
	public boolean isInsert() {
		return insert;
	}


	public void setInsert(boolean insert) {
		this.insert = insert;
	}
	
	

	
	public FormEntity getForm() {
		return form;
	}


	public void setForm(FormEntity form) {
		this.form = form;
	}


	public LogicalFlow getFlow() {
		return flow;
	}


	public void setFlow(LogicalFlow flow) {
		this.flow = flow;
	}


	public void init() {
		this.form = ServicesFactory.getInstance().getFlowEditorService().getForm();
    	this.flow = ServicesFactory.getInstance().getFlowEditorService().getFlow();
	}
	protected void collect() {
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_name") != null)
			this.form.setName(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_name").toString());
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_description") != null)
			this.form.setDescription(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_description").toString());
	}
	
	public abstract void save(ActionEvent event);
	public abstract void update(ActionEvent event);
	public abstract void edit(String id);
	public abstract void delete(String id);
	public abstract boolean isUsed(String id);
	protected abstract void updateExternalsBean();
}