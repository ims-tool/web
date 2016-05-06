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
import br.com.ims.flow.model.ConditionGroupEntity;
import br.com.ims.flow.model.ConditionMapEntity;
import br.com.ims.flow.model.ConditionParameterEntity;
import br.com.ims.flow.model.ConditionValueEntity;
import br.com.ims.flow.model.TagEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "conditiongroupEditorView")
@ViewScoped
public class ConditionGroupEditorBean extends AbstractBean {
     
	
	
	private ConditionGroupEntity conditionGroup;	
	private ConditionParameterEntity conditionParameter;
	private ConditionValueEntity conditionValue ;
	
	private List<ConditionParameterEntity> listConditionParameter;
	private List<ConditionValueEntity> listConditionValue;
	
	String mapId;
	
	String tagTrueId;
	String tagFalseId;
	
	
	ConditionEditorBean conditionBean;
	
	List<ConditionMapEntity> maps;
	List<TagEntity> tags;
	
	public ConditionGroupEditorBean() {
    	init();
    }
    
    public void init() {
    	this.conditionGroup = new ConditionGroupEntity();
    	this.conditionGroup.setId(Util.getUID());
    	this.conditionParameter = new ConditionParameterEntity();
    	this.conditionValue = new ConditionValueEntity();
    	
    	this.listConditionParameter = new ArrayList<ConditionParameterEntity>() ;
    	this.listConditionValue = new ArrayList<ConditionValueEntity>(); 
    	
    	this.insert = true;
    	
    	this.conditionBean = null;
    	
    }
    
    
	public ConditionEditorBean getConditionBean() {
		return conditionBean;
	}

	public void setConditionBean(ConditionEditorBean conditionBean) {
		this.init();
		this.conditionBean = conditionBean;
	}

	public ConditionGroupEntity getConditionGroup() {
		return conditionGroup;
	}

	@SuppressWarnings("unchecked")
	public void setConditionGroup(ConditionGroupEntity conditionGroup) {
		this.insert = false;
		this.conditionGroup = conditionGroup;
		this.mapId = conditionGroup.getConditionMap().getId();
		
		if(this.conditionGroup.getListConditionParameters() == null) {
    		this.conditionGroup.setListConditionParameters(new ArrayList<ConditionParameterEntity>());
    	}

    	this.listConditionParameter = (List<ConditionParameterEntity>)((ArrayList<ConditionParameterEntity>)this.conditionGroup.getListConditionParameters()).clone();
    	
    	if(this.conditionGroup.getListConditionValues() == null) {
    		this.conditionGroup.setListConditionValues(new ArrayList<ConditionValueEntity>());
    	}

    	this.listConditionValue = (List<ConditionValueEntity>)((ArrayList<ConditionValueEntity>)this.conditionGroup.getListConditionValues()).clone();
    	
	}
	
	

	public ConditionParameterEntity getConditionParameter() {
		return conditionParameter;
	}

	public void setConditionParameter(ConditionParameterEntity conditionParameter) {
		this.conditionParameter = conditionParameter;
	}

