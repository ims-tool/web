package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class TagEntity extends AbstractEntity{
	
		
	private String description;	
	private TagTypeEntity type;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public TagTypeEntity getType() {
		return type;
	}
	public void setType(TagTypeEntity type) {
		this.type = type;
	}
	
	
			
}
