package br.com.ims.flow.dao;

import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.AbstractEntity;

public abstract class AbstractDAO <T extends AbstractEntity> {
	
	protected DbConnection db = null;
	
	public abstract List<T> getAll();
	
	public abstract T get(String id);
	
	public abstract boolean save(T entity);
	
	public abstract boolean update(T entity);
	
	public abstract boolean delete(T entity);

}
