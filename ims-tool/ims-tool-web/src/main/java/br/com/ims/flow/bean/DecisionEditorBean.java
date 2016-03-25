package br.com.ims.flow.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.model.diagram.Element;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.DecisionChanceEntity;
import br.com.ims.flow.model.DecisionEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "decisionEditorView")
@ViewScoped
public class DecisionEditorBean extends AbstractBean {
     
	
	private String conditionId;
	private String orderNum;
	
	private List<ConditionEntity> conditions;
	
	private DecisionEntity decision;

	private List<DecisionChanceEntity> listDecisionChance;
	private String originalName;
	
	
    public DecisionEditorBean() {
    	//init();
    }
    
    @SuppressWarnings("unchecked")
	public void init() {
    	super.init();
    	
    	this.decision = (DecisionEntity)this.form.getFormId();
    	if(this.decision.getListDecisionChance() == null) {
    		this.decision.setListDecisionChance(new ArrayList<DecisionChanceEntity>());    
    	}
    	
    	    	
    	this.listDecisionChance = (List<DecisionChanceEntity>)((ArrayList<DecisionChanceEntity>)this.decision.getListDecisionChance()).clone();
    	
    	this.originalName = this.decision.getName();
    	
    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    }
    
    
   
	public DecisionEntity getDecision() {
		return decision;
	}

	public void setDecision(DecisionEntity decision) {
		this.decision = decision;
	}

	public List<DecisionChanceEntity> getListDecisionChance() {
		return listDecisionChance;
	}

