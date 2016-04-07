package br.com.ims.flow.service;

import java.io.Serializable;

import br.com.ims.flow.bean.AbstractBean;

@SuppressWarnings("serial")
public abstract class AbstractBeanService <T extends AbstractBean> implements Serializable {
	
	protected T bean;
	
	public T getBean() { 
		return bean;
	}
		
}
