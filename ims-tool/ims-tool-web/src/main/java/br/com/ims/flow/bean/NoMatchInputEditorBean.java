package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.common.Constants;
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
    	this.insert = true;
    	this.menuBean = null;
    	this.promptCollectBean = null;
    	
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
		
		ServicesFactory.getInstance().getNoMatchInputService().save(this.noMatchInput);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "NoInput/NoMatch",this.noMatchInput.getName()+" - Saved!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		updateExternalsBean();
		
		init();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);
		
    }   
	
	
	public void addPrompt(ActionEvent event) {
		this.collect();
		
		ServicesFactory.getInstance().getFlowEditorService().getBean().setComplementPageEditor("/pages/complement/Prompt.xhtml");
		
		ServicesFactory.getInstance().getPromptEditorService().getBean().setNoMatchInputBean(this);
		
	}

	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return ServicesFactory.getInstance().getNoMatchInputService().isUsed(id);
		
	}

	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
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
	



	
    
}