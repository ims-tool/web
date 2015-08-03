package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class TagTypeEntity extends AbstractEntity{
	
	private String name;
	private TagTypeEntity tagType;	
	private String description;	

	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TagTypeEntity getTagType() {
		return tagType;
	}
	public void setTagType(TagTypeEntity tagType) {
		this.tagType = tagType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	
		
}
