package br.com.ims.flow.bean;

import java.io.Serializable;
 
@SuppressWarnings("serial")
public abstract class AbstractBean implements Serializable {
     
	protected boolean insert;
	
	
	public boolean isInsert() {
		return insert;
	}




	public void setInsert(boolean insert) {
		this.insert = insert;
	}




	public abstract void init();
   	
    
}