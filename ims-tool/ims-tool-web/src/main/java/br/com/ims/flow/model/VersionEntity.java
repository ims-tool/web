package br.com.ims.flow.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class VersionEntity extends AbstractEntity {
	
	private String description;	
	private String system_user;
	private Date date_create;
	
	public VersionEntity() {
		super();
		this.date_create = Calendar.getInstance().getTime();		
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSystem_user() {
		return system_user;
	}
	public void setSystem_user(String system_user) {
		this.system_user = system_user;
	}
	public Date getDate_create() {
		return date_create;
	}
	public void setDate_create(Date date_create) {
		this.date_create = date_create;
	}
	public String getDateCreateString() {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatDate.format(this.date_create);
	}
	public String toString() {
		return this.id+" - "+this.description;
	}
	
	
			
}
