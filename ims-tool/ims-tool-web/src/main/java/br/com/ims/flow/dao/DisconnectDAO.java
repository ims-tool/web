package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.DisconnectEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class DisconnectDAO extends AbstractDAO<DisconnectEntity>{
	private static DisconnectDAO instance = null;
	public static Logger log = Logger.getLogger(DisconnectDAO.class);
	//private DbConnection db =  null;
	private DisconnectDAO() {
		//db =  new DbConnection("DisconnectDAO");
	}
	
	public static DisconnectDAO getInstance() {
		if(instance == null) {
			instance = new DisconnectDAO();
		}
		return instance;
	}
	
	public List<DisconnectEntity> getByFilter(String where) {
	
		log.debug("getByFilter("+where+")");
		String sql = "SELECT d.id d_id,d.name d_name,d.description d_description,d.versionid d_versionid, "+
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
					 "FROM flow.disconnect d "+
					 "LEFT JOIN flow.tag t ON d.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
				     "<WHERE> "+ 
					 "ORDER BY d.name";
		if(where != null && where.length() >0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		
		List<DisconnectEntity> result = new ArrayList<DisconnectEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("DisconnectDAO-getByFilter");
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
				
				
				DisconnectEntity disconnect = new DisconnectEntity();
				disconnect.setId(rs.getString("d_id"));
				disconnect.setName(rs.getString("d_name"));
				disconnect.setDescription(rs.getString("d_description"));
				disconnect.setTag(tag);
				disconnect.setVersionId(rs.getString("d_versionid"));
				
				result.add(disconnect);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
		}
		
		return result;
		
	}
	public List<DisconnectEntity> getAll() {
		return this.getByFilter(null);
	}
	public DisconnectEntity get(String id) {
		List<DisconnectEntity> result = this.getByFilter("WHERE d.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;	
	}
	
	public boolean save(DisconnectEntity disconnect) {
		boolean result = true;
		log.debug("save()");
		String sql = "INSERT INTO flow.disconnect (id,name,description,tag,versionid) "+
					 "VALUES ('"+disconnect.getId()+"','"+disconnect.getName()+"','"+disconnect.getDescription()+"',"
					 		+ (disconnect.getTag() == null ? "NULL" : disconnect.getTag().getId())+",'"+disconnect.getVersionId()+"') ";
		DbConnection db = new DbConnection("DecisionDAO-save");             
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
	}

	@Override
	public boolean update(DisconnectEntity disconnect) {
		boolean result = true;
		log.debug("update()");
		String sql = "UPDATE flow.disconnect SET name='"+disconnect.getName()+"',description='"+disconnect.getDescription()+"',"
				   + "tag="+(disconnect.getTag() == null ? "NULL" : disconnect.getTag().getId())+",versionid='"+disconnect.getVersionId()+"' "+
					 "WHERE id = '"+disconnect.getId()+"' ";
		DbConnection db = new DbConnection("DecisionDAO-update");
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

	@Override
	public boolean delete(DisconnectEntity disconnect) {
		boolean result = true;
		log.debug("delete()");
		String sql = "DELETE FROM flow.disconnect WHERE id = '"+disconnect.getId()+"' ";
		DbConnection db = new DbConnection("DecisionDAO-delete");
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

}
