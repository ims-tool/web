package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AudioEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.PromptAudioEntity;
import br.com.ims.flow.model.PromptEntity;

public class PromptDAO extends AbstractDAO<PromptEntity> {
	private static PromptDAO instance = null;
	private DbConnection db = null;
	private PromptDAO() {
		db = new DbConnection(""); 			
	}
	
	public static PromptDAO getInstance() {
		if(instance == null) {
			instance = new PromptDAO();
		}
		return instance;
	}
	private List<PromptAudioEntity> getPromptAudio(String idPrompt) {
		String sql = "SELECT pa.prompt pa_prompt,pa.ordernum pa_ordernum,pa.condition pa_condition, "+
				     "a.id a_id,a.type a_type,a.name a_name,a.description a_description, a.path a_path,a.property a_property "+	                 
	                 "FROM flow.promptaudio pa "+
	                 "INNER JOIN flow.audio a ON pa.audio = a.id "+
	                 "WHERE pa.prompt ='"+idPrompt+"' "+
	                 "ORDER BY pa.ordernum";
		List<PromptAudioEntity> result = new ArrayList<PromptAudioEntity>();
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				AudioEntity audio = new AudioEntity();
				audio.setId(rs.getString("a_id"));
				audio.setType("a_type");
				audio.setName(rs.getString("a_name"));
				audio.setDescription(rs.getString("a_description"));
				audio.setPath(rs.getString("a_path"));
				audio.setProperty(rs.getString("a_property"));
				audio.setVersionId(null);
				
				ConditionEntity condition = null;
				if(rs.getString("pa_condition") != null && rs.getString("pa_condition").length() > 0) {
					condition = ServicesFactory.getInstance().getConditionService().get(rs.getString("pa_condition"));
				}
				
				PromptAudioEntity promptAudio = new PromptAudioEntity ();
				
				promptAudio.setPromptId(rs.getString("pa_prompt"));
				promptAudio.setOrderNum(rs.getInt("pa_ordernum"));
				promptAudio.setAudio(audio);
				promptAudio.setCondition(condition);
				promptAudio.setVersionId(null);
				
				
				result.add(promptAudio);
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
	public List<PromptEntity> getByFilter(String where) {
		
		String sql = "SELECT id,name,versionid FROM flow.prompt <WHERE> ORDER BY name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		
		List<PromptEntity> result = new ArrayList<PromptEntity>();
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				PromptEntity prompt = new PromptEntity ();
				prompt.setId(rs.getString("id"));
				prompt.setName(rs.getString("name"));
				List<PromptAudioEntity> promptAudioList = getPromptAudio(rs.getString("id"));
				prompt.setAudios(promptAudioList);
				prompt.setVersionId(null);
				result.add(prompt);
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

	public List<PromptEntity> getAll() {
		return this.getByFilter(null);
	}
	public PromptEntity get(String id) {
		List<PromptEntity> result = this.getByFilter("WHERE id = "+id);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	public boolean save(PromptEntity prompt) {
		boolean result = true;
		String sql = "INSERT INTO flow.prompt (id,name,versionid) "+
					 "VALUES ('"+prompt.getId()+"','"+prompt.getName()+"','"+prompt.getVersionId().getVersionId()+"') ";
		             
		result = db.ExecuteSql(sql);
		if(result) {
			for(PromptAudioEntity promptAudio : prompt.getAudios()) {
				sql = "INSERT INTO flow.promptaudio (prompt,audio,ordernum,condition) "+
					   "VALUES ('"+prompt.getId()+"','"+promptAudio.getAudio().getId()+"',"+(promptAudio.getCondition() == null ? "NULL" : "'"+promptAudio.getCondition().getId()+"'")+") ";
				result = result & db.ExecuteSql(sql);
				if(!result) {
					//rollback
					sql = "DELETE FROM flow.promptaudio WHERE prompt = '"+prompt.getId()+"' ";
					db.ExecuteSql(sql);
					sql = "DELETE FROM flow.prompt WHERE id = '"+prompt.getId()+"' ";
					db.ExecuteSql(sql);
					break;
				}
			}
						
		}
		return result;
	}

	@Override
	public boolean update(PromptEntity entity) {
		boolean result = true;
		String sql = "UPDATE flow.prompt SET name='"+entity.getName()+"',versionid = "+entity.getVersionId().getId()+" "+
					 "WHERE id="+entity.getId()+" ";
		             
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.promptaudio WHERE prompt = "+entity.getId();
			result = db.ExecuteSql(sql);
			if(result) {
				for(PromptAudioEntity promptAudio : entity.getAudios()) {
					sql = "INSERT INTO flow.promptaudio (prompt,audio,ordernum,condition) "+
						   "VALUES ('"+entity.getId()+"','"+promptAudio.getAudio().getId()+"',"+(promptAudio.getCondition() == null ? "NULL" : "'"+promptAudio.getCondition().getId()+"'")+") ";
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
	public boolean delete(PromptEntity entity) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM flow.promptaudio WHERE prompt = "+entity.getId();
		boolean result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE flow.prompt WHERE id="+entity.getId()+" ";
			             
			result = result & db.ExecuteSql(sql);
		}
		return result;
		
	}

}
