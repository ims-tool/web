package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.FormEntity;

public class FormDAO extends AbstractDAO<FormEntity>{
	List<FormEntity> result = null;
	
	
	private static FormDAO instance = null;
	
	
	private FormDAO() {
		super();
		this.result = new ArrayList<FormEntity>();
	}
	
	public static FormDAO getInstance() {
		if(instance == null) {
			instance = new FormDAO();
		}
		return instance;
	}
	public List<FormEntity> getAll() {
		return result;
	}

	@Override
	public FormEntity get(String id) {
		// TODO Auto-generated method stub
		for(FormEntity form : result) {
			if(form.getId().equals(id)) {
				return form;
			}
		}
		return null;
	}
	public List<FormEntity> getByTypeName(String typeName) {
		List<FormEntity> result= new ArrayList<FormEntity>();
		for(FormEntity form : result) {
			if(form.getFormType().getName().equals(typeName)) {
				result.add(form);
			}
		}
		return result;
	}

	@Override
	public boolean save(FormEntity entity) {
		// TODO Auto-generated method stub
		result.add(entity);
		return true;
		
	}

	@Override
	public boolean update(FormEntity entity) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean delete(FormEntity entity) {
		// TODO Auto-generated method stub
		result.remove(entity);
		return true;
		
	}

}
