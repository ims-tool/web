package br.com.ims.flow.bean;

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
import br.com.ims.flow.model.OperationMapEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "operationmapEditorView")
@ViewScoped
public class OperationMapEditorBean extends AbstractBean {
     
	
	
	private OperationMapEntity operationMap;
	private List<OperationMapEntity> operationMaps;
	
	
	
	
	OperationGroupEditorBean operationGroupBean;
	
	
	public OperationMapEditorBean() {
    	init();
    }
    
    public void init() {
    	this.operationMap = new OperationMapEntity();
    	this.operationMap.setId(Util.getUID());
    	this.insert = true;
    	
    	this.operationGroupBean = null;
    	
    }
    
    
	

	public OperationMapEntity getOperationMap() {
		return operationMap;
	}

	public void setOperationMap(OperationMapEntity operationMap) {
		this.insert = false;
		this.operationMap = operationMap;
	}

	public List<OperationMapEntity> getOperationMaps() {
		this.operationMaps = ServicesFactory.getInstance().getOperationMapService().getAll();
		return operationMaps;
	}

	public void setOperationMaps(List<OperationMapEntity> operationMaps) {
		this.operationMaps = operationMaps;
	}

	public OperationGroupEditorBean getOperationGroupBean() {
		return operationGroupBean;
	}

	public void setOperationGroupBean(OperationGroupEditorBean operationGroupBean) {
		this.operationGroupBean = operationGroupBean;
	}

	private boolean validateFields() {
		if(this.operationMap.getName() == null || this.operationMap.getName().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Map","Please,inform the Name!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.operationMap.getDescription() == null || this.operationMap.getDescription().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Map","Please,inform the Description!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.operationMap.getMethodReference() == null || this.operationMap.getMethodReference().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Map","Please,inform the Method Reference!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		
		OperationMapEntity tmp = ServicesFactory.getInstance().getOperationMapService().getByName(this.operationMap.getName()) ;
		
		if(tmp != null && 
				tmp.getName().equalsIgnoreCase(this.operationMap.getName()) &&
				!tmp.getId().equals(operationMap.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Map","Operation Map with name '"+this.operationMap.getName()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		tmp = ServicesFactory.getInstance().getOperationMapService().getByMethodReference(this.operationMap.getMethodReference()) ;
		
		if(tmp != null && 
				tmp.getMethodReference().equalsIgnoreCase(this.operationMap.getMethodReference()) &&
				!tmp.getId().equals(operationMap.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Map","Operation Map with MethodReference '"+this.operationMap.getMethodReference()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion() == null) {
			ServicesFactory.getInstance().getIvrEditorService().getBean().requestVersion(true);
			return false;
		}
		this.operationMap.setVersionId(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion().getId());
		return true;
	}

	
	public void save(ActionEvent event) {

		if(validateFields()) {
			if(ServicesFactory.getInstance().getOperationMapService().save(this.operationMap)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation Group - Map","Operation Map "+this.operationMap.getName()+" - Saved!");
				
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init(); 
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Map","Error on Save "+this.operationMap.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}	

		}
		
    }   
	
	
	
	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return ServicesFactory.getInstance().getConditionMapService().isUsed(id);
		
	}

	@Override
	public void update(ActionEvent event) {
		if(validateFields()) {
			if(ServicesFactory.getInstance().getOperationMapService().update(this.operationMap)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation Group - Map","Operation Map "+this.operationMap.getName()+" - Updated!");
				
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init(); 
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Map","Error on Update "+this.operationMap.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}	

		}
		
	}

	
	@Override
	protected void updateExternalsBean() {
		if(this.operationGroupBean != null) {
			this.operationGroupBean.setMapId(this.operationMap.getId());
		}
	}

	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		this.operationMap = ServicesFactory.getInstance().getOperationMapService().get(id);
		this.insert = false;
		
	}

	@Override
	public void delete(String id) {
		this.operationMap = ServicesFactory.getInstance().getOperationMapService().get(id);
		if(ServicesFactory.getInstance().getOperationMapService().delete(this.operationMap)) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation Map",this.operationMap.getName()+" - Deleted!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			updateExternalsBean();
			
			init();
			
			RequestContext context = RequestContext.getCurrentInstance();
			boolean saved = true;
			context.addCallbackParam("saved", saved);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Map","Error on Delete "+this.operationMap.getName()+", please contact your support.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void viewDependence(String id, String name) {
		ServicesFactory.getInstance().getDependenceEditorService().getBean().setObject(Constants.DEPENDENCE_OBJECT_TYPE_OPERATION_MAP,id, name);
	}
	public void newMap() {
    	this.operationMap = new OperationMapEntity();
    	this.operationMap.setId(Util.getUID());
    	
    	this.insert = true;
    }
	
    
}