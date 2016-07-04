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

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.LogicEntity;
import br.com.ims.flow.model.LogicNodeEntity;
import br.com.ims.flow.model.LogicNodeOperationEntity;
import br.com.ims.flow.model.LogicNodeParameterEntity;
import br.com.ims.flow.model.LogicNodeValueEntity;
import br.com.ims.flow.model.VersionEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "logicEditorView")
@ViewScoped
public class LogicEditorBean extends AbstractBean {
    
	private LogicEntity logic;	
	
	
	private List<LogicEntity> logics;
	private List<LogicNodeEntity> logicNodes;
	
	
	private TransferEditorBean transferBean;
	
	private VersionEntity version;
	
	
    public LogicEditorBean() {
    	init();
    }
    
    @SuppressWarnings("unchecked")
	public void init() {
    	this.logic = new LogicEntity();
    	this.logic.setId(Util.getUID());
    	if(this.logic.getListLogicNode() == null) {
    		this.logic.setListLogicNode(new ArrayList<LogicNodeEntity>());
    	}

    	this.logicNodes = (List<LogicNodeEntity>)((ArrayList<LogicNodeEntity>)this.logic.getListLogicNode()).clone(); 
    	
    	
    	this.insert = true;
    	
    	this.transferBean = null;
    }
    
	public LogicEntity getLogic() {
		return logic;
	}

	public void setLogic(LogicEntity logic) {
		this.logic = logic;
	}
	
	

	public List<LogicEntity> getLogics() {
		this.logics = ServicesFactory.getInstance().getLogicService().getAll();
		return logics;
	}

	public void setLogics(List<LogicEntity> logics) {
		this.logics = logics;
	}

	
	
	public List<LogicNodeEntity> getLogicNodes() {
		return logicNodes;
	}

	public void setLogicNodes(List<LogicNodeEntity> logicNodes) {
		this.logicNodes = logicNodes;
	}

	
	public TransferEditorBean getTransferBean() {
		return transferBean;
	}

	public void setTransferBean(TransferEditorBean transferBean) {
		this.transferBean = transferBean;
	}
	
	public VersionEntity getVersion() {
		return version;
	}

	public void setVersion(VersionEntity version) {
		this.version = version;
	}
	
