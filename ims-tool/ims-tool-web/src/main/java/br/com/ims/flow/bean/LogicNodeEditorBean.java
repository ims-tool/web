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
import br.com.ims.flow.model.LogicNodeEntity;
import br.com.ims.flow.model.LogicNodeOperationEntity;
import br.com.ims.flow.model.LogicNodeParameterEntity;
import br.com.ims.flow.model.TagEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "logicnodeEditorView")
@ViewScoped
public class LogicNodeEditorBean extends AbstractBean {
     
	
	
	private LogicNodeEntity logicNode;
	private LogicNodeOperationEntity nodeOperation;
	private LogicNodeParameterEntity nodeParameter;
	
	private List<LogicNodeOperationEntity> listNodeOperation;
	private List<LogicNodeParameterEntity> listNodeParameter;
	
	String mapId;
	
	String tagTrueId;
	String tagFalseId;
	
	String gotoTrue;
	String gotoFalse;
	
	LogicEditorBean logicBean;
	
	List<LogicMapEntity> maps;
	List<TagEntity> tags;
	
	public LogicNodeEditorBean() {
    	init();
    }
    
    public void init() {
    	this.logicNode = new LogicNodeEntity();
    	this.logicNode.setId(Util.getUID());

    	this.nodeOperation = new LogicNodeOperationEntity();
    	this.nodeParameter = new LogicNodeParameterEntity();

    	
    	this.listNodeOperation = new ArrayList<LogicNodeOperationEntity>();
    	this.listNodeParameter = new ArrayList<LogicNodeParameterEntity>();
    	
    	this.insert = true;
    	
    	this.logicBean = null;
    	
    }
    
   public LogicEditorBean getLogicBean() {
		return logicBean;
	}

	public void setLogicBean(LogicEditorBean logicBean) {
		this.init();
		this.logicBean = logicBean;
	}

	public LogicNodeEntity getLogicNode() {
		return this.logicNode;
	}

	@SuppressWarnings("unchecked")
	public void setLogicNode(LogicNodeEntity logicNode) {
		this.insert = false;
		this.logicNode = logicNode;
		this.mapId = logicNode.getLogicMap().getId();
		
		if(this.logicNode.getListParameter() == null) {
    		this.logicNode.setListParameter(new ArrayList<LogicNodeParameterEntity>());
    	}

    	this.listNodeParameter = (List<LogicNodeParameterEntity>)((ArrayList<LogicNodeParameterEntity>)this.logicNode.getListParameter()).clone();
    	
    	if(this.logicNode.getListOperation() == null) {
    		this.logicNode.setListOperation(new ArrayList<LogicNodeOperationEntity>());
    	}

    	this.listNodeOperation = (List<LogicNodeOperationEntity>)((ArrayList<LogicNodeOperationEntity>)this.logicNode.getListOperation()).clone();
    	
	}
	
		public LogicNodeParameterEntity getNodeParameter() {
		return nodeParameter;
	}

	public void setNodeParameter(LogicNodeParameterEntity nodeParameter) {
		this.nodeParameter = nodeParameter;
	}


	public LogicNodeOperationEntity getNodeOperation() {
		return nodeOperation;
	}

