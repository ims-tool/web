package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
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
	public OperationMapEntity getByName(String name) {
		
		return DAOFactory.getInstance().getOperationMapDAO().getByName(name);
	}
	public OperationMapEntity getByMethodReference(String methodReference) {
		return DAOFactory.getInstance().getOperationMapDAO().getByMethodReference(methodReference);
	}
	
	public boolean save(OperationMapEntity entity) {
		return DAOFactory.getInstance().getOperationMapDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		
		List<OperationEntity> operations = DAOFactory.getInstance().getOperationDAO().getAll();
		for(OperationEntity operation :  operations) {
			if(operation.getListOperationGroup() != null) {
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

	@Override
	public List<String[]> getUsed(String id) {
		// TODO Auto-generated method stub
		List<String []> result = new ArrayList<String []>();
		List<OperationEntity> operations = DAOFactory.getInstance().getOperationDAO().getAll();
		for(OperationEntity operation :  operations) {
			boolean found = false;
			if(operation.getListOperationGroup() != null) {
				for(int index = 0; index < operation.getListOperationGroup().size() && !found; index++) {
					OperationGroupEntity group = operation.getListOperationGroup().get(index);
					if(group.getOperationMap().getId().equals(id)) {
						String [] obj = {"Operation",operation.getName()};
						result.add(obj);
					}
				}
			}
			
		}
		return result;
	}

}