	protected void updateExternalsBean() {
    
		if(this.transferBean != null) {
			//this.transferBean.setConditionId(this.condition.getId());
		}
		
    }
	@SuppressWarnings("unchecked")
	public void newLogic() {
		this.logic = new LogicEntity();
    	this.logic.setId(Util.getUID());
    	if(this.logic.getListLogicNode() == null) {
    		this.logic.setListLogicNode(new ArrayList<LogicNodeEntity>());
    	}

    	this.logicNodes = (List<LogicNodeEntity>)((ArrayList<LogicNodeEntity>)this.logic.getListLogicNode()).clone(); 
    	
    	this.insert = true;
	}
	private boolean validateFields() {
		
		if(this.logicNodes.size() ==0 ){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic","Please, configure at least one Logic Node.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		
		LogicEntity tmp = ServicesFactory.getInstance().getLogicService().getByName(this.logic.getName()) ;
		
		if(tmp != null && 
				tmp.getName().equalsIgnoreCase(this.logic.getName()) &&
				!tmp.getId().equals(logic.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Logic","Logic with name '"+this.logic.getName()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion() == null) {
			ServicesFactory.getInstance().getIvrEditorService().getBean().requestVersion(true);
			return false;
		}
		this.version = ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion();
		
		this.logic.setListLogicNode(this.logicNodes);
		this.logic.setVersionId(this.version.getId());
		return true;
	}
	
	
	
	public void save(ActionEvent event) {
		
		if(validateFields()) {
		
			
			if(ServicesFactory.getInstance().getLogicService().save(this.logic)) {
			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logic",this.logic.getName()+" - Saved!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				
				updateExternalsBean();
				
				
				init();
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic","Error on Save "+this.logic.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
    }   
	

	public boolean isUsed(String id) {
		return ServicesFactory.getInstance().getConditionService().isUsed(id) ;
	}

	public List<String> paremeterView(String id) {
		List<String> result = new ArrayList<String>();
		for(LogicNodeEntity node : logicNodes) {
			if(node.getId().equals(id)) {
				List<LogicNodeParameterEntity> parameters = node.getListParameter();
				if(parameters != null && parameters.size() > 0) {
					
					for(LogicNodeParameterEntity parameter : parameters) {
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

	public List<String> operationView(String id) {
		List<String> result = new ArrayList<String>();
		
		for(LogicNodeEntity node : logicNodes) {
			if(node.getId().equals(id)) {
				List<LogicNodeOperationEntity> operations = node.getListOperation();
				if(operations != null && operations.size() > 0) {
					Collections.sort(operations);
					for(LogicNodeOperationEntity operation : operations) {
						String linha = operation.getOperation()+":{ ";
						List<LogicNodeValueEntity> values = operation.getListLogicNodeValues(); 
						Collections.sort(values);
						if(values != null && values.size() > 0) {
							for(LogicNodeValueEntity value : values) {
								if(value.getResultService() == 1) {
									linha+= "["+node.getLogicMap().getMethodReference();
								} else {
									linha+= "["+value.getResultContext();
								}
								linha+= " ("+value.getOperation()+") "+value.getValue1();
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
								linha+="]";
							}
						}
						linha+="}";
						result.add(linha);
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
		
		this.logic.setName(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogic:logic_name").toString());
		this.logic.setDescription(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogic:logic_description").toString());
	}
	
	public void addNewNode(ActionEvent event) {
		
		collect();
		ServicesFactory.getInstance().getLogicNodeEditorService().getBean().setLogicBean(this);
		
	}
	
	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		if(validateFields()) {
		
			
			if(ServicesFactory.getInstance().getLogicService().update(this.logic)) {
			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logic",this.logic.getName()+" - Saved!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				
				updateExternalsBean();
				
				
				init();
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic","Error on Save "+this.logic.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}
	public void editNode(String id) {
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setOtherPageEditor("/pages/auxiliar/ConditionGroup.xhtml");
		
		//ServicesFactory.getInstance().getConditionGroupEditorService().getBean().setConditionBean(this);
		
		for(LogicNodeEntity node : this.logicNodes) {
			if(node.getId().equals(id)) {
				//ServicesFactory.getInstance().getConditionGroupEditorService().getBean().setConditionGroup(group);
			}
		}
	}
	public void deleteNode(String id) {
		for(int index = 0; index < this.logicNodes.size(); index++) {
			LogicNodeEntity node = this.logicNodes.get(index);
			if(node.getId().equals(id)) {
				node.setDelete(true);
				return;
			}
		}
	}
	
	public void addValue(String id) {
		for(int index = 0; index < this.logicNodes.size(); index++) {
			LogicNodeEntity node = this.logicNodes.get(index);
			if(node.getId().equals(id)) {
				node.setDelete(true);
				return;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void edit(String id) {
		this.logic = ServicesFactory.getInstance().getLogicService().get(id);
		this.logicNodes = (List<LogicNodeEntity>)((ArrayList<LogicNodeEntity>)this.logic.getListLogicNode()).clone();
		this.insert= false;
		
	}

	@Override
	public void delete(String id) {
		this.logic = ServicesFactory.getInstance().getLogicService().get(id);
		this.insert = false;
		if(this.isUsed(id)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Logic","You cannot delete Logic '"+this.logic.getName()+"' because there are dependences.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(ServicesFactory.getInstance().getLogicService().delete(this.logic)) {
			
			this.insert = true;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logic",this.logic.getName()+" - Deleted!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic","Error on Delete "+this.logic.getName()+", please contact your support.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	public void viewDependence(String id, String name) {
		ServicesFactory.getInstance().getDependenceEditorService().getBean().setObject(Constants.DEPENDENCE_OBJECT_TYPE_CONDITION,id, name);
	}
	
    
}