package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
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
		List<ControlPanelEntity> result = new ArrayList<ControlPanelEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("ControlPanelDAO-getByFilter");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				
				ControlPanelEntity controlPanel = new ControlPanelEntity();
				controlPanel.setId(rs.getString("cp_id"));
				controlPanel.setMethodname(rs.getString("cp_methodname"));
				controlPanel.setDescription(rs.getString("cp_description"));
				controlPanel.setOwner(rs.getString("cp_owner"));
				controlPanel.setReferencedBy(rs.getString("cp_referencedby"));
				controlPanel.setStatus(rs.getString("cp_status"));
				controlPanel.setLoginid(rs.getString("cp_loginid"));
				controlPanel.setStartdate(rs.getString("cp_startdate"));
				controlPanel.setVersionId(rs.getString("versionid"));
				controlPanel.setTimeout(rs.getInt("cp_timeout"));
				
					
				result.add(controlPanel);
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
		List<ControlPanelEntity> result = this.getByFilter("WHERE cp.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public ControlPanelEntity getByMethod(String method) {
		List<ControlPanelEntity> result = this.getByFilter("WHERE lower(cp.methodname) = '"+method.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(ControlPanelEntity entity) {
		
		String sql = "INSERT INTO flow.controlpanel (id,methodname,description,owner,referencedby,status,loginid,startdate,"
				    + "versionid,timeout,internalservice) "
				    + "VALUES ("+Util.getUID()+",'"+entity.getMethodname()+"','"+entity.getDescription()+"','"+entity.getOwner()+"','"
				    + entity.getReferencedBy()+"','"+entity.getStatus()+"','"+entity.getLoginid()+"',"
				    + "to_date('"+entity.getStartdate()+"','DD/MM/YYYY HH24:MI:SS'),'"+entity.getVersionId()+"','"+entity.getTimeout()+"',"
				    + entity.getInternalService()+") ";
		boolean result = true;
		DbConnection db = new DbConnection("ControlPanelDAO-save");
		result =  db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_ADD);
		return result;
		
	}

	@Override
	public boolean update(ControlPanelEntity entity) {
		// TODO Auto-generated method stub
		String sql = "UPDATE flow.controlpanel SET methodname = '"+entity.getMethodname()+"',description='"+entity.getDescription()+"',"
					+ "owner = '"+entity.getOwner()+"',referencedby='"+entity.getReferencedBy()+"',status='"+entity.getStatus()+"' ,"
					+ "loginid='"+entity.getLoginid()+"',startdate=to_date('"+entity.getStartdate()+"','DD/MM/YYYY HH24:MI:SS'), "
					+ "versionid='"+entity.getVersionId()+"',timeout='"+entity.getTimeout()+"',internalservice='"+entity.getInternalService()+"' "
   			    	+ "WHERE id = '"+entity.getId()+"'  ";
		boolean result = true;
		DbConnection db = new DbConnection("ControlPanelDAO-update");
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_UPDATE);
		return result;
		
	}

	@Override
	public boolean delete(ControlPanelEntity entity) {
		String sql = "DELETE FROM flow.controlpanel WHERE id = '"+entity.getId()+"'  ";
		// TODO Auto-generated method stub
		boolean result = true;
		DbConnection db = new DbConnection("ControlPanelDAO-delete");
		result = db.ExecuteSql(sql);
		if(result)
			db.finalize();
		return result;
	}

}
