package br.com.ims.flow.service;




import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.TagEditorBean;

public class TagEditorService extends AbstractBeanService<TagEditorBean> {
	
	
	public TagEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (TagEditorBean) elContext.getELResolver().getValue(elContext, null, "tagEditorView");
	}
	
	
}
