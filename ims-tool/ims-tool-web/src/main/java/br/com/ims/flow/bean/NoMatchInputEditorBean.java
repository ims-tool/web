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
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "nomatchinputEditorView")
@ViewScoped
public class NoMatchInputEditorBean extends AbstractBean {
     
	
	
	private String promptId;
	private List<PromptEntity> prompts;
	private List<NoMatchInputEntity> noMatchInputs;
	
	private NoMatchInputEntity noMatchInput;
	private MenuEditorBean menuBean;
	private PromptCollectEditorBean promptCollectBean;
	
	
	
	
    public NoMatchInputEditorBean() {
    	init();
    }
    
    public void init() {
    	this.noMatchInput = new NoMatchInputEntity();   
    	this.noMatchInput.setId(Util.getUID());
    	this.insert = true;
    	this.menuBean = null;
    	this.promptCollectBean = null;
    	
    }
    public void newNoMatchInput() {
    	this.noMatchInput = new NoMatchInputEntity();   
    	this.noMatchInput.setId(Util.getUID());
    	this.insert = true;
	}
    
    
   
   

	public String getPromptId() {
		return promptId;
	}

	public void setPromptId(String promptId) {
		this.promptId = promptId;
	}

	public List<PromptEntity> getPrompts() {
		this.prompts = ServicesFactory.getInstance().getPromptService().getAll();
		return prompts;
	}
	
	

	public List<NoMatchInputEntity> getNoMatchInputs() {
		this.noMatchInputs =  ServicesFactory.getInstance().getNoMatchInputService().getAll();
		return this.noMatchInputs;
	}

	public void setNoMatchInputs(List<NoMatchInputEntity> noMatchInputs) {
		this.noMatchInputs =  noMatchInputs;
	}

	public void setPrompts(List<PromptEntity> prompts) {
		this.prompts = prompts;
	}

	public NoMatchInputEntity getNoMatchInput() {
		return noMatchInput;
	}

	public void setNoMatchInput(NoMatchInputEntity noMatchInput) {
		this.noMatchInput = noMatchInput;
	}
	
	

	public MenuEditorBean getMenuBean() {
		return menuBean;
	}

	public void setMenuBean(MenuEditorBean menuBean) {
		this.menuBean = menuBean;
	}
	

	public PromptCollectEditorBean getPromptCollectBean() {
		return promptCollectBean;
	}

	public void setPromptCollectBean(PromptCollectEditorBean promptCollectBean) {
		this.promptCollectBean = promptCollectBean;
	}

	protected void updateExternalsBean() {
    	if(this.menuBean != null) {
			if(this.noMatchInput.getType().equalsIgnoreCase(Constants.NO_INPUT)) {
				this.menuBean.setNoInputId(this.noMatchInput.getId());
			} else {
				this.menuBean.setNoMatchId(this.noMatchInput.getId());
				
			}
		}	
    	if(this.promptCollectBean != null) {
    		if(this.noMatchInput.getType().equalsIgnoreCase(Constants.NO_INPUT)) {
				this.promptCollectBean.setNoInputId(this.noMatchInput.getId());
			} else {
				this.promptCollectBean.setNoMatchId(this.noMatchInput.getId());
				
			}
    	}
    }
	
	public void save(ActionEvent event) {
		if(validateFields()) {
			if(ServicesFactory.getInstance().getNoMatchInputService().save(this.noMatchInput)) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "NoInput/NoMatch",this.noMatchInput.getName()+" - Saved!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init();
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NoInput/NoMatch","Error on Save "+this.noMatchInput.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
		
    }   
	
	
	public void addPrompt(ActionEvent event) {
		this.collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setComplementPageEditor("/pages/complement/Prompt.xhtml");
		
		ServicesFactory.getInstance().getPromptEditorService().getBean().setNoMatchInputBean(this);
		
	}

	@Override
	public void update(ActionEvent event) {
		if(validateFields()) {
			if(ServicesFactory.getInstance().getNoMatchInputService().update(this.noMatchInput)) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "NoInput/NoMatch",this.noMatchInput.getName()+" - Updated!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init();
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NoInput/NoMatch","Error on Update "+this.noMatchInput.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
	}

	

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return ServicesFactory.getInstance().getNoMatchInputService().isUsed(id);
		
	}

	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		this.noMatchInput = ServicesFactory.getInstance().getNoMatchInputService().get(id);
		if(this.noMatchInput.getPrompt() != null)
			this.promptId = this.noMatchInput.getPrompt().getId(); 
		this.insert = false;
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.noMatchInput = ServicesFactory.getInstance().getNoMatchInputService().get(id);
		if(ServicesFactory.getInstance().getNoMatchInputService().delete(this.noMatchInput)) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "NoInput/NoMatch",this.noMatchInput.getName()+" - Deleted!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			updateExternalsBean();
			
			init();
			
			RequestContext context = RequestContext.getCurrentInstance();
			boolean saved = true;
			context.addCallbackParam("saved", saved);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NoInput/NoMatch","Error on Delete "+this.noMatchInput.getName()+", please contact your support.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	protected void collect() {
		this.noMatchInput.setType(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:other_nmi_type_input").toString());
		this.noMatchInput.setName(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:other_nmi_name").toString());

		if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:other_nmi_threshold") != null && 
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:other_nmi_threshold").toString().length() > 0) {
			this.noMatchInput.setThreshold(Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:other_nmi_threshold")));
		} else {
			this.noMatchInput.setThreshold(null);
		}
		this.promptId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formOther:other_nmi_prompt_input").toString();
		
		
	}
	public void viewDependence(String id, String name) {
		ServicesFactory.getInstance().getDependenceEditorService().getBean().setObject(Constants.DEPENDENCE_OBJECT_TYPE_NOMATCH_NOINPUT,id, name);
	}
	
	private boolean validateFields() {
		if(this.noMatchInput.getName() == null || this.noMatchInput.getName().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "NoInput/NoMatch","Please,inform the Name!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.noMatchInput.getThreshold() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "NoInput/NoMatch","Please,inform the Threshold!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.noMatchInput.getThreshold() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "NoInput/NoMatch","Please,inform the Threshold!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		
		NoMatchInputEntity tmp = ServicesFactory.getInstance().getNoMatchInputService().getByName(this.noMatchInput.getName()) ;
		
		if(tmp != null && 
				tmp.getName().equalsIgnoreCase(this.noMatchInput.getName()) &&
				!tmp.getId().equals(noMatchInput.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "NoInput/NoMatch","NoInput/NoMatch with name '"+this.noMatchInput.getName()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion() == null) {
			ServicesFactory.getInstance().getIvrEditorService().getBean().requestVersion(true);
			return false;
		}
		this.noMatchInput.setPrompt(null);
		if(this.promptId != null && this.promptId.length() > 0) {
			this.noMatchInput.setPrompt(ServicesFactory.getInstance().getPromptService().get(this.promptId));
		}
		this.noMatchInput.setVersionId(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion().getId());
		return true;
		
	}


	
    
}