package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class GrammarEntity extends AbstractEntity {
	
	private String name;
	private String description;
	private String type;/*list box DIGITS*/
	private Integer sizeMax;
	private Integer sizeMin;

	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getSizeMax() {
		return sizeMax;
	}
	public void setSizeMax(Integer sizeMax) {
		this.sizeMax = sizeMax;
	}
	public Integer getSizeMin() {
		return sizeMin;
	}
	public void setSizeMin(Integer sizeMin) {
		this.sizeMin = sizeMin;
	}
	
	


		
}
