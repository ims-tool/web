package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.ReturnEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

public class ReturnDAO extends AbstractDAO<ReturnEntity>{
	private static ReturnDAO instance = null;
	private DbConnection db =  null;
	private ReturnDAO() {
		db =  new DbConnection("");
	}
	
	public static ReturnDAO getInstance() {
		if(instance == null) {
			instance = new ReturnDAO();
		}
		return instance;
	}
	
	public List<ReturnEntity> getByFilter(String where) {
		
		String sql = "SELECT r.id r_id,r.name r_name,r.description r_description, "+
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
					 "FROM flow.return r "+
					 "LEFT JOIN flow.tag t ON r.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
					 "<WHERE> "+
				     "ORDER BY r.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		
		List<ReturnEntity> result = new ArrayList<ReturnEntity>();
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				TagEntity tag = null;
				if(rs.getString("t_id") != null && rs.getString("t_id").length() > 0) {
					TagTypeEntity tagType = new TagTypeEntity();
					tagType.setId(rs.getString("tt_id"));
					tagType.setName(rs.getString("tt_name"));
					tagType.setDescription(rs.getString("tt_description"));
					
					tag = new TagEntity();
					tag.setId(rs.getString("t_id"));
					tag.setDescription(rs.getString("t_description"));
					tag.setType(tagType);
				}
				
				
				ReturnEntity return_ = new ReturnEntity();
				return_.setId(rs.getString("r_id"));
				return_.setName(rs.getString("r_name"));
				return_.setDescription(rs.getString("r_description"));
				return_.setTag(tag);
				
				result.add(return_);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};
		}
		
		return result;
		
	}
	public List<ReturnEntity> getAll() { 
		return this.getByFilter(null);
	}
	public ReturnEntity get(String id) {
		List<ReturnEntity> result = this.getByFilter("WHERE r.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	
	public boolean save(ReturnEntity _return) {
		boolean result = true;
		
		String sql = "INSERT INTO flow.return (id,name,description,tag,versionid) "+
					 "VALUES ('"+_return.getId()+"','"+_return.getName()+"','"+_return.getDescription()+"',"
					+(_return.getTag() == null ? "NULL" : _return.getTag().getId())+",'"+_return.getVersionId().getId()+"') ";
		             
		result = db.ExecuteSql(sql);
		return result;
	}

	@Override
	public boolean update(ReturnEntity _return) {
		boolean result = true;
		String sql = "UPDATE flow.return SET name='"+_return.getName()+"',description='"+_return.getDescription()+"',"
				   + "tag="+(_return.getTag() ==  null ? "NULL" :_return.getTag().getId())+"',versionid='"+_return.getVersionId().getId()+"' "+
					 "WHERE id = '"+_return.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

	@Override
	public boolean delete(ReturnEntity _return) {
		boolean result = true;
		String sql = "DELETE FROM flow.return WHERE id = '"+_return.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

}
