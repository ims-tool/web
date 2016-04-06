package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class NoMatchInputDAO extends AbstractDAO<NoMatchInputEntity> {
	private DbConnection db =  null;
	private static NoMatchInputDAO instance = null;
	private NoMatchInputDAO() {
		db =  new DbConnection(""); 			
	}
	
	public static NoMatchInputDAO getInstance() {
		if(instance == null) {
			instance = new NoMatchInputDAO();
		}
		return instance;
	}
	public List<NoMatchInputEntity> getByFilter(String where) {

		String sql = "SELECT n.id n_id,n.name n_name,n.type n_type,n.threshold n_threshold,n.prompt n_prompt,n.nextform n_nextform, "+
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
					 "FROM flow.nomatchinput n "+
					 "LEFT JOIN flow.tag t ON n.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
		     	 	 "<WHERE> "+
					 "ORDER BY n.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		
		List<NoMatchInputEntity> result = new ArrayList<NoMatchInputEntity>();
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
				PromptEntity prompt = null;
				if(rs.getString("n_prompt") != null && rs.getString("n_prompt").length() >0) {
					prompt = ServicesFactory.getInstance().getPromptService().get(rs.getString("n_prompt"));
				}
				
				
				NoMatchInputEntity ni = new NoMatchInputEntity();
				ni.setId(rs.getString("n_id"));
				ni.setName(rs.getString("n_name"));
				ni.setType(rs.getString("n_type"));
				ni.setThreshold(rs.getInt("n_threshold"));
				ni.setNextForm(rs.getString("n_nextform"));
				ni.setTag(tag);
				ni.setPrompt(prompt);
				
				result.add(ni);
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
	public List<NoMatchInputEntity> getAll() {
		
		return this.getByFilter(null);		
	}
	public NoMatchInputEntity get(String id) {
		List<NoMatchInputEntity> result = this.getByFilter("WHERE n.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public NoMatchInputEntity getByName(String name) {
		List<NoMatchInputEntity> result = this.getByFilter("WHERE lower(n.name) = '"+name.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public boolean save(NoMatchInputEntity entity) {
		boolean result = true;
		
		String sql = "INSERT INTO flow.nomatchinput (id,name,type,threshold,prompt,nextform,tag,versionid) "+
					 "VALUES ('"+entity.getId()+"','"+entity.getName()+"','"+entity.getType()+"','"+entity.getThreshold()+"',"
				   + (entity.getPrompt() ==null ? "NULL" : entity.getPrompt().getId())+","+entity.getNextForm()+","
					+(entity.getTag() == null ? "NULL" : entity.getTag().getId())+",'"+entity.getVersionId().getId()+"') ";
		             
		result = db.ExecuteSql(sql);
		return result;
	}

	@Override
	public boolean update(NoMatchInputEntity entity) {
		boolean result = true;
		
		String sql = "UPDATE flow.nomatchinput SET name='"+entity.getName()+"',type='"+entity.getType()+"',threshold='"+entity.getThreshold()+"',"
				   + "prompt="+(entity.getPrompt() == null ? "NULL" : entity.getPrompt().getId())+","
				   + "nextform='"+entity.getNextForm()+"',"
				   + "tag="+(entity.getTag() == null ? "NULL" : entity.getTag().getId())+","
				   + "versionid='"+entity.getVersionId().getId()+"' "
				   + "WHERE id = '"+entity.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

	@Override
	public boolean delete(NoMatchInputEntity entity) {
		boolean result = true;
		
		String sql = "DELETE FROM flow.nomatchinput WHERE id = '"+entity.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}
	

	

}
