package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.LogicEntity;
import br.com.ims.flow.model.LogicNodeEntity;
import br.com.ims.flow.model.MapTypeEntity;

@SuppressWarnings("serial")
public class MapTypeService extends AbstractEntityService<MapTypeEntity>{
	
	public List<MapTypeEntity> getAll() {
		
		return DAOFactory.getInstance().getMapTypeDAO().getAll();
	}
	
	public MapTypeEntity get(String id) {
		
		return DAOFactory.getInstance().getMapTypeDAO().get(id);
	}
	
	
	public boolean save(MapTypeEntity entity) {
		boolean result = DAOFactory.getInstance().getMapTypeDAO().save(entity);
		return result;
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		
		if ((id == null) || (id.length() == 0)) {
			return false;
		}
		List<LogicNodeEntity> nodes = DAOFactory.getInstance().getLogicDAO().getLogicNode("WHERE mt.id = "+id, true);
		if (nodes.size() > 0) {
			return true;
		}
		return false;
		
	}

	@Override
	public boolean update(MapTypeEntity entity) {
		// TODO Auto-generated method stub
		boolean result = DAOFactory.getInstance().getMapTypeDAO().update(entity);
		return result;
	}

	@Override
	public boolean delete(MapTypeEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getMapTypeDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		
		
		List<String[]> result = new ArrayList<String[]>();
	    if ((id == null) || (id.length() == 0)) {
	      return result;
	    }
	    List<LogicNodeEntity> logicNodes = DAOFactory.getInstance().getLogicDAO().getLogicNode("WHERE mt.id ='"+id+"' ", true);
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
