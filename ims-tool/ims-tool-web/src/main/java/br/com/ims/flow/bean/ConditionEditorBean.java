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

import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.ConditionGroupEntity;
import br.com.ims.flow.model.ConditionParameterEntity;
import br.com.ims.flow.model.ConditionValueEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "conditionEditorView")
@ViewScoped
public class ConditionEditorBean extends AbstractBean {
     
	
	
	private ConditionEntity condition;	
	
	
	private List<ConditionEntity> conditions;
	private List<ConditionGroupEntity> conditionGroups;
	
	
	private MenuEditorBean menuBean;
	private PromptEditorBean promptBean;
	private TransferEditorBean transferBean;
	private DecisionEditorBean decisionBean;
	
    public ConditionEditorBean() {
    	init();
    }
    
    @SuppressWarnings("unchecked")
	public void init() {
    	this.condition = new ConditionEntity();
    	if(this.condition.getListConditionGroup() == null) {
    		this.condition.setListConditionGroup(new ArrayList<ConditionGroupEntity>());
    	}

    	this.conditionGroups = (List<ConditionGroupEntity>)((ArrayList<ConditionGroupEntity>)this.condition.getListConditionGroup()).clone(); 
    	
    	
    	this.insert = true;
    	
    	this.menuBean = null;
    	this.promptBean = null;
    	this.transferBean = null;
    	this.decisionBean = null;
    }
    
    
    
	public ConditionEntity getCondition() {
		return condition;
	}

	public void setCondition(ConditionEntity condition) {
		this.condition = condition;
	}

	
	public List<ConditionEntity> getConditions() {
		this.conditions = ServicesFactory.getInstance().getConditionService().getAll();
		return conditions;
	}

	
	public void setConditions(List<ConditionEntity> conditions) {
		this.conditions = conditions;
	}

	public List<ConditionGroupEntity> getConditionGroups() {
		return conditionGroups;
	}

	public void setConditionGroups(List<ConditionGroupEntity> conditionGroups) {
		this.conditionGroups = conditionGroups;
	}

	public MenuEditorBean getMenuBean() {
		return menuBean;
	}

	public void setMenuBean(MenuEditorBean menuBean) {
		this.menuBean = menuBean;
	}
	
	

	public PromptEditorBean getPromptBean() {
		return promptBean;
	}

	public void setPromptBean(PromptEditorBean promptBean) {
		this.promptBean = promptBean;
	}
	
	
	public TransferEditorBean getTransferBean() {
		return transferBean;
	}

	public void setTransferBean(TransferEditorBean transferBean) {
		this.transferBean = transferBean;
	}
	
	
	public DecisionEditorBean getDecisionBean() {
		return decisionBean;
	}

	public void setDecisionBean(DecisionEditorBean decisionBean) {
		this.decisionBean = decisionBean;
	}

	protected void updateExternalsBean() {
    
		
		if(this.menuBean != null) {
			this.menuBean.setConditionId(this.condition.getId());
		}
		if(this.promptBean != null) {
			this.promptBean.setConditionId(this.condition.getId());
		}
		if(this.transferBean != null) {
			this.transferBean.setConditionId(this.condition.getId());
		}
		if(this.decisionBean != null) {
			this.decisionBean.setConditionId(this.condition.getId());
		}
    }
	
	public void save(ActionEvent event) {
		
		
		if(ServicesFactory.getInstance().getConditionService().getByName(this.condition.getName()) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition","Condition with name '"+this.condition.getName()+"' already exists.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(this.conditionGroups.size() ==0 ){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition","Please, configure at least one Condition Group.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		this.condition.setListConditionGroup(this.conditionGroups);
		
		ServicesFactory.getInstance().getConditionService().save(this.condition);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Condition",this.condition.getName()+" - Saved!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		
		updateExternalsBean();
		
		
		init();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);
		
    }   
	

	public boolean isUsed(String id) {
		return ServicesFactory.getInstance().getPromptService().isUsed(id) ;
	}

	public List<String> paremeterView(String id) {
		List<String> result = new ArrayList<String>();
		for(ConditionGroupEntity group : conditionGroups) {
			if(group.getId().equals(id)) {
				List<ConditionParameterEntity> parameters = group.getListConditionParameters();
				if(parameters != null && parameters.size() > 0) {
					
					for(ConditionParameterEntity parameter : parameters) {
						result.add("Name:"+parameter.getParamName()+", Value:"+parameter.getParamValue());
					}
				}
			}
		}
		
		if(result.size() == 0) {
			result.add("N/A");
		}
		
		
		return result;
	}
	
	public List<String> valueView(String id) {
		List<String> result = new ArrayList<String>();
		if(condition.getListConditionGroup() != null) {
			for(ConditionGroupEntity group : condition.getListConditionGroup()) {
				if(group.getId().equals(id)) {
					List<ConditionValueEntity> values = group.getListConditionValues();
					if(values != null && values.size() > 0) {
						Collections.sort(values);
						
						for(ConditionValueEntity value : values) {
							String linha = group.getConditionMap().getMethodReference()+" ("+value.getOperation()+") "+value.getValue1();
							if(value.getValue2() != null && value.getValue2().length() > 0) {
								linha+=", "+value.getValue2();
							}
							if(value.getValue3() != null && value.getValue3().length() > 0) {
								linha+=", "+value.getValue3();
							}
							if(value.getValue4() != null && value.getValue4().length() > 0) {
								linha+=", "+value.getValue4();
							}
							if(value.getValue5() != null && value.getValue5().length() > 0) {
								linha+=", "+value.getValue5();
							}
							if(value.getValue6() != null && value.getValue6().length() > 0) {
								linha+=", "+value.getValue6();
							}
							if(value.getValue7() != null && value.getValue7().length() > 0) {
								linha+=", "+value.getValue7();
							}
							if(value.getValue8() != null && value.getValue8().length() > 0) {
								linha+=", "+value.getValue8();
							}
							if(value.getValue9() != null && value.getValue9().length() > 0) {
								linha+=", "+value.getValue9();
							}
							if(value.getValue10() != null && value.getValue10().length() > 0) {
								linha+=", "+value.getValue10();
							}
							result.add(linha);
						}
						
					}
				}
			}
		}
		if(result.size() == 0) {
			result.add("N/A");
		}
		return result;
	}
	
	protected void collect() {
		this.condition.setName(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:auxiliar_condition_name").toString());
		this.condition.setDescription(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAuxiliar:auxiliar_condition_description").toString());
	}
	
	public void addNewGroup(ActionEvent event) {
		
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setOtherPageEditor("/pages/auxiliar/ConditionGroup.xhtml");
		
		ServicesFactory.getInstance().getConditionGroupEditorService().getBean().setConditionBean(this);
	}
	
	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void edit(String id) {
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setOtherPageEditor("/pages/auxiliar/ConditionGroup.xhtml");
		
		ServicesFactory.getInstance().getConditionGroupEditorService().getBean().setConditionBean(this);
		
		
		for(ConditionGroupEntity group : this.conditionGroups) {
			if(group.getId().equals(id)) {
				ServicesFactory.getInstance().getConditionGroupEditorService().getBean().setConditionGroup(group);
			}
		}
		
	}

	@Override
	public void delete(String id) {
		for(int index = 0; index < this.conditionGroups.size(); index++) {
			ConditionGroupEntity group = this.conditionGroups.get(index);
			if(group.getId().equals(id)) {
				this.conditionGroups.remove(index);
				return;
			}
		}		
		
	}
	
    
}