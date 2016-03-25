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
import br.com.ims.flow.model.GrammarEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "grammarEditorView")
@ViewScoped
public class GrammarEditorBean extends AbstractBean {
    	
	private GrammarEntity grammar;
	private List<GrammarEntity> grammars;
	
	private PromptCollectEditorBean promptCollectBean;
		
	
	
    public GrammarEditorBean() {
    	init();
    }
    
    public void init() {
    	this.grammar = new GrammarEntity();    	
    	this.grammar.setId(Util.getUID());
    	this.insert = true;
    	
    	this.promptCollectBean = null;
    	
    }
   
    public void newGrammar() {
    	this.grammar = new GrammarEntity();    	
    	this.grammar.setId(Util.getUID());
    	this.insert = true;
    	
    }
    public void viewDependence(String id, String name) {
		ServicesFactory.getInstance().getDependenceEditorService().getBean().setObject(Constants.DEPENDENCE_OBJECT_TYPE_GRAMMAR,id, name);
		
	}
    
    

	public GrammarEntity getGrammar() {
		return grammar;
	}

	public void setGrammar(GrammarEntity grammar) {
		this.grammar = grammar;
	}

	public List<GrammarEntity> getGrammars() {
		this.grammars = ServicesFactory.getInstance().getGrammarService().getAll();
		return grammars;
	}

	public void setGrammars(List<GrammarEntity> grammars) {
		this.grammars = grammars;
	}
	
	public PromptCollectEditorBean getPromptCollectBean() {
		return promptCollectBean;
	}

	public void setPromptCollectBean(PromptCollectEditorBean promptCollectBean) {
		this.promptCollectBean = promptCollectBean;
	}

	protected void updateExternalsBean() {
    	
    	if(this.promptCollectBean != null) {
    		
			this.promptCollectBean.setGrammarId(this.grammar.getId());
			
    	}
    }
	private boolean validateFields() {
		if(this.grammar.getName() == null || this.grammar.getName().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Grammar","Please,inform the Name!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.grammar.getDescription() == null || this.grammar.getDescription().length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Grammar","Please,inform the Description!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.grammar.getSizeMin() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Please,inform the Size Min!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(this.grammar.getSizeMax() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Audio","Please,inform the Size Max!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		
		GrammarEntity tmp = ServicesFactory.getInstance().getGrammarService().getByName(this.grammar.getName()) ;
		
		if(tmp != null && 
				tmp.getName().equalsIgnoreCase(this.grammar.getName()) &&
				!tmp.getId().equals(grammar.getId())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Grammar","Gramar with name '"+this.grammar.getName()+"' already exists!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion() == null) {
			ServicesFactory.getInstance().getIvrEditorService().getBean().requestVersion(true);
			return false;
		}
		this.grammar.setVersionId(ServicesFactory.getInstance().getIvrEditorService().getBean().getVersion());
		return true;
	}
	
	public void save(ActionEvent event) {
		
		
		if(validateFields()) {
			if(ServicesFactory.getInstance().getGrammarService().save(this.grammar)) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Grammar",this.grammar.getName()+" - Saved!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init();
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Grammar","Error on Save "+this.grammar.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
    }   
	
	
	@Override
	public void update(ActionEvent event) {
		if(validateFields()) {
			if(ServicesFactory.getInstance().getGrammarService().update(this.grammar)) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Grammar",this.grammar.getName()+" - Updated!");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				updateExternalsBean();
				
				init();
				
				RequestContext context = RequestContext.getCurrentInstance();
				boolean saved = true;
				context.addCallbackParam("saved", saved);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Grammar","Error on Update "+this.grammar.getName()+", please contact your support.");
				 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
	}

	

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return ServicesFactory.getInstance().getGrammarService().isUsed(id);
		
	}

	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		this.grammar = ServicesFactory.getInstance().getGrammarService().get(id);
		this.insert = false;
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.grammar = ServicesFactory.getInstance().getGrammarService().get(id);
		if(ServicesFactory.getInstance().getGrammarService().delete(this.grammar)) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Grammar",this.grammar.getName()+" - Deleted!");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			updateExternalsBean();
			
			init();
			
			RequestContext context = RequestContext.getCurrentInstance();
			boolean saved = true;
			context.addCallbackParam("saved", saved);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Grammar","Error on Delete "+this.grammar.getName()+", please contact your support.");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	



	
    
}