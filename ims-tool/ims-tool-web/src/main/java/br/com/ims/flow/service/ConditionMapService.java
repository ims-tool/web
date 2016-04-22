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
		List<ConditionGroupEntity> conditions = DAOFactory.getInstance().getConditionDAO().getConditionGroups("WHERE cg.conditionmapid ='" + id + "' ", true);
		if (conditions.size() > 0) {
			return true;
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
	public boolean delete(ConditionMapEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getConditionMapDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		
		
		List<String[]> result = new ArrayList<String[]>();
	    if ((id == null) || (id.length() == 0)) {
	      return result;
	    }
	    List<ConditionGroupEntity> conditions = DAOFactory.getInstance().getConditionDAO().getConditionGroups("WHERE cg.conditionmapid ='" + id + "' ", true);
	    if (conditions.size() > 0)
	    {
	      List<String> conditionId = new ArrayList<String>();
	      for (ConditionGroupEntity group : conditions) {
	        if (!conditionId.contains(group.getConditionId()))
	        {
	          ConditionEntity condition = DAOFactory.getInstance().getConditionDAO().get(group.getConditionId(), true);
	          String[] dependence = { "Condition", condition.getName() };
	          result.add(dependence);
	        }
	      }
	    }
	    return result;
	}

}
