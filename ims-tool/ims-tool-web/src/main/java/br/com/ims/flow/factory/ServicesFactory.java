package br.com.ims.flow.factory;

import br.com.ims.flow.service.AudioEditorService;
import br.com.ims.flow.service.AudioService;
import br.com.ims.flow.service.ConditionService;
import br.com.ims.flow.service.FlowEditorService;
import br.com.ims.flow.service.FormService;
import br.com.ims.flow.service.FormTypeService;
import br.com.ims.flow.service.NoMatchInputEditorService;
import br.com.ims.flow.service.NoMatchInputService;
import br.com.ims.flow.service.PromptEditorService;
import br.com.ims.flow.service.PromptService;
import br.com.ims.flow.service.TagEditorService;
import br.com.ims.flow.service.TagService;
import br.com.ims.flow.service.TagTypeService;


public class ServicesFactory {

	private static ServicesFactory instance = null;

	public static ServicesFactory getInstance() {
		if (instance == null) {
			instance = new ServicesFactory();
		}
		return instance;
	}

	private ServicesFactory() {
	}

	public FlowEditorService getFlowEditorService() {
		return new FlowEditorService();
	}
	
	public FormService getFormService() {
		return new FormService();
	}
	
	public FormTypeService getFormTypeService() {
		return new FormTypeService();
	}
	
	public PromptService getPromptService() {
		return new PromptService(); 
	}
	
	public PromptEditorService getPromptEditorService() {
		return new PromptEditorService();
	}
	public AudioService getAudioService() {
		return new AudioService();
	}
	
	public AudioEditorService getAudioEditorService() {
		return new AudioEditorService(); 
	}
	
	public ConditionService getConditionService() {
		return new ConditionService();
	}
	
	public TagService getTagService() {
		return new TagService();
	}
	
	public TagTypeService getTagTypeService() {
		return new TagTypeService();
		
	}
	
	public TagEditorService getTagEditorService() {
		return new TagEditorService();
	}
	
	public NoMatchInputService getNoMatchInputService() {
		return new NoMatchInputService();
	}
	
	public NoMatchInputEditorService getNoMatchInputEditorService() {
		return new NoMatchInputEditorService();
	}
	
	

}
