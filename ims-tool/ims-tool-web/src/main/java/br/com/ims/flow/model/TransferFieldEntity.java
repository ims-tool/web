package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class TransferFieldEntity extends AbstractEntity{
	
	private String name;
	private String type;
	private String field;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
			
}
