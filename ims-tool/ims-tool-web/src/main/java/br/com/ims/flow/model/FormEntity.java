package br.com.ims.flow.model;

import br.com.ims.flow.common.Constants;

@SuppressWarnings("serial")
public class FormEntity extends AbstractFormEntity{
	
	private FormTypeEntity formType;
	private Integer formId;
	private Integer tag;
	private Integer condition;
	private Integer nextFormDefault;
	private VersionEntity versionId;
	
	
	
	//controle interno
	
	private Object object;
	
	private boolean formError;
	private String errorDescription;
	
	public FormEntity() {
		formType = new FormTypeEntity();
		object = null;
		formError = false;
		errorDescription="";
	}
	
	public void setName(String name) {
		this.name = name;
		if(this.object != null) {
			((AbstractFormEntity)this.object).setName(name);
		}
	}
	
	
	public void setDescription(String description) {
		this.description = description;
		if(this.object != null) { 
			((AbstractFormEntity)this.object).setDescription(description);
		}
	}
	public FormTypeEntity getFormType() {
		return formType;
	}
	public void setFormType(FormTypeEntity formType) {
		this.formType = formType;
		if(this.formType != null) {
			String entity = Constants.CLASS_ENTITY_PATH + this.formType.getName()+"Entity";
			
			try {
				this.object = Class.forName(entity).newInstance();
				((AbstractFormEntity)this.object).setName(this.name);
				((AbstractFormEntity)this.object).setDescription(this.description);
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
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	public Integer getNextFormDefault() {
		return nextFormDefault;
	}
	public void setNextFormDefault(Integer nextFormDefault) {
		this.nextFormDefault = nextFormDefault;
	}

	public VersionEntity getVersionId() {
		return versionId;
	}

	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}

	public Object getObject() {
		return object;
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

	
	
	
	
	
}
