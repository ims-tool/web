package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.DependenceEditorBean;

@SuppressWarnings("serial")
public class DependenceEditorService extends AbstractBeanService<DependenceEditorBean>{
	public DependenceEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (DependenceEditorBean) elContext.getELResolver().getValue(elContext, null, "dependenceEditorView");
	}

	public DependenceEditorBean getBean() {
		return this.bean;
	}
	

}
