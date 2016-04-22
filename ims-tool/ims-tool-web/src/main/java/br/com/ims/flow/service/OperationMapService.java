package br.com.ims.flow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ControlPanelEntity;
import br.com.ims.flow.model.OperationEntity;
import br.com.ims.flow.model.OperationGroupEntity;
import br.com.ims.flow.model.OperationMapEntity;

@SuppressWarnings("serial")
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
		boolean result = DAOFactory.getInstance().getOperationMapDAO().save(entity); 
		if(result) {
			ControlPanelEntity cp =  ServicesFactory.getInstance().getControlPanelService().getByMethod(entity.getName());
			if(cp == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
				cp = new ControlPanelEntity();
				cp.setMethodname(entity.getMethodReference());
				cp.setInternalService("true");
				cp.setStatus("true");
				cp.setVersionId(entity.getVersionId());
				cp.setLoginid(Util.getUserName());
				cp.setStartdate(sdf.format(Calendar.getInstance().getTime()));
				cp.setOwner("IMS-TOOL");
				cp.setReferencedBy("IMS-TOOL");
				cp.setTimeout(10);
				result = ServicesFactory.getInstance().getControlPanelService().save(cp);				
			}
		}
		return result;
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		
		if ((id == null) || (id.length() == 0)) {
			return false;
		}
		List<OperationGroupEntity> operations = DAOFactory.getInstance().getOperationDAO()
				.getOperationGroups("WHERE og.operationmapid '" + id + "' ", true);
		if (operations.size() > 0) {
			return true;
		}
		return false;
		
	}

	@Override
	public boolean update(OperationMapEntity object) {
		// TODO Auto-generated method stub
		boolean result = DAOFactory.getInstance().getOperationMapDAO().update(object);
		if(result) {
			ControlPanelEntity cp =  ServicesFactory.getInstance().getControlPanelService().getByMethod(object.getName());
			if(cp == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
				cp = new ControlPanelEntity();
				cp.setMethodname(object.getMethodReference());
				cp.setInternalService("true");
				cp.setStatus("true");
				cp.setVersionId(object.getVersionId());
				cp.setLoginid(Util.getUserName());
				cp.setStartdate(sdf.format(Calendar.getInstance().getTime()));
				cp.setOwner("IMS-TOOL");
				cp.setReferencedBy("IMS-TOOL");
				cp.setTimeout(10);
				result = ServicesFactory.getInstance().getControlPanelService().save(cp);				
			}
		}
		return result;
		
	}

	@Override
	public boolean delete(OperationMapEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getOperationMapDAO().delete(object);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
	    List<String[]> result = new ArrayList<String[]>();
	    if ((id == null) || (id.length() == 0)) {
	      return result;
	    }
	    List<OperationGroupEntity> operations = DAOFactory.getInstance().getOperationDAO().getOperationGroups("WHERE og.operationmapid '" + id + "' ", true);
	    if (operations.size() > 0)
	    {
	      List<String> operationId = new ArrayList<String>();
	      for (OperationGroupEntity group : operations) {
	        if (!operationId.contains(group.getOperationId()))
	        {
	          OperationEntity operation = DAOFactory.getInstance().getOperationDAO().get(group.getOperationId(), true);
	          String[] obj = { "Operation", operation.getName() };
	          result.add(obj);
	        }
	      }
	    }
	    return result;
	}

}
