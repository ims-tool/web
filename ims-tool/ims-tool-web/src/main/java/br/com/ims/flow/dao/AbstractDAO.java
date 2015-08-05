package br.com.ims.flow.dao;

import java.util.List;

import br.com.ims.flow.model.AbstractEntity;

public abstract class AbstractDAO <T extends AbstractEntity> {
	
	
	
	public abstract List<T> getAll();
	
	public abstract T get(String id);
	
	public abstract void save(T entity);
	
	public abstract void update(T entity);
	
	public abstract void delete(T entity);

}
