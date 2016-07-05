package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.LogicOperationEditorBean;

@SuppressWarnings("serial")
public class LogicOperationEditorService extends AbstractBeanService<LogicOperationEditorBean>{
	public LogicOperationEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (LogicOperationEditorBean) elContext.getELResolver().getValue(elContext, null, "logicoperationEditorView");
	}

	public LogicOperationEditorBean getBean() {
		return this.bean;
	}
	

}
