package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class FormTypeEntity extends AbstractEntity{
	
	private String name;
	private String description;
	private String imagePathSuccess;
	private String imagePathError;
	private Integer allowInput;
	private Integer allowOutput;
	private Integer mandatoryInput;
	private Integer mandatoryOutput;
	
	
	
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
	
	public String getImagePathSuccess() {
		return imagePathSuccess;
	}
	public void setImagePathSuccess(String imagePathSuccess) {
		this.imagePathSuccess = imagePathSuccess;
	}
	public String getImagePathError() {
		return imagePathError;
	}
	public void setImagePathError(String imagePathError) {
		this.imagePathError = imagePathError;
	}
	public Integer getAllowInput() {
		return allowInput;
	}
	public void setAllowInput(Integer allowInput) {
		this.allowInput = allowInput;
	}
	public Integer getAllowOutput() {
		return allowOutput;
	}
	public void setAllowOutput(Integer allowOutput) {
		this.allowOutput = allowOutput;
	}
	public Integer getMandatoryInput() {
		return mandatoryInput;
	}
	public void setMandatoryInput(Integer mandatoryInput) {
		this.mandatoryInput = mandatoryInput;
	}
	public Integer getMandatoryOutput() {
		return mandatoryOutput;
	}
	public void setMandatoryOutput(Integer mandatoryOutput) {
		this.mandatoryOutput = mandatoryOutput;
	}
	
	
}
