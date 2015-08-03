package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.FormTypeEntity;

public class FormTypeService extends AbstractEntityService<FormTypeEntity> {
	
	public List<FormTypeEntity> getAll() {
		return DAOFactory.getInstance().getFormTypeDAO().getAll();
	}

	@Override
	public FormTypeEntity get(String id) {
		// TODO Auto-generated method stub
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
}