	public void setNodeOperation(LogicNodeOperationEntity nodeOperation) {
		this.nodeOperation = nodeOperation;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getTagTrueId() {
		return tagTrueId;
	}

	public void setTagTrueId(String tagTrueId) {
		this.tagTrueId = tagTrueId;
	}

	public String getTagFalseId() {
		return tagFalseId;
	}

	public void setTagFalseId(String tagFalseId) {
		this.tagFalseId = tagFalseId;
	}
	
	public String getGotoTrue() {
		return gotoTrue;
	}

	public void setGotoTrue(String gotoTrue) {
		this.gotoTrue = gotoTrue;
	}

	public String getGotoFalse() {
		return gotoFalse;
	}

	public void setGotoFalse(String gotoFalse) {
		this.gotoFalse = gotoFalse;
	}

	public List<LogicMapEntity> getMaps() {
		this.maps = ServicesFactory.getInstance().getLogicMapService().getAll();
    	return maps;
	}

	public void setMaps(List<LogicMapEntity> maps) {
		
		this.maps = maps;
	}

	
	public List<TagEntity> getTags() {
		this.tags = ServicesFactory.getInstance().getTagService().getAll();
		return tags;
	}

	public void setTags(List<TagEntity> tags) {
		this.tags = tags;
	}

	
	
	public List<LogicNodeParameterEntity> getListNodeParameter() {
		return listNodeParameter;
	}

	public void setListNodeParameter(List<LogicNodeParameterEntity> listNodeParameter) {
		this.listNodeParameter = listNodeParameter;
	}

	

	public List<LogicNodeOperationEntity> getListNodeOperation() {
		return listNodeOperation;
	}

	public void setListNodeOperation(List<LogicNodeOperationEntity> listNodeOperation) {
		this.listNodeOperation = listNodeOperation;
	}

	public void save(ActionEvent event) {
		
		if(this.logicBean != null) {
			
			List<LogicNodeEntity> listEntity = this.logicBean.getLogicNodes();
			
			for(int index = 0; index < listEntity.size() ; index++) {
				LogicNodeEntity ln = listEntity.get(index);
				if(!ln.getId().equals(this.logicNode.getId())) {
					if(ln.getOrderNum().equals(this.logicNode.getOrderNum())) {
			
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node","Number Order '"+this.logicNode.getOrderNum()+"' already exists on Logic "+this.logicBean.getLogic().getName());
						 
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
						return;
						
					}
				}
				
			}
			if(this.listNodeOperation.size() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node","You must configure at least one 'Operation'");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				return;
			}
			LogicMapEntity map = ServicesFactory.getInstance().getLogicMapService().get(this.mapId);
			
			this.logicNode.setLogicMap(map);
			
			this.logicNode.setListParameter(this.listNodeParameter);
			this.logicNode.setListOperation(this.listNodeOperation);
			
			if(this.insert) {
				this.logicBean.getLogicNodes().add(this.logicNode);
								
			}
			
			
			init(); 
			
			RequestContext context = RequestContext.getCurrentInstance();
			boolean saved = true;
			context.addCallbackParam("saved", saved);
		}
		
    }   
	public void addParameter(ActionEvent event) {
		
		String paramName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_paramname").toString();
		String paramValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_paramvalue").toString();
		
		if(paramName == null || paramName.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node - Parameter","Please, inform the Param Name!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(paramValue == null || paramValue.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node - Parameter","Please, inform the Param Value!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		LogicNodeParameterEntity ln = new LogicNodeParameterEntity();
		ln.setId(Util.getUID());
		ln.setParamName(paramName);
		ln.setParamValue(paramName);
		ln.setLogicNodeId(this.logicNode.getId());
		ln.setVersionId(this.logicNode.getVersionId());
		this.listNodeParameter.add(ln);
		
		
	}
	public void removeParameter(String parameterId) {
		
		boolean find = false;
		for(int index = 0; index < this.listNodeParameter.size() && !find;index++) {
			LogicNodeParameterEntity ln = this.listNodeParameter.get(index);
			if(ln.getId().equals(parameterId)) {
				find = true;
				this.listNodeParameter.remove(index);
			}
		}
	}
	
	public void addOperation(ActionEvent event) { 
		
		this.collect();
	}
	public void removeOperation(String valueId) {
		boolean find = false;
		for(int index = 0; index < listNodeOperation.size() && !find;index++) {
			LogicNodeOperationEntity no = listNodeOperation.get(index);
			if(no.getId().equals(valueId)) {
				find = true;
				listNodeOperation.remove(index);
			}
		}
	}
	public void addValue(ActionEvent event) {
		this.collect();
		ServicesFactory.getInstance().getIvrEditorService().getBean().setUtilPageEditor("/pages/logic/LogicMap.xhtml");
		
		//ServicesFactory.getInstance().getConditionMapEditorService().getBean().setConditionGroupBean(this);
		//ServicesFactory.getInstance().getConditionMapEditorService().getBean().setVersion(this.conditionBean.getVersion());
		
	}
	public void removeValue(String valueId) {
		
	}

	public void addNewMap(ActionEvent event) {
		
		this.collect();
		
		//ServicesFactory.getInstance().getConditionMapEditorService().getBean().setConditionGroupBean(this);
		//ServicesFactory.getInstance().getConditionMapEditorService().getBean().setVersion(this.conditionBean.getVersion());
	}
	protected void collect() {
		String order = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicNode:logicnode_order").toString();
		this.logicNode.setDescription(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicNode:logicnode_description").toString());
		
		order = order == null || order.length() == 0 ? "0" : order; 
		
		this.logicNode.setOrderNum(Integer.valueOf(order));
		this.mapId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicNode:logicnode_map_input").toString();
		this.tagTrueId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicNode:logicnode_tagTrue_input").toString();
		this.tagFalseId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicNode:logicnode_tagFalse_input").toString();
		
		
		order = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formLogicNode:logicnode_orderNum").toString();
		
		order = order == null || order.length() == 0 ? "0" : order;
		/*
		this.conditionValue.setOrderNum(Integer.valueOf(order	));
		this.conditionValue.setOperation(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_operation_input").toString());
		this.conditionValue.setValue1(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value1").toString());
		
		if(this.conditionValue.getOperation().equals("BETWEEN")) {
			this.conditionValue.setValue2(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value2").toString());
			this.conditionValue.setValue3("");
			this.conditionValue.setValue4("");
			this.conditionValue.setValue5("");
			this.conditionValue.setValue6("");
			this.conditionValue.setValue7("");
			this.conditionValue.setValue8("");
			this.conditionValue.setValue9("");
			this.conditionValue.setValue10("");
		} else if(this.conditionValue.getOperation().equals("IN") || this.conditionValue.getOperation().equals("NOT IN")) {			
			this.conditionValue.setValue2(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value2").toString());
			this.conditionValue.setValue3(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value3").toString());
			this.conditionValue.setValue4(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value4").toString());
			this.conditionValue.setValue5(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value5").toString());
			this.conditionValue.setValue6(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value6").toString());
			this.conditionValue.setValue7(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value7").toString());
			this.conditionValue.setValue8(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value8").toString());
			this.conditionValue.setValue9(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value9").toString());
			this.conditionValue.setValue10(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_value10").toString());
		} else {
			this.conditionValue.setValue2("");
			this.conditionValue.setValue3("");
			this.conditionValue.setValue4("");
			this.conditionValue.setValue5("");
			this.conditionValue.setValue6("");
			this.conditionValue.setValue7("");
			this.conditionValue.setValue8("");
			this.conditionValue.setValue9("");
			this.conditionValue.setValue10("");
		}*/
	}
	
	public void addNewTag(ActionEvent event) {
		
		this.collect();
		
		//ServicesFactory.getInstance().getTagEditorService().getBean().setConditionGroupBean(this);
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