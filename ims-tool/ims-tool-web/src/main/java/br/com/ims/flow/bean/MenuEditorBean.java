package br.com.ims.flow.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.model.diagram.Element;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AbstractFormEntity;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.ConditionEntity;
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
	private String conditionId;
	
	private String choiceName;
	private String choiceDtmf;
	
	private String selectedChoiceId;
	
	private List<ChoiceEntity> choices;
	private List<ConditionEntity> conditions;
	
	
	
	
	private MenuEntity menu;
	
	
	private String originalName;
	
    public MenuEditorBean() {
    	//init();
    }
    
    @SuppressWarnings("unchecked")
	public void init() {
    	super.init();
    	
    	this.menu = (MenuEntity)this.form.getFormId();
    	if(this.menu.getChoices() == null) {
    		this.menu.setChoices(new ArrayList<ChoiceEntity>());
    	}
    	if(this.menu.getNoInput() != null) {
    		this.noInputId = this.menu.getNoInput().getId(); 
    	}
    	if(this.menu.getNoMatch() != null) {
    		this.noMatchId = this.menu.getNoMatch().getId(); 
    	}    	
    	if(this.menu.getPrompt() != null)
    		this.promptId = this.menu.getPrompt().getId();
    	this.choices = (List<ChoiceEntity>)((ArrayList<ChoiceEntity>)this.menu.getChoices()).clone();
    	
    	this.originalName = this.menu.getName();
    	
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

	private void removeChoices() {
		
		
		if(this.menu.getChoices() != null) {
			Node source = logicalFlow.getNode(this.form);
			
			for(int index =0; index < this.menu.getChoices().size(); index++) {
				ChoiceEntity menuChoice = this.menu.getChoices().get(index);	
				
				for(Node target : source.getListTarget()) {
					FormEntity formTarget = (FormEntity)target.getElement().getData();
					AbstractFormEntity obj = (AbstractFormEntity)formTarget.getFormId();
					if(!(formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCHINPUT) || 
							formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCH) ||
							formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOINPUT)) &&
							obj.getId().equals(menuChoice.getId())) {
						
						boolean remove = true;
						for(ChoiceEntity choiceTemp : this.choices) {
							if(menuChoice.getId().equals(choiceTemp.getId())) {
								remove = false;
							}
						}
						if(remove) {
							this.menu.getChoices().remove(index);
							ServicesFactory.getInstance().getIvrEditorService().deleteForm(target.getElement(),true);							
							index = -1;
							break;
						} 
						 
					}
					
				}								
			}
			
		}
		
		
		
	}
	
	private void removeNoInput() {
		
		if(this.menu.getNoInput() != null && !this.menu.getNoInput().getId().equals(this.noInputId)) {
			Node source = logicalFlow.getNode(this.form);
			for(Node target : source.getListTarget()) {
				FormEntity formTarget = (FormEntity)target.getElement().getData();
				if(formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOINPUT)) {
					ServicesFactory.getInstance().getIvrEditorService().deleteForm(target.getElement(),true);
					break;
				}
				
			}
		}
	}
	private void removeNoMatch() {
		if(this.menu.getNoMatch() != null && !this.menu.getNoMatch().getId().equals(this.noMatchId)) {
			Node source = logicalFlow.getNode(this.form);
			for(Node target : source.getListTarget()) {
				FormEntity formTarget = (FormEntity)target.getElement().getData();
				if(formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCH)) {
					ServicesFactory.getInstance().getIvrEditorService().deleteForm(target.getElement(),true);
					break;
				}
				
			}
		}
	}

	private void addNoInput() {
		
		
		if(this.menu.getNoInput() != null && this.menu.getNoInput().getId().equals(this.noInputId)  ) {
			updateNoMatchInputName();
			return;
		}
		
		
		
		NoMatchInputEntity noInput = ServicesFactory.getInstance().getNoMatchInputService().get(this.noInputId);
		
		
		this.menu.setNoInput(noInput);
		
		
		FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().getByName(Constants.FORM_TYPE_NOINPUT);
		
		FormEntity formNoInput = new FormEntity();
		formNoInput.setId(this.menu.getId()+this.noInputId);
		formNoInput.setDescription("Form de Controle para guardar a posição X e Y do NoInput, Menu: "+this.menu.getId()+", NoMatch: "+this.noMatchId);
		formNoInput.setName(this.menu.getName()+"_"+noInput.getName());
		formNoInput.setFormType(formType, noInput);
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
		
		
		ServicesFactory.getInstance().getIvrEditorService().connect(source.getElement(), element);
	
	}
	private void updateNoMatchInputName() {
		/**
		 * Update name
		 */
		Node source = logicalFlow.getNode(this.form.getId());
		
		for(Node target : source.getListTarget()) {
			FormEntity formTarget = (FormEntity)target.getElement().getData();
			if(formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOINPUT)	) {
				formTarget.setName(this.menu.getName()+"_"+this.menu.getNoInput().getName());
			} else if(formTarget.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCH)) {
				formTarget.setName(this.menu.getName()+"_"+this.menu.getNoMatch().getName());
			}
			
		}
		
	}
	private void addNoMatch() {
		
		if(this.menu.getNoMatch() != null && this.menu.getNoMatch().getId().equals(this.noMatchId) ) {
			updateNoMatchInputName();
			return;
		}
		
		NoMatchInputEntity noMatch = ServicesFactory.getInstance().getNoMatchInputService().get(this.noMatchId);
		
		this.menu.setNoMatch(noMatch);
		
		FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().getByName(Constants.FORM_TYPE_NOMATCH);
		
		FormEntity formNoMatch = new FormEntity();
		formNoMatch.setId(this.menu.getId()+this.noMatchId);
		formNoMatch.setDescription("Form de Controle para guardar a posição X e Y do NoMatch, PromptCollect: "+this.menu.getId()+", NoMatch: "+this.noMatchId);
		formNoMatch.setName(this.menu.getName()+"_"+noMatch.getName());
		formNoMatch.setFormType(formType, noMatch);
		formNoMatch.setPositionX(this.form.getPositionX());
		formNoMatch.setPositionY(this.form.getPositionY());

		
		String imgPath = formType.getImagePathSuccess();
		formType.setImagePathSuccess(imgPath.replace("<NOMACHINPUT>", Constants.NO_MATCH.toLowerCase()));
		
		imgPath = formType.getImagePathError();
		formType.setImagePathError(imgPath.replace("<NOMACHINPUT>", Constants.NO_MATCH.toLowerCase()));
		
		
		Element element = new Element(formNoMatch);
		element.setX(formNoMatch.getPositionX());
		element.setY(formNoMatch.getPositionY());
		
		ServicesFactory.getInstance().getIvrEditorService().setEndPoint(formType, element);
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().getModel().addElement(element);
		ServicesFactory.getInstance().getIvrEditorService().getBean().getListForm().add(formNoMatch);
		
		logicalFlow.addNode(element);
		
		Node source = logicalFlow.getNode(this.form);
		
		
		//ServicesFactory.getInstance().getIvrEditorService().connectForm(source.getElement(), element);
		ServicesFactory.getInstance().getIvrEditorService().connect(source.getElement(), element);
	}
	
	
	private void addChoices() {
		
		Node source = logicalFlow.getNode(this.form);
		
		Collections.sort(this.choices);
		
		for(ChoiceEntity choice : this.choices) {
			
		
			boolean insere = true;
			if(this.menu.getChoices() != null) {
				for(ChoiceEntity choiceMenu : this.menu.getChoices()) {
					if(choiceMenu.getId().equals(choice.getId())) {						
						insere = false;						
					}
				}
			}
			if(insere) {
								
				FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().getByName(Constants.FORM_TYPE_CHOICE);
				
				FormEntity formChoice = new FormEntity();
				formChoice.setId(Util.getUID());
				formChoice.setDescription(this.menu.getName()+"_"+choice.getName());
				formChoice.setName(this.menu.getName()+"_"+choice.getName());
				formChoice.setFormType(formType,choice);
				formChoice.setPositionX(this.form.getPositionX());
				formChoice.setPositionY(this.form.getPositionY());
				
				
				String imgPath = formType.getImagePathSuccess();
				formType.setImagePathSuccess(imgPath.replace("<NUMBER>", choice.getDtmf().equals("*") ? "x" : choice.getDtmf()  ));
				
				imgPath = formType.getImagePathError();
				formType.setImagePathError(imgPath.replace("<NUMBER>", choice.getDtmf().equals("*") ? "x" : choice.getDtmf() ));
				
				
				Element element = new Element(formChoice);
				element.setX(formChoice.getPositionX());
				element.setY(formChoice.getPositionY());
				
				ServicesFactory.getInstance().getIvrEditorService().setEndPoint(formType, element);
				
				ServicesFactory.getInstance().getIvrEditorService().getBean().getModel().addElement(element);
				ServicesFactory.getInstance().getIvrEditorService().getBean().getListForm().add(formChoice);
				
				logicalFlow.addNode(element);
				
				
				ServicesFactory.getInstance().getIvrEditorService().connectForm(source.getElement(), element);
			} 
		}		
		this.menu.setChoices(this.choices);
		//atualiza nome dos form choices e ordena as choices
		for(Node nodeChoice : source.getListTarget()) {
			
			if(( (FormEntity) nodeChoice.getElement().getData()).getFormType().getName().equals(Constants.FORM_TYPE_CHOICE) ) {
				for(ChoiceEntity choiceMenu : this.menu.getChoices()) {
				
			
					FormEntity formChoice = (FormEntity)nodeChoice.getElement().getData();
					ChoiceEntity choice = (ChoiceEntity)formChoice.getFormId();
					
					if(choiceMenu.getId().equals(choice.getId()) ) { 
						
						if(!this.menu.getName().equalsIgnoreCase(this.originalName) && 
								formChoice.getName().contains(this.originalName+"_")) {
							String name = formChoice.getName().replace(this.originalName+"_", this.menu.getName()+"_");
									
							formChoice.setName(name);
							formChoice.setDescription(name);
						}																
					}
					
				}
			}						
		}
		ServicesFactory.getInstance().getIvrEditorService().alingMenuChoices(source.getElement());
		
	}
	
	public void update(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		
		removeNoInput();
		removeNoMatch();
		removeChoices();
		
		
		addChoices();
		addNoInput();
		addNoMatch();
		
		this.menu.setPrompt(ServicesFactory.getInstance().getPromptService().get(this.promptId));
		
		
		logicalFlow.validateNodes();
		//logicalFlow.align();
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Menu",this.menu.getName()+" - Updated!");
		
		 
		ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("saved", saved);		
		logicalFlow.validateNodes();

    }
	
	public void addPrompt(ActionEvent event) {
		
		this.collect();		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setComplementPageEditor("/pages/complement/Prompt.xhtml");
		
		ServicesFactory.getInstance().getPromptEditorService().getBean().setMenuBean(this);
		
    }
	public void addCondition(ActionEvent event) {
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setAuxiliarPageEditor("/pages/auxiliar/Condition.xhtml");
		
		ServicesFactory.getInstance().getConditionEditorService().getBean().setMenuBean(this);
		
	}
	
	public void addNoMatchInput(ActionEvent event) {
		
		this.collect();

		ServicesFactory.getInstance().getIvrEditorService().getBean().setOtherPageEditor("/pages/other/NoMatchInput.xhtml");
		
		ServicesFactory.getInstance().getNoMatchInputEditorService().getBean().setMenuBean(this);
		
    }
	
	public void addChoiceToMenu(ActionEvent event) {
		
		
		this.choiceName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:form_menu_choice_name").toString();
		this.choiceDtmf = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:form_menu_choice_dtmf").toString();
		this.conditionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:form_menu_choice_condition_input").toString();
		
		FacesMessage msg = null;
		
		if(this.choiceName == null || this.choiceName.length() == 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Menu","Choice: You have to fill the Name");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(this.choiceDtmf == null || this.choiceDtmf.length() == 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Menu","Choice: You have to fill the DTMF");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} else if (!Pattern.matches("[0-9*#]", this.choiceDtmf)){
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Menu","Choice: DTMF accept only numbers(0-9) or '*' or '#'");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		for(ChoiceEntity choice : this.choices) {
			if(choice.getName().equalsIgnoreCase(this.choiceName)) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Menu","Choice: Name ("+this.choiceName+") already exists");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
			if(choice.getDtmf().equalsIgnoreCase(this.choiceDtmf)) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Menu","Choice: DTMF ("+this.choiceDtmf+") already exists");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		
		ChoiceEntity choice = new ChoiceEntity();
		choice.setId(Util.getUID());
		choice.setName(this.choiceName);
		choice.setDtmf(this.choiceDtmf);
		
		if(this.conditionId != null && this.conditionId.length() > 0) {
			choice.setCondition(ServicesFactory.getInstance().getConditionService().get(this.conditionId));					
		}
		
		choices.add(choice);
		
		this.choiceName = "";
		this.choiceDtmf = "";
		this.conditionId = "";
		
	}
	
	public void deleteChoice(String id) {
		
		for(int index = 0; index < this.choices.size(); index++) {
			ChoiceEntity choice = this.choices.get(index);
			if(choice.getId().equals(id)) {
				this.choices.remove(index);
				return;
			}
		}		
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

	public List<PromptEntity> getPrompts() {
		this.prompts = ServicesFactory.getInstance().getPromptService().getAll();
		return prompts;
	}

	public void setPrompts(List<PromptEntity> prompts) {
		this.prompts = prompts;
	}

	public String getPromptId() {
		if((this.promptId == null ||  this.promptId.length() == 0) && this.menu.getPrompt() != null) {
			this.promptId = this.menu.getPrompt().getId();
		}
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
		return this.choices;
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
		if((this.noInputId == null ||  this.noInputId.length() == 0) && this.menu.getNoInput() != null) {
			this.noInputId = this.menu.getNoInput().getId();
		}
		return noInputId;
	}

	public void setNoInputId(String noInputId) {
		this.noInputId = noInputId;
	}

	public String getNoMatchId() {
		if((this.noMatchId == null ||  this.noMatchId.length() == 0) && this.menu.getNoMatch() != null) {
			this.noMatchId = this.menu.getNoMatch().getId();
		}
		return noMatchId;
	}

	public void setNoMatchId(String noMatchId) {
		this.noMatchId = noMatchId;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getChoiceName() {
		return choiceName;
	}

	public void setChoiceName(String choiceName) {
		this.choiceName = choiceName;
	}

	public String getChoiceDtmf() {
		return choiceDtmf;
	}

	public void setChoiceDtmf(String choiceDtmf) {
		this.choiceDtmf = choiceDtmf;
	}

	public List<ConditionEntity> getConditions() {
		this.conditions = ServicesFactory.getInstance().getConditionService().getAll();
		return conditions;
	}

	public void setConditions(List<ConditionEntity> conditions) {
		this.conditions = conditions;
	}

	public String getSelectedChoiceId() {
		return selectedChoiceId;
	}

	public void setSelectedChoiceId(String selectedChoiceId) {
		this.selectedChoiceId = selectedChoiceId;
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
		
		this.promptId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_prompt_input").toString();
		this.noInputId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_noinput_input").toString();
		this.noMatchId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:dialog_form_nomatch_input").toString();
		
		this.choiceName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:form_menu_choice_name").toString();
		this.choiceDtmf = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:form_menu_choice_dtmf").toString();
		
		
	}
	
	
	
	
	
	
	
    
}
