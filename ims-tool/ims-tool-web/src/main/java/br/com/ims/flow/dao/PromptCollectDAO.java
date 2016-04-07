package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.GrammarEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptCollectEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;
@SuppressWarnings("serial")
public class PromptCollectDAO extends AbstractDAO<PromptCollectEntity>{
	public static Logger log = Logger.getLogger(PromptCollectDAO.class);
	private static PromptCollectDAO instance = null;
	//private DbConnection db =  null;
	private PromptCollectDAO() {
		//db =  new DbConnection("PromptCollectDAO");
	}
	
	public static PromptCollectDAO getInstance() {
		if(instance == null) {
			instance = new PromptCollectDAO();
		}
		return instance;
	}
	public List<PromptCollectEntity> getByFilter(String where) {
		return this.getByFilter(where,false);
	}
	
	public List<PromptCollectEntity> getByFilter(String where,boolean lazy) {
		log.info("getByFilter("+where+","+lazy+")");
		System.out.println("PromptCollectDAO-getByFilter("+where+","+lazy+")");
		
		String sql = "SELECT pc.id pc_id,pc.name pc_name,pc.description pc_description,pc.flushprompt pc_flushprompt,pc.prompt pc_prompt,"+
				 "pc.fetchtimeout pc_fetchtimeout, pc.interdigittimeout pc_interdigittimeout, pc.terminatingcharacter pc_terminatingcharacter, "+
				 "pc.nextform pc_nextform, pc.noinput_nextform pc_noinput_nextform,pc.nomatch_nextform pc_nomatch_nextform, "+
				 "g.id g_id, g.name g_name,g.description g_description,g.type g_type,g.sizemax g_sizemax,g.sizemin g_sizemin, "+
			     "ni.id ni_id,ni.name ni_name, ni.type ni_type,ni.threshold ni_threshold, ni.prompt ni_prompt, "+
			     "nm.id nm_id,nm.name nm_name, nm.type nm_type,nm.threshold nm_threshold, nm.prompt nm_prompt,  "+
				 "t.id t_id, t.description t_description, "+ 
				 "tt.id tt_id, tt.name tt_name,tt.description tt_description, "+
				 "t_ni.id t_ni_id, t_ni.description t_ni_description, "+ 
				 "tt_ni.id tt_ni_id, tt_ni.name tt_ni_name,tt_ni.description tt_ni_description, "+
				 "t_nm.id t_nm_id, t_nm.description t_nm_description, "+ 
				 "tt_nm.id tt_nm_id, tt_nm.name tt_nm_name,tt_nm.description tt_nm_description "+
				 "FROM flow.promptcollect pc "+
				 "INNER JOIN flow.grammar g ON pc.grammar = g.id "+
				 "INNER JOIN flow.nomatchinput ni ON ni.id = pc.noinput "+
				 "INNER JOIN flow.nomatchinput nm ON nm.id = pc.nomatch "+
				 "LEFT JOIN flow.tag t ON pc.tag = t.id "+ 
				 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
				 "LEFT JOIN flow.tag t_ni ON pc.noinput_tag = t_ni.id "+ 
				 "LEFT JOIN flow.tagtype tt_ni ON t_ni.tagtypeid = tt_ni.id "+
				 "LEFT JOIN flow.tag t_nm ON pc.nomatch_tag = t_nm.id "+ 
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
	DbConnection db = new DbConnection("PromptCollectDAO-getByFilter");
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
			
			PromptEntity prompt = null;
			PromptEntity prompt_ni = null;
			PromptEntity prompt_nm = null;
			
			if(!lazy) {
				prompt = ServicesFactory.getInstance().getPromptService().get(rs.getString("pc_prompt"));
				if(rs.getString("ni_prompt") != null && rs.getString("ni_prompt").length() > 0)
					prompt_ni = ServicesFactory.getInstance().getPromptService().get(rs.getString("ni_prompt"));
				if(rs.getString("nm_prompt") != null && rs.getString("nm_prompt").length() > 0) 
					prompt_nm = ServicesFactory.getInstance().getPromptService().get(rs.getString("nm_prompt"));
			}
			
			GrammarEntity grammar = new GrammarEntity();
			grammar.setId(rs.getString("g_id"));
			grammar.setName(rs.getString("g_name"));
			grammar.setDescription(rs.getString("g_description"));
			grammar.setType(rs.getString("g_type"));
			grammar.setSizeMax(rs.getInt("g_sizemax"));
			grammar.setSizeMax(rs.getInt("g_sizemin"));
			
			NoMatchInputEntity noInput = new NoMatchInputEntity();
			noInput.setId(rs.getString("ni_id"));
			noInput.setName(rs.getString("ni_name"));
			noInput.setType(rs.getString("ni_type"));
			noInput.setThreshold(rs.getInt("ni_threshold"));
			noInput.setPrompt(prompt_ni);
			
			NoMatchInputEntity noMatch = new NoMatchInputEntity();
			noMatch.setId(rs.getString("nm_id"));
			noMatch.setName(rs.getString("nm_name"));
			noMatch.setType(rs.getString("nm_type"));
			noMatch.setThreshold(rs.getInt("nm_threshold"));
			noMatch.setPrompt(prompt_nm);
			
			PromptCollectEntity promptCollect = new PromptCollectEntity();
			promptCollect.setId(rs.getString("pc_id"));
			promptCollect.setName(rs.getString("pc_name"));
			promptCollect.setDescription(rs.getString("pc_description"));
			promptCollect.setFlushprompt(rs.getInt("pc_flushprompt"));
			promptCollect.setPrompt(prompt);
			promptCollect.setGrammar(grammar);
			promptCollect.setNoInput(noInput);
			promptCollect.setNoMatch(noMatch);
			promptCollect.setFetchTimeout(rs.getString("pc_fetchtimeout"));
			promptCollect.setInterDigitTimeout(rs.getString("pc_interdigittimeout"));
			promptCollect.setTerminatingCharacter(rs.getString("pc_terminatingcharacter"));
			promptCollect.setNextForm(rs.getString("pc_nextform"));
			promptCollect.setNoInput_NextForm(rs.getString("pc_noinput_nextform"));
			promptCollect.setNoMatch_NextForm(rs.getString("pc_nomatch_nextform"));
			promptCollect.setNoInput_Tag(tag_ni);
			promptCollect.setNoMatch_Tag(tag_nm);
			
		
			result.add(promptCollect);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
	
	public List<PromptCollectEntity> getAll() {
		return this.getByFilter(null,false);		
		
	}
	public List<PromptCollectEntity> getAll(boolean lazy) {
		return this.getByFilter(null,lazy);		
		
	}
	public PromptCollectEntity get(String id) {
		List<PromptCollectEntity> result = this.getByFilter("WHERE pc.id ='"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(PromptCollectEntity promptCollect) {
		boolean result = true;
		
		String sql = "INSERT INTO flow.promptcollect (id,name,description,flushprompt,prompt,grammar,noinput,nomatch,noinput_nextform,nomatch_nextform,"
					+ "noinput_tag,nomatch_tag,fetchtimeout,interdigittimeout,terminatingtimeout,terminatingcharacter,nextform,tag,versionid) "+
					 "VALUES ('"+promptCollect.getId()+"','"+promptCollect.getName()+"',"
					+ "'"+promptCollect.getDescription()+"','"+promptCollect.getFlushprompt()+"',"
					+ "'"+promptCollect.getPrompt().getId()+"',"
					+ "'"+promptCollect.getGrammar().getId()+"',"
					+ "'"+promptCollect.getNoInput().getId()+"',"
					+ "'"+promptCollect.getNoMatch().getId()+"',"
					+ "'"+promptCollect.getNoInput_NextForm()+"',"
					+ "'"+promptCollect.getNoMatch_NextForm()+"',"
					+ (promptCollect.getNoInput_Tag() == null ? "NULL" : promptCollect.getNoInput_Tag().getId() )+","
					+ (promptCollect.getNoMatch_Tag() == null ? "NULL" : promptCollect.getNoMatch_Tag().getId() )+","
					+ (promptCollect.getFetchTimeout() == null || promptCollect.getFetchTimeout().length() == 0? "NULL" : promptCollect.getFetchTimeout() )+","
					+ (promptCollect.getInterDigitTimeout() == null ||  promptCollect.getInterDigitTimeout().length() == 0 ? "NULL" : promptCollect.getInterDigitTimeout())+","
					+ (promptCollect.getTerminatingTimeout() ==  null || promptCollect.getTerminatingTimeout().length() == 0 ? "NULL" : promptCollect.getTerminatingTimeout())+","
					+ (promptCollect.getTerminatingCharacter() == null || promptCollect.getTerminatingCharacter().length() == 0 ? "NULL" : promptCollect.getTerminatingCharacter())+","
					+ promptCollect.getNextForm()+","
					+ (promptCollect.getTag() == null ? "NULL" : promptCollect.getTag().getId())+",'"+promptCollect.getVersionId().getId()+"') ";
		DbConnection db = new DbConnection("PromptCollectDAO-save");             
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
	}

	@Override
	public boolean update(PromptCollectEntity promptCollect) {
		boolean result = true;
		String sql = "UPDATE flow.promptcollect SET name = '"+promptCollect.getName()+"', "+
		             "description = '"+promptCollect.getDescription()+"',flushprompt = "+promptCollect.getFlushprompt()+", "+
				     "prompt= '"+promptCollect.getPrompt().getId()+"',"+
				     "grammar= '"+promptCollect.getGrammar().getId()+"',"+
				     "noinput='"+promptCollect.getNoInput().getId()+"', "+
		             "nomatch='"+promptCollect.getNoMatch().getId()+"',"+
				     "noinput_nextform='"+promptCollect.getNoInput_NextForm()+"',"+
				     "nomatch_nextform='"+promptCollect.getNoMatch_NextForm()+"',"+
				     "noinput_tag="+(promptCollect.getNoInput_Tag() == null ? "NULL" : promptCollect.getNoInput_Tag().getId())+","+
				     "nomatch_tag="+(promptCollect.getNoMatch_Tag() == null ? "NULL" : promptCollect.getNoMatch_Tag().getId())+","+
		             "fetchtimeout="+(promptCollect.getFetchTimeout() == null || promptCollect.getFetchTimeout().length() == 0 ? "NULL" : promptCollect.getFetchTimeout())+", "+
				     "interdigittimeout="+(promptCollect.getInterDigitTimeout() == null || promptCollect.getInterDigitTimeout().length() == 0 ? "NULL" : promptCollect.getInterDigitTimeout())+","+
				     "terminatingtimeout="+(promptCollect.getTerminatingTimeout() == null || promptCollect.getTerminatingTimeout().length() == 0 ? "NULL" : promptCollect.getTerminatingTimeout())+", "+
		             "terminatingcharacter="+(promptCollect.getTerminatingCharacter()== null || promptCollect.getTerminatingCharacter().length() == 0 ? "NULL" : promptCollect.getTerminatingCharacter())+","+
		             "nextform='"+promptCollect.getNextForm()+"', "+
				     "tag="+(promptCollect.getTag() == null ? "NULL" : promptCollect.getTag().getId())+",versionid='"+promptCollect.getVersionId().getId()+"' "+
					 "WHERE id = '"+promptCollect.getId()+"' ";
		DbConnection db = new DbConnection("PromptCollectDAO-update");             
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

	@Override
	public boolean delete(PromptCollectEntity promptCollect) {
		boolean result = true;
		String sql = "DELETE FROM flow.promptcollect WHERE id = '"+promptCollect.getId()+"' ";
		DbConnection db = new DbConnection("PromptCollectDAO-delete");             
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

}
