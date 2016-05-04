package br.com.ims.flow.service;


import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.*;

@SuppressWarnings("serial")
public class VersionEditorService extends AbstractBeanService<VersionEditorBean>{
	
	
	public VersionEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (VersionEditorBean) elContext.getELResolver().getValue(elContext, null, "versionEditorView");
	}

	public VersionEditorBean getBean() {
		return this.bean;
	}

	
}
