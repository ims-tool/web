package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.OperationMapEntity;

@SuppressWarnings("serial")
public class OperationMapDAO extends AbstractDAO<OperationMapEntity> {
	//private DbConnection db =  null;
	private static OperationMapDAO instance = null;
	private OperationMapDAO() {
		//db =  new DbConnection("OperationMapDAO"); 			
	}
	
	public static OperationMapDAO getInstance() {
		if(instance == null) {
			instance = new OperationMapDAO();
		}
		return instance;
	}

	public List<OperationMapEntity> getByFilter(String where) {

		String sql = "SELECT om.id om_id, om.name om_name,om.description om_description, om.methodreference om_methodreference, om.log_active om_log_active,om.versionid om_versionid "
			    + "FROM flow.operationmap om "
			    + "<WHERE> "
			    + "ORDER BY om.name";
	if(where != null && where.length() > 0) {
		sql = sql.replace("<WHERE>", where);
	} else {
		sql = sql.replace("<WHERE>", "");
	}
	List<OperationMapEntity> result = new ArrayList<OperationMapEntity>();
	ResultSet rs = null;
	DbConnection db = new DbConnection("OperationMapDAO-getByFilter");
	try {
		rs = db.ExecuteQuery(sql);
		while(rs.next()) {
			
			OperationMapEntity operationMap = new OperationMapEntity();
			operationMap.setId(rs.getString("om_id"));
			operationMap.setName(rs.getString("om_name"));
			operationMap.setDescription(rs.getString("om_description"));
			operationMap.setMethodReference(rs.getString("om_methodreference"));
			operationMap.setLogActive(rs.getInt("om_log_active"));	
			operationMap.setVersionId(rs.getString("om_versionid")); 
			
			result.add(operationMap);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		
		db.finalize();
	}
	
	return result;
	}
	
	public List<OperationMapEntity> getAll() {
		
		return this.getByFilter(null);
	}
	public OperationMapEntity getByName(String name) {
		List<OperationMapEntity> result =this.getByFilter("WHERE lower(om.name) = '"+name.toLowerCase()+"'");
		
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public OperationMapEntity getByMethodReference(String methodReference) {
		List<OperationMapEntity> result =this.getByFilter("WHERE lower(om.methodreference) = '"+methodReference.toLowerCase()+"'");
		
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public OperationMapEntity get(String id) {
		List<OperationMapEntity> result =this.getByFilter("WHERE om.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(OperationMapEntity entity) {
		String sql = "INSERT INTO flow.operationmap (id,name,description,methodreference,log_active,versionid) "
			    	+ "VALUES ("+entity.getId()+",'"+entity.getName()+"','"+entity.getDescription()+"','"+entity.getMethodReference()+"','"+entity.getLogActive()+"','"+entity.getVersionId()+"') ";
		boolean result = true;
		DbConnection db = new DbConnection("OperationMapDAO-save");
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_ADD);
		return result;
	}

	@Override
	public boolean update(OperationMapEntity entity) {
		// TODO Auto-generated method stub
		String sql = "UPDATE flow.operationmap SET name = '"+entity.getName()+"',description='"+entity.getDescription()+"',methodreference='"+entity.getMethodReference()+"',log_active='"+entity.getLogActive()+"',versionid='"+entity.getVersionId()+"' "
			    	+ "WHERE id = '"+entity.getId()+"'  ";
	
		boolean result = true;
		DbConnection db = new DbConnection("OperationMapDAO-update");
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_UPDATE);
		return result;
	}

	@Override
	public boolean delete(OperationMapEntity entity) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM flow.operationmap WHERE id = '"+entity.getId()+"'  ";
		// TODO Auto-generated method stub
		boolean result = true;
		DbConnection db = new DbConnection("OperationMapDAO-delete");
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_DELETE);
		return result;
		
	}

}
