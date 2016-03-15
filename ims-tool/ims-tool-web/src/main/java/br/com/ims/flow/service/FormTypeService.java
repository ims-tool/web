package br.com.ims.flow.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.FormTypeEntity;

@SuppressWarnings("serial")
public class FormTypeService extends AbstractEntityService<FormTypeEntity> implements Serializable{
	
	public List<FormTypeEntity> getAll() {
		List<FormTypeEntity> formTypes = new ArrayList<FormTypeEntity>();
		for(FormTypeEntity formType : DAOFactory.getInstance().getFormTypeDAO().getAll()) {
			if(formType.isVisible()) {
				formTypes.add(formType);
			}
		}
		return formTypes;
	}
	
	public FormTypeEntity getByName(String name) {
		// TODO Auto-generated method stub
		List<FormTypeEntity> formTypes = DAOFactory.getInstance().getFormTypeDAO().getAll(); 
		for(FormTypeEntity formType : formTypes) {
			if(formType.getName().equals(name)) {
				return formType; 
			}
		}
		return null;
	}
	
	@Override
	public FormTypeEntity get(String id) {
		// TODO Auto-generated method stub
		List<FormTypeEntity> formTypes = DAOFactory.getInstance().getFormTypeDAO().getAll(); 
		for(FormTypeEntity formType : formTypes) {
			if(formType.getId().equals(id)) {
				return formType; 
			}
		}
		return null;
	}

	@Override
	public boolean save(FormTypeEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getFormTypeDAO().save(object);
		
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean update(FormTypeEntity object) {
		return DAOFactory.getInstance().getFormTypeDAO().update(object);
		
	}

	@Override
	public boolean delete(FormTypeEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getFormTypeDAO().delete(object);
		
	}
}
