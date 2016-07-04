package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.LogicNodeEditorBean;

@SuppressWarnings("serial")
public class LogicNodeEditorService extends AbstractBeanService<LogicNodeEditorBean>{
	public LogicNodeEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (LogicNodeEditorBean) elContext.getELResolver().getValue(elContext, null, "logicnodeEditorView");
	}

	public LogicNodeEditorBean getBean() {
		return this.bean;
	}
	

}
