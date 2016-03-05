package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

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
    	this.insert = true;
    	
    	this.promptCollectBean = null;
    	
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
	
	public void save(ActionEvent event) {
		
		if(ServicesFactory.getInstance().getGrammarService().getByName(this.grammar.getName()) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Grammar","Grammar with name '"+this.grammar.getName()+"' alread exists!");

			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return;
		}
		
		ServicesFactory.getInstance().getGrammarService().save(this.grammar);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Grammar",this.grammar.getName()+" - Saved!");
		 
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
	



	
    
}