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
	private VersionDAO() {
		 			
	}
	
	public static VersionDAO getInstance() {
		if(instance == null) {
			instance = new VersionDAO();
		}
		return instance;
	}
	
	public List<VersionEntity> getAll() {
		
		List<VersionEntity> result = new ArrayList<VersionEntity>();
		
		DbConnection db = new DbConnection("");
		String sql = "SELECT id,description,system_user,to_char(date_create,'DD/MM/YYYY HH24:MI:DD') date_create FROM flow.version ";
		
		try {
			ResultSet rs = db.ExecuteQuery(sql);
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
			db.finalize();
		}
		
		return result;

	}
	public VersionEntity get(String id) {
		DbConnection db = new DbConnection("");
		String sql = "SELECT id,description,system_user,to_char(date_create,'DD/MM/YYYY HH24:MI:DD') date_create FROM flow.version WHERE id ='"+id+"' ";
		VersionEntity version = null;
		try {
			ResultSet rs = db.ExecuteQuery(sql);
			if(rs.next()) {
				version = new VersionEntity();
				version.setId(rs.getString("id"));
				version.setDescription(rs.getString("description"));
				version.setSystem_user(rs.getString("system_user"));
				version.setDate_create(Util.dateFormat(rs.getString("date_create")));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.finalize();
		}
		return version;
	}
	
	public boolean save(VersionEntity entity) {
		boolean result = true;
		DbConnection db = new DbConnection("");
		String sql = "INSERT INTO flow.version (id,description,system_user) VALUES "+
	                 "('"+entity.getId()+"','"+entity.getDescription()+"','"+entity.getSystem_user()+"') ";
		result = db.ExecuteSql(sql);
		db.finalize();
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
