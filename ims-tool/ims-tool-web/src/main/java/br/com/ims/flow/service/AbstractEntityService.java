package br.com.ims.flow.service;

import java.io.Serializable;
import java.util.List;

import br.com.ims.flow.model.AbstractEntity;

@SuppressWarnings("serial")
public abstract class AbstractEntityService <T extends AbstractEntity> implements Serializable {
	
	public abstract List<T> getAll();
	
	public abstract T get(String id);
	
	public abstract boolean save(T object);
	
	public abstract boolean update(T object);
	
	public abstract boolean delete(T object);
	
	public abstract boolean isUsed(String id);
	
	public abstract List<String[]> getUsed(String id);

}
