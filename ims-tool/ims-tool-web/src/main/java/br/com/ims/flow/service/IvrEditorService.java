package br.com.ims.flow.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import br.com.ims.flow.bean.IvrEditorBean;
import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AbstractFormEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
import br.com.ims.flow.model.VersionEntity;

public class IvrEditorService extends AbstractBeanService<IvrEditorBean>{
	
	public IvrEditorService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (IvrEditorBean) elContext.getELResolver().getValue(elContext, null, "ivrEditorView");
	}
	public Object getBean(String form) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();

		return  elContext.getELResolver().getValue(elContext, null, form.toLowerCase()+"EditorView");
	}

	public FormEntity getForm() {
		
		return this.bean.getForm();
	}
	
	public FormEntity getForm(String code) {
		return getForm(this.bean, code);
	}
	
	public FormEntity getForm(IvrEditorBean flowEditor, String code) {		
		for (FormEntity form : flowEditor.getListForm()) {
			if(code.equals(form.getId())) {
		
				return form;
			}
		}
		return null;
	}
	
	public void connectForm(Element sourceElement, Element targetElement) {
		this.connectForm(bean.getModel(), bean.getLogicalFlow(), sourceElement, targetElement);
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
		disconnectForm(bean.getModel(),bean.getLogicalFlow(), sourceElement);
	}
	public void disconnectForm(DefaultDiagramModel model,LogicalFlow flow, Element sourceElement) {
		
		Node nodeSource = flow.getNode(sourceElement);
		List<Connection> connections = model.getConnections();
		boolean find = false;
		for(int index = 0; index < connections.size() && !find; index++ ){
			Connection connection = connections.get(index) ;

			if(nodeSource.getConnection() != null &&
			   nodeSource.getConnection().getTarget().getId().equals(connection.getTarget().getId()) &&
			   nodeSource.getConnection().getSource().getId().equals(connection.getSource().getId()) ) {
				model.getConnections().remove(index);
				find = true;
			}
			
		}
		
		flow.disconnect(sourceElement);
	}
	public void disconnectForm(DefaultDiagramModel model,LogicalFlow flow, Element sourceElement, Element targetElement) {
		
		List<Connection> connections = model.getConnections();
		boolean find = false;
		
		for(int index = 0; index < connections.size() && !find; index++ ){
			Connection connection = connections.get(index);
			
			for(EndPoint sourceEndPoint : sourceElement.getEndPoints()) {
				if(connection.getSource().equals(sourceEndPoint)) {
					for(EndPoint targetEndPoint : targetElement.getEndPoints()) {
						if(connection.getTarget().equals(targetEndPoint)) {
							model.getConnections().remove(index);
							find = true;
						}
					}
				}
			}
			
		}
				
		flow.disconnect(sourceElement,targetElement);
	}
	public void deleteForm(Element element) {		
		
		deleteForm(bean.getModel(),bean.getLogicalFlow(), element);
	}
	public void deleteForm(DefaultDiagramModel model,LogicalFlow flow, Element element) {
		Node node = flow.getNode(element);
		if(node != null) {
			while(node.getListSource().size() > 0) {
				Node source = node.getListSource().get(0);
				disconnectForm(model, flow, source.getElement(), node.getElement());
			}
			disconnectForm(model, flow, node.getElement());
			model.getElements().remove(node.getElement());
			flow.delNode(node.getElement());		
		}
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
			
			endPoint = new BlankEndPoint(EndPointAnchor.LEFT);
			element.addEndPoint(endPoint);
		}else {
			if(formType.getMandatoryOutput() == 1) {
				EndPoint endPoint = new BlankEndPoint(EndPointAnchor.BOTTOM);
				element.addEndPoint(endPoint);
				
			}
			
		}
    }
    
    public void alingMenuChoices(Element element) {
    	this.bean.getLogicalFlow().alingMenuChoices(element);
    	
    }
    public boolean save(LogicalFlow logicalFlow, VersionEntity version) {
    	boolean result = true;
    	Map<String,List<FormEntity>> map = new HashMap<String,List<FormEntity>>();
    	for(Node node : logicalFlow.getListNode()) {
    		
    		FormEntity form = node.getForm();
    		form.setVersionId(version);
    		((AbstractFormEntity)form.getFormId()).setVersionId(version);
    		if(form.getFormType().getName().equals(Constants.FORM_TYPE_ANSWER) ||
    			form.getFormType().getName().equals(Constants.FORM_TYPE_ANNOUNCE) ||
    			form.getFormType().getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT) ||
    			form.getFormType().getName().equals(Constants.FORM_TYPE_MENU) ||
    			form.getFormType().getName().equals(Constants.FORM_TYPE_FLOW) ||
    			form.getFormType().getName().equals(Constants.FORM_TYPE_DECISION) ||
    			form.getFormType().getName().equals(Constants.FORM_TYPE_OPERATION) ||
    			form.getFormType().getName().equals(Constants.FORM_TYPE_TRANSFER) ||
    			form.getFormType().getName().equals(Constants.FORM_TYPE_DISCONNECT) ||
    			form.getFormType().getName().equals(Constants.FORM_TYPE_RETURN)) {
    			if(ServicesFactory.getInstance().getFormService().get(form.getId(),true) == null) {
    				result = ServicesFactory.getInstance().getFormService().save(form);
    				if(!result) {
    					return result;
    				}
        			List<FormEntity> listForm =  null;
        			if(map.get("INSERT") == null) {
        				listForm = new ArrayList<FormEntity>();
        				map.put("INSERT", listForm);
        			}
        			listForm = map.get("INSERT");
        			listForm.add(form); 
        		} else {
        			result = ServicesFactory.getInstance().getFormService().update(form);
        			if(!result) {
    					return result;
    				}
        			List<FormEntity> listForm =  null;
        			if(map.get("UPDATE") == null) {
        				listForm = new ArrayList<FormEntity>();
        				map.put("UPDATE", listForm);
        			}
        			listForm = map.get("UPDATE");
        			listForm.add(form); 
        		}
    		}
    		
    	}
    	for(Entry<String,List<FormEntity>> entry : map.entrySet()) {
    		if(entry.getKey().equals("INSERT")) {
    			List<FormEntity> list = entry.getValue();
    			for(FormEntity form : list) {
    				result = ServicesFactory.getInstance().getFormService().saveObj(form);
    				if(!result) {
    					return result;
    				}
    			}
    		} else {
    			List<FormEntity> list = entry.getValue();
    			for(FormEntity form : list) {
    				result = ServicesFactory.getInstance().getFormService().updateObj(form);
    				if(!result) {
    					return result;
    				}
    			}
    		}
    	}
    	return result;
		
    }
    public void update(FormEntity formEntity) {
    	
    }
     
	
}
