package br.com.ims.flow.service;

import br.com.ims.flow.bean.AbstractBean;

public abstract class AbstractBeanService <T extends AbstractBean> {
	
	protected T bean;
	
	public T getBean() { 
		return bean;
	}
		
}
