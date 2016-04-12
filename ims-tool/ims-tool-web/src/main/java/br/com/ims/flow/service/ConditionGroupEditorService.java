package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.ConditionGroupEditorBean;

@SuppressWarnings("serial")
public class ConditionGroupEditorService extends AbstractBeanService<ConditionGroupEditorBean>{
	public ConditionGroupEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (ConditionGroupEditorBean) elContext.getELResolver().getValue(elContext, null, "conditiongroupEditorView");
	}

	public ConditionGroupEditorBean getBean() {
		return this.bean;
	}
	

}
