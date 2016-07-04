package br.com.ims.flow.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.LogicMapEntity;
import br.com.ims.flow.model.LogicNodeOperationEntity;
import br.com.ims.flow.model.LogicNodeValueEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "conditiongroupEditorView")
@ViewScoped
public class LogicOperationEditorBean extends AbstractBean {
     
	
	private LogicNodeOperationEntity nodeOperation;
	private LogicNodeValueEntity nodeOperationValue;
	private List<LogicNodeValueEntity> listNodeOperationValue;
	
	private String operationId;
	
	
	private LogicNodeEditorBean logicNodeBean;
	
	
	public LogicOperationEditorBean() {
    	init();
    }
    
    public void init() {
    	this.nodeOperation = new LogicNodeOperationEntity();
    	this.nodeOperation.setId(Util.getUID());
    	this.nodeOperationValue = new LogicNodeValueEntity();
    	
    	
    	this.listNodeOperationValue = new ArrayList<LogicNodeValueEntity>(); 
    	
    	this.insert = true;
    	
    	this.logicNodeBean = null;
    	operationId = "";
    	
    }
    
    
    
    
	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public LogicNodeEditorBean getLogicNodeBean() {
		return logicNodeBean;
	}

	public void setLogicNodeBean(LogicNodeEditorBean logicNodeBean) {
		this.init();
		this.logicNodeBean = logicNodeBean;
	}

	
	

	public LogicNodeOperationEntity getNodeOperation() {
		return nodeOperation;
	}

	@SuppressWarnings("unchecked")
	public void setNodeOperation(LogicNodeOperationEntity nodeOperation) {
		this.insert = false;
		this.nodeOperation = nodeOperation;
		if(this.nodeOperation.getListLogicNodeValues() ==  null) {
			this.nodeOperation.setListLogicNodeValues(new ArrayList<LogicNodeValueEntity>());
		}
		this.listNodeOperationValue = (List<LogicNodeValueEntity>)((ArrayList<LogicNodeValueEntity>)this.nodeOperation.getListLogicNodeValues()).clone();
    	
	}

	
	
	public LogicNodeValueEntity getNodeOperationValue() {
		return nodeOperationValue;
	}

	public void setNodeOperationValue(LogicNodeValueEntity nodeOperationValue) {
		this.nodeOperationValue = nodeOperationValue;
	}

	

	public List<LogicNodeValueEntity> getListNodeOperationValue() {
		return listNodeOperationValue;
	}

	public void setListNodeOperationValue(List<LogicNodeValueEntity> listNodeOperationValue) {
		this.listNodeOperationValue = listNodeOperationValue;
	}

	

	public void save(ActionEvent event) {
		
		if(this.logicNodeBean != null) {
			
			List<LogicNodeOperationEntity> listEntity = this.logicNodeBean.getListNodeOperation();
			
			for(int index = 0; index < listEntity.size() ; index++) {
				LogicNodeOperationEntity operation = listEntity.get(index);
				if(!operation.getId().equals(this.nodeOperation.getId())) {
					if(operation.getOrderNum().equals(this.nodeOperation.getOrderNum())) {
			
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node Operation","Number Order '"+this.nodeOperation.getOrderNum()+"' already exists on Logic Node "+this.logicNodeBean.getLogicNode().getName());
						 
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
						return;
						
					}
				}
				
			}
			if(this.listNodeOperationValue.size() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node Operation","You must configure at least one value on field 'Values'");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				return;
			}
			for(LogicNodeValueEntity value : this.listNodeOperationValue) {
				if(value.getResultService().equals(1)) {
					if(this.logicNodeBean.getMapId() != null && this.logicNodeBean.getMapId().length() > 0 ) {
						LogicMapEntity map = ServicesFactory.getInstance().getLogicMapService().get(this.logicNodeBean.getMapId());
						if(map.getReturnType().equals("TEXT") &&
								(value.getOperation().equals(">") || 
										value.getOperation().equals("<") ||
										value.getOperation().equals("BETWEEN")) ) {
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node Operation","Return of MAP is TEXT and doesn't work properly with logical test '"+value.getOperation()+"' in VALUE number order '"+value.getOrderNum()+"' ");
							FacesContext.getCurrentInstance().addMessage(null, msg);
							return;
						}
					}
				}
			}
			this.nodeOperation.setListLogicNodeValues(this.listNodeOperationValue);
			
			if(this.insert) {
				this.logicNodeBean.getListNodeOperation().add(this.nodeOperation);
								
			}
			
			init(); 
			
			RequestContext context = RequestContext.getCurrentInstance();
			boolean saved = true;
			context.addCallbackParam("saved", saved);
		}
		
    }   
	
