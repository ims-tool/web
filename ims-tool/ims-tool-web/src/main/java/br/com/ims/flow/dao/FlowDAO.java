package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.FlowEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class FlowDAO extends AbstractDAO<FlowEntity>{
	private static FlowDAO instance = null;
	private DbConnection db =  null;
	private FlowDAO() {
		db =  new DbConnection("");
	}
	
	public static FlowDAO getInstance() {
		if(instance == null) {
			instance = new FlowDAO();
		}
		return instance;
	}
	
	public List<FlowEntity> getByFilter(String where) {
		
		String sql = "SELECT f.id f_id,f.name f_name,f.description f_description,f.flowname f_flowname,f.nextform f_nextform, "+
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
					 "FROM flow.flow f "+
					 "LEFT JOIN flow.tag t ON f.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
					 "<WHERE> "+
				     "ORDER BY f.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		List<FlowEntity> result = new ArrayList<FlowEntity>();
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
				
				
				FlowEntity flow = new FlowEntity();
				flow.setId(rs.getString("f_id"));
				flow.setName(rs.getString("f_name"));
				flow.setDescription(rs.getString("f_description"));
				flow.setFlowName(rs.getString("f_flowname"));
				flow.setNextForm(rs.getString("f_nextform"));
				flow.setTag(tag);
				
				result.add(flow);
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
	public List<FlowEntity> getAll() {
		return this.getByFilter(null);
	}
	public FlowEntity get(String id) {
		List<FlowEntity> result = this.getByFilter("WHERE f.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;		
	}
	
	public boolean save(FlowEntity entity) {
		boolean result = true;
		
		String sql = "INSERT INTO flow.flow (id,name,description,flowname,nextform,tag,versionid) "+
					 "VALUES ('"+entity.getId()+"','"+entity.getName()+"','"+entity.getDescription()+"','"+entity.getFlowName()+"',"+
					 entity.getNextForm()+","+
					 (entity.getTag() == null ? "NULL" : entity.getTag().getId())+",'"+entity.getVersionId().getId()+"') ";
		             
		result = db.ExecuteSql(sql);
		return result;
	}

	@Override
	public boolean update(FlowEntity entity) {
		boolean result = true;
		String sql = "UPDATE flow.flow SET name='"+entity.getName()+"',description='"+entity.getDescription()+"',flowname='"+entity.getFlowName()+"',"
				   + "nextform='"+entity.getNextForm()+"',"
				   + "tag="+(entity.getTag() == null ? "NULL" : entity.getTag().getId())+",versionid='"+entity.getVersionId().getId()+"' "+
					 "WHERE id = '"+entity.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

	@Override
	public boolean delete(FlowEntity entity) {
		boolean result = true;
		String sql = "DELETE FROM flow.flow WHERE id = '"+entity.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

}
