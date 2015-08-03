package br.com.ims.flow.bean;

import java.io.Serializable;

import javax.faces.event.ActionEvent;
 
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
   	
	public abstract void save(ActionEvent event);
	public abstract void update(ActionEvent event);
	public abstract void delete(ActionEvent event);
	public abstract boolean isUsed(String id);
    
}