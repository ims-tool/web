package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.AnswerEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class AnswerDAO extends AbstractDAO<AnswerEntity>{
	private static AnswerDAO instance = null;
	private DbConnection db =  null;
	private AnswerDAO() {
		db =  new DbConnection("AnswerDAO");
	}
	
	public static AnswerDAO getInstance() {
		if(instance == null) {
			instance = new AnswerDAO();
		}
		return instance;
	}
	
	public List<AnswerEntity> getByFilter(String where) {
		
		String sql = "SELECT a.id a_id,a.name a_name,a.description a_description,a.nextform a_nextform, "+
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
					 "FROM flow.answer a "+
					 "LEFT JOIN flow.tag t ON a.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
					 "<WHERE> "+
				     "ORDER BY a.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		List<AnswerEntity> result = new ArrayList<AnswerEntity>();
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
				
				
				AnswerEntity answer = new AnswerEntity();
				answer.setId(rs.getString("a_id"));
				answer.setName(rs.getString("a_name"));
				answer.setDescription(rs.getString("a_description"));
				answer.setNextForm(rs.getString("a_nextform"));
				answer.setTag(tag);
				
				result.add(answer);
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
	public List<AnswerEntity> getAll() {
		return this.getByFilter(null);
	}
	public AnswerEntity get(String id) {
		List<AnswerEntity> result = this.getByFilter("WHERE a.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;		
	}
	
	public boolean save(AnswerEntity answer) {
		boolean result = true;
		
		String sql = "INSERT INTO flow.answer (id,name,description,nextform,tag,versionid) "+
					 "VALUES ('"+answer.getId()+"','"+answer.getName()+"','"+answer.getDescription()+"','"+answer.getNextForm()+"',"
					 		+ (answer.getTag() == null ? "NULL" : answer.getTag().getId())+",'"+answer.getVersionId().getId()+"') ";
		             
		result = db.ExecuteSql(sql);
		return result;
	}

	@Override
	public boolean update(AnswerEntity answer) {
		boolean result = true;
		String sql = "UPDATE flow.answer SET name='"+answer.getName()+"',description='"+answer.getDescription()+"',nextform='"+answer.getNextForm()+"',"
				   + "tag="+(answer.getTag() == null ? "NULL" : answer.getTag().getId())+",versionid='"+answer.getVersionId().getId()+"' "+
					 "WHERE id = '"+answer.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

	@Override
	public boolean delete(AnswerEntity answer) {
		boolean result = true;
		String sql = "DELETE FROM flow.answer WHERE id = '"+answer.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

}
