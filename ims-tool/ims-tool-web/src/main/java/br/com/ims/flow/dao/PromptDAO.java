package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AudioEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.PromptAudioEntity;
import br.com.ims.flow.model.PromptEntity;

@SuppressWarnings("serial")
public class PromptDAO extends AbstractDAO<PromptEntity> {
	public static Logger log = Logger.getLogger(PromptDAO.class);
	private static PromptDAO instance = null;
	//private DbConnection db = null;
	private PromptDAO() {
		//db = new DbConnection("PromptDAO"); 			
	}
	
	public static PromptDAO getInstance() {
		if(instance == null) {
			instance = new PromptDAO();
		}
		return instance;
	}
	
	public List<PromptAudioEntity> getPromptAudio(String where,boolean lazy) {
		log.info("getPromptAudio("+where+")");
		System.out.println("PromptDAO-getPromptAudio("+where+")");
		String sql = "SELECT pa.prompt pa_prompt,pa.ordernum pa_ordernum,pa.condition pa_condition,pa.versionid pa_versionid, "+
				     "a.id a_id,a.type a_type,a.name a_name,a.description a_description, a.path a_path,a.property a_property "+	                 
	                 "FROM flow.promptaudio pa "+
	                 "INNER JOIN flow.audio a ON pa.audio = a.id "+
	                 "<WHERE> "+
	                 "ORDER BY pa.ordernum";
		List<PromptAudioEntity> result = new ArrayList<PromptAudioEntity>();
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		ResultSet rs = null;
		DbConnection db = new DbConnection("PromptDAO");
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
				if(!lazy) {
					if(rs.getString("pa_condition") != null && rs.getString("pa_condition").length() > 0) {
						condition = ServicesFactory.getInstance().getConditionService().get(rs.getString("pa_condition"));
					}
				}
				
				PromptAudioEntity promptAudio = new PromptAudioEntity ();
				
				promptAudio.setPromptId(rs.getString("pa_prompt"));
				promptAudio.setOrderNum(rs.getInt("pa_ordernum"));
				promptAudio.setAudio(audio);
				promptAudio.setCondition(condition);
				promptAudio.setVersionId(null);
				promptAudio.setVersionId(rs.getString("pa_versionid"));
				
				
				result.add(promptAudio);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
			log.debug(sql);
			
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
	public List<PromptEntity> getByFilter(String where) {
		return getByFilter(where,false);
	}
	public List<PromptEntity> getByFilter(String where,boolean lazy) {
		
		log.info("getByFilter("+where+")");
		System.out.println("PromptDAO-getByFilter("+where+")");
		String sql = "SELECT p.id p_id,p.name p_name, p.versionid p_versionid FROM flow.prompt p <WHERE> ORDER BY p.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		List<PromptEntity> result = new ArrayList<PromptEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("PromptDAO-getByFilter");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				PromptEntity prompt = new PromptEntity ();
				prompt.setId(rs.getString("p_id"));
				prompt.setName(rs.getString("p_name"));
				List<PromptAudioEntity> promptAudioList = new ArrayList<PromptAudioEntity>();
				if(!lazy) {
					promptAudioList.addAll(getPromptAudio("WHERE pa.prompt ='"+rs.getString("p_id")+"' ",lazy ));
				}
				prompt.setAudios(promptAudioList);
				prompt.setVersionId(rs.getString("p_versionid"));
				result.add(prompt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
			log.debug(sql);
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

	public List<PromptEntity> getAll() {
		return this.getByFilter(null);
	}
	public List<PromptEntity> getAll(boolean lazy) {
		return this.getByFilter(null,lazy);
	}
	public PromptEntity get(String id) {
		List<PromptEntity> result = this.getByFilter("WHERE p.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	public PromptEntity get(String id,boolean lazy) {
		List<PromptEntity> result = this.getByFilter("WHERE p.id = '"+id+"'",lazy);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	
	public PromptEntity getByName(String name) {
		List<PromptEntity> result = this.getByFilter("WHERE lower(p.name) = '"+name.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	public boolean save(PromptEntity prompt) {
		boolean result = true;
		log.info("save()");
		System.out.println("PromptDAO-save()");
		String sql = "INSERT INTO flow.prompt (id,\"name\",versionid) "+
					 "VALUES ('"+prompt.getId()+"','"+prompt.getName()+"','"+prompt.getVersionId()+"') ";
		
		DbConnection db = new DbConnection("PromptDAO-save");
		result = db.ExecuteSql(sql);
		if(result) {
			for(PromptAudioEntity promptAudio : prompt.getAudios()) {
				sql = "INSERT INTO flow.promptaudio (prompt,audio,ordernum,condition,versionid) "+
					   "VALUES ('"+prompt.getId()+"','"+promptAudio.getAudio().getId()+"',"+promptAudio.getOrderNum()+","+
						(promptAudio.getCondition() == null ? "NULL" : "'"+promptAudio.getCondition().getId()+"'")+","+
						prompt.getVersionId()+") ";
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
		db.finalize();
		if(result)
			Util.audit(prompt, Constants.AUDIT_TYPE_ADD);
		return result;
	}

	@Override
	public boolean update(PromptEntity entity) {
		boolean result = true;
		log.info("update()");
		System.out.println("PromptDAO-update()");
		String sql = "UPDATE flow.prompt SET \"name\"='"+entity.getName()+"',versionid = "+entity.getVersionId()+" "+
					 "WHERE id="+entity.getId()+" ";
		DbConnection db = new DbConnection("PromptDAO-update");
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.promptaudio WHERE prompt = "+entity.getId();
			result = db.ExecuteSql(sql);
			if(result) {
				for(PromptAudioEntity promptAudio : entity.getAudios()) {
					sql = "INSERT INTO flow.promptaudio (prompt,audio,ordernum,condition,versionid) "+
						   "VALUES ('"+entity.getId()+"','"+promptAudio.getAudio().getId()+"',"+promptAudio.getOrderNum()+","+(promptAudio.getCondition() == null ? "NULL" : "'"+promptAudio.getCondition().getId()+"'")+","+entity.getVersionId()+") ";
					result = result & db.ExecuteSql(sql);
					if(!result) {
						break;
					}
				}
			}
						
		}
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_UPDATE);
		return result;
	}

	@Override
	public boolean delete(PromptEntity entity) {
		// TODO Auto-generated method stub
		log.info("delete()");
		System.out.println("PromptDAO-delete()");
		String sql = "DELETE FROM flow.promptaudio WHERE prompt = '"+entity.getId()+"'";
		DbConnection db = new DbConnection("PromptDAO-delete");
		boolean result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.prompt WHERE id='"+entity.getId()+"' ";
			             
			result = result & db.ExecuteSql(sql);
		}
		db.finalize();
		if(result)
			Util.audit(entity, Constants.AUDIT_TYPE_DELETE);
		return result;
		
	}

}
