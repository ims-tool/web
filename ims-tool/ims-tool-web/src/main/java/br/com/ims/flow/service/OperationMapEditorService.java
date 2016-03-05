package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.OperationMapEditorBean;

public class OperationMapEditorService extends AbstractBeanService<OperationMapEditorBean>{
	public OperationMapEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (OperationMapEditorBean) elContext.getELResolver().getValue(elContext, null, "operationmapEditorView");
	}

	public OperationMapEditorBean getBean() {
		return this.bean;
	}
	

}
