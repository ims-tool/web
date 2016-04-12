package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.NoMatchInputEditorBean;

@SuppressWarnings("serial")
public class NoMatchInputEditorService extends AbstractBeanService<NoMatchInputEditorBean>{
	
	public NoMatchInputEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (NoMatchInputEditorBean) elContext.getELResolver().getValue(elContext, null, "nomatchinputEditorView");
	}

	public NoMatchInputEditorBean getBean() {
		return this.bean;
	}

}
