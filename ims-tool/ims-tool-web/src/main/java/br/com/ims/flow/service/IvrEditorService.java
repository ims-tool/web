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
import org.primefaces.model.diagram.overlay.LabelOverlay;

import br.com.ims.flow.bean.IvrEditorBean;
import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AbstractFormEntity;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.DecisionChanceEntity;
import br.com.ims.flow.model.DecisionEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptCollectEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.VersionEntity;

@SuppressWarnings("serial")
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
			/*Connection conn = new Connection(sourceElement.getEndPoints().get(sourceElement.getEndPoints().size()-1), targetElement.getEndPoints().get(0));
			model.getConnections().add(conn);
			model.connect(conn);
			flow.connect(sourceElement, targetElement, conn);*/
			this.connect(sourceElement, targetElement);
		}
		
	}
	public void disconnectForm(Element sourceElement,boolean allNodes) {
		disconnectForm(bean.getModel(),bean.getLogicalFlow(), sourceElement,allNodes);
	}
	public void disconnectForm(DefaultDiagramModel model,LogicalFlow flow, Element sourceElement,boolean allNodes) {
		
		Node nodeSource = flow.getNode(sourceElement);
		List<Connection> connections = model.getConnections();
		boolean find = false;
		for(int index = 0; index < connections.size() && !find; index++ ){
			Connection connection = connections.get(index) ;
			
			if(nodeSource.getConnection() != null &&
			  (nodeSource.getConnection().getSource().getAnchor().equals(EndPointAnchor.BOTTOM) || allNodes) &&		
			   nodeSource.getConnection().getTarget().getId().equals(connection.getTarget().getId()) &&
			   nodeSource.getConnection().getSource().getId().equals(connection.getSource().getId()) ) {
				find = true;
				model.getConnections().remove(index);				
				flow.disconnect(sourceElement,allNodes);
			}
			
		}				
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
	public void deleteForm(Element element,boolean allNodes) {		
		
		deleteForm(bean.getModel(),bean.getLogicalFlow(), element,allNodes);
	}
	
	public void deleteForm(DefaultDiagramModel model,LogicalFlow flow, Element element,boolean allNodes) {
		Node node = flow.getNode(element);
		if(node != null) {
			
			while(node.getListSource().size() > 0) {
				Node source = node.getListSource().get(0);
				disconnectForm(model, flow, source.getElement(), node.getElement());
			}
			disconnectForm(model, flow, node.getElement(),allNodes);
			model.getElements().remove(node.getElement());
			flow.delNode(node.getElement(),allNodes);
		}
		this.bean.getListForm().remove((FormEntity)element.getData());				
	}
	
	
	private EndPoint createDotEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor,4);
        endPoint.setScope("formEntity");
        endPoint.setTarget(true);
        endPoint.setSource(false);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
     
    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor,10,10);
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
    public boolean save(LogicalFlow logicalFlow, String flowName, VersionEntity version) {
    	boolean result = true;
    	Map<String,List<FormEntity>> map = new HashMap<String,List<FormEntity>>();
    	
    	/**
    	 * Caso tenho algum noinput,nomatch,choice ou decisionchance para deletar, 
    	 * antes irei fazer um update no nome para não dar erro de chave, no final do codigo será excluído
    	 */
    	for(Node node : logicalFlow.getListDeletedNode()) {
    		if(node.getForm().getFormType().getName().equals(Constants.FORM_TYPE_NOINPUT) || 
    				node.getForm().getFormType().getName().equals(Constants.FORM_TYPE_NOMATCH) ||
    				node.getForm().getFormType().getName().equals(Constants.FORM_TYPE_DECISION_CHANCE) ||
    				node.getForm().getFormType().getName().equals(Constants.FORM_TYPE_CHOICE)) {
    			node.getForm().setName(node.getForm().getName()+"_"+node.getForm().getId());
    			result = result & ServicesFactory.getInstance().getFormService().update(node.getForm());
    		}
    					
    	}
    	/**********************************/
    	for(Node node : logicalFlow.getListNode()) {
    		
    		FormEntity form = node.getForm();
    		form.setVersionId(version.getId());
    		form.setFlowName(flowName);
    		((AbstractFormEntity)form.getFormId()).setVersionId(version.getId());
    		boolean exists = false;
    		
    		/**
    		 * Preciso salvar todos os forms pra manter a posição X e Y na tela
    		 */
    		if(ServicesFactory.getInstance().getFormService().get(form.getId(),true) == null) {
    			result = result & ServicesFactory.getInstance().getFormService().save(form);
				
    		} else {
    			exists = true;
    			result = result & ServicesFactory.getInstance().getFormService().update(form);
    			
    			
    		}
    		/**
    		 * Setando o noinput / nomatch no objeto pai
    		 */
    		if(form.getFormType().getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT))  {
    			PromptCollectEntity pc = (PromptCollectEntity)form.getFormId();
    			
    			if(node.getListTarget() != null) {
	    			for(Node aux : node.getListTarget()) {
	    				AbstractFormEntity abs = (AbstractFormEntity)aux.getForm().getFormId();
	    				if(aux.getForm().getFormType().getName().equals(Constants.FORM_TYPE_NOINPUT)) {    					
	    					pc.setNoInput_NextForm(abs.getNextForm());
	    					pc.setNoInput_Tag(abs.getTag());
	    				} else if(aux.getForm().getFormType().getName().equals(Constants.FORM_TYPE_NOMATCH)) {
	    					pc.setNoMatch_NextForm(abs.getNextForm());
	    					pc.setNoMatch_Tag(abs.getTag());
	    				}
	    			}
    			}
    		}
    		if(form.getFormType().getName().equals(Constants.FORM_TYPE_MENU))  {
    			MenuEntity menu = (MenuEntity)form.getFormId();
    			
    			if(node.getListTarget() != null) {
	    			for(Node aux : node.getListTarget()) {
	    				AbstractFormEntity abs = (AbstractFormEntity)aux.getForm().getFormId();
	    				if(aux.getForm().getFormType().getName().equals(Constants.FORM_TYPE_NOINPUT)) {
	    					menu.setNoInput_NextForm(abs.getNextForm());
	    					menu.setNoInput_Tag(abs.getTag());
	    				} else if(aux.getForm().getFormType().getName().equals(Constants.FORM_TYPE_NOMATCH)) {
	    					menu.setNoMatch_NextForm(abs.getNextForm());
	    					menu.setNoMatch_Tag(abs.getTag());
	    				}
	    			}
    			}
    		}
    		/******************************************/
    		
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
    			if(!exists) {
    				List<FormEntity> listForm =  null;
        			if(map.get("INSERT") == null) {
        				listForm = new ArrayList<FormEntity>();
        				map.put("INSERT", listForm);
        			}
        			listForm = map.get("INSERT");
        			listForm.add(form); 
        		} else {
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
    				result = result & ServicesFactory.getInstance().getFormService().saveObj(form);
    				
    			}
    		} else {
    			List<FormEntity> list = entry.getValue();
    			for(FormEntity form : list) {
    				result = result & ServicesFactory.getInstance().getFormService().updateObj(form);
    				
    			}
    		}
    	}
    	for(Node node : logicalFlow.getListDeletedNode()) {
    		result = result & ServicesFactory.getInstance().getFormService().deleteObj(node.getForm());
    					
    	}
    	for(Node node : logicalFlow.getListDeletedNode()) {
    		result = result & ServicesFactory.getInstance().getFormService().delete(node.getForm());
    					
    	}
    	return result;
		
    }
    
    private Element createElement(Element source,FormEntity form) {
    	this.bean.getListForm().add(form);
    	
    	Element element = new Element(form);
		element.setX(form.getPositionX());
		element.setY(form.getPositionY());
		
		ServicesFactory.getInstance().getIvrEditorService().setEndPoint(form.getFormType(), element);
		bean.getModel().addElement(element);
		bean.getLogicalFlow().addNode(element);
		
		this.connect(source, element);
		return element;
    }
    public void loadFlow(Element source, String formId) throws Exception {
    	
    	Node node = bean.getLogicalFlow().getNode(formId);    	
    	if(node != null) {
    		this.connect(source, node.getElement());
    		return;
    	}
    	FormEntity form = ServicesFactory.getInstance().getFormService().get(formId);
    	try {
	    	Element element = createElement(source,form);
			form.setNextForm(((AbstractFormEntity)form.getFormId()).getNextForm());
			if(form.getFormType().getName().equals(Constants.FORM_TYPE_ANSWER)) {
				this.bean.updateTabFlowName(form.getName());
	    		
	    	} else if(form.getFormType().getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT)) {
				PromptCollectEntity pc = (PromptCollectEntity)form.getFormId();
				FormEntity noInput = null;
				FormEntity noMatch = null;
				
				if(pc.getNoInput() != null) {
					noInput = this.getFormNoMatchInput(pc.getId()+pc.getNoInput().getId(),
													  Constants.FORM_TYPE_NOINPUT,
													  pc.getNoInput_NextForm(),
													  form,
													  pc.getNoInput());
				}
				if(pc.getNoMatch() != null){
					noMatch = this.getFormNoMatchInput(pc.getId()+pc.getNoMatch().getId(),
													  Constants.FORM_TYPE_NOMATCH,
													  pc.getNoMatch_NextForm(),
													  form,
				
													  pc.getNoMatch());
				}	
				if(noInput != null) {
					Element elementNoInput = createElement(element,noInput);
					if(noInput.getNextForm() != null && noInput.getNextForm().length() > 0) {
						loadFlow(elementNoInput, noInput.getNextForm());
					}
				}
				if(noMatch != null) {
					Element elementNoMatch = createElement(element,noMatch);
					if(noMatch.getNextForm() != null && noMatch.getNextForm().length() > 0) {
						loadFlow(elementNoMatch, noMatch.getNextForm());
					}
				}
				
	    	} else if(form.getFormType().getName().equals(Constants.FORM_TYPE_MENU)) {
				
				MenuEntity menu = (MenuEntity)form.getFormId();
				FormEntity noInput = null;
				FormEntity noMatch = null;
				if(menu.getNoInput() != null) {		
					noInput = this.getFormNoMatchInput(menu.getId()+menu.getNoInput().getId(),
							  Constants.FORM_TYPE_NOINPUT,
							  menu.getNoInput_NextForm(),
							  form,
							  menu.getNoInput());
				}
				if(menu.getNoMatch() != null) {
					noMatch = this.getFormNoMatchInput(menu.getId()+menu.getNoMatch().getId(),
							  Constants.FORM_TYPE_NOMATCH,
							  menu.getNoMatch_NextForm(),
							  form,
							  menu.getNoMatch());
				}
				if(noInput != null) {
					Element elementNoInput = createElement(element,noInput);
					if(noInput.getNextForm() != null && noInput.getNextForm().length() > 0) {
						loadFlow(elementNoInput, noInput.getNextForm());
					}
				}
				
				if(noMatch != null) {
					Element elementNoMatch = createElement(element,noMatch);
					if(noMatch.getNextForm() != null && noMatch.getNextForm().length() > 0) {
						loadFlow(elementNoMatch, noMatch.getNextForm());
					}
				}
				if(menu.getChoices() != null && menu.getChoices().size() > 0) {
					for(ChoiceEntity choice : menu.getChoices()) {
						FormEntity formChoice = this.getFormChoice(choice);
						Element elementForm = createElement(element,formChoice);
						if(formChoice.getNextForm() != null && formChoice.getNextForm().length() > 0) {
							loadFlow(elementForm, formChoice.getNextForm());
						}
						
					}
				}
			} else if(form.getFormType().getName().equals(Constants.FORM_TYPE_DECISION)) {
				DecisionEntity decision = (DecisionEntity)form.getFormId();
				if(decision.getListDecisionChance() != null && decision.getListDecisionChance().size() > 0) {
					for(DecisionChanceEntity chance : decision.getListDecisionChance()) {
						FormEntity formChance = this.getFormDecisionChance(chance);
						if(formChance != null) {
							Element elementForm = createElement(element,formChance);
							if(formChance.getNextForm() != null && formChance.getNextForm().length() > 0) {
								loadFlow(elementForm, formChance.getNextForm());
							}
						}
					}
				}
			}
			if(form.getNextForm() != null && form.getNextForm().length() > 0) {
				loadFlow(element, form.getNextForm());
			}
    	} catch(Exception e) {
    		if(e.getMessage() != null && e.getMessage().indexOf("Error on load Node: ") > -1) {
    			throw e;
    		} else {
    			throw new Exception("Error on load Node: "+(form == null ? formId : form.getName()),e);
    		}
    	}
   
		
    }
    private FormEntity getFormNoMatchInput(String id,String formType, String nextform,FormEntity parent, NoMatchInputEntity obj) {

    	FormEntity form = ServicesFactory.getInstance().getFormService().get(id);
    	
    	if(form == null) {
    		form = new FormEntity();
    		form.setId(id);
    		form.setFormType(ServicesFactory.getInstance().getFormTypeService().getByName(formType), obj);
    		form.setPositionX(parent.getPositionX());
    		form.setPositionY(parent.getPositionY());    		
		}
    	form.setNextForm(nextform);
    	String imgPath = form.getFormType().getImagePathSuccess();
		if(formType.equals(Constants.FORM_TYPE_NOINPUT)) {
			form.getFormType().setImagePathSuccess(imgPath.replace("<NOMACHINPUT>", Constants.NO_INPUT.toLowerCase()));
		} else {
			form.getFormType().setImagePathSuccess(imgPath.replace("<NOMACHINPUT>", Constants.NO_MATCH.toLowerCase()));
		}
		imgPath = form.getFormType().getImagePathError();
		if(formType.equals(Constants.FORM_TYPE_NOMATCH)) {
			form.getFormType().setImagePathError(imgPath.replace("<NOMACHINPUT>", Constants.NO_INPUT.toLowerCase()));
		} else {
			form.getFormType().setImagePathError(imgPath.replace("<NOMACHINPUT>", Constants.NO_MATCH.toLowerCase()));
		}
		
    	return form;
		
    }
    private FormEntity getFormChoice(ChoiceEntity choice) {

    	List<FormEntity> listForm = ServicesFactory.getInstance().getFormService().getByFilter("WHERE f.formid = '"+choice.getId()+"' AND ft.name = '"+Constants.FORM_TYPE_CHOICE+"' ", false);
    	FormEntity form = null;
    	if(listForm.size() > 0) {
    		form = listForm.get(0);
    		form.setNextForm(((AbstractFormEntity)form.getFormId()).getNextForm());
    		form.setFormType(form.getFormType(),choice);
    		
			String imgPath = form.getFormType().getImagePathSuccess();
			form.getFormType().setImagePathSuccess(imgPath.replace("<NUMBER>", choice.getDtmf().equals("*") ? "x" : choice.getDtmf()  ));
			
			imgPath = form.getFormType().getImagePathError();
			form.getFormType().setImagePathError(imgPath.replace("<NUMBER>", choice.getDtmf().equals("*") ? "x" : choice.getDtmf() ));
			
		}
    	return form;
		
    }
    private FormEntity getFormDecisionChance(DecisionChanceEntity decisionChance) {
    	
    	List<FormEntity> listForm = ServicesFactory.getInstance().getFormService().getByFilter("WHERE f.formid = '"+decisionChance.getId()+"' AND ft.name = '"+Constants.FORM_TYPE_DECISION_CHANCE+"' ", false);
    	FormEntity form = null;
    	if(listForm.size() > 0) {
    		form = listForm.get(0);
    		form.setNextForm(((AbstractFormEntity)form.getFormId()).getNextForm());
    		form.setFormType(form.getFormType(),decisionChance);
		}
    	return form;
		
    }
    public void connect(Element source, Element target) {
    	if(source != null) {
    		EndPoint epSource = null;
    		EndPoint epTarget = null;
    		
    		FormEntity fSource = (FormEntity)source.getData();
    		FormEntity fTarget = (FormEntity)target.getData();

    		for(EndPoint ep : source.getEndPoints()) {
    			if(fSource.getFormType().getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT)) {
    				if(fTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOINPUT) || 
    						fTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCH)	) {
    					if(ep.getAnchor().equals(EndPointAnchor.LEFT)) {
        					epSource = ep;
        					break;
        				}
    				} else {
    					if(ep.getAnchor().equals(EndPointAnchor.BOTTOM)) {
        					epSource = ep;
        					break;
        				}
    				}
    				
    			} else {
    				if(ep.getAnchor().equals(EndPointAnchor.BOTTOM)) {
        				epSource = ep;
        				break;
        			}
    			}
    			
    		}
    		for(EndPoint ep : target.getEndPoints()) {
    			if(ep.getAnchor().equals(EndPointAnchor.TOP)) {
    				epTarget = ep;
    				break;
    			}
    		}
    		
    		Connection conn = new Connection(epSource, epTarget);
			if(!(fTarget.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_CHOICE) ||
				  fTarget.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_NOINPUT) ||
				  fTarget.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_NOMATCH) ||
				  fTarget.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_DECISION_CHANCE))) {
	    		TagEntity tag = ((FormEntity)source.getData()).getTag();
				if(tag != null) {
					conn.getOverlays().add(new LabelOverlay("Tag "+tag.getId(), "flow-label", 0.5));
				}
			}
			bean.getModel().connect(conn);
			bean.getLogicalFlow().connect(source, target, conn);

			//this.connectForm(source, target);
		}
    }
    
    public void update(FormEntity formEntity) {
    	
    }
     
	
}
