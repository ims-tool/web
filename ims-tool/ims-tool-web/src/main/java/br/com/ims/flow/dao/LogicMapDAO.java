package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.LogicMapEntity;
import br.com.ims.flow.model.MapTypeEntity;

@SuppressWarnings("serial")
public class LogicMapDAO extends AbstractDAO<LogicMapEntity> {
	//private DbConnection db =  null;
	public static Logger log = Logger.getLogger(LogicMapDAO.class);
	private static LogicMapDAO instance = null;
	private LogicMapDAO() {
		//db = new DbConnection("ConditionMapDAO"); 			
	}
	
	public static LogicMapDAO getInstance() {
		if(instance == null) {
			instance = new LogicMapDAO();
		}
		return instance;
	}
	public List<LogicMapEntity> getByFilter(String where) {
		log.debug("getByFilter("+where+")");
		String sql = "SELECT lm.id lm_id, lm.name lm_name,lm.description lm_description, lm.methodreference lm_methodreference, lm.returntype lm_returntype, lm.log_active lm_log_active, lm.versionid lm_versionid,"
					+ "mt.id mt_id, mt.name mt_name, mt.description mt_description "
				    + "FROM flow.logicmap lm "
				    + "INNER JOIN flow.maptype mt ON lm.maptypeid = mt.id "
				    + "<WHERE> "
				    + "ORDER BY lm.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<LogicMapEntity> result = new ArrayList<LogicMapEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("LogicMapDAO-getByFilter");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				
				LogicMapEntity logicMap = new LogicMapEntity();
				logicMap.setId(rs.getString("lm_id"));
				logicMap.setName(rs.getString("lm_name"));
				logicMap.setDescription(rs.getString("lm_description"));
				MapTypeEntity mapType = new MapTypeEntity();
				mapType.setId(rs.getString("mt_id"));
				mapType.setName(rs.getString("mt_name"));
				mapType.setDescription(rs.getString("mt_description"));
				logicMap.setMapType(mapType);
				logicMap.setReturnType(rs.getString("lm_returntype"));
				logicMap.setMethodReference(rs.getString("lm_methodreference"));
				logicMap.setLogActive(rs.getInt("lm_log_active"));	
				logicMap.setVersionId(rs.getString("lm_versionid")); 
				
				result.add(logicMap);
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
	
	public List<LogicMapEntity> getAll() {
		
		return this.getByFilter(null);
		
	}
	public LogicMapEntity get(String id) {
		List<LogicMapEntity> result = this.getByFilter("WHERE lm.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public LogicMapEntity getByName(String name) {
		List<LogicMapEntity> result = this.getByFilter("WHERE lower(lm.name) = '"+name.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public LogicMapEntity getByMethodReference(String method) {
		List<LogicMapEntity> result = this.getByFilter("WHERE lower(lm.methodreference) = '"+method.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(LogicMapEntity entity) {
		log.debug("save()");
		String sql = "INSERT INTO flow.logicmap (id,name,description,maptypeid,methodreference,returntype,log_active,versionid) "
				    + "VALUES ("+entity.getId()+",'"+entity.getName()+"','"+entity.getDescription()+"','"+entity.getMapType().getId()+"','"+entity.getMethodReference()+"','"+entity.getReturnType()+"','"+entity.getLogActive()+"','"+entity.getVersionId()+"') ";
		boolean result = true;
		DbConnection db = new DbConnection("LogicMapDAO-save");
		result =  db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_ADD);
		return result;
		
	}

	@Override
	public boolean update(LogicMapEntity entity) {
		// TODO Auto-generated method stub
		log.debug("update()");
		String sql = "UPDATE flow.logicmap SET name = '"+entity.getName()+"',description='"+entity.getDescription()+"',returntype = '"+entity.getReturnType()+"',methodreference='"+entity.getMethodReference()+"',maptypeid='"+entity.getMapType().getId()+"',log_active='"+entity.getLogActive()+"' ,versionid='"+entity.getVersionId()+"' "
   			    	+ "WHERE id = '"+entity.getId()+"'  ";
		boolean result = true;
		DbConnection db = new DbConnection("LogicMapDAO-update");
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_UPDATE);
		return result;
		
	}

	@Override
	public boolean delete(LogicMapEntity entity) {
		log.debug("delete()");
		String sql = "DELETE FROM flow.logicmap WHERE id = '"+entity.getId()+"'  ";
		// TODO Auto-generated method stub
		boolean result = true;
		DbConnection db = new DbConnection("LogicMapDAO-delete");
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_DELETE);
		return result;
	}

}
