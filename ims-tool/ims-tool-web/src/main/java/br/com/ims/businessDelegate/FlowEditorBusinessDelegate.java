package br.com.ims.businessDelegate;

import java.util.List;

import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.model.diagram.DefaultDiagramModel;

import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.PromptEntity;

public class FlowEditorBusinessDelegate {

	
	
	public static void connectForm(DefaultDiagramModel model, LogicalFlow flow, ConnectEvent event) {
		ServicesFactory.getInstance().getFlowEditorService().connectForm(model, flow, event);
	}
	
	public static List<PromptEntity> getAllPrompt() {
		return ServicesFactory.getInstance().getPromptService().getAll();
	}
}
