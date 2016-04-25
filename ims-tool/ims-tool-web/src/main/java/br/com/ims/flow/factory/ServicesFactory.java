package br.com.ims.flow.factory;

import br.com.ims.flow.service.AnnounceService;
import br.com.ims.flow.service.AnswerService;
import br.com.ims.flow.service.AudioEditorService;
import br.com.ims.flow.service.AudioService;
import br.com.ims.flow.service.ConditionEditorService;
import br.com.ims.flow.service.ConditionGroupEditorService;
import br.com.ims.flow.service.ConditionMapEditorService;
import br.com.ims.flow.service.ConditionMapService;
import br.com.ims.flow.service.ConditionService;
import br.com.ims.flow.service.ControlPanelService;
import br.com.ims.flow.service.DecisionService;
import br.com.ims.flow.service.DependenceEditorService;
import br.com.ims.flow.service.DisconnectService;
import br.com.ims.flow.service.FlowService;
import br.com.ims.flow.service.FormService;
import br.com.ims.flow.service.FormTypeService;
import br.com.ims.flow.service.GrammarEditorService;
import br.com.ims.flow.service.GrammarService;
import br.com.ims.flow.service.IvrEditorService;
import br.com.ims.flow.service.LoginBeanService;
import br.com.ims.flow.service.MenuService;
import br.com.ims.flow.service.NoMatchInputEditorService;
import br.com.ims.flow.service.NoMatchInputService;
import br.com.ims.flow.service.OperationGroupEditorService;
import br.com.ims.flow.service.OperationMapEditorService;
import br.com.ims.flow.service.OperationMapService;
import br.com.ims.flow.service.OperationService;
import br.com.ims.flow.service.PromptCollectService;
import br.com.ims.flow.service.PromptEditorService;
import br.com.ims.flow.service.PromptService;
import br.com.ims.flow.service.ReturnService;
import br.com.ims.flow.service.TagEditorService;
import br.com.ims.flow.service.TagService;
import br.com.ims.flow.service.TagTypeService;
import br.com.ims.flow.service.TransferService;
import br.com.ims.flow.service.VersionService;


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

	public IvrEditorService getIvrEditorService() {
		return new IvrEditorService();
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
	

	public ConditionEditorService getConditionEditorService() {
		return new ConditionEditorService();
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
	
	public GrammarService getGrammarService() {
		return new GrammarService();
	}
	public GrammarEditorService getGrammarEditorService() {
		return new GrammarEditorService();
	}
	
	public ConditionGroupEditorService getConditionGroupEditorService() {
		return new ConditionGroupEditorService();
	}
	
	public ConditionMapService getConditionMapService() {
		return new ConditionMapService();
	}
	public ConditionMapEditorService getConditionMapEditorService() {
		return new ConditionMapEditorService();
	}
	public OperationMapService getOperationMapService() {
		return new OperationMapService();
	}

	public OperationMapEditorService getOperationMapEditorService() {
		return new OperationMapEditorService();
	}
	public OperationGroupEditorService getOperationGroupEditorService() {
		return new OperationGroupEditorService();
	}
	public VersionService getVersionService() {
		return new VersionService();
	}
	
	public DependenceEditorService getDependenceEditorService() {
		return new DependenceEditorService();
	}
	public AnnounceService getAnnounceService() {
		return new AnnounceService();
	}
	public AnswerService getAnswerService() {
		return new AnswerService();
	}
	public ReturnService getReturnService() {
		return new ReturnService();
	}
	public DisconnectService getDisconnectService() {
		return new DisconnectService();
	}
	public PromptCollectService getPromptCollectService() {
		return new PromptCollectService();
	}
	public MenuService getMenuService() {
		return new MenuService();
	}
	public DecisionService getDecisionService() {
		return new DecisionService();
	}
	public FlowService getFlowService() {
		return new FlowService();
	}
	public TransferService getTransferService() {
		return new TransferService();
	}
	public OperationService getOperationService() {
		return new OperationService();
	}
	public ControlPanelService getControlPanelService() {
		return new ControlPanelService();
	}
	public LoginBeanService getLoginBeanService() {
		return new LoginBeanService();
	}

}
