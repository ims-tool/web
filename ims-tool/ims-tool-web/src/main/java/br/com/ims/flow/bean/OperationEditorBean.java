package br.com.ims.flow.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.OperationEntity;
import br.com.ims.flow.model.OperationGroupEntity;
import br.com.ims.flow.model.OperationParameterEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "operationEditorView")
@ViewScoped
public class OperationEditorBean extends AbstractBean {
     
	
	
	private OperationEntity operation;	
	
	
	private List<OperationGroupEntity> operationGroups;
	
	
    public OperationEditorBean() {
    	//init();
    }
    
    @SuppressWarnings("unchecked")
	public void init() {
    	super.init();
    	this.operation = (OperationEntity)this.form.getFormId();

    	if(this.operation.getListOperationGroup() == null) {
    		this.operation.setListOperationGroup(new ArrayList<OperationGroupEntity>());
    	}

    	this.operationGroups = (List<OperationGroupEntity>)((ArrayList<OperationGroupEntity>)this.operation.getListOperationGroup()).clone(); 

    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    	
    	
    }
    
    
    
	public OperationEntity getOperation() {
		return this.operation;
	}

	public void setOperation(OperationEntity operation) {
		this.operation = operation;
	}

	
	public List<OperationGroupEntity> getOperationGroups() {
		return operationGroups;
	}

	public void setOperationGroups(List<OperationGroupEntity> operationGroups) {
		this.operationGroups = operationGroups;
	}

	
	public void save(ActionEvent event) {
		
		
    }   
	
	public boolean isUsed(String id) {
		return false;
	}

	public List<String> paremeterView(String id) {
		List<String> result = new ArrayList<String>();
		for(OperationGroupEntity group : this.operationGroups) {
			if(group.getId().equals(id)) {
				List<OperationParameterEntity> parameters = group.getListOperationParameters();
				if(parameters != null && parameters.size() > 0) {
					
					for(OperationParameterEntity parameter : parameters) {
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
	
	public void addNewGroup(ActionEvent event) {
		
		super.collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setComplementPageEditor("/pages/forms/OperationGroup.xhtml");
		
		ServicesFactory.getInstance().getOperationGroupEditorService().getBean().setOperationBean(this);
	}
	
	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		
		if(this.operationGroups.size() ==0 ){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation","Please, configure at least one Operation Group.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		this.operation.setListOperationGroup(this.operationGroups);
		
		logicalFlow.validateNodes();
		//logicalFlow.align();
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation",this.operation.getName()+" - Updated!");
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("saved", saved);		
		

	}


	@Override
	public void edit(String id) {
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setOtherPageEditor("/pages/auxiliar/ConditionGroup.xhtml");
		
		ServicesFactory.getInstance().getOperationGroupEditorService().getBean().setOperationBean(this);
		
		
		for(OperationGroupEntity group : this.operationGroups) {
			if(group.getId().equals(id)) {
				ServicesFactory.getInstance().getOperationGroupEditorService().getBean().setOperationGroup(group);
			}
		}
		
	}

	public void deleteGroup(String id) {
		for(int index = 0; index < this.operationGroups.size(); index++) {
			OperationGroupEntity group = this.operationGroups.get(index);
			if(group.getId().equals(id)) {
				this.operationGroups.remove(index);
				return;
			}
		}		
		
	}

	@Override
	protected void updateExternalsBean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	
    
}