package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AnnounceEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class AnnounceDAO extends AbstractDAO<AnnounceEntity>{
	public static Logger log = Logger.getLogger(AnnounceDAO.class);
	private static AnnounceDAO instance = null;
	//private DbConnection db =  null;
	private AnnounceDAO() {
		//db =  new DbConnection("AnnounceDAO");
	}
	
	public static AnnounceDAO getInstance() {
		if(instance == null) {
			instance = new AnnounceDAO();
		}
		return instance;
	}
	public List<AnnounceEntity> getByFilter(String where) {
		return getByFilter(where,false);
	}
	
	public List<AnnounceEntity> getByFilter(String where,boolean lazy) {
		log.debug("getByFilter("+where+","+lazy+")");	
		String sql = "SELECT a.id a_id,a.name a_name,a.description a_description,a.flushprompt a_flushprompt,a.prompt a_prompt,a.nextform a_nextform, a.versionid a_versionid,"+
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
					 "FROM flow.announce a "+
					 "LEFT JOIN flow.tag t ON a.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
					 "<WHERE> "+
				     "ORDER BY a.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} 
		sql = sql.replace("<WHERE>", "");
		
		List<AnnounceEntity> result = new ArrayList<AnnounceEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("AnnounceDAO-getByFilter");
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
				PromptEntity prompt = null;
				if(!lazy) {
					if(rs.getString("a_prompt") != null && rs.getString("a_prompt").length() > 0) {
						prompt = ServicesFactory.getInstance().getPromptService().get(rs.getString("a_prompt"));
					}
				}
				
				AnnounceEntity announce = new AnnounceEntity();
				announce.setId(rs.getString("a_id"));
				announce.setName(rs.getString("a_name"));
				announce.setDescription(rs.getString("a_description"));
				announce.setFlushprompt(rs.getInt("a_flushprompt"));
				announce.setNextForm(rs.getString("a_nextform"));
				announce.setPrompt(prompt);
				announce.setVersionId(rs.getString("a_versionid"));
				announce.setTag(tag);
				
				result.add(announce);
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
	public List<AnnounceEntity> getAll() {
		return this.getByFilter(null);
	}
	public AnnounceEntity get(String id) {
		List<AnnounceEntity> result = this.getByFilter("WHERE a.id = "+id);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
		
	}
	
	public boolean save(AnnounceEntity announce) {
		log.debug("save()");
		System.out.println("AnnounceDAO-save()");
		boolean result = true;
		
		String sql = "INSERT INTO flow.announce (id,name,description,flushprompt,prompt,nextform,tag,versionid) "+
					 "VALUES ('"+announce.getId()+"','"+announce.getName()+"','"+announce.getDescription()+"','"+announce.getFlushprompt()+"',"
					 		+ (announce.getPrompt() == null ? "NULL" : announce.getPrompt().getId())+","
					 		+ (announce.getNextForm() == null || announce.getNextForm().length() == 0 ? "NULL" : announce.getNextForm() )+","
					 		+(announce.getTag() == null ? "NULL" : announce.getTag().getId())+",'"+announce.getVersionId()+"') ";
		DbConnection db = new DbConnection("AnnounceDAO-save");             
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
	}

	@Override
	public boolean update(AnnounceEntity announce) {
		log.debug("update()");
		System.out.println("AnnounceDAO-update()");
		boolean result = true;
		String sql = "UPDATE flow.announce SET name='"+announce.getName()+"',description='"+announce.getDescription()+"',"
				   + "flushprompt='"+announce.getFlushprompt()+"',"
				   + "prompt="+(announce.getPrompt() == null ? "NULL" : announce.getPrompt().getId())+","
				   + "nextform="+(announce.getNextForm() == null || announce.getNextForm().length() == 0 ? "NULL" : announce.getNextForm())+","
				   + "tag="+(announce.getTag() == null ? "NULL" : announce.getTag().getId())+",versionid='"+announce.getVersionId()+"' "+
					 "WHERE id = '"+announce.getId()+"' ";
		DbConnection db = new DbConnection("AnnounceDAO-update");             
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

	@Override
	public boolean delete(AnnounceEntity announce) {
		boolean result = true;
		log.debug("delete()");
		System.out.println("AnnounceDAO-delete()");
		String sql = "DELETE FROM flow.announce WHERE id = '"+announce.getId()+"' ";
		DbConnection db = new DbConnection("AnnounceDAO-delete");             
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

}
