package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.OperationEntity;
import br.com.ims.flow.model.OperationGroupEntity;
import br.com.ims.flow.model.OperationMapEntity;

public class OperationMapService extends AbstractEntityService<OperationMapEntity>{
	
	public List<OperationMapEntity> getAll() {
		
		return DAOFactory.getInstance().getOperationMapDAO().getAll();
	}
	
	public OperationMapEntity get(String id) {
		
		return DAOFactory.getInstance().getOperationMapDAO().get(id);
	}
	
	public boolean save(OperationMapEntity entity) {
		return DAOFactory.getInstance().getOperationMapDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		
		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll();
		for(FormEntity form :  forms) {
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_OPERATION)) {
				OperationEntity operation = (OperationEntity)form.getFormId();
				for(OperationGroupEntity group : operation.getListOperationGroup()) {
					if(group.getOperationMap().getId().equals(id)) {
						return true;
					}
				}				
			}
		}
		return false;
		
	}

	@Override
	public boolean update(OperationMapEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getOperationMapDAO().update(object);
		
	}

	@Override
	public boolean delete(OperationMapEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getOperationMapDAO().delete(object);
		
	}

}
