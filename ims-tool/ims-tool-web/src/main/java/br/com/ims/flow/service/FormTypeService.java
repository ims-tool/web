package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.FormTypeEntity;

public class FormTypeService extends AbstractEntityService<FormTypeEntity> {
	
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
	public void save(FormTypeEntity object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(FormTypeEntity object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(FormTypeEntity object) {
		// TODO Auto-generated method stub
		
	}
}
