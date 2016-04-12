package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.OperationGroupEditorBean;

@SuppressWarnings("serial")
public class OperationGroupEditorService extends AbstractBeanService<OperationGroupEditorBean>{
	public OperationGroupEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (OperationGroupEditorBean) elContext.getELResolver().getValue(elContext, null, "operationgroupEditorView");
	}

	public OperationGroupEditorBean getBean() {
		return this.bean;
	}
	

}