	public void setListDecisionChance(List<DecisionChanceEntity> listDecisionChance) {
		this.listDecisionChance = listDecisionChance;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	

	private void removeChances() {
		
		
		
		if(this.decision.getListDecisionChance() != null) {
			Node source = logicalFlow.getNode(this.form);
			
			for(int index = 0; index < this.decision.getListDecisionChance().size(); index++) {
				DecisionChanceEntity decisionChance = this.decision.getListDecisionChance().get(index);
				for(Node target : source.getListTarget()) {
					FormEntity formTarget = (FormEntity)target.getElement().getData();
					if(formTarget.getName().equals(decisionChance.getName())) {
						
						boolean remove = true;
						for(DecisionChanceEntity chanceTemp : this.listDecisionChance) {
							if(decisionChance.getId().equals(chanceTemp.getId())) {
								remove = false;
							}
						}
						if(remove) {
							this.decision.getListDecisionChance().remove(decisionChance);							
							ServicesFactory.getInstance().getIvrEditorService().deleteForm(target.getElement());
							index = -1;
						} 
						 
					}
					
				}								
			}
			
		}
		
		
		
	}
	
	
	private void addChances() {
		
		Node source = logicalFlow.getNode(this.form);
		
		Collections.sort(this.listDecisionChance);
		
		for(DecisionChanceEntity chance : this.listDecisionChance) {
			
			boolean insere = true;
			if(this.decision.getListDecisionChance() != null) {
				for(DecisionChanceEntity decisionChance : this.decision.getListDecisionChance()) {
					if(decisionChance.getId().equals(chance.getId())) {						
						insere = false;						
					}
				}
			}
			if(insere) {
								
				FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().getByName(Constants.FORM_TYPE_DECISION_CHANCE);
				
				FormEntity formChance = new FormEntity();
				formChance.setId(Util.getUID());
				if(chance.getCondition() == null) {
					formChance.setDescription(this.decision.getName()+"_Default");
					formChance.setName(this.decision.getName()+"_Default");
				} else {
					formChance.setDescription(this.decision.getName()+"_"+chance.getCondition().getName());
					formChance.setName(this.decision.getName()+"_"+chance.getCondition().getName());
				}
				formChance.setFormType(formType,chance);
				formChance.setPositionX(this.form.getPositionX());
				formChance.setPositionY(this.form.getPositionY());
				
				Element element = new Element(formChance);
				element.setX(formChance.getPositionX());
				element.setY(formChance.getPositionY());
				
				ServicesFactory.getInstance().getIvrEditorService().setEndPoint(formType, element);
				
				ServicesFactory.getInstance().getIvrEditorService().getBean().getModel().addElement(element);
				ServicesFactory.getInstance().getIvrEditorService().getBean().getListForm().add(formChance);
				
				logicalFlow.addNode(element);
				
				
				ServicesFactory.getInstance().getIvrEditorService().connectForm(source.getElement(), element);
			} 
		}		
		this.decision.setListDecisionChance(this.listDecisionChance);
		//atualiza nome dos form choices e ordena as choices
		for(Node nodeChance : source.getListTarget()) {
			
			if(( (FormEntity) nodeChance.getElement().getData()).getFormType().getName().equals(Constants.FORM_TYPE_DECISION_CHANCE) ) {
				for(DecisionChanceEntity decisionChance : this.decision.getListDecisionChance()) {
				
			
					FormEntity formChance = (FormEntity)nodeChance.getElement().getData();
					DecisionChanceEntity chance = (DecisionChanceEntity)formChance.getFormId();
					
					if(decisionChance.getId().equals(chance.getId()) ) { 
						
						if(chance.getCondition() == null) {
							formChance.setDescription(this.decision.getName()+"_Default");
							formChance.setName(this.decision.getName()+"_Default");
						} else {
							formChance.setDescription(this.decision.getName()+"_"+chance.getCondition().getName());
							formChance.setName(this.decision.getName()+"_"+chance.getCondition().getName());
						}
																						
					}
					
				}
			}						
		}
		ServicesFactory.getInstance().getIvrEditorService().alingMenuChoices(source.getElement());
		
	}
	
	public void update(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		
		if(this.listDecisionChance.size() < 2) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Decision","Please, configure at least two Chances, being one of them as Default!");
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		Collections.sort(this.listDecisionChance);
		List<Integer> indexes = new ArrayList<Integer>();
		for(int index = 0; index < this.listDecisionChance.size(); index++) {
			DecisionChanceEntity chance = this.listDecisionChance.get(index);
			if(chance.getCondition() == null) {
				indexes.add(index);	
			}
		}
		if(indexes.size() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Decision","You have to configure one 'Chance Default' (without Condition).");
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} else if(indexes.size() > 1) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Decision","You have to configure only one 'Chance Default' (without Condition).");
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} else if( (indexes.get(0)+1) != this.listDecisionChance.size() ) {
			String text = "The 'Chance Default' at number order "+this.listDecisionChance.get(indexes.get(0)).getOrderNum()+" will invalidate the next Chance, please, correct that.";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Decision",text);
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		removeChances();
		
		addChances();
		
		
		logicalFlow.validateNodes();
		//logicalFlow.align();
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Decision",this.decision.getName()+" - Updated!");
		
		 
		ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("saved", saved);		
		

    }
	
	public void addCondition(ActionEvent event) {
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setAuxiliarPageEditor("/pages/auxiliar/Condition.xhtml");		
		ServicesFactory.getInstance().getConditionEditorService().getBean().setDecisionBean(this);
		
	}
		
	public void addChanceToDecision(ActionEvent event) {
		
		this.collect();
		
		FacesMessage msg = null;
		
		if(this.orderNum == null || this.orderNum.length() == 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Decision","Chance: You have to inform the Number Order");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		List<String> chanceCondition = new ArrayList<String>();
		for(DecisionChanceEntity chance : this.listDecisionChance) {
			if(chance.getCondition()!= null) {
				chanceCondition.add(chance.getCondition().getId());
			}
			if(chance.getOrderNum().equals(Integer.valueOf(this.orderNum))) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Menu","Chance: Number Oder ("+this.orderNum+") already exists");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		
		DecisionChanceEntity chance = new DecisionChanceEntity();
		chance.setId(Util.getUID());
		chance.setOrderNum(Integer.valueOf(this.orderNum));
		
		if(this.conditionId != null && this.conditionId.length() >0) {
			ConditionEntity condition = ServicesFactory.getInstance().getConditionService().get(this.conditionId);
			if(chanceCondition.contains(this.conditionId)) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Menu","Chance: Condition '"+condition.getName()+"' is already assigned to another Chance");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
			chance.setCondition(condition);
		}
		
		this.listDecisionChance.add(chance);
		
		this.conditionId = "";
		this.orderNum = "";
		
	}
	
	public void deleteChance(String id) {
		
		for(int index = 0; index < this.listDecisionChance.size(); index++) {
			DecisionChanceEntity chance = this.listDecisionChance.get(index);
			if(chance.getId().equals(id)) {
				this.listDecisionChance.remove(index);
				return;
			}
		}		
	}

	@Override
	public void save(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
	}


	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}
	
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public List<ConditionEntity> getConditions() {
		this.conditions = ServicesFactory.getInstance().getConditionService().getAll();
		return conditions;
	}

	public void setConditions(List<ConditionEntity> conditions) {
		this.conditions = conditions;
	}


	@Override
	protected void updateExternalsBean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	
	protected void collect() {
		super.collect();
		
		this.conditionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:form_chance_condition_input").toString();
		this.orderNum = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:form_chance_order").toString();
		
		
	}
	
	
	
	
	
	
	
    
}
