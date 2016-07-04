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
import br.com.ims.flow.model.LogicMapEntity;
import br.com.ims.flow.model.MapTypeEntity;
import br.com.ims.flow.model.VersionEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "logicmapEditorView")
@ViewScoped
public class LogicMapEditorBean extends AbstractBean {
     
	private LogicMapEntity logicMap;
	private List<LogicMapEntity> logicMaps;
	private String mapTypeId;
	private List<MapTypeEntity> mapTypes;
	
	
	LogicNodeEditorBean logicNodeBean;
	
	
	private VersionEntity version;
	
	
	public LogicMapEditorBean() {
    	init();
    }
    
    public void init() {
    	this.logicMap = new LogicMapEntity();
    	this.logicMap.setId(Util.getUID());
    	
    	this.insert = true;
    	
    	this.logicNodeBean = null;
    	
    }
    
    public void newMap() {
    	this.logicMap = new LogicMapEntity();
    	this.logicMap.setId(Util.getUID());
    	
    	this.insert = true;
    }
    
	

	public List<MapTypeEntity> getMapTypes() {
		this.mapTypes = ServicesFactory.getInstance().getMapTypeService().getAll();
		return mapTypes;
	}

	public void setMapTypes(List<MapTypeEntity> mapTypes) {
		this.mapTypes = mapTypes;
	}

	public LogicMapEntity getLogicMap() {
		return logicMap;
	}

	public void setLogicMap(LogicMapEntity logicMap) {
		this.insert = false;
		this.logicMap = logicMap;
	}

	public List<LogicMapEntity> getLogicMaps() {
		this.logicMaps = ServicesFactory.getInstance().getLogicMapService().getAll();
		return logicMaps;
	}


	

	public String getMapTypeId() {
		return mapTypeId;
	}

	public void setMapTypeId(String mapTypeId) {
		this.mapTypeId = mapTypeId;
	}

	public void setLogicMaps(List<LogicMapEntity> logicMaps) {
		this.logicMaps = logicMaps;
	}

	public void setLogicNodeBean(LogicNodeEditorBean logicNodeBean) {
		this.logicNodeBean = logicNodeBean;
	}

	public void viewDependence(String id, String name) {
		ServicesFactory.getInstance().getDependenceEditorService().getBean().setObject(Constants.DEPENDENCE_OBJECT_TYPE_LOGIC_MAP,id, name);
	}
	
	private boolean validateFields() {
		if(this.logicMap.getName() == null || this.logicMap.getName().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Map","Please,inform the Name!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.logicMap.getDescription() == null || this.logicMap.getDescription().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Map","Please,inform the Description!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.logicMap.getMethodReference() == null || this.logicMap.getMethodReference().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Map","Please,inform the Method Reference!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		
		LogicMapEntity tmp = ServicesFactory.getInstance().getLogicMapService().getByName(this.logicMap.getName()) ;
		
		if(tmp != null && 
				tmp.getName().equalsIgnoreCase(this.logicMap.getName()) &&
				!tmp.getId().equals(logicMap.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Map","Logic Map with name '"+this.logicMap.getName()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		tmp = ServicesFactory.getInstance().getLogicMapService().getByMethodReference(this.logicMap.getMethodReference()) ;
		
		if(tmp != null && 
				tmp.getMethodReference().equalsIgnoreCase(this.logicMap.getMethodReference()) &&
				!tmp.getId().equals(logicMap.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Map","Logic Map with MethodReference '"+this.logicMap.getMethodReference()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion() == null) {
			ServicesFactory.getInstance().getIvrEditorService().getBean().requestVersion(true);
			return false;
		}
		this.version = ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion();
		
		MapTypeEntity mapType = ServicesFactory.getInstance().getMapTypeService().get(this.mapTypeId);
		this.logicMap.setMapType(mapType);
		
		this.logicMap.setVersionId(this.version.getId());
		return true;
	}

	public void save(ActionEvent event) {

		if(this.validateFields()) {
			if(ServicesFactory.getInstance().getLogicMapService().save(this.logicMap)) {
			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logic Map","Logic Map "+this.logicMap.getName()+" - Saved!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init(); 
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Map","Error on Save "+this.logicMap.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
		
		
    }   
	
	public VersionEntity getVersion() {
		return version;
	}

	public void setVersion(VersionEntity version) {
		this.version = version;
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
			if(ServicesFactory.getInstance().getLogicMapService().update(this.logicMap)) {
			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logic Node - Map","Logic Map "+this.logicMap.getName()+" - Saved!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init(); 
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logic Node - Map","Error on Update "+this.logicMap.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	
	@Override
	protected void updateExternalsBean() {
		if(this.logicNodeBean != null) {
			this.logicNodeBean.setMapId(this.logicMap.getId());
			this.logicNodeBean.getLogicBean().setVersion(this.version);
		}
	}

	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		this.logicMap = ServicesFactory.getInstance().getLogicMapService().get(id);
		
		this.insert= false;
		
	}

	@Override
	public void delete(String id) {
		this.logicMap = ServicesFactory.getInstance().getLogicMapService().get(id);
		this.insert = false;
		if(this.isUsed(id)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Logic Map","You cannot delete Logics Map '"+this.logicMap.getName()+"' because there are dependences.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(ServicesFactory.getInstance().getLogicMapService().delete(this.logicMap)) {
			this.insert = true;
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Condition Map",this.logicMap.getName()+" - Deleted!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Map","Error on Delete "+this.logicMap.getName()+", please contact your support.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
    
}