package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.model.diagram.Element;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
import br.com.ims.flow.model.GrammarEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptCollectEntity;
import br.com.ims.flow.model.PromptEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "promptcollectEditorView")
@ViewScoped
public class PromptCollectEditorBean extends AbstractBean {
     

	private List<PromptEntity> prompts;
	private List<NoMatchInputEntity> noInputs;
	private List<NoMatchInputEntity> noMatchs;
	private List<GrammarEntity> grammars;
	
	
	private String promptId;
	private String grammarId;
	private String noInputId;
	private String noMatchId;
	
	private PromptCollectEntity promptCollect;
	
	public PromptCollectEditorBean() {
    	//init();
    }
    
    public void init() {
    	super.init();
    	
    	this.promptCollect = (PromptCollectEntity)this.form.getFormId();
    	if(this.promptCollect.getPrompt() != null) {
    		this.promptId = this.promptCollect.getPrompt().getId();    		
    	}
    	if(this.promptCollect.getGrammar() != null) {
    		this.grammarId = this.promptCollect.getGrammar().getId();
    	}
    	if(this.promptCollect.getNoInput() != null) {
    		this.noInputId = this.promptCollect.getNoInput().getId(); 
    	}
    	if(this.promptCollect.getNoMatch() != null) {
    		this.noMatchId = this.promptCollect.getNoMatch().getId(); 
    	}
    	
    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    }
   
    public List<PromptEntity> getPrompts() {
		this.prompts = ServicesFactory.getInstance().getPromptService().getAll();
		return prompts;
	}

	public void setPrompts(List<PromptEntity> prompts) {
		this.prompts = prompts;
	}
	
	

	public List<NoMatchInputEntity> getNoInputs() {
		this.noInputs = ServicesFactory.getInstance().getNoMatchInputService().getNoInputAll();
		return noInputs;
	}

	public void setNoInputs(List<NoMatchInputEntity> noInputs) {
		this.noInputs = noInputs;
	}

	public List<NoMatchInputEntity> getNoMatchs() {
		this.noMatchs = ServicesFactory.getInstance().getNoMatchInputService().getNoMatchAll();
		return noMatchs;
	}

	public void setNoMatchs(List<NoMatchInputEntity> noMatchs) {
		this.noMatchs = noMatchs;
	}
	
	public List<GrammarEntity> getGrammars() {
		this.grammars = ServicesFactory.getInstance().getGrammarService().getAll();
		return grammars;
	}

	public void setGrammars(List<GrammarEntity> grammars) {
		this.grammars = grammars;
	}

	public PromptCollectEntity getPromptCollect() {
		return promptCollect;
	}

	public void setPromptCollect(PromptCollectEntity promptCollect) {
		this.promptCollect = promptCollect;
	}

	public String getPromptId() {
		return promptId;
	}

	public void setPromptId(String promptId) {
		this.promptId = promptId;
	}
	
	

	public String getGrammarId() {
		return grammarId;
	}

	public void setGrammarId(String grammarId) {
		this.grammarId = grammarId;
	}

	public String getNoInputId() {
		return noInputId;
	}

	public void setNoInputId(String noInputId) {
		this.noInputId = noInputId;
	}

	public String getNoMatchId() {
		return noMatchId;
	}

	public void setNoMatchId(String noMatchId) {
		this.noMatchId = noMatchId;
	}

	public FormEntity getForm() {
		return form;
	}

	public void setForm(FormEntity form) {
		this.form = form;
	}

