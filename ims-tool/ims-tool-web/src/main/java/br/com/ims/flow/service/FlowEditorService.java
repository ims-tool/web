package br.com.ims.flow.service;


import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;

import br.com.ims.flow.bean.FlowEditorBean;
import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
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
	public void connectForm(Element sourceElement, Element targetElement) {
		this.connectForm(bean.getModel(), bean.getFlow(), sourceElement, targetElement);
	}
	
	public void connectForm(DefaultDiagramModel model, LogicalFlow flow, Element sourceElement, Element targetElement) {
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
		/**
		 * Se o primefaces bugou, eu crio a conexão manualmente
		 */
		if(!find) {
			Connection conn = new Connection(sourceElement.getEndPoints().get(sourceElement.getEndPoints().size()-1), targetElement.getEndPoints().get(0));
			model.getConnections().add(conn);
			flow.connect(sourceElement, targetElement, conn);
		}
		
	}
	public void disconnectForm(Element sourceElement) {
		disconnectForm(bean.getModel(),bean.getFlow(), sourceElement);
	}
	public void disconnectForm(DefaultDiagramModel model,LogicalFlow flow, Element sourceElement) {
		
		Node node = flow.getNode(sourceElement);
		List<Connection> connections = model.getConnections();
		boolean find = false;
		for(int index = 0; index < connections.size() && !find; index++ ){
			Connection connection = connections.get(index) ;
			if(node.getConnection().getTarget().getId().equals(connection.getTarget().getId()) &&
			   node.getConnection().getSource().getId().equals(connection.getSource().getId()) ) {
				model.getConnections().remove(index);
				find = true;
			}
			
		}
		
		flow.disconnect(sourceElement);
	}
	public void deleteForm(Element element) {		
		
		deleteForm(bean.getModel(),bean.getFlow(), element);
	}
	public void deleteForm(DefaultDiagramModel model,LogicalFlow flow, Element element) {
		Node node = flow.getNode(element);
		for(Node source : node.getListSource()) {
			disconnectForm(model, flow, source.getElement());
		}
		disconnectForm(model, flow, node.getElement());
		model.getElements().remove(node.getElement());
		flow.delNode(node.getElement());
		
		this.bean.getListForm().remove((FormEntity)element.getData());				
	}
	
	
	private EndPoint createDotEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setScope("formEntity");
        endPoint.setTarget(true);
        endPoint.setSource(false);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
     
    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
        endPoint.setScope("formEntity");
        endPoint.setTarget(false);
        endPoint.setSource(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
    
    public void setEndPoint(final FormTypeEntity formType, Element element) {
    	if(formType.getAllowInput() == 1) {
			EndPoint endPoint = createDotEndPoint(EndPointAnchor.TOP);
			element.addEndPoint(endPoint);
		} else {
			if(formType.getMandatoryInput() == 1) {
				EndPoint endPoint = new BlankEndPoint(EndPointAnchor.TOP);
				element.addEndPoint(endPoint);
				
			}
		}
		if(formType.getAllowOutput() == 1) {
			EndPoint endPoint = createRectangleEndPoint(EndPointAnchor.BOTTOM);
			element.addEndPoint(endPoint);
		}else {
			if(formType.getMandatoryOutput() == 1) {
				EndPoint endPoint = new BlankEndPoint(EndPointAnchor.BOTTOM);
				element.addEndPoint(endPoint);
				
			}
			
		}
    }
     
	
}
