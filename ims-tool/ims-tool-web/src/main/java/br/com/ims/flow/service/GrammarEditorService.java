package br.com.ims.flow.service;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.GrammarEditorBean;

public class GrammarEditorService extends AbstractBeanService<GrammarEditorBean>{
	
	public GrammarEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (GrammarEditorBean) elContext.getELResolver().getValue(elContext, null, "grammarEditorView");
	}
	public GrammarEditorBean getBean() {
		return this.bean;
	}

}
