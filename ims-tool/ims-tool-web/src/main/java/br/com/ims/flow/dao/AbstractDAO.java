package br.com.ims.flow.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import br.com.ims.flow.model.AbstractEntity;

@SuppressWarnings("serial")
public abstract class AbstractDAO <T extends AbstractEntity> implements Serializable {
	
	public abstract List<T> getByFilter(String where);
	
	public abstract List<T> getAll();
	
	public abstract T get(String id);
	
	public abstract boolean save(T entity);
	
	public abstract boolean update(T entity);
	
	public abstract boolean delete(T entity);
	
	protected void audit(T entity)  {
		StringBuffer result = new StringBuffer();
		try {
			Method[] methods = entity.getClass().getMethods();
			for(Method method : methods) {
				if(method.getName().startsWith("get") && !method.getName().equalsIgnoreCase("getClass")) {
					String field = method.getName().substring(3);
					
					result.append(field+"="+method.invoke(entity)+",");
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result.toString());
		
	}

}