	public void addValue(ActionEvent event) {
		
		this.collect();
		
		for(LogicNodeValueEntity value : this.listNodeOperationValue) {
			if(value.getOrderNum().equals(this.nodeOperationValue.getOrderNum())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node Operaton - Value","Order Number("+this.nodeOperationValue.getOrderNum()+") already exists!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		
		
		if(this.nodeOperationValue.getValue1() == null || this.nodeOperationValue.getValue1().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node Operation - Value","Please, inform Value1!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(!(this.nodeOperationValue.getOperation().equals("=") || this.nodeOperationValue.getOperation().equals("<") || this.nodeOperationValue.getOperation().equals(">") ) 
				&& (this.nodeOperationValue.getValue2() == null || this.nodeOperationValue.getValue2().length() == 0)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node Operation - Value","Please, inform Value2!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		this.nodeOperationValue.setId(Util.getUID());
		this.listNodeOperationValue.add(this.nodeOperationValue);

		this.nodeOperationValue = new LogicNodeValueEntity();
		
	}
	public void removeValue(String valueId) {
		boolean find = false;
		for(int index = 0; index < listNodeOperationValue.size() && !find;index++) {
			LogicNodeValueEntity value = listNodeOperationValue.get(index);
			if(value.getId().equals(valueId)) {
				find = true;
				listNodeOperationValue.remove(index);
			}
		}
	}

	
	protected void collect() {
		String orderNum = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:logicnode_orderNum").toString();
		String orderNumValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_orderNum").toString();
		
		this.operationId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:logicnode_operation_input").toString();
		
		orderNum = orderNum == null || orderNum.length() == 0 ? "0" : orderNum; 
		orderNumValue = orderNumValue == null || orderNumValue.length() == 0 ? "0" : orderNumValue; 
		
		this.nodeOperation.setOperation(this.operationId);
		this.nodeOperation.setOrderNum(Integer.valueOf(orderNum));
		
		this.nodeOperationValue.setOrderNum(Integer.valueOf(orderNumValue));
		this.nodeOperationValue.setOperation(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_operation_input").toString());
		
		this.nodeOperationValue.setOperation(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_operation_input").toString());
		this.nodeOperationValue.setValue1(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value1").toString());
		
		if(this.nodeOperationValue.getOperation().equals("BETWEEN")) {
			this.nodeOperationValue.setValue2(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value2").toString());
			this.nodeOperationValue.setValue3("");
			this.nodeOperationValue.setValue4("");
			this.nodeOperationValue.setValue5("");
			this.nodeOperationValue.setValue6("");
			this.nodeOperationValue.setValue7("");
			this.nodeOperationValue.setValue8("");
			this.nodeOperationValue.setValue9("");
			this.nodeOperationValue.setValue10("");
		} else if(this.nodeOperationValue.getOperation().equals("IN") || this.nodeOperationValue.getOperation().equals("NOT IN")) {			
			this.nodeOperationValue.setValue2(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value2").toString());
			this.nodeOperationValue.setValue3(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value3").toString());
			this.nodeOperationValue.setValue4(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value4").toString());
			this.nodeOperationValue.setValue5(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value5").toString());
			this.nodeOperationValue.setValue6(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value6").toString());
			this.nodeOperationValue.setValue7(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value7").toString());
			this.nodeOperationValue.setValue8(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value8").toString());
			this.nodeOperationValue.setValue9(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value9").toString());
			this.nodeOperationValue.setValue10(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicOperation:operationvalue_value10").toString());
		} else {
			this.nodeOperationValue.setValue2("");
			this.nodeOperationValue.setValue3("");
			this.nodeOperationValue.setValue4("");
			this.nodeOperationValue.setValue5("");
			this.nodeOperationValue.setValue6("");
			this.nodeOperationValue.setValue7("");
			this.nodeOperationValue.setValue8("");
			this.nodeOperationValue.setValue9("");
			this.nodeOperationValue.setValue10("");
		}
	}
	
	
	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
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
	
    
}