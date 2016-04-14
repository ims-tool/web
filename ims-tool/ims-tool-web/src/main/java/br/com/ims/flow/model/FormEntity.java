package br.com.ims.flow.model;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.Util;

@SuppressWarnings("serial")
public class FormEntity extends AbstractFormEntity{
	
	private FormTypeEntity formType;
	private Object formId;
	private ConditionEntity condition;
	private FormEntity nextFormDefault;
	private String positionX;
	private String positionY;
	private String flowName;
	private boolean clone;
	
	
	
	//controle interno
	
	
	
	private boolean formError;
	private String errorDescription;
	
	public FormEntity() {
		formType = new FormTypeEntity();
		formId = null;
		formError = false;
		errorDescription="";
		clone = false;
	}
	
	public void setName(String name) {
		this.name = name;
		if(this.formId != null) {
			((AbstractFormEntity)this.formId).setName(name);
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setDescription(String description) {
		this.description = description;
		if(this.formId != null) { 
			((AbstractFormEntity)this.formId).setDescription(description);
		}
	}
	public FormTypeEntity getFormType() {
		return formType;
	}
	public void setFormType(FormTypeEntity formType, Object formId) {
		this.formType = formType;
		this.formId = formId;
	}
	public void setFormType(FormTypeEntity formType) {
		this.formType = formType;
		if(this.formType != null) {
			String entity = Constants.CLASS_ENTITY_PATH + this.formType.getName()+"Entity";
			if(this.formType.getName().equals(Constants.FORM_TYPE_NOINPUT) ||
					this.formType.getName().equals(Constants.FORM_TYPE_NOMATCH)) {
				entity = Constants.CLASS_ENTITY_PATH + Constants.FORM_TYPE_NOMATCHINPUT +"Entity";
			}
			
			try {
				this.formId = Class.forName(entity).newInstance();
				((AbstractFormEntity)this.formId).setId(Util.getUID());
				((AbstractFormEntity)this.formId).setName(this.name);
				((AbstractFormEntity)this.formId).setDescription(this.description);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Object getFormId() {
		return formId;
	}

	public void setFormId(Object formId) {
		this.formId = formId;
	}
	
	
	public ConditionEntity getCondition() {
		return condition;
	}

	public void setCondition(ConditionEntity condition) {
		this.condition = condition;
	}

	public FormEntity getNextFormDefault() {
		return nextFormDefault;
	}

	public void setNextFormDefault(FormEntity nextFormDefault) {
		this.nextFormDefault = nextFormDefault;
	}


	public String getImagePath() {
		String imagePath = "";
		if(this.formError) {
			imagePath = this.formType.getImagePathError();
		} else {
			imagePath = this.formType.getImagePathSuccess();
		}
		return imagePath;
	}
	
	public boolean isFormError() {
		return formError;
	}

	public void setFormError(boolean formError) {
		this.formError = formError;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}

	public String getPositionX() {
		return positionX;
	}

	public void setPositionX(String positionX) {
		this.positionX = positionX;
	}

	public String getPositionY() {
		return positionY;
	}

	public void setPositionY(String positionY) {
		this.positionY = positionY;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
	
	

	
	
	
	
	
}