	private void removeNoInput() {
		
		if(this.promptCollect.getNoInput() != null && !this.promptCollect.getNoInput().getId().equals(this.noInputId)) {
			Node source = logicalFlow.getNode(this.form);
			for(Node target : source.getListTarget()) {
				FormEntity formTarget = (FormEntity)target.getElement().getData();
				if(formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCHINPUT) &&
				   formTarget.getName().equals(this.promptCollect.getNoInput().getName())) {
					ServicesFactory.getInstance().getIvrEditorService().deleteForm(target.getElement());
				}
				
			}
		}
	}
	private void removeNoMatch() {
		if(this.promptCollect.getNoMatch() != null && !this.promptCollect.getNoMatch().getId().equals(this.noMatchId)) {
			Node source = logicalFlow.getNode(this.form);
			for(Node target : source.getListTarget()) {
				FormEntity formTarget = (FormEntity)target.getElement().getData();
				if(formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCHINPUT) &&
				   formTarget.getName().equals(this.promptCollect.getNoMatch().getName())) {
					ServicesFactory.getInstance().getIvrEditorService().deleteForm(target.getElement());
				}
				
			}
		}
	}
	private void addNoInput() {
		
		
		if(this.promptCollect.getNoInput() != null && this.promptCollect.getNoInput().getId().equals(this.noInputId)  ) {
			return;
		}
		
		
		
		NoMatchInputEntity noInput = ServicesFactory.getInstance().getNoMatchInputService().get(this.noInputId);
		
		
		this.promptCollect.setNoInput(noInput);
		
		
		FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().getByName(Constants.FORM_TYPE_NOMATCHINPUT);
		
		FormEntity formNoInput = new FormEntity();		
		formNoInput.setDescription(noInput.getDescription());
		formNoInput.setName(noInput.getName());
		formNoInput.setFormType(formType);
		formNoInput.setPositionX(this.form.getPositionX());
		formNoInput.setPositionY(this.form.getPositionY());
		
		String imgPath = formType.getImagePathSuccess();
		formType.setImagePathSuccess(imgPath.replace("<NOMACHINPUT>", Constants.NO_INPUT.toLowerCase()));
		
		imgPath = formType.getImagePathError();
		formType.setImagePathError(imgPath.replace("<NOMACHINPUT>", Constants.NO_INPUT.toLowerCase()));
		
		
		Element element = new Element(formNoInput);
		element.setX(formNoInput.getPositionX());
		element.setY(formNoInput.getPositionY());
		
		ServicesFactory.getInstance().getIvrEditorService().setEndPoint(formType, element);
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().getModel().addElement(element);
		ServicesFactory.getInstance().getIvrEditorService().getBean().getListForm().add(formNoInput);
		
		logicalFlow.addNode(element);
		
		Node source = logicalFlow.getNode(this.form);
		
		
		ServicesFactory.getInstance().getIvrEditorService().connectForm(source.getElement(), element);
	
	}
	private void addNoMatch() {
		
		if(this.promptCollect.getNoMatch() != null && this.promptCollect.getNoMatch().getId().equals(this.noMatchId) ) {
			
			return;
		}
		
		NoMatchInputEntity noMatch = ServicesFactory.getInstance().getNoMatchInputService().get(this.noMatchId);
		
		this.promptCollect.setNoMatch(noMatch);
		
		FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().getByName(Constants.FORM_TYPE_NOMATCHINPUT);
		
		FormEntity formNoMatch = new FormEntity();
		formNoMatch.setDescription(noMatch.getDescription());
		formNoMatch.setName(noMatch.getName());
		formNoMatch.setFormType(formType);
		
		String imgPath = formType.getImagePathSuccess();
		formType.setImagePathSuccess(imgPath.replace("<NOMACHINPUT>", Constants.NO_MATCH.toLowerCase()));
		
		imgPath = formType.getImagePathError();
		formType.setImagePathError(imgPath.replace("<NOMACHINPUT>", Constants.NO_MATCH.toLowerCase()));
		
		
		Element element = new Element(formNoMatch);
		
		ServicesFactory.getInstance().getIvrEditorService().setEndPoint(formType, element);
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().getModel().addElement(element);
		ServicesFactory.getInstance().getIvrEditorService().getBean().getListForm().add(formNoMatch);
		
		logicalFlow.addNode(element);
		
		Node source = logicalFlow.getNode(this.form);
		
		
		ServicesFactory.getInstance().getIvrEditorService().connectForm(source.getElement(), element);
	}
	
	public void update(ActionEvent event) {
		
		
		this.promptCollect.setPrompt(ServicesFactory.getInstance().getPromptService().get(this.promptId));
		
		this.promptCollect.setGrammar(ServicesFactory.getInstance().getGrammarService().get(this.grammarId));
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "PromptCollect",this.promptCollect.getName()+" - Updated!");
		 
		ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		removeNoInput();
		removeNoMatch();
		
		addNoInput();
		addNoMatch();
		
		
		logicalFlow.validateNodes();
		//logicalFlow.align();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);

    }
	
	public void addPrompt(ActionEvent event) {

		this.collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setComplementPageEditor("/pages/complement/Prompt.xhtml");
		
		ServicesFactory.getInstance().getPromptEditorService().getBean().setPromptCollectorBean(this); 

    }
	
	public void addNoMatchInput(ActionEvent event) {
		
		this.collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setOtherPageEditor("/pages/other/NoMatchInput.xhtml");
		
		ServicesFactory.getInstance().getNoMatchInputEditorService().getBean().setPromptCollectBean(this);
		
    }
	public void addGrammar(ActionEvent event) {
		
		this.collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setComplementPageEditor("/pages/complement/Grammar.xhtml");
		
		ServicesFactory.getInstance().getGrammarEditorService().getBean().setPromptCollectBean(this);
		
    }

	@Override
	public void save(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
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
	protected void collect() {
		super.collect();
		
		this.grammarId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_grammar_input").toString();
		this.promptId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_prompt_input").toString();
		this.noInputId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_noinput_input").toString();
		this.noMatchId  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_nomatch_input").toString();
				
		this.promptCollect.setTerminatingCharacter(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_terminatingCharacter"));
		
		String flushPrompt = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_flush_prompt_input");
		String fetchTimeOut = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_fetchtimeout");
		String interDigitTimeout = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_interDigitTimeout");
		String terminatingTimeout = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_terminatingTimeout"); 
		
		if(flushPrompt.length() > 0)
			this.promptCollect.setFlushprompt(Integer.valueOf(flushPrompt));
		else
			this.promptCollect.setFlushprompt(null);
		
		if(fetchTimeOut.length() > 0) 
			this.promptCollect.setFetchTimeout(fetchTimeOut);
		else
			this.promptCollect.setFetchTimeout(null);
		
		
		if(interDigitTimeout.length() > 0)
			this.promptCollect.setInterDigitTimeout(interDigitTimeout);
		else
			this.promptCollect.setInterDigitTimeout(null);
			
		if(terminatingTimeout.length() > 0)
			this.promptCollect.setTerminatingTimeout(terminatingTimeout);
		else
			this.promptCollect.setTerminatingTimeout(null);
		
		
		
		
		
		
	}
	
    
}