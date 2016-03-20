package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptCollectEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

public class PromptCollectDAO extends AbstractDAO<PromptCollectEntity>{
	private static PromptCollectDAO instance = null;
	private DbConnection db =  null;
	private PromptCollectDAO() {
		db =  new DbConnection("");
	}
	
	public static PromptCollectDAO getInstance() {
		if(instance == null) {
			instance = new PromptCollectDAO();
		}
		return instance;
	}
	
	public List<PromptCollectEntity> getByFilter(String where) {
		String sql = "SELECT pc.id pc_id,pc.name pc_name,pc.description pc_description,pc.flushprompt pc_flushprompt,pc.prompt pc_prompt,pc.fetchtimeout pc_fetchtimeout, pc.interdigittimeout pc_interdigittimeout, pc.terminatingcharacter pc_terminatingcharacter, pc.nextform pc_nextform, "+
				 "g.id g_id, g.name g_name,g.description g_description,g.type g_type,g.sizemax g_sizemax,g.sizemin g_sizemin, "+
			     "ni.id ni_id,ni.name ni_name, ni.type ni_type,ni.threshold ni_threshold, ni.prompt ni_prompt, ni.nextform ni_nextform, "+
			     "nm.id nm_id,nm.name nm_name, nm.type nm_type,nm.threshold nm_threshold, nm.prompt nm_prompt, nm.nextform nm_nextform, "+
				 "t.id t_id, t.description t_description, "+ 
				 "tt.id tt_id, tt.name tt_name,tt.description tt_description, "+
				 "t_ni.id t_ni_id, t_ni.description t_ni_description, "+ 
				 "tt_ni.id tt_id, tt_ni.name tt_ni_name,tt_ni.description tt_ni_description, "+
				 "t_nm.id t_nm_id, t_nm.description t_nm_description, "+ 
				 "tt_nm.id tt_nm_id, tt_nm.name tt_nm_name,tt_nm.description tt_nm_description "+
				 "FROM flow.promptcollect pc "+
				 "INNER JOIN flow.grammar g ON pc.grammar = g.id "+
				 "INNER JOIN flow.nomatchinput ni ON ni.id = pc.noinput "+
				 "INNER JOIN flow.nomatchinput nm ON nm.id = pc.nomatch "+
				 "LEFT JOIN flow.tag t ON pc.tag = t.id "+ 
				 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
				 "LEFT JOIN flow.tag t_ni ON ni.tag = t_ni.id "+ 
				 "LEFT JOIN flow.tagtype tt_ni ON t_ni.tagtypeid = tt_ni.id "+
				 "LEFT JOIN flow.tag t_nm ON nm.tag = t_nm.id "+ 
				 "LEFT JOIN flow.tagtype tt_nm ON t_nm.tagtypeid = tt_nm.id "+
			     "<WHERE> "+
				 "ORDER BY pc.name";
	if(where != null && where.length() > 0) {
		sql = sql.replace("<WHERE>", where);
	} else {
		sql = sql.replace("<WHERE>", "");
	}
	List<PromptCollectEntity> result = new ArrayList<PromptCollectEntity>();
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
			TagEntity tag_ni = null;
			if(rs.getString("t_ni_id") != null && rs.getString("t_ni_id").length() > 0) {
				TagTypeEntity tagType_ni = new TagTypeEntity();
				tagType_ni.setId(rs.getString("tt_ni_id"));
				tagType_ni.setName(rs.getString("tt_ni_name"));
				tagType_ni.setDescription(rs.getString("tt_ni_description"));
				
				tag_ni = new TagEntity();
				tag_ni.setId(rs.getString("t_ni_id"));
				tag_ni.setDescription(rs.getString("t_ni_description"));
				tag_ni.setType(tagType_ni);
			}
			TagEntity tag_nm = null;
			if(rs.getString("t_nm_id") != null && rs.getString("t_nm_id").length() > 0) {
				TagTypeEntity tagType_nm = new TagTypeEntity();
				tagType_nm.setId(rs.getString("tt_nm_id"));
				tagType_nm.setName(rs.getString("tt_nm_name"));
				tagType_nm.setDescription(rs.getString("tt_nm_description"));
				
				tag_nm = new TagEntity();
				tag_nm.setId(rs.getString("t_nm_id"));
				tag_nm.setDescription(rs.getString("t_nm_description"));
				tag_nm.setType(tagType_nm);
			}
			
			PromptEntity prompt = ServicesFactory.getInstance().getPromptService().get(rs.getString("a_prompt"));
			PromptEntity prompt_ni = ServicesFactory.getInstance().getPromptService().get(rs.getString("ni_prompt"));
			PromptEntity prompt_nm = ServicesFactory.getInstance().getPromptService().get(rs.getString("nm_prompt"));
			
			
			
			NoMatchInputEntity noInput = new NoMatchInputEntity();
			noInput.setId(rs.getString("ni_id"));
			noInput.setName(rs.getString("ni_name"));
			noInput.setType(rs.getString("ni_type"));
			noInput.setThreshold(rs.getInt("ni_threshold"));
			noInput.setPrompt(prompt_ni);
			noInput.setNextForm(rs.getString("ni_nextform"));
			
			NoMatchInputEntity noMatch = new NoMatchInputEntity();
			noMatch.setId(rs.getString("nm_id"));
			noMatch.setName(rs.getString("nm_name"));
			noMatch.setType(rs.getString("nm_type"));
			noMatch.setThreshold(rs.getInt("nm_threshold"));
			noMatch.setPrompt(prompt_nm);
			noMatch.setNextForm(rs.getString("nm_nextform"));
			
			PromptCollectEntity promptCollect = new PromptCollectEntity();
			promptCollect.setId(rs.getString("pc_id"));
			promptCollect.setName(rs.getString("pc_name"));
			promptCollect.setDescription(rs.getString("pc_description"));
			promptCollect.setFlushprompt(rs.getInt("pc_flushprompt"));
			promptCollect.setPrompt(prompt);
			promptCollect.setNoInput(noInput);
			promptCollect.setNoMatch(noMatch);
			promptCollect.setFetchTimeout(rs.getInt("pc_fetchtimeou"));
			promptCollect.setInterDigitTimeout(rs.getInt("pc_interdigittimeout"));
			promptCollect.setTerminatingCharacter(rs.getString("pc_terminatingcharacter"));
			promptCollect.setNextForm(rs.getString("pc_nextform"));
				
			result.add(promptCollect);
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
	
	public List<PromptCollectEntity> getAll() {
		return this.getByFilter(null);
		
		
	}
	public PromptCollectEntity get(String id) {
		List<PromptCollectEntity> result = this.getByFilter("WHERE pc.id = "+id);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(PromptCollectEntity promptCollect) {
		boolean result = true;
		
		String sql = "INSERT INTO flow.promptcollect (id,name,description,flushprompt,prompt,noinput,nomatch,"
					+ "fechtimeout,interdigittimeout,terminatingtimeout,terminatincharacter,nextform,tag,"
					+ "versionid) "+
					 "VALUES ('"+promptCollect.getId()+"','"+promptCollect.getName()+"',"
					+ "'"+promptCollect.getDescription()+"','"+promptCollect.getFlushprompt()+"',"
					+ "'"+promptCollect.getPrompt().getId()+"','"+promptCollect.getNoInput().getId()+"',"
					+ "'"+promptCollect.getNoMatch().getId()+"',"+promptCollect.getFetchTimeout()+","
					+ promptCollect.getInterDigitTimeout()+","+promptCollect.getTerminatingTimeout()+","
					+ promptCollect.getTerminatingCharacter()+","+promptCollect.getNextForm()+",'"
					+ promptCollect.getTag().getId()+"','"+promptCollect.getVersionId().getId()+"') ";
		             
		result = db.ExecuteSql(sql);
		return result;
	}

	@Override
	public boolean update(PromptCollectEntity promptCollect) {
		boolean result = true;
		String sql = "UPDATE flow.promptcollect SET name = '"+promptCollect.getName()+"', "+
		             "description = '"+promptCollect.getDescription()+"',flushprompt = "+promptCollect.getFlushprompt()+", "+
				     "prompt= '"+promptCollect.getPrompt().getId()+"',noinput='"+promptCollect.getNoInput().getId()+"', "+
		             "nomatch='"+promptCollect.getNoMatch().getId()+"',fechtimeout="+promptCollect.getFetchTimeout()+", "+
				     "interdigittimeout="+promptCollect.getInterDigitTimeout()+",terminatingtimeout="+promptCollect.getTerminatingTimeout()+", "+
		             "terminatincharacter="+promptCollect.getTerminatingCharacter()+",nextform='"+promptCollect.getNextForm()+"', "+
				     "tag='"+promptCollect.getTag().getId()+"',versionid='"+promptCollect.getVersionId().getId()+"' "+
					 "WHERE id = '"+promptCollect.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

	@Override
	public boolean delete(PromptCollectEntity promptCollect) {
		boolean result = true;
		String sql = "DELETE FROM flow.promptcollect WHERE id = '"+promptCollect.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

}
