package br.com.ims.flow.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TransferEntity;
import br.com.ims.flow.model.TransferRuleEntity;
 
@SuppressWarnings("serial")
@ManagedBean(name = "transferEditorView")
@ViewScoped
public class TransferEditorBean extends AbstractBean {
     
	
	
	private TransferEntity transfer;
	private List<TransferRuleEntity> rules;
	
	private List<PromptEntity> prompts;
	private List<ConditionEntity> conditions;
	private List<TagEntity> tags;
	
	private String promptId;
	private String conditionId;
	private String tagId;
	
	private String number;
	private String orderNum;
	
	
    public TransferEditorBean() {
    	//init();
    }
    @SuppressWarnings("unchecked")
    public void init() {
    	super.init();
    	this.transfer = (TransferEntity)this.form.getFormId();
    	if(this.transfer.getTransferRules() == null) {
    		this.transfer.setTransferRules(new ArrayList<TransferRuleEntity>());
    	}
    	
    	this.rules = (List<TransferRuleEntity>)((ArrayList<TransferRuleEntity>)this.transfer.getTransferRules()).clone(); 
    	
    	
    	if(form.isFormError())
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", form.getErrorDescription()));
    }
   
    
    public FormEntity getForm() {
		return form;
	}

	public void setForm(FormEntity form) {
		this.form = form;
	}

	public TransferEntity getTransfer() {
		return transfer;
	}

	public void setTransfer(TransferEntity transfer) {
		this.transfer = transfer;
	}
	

	public List<TransferRuleEntity> getRules() {
		return rules;
	}

	public void setRules(List<TransferRuleEntity> rules) {
		this.rules = rules;
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

	public List<ConditionEntity> getConditions() {
		this.conditions = ServicesFactory.getInstance().getConditionService().getAll();
		return conditions;
	}

	public void setConditions(List<ConditionEntity> conditions) {
		this.conditions = conditions;
	}

	public List<TagEntity> getTags() {
		this.tags = ServicesFactory.getInstance().getTagService().getAll();
		return tags;
	}

	public void setTags(List<TagEntity> tags) {
		this.tags = tags;
	}

	

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public void update(ActionEvent event) {
		
		if(this.rules.size() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transfer","Please, configure at least one Rule!");
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;	
		}
		Collections.sort(this.rules);
		
		List<Integer> indexes = new ArrayList<Integer>();
		for(int index = 0; index < this.rules.size(); index++) {
			TransferRuleEntity rule = this.rules.get(index);
			if(rule.getCondition() == null) {
				indexes.add(index);	
			}
		}
		if(indexes.size() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transfer","You have to configure one 'Rule Default' (without Condition).");
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} else if(indexes.size() > 1) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transfer","You have to configure only one 'Rule Default' (without Condition).");
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} else if( (indexes.get(0)+1) != this.rules.size() ) {
			String text = "The 'Rule Default' at number order "+this.rules.get(indexes.get(0)).getOrderNum()+" will invalidate the next Rule, please, correct that.";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transfer",text);
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		this.transfer.setTransferRules(this.rules);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Transfer",this.transfer.getName()+" - Updated!");
		 
		ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
				
		logicalFlow.validateNodes();
		
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = true;
		context.addCallbackParam("saved", saved);

    }
	
	public void addRule(ActionEvent event) {
		collect();
		
		if(this.orderNum == null || this.orderNum.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transfer","Please, inform the 'Number Order'.");
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(this.number == null || this.number.length() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transfer","Please, inform the 'Number' to be transferred.");
			 
			ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		for(TransferRuleEntity rule : this.rules) {
			if(rule.getOrderNum().equals(Integer.valueOf(this.orderNum))) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transfer","Rule with 'Number Order' ("+this.orderNum+") already exists.");
				 
				ServicesFactory.getInstance().getIvrEditorService().getBean().setEditing(true);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		
		TransferRuleEntity rule = new TransferRuleEntity();
		rule.setOrderNum(Integer.valueOf(this.orderNum));
		rule.setNumber(this.number);
		if(this.conditionId != null && this.conditionId.length() > 0) {
			rule.setCondition(ServicesFactory.getInstance().getConditionService().get(this.conditionId));
		}
		if(this.promptId != null && this.promptId.length() > 0) {
			rule.setPrompt(ServicesFactory.getInstance().getPromptService().get(this.promptId));
		}
		if(this.tagId != null && this.tagId.length() > 0) {
			rule.setTag(ServicesFactory.getInstance().getTagService().get(this.tagId));
		}
		this.rules.add(rule);
		
			
	}
	
	public void addNewPrompt(ActionEvent event) {
		
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setComplementPageEditor("/pages/complement/Prompt.xhtml");
		
		ServicesFactory.getInstance().getPromptEditorService().getBean().setTransferBean(this);
	}
	
	public void addNewCondition(ActionEvent event) {
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setAuxiliarPageEditor("/pages/auxiliar/Condition.xhtml");
		
		ServicesFactory.getInstance().getConditionEditorService().getBean().setTransferBean(this);
		
	}
	public void addNewTag(ActionEvent event) {
		collect();
		
		ServicesFactory.getInstance().getIvrEditorService().getBean().setUtilPageEditor("/pages/util/TAG.xhtml");
		
		ServicesFactory.getInstance().getTagEditorService().getBean().setTransferBean(this);
		
	}

	@Override
	public void save(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		for(int index = 0; index < this.rules.size(); index++) {
			TransferRuleEntity rule = this.rules.get(index);
			if(rule.getId().equals(id)) {
				this.rules.remove(index);
				index = this.rules.size();
			}
		}
		
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
	
	protected void collect() {
		super.collect();
		
		
		this.orderNum = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:transfer_orderNum").toString();
		this.number = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:transfer_number").toString();
		
		this.conditionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:transfer_condition_input").toString();
		this.promptId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:transfer_prompt_input").toString();
		this.tagId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formForm:transfer_tag_input").toString();
		
		

						
	}
    
}