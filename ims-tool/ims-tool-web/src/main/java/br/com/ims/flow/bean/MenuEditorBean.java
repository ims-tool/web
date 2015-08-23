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
import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AbstractFormEntity;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "menuEditorView")
@ViewScoped
public class MenuEditorBean extends AbstractBean {
     
	
	private List<PromptEntity> prompts;
	private List<NoMatchInputEntity> noInputs;
	private List<NoMatchInputEntity> noMatchs;
	
	
	private String promptId;
	private String noInputId;
	private String noMatchId;	
	private List<ChoiceEntity> choices;
	
	
	private MenuEntity menu;
	private FormEntity form;
	private LogicalFlow flow;
	
    public MenuEditorBean() {
    	//init();
    }
    
    public void init() {
    	this.form = ServicesFactory.getInstance().getFlowEditorService().getForm();
    	this.flow = ServicesFactory.getInstance().getFlowEditorService().getFlow();
    	this.menu = (MenuEntity)this.form.getFormId();
    	
    	
    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    }
   
    

	public FormEntity getForm() {
		return form;
	}

	public void setForm(FormEntity form) {
		this.form = form;
	}

	

	public MenuEntity getMenu() {
		return menu;
	}

	public void setMenu(MenuEntity menu) {
		this.menu = menu;
	}

	public LogicalFlow getFlow() {
		return flow;
	}

	public void setFlow(LogicalFlow flow) {
		this.flow = flow;
	}
	private void removeChoices() {
		Node source = flow.getNode(this.form);
		for(Node target : source.getListTarget()) {
			ServicesFactory.getInstance().getFlowEditorService().deleteForm(target.getElement());
		}
	}
	
	private void removeNoInput() {
		
		if(this.menu.getNoInput() != null && !this.menu.getNoInput().getId().equals(this.noInputId)) {
			Node source = flow.getNode(this.form);
			for(Node target : source.getListTarget()) {
				FormEntity formTarget = (FormEntity)target.getElement().getData();
				if(formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCHINPUT) &&
				   formTarget.getName().equals(this.menu.getNoInput().getName())) {
					ServicesFactory.getInstance().getFlowEditorService().deleteForm(target.getElement());
				}
				
			}
		}
	}
	private void removeNoMatch() {
		if(this.menu.getNoMatch() != null && !this.menu.getNoMatch().getId().equals(this.noMatchId)) {
			Node source = flow.getNode(this.form);
			for(Node target : source.getListTarget()) {
				FormEntity formTarget = (FormEntity)target.getElement().getData();
				if(formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCHINPUT) &&
				   formTarget.getName().equals(this.menu.getNoMatch().getName())) {
					ServicesFactory.getInstance().getFlowEditorService().deleteForm(target.getElement());
				}
				
			}
		}
	}

	private void addNoInput() {
		FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().getByName(Constants.FORM_TYPE_NOMATCHINPUT);
		
		FormEntity formNoInput = new FormEntity();
		//pegar nomatchinput do services
		formNoInput.setDescription(this.menu.getNoInput().getDescription());
		formNoInput.setName(this.menu.getNoInput().getName());
		formNoInput.setFormType(formType);
		
		String imgPath = formType.getImagePathSuccess();
		formType.setImagePathSuccess(imgPath.replace("<NOMACHINPUT>", Constants.NO_INPUT.toLowerCase()));
		
		imgPath = formType.getImagePathError();
		formType.setImagePathError(imgPath.replace("<NOMACHINPUT>", Constants.NO_INPUT.toLowerCase()));
		
		
		Element element = new Element(formNoInput);
		
		ServicesFactory.getInstance().getFlowEditorService().setEndPoint(formType, element);
		
		ServicesFactory.getInstance().getFlowEditorService().getBean().getModel().addElement(element);
		flow.addNode(element);
		
		Node source = flow.getNode(this.form);
		
		
		ServicesFactory.getInstance().getFlowEditorService().connectForm(source.getElement(), element);
	
	}
	private void addNoMatch() {
		FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().getByName(Constants.FORM_TYPE_NOMATCHINPUT);
		
		FormEntity formNoMatch = new FormEntity();
		formNoMatch.setDescription(this.menu.getNoMatch().getDescription());
		formNoMatch.setName(this.menu.getNoMatch().getName());
		formNoMatch.setFormType(formType);
		
		String imgPath = formType.getImagePathSuccess();
		formType.setImagePathSuccess(imgPath.replace("<NOMACHINPUT>", Constants.NO_MATCH.toLowerCase()));
		
		imgPath = formType.getImagePathError();
		formType.setImagePathError(imgPath.replace("<NOMACHINPUT>", Constants.NO_MATCH.toLowerCase()));
		
		
		Element element = new Element(formNoMatch);
		
		ServicesFactory.getInstance().getFlowEditorService().setEndPoint(formType, element);
		
		ServicesFactory.getInstance().getFlowEditorService().getBean().getModel().addElement(element);
		flow.addNode(element);
		
		Node source = flow.getNode(this.form);
		
		
		ServicesFactory.getInstance().getFlowEditorService().connectForm(source.getElement(), element);
	}
	
	private void addChoices() {
		removeNoInput();
		removeNoMatch();
		
		addNoInput();
		addNoMatch();
			
		
		
		flow.validateNodes();
		flow.align();
	}
	
	public void update(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		
		removeChoices();
		
		
		
		addChoices();
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Menu",this.menu.getName()+" - Updated!");
		
		 
		ServicesFactory.getInstance().getFlowEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("saved", saved);		
		flow.validateNodes();

    }
	
	public void addPrompt(ActionEvent event) {
		
				
		ServicesFactory.getInstance().getFlowEditorService().getBean().setComplementPageEditor("/pages/complement/Prompt.xhtml");
		
		ServicesFactory.getInstance().getPromptEditorService().getBean().setMenuBean(this);
		
		
    }
	public void addNoMatchInput(ActionEvent event) {
		
		

		ServicesFactory.getInstance().getFlowEditorService().getBean().setOtherPageEditor("/pages/other/NoMatchInput.xhtml");
		
		ServicesFactory.getInstance().getNoMatchInputEditorService().getBean().setMenuBean(this);
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Menu","Add NoInput Clicked!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		

    }

	@Override
	public void save(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<PromptEntity> getPrompts() {
		this.prompts = ServicesFactory.getInstance().getPromptService().getAll();
		return prompts;
	}

	public void setPrompts(List<PromptEntity> prompts) {
		this.prompts = prompts;
	}

	public String getPromptId() {
		return promptId;
	}

	public void setPromptId(String promptId) {
		this.promptId = promptId;
	}

	public List<NoMatchInputEntity> getNoInputs() {
		this.noInputs = ServicesFactory.getInstance().getNoMatchInputService().getNoInputAll();
		return noInputs;
	}

	

	public List<ChoiceEntity> getChoices() {
		return choices;
	}

	public void setChoices(List<ChoiceEntity> choices) {
		this.choices = choices;
	}

	public List<NoMatchInputEntity> getNoMatchs() {
		this.noMatchs = ServicesFactory.getInstance().getNoMatchInputService().getNoMatchAll();
		return noMatchs;
	}

	public void setNoInputs(List<NoMatchInputEntity> noInputs) {
		this.noInputs = noInputs;
	}

	public void setNoMatchs(List<NoMatchInputEntity> noMatchs) {
		this.noMatchs = noMatchs;
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
	
	
	
	
	
    
}
