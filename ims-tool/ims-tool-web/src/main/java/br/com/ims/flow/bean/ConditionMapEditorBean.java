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
import br.com.ims.flow.model.ConditionMapEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "conditionmapEditorView")
@ViewScoped
public class ConditionMapEditorBean extends AbstractBean {
     
	
	
	private ConditionMapEntity conditionMap;
	private List<ConditionMapEntity> conditionMaps;
	
	
	
	
	ConditionGroupEditorBean conditionGroupBean;
	
	
	public ConditionMapEditorBean() {
    	init();
    }
    
    public void init() {
    	this.conditionMap = new ConditionMapEntity();
    	this.conditionMap.setId(Util.getUID());
    	
    	this.insert = true;
    	
    	this.conditionGroupBean = null;
    	
    }
    
    public void newMap() {
    	this.conditionMap = new ConditionMapEntity();
    	this.conditionMap.setId(Util.getUID());
    	
    	this.insert = true;
    }
    
	

	public ConditionMapEntity getConditionMap() {
		return conditionMap;
	}

	public void setConditionMap(ConditionMapEntity conditionMap) {
		this.insert = false;
		this.conditionMap = conditionMap;
	}

	public List<ConditionMapEntity> getConditionMaps() {
		this.conditionMaps = ServicesFactory.getInstance().getConditionMapService().getAll();
		return conditionMaps;
	}

	public void setConditionMaps(List<ConditionMapEntity> conditionMaps) {
		this.conditionMaps = conditionMaps;
	}

	public ConditionGroupEditorBean getConditionGroupBean() {
		return conditionGroupBean;
	}

	public void setConditionGroupBean(ConditionGroupEditorBean conditionGroupBean) {
		this.conditionGroupBean = conditionGroupBean;
	}

	public void viewDependence(String id, String name) {
		ServicesFactory.getInstance().getDependenceEditorService().getBean().setObject(Constants.DEPENDENCE_OBJECT_TYPE_CONDITION_MAP,id, name);
	}
	
	private boolean validateFields() {
		if(this.conditionMap.getName() == null || this.conditionMap.getName().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Map","Please,inform the Name!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.conditionMap.getDescription() == null || this.conditionMap.getDescription().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Map","Please,inform the Description!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.conditionMap.getMethodReference() == null || this.conditionMap.getMethodReference().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Map","Please,inform the Method Reference!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		
		ConditionMapEntity tmp = ServicesFactory.getInstance().getConditionMapService().getByName(this.conditionMap.getName()) ;
		
		if(tmp != null && 
				tmp.getName().equalsIgnoreCase(this.conditionMap.getName()) &&
				!tmp.getId().equals(conditionMap.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Map","Condition Map with name '"+this.conditionMap.getName()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		tmp = ServicesFactory.getInstance().getConditionMapService().getByMethodReference(this.conditionMap.getMethodReference()) ;
		
		if(tmp != null && 
				tmp.getMethodReference().equalsIgnoreCase(this.conditionMap.getMethodReference()) &&
				!tmp.getId().equals(conditionMap.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Map","Condition Map with MethodReference '"+this.conditionMap.getMethodReference()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion() == null) {
			ServicesFactory.getInstance().getIvrEditorService().getBean().requestVersion(true);
			return false;
		}
		this.conditionMap.setVersionId(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion().getId());
		return true;
	}

	
	public void save(ActionEvent event) {

		if(this.validateFields()) {
			if(ServicesFactory.getInstance().getConditionMapService().save(this.conditionMap)) {
			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Condition Map","Condition Map "+this.conditionMap.getName()+" - Saved!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init(); 
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Map","Error on Save "+this.conditionMap.getName()+", please contact your support.");
				 
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
		// TODO Auto-generated method stub
		if(this.validateFields()) {
			if(ServicesFactory.getInstance().getConditionMapService().update(this.conditionMap)) {
			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Condition Group - Map","Condition Map "+this.conditionMap.getName()+" - Saved!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init(); 
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Map","Error on Update "+this.conditionMap.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	
	@Override
	protected void updateExternalsBean() {
		if(this.conditionGroupBean != null) {
			this.conditionGroupBean.setMapId(this.conditionMap.getId());
		}
	}

	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		this.conditionMap = ServicesFactory.getInstance().getConditionMapService().get(id);
		
		this.insert= false;
		
	}

	@Override
	public void delete(String id) {
		this.conditionMap = ServicesFactory.getInstance().getConditionMapService().get(id);
		if(ServicesFactory.getInstance().getConditionMapService().delete(this.conditionMap)) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Condition Map",this.conditionMap.getName()+" - Deleted!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			updateExternalsBean();
			
			init();
			
			RequestContext context = RequestContext.getCurrentInstance();
			boolean saved = true;
			context.addCallbackParam("saved", saved);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Map","Error on Delete "+this.conditionMap.getName()+", please contact your support.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
    
}