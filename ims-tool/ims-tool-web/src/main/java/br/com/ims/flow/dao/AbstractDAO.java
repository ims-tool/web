package br.com.ims.flow.dao;

import java.util.List;

import br.com.ims.flow.model.AbstractEntity;

public abstract class AbstractDAO <T extends AbstractEntity> {
	
	public abstract List<T> getByFilter(String where);
	
	public abstract List<T> getAll();
	
	public abstract T get(String id);
	
	public abstract boolean save(T entity);
	
	public abstract boolean update(T entity);
	
	public abstract boolean delete(T entity);

}
