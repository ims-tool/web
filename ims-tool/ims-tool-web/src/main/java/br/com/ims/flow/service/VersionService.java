package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.VersionEntity;

public class VersionService extends AbstractEntityService<VersionEntity>{
	
	public List<VersionEntity> getAll() {
		
		return DAOFactory.getInstance().getVersionDAO().getAll();
	}
	
	public ConditionEntity getByName(String name) {
		
		return DAOFactory.getInstance().getConditionDAO().getByName(name);
	}
	
	public VersionEntity get(String id) {
		
		return DAOFactory.getInstance().getVersionDAO().get(id);
	}
	
	public boolean save(VersionEntity entity) {
		return DAOFactory.getInstance().getVersionDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll();
		for(FormEntity form :  forms) {
			
			if(form.getVersionId() != null && form.getVersionId().getId().equals(id)) {
				return true;
				
			}
			
		}
		
		return false;
	}
		
	@Override
	public boolean update(VersionEntity object) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean delete(VersionEntity object) {
		// TODO Auto-generated method stub
		return true;
	}

}
