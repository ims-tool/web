package br.com.ims.flow.model;


@SuppressWarnings("serial")
public abstract class AbstractFormEntity extends AbstractEntity{
	
	protected String name;
	protected String description;
	protected String nextForm;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNextForm() {
		return nextForm;
	}

	public void setNextForm(String nextForm) {
		this.nextForm = nextForm;
	}
	
	
	
	
	
	
}
