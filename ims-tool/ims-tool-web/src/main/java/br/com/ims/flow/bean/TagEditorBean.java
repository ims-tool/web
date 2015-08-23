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
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;
 
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
	
	
    public TagEditorBean() { 
    	init();
    }
    
    public void init() {
    	
    	this.tag = new TagEntity();
    	
    	this.insert = true;
    	
    	this.node = null;
    	this.noMatchInput = null;
    	
    }

	public void setNode(Node node) {
		this.node = node;
	}
	
	public void setNoMatchInput(NoMatchInputEntity noMatchInput) {
		this.noMatchInput = noMatchInput;
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
		
		init();
		
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

	@Override
	public void save(ActionEvent event) {
		
		FacesMessage msg = null;
		this.tag.setType(ServicesFactory.getInstance().getTagTypeService().get(this.tagTypeId));
		if(this.insert) {					
			ServicesFactory.getInstance().getTagService().save(this.tag);			
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "TAG",this.tag.getId()+" - Saved!");
		} else {
			ServicesFactory.getInstance().getTagService().update(this.tag);			
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "TAG",this.tag.getId()+" - Updated!");
		}
			
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		updateExternalsBean();
		
		init();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);
		
	}

	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return ServicesFactory.getInstance().getTagService().isUsed(id);
	}

    private void updateExternalsBean() {
    	if(this.node != null) {
			Connection connection = this.node.getConnection();
			
			if(connection.getOverlays().size() > 0) {
				connection.getOverlays().clear();
			} 

			connection.getOverlays().add(new LabelOverlay("Tag "+this.tag.getId(), "flow-label", 0.5));
			
			
			FormEntity form = (FormEntity)this.node.getElement().getData();
			form.setTag(this.tag);
		}	
    	if(this.noMatchInput != null) {
    		this.noMatchInput.setTag(this.tag);
    	}
    }
   
 
	
    
}