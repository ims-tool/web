package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

public class MenuDAO extends AbstractDAO<MenuEntity>{
	private static MenuDAO instance = null;
	private DbConnection db =  null;
	private MenuDAO() {
		db =  new DbConnection("");
	}
	
	public static MenuDAO getInstance() {
		if(instance == null) {
			instance = new MenuDAO();
		}
		return instance;
	}
	private List<ChoiceEntity> getChoices(String menuId) {
		String sql = "SELECT c.id c_id,c.name c_name,c.menu c_menu,c.dtmf c_dtmf,c.nextform c_nextform,c.condition c_condition, "+
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description, "+	                 
	                 "FROM flow.choice c "+
	                 "LEFT JOIN flow.tag t ON c.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
	                 "WHERE c.menu ='"+menuId+"' "+
	                 "ORDER BY c.dtmf ";
		List<ChoiceEntity> result = new ArrayList<ChoiceEntity>();
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
				ConditionEntity condition = null;
				if(rs.getString("c_condition") != null && rs.getString("c_condition").length() > 0) {
					condition = ServicesFactory.getInstance().getConditionService().get(rs.getString("c_condition"));
				}
				
				ChoiceEntity choice = new ChoiceEntity();
				choice.setId(rs.getString("c_id"));
				choice.setName(rs.getString("c_name"));
				choice.setMenuId(rs.getString("c_menu"));
				choice.setDtmf(rs.getString("c_dtmf"));
				choice.setNextForm(rs.getString("c_nextform"));
				choice.setTag(tag);
				choice.setCondition(condition);
				
				result.add(choice);
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
	
	public List<MenuEntity> getByFilter(String where) {
		String sql = "SELECT m.id m_id,m.name m_name,m.description m_description,m.prompt m_prompt,m.fetchtimeout m_fetchtimeout, m.terminatingtimeout m_terminatingtimeout, m.terminatingcharacter m_terminatingcharacter, "+
				 "ni.id ni_id,ni.name ni_name, ni.type ni_type,ni.threshold ni_threshold, ni.prompt ni_prompt, ni.nextform ni_nextform, "+
			     "nm.id nm_id,nm.name nm_name, nm.type nm_type,nm.threshold nm_threshold, nm.prompt nm_prompt, nm.nextform nm_nextform, "+
				 "t_ni.id t_ni_id, t_ni.description t_ni_description, "+ 
				 "tt_ni.id tt_id, tt_ni.name tt_ni_name,tt_ni.description tt_ni_description, "+
				 "t_nm.id t_nm_id, t_nm.description t_nm_description, "+ 
				 "tt_nm.id tt_nm_id, tt_nm.name tt_nm_name,tt_nm.description tt_nm_description "+
				 "FROM flow.menu m "+
				 "INNER JOIN flow.nomatchinput ni ON ni.id = m.noinput "+
				 "INNER JOIN flow.nomatchinput nm ON nm.id = m.nomatch "+
				 "LEFT JOIN flow.tag t_ni ON ni.tag = t_ni.id "+ 
				 "LEFT JOIN flow.tagtype tt_ni ON t_ni.tagtypeid = tt_ni.id "+
				 "LEFT JOIN flow.tag t_nm ON nm.tag = t_nm.id "+ 
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
			
			List<ChoiceEntity> choices = this.getChoices(rs.getString("m_id"));
			
			
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
				
			result.add(menu);
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
	
	public List<MenuEntity> getAll() {
		return this.getByFilter(null);
		
		
	}
	public MenuEntity get(String id) {
		List<MenuEntity> result = this.getByFilter("WHERE ms.id = "+id);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(MenuEntity menu) {
		boolean result = true;
		String sql = "INSERT INTO flow.menu (id,name,description,prompt,noinput,nomatch,fetchtimeout,terminatingtimeout,terminatingcharacter,versionid) "+
					 "VALUES ('"+menu.getId()+"','"+menu.getName()+"','"+menu.getDescription()+"','"+menu.getPrompt().getId()+"','"+menu.getNoInput().getId()+"','"+
				     menu.getNoMatch().getId()+"',"+
					 (menu.getFetchTimeOut() == null || menu.getFetchTimeOut().length() == 0 ? "NULL" : menu.getFetchTimeOut())+","+
				     (menu.getTerminatingTimeOut() == null || menu.getTerminatingTimeOut().length() == 0 ? "NULL" : menu.getFetchTimeOut() )+","+
					 (menu.getTerminatingCharacter() ==  null || menu.getTerminatingCharacter().length() == 0? "NULL" : "'"+menu.getTerminatingCharacter()+"'")+",'"+menu.getVersionId().getId()+"') ";
		             
		result = db.ExecuteSql(sql);
		if(result) {
			for(ChoiceEntity choice : menu.getChoices()) {
				sql = "INSERT INTO flow.choice (id,name,menu,dtfm,nextform,condition,tag,versionid) "+
					   "VALUES ('"+choice.getId()+"','"+choice.getName()+"','"+choice.getMenuId()+"','"+choice.getNextForm()+"',"+
						(choice.getCondition() == null ? "NULL" : "'"+choice.getCondition().getId()+"'")+","+
					   (choice.getTag() == null ? "NULL" : choice.getTag().getId())+","+choice.getVersionId().getId()+") ";
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
		return result;

	}

	@Override
	public boolean update(MenuEntity menu) {
		boolean result = true;
		String sql = "UPDATE flow.menu SET name = '"+menu.getName()+"',description='"+menu.getDescription()+"',"
				+ "prompt = '"+menu.getPrompt().getId()+"',noinput='"+menu.getNoInput().getId()+"',"
				+ "nomatch ='"+menu.getNoMatch().getId()+"',"
				+ "fetchtimeout = "+(menu.getFetchTimeOut() == null || menu.getFetchTimeOut().length() == 0? "NULL" : menu.getFetchTimeOut())+","
				+ "terminatingtimeout = "+(menu.getTerminatingTimeOut() == null || menu.getTerminatingTimeOut().length() == 0? "NULL" : menu.getTerminatingTimeOut())+","
				+ "terminatingcharacter = "+(menu.getTerminatingCharacter() == null || menu.getTerminatingCharacter().length()==0? "NULL" :  "'"+menu.getTerminatingCharacter()+"'")+","
				+ "versionid = "+menu.getVersionId().getVersionId()+") "
				+ "WHERE id = "+menu.getId();
		             
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.choice WHERE menu = '"+menu.getId()+"' ";
			result = db.ExecuteSql(sql);
			if(result) {
				for(ChoiceEntity choice : menu.getChoices()) {
					sql = "INSERT INTO flow.choice (id,name,menu,dtfm,nextform,condition,tag,versionid) "+
						   "VALUES ('"+choice.getId()+"','"+choice.getName()+"','"+choice.getMenuId()+"','"+choice.getNextForm()+"',"+
							(choice.getCondition() == null ? "NULL" : "'"+choice.getCondition().getId()+"'")+","+
						   (choice.getTag() == null ? "NULL" : choice.getTag().getId())+","+choice.getVersionId().getId()+") ";
					result = result & db.ExecuteSql(sql);
					if(!result) {
						break;
					}
				}
			}
						
		}
		return result;
		
		
	}

	@Override
	public boolean delete(MenuEntity menu) {
		boolean result = true;
		String sql = "DELETE FROM flow.choice WHERE menu = '"+menu.getId()+"' ";
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.menu WHERE id = '"+menu.getId()+"' ";
		             
			result = db.ExecuteSql(sql);
		}
		return result;
		
	}

}
