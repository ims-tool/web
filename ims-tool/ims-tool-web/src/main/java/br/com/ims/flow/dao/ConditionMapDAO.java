package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.ConditionMapEntity;

@SuppressWarnings("serial")
public class ConditionMapDAO extends AbstractDAO<ConditionMapEntity> {
	//private DbConnection db =  null;
	public static Logger log = Logger.getLogger(ConditionMapDAO.class);
	private static ConditionMapDAO instance = null;
	private ConditionMapDAO() {
		//db = new DbConnection("ConditionMapDAO"); 			
	}
	
	public static ConditionMapDAO getInstance() {
		if(instance == null) {
			instance = new ConditionMapDAO();
		}
		return instance;
	}
	public List<ConditionMapEntity> getByFilter(String where) {
		log.debug("getByFilter("+where+")");
		String sql = "SELECT cm.id cm_id, cm.name cm_name,cm.description cm_description, cm.type cm_type, cm.methodreference cm_methodreference, cm.log_active cm_log_active, cm.versionid cm_versionid "
				    + "FROM flow.conditionmap cm "
				    + "<WHERE> "
				    + "ORDER BY cm.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<ConditionMapEntity> result = new ArrayList<ConditionMapEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("ConditionMapDAO-getByFilter");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				
				ConditionMapEntity conditionMap = new ConditionMapEntity();
				conditionMap.setId(rs.getString("cm_id"));
				conditionMap.setName(rs.getString("cm_name"));
				conditionMap.setDescription(rs.getString("cm_description"));
				conditionMap.setType(rs.getString("cm_type"));
				conditionMap.setMethodReference(rs.getString("cm_methodreference"));
				conditionMap.setLogActive(rs.getInt("cm_log_active"));	
				conditionMap.setVersionId(rs.getString("cm_versionid")); 
				
				result.add(conditionMap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
			
		}
		
		return result;
	}
	
	public List<ConditionMapEntity> getAll() {
		
		return this.getByFilter(null);
		
	}
	public ConditionMapEntity get(String id) {
		List<ConditionMapEntity> result = this.getByFilter("WHERE cm.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public ConditionMapEntity getByName(String name) {
		List<ConditionMapEntity> result = this.getByFilter("WHERE lower(cm.name) = '"+name.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public ConditionMapEntity getByMethodReference(String method) {
		List<ConditionMapEntity> result = this.getByFilter("WHERE lower(cm.methodreference) = '"+method.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(ConditionMapEntity entity) {
		log.debug("save()");
		String sql = "INSERT INTO flow.conditionmap (id,name,description,type,methodreference,log_active,versionid) "
				    + "VALUES ("+entity.getId()+",'"+entity.getName()+"','"+entity.getDescription()+"','"+entity.getType()+"','"+entity.getMethodReference()+"','"+entity.getLogActive()+"','"+entity.getVersionId()+"') ";
		boolean result = true;
		DbConnection db = new DbConnection("ConditionMapDAO-save");
		result =  db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_ADD);
		return result;
		
	}

	@Override
	public boolean update(ConditionMapEntity entity) {
		// TODO Auto-generated method stub
		log.debug("update()");
		String sql = "UPDATE flow.conditionmap SET name = '"+entity.getName()+"',description='"+entity.getDescription()+"',type = '"+entity.getType()+"',methodreference='"+entity.getMethodReference()+"',log_active='"+entity.getLogActive()+"' ,versionid='"+entity.getVersionId()+"' "
   			    	+ "WHERE id = '"+entity.getId()+"'  ";
		boolean result = true;
		DbConnection db = new DbConnection("ConditionMapDAO-update");
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_UPDATE);
		return result;
		
	}

	@Override
	public boolean delete(ConditionMapEntity entity) {
		log.debug("delete()");
		String sql = "DELETE FROM flow.conditionmap WHERE id = '"+entity.getId()+"'  ";
		// TODO Auto-generated method stub
		boolean result = true;
		DbConnection db = new DbConnection("ConditionMapDAO-delete");
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_DELETE);
		return result;
	}

}
