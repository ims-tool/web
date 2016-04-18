package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.ConditionMapEntity;
import br.com.ims.flow.model.ControlPanelEntity;

@SuppressWarnings("serial")
public class ControlPanelDAO extends AbstractDAO<ControlPanelEntity> {
	//
	private static ControlPanelDAO instance = null;
	private ControlPanelDAO() {
		//db = new DbConnection("ConditionMapDAO"); 			
	}
	
	public static ControlPanelDAO getInstance() {
		if(instance == null) {
			instance = new ControlPanelDAO();
		}
		return instance;
	}
	public List<ControlPanelEntity> getByFilter(String where) {
		String sql = "select cp.id cp_id, cp.methodname cp_methodname, cp.description cp_description,cp.owner cp_owner, cp.referencedby cp_referencedby, "
				+ "cp.status cp_status,cp.loginid cp_loginid, to_char(cp.startdate,'DD/MM/YYYY HH24:MI:SS') cp_startdate,cp.versionid cp_versionid,cp.timeout cp_timeout "
				+ "from flow.controlpanel cp "
				+ "<WHERE> "
				+ "ORDER BY cp.methodname ";
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
		} finally {
			db.finalize();
			
		}
		
		return result;
	}
	
	public List<ControlPanelEntity> getAll() {
		
		return this.getByFilter(null);
		
	}
	public ControlPanelEntity get(String id) {
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
	public ControlPanelEntity getByMethodReference(String method) {
		List<ConditionMapEntity> result = this.getByFilter("WHERE lower(cm.methodreference) = '"+method.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(ControlPanelEntity entity) {
		
		String sql = "INSERT INTO flow.conditionmap (id,name,description,type,methodreference,log_active,versionid) "
				    + "VALUES ("+entity.getId()+",'"+entity.getName()+"','"+entity.getDescription()+"','"+entity.getType()+"','"+entity.getMethodReference()+"','"+entity.getLogActive()+"','"+entity.getVersionId()+"') ";
		boolean result = true;
		DbConnection db = new DbConnection("ConditionMapDAO-save");
		result =  db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

	@Override
	public boolean update(ControlPanelEntity entity) {
		// TODO Auto-generated method stub
		String sql = "UPDATE flow.conditionmap SET name = '"+entity.getName()+"',description='"+entity.getDescription()+"',type = '"+entity.getType()+"',methodreference='"+entity.getMethodReference()+"',log_active='"+entity.getLogActive()+"' ,versionid='"+entity.getVersionId()+"' "
   			    	+ "WHERE id = '"+entity.getId()+"'  ";
		boolean result = true;
		DbConnection db = new DbConnection("ConditionMapDAO-update");
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

	@Override
	public boolean delete(ControlPanelEntity entity) {
		String sql = "DELETE FROM flow.conditionmap WHERE id = '"+entity.getId()+"'  ";
		// TODO Auto-generated method stub
		boolean result = true;
		DbConnection db = new DbConnection("ConditionMapDAO-delete");
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
	}

}
