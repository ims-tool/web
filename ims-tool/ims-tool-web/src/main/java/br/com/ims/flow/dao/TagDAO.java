package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class TagDAO extends AbstractDAO<TagEntity>{
	//private DbConnection db =  null;
	private static TagDAO instance = null;
	private TagDAO() {
		//db = new DbConnection("TagDAO"); 			
	}
	
	public static TagDAO getInstance() {
		if(instance == null) {
			instance = new TagDAO();
		}
		return instance;
	}

	public List<TagEntity> getByFilter(String where) {
		String sql = "SELECT t.id t_id, t.description t_description, t.versionid t_versionid,"+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+	
					 "FROM flow.tag t "+
					 "INNER JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
					 "<WHERE> ORDER BY t.id DESC";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		
		List<TagEntity> result = new ArrayList<TagEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("TagDAO-getByFilter");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				TagTypeEntity tagType = new TagTypeEntity();
				tagType.setId(rs.getString("tt_id"));
				tagType.setName(rs.getString("tt_name"));
				tagType.setDescription(rs.getString("tt_description"));
				
				TagEntity tag = new TagEntity();
				tag.setId(rs.getString("t_id"));
				tag.setDescription(rs.getString("t_description"));
				tag.setType(tagType);
				tag.setVersionId(rs.getString("t_versionid"));
				
				result.add(tag);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			db.finalize();
		}
		
		return result;
	}
	public List<TagEntity> getAll() {
		
		return this.getByFilter(null);
		
	}
	public TagEntity get(String id) {
		List<TagEntity> result = this.getByFilter("WHERE t.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;		
	}
	public boolean save(TagEntity tag) {
		String sql = "INSERT INTO flow.tag (id,tagtypeid,description,versionid) "+
				 "VALUES ('"+tag.getId()+"','"+tag.getType().getId()+"','"+tag.getDescription()+"',"+tag.getVersionId()+") ";
	             
		DbConnection db = new DbConnection("TagDAO-save");
		boolean result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(tag, Constants.AUDIT_TYPE_ADD);
		return result;
		
	}

	@Override
	public boolean update(TagEntity entity) {
		// TODO Auto-generated method stub
		String sql = "UPDATE flow.tag SET typetypeid='"+entity.getType().getId()+"',description='"+entity.getDescription()+"',versionid='"+entity.getVersionId()+"' "+
					 "WHERE id '"+entity.getId()+"' ";

	             
		DbConnection db = new DbConnection("TagDAO-update");
		boolean result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_UPDATE);
		return result;
	}

	@Override
	public boolean delete(TagEntity entity) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM flow.tag  WHERE id '"+entity.getId()+"' ";

            
		DbConnection db = new DbConnection("TagDAO-delete");
		boolean result = db.ExecuteSql(sql);
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_DELETE);
		return result;
		
	}

}