	public ConditionValueEntity getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(ConditionValueEntity conditionValue) {
		this.conditionValue = conditionValue;
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
	
    public List<ConditionMapEntity> getMaps() {
		this.maps = ServicesFactory.getInstance().getConditionMapService().getAll();
    	return maps;
	}

	public void setMaps(List<ConditionMapEntity> maps) {
		
		this.maps = maps;
	}

	
	public List<TagEntity> getTags() {
		this.tags = ServicesFactory.getInstance().getTagService().getAll();
		return tags;
	}

	public void setTags(List<TagEntity> tags) {
		this.tags = tags;
	}

	
	
	public List<ConditionParameterEntity> getListConditionParameter() {
		return listConditionParameter;
	}

	public void setListConditionParameter(List<ConditionParameterEntity> listConditionParameter) {
		this.listConditionParameter = listConditionParameter;
	}

	public List<ConditionValueEntity> getListConditionValue() {
		return listConditionValue;
	}

	public void setListConditionValue(List<ConditionValueEntity> listConditionValue) {
		this.listConditionValue = listConditionValue;
	}

	public void save(ActionEvent event) {
		
		if(this.conditionBean != null) {
			
			List<ConditionGroupEntity> listEntity = this.conditionBean.getConditionGroups();
			
			for(int index = 0; index < listEntity.size() ; index++) {
				ConditionGroupEntity cg = listEntity.get(index);
				if(!cg.getId().equals(this.conditionGroup.getId())) {
					if(cg.getOrderNum().equals(this.conditionGroup.getOrderNum())) {
			
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group","Number Order '"+this.conditionGroup.getOrderNum()+"' already exists on Condition "+this.conditionBean.getCondition().getName());
						 
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
						return;
						
					}
				}
				
			}
			if(this.listConditionValue.size() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group","You must configure at least one value on field 'Values'");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				return;
			}
			ConditionMapEntity map = ServicesFactory.getInstance().getConditionMapService().get(this.mapId);
			
			for(ConditionValueEntity value :  this.listConditionValue) {
				if(map.getType().equals("TEXT") &&
						(value.getOperation().equals(">") || 
								value.getOperation().equals("<") ||
								value.getOperation().equals("BETWEEN")) ) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group","Return of MAP is TEXT and doesn't work properly with Operation '"+this.conditionValue.getOperation()+"' in VALUE number order '"+value.getOrderNum()+"' ");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return;
				} 
			}
			
			
			this.conditionGroup.setConditionMap(map);
			
			this.conditionGroup.setListConditionParameters(this.listConditionParameter);
			this.conditionGroup.setListConditionValues(this.listConditionValue);
			
			if(this.insert) {
				this.conditionBean.getConditionGroups().add(this.conditionGroup);				
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
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Parameter","Please, inform Param Name!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(paramValue == null || paramValue.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Parameter","Please, inform Param Value!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		ConditionParameterEntity cp = new ConditionParameterEntity();
		cp.setId(Util.getUID());
		cp.setParamName(paramName);
		cp.setParamValue(paramValue);
		cp.setConditionGroupId(this.conditionGroup.getId());
		cp.setVersionId(cp.getVersionId());
		this.listConditionParameter.add(cp);
		
		
	}
	public void removeParameter(String parameterId) {
		
		boolean find = false;
		for(int index = 0; index < this.listConditionParameter.size() && !find;index++) {
			ConditionParameterEntity cp = this.listConditionParameter.get(index);
			if(cp.getId().equals(parameterId)) {
				find = true;
				this.listConditionParameter.remove(index);
			}
		}
	}
	public void addValue(ActionEvent event) {
		
		this.collect();
		
		for(ConditionValueEntity value : this.listConditionValue) {
			if(value.getOrderNum().equals(this.conditionValue.getOrderNum())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Value","OrderNum number("+this.conditionValue.getOrderNum()+") already exists!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		if(this.mapId == null || this.mapId.length() ==0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Value","You must assign the MAP before!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		ConditionMapEntity map = ServicesFactory.getInstance().getConditionMapService().get(this.mapId);
		if(map.getType().equals("TEXT") &&
				(this.conditionValue.getOperation().equals(">") || 
						this.conditionValue.getOperation().equals("<") ||
						this.conditionValue.getOperation().equals("BETWEEN")) ) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Value","Return of MAP is TEXT and doesn't work properly with Operation '"+this.conditionValue.getOperation()+"'");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} 
		
		if(this.conditionValue.getValue1() == null || this.conditionValue.getValue1().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Value","Please, inform Value1!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(!(this.conditionValue.getOperation().equals("=") || this.conditionValue.getOperation().equals("<") || this.conditionValue.getOperation().equals(">") ) 
				&& (this.conditionValue.getValue2() == null || this.conditionValue.getValue2().length() == 0)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Value","Please, inform Value2!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(this.tagTrueId == null || this.tagTrueId.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Value","Please, inform Tag True!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(this.tagFalseId == null || this.tagFalseId.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condition Group - Value","Please, inform Tag False!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		TagEntity tagTrue = ServicesFactory.getInstance().getTagService().get(this.tagTrueId);
		TagEntity tagFalse = ServicesFactory.getInstance().getTagService().get(this.tagFalseId);
		this.conditionValue.setId(Util.getUID());
		this.conditionValue.setTagTrue(tagTrue);
		this.conditionValue.setTagFalse(tagFalse);
		
		this.listConditionValue.add(this.conditionValue);
		this.conditionValue = new ConditionValueEntity();
	}
	public void removeValue(String valueId) {
		boolean find = false;
		for(int index = 0; index < listConditionValue.size() && !find;index++) {
			ConditionValueEntity cv = listConditionValue.get(index);
			if(cv.getId().equals(valueId)) {
				find = true;
				listConditionValue.remove(index);
			}
		}
	}

	public void addNewMap(ActionEvent event) {
		
		this.collect();
		ServicesFactory.getInstance().getIvrEditorService().getBean().setUtilPageEditor("/pages/auxiliar/ConditionMap.xhtml");
		
		ServicesFactory.getInstance().getConditionMapEditorService().getBean().setConditionGroupBean(this);
		ServicesFactory.getInstance().getConditionMapEditorService().getBean().setVersion(this.conditionBean.getVersion());
	}
	protected void collect() {
		String order = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_order").toString();
		this.conditionGroup.setDescription(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_description").toString());
		
		order = order == null || order.length() == 0 ? "0" : order; 
		
		this.conditionGroup.setOrderNum(Integer.valueOf(order));
		this.mapId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_map_input").toString();
		this.tagTrueId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_tagTrue_input").toString();
		this.tagFalseId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_tagFalse_input").toString();
		
		
		order = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:auxiliar_conditiongroup_orderNum").toString();
		
		order = order == null || order.length() == 0 ? "0" : order;
		
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
		}
	}
	
	public void addNewTag(ActionEvent event) {
		
		this.collect();
		
		ServicesFactory.getInstance().getTagEditorService().getBean().setConditionGroupBean(this);
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