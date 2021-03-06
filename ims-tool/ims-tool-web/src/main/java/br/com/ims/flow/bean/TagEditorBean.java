package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.overlay.LabelOverlay;

import br.com.ims.flow.common.Node;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AbstractFormEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;
import br.com.ims.flow.model.VersionEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "tagEditorView")
@ViewScoped
public class TagEditorBean extends AbstractBean {
     
	
	private Node node;
	private NoMatchInputEntity noMatchInput;

	
	private TagEntity tag;
	
	private List<TagEntity> tags;
	
	private List<TagTypeEntity> tagTypes;
	
	private String tagTypeId;
	
	
	private ConditionGroupEditorBean conditionGroupBean;
	private TransferEditorBean transferBean;
	private LogicNodeEditorBean logicNodeEditorBean;
	private boolean logicNodeTagTrue = false;
	
	private VersionEntity version;
    public TagEditorBean() { 
    	init();
    }
    
    
    
    
    public void init() {
    	
    	this.tag = new TagEntity();
    	this.tag.setId(Util.getTAGID());
    	this.insert = true;
    	
    	this.node = null;
    	this.noMatchInput = null;
    	
    	this.conditionGroupBean = null;
    	this.transferBean = null;
    	this.logicNodeEditorBean = null;
    	this.logicNodeTagTrue = false;
    	
    }

	public void setNode(Node node) {
		this.node = node;
	}
	
	
	public void setNoMatchInput(NoMatchInputEntity noMatchInput) {
		this.noMatchInput = noMatchInput;
	}

	
	
	public void setLogicNodeEditorBean(LogicNodeEditorBean logicNodeEditorBean) {
		this.logicNodeEditorBean = logicNodeEditorBean;
	}




	public void setLogicNodeTagTrue(boolean logicNodeTagTrue) {
		this.logicNodeTagTrue = logicNodeTagTrue;
	}




	public TagEntity getTag() {
		return tag;
	}

	public void setTagFromExternal(TagEntity tag) {
		if(tag != null) {
			this.tag = tag;
			this.insert = false;
		}
	}
	
	public void setTag(TagEntity tag) {
		this.tag = tag;
		
		updateExternalsBean();
		
		
		//init();
		
	}
	
	
	public List<TagTypeEntity> getTagTypes() {
		this.tagTypes = ServicesFactory.getInstance().getTagTypeService().getAll();
		return tagTypes;
	}

	public void setTagTypes(List<TagTypeEntity> tagTypes) {
		this.tagTypes = tagTypes;
	}

	public List<TagEntity> getTags() {
		this.tags = ServicesFactory.getInstance().getTagService().getAll();
		return tags;
	}

	public void setTags(List<TagEntity> tags) {
		this.tags = tags;
	}


	public String getTagTypeId() {
		return tagTypeId;
	}

	public void setTagTypeId(String tagTypeId) {
		this.tagTypeId = tagTypeId;
	}

	public void requestVersion(boolean save) {
		if(save) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "You have to assign the Version to save changes.",
	                "TAG");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		ServicesFactory.getInstance().getVersionEditorService().getBean().setTagEditorBean(this);
		
		RequestContext context = RequestContext.getCurrentInstance();
    	context.execute("PF('settingAdminDlg').show();");
        context.update("settingAdminDlgId");
	}
	private boolean validateFields() {
		
		if(this.tagTypeId == null || this.tagTypeId.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Please, select the Type!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}

		if(this.tag.getDescription() == null || this.tag.getDescription().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "TAG","Please, inform the Description!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion() == null) {
			ServicesFactory.getInstance().getIvrEditorService().getBean().requestVersion(true);
			return false;
		}
		this.version = ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion();
		
		this.tag.setVersionId(this.version.getId());
		return true;
	}
	
	@Override
	public void save(ActionEvent event) {
		
		if(validateFields()) {
			
			this.tag.setType(ServicesFactory.getInstance().getTagTypeService().get(this.tagTypeId));
								
			if(ServicesFactory.getInstance().getTagService().save(this.tag)) {			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "TAG",this.tag.getId()+" - Saved!");
					
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tag","Error on Save "+this.tag.getId()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
		
		
		
	}

	@Override
	public void update(ActionEvent event) {
		if(validateFields()) {
			
			this.tag.setType(ServicesFactory.getInstance().getTagTypeService().get(this.tagTypeId));
								
			if(ServicesFactory.getInstance().getTagService().update(this.tag)) {			
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "TAG",this.tag.getId()+" - Updated!");
					
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tag","Error on Update "+this.tag.getId()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
	}

	

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return ServicesFactory.getInstance().getTagService().isUsed(id);
	}

	
    protected void updateExternalsBean() {
    	
    	if(this.node != null) {
			Connection connection = this.node.getConnection();
			
			if(connection.getOverlays().size() > 0) {
				connection.getOverlays().clear();
			} 

			connection.getOverlays().add(new LabelOverlay("Tag "+this.tag.getId(), "flow-label", 0.5));
			
			
			FormEntity form = (FormEntity)this.node.getElement().getData();			
			form.setTag(this.tag);
			AbstractFormEntity abs = (AbstractFormEntity)form.getFormId();
			abs.setTag(this.tag);
		}	
    	if(this.noMatchInput != null) {
    		this.noMatchInput.setTag(this.tag);
    		this.noMatchInput = null;
    	}
    	if(this.conditionGroupBean != null) {
    		if(this.conditionGroupBean.getTagTrueId() == null || 
    				this.conditionGroupBean.getTagTrueId().length() == 0 ||
    						this.conditionGroupBean.getTagTrueId().equals("0"))
    		{
    			this.conditionGroupBean.setTagTrueId(this.tag.getId());
    		} else {
    			this.conditionGroupBean.setTagFalseId(this.tag.getId());
    		}
    		this.conditionGroupBean.getConditionBean().setVersion(this.version);
    		this.conditionGroupBean = null;
    	}
    	if(this.transferBean != null) {
    		this.transferBean.setTagId(this.tag.getId());
    		this.transferBean = null;
    	}
    	if(this.logicNodeEditorBean != null) {
    		if(this.logicNodeTagTrue)
    			this.logicNodeEditorBean.setTagFalseId(this.tag.getId());
    		else 
    			this.logicNodeEditorBean.setTagFalseId(this.tag.getId());
    		this.logicNodeEditorBean=null;
    	}
    	
    	init();
    		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);
    	
    }
    
    public boolean isRendered() {
    	if(this.node != null || this.noMatchInput != null || this.conditionGroupBean != null) {
    		return true;
    	}
    	return false;
    }




	public ConditionGroupEditorBean getConditionGroupBean() {
		return conditionGroupBean;
	}




	public void setConditionGroupBean(ConditionGroupEditorBean conditionGroupBean) {
		this.conditionGroupBean = conditionGroupBean;
	}


	


	public TransferEditorBean getTransferBean() {
		return transferBean;
	}




	public void setTransferBean(TransferEditorBean transferBean) {
		this.transferBean = transferBean;
	}




	@Override
	public void edit(String id) {
		this.tag = ServicesFactory.getInstance().getTagService().get(id);
		if(tag.getType()!= null)
			this.tagTypeId = tag.getType().getId(); 
		this.insert= false;
		
	}




	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}




	public VersionEntity getVersion() {
		return version;
	}




	public void setVersion(VersionEntity version) {
		this.version = version;
	}

    
   
    
 
	
    
}