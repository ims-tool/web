package br.com.ims.flow.service;

import java.io.Serializable;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import br.com.ims.flow.bean.LoginBean;

@SuppressWarnings("serial")
public class LoginBeanService implements Serializable{
	LoginBean bean;
	
	public LoginBeanService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		this.bean = (LoginBean) elContext.getELResolver().getValue(elContext, null, "loginBean");
	}

	public LoginBean getBean() {
		return this.bean;
	}
	

}
