package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.ConditionEditorBean;

@SuppressWarnings("serial")
public class ConditionEditorService extends AbstractBeanService<ConditionEditorBean>{
	public ConditionEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (ConditionEditorBean) elContext.getELResolver().getValue(elContext, null, "conditionEditorView");
	}

	public ConditionEditorBean getBean() {
		return this.bean;
	}
	

}
