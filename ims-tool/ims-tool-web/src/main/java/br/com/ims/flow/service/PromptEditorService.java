package br.com.ims.flow.service;




import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.PromptEditorBean;

@SuppressWarnings("serial")
public class PromptEditorService extends AbstractBeanService<PromptEditorBean>{
	
	
	
	public PromptEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (PromptEditorBean) elContext.getELResolver().getValue(elContext, null, "promptEditorView");
	}

	public PromptEditorBean getBean() {
		return this.bean;
	}

	
}
