package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.VersionEntity;

public class VersionDAO extends AbstractDAO<VersionEntity> {
	private static VersionDAO instance = null;
	private static DbConnection db = null;
	private VersionDAO() {
		db = new DbConnection("");			
	}
	
	public static VersionDAO getInstance() {
		if(instance == null) {
			instance = new VersionDAO();
		}
		return instance;
	}
	
	public List<VersionEntity> getByFilter(String where) {
		
		List<VersionEntity> result = new ArrayList<VersionEntity>();
		
		String sql = "SELECT id,description,system_user,to_char(date_create,'DD/MM/YYYY HH24:MI:DD') date_create "
				+ " FROM flow.version <WHERE> ";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				VersionEntity version = new VersionEntity();
				version.setId(rs.getString("id"));
				version.setDescription(rs.getString("description"));
				version.setSystem_user(rs.getString("system_user"));
				version.setDate_create(Util.dateFormat(rs.getString("date_create")));
				
				result.add(version);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} catch(Exception e){
				
			}
		}
		
		return result;

	}
	public List<VersionEntity> getAll() {
		return this.getByFilter(null);
	}
	
	public VersionEntity get(String id) {
		List<VersionEntity> result = this.getByFilter("WHERE id = "+id);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	
	public boolean save(VersionEntity entity) {
		boolean result = true;
		String sql = "INSERT INTO flow.version (id,description,system_user) VALUES "+
	                 "('"+entity.getId()+"','"+entity.getDescription()+"','"+entity.getSystem_user()+"') ";
		result = db.ExecuteSql(sql);
		return result;
	}

	@Override
	public boolean update(VersionEntity entity) {
		// TODO Auto-generated method stub
		return true;
		
	}

	@Override
	public boolean delete(VersionEntity entity) {
		// TODO Auto-generated method stub
		return true;
	}

}
