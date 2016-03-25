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
import br.com.ims.flow.model.OperationGroupEntity;
import br.com.ims.flow.model.OperationMapEntity;
import br.com.ims.flow.model.OperationParameterEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "operationgroupEditorView")
@ViewScoped
public class OperationGroupEditorBean extends AbstractBean {
     
	
	
	private OperationGroupEntity operationGroup;	
	private OperationParameterEntity operationParameter;
	private List<OperationParameterEntity> listOperationParameter;
	
	
	String mapId;
	
	
	OperationEditorBean operationBean;
	
	List<OperationMapEntity> maps;
	
	public OperationGroupEditorBean() {
    	init();
    }
    
    public void init() {
    	this.operationGroup = new OperationGroupEntity();
    	this.operationGroup.setId(Util.getUID());
    	this.operationParameter = new OperationParameterEntity();
    	this.operationParameter.setId(Util.getUID());
    	this.listOperationParameter = new ArrayList<OperationParameterEntity>();
    	
    	
    	this.insert = true;
    	
    	this.operationBean = null;
    	
    }
    
    
	public OperationEditorBean getOperationBean() {
		return operationBean;
	}

	public void setOperationBean(OperationEditorBean operationBean) {
		this.init();
		this.operationBean = operationBean;
	}

	public OperationGroupEntity getOperationGroup() {
		return operationGroup;
	}

	@SuppressWarnings("unchecked")
	public void setOperationGroup(OperationGroupEntity operationGroup) {
		this.insert = false;
		this.operationGroup = operationGroup;
		if(this.operationGroup.getListOperationParameters() == null) {
    		this.operationGroup.setListOperationParameters(new ArrayList<OperationParameterEntity>());
    	}

    	this.listOperationParameter = (List<OperationParameterEntity>)((ArrayList<OperationParameterEntity>)this.operationGroup.getListOperationParameters()).clone(); 

	}
	
	public List<OperationParameterEntity> getListOperationParameter() {
		return listOperationParameter;
	}

	public void setListOperationParameter(List<OperationParameterEntity> listOperationParameter) {
		this.listOperationParameter = listOperationParameter;
	}

	public OperationParameterEntity getOperationParameter() {
		return operationParameter;
	}

	public void setOperationParameter(OperationParameterEntity operationParameter) {
		this.operationParameter = operationParameter;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
	
    public List<OperationMapEntity> getMaps() {
		this.maps = ServicesFactory.getInstance().getOperationMapService().getAll();
    	return maps;
	}

	public void setMaps(List<OperationMapEntity> maps) {
		
		this.maps = maps;
	}

	public void save(ActionEvent event) {
		
		if(this.operationBean != null) {
			
			List<OperationGroupEntity> listEntity = this.operationBean.getOperationGroups();
			
			for(int index = 0; index < listEntity.size() ; index++) {
				OperationGroupEntity og = listEntity.get(index);
				if(!og.getId().equals(this.operationGroup.getId())) {
					if(og.getOrderNum().equals(this.operationGroup.getOrderNum())) {
			
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Group","Number Order '"+this.operationGroup.getOrderNum()+"' already exists on Operation "+this.operationBean.getOperation().getName());
						 
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
						return;
						
					}
				}
				
			}
			if(this.listOperationParameter.size() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Group","You must configure at least one Parameter on field 'Parameters'");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				return;
			}
			this.operationGroup.setListOperationParameters(this.listOperationParameter);
			
			OperationMapEntity map = ServicesFactory.getInstance().getOperationMapService().get(this.mapId);
			this.operationGroup.setOperationMap(map);
			
			if(this.insert) {
				this.operationBean.getOperationGroups().add(this.operationGroup);				
			}
			
			
			init(); 
			
			RequestContext context = RequestContext.getCurrentInstance();
			boolean saved = true;
			context.addCallbackParam("saved", saved);
		}
		
    }   
	public void addParameter(ActionEvent event) {
		
		String paramName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:operationgroup_paramname").toString();
		String paramValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:operationgroup_paramvalue").toString();
		
		if(paramName == null || paramName.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Group - Parameter","Please, inform the Param Name!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(paramValue == null || paramValue.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Group - Parameter","Please, inform the Param Value!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		OperationParameterEntity op = new OperationParameterEntity();
		op.setId(Util.getUID());
		op.setParamName(paramName);
		op.setParamValue(paramValue);
		op.setOperationGroupId(this.operationGroup.getId());
		op.setVersionId(op.getVersionId());
		this.listOperationParameter.add(op);
		
		
	}
	public void removeParameter(String parameterId) {
		boolean find = false;
		for(int index = 0; index < this.listOperationParameter.size() && !find;index++) {
			OperationParameterEntity op = this.listOperationParameter.get(index);
			if(op.getId().equals(parameterId)) {
				find = true;
				this.listOperationParameter.remove(index);
			}
		}
	}
	
	public void addNewMap(ActionEvent event) {
		
		this.collect();
		ServicesFactory.getInstance().getIvrEditorService().getBean().setUtilPageEditor("/pages/forms/OperationMap.xhtml");
		
		ServicesFactory.getInstance().getOperationMapEditorService().getBean().setOperationGroupBean(this);
	}
	protected void collect() {
		String order = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:operationgroup_order").toString();
		this.operationGroup.setDescription(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:operationgroup_description").toString());
		
		order = order == null || order.length() == 0 ? "0" : order; 
		
		this.operationGroup.setOrderNum(Integer.valueOf(order));
		this.mapId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formComplement:operationgroup_map_input").toString();
		
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