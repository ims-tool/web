package br.com.ims.flow.model;

import br.com.ims.flow.common.Constants;

@SuppressWarnings("serial")
public class FormEntity extends AbstractFormEntity{
	
	private FormTypeEntity formType;
	private Object formId;
	private TagEntity tag;
	private ConditionEntity condition;
	private FormEntity nextFormDefault;
	private VersionEntity versionId;
	private String positionX;
	private String positionY;
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
			
			try {
				this.formId = Class.forName(entity).newInstance();
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
	
	public TagEntity getTag() {
		return tag;
	}

	public void setTag(TagEntity tag) {
		this.tag = tag;
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

	public VersionEntity getVersionId() {
		return versionId;
	}

	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
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
	
	

	
	
	
	
	
}
