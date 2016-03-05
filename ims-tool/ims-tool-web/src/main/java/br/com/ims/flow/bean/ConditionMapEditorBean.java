package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionMapEntity;
import br.com.ims.flow.model.ConditionParameterEntity;
import br.com.ims.flow.model.ConditionValueEntity;
 
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
    	
    	this.insert = true;
    	
    	this.conditionGroupBean = null;
    	
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

	

	
	public void save(ActionEvent event) {

		this.conditionMaps = this.getConditionMaps();
		for(ConditionMapEntity map : this.conditionMaps) {
			if(!map.getId().equals(this.conditionMap.getId()) &&
					map.getName().equalsIgnoreCase(this.conditionMap.getName())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Map","Condition Map with name '"+this.conditionMap.getName()+"' already exists!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
			if(!map.getId().equals(this.conditionMap.getId()) &&
					map.getMethodReference().equalsIgnoreCase(this.conditionMap.getMethodReference())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Map","Condition Map with Method Reference '"+this.conditionMap.getMethodReference()+"' already exists!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		
		FacesMessage msg = null;
		if(insert) {
			ServicesFactory.getInstance().getConditionMapService().save(this.conditionMap);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Condition Group - Map","Condition Map "+this.conditionMap.getName()+" - Saved!");
			
		}
		else {
			ServicesFactory.getInstance().getConditionMapService().update(this.conditionMap);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Condition Group - Map","Condition Map "+this.conditionMap.getName()+" - Updated!");
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
		if(this.conditionGroupBean != null) {
			this.conditionGroupBean.setMapId(this.conditionMap.getId());
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