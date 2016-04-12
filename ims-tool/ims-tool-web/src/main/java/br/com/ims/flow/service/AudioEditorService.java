package br.com.ims.flow.service;




import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.AudioEditorBean;

@SuppressWarnings("serial")
public class AudioEditorService extends AbstractBeanService<AudioEditorBean> {
	
	
	public AudioEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (AudioEditorBean) elContext.getELResolver().getValue(elContext, null, "audioEditorView");
	}
	
	
}
