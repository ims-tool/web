package br.com.ims.flow.common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MyBoolean implements Serializable{
	
	private boolean value;
	
	public MyBoolean(boolean value) {
		this.value = value;
	}

	public boolean booleanValue() {
		return value;
	}

	public void setBooleanValue(boolean value) {
		this.value = value;
	}
	
}
