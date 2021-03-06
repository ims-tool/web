package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.AnswerEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class AnswerDAO extends AbstractDAO<AnswerEntity>{
	public static Logger log = Logger.getLogger(AnswerDAO.class);
	private static AnswerDAO instance = null;
	//private DbConnection db =  null;
	private AnswerDAO() {
		//db =  new DbConnection("AnswerDAO");
	}
	
	public static AnswerDAO getInstance() {
		if(instance == null) {
			instance = new AnswerDAO();
		}
		return instance;
	}
	
	public List<AnswerEntity> getByFilter(String where) {
		log.debug("getByFilter("+where+")");
		String sql = "SELECT a.id a_id,a.name a_name,a.description a_description,a.nextform a_nextform,a.versionid a_versionid, "+
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
		DbConnection db = new DbConnection("AnswerDAO-getByFilter");
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
				answer.setVersionId(rs.getString("a_versionid"));
				
				result.add(answer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
			/*try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};*/
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
		log.debug("save()");
		String sql = "INSERT INTO flow.answer (id,name,description,nextform,tag,versionid) "+
					 "VALUES ('"+answer.getId()+"','"+answer.getName()+"','"+answer.getDescription()+"',"
					 		+(answer.getNextForm() ==  null || answer.getNextForm().length() == 0 ? "NULL" : answer.getNextForm())+","
					 		+ (answer.getTag() == null ? "NULL" : answer.getTag().getId())+",'"+answer.getVersionId()+"') ";
		             
		DbConnection db = new DbConnection("AnswerDAO-save");
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result) {
			Util.audit(answer, Constants.AUDIT_TYPE_ADD);
		}
		return result;
	}

	@Override
	public boolean update(AnswerEntity answer) {
		boolean result = true;
		log.debug("update()");
		String sql = "UPDATE flow.answer SET name='"+answer.getName()+"',description='"+answer.getDescription()+"',"
					+ "nextform="+(answer.getNextForm() == null || answer.getNextForm().length() == 0 ? "NULL" : answer.getNextForm())+","
					+ "tag="+(answer.getTag() == null ? "NULL" : answer.getTag().getId())+",versionid='"+answer.getVersionId()+"' "+
					 "WHERE id = '"+answer.getId()+"' ";
		DbConnection db = new DbConnection("AnswerDAO-update");             
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result) {
			Util.audit(answer, Constants.AUDIT_TYPE_UPDATE);
		}

		return result;
		
	}

	@Override
	public boolean delete(AnswerEntity answer) {
		boolean result = true;
		log.debug("delete()");
		String sql = "DELETE FROM flow.answer WHERE id = '"+answer.getId()+"' ";
		DbConnection db = new DbConnection("AnswerDAO-delete");             
		result = db.ExecuteSql(sql);
		db.finalize();
		if(result) {
			Util.audit(answer, Constants.AUDIT_TYPE_DELETE);
		}

		return result;
		
	}

}
