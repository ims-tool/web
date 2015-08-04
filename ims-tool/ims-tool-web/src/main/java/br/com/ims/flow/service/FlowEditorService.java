package br.com.ims.flow.service;


import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.EndPoint;

import br.com.ims.flow.bean.FlowEditorBean;
import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.PromptEntity;

public class FlowEditorService extends AbstractBeanService<FlowEditorBean>{
	
	public FlowEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (FlowEditorBean) elContext.getELResolver().getValue(elContext, null, "flowEditorView");
	}
	public Object getBean(String form) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();

		return  elContext.getELResolver().getValue(elContext, null, form.toLowerCase()+"EditorView");
	}

	public FormEntity getForm() {
		
		return this.bean.getForm();
	}
	
	public FormEntity getForm(int code) {
		return getForm(this.bean, code);
	}
	
	public PromptEntity getPrompt(int code) {
		
		return getPrompt(this.bean, code);
	}
	
	public FormEntity getForm(FlowEditorBean flowEditor, int code) {		
		for (FormEntity form : flowEditor.getListForm()) {
			if(String.valueOf(code).equals(form.getId())) {
		
				return form;
			}
		}
		return null;
	}
	
	public PromptEntity getPrompt(FlowEditorBean flowEditor, int code) {
		for (PromptEntity prompt : flowEditor.getPrompts()) {
			if(String.valueOf(code).equals(prompt.getId())) {
		
				return prompt;
			}
		}
		return null;
	}
	
	public LogicalFlow getFlow() {
		return this.bean.getFlow();
	}
	
	public void connectForm(DefaultDiagramModel model, LogicalFlow flow, Element sourceElement, Element targetElement) {
		/*List<Element> elements = model.getElements();
        boolean find = false;
        for (int indexElement = 0; indexElement < elements.size() && !find; indexElement++ ) {
        	Element element = elements.get(indexElement);
			if(element.getEndPoints() != null) {
				List<EndPoint> endpointsSourceElement = event.getSourceElement().getEndPoints();
				List<EndPoint> endpointsTargetElement = event.getTargetElement().getEndPoints();
				List<Connection> connections = model.getConnections();
				for (Connection connection : connections) { 
					
					for (EndPoint endPointSource : endpointsSourceElement) {
						if(connection.getSource() == endPointSource) {
							for(EndPoint endPointTarget : endpointsTargetElement) {
								if(connection.getTarget() == endPointTarget) {
									flow.connect(event.getSourceElement(), event.getTargetElement(), connection);
									find = true;
								}
							}
							
						}
					}
					
				}
				
			}
        }*/
		boolean find = false;
		List<EndPoint> endpointsSourceElement = sourceElement.getEndPoints();
		List<EndPoint> endpointsTargetElement = targetElement.getEndPoints();
		List<Connection> connections = model.getConnections();
		for (int index = 0; index < connections.size() && !find; index++) { 			
			Connection connection = connections.get(index);
			for (EndPoint endPointSource : endpointsSourceElement) {
				if(connection.getSource().getId().equals(endPointSource.getId())) {
					for(EndPoint endPointTarget : endpointsTargetElement) {
						if(connection.getTarget().getId().equals(endPointTarget.getId())) {
							flow.connect(sourceElement, targetElement, connection);
							find = true;
						}
					}
					
				}
			}
			
		}
	}
	public void disconnectForm(DefaultDiagramModel model,LogicalFlow flow, Element sourceElement) {
		
		Node node = flow.getNode(sourceElement);
		List<Connection> connections = model.getConnections();
		boolean find = false;
		for(int index = 0; index < connections.size() && !find; index++ ){
			Connection connection = connections.get(index) ;
			if(node.getConnection().getTarget().getId().equals(connection.getTarget().getId()) &&
			   node.getConnection().getSource().getId().equals(connection.getSource().getId()) ) {
		//		model.getConnections().remove(index);
				find = true;
			}
			
		}
			
		flow.disconnect(sourceElement);
	}
	
}
