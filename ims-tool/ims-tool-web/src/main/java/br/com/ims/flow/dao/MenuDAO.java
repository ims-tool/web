package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class MenuDAO extends AbstractDAO<MenuEntity>{
	private static MenuDAO instance = null;
	//private DbConnection db =  null;
	private MenuDAO() {
		//db =  new DbConnection("MenuDAO");
	}
	
	public static MenuDAO getInstance() {
		if(instance == null) {
			instance = new MenuDAO();
		}
		return instance;
	}
	public ChoiceEntity getChoice(String id) {
		List<ChoiceEntity> result = getChoicesByFilter("WHERE c.id = '"+id+"'",false);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public List<ChoiceEntity> getChoicesByFilter(String where,boolean lazy) {
		String sql = "SELECT c.id c_id,c.name c_name,c.menu c_menu,c.dtmf c_dtmf,c.nextform c_nextform,c.condition c_condition,c.versionid c_versionid, "+					
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+	                 
	                 "FROM flow.choice c "+
	                 "LEFT JOIN flow.tag t ON c.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
	                 "<WHERE> "+
	                 "ORDER BY c.dtmf ";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<ChoiceEntity> result = new ArrayList<ChoiceEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("MenuDAO-getChoicesByFilter");
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
				ConditionEntity condition = null;
				if(!lazy) {
					if(rs.getString("c_condition") != null && rs.getString("c_condition").length() > 0) {
						condition = ServicesFactory.getInstance().getConditionService().get(rs.getString("c_condition"));
					}
				}
				
				ChoiceEntity choice = new ChoiceEntity();
				choice.setId(rs.getString("c_id"));
				choice.setName(rs.getString("c_name"));
				choice.setMenuId(rs.getString("c_menu"));
				choice.setDtmf(rs.getString("c_dtmf"));
				choice.setNextForm(rs.getString("c_nextform"));
				choice.setTag(tag);
				choice.setCondition(condition);
				choice.setVersionId(rs.getString("c_versionid"));
				
				result.add(choice);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.finalize();
		}
		
		return result;
	}
	public List<MenuEntity> getByFilter(String where) {
		return getByFilter(where,false);
	}
	
	public List<MenuEntity> getByFilter(String where,boolean lazy) {
		String sql = "SELECT m.id m_id,m.name m_name,m.description m_description,m.prompt m_prompt,m.fetchtimeout m_fetchtimeout,m.versionid m_versionid, "+
				 "m.terminatingtimeout m_terminatingtimeout, m.terminatingcharacter m_terminatingcharacter, "+
				 "m.noinput_nextform m_noinput_nextform,m.nomatch_nextform m_nomatch_nextform, "+
				 "ni.id ni_id,ni.name ni_name, ni.type ni_type,ni.threshold ni_threshold, ni.prompt ni_prompt, ni.nextform ni_nextform, "+
			     "nm.id nm_id,nm.name nm_name, nm.type nm_type,nm.threshold nm_threshold, nm.prompt nm_prompt, nm.nextform nm_nextform, "+
				 "t_ni.id t_ni_id, t_ni.description t_ni_description, "+ 
				 "tt_ni.id tt_ni_id, tt_ni.name tt_ni_name,tt_ni.description tt_ni_description, "+
				 "t_nm.id t_nm_id, t_nm.description t_nm_description, "+ 
				 "tt_nm.id tt_nm_id, tt_nm.name tt_nm_name,tt_nm.description tt_nm_description "+
				 "FROM flow.menu m "+
				 "LEFT JOIN flow.nomatchinput ni ON ni.id = m.noinput "+
				 "LEFT JOIN flow.nomatchinput nm ON nm.id = m.nomatch "+
				 "LEFT JOIN flow.tag t_ni ON m.noinput_tag = t_ni.id "+ 
				 "LEFT JOIN flow.tagtype tt_ni ON t_ni.tagtypeid = tt_ni.id "+
				 "LEFT JOIN flow.tag t_nm ON m.nomatch_tag = t_nm.id "+ 
				 "LEFT JOIN flow.tagtype tt_nm ON t_nm.tagtypeid = tt_nm.id "+
			     "<WHERE> "+
				 "ORDER BY m.name";
	if(where != null && where.length() > 0) {
		sql = sql.replace("<WHERE>", where);
	} else {
		sql = sql.replace("<WHERE>", "");
	}
	List<MenuEntity> result = new ArrayList<MenuEntity>();
	ResultSet rs = null;
	DbConnection db = new DbConnection("MenuDAO-getByFilter");
	try {
		rs = db.ExecuteQuery(sql);
		while(rs.next()) {
			
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
			List<ChoiceEntity> choices =  null;
			if(!lazy) {
				if(rs.getString("m_prompt") != null && rs.getString("m_prompt").length() > 0) {
					prompt = ServicesFactory.getInstance().getPromptService().get(rs.getString("m_prompt"));
				}
				if(rs.getString("ni_prompt") != null && rs.getString("ni_prompt").length() > 0)
					prompt_ni = ServicesFactory.getInstance().getPromptService().get(rs.getString("ni_prompt"));
				if(rs.getString("nm_prompt") != null && rs.getString("nm_prompt").length() > 0)
					prompt_nm = ServicesFactory.getInstance().getPromptService().get(rs.getString("nm_prompt"));
				choices = this.getChoicesByFilter("WHERE c.menu = '"+rs.getString("m_id")+"'",lazy);
			}
			
			
			NoMatchInputEntity noInput = null;
			if(rs.getString("ni_id") != null && rs.getString("ni_id").length() > 0) {
				noInput = new NoMatchInputEntity();
				noInput.setId(rs.getString("ni_id"));
				noInput.setName(rs.getString("ni_name"));
				noInput.setType(rs.getString("ni_type"));
				noInput.setThreshold(rs.getInt("ni_threshold"));
				noInput.setPrompt(prompt_ni);
				noInput.setNextForm(rs.getString("ni_nextform"));
			}
			
			NoMatchInputEntity noMatch = null;
			if(rs.getString("nm_id") != null && rs.getString("nm_id").length() > 0) {
				noMatch = new NoMatchInputEntity();
				noMatch.setId(rs.getString("nm_id"));
				noMatch.setName(rs.getString("nm_name"));
				noMatch.setType(rs.getString("nm_type"));
				noMatch.setThreshold(rs.getInt("nm_threshold"));
				noMatch.setPrompt(prompt_nm);
				noMatch.setNextForm(rs.getString("nm_nextform"));
			}	
			
			
			MenuEntity menu = new MenuEntity();
			menu.setId(rs.getString("m_id"));
			menu.setName(rs.getString("m_name"));
			menu.setDescription(rs.getString("m_description"));
			menu.setPrompt(prompt);
			menu.setNoInput(noInput);
			menu.setNoMatch(noMatch);
			menu.setFetchTimeOut(rs.getString("m_fetchtimeout"));
			menu.setTerminatingTimeOut(rs.getString("m_terminatingtimeout"));
			menu.setTerminatingCharacter(rs.getString("m_terminatingcharacter"));
			menu.setChoices(choices);
			menu.setNoInput_NextForm(rs.getString("m_noinput_nextform"));
			menu.setNoMatch_NextForm(rs.getString("m_nomatch_nextform"));
			menu.setNoInput_Tag(tag_ni);
			menu.setNoMatch_Tag(tag_nm);
			menu.setVersionId(rs.getString("m_versionid"));
				
			result.add(menu);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		
		db.finalize();
	}
	
	return result;
	}
	
	public List<MenuEntity> getAll() {
		return this.getByFilter(null);
		
		
	}
	public List<MenuEntity> getAll(boolean lazy) {
		return this.getByFilter(null,lazy);
		
		
	}
	public MenuEntity get(String id) {
		List<MenuEntity> result = this.getByFilter("WHERE m.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public MenuEntity get(String id,boolean lazy) {
		List<MenuEntity> result = this.getByFilter("WHERE m.id = '"+id+"'",lazy);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(MenuEntity menu) {
		boolean result = true;
		String sql = "INSERT INTO flow.menu (id,name,description,prompt,noinput,nomatch,noinput_nextform,nomatch_nextform,noinput_tag,nomatch_tag,fetchtimeout,terminatingtimeout,terminatingcharacter,versionid) "+
					 "VALUES ('"+menu.getId()+"','"+menu.getName()+"','"+menu.getDescription()+"',"+
					 (menu.getPrompt() == null ? "NULL" : menu.getPrompt().getId())+","+
					 (menu.getNoInput() == null ? "NULL" :  menu.getNoInput().getId())+","+
				     (menu.getNoMatch() == null ? "NULL" : menu.getNoMatch().getId())+","+
					 (menu.getNoInput_NextForm() == null || menu.getNoInput_NextForm().length() == 0 ? "NULL" : menu.getNoInput_NextForm() )+","+
					 (menu.getNoMatch_NextForm() == null || menu.getNoMatch_NextForm().length() == 0 ? "NULL" : menu.getNoMatch_NextForm() )+","+
					 (menu.getNoInput_Tag() == null ? "NULL" : menu.getNoInput_Tag().getId())+","+
					 (menu.getNoMatch_Tag() == null ? "NULL" : menu.getNoMatch_Tag().getId())+","+
					 (menu.getFetchTimeOut() == null || menu.getFetchTimeOut().length() == 0 ? "NULL" : menu.getFetchTimeOut())+","+
				     (menu.getTerminatingTimeOut() == null || menu.getTerminatingTimeOut().length() == 0 ? "NULL" : menu.getFetchTimeOut() )+","+
					 (menu.getTerminatingCharacter() ==  null || menu.getTerminatingCharacter().length() == 0? "NULL" : "'"+menu.getTerminatingCharacter()+"'")+",'"+menu.getVersionId()+"') ";
		DbConnection db = new DbConnection("MenuDAO-save");             
		result = db.ExecuteSql(sql);
		if(result) {
			if(menu.getChoices() != null && menu.getChoices().size() > 0) {
				for(ChoiceEntity choice : menu.getChoices()) {
					sql = "INSERT INTO flow.choice (id,name,menu,dtmf,nextform,condition,tag,versionid) "+
						   "VALUES ('"+choice.getId()+"','"+choice.getName()+"','"+menu.getId()+"','"+choice.getDtmf()+"',"+
							(choice.getNextForm() == null || choice.getNextForm().length() == 0 ? "NULL" : choice.getNextForm())+","+
							(choice.getCondition() == null ? "NULL" : "'"+choice.getCondition().getId()+"'")+","+
						   (choice.getTag() == null ? "NULL" : choice.getTag().getId())+","+menu.getVersionId()+") ";
					result = result & db.ExecuteSql(sql);
					if(!result) {
						//rollback
						sql = "DELETE FROM flow.choice WHERE menu = '"+menu.getId()+"' ";
						db.ExecuteSql(sql);
						sql = "DELETE FROM flow.menu WHERE id = '"+menu.getId()+"' ";
						db.ExecuteSql(sql);
						break;
					}
				}
			}
						
		}
		db.finalize();
		if(result)
			Util.audit(menu, Constants.AUDIT_TYPE_ADD);
		return result;

	}

	@Override
	public boolean update(MenuEntity menu) {
		boolean result = true;
		String sql = "UPDATE flow.menu SET name = '"+menu.getName()+"',description='"+menu.getDescription()+"',"
				+ "prompt = "+(menu.getPrompt() == null ? "NULL" : menu.getPrompt().getId())+","
				+ "noinput="+(menu.getNoInput() == null ? "NULL" : menu.getNoInput().getId())+","
				+ "nomatch ="+(menu.getNoMatch() == null ? "NULL" : menu.getNoMatch().getId())+","
				+ "noinput_nextform ="+(menu.getNoInput_NextForm() == null || menu.getNoInput_NextForm().length() == 0 ? "NULL" : menu.getNoInput_NextForm())+","
				+ "nomatch_nextform ="+(menu.getNoMatch_NextForm() == null || menu.getNoMatch_NextForm().length() == 0 ? "NULL" : menu.getNoMatch_NextForm())+","
				+ "noinput_tag = "+(menu.getNoInput_Tag() == null ? "NULL" : menu.getNoInput_Tag().getId())+","
				+ "nomatch_tag = "+(menu.getNoMatch_Tag() == null ? "NULL" : menu.getNoMatch_Tag().getId())+","
				+ "fetchtimeout = "+(menu.getFetchTimeOut() == null || menu.getFetchTimeOut().length() == 0? "NULL" : menu.getFetchTimeOut())+","
				+ "terminatingtimeout = "+(menu.getTerminatingTimeOut() == null || menu.getTerminatingTimeOut().length() == 0? "NULL" : menu.getTerminatingTimeOut())+","
				+ "terminatingcharacter = "+(menu.getTerminatingCharacter() == null || menu.getTerminatingCharacter().length()==0? "NULL" :  "'"+menu.getTerminatingCharacter()+"'")+","
				+ "versionid = "+menu.getVersionId()+" "
				+ "WHERE id = "+menu.getId();
		DbConnection db = new DbConnection("MenuDAO-update");            
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.choice WHERE menu = '"+menu.getId()+"' ";
			result = db.ExecuteSql(sql);
			if(result) {
				if(menu.getChoices() != null && menu.getChoices().size() > 0) {
					for(ChoiceEntity choice : menu.getChoices()) {
						sql = "INSERT INTO flow.choice (id,name,menu,dtmf,nextform,condition,tag,versionid) "+
							   "VALUES ('"+choice.getId()+"','"+choice.getName()+"','"+menu.getId()+"','"+choice.getDtmf()+"',"+
								(choice.getNextForm() == null || choice.getNextForm().length() == 0 ? "NULL" : choice.getNextForm())+","+
								(choice.getCondition() == null ? "NULL" : "'"+choice.getCondition().getId()+"'")+","+
							   (choice.getTag() == null ? "NULL" : choice.getTag().getId())+","+menu.getVersionId()+") ";
						result = result & db.ExecuteSql(sql);
						if(!result) {
							break;
						}
					}
				}
			}
						
		}
		db.finalize();
		if(result)
			Util.audit(menu, Constants.AUDIT_TYPE_UPDATE);
		return result;
		
		
	}
	

	@Override
	public boolean delete(MenuEntity menu) {
		boolean result = true;
		String sql = "DELETE FROM flow.choice WHERE menu = '"+menu.getId()+"' ";
		DbConnection db = new DbConnection("MenuDAO-delete");
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.menu WHERE id = '"+menu.getId()+"' ";
		             
			result = db.ExecuteSql(sql);
		}
		db.finalize();
		if(result)
			Util.audit(menu, Constants.AUDIT_TYPE_DELETE);
		return result;
		
	}

}
