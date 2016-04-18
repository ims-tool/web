package br.com.ims.flow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.ConditionGroupEntity;
import br.com.ims.flow.model.ConditionMapEntity;
import br.com.ims.flow.model.ControlPanelEntity;

@SuppressWarnings("serial")
public class ConditionMapService extends AbstractEntityService<ConditionMapEntity>{
	
	public List<ConditionMapEntity> getAll() {
		
		return DAOFactory.getInstance().getConditionMapDAO().getAll();
	}
	
	public ConditionMapEntity get(String id) {
		
		return DAOFactory.getInstance().getConditionMapDAO().get(id);
	}
	public ConditionMapEntity getByName(String name) {
		
		return DAOFactory.getInstance().getConditionMapDAO().getByName(name);
	}
	public ConditionMapEntity getByMethodReference(String method) {
		
		return DAOFactory.getInstance().getConditionMapDAO().getByMethodReference(method);
	}
	
	public boolean save(ConditionMapEntity entity) {
		boolean result = DAOFactory.getInstance().getConditionMapDAO().save(entity);
		if(result) {
			ControlPanelEntity cp =  ServicesFactory.getInstance().getControlPanelService().getByMethod(entity.getName());
			if(cp == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
				cp = new ControlPanelEntity();
				cp.setMethodname(entity.getMethodReference());
				cp.setInternalService("true");
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
		
		List<ConditionEntity> conditions = ServicesFactory.getInstance().getConditionService().getAll();
		
		for(ConditionEntity condition :  conditions) {
			List<ConditionGroupEntity> groups = condition.getListConditionGroup();
			for(ConditionGroupEntity group : groups) {
				if(group.getConditionMap().getId().equals(id)) {
					return true;
				}
			}
			
		}
		return false;
	}

	@Override
	public boolean update(ConditionMapEntity entity) {
		// TODO Auto-generated method stub
		boolean result = DAOFactory.getInstance().getConditionMapDAO().update(entity);
		if(result) {
			ControlPanelEntity cp =  ServicesFactory.getInstance().getControlPanelService().getByMethod(entity.getName());
			if(cp == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
				cp = new ControlPanelEntity();
				cp.setMethodname(entity.getMethodReference());
				cp.setInternalService("true");
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
	public boolean delete(ConditionMapEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getConditionMapDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		
		
		List<String[]> result = new ArrayList<String[]>();
		
		List<ConditionEntity> conditions = ServicesFactory.getInstance().getConditionService().getAll();
		
		for(ConditionEntity condition :  conditions) {
			List<ConditionGroupEntity> groups = condition.getListConditionGroup();
			
			boolean found = false;
			for(int index = 0; index < groups.size() && !found; index++) {
			
				ConditionGroupEntity group = groups.get(index);
				if(group.getConditionMap().getId().equals(id)) {
					found = true;
					String [] dependence = {"Condition",condition.getName()};
					result.add(dependence);
				}
			}
			
		}
		
		
		return result;
	}

}
