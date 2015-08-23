package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class FormTypeEntity extends AbstractEntity{
	
	private String name;
	private String description;
	private String imagePathSuccess;
	private String imagePathError;
	private Integer imageSizeWidth;
	private Integer imageSizeHeight;
	private Integer allowInput;
	private Integer allowOutput;
	private Integer mandatoryInput;
	private Integer mandatoryOutput;
	
	private boolean visible;
	
	public FormTypeEntity() {
		this.visible = true;
		this.imageSizeWidth = 60;
		this.imageSizeHeight = 60;
	}
	
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
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Integer getImageSizeWidth() {
		return imageSizeWidth;
	}

	public void setImageSizeWidth(Integer imageSizeWidth) {
		this.imageSizeWidth = imageSizeWidth;
	}

	public Integer getImageSizeHeight() {
		return imageSizeHeight;
	}

	public void setImageSizeHeight(Integer imageSizeHeight) {
		this.imageSizeHeight = imageSizeHeight;
	}
	public String getImageSizeWidthStr() {
		return String.valueOf(imageSizeWidth)+"px";
	}
	
	public String getImageSizeHeightStr() {
		return String.valueOf(imageSizeHeight)+"px";
	}
	
	
	
}
