package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

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

	

	
	public void save(ActionEvent event) {

		this.operationMaps = this.getOperationMaps();
		for(OperationMapEntity map : this.operationMaps) {
			if(!map.getId().equals(this.operationMap.getId()) &&
					map.getName().equalsIgnoreCase(this.operationMap.getName())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Group - Map","Opertion Map with name '"+this.operationMap.getName()+"' already exists!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
			if(!map.getId().equals(this.operationMap.getId()) &&
					map.getMethodReference().equalsIgnoreCase(this.operationMap.getMethodReference())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Group - Map","Operation Map with Method Reference '"+this.operationMap.getMethodReference()+"' already exists!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		
		FacesMessage msg = null;
		if(insert) {
			ServicesFactory.getInstance().getOperationMapService().save(this.operationMap);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Condition Group - Map","Condition Map "+this.operationMap.getName()+" - Saved!");
			
		}
		else {
			ServicesFactory.getInstance().getOperationMapService().update(this.operationMap);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Condition Group - Map","Condition Map "+this.operationMap.getName()+" - Updated!");
		}
		
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		updateExternalsBean();
		
		init(); 
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);
		
		
    }   
	
	
	
	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return ServicesFactory.getInstance().getConditionMapService().isUsed(id);
		
	}

	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
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
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	
    
}