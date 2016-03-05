package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.ConditionMapEditorBean;

public class ConditionMapEditorService extends AbstractBeanService<ConditionMapEditorBean>{
	public ConditionMapEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (ConditionMapEditorBean) elContext.getELResolver().getValue(elContext, null, "conditionmapEditorView");
	}

	public ConditionMapEditorBean getBean() {
		return this.bean;
	}
	

}
