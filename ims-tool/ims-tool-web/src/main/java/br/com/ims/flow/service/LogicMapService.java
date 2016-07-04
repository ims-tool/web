package br.com.ims.flow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionGroupEntity;
import br.com.ims.flow.model.ControlPanelEntity;
import br.com.ims.flow.model.LogicEntity;
import br.com.ims.flow.model.LogicMapEntity;
import br.com.ims.flow.model.LogicNodeEntity;

@SuppressWarnings("serial")
public class LogicMapService extends AbstractEntityService<LogicMapEntity>{
	
	public List<LogicMapEntity> getAll() {
		
		return DAOFactory.getInstance().getLogicMapDAO().getAll();
	}
	
	public LogicMapEntity get(String id) {
		
		return DAOFactory.getInstance().getLogicMapDAO().get(id);
	}
	public LogicMapEntity getByName(String name) {
		
		return DAOFactory.getInstance().getLogicMapDAO().getByName(name);
	}
	public LogicMapEntity getByMethodReference(String method) {
		
		return DAOFactory.getInstance().getLogicMapDAO().getByMethodReference(method);
	}
	
	public boolean save(LogicMapEntity entity) {
		boolean result = DAOFactory.getInstance().getLogicMapDAO().save(entity);
		if(entity.getMapType().getId().equals("1") /*WebService*/ &&  result) {
			ControlPanelEntity cp =  ServicesFactory.getInstance().getControlPanelService().getByMethod(entity.getMethodReference());
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
	public boolean update(LogicMapEntity entity) {
		// TODO Auto-generated method stub
		boolean result = DAOFactory.getInstance().getLogicMapDAO().update(entity);
		if(entity.getMapType().getId().equals("1") /*WebService*/&& result) {
			ControlPanelEntity cp =  ServicesFactory.getInstance().getControlPanelService().getByMethod(entity.getMethodReference());
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
	public boolean delete(LogicMapEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getLogicMapDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		
		
		List<String[]> result = new ArrayList<String[]>();
	    if ((id == null) || (id.length() == 0)) {
	      return result;
	    }
	    List<LogicNodeEntity> logicNodes = DAOFactory.getInstance().getLogicDAO().getLogicNode("WHERE ln.logicmapid ='"+id+"' ", true);
	    if (logicNodes.size() > 0)
	    {
	      List<String> logicId = new ArrayList<String>();
	      for (LogicNodeEntity node: logicNodes) {
	        if (!logicId.contains(node.getLogicId() ))
	        {
	        	logicId.add(node.getLogicId());
	        	LogicEntity logic = DAOFactory.getInstance().getLogicDAO().get(node.getLogicId(), true);
	        	String[] dependence = { "Logic", logic.getName() };
	        	result.add(dependence);
	        }
	      }
	    }
	    return result;
	}

}
