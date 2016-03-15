package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.model.AbstractEntity;

public abstract class AbstractEntityService <T extends AbstractEntity> {
	
	public abstract List<T> getAll();
	
	public abstract T get(String id);
	
	public abstract boolean save(T object);
	
	public abstract boolean update(T object);
	
	public abstract boolean delete(T object);
	
	public abstract boolean isUsed(String id);

}
