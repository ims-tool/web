package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.AudioEntity;
import br.com.ims.flow.model.AudioVarEntity;

@SuppressWarnings("serial")
public class AudioDAO extends AbstractDAO<AudioEntity>{
	public static Logger log = Logger.getLogger(AudioDAO.class);
	private static AudioDAO instance = null;
	//private DbConnection db =  null;
	private AudioDAO() {
		//db =  new DbConnection("AudioDAO");
	}
	
	public static AudioDAO getInstance() {
		if(instance == null) {
			instance = new AudioDAO();
		}
		return instance;
	}
	public List<AudioVarEntity> getAudioVar(String audioId) {
		log.debug("getAudioVar("+audioId+")");
		String sql = "SELECT id,audioid,paramtype,paramname,paramvalue,versionid "+
				     "FROM flow.audiovar WHERE audioid="+audioId;
		
		List<AudioVarEntity> result = new ArrayList<AudioVarEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("AudioDAO-getAudioVar");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				AudioVarEntity var = new AudioVarEntity();
				var.setId(rs.getString("id"));
				var.setFormatName(rs.getString("paramtype"));
				var.setFormatParameter(rs.getString("paramname"));
				var.setFormatValue(rs.getString("paramvalue"));
				
				result.add(var);
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
	
	public List<AudioEntity> getByFilter(String where) {
		log.debug("getByFilter("+where+")");
		String sql = "SELECT id,type,name,description,path,context,property,versionid "+
				     "FROM flow.audio <WHERE> ORDER BY name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		
		List<AudioEntity> result = new ArrayList<AudioEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("AudioDAO-getByFilter");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				AudioEntity audio = new AudioEntity();
				audio.setId(rs.getString("id"));
				audio.setType(rs.getString("type"));
				audio.setName(rs.getString("name"));
				audio.setDescription(rs.getString("description"));
				audio.setPath(rs.getString("path"));
				audio.setContext(rs.getString("context"));
				audio.setProperty(rs.getString("property"));
				audio.setVersionId(rs.getString("versionid"));
				audio.setAudioVar(this.getAudioVar(rs.getString("id")));
				result.add(audio);
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
	public List<AudioEntity> getAll() {
		return this.getByFilter(null);
	}
	public AudioEntity get(String id) {
		List<AudioEntity> result = this.getByFilter("WHERE id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;	
	}
	public AudioEntity getByName(String name) {
		List<AudioEntity> result = this.getByFilter("WHERE lower(name) = '"+name.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	public boolean save(AudioEntity audio) {
		boolean result = true;
		log.debug("save()");
		String sql = "INSERT INTO flow.audio (id,type,name,description,path,context,versionid) "+
					 "VALUES ('"+audio.getId()+"','"+audio.getType()+"','"+audio.getName()+"','"+audio.getDescription()+"',"
					 		+ (audio.getPath() == null || audio.getPath().length() == 0 ? "NULL" : "'"+audio.getPath()+"'")+","
					 		+ (audio.getContext() == null || audio.getContext().length() == 0 ? "NULL" : "'"+audio.getContext()+"'")+","+audio.getVersionId()+") ";
		             
		DbConnection db = new DbConnection("AudioDAO-save");
		result = db.ExecuteSql(sql);
		
		if(result) {
			for(AudioVarEntity var : audio.getAudioVar()) {
				sql = "INSERT INTO flow.audiovar (id,audioid,paramtype,paramname,paramvalue,versionid) "+
			          "VALUES ("+var.getId()+","+audio.getId()+",'"+var.getFormatName()+"','"+var.getFormatParameter()+"',"+
			          "'"+var.getFormatValue()+"',"+audio.getVersionId()+")";
				result = result & db.ExecuteSql(sql);
			}
			if(result) {
				Util.audit(audio, Constants.AUDIT_TYPE_ADD);
			} else {
				sql = "DELETE FROM flow.audiovar WHERE audioid = "+audio.getId();
				db.ExecuteSql(sql);
				sql = "DELETE FROM flow.audio WHERE id = "+audio.getId();
				db.ExecuteSql(sql);
				
			}
		}
		db.finalize();

		return result;
	}

	@Override
	public boolean update(AudioEntity audio) {
		boolean result = true;
		log.debug("update()");
		String sql = "UPDATE flow.audio SET type='"+audio.getType()+"',name='"+audio.getName()+"',description='"+audio.getDescription()+"',path='"+audio.getPath()+"',versionid='"+audio.getVersionId()+"' "+
					 "WHERE id = '"+audio.getId()+"' ";
		             
		DbConnection db = new DbConnection("AudioDAO-update");
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.audiovar WHERE audioid = "+audio.getId();
			result = db.ExecuteSql(sql);
			if(result) {
				for(AudioVarEntity var : audio.getAudioVar()) {
					sql = "INSERT INTO flow.audiovar (id,audioid,paramtype,paramname,paramvalue,versionid) "+
				          "VALUES ("+var.getId()+","+audio.getId()+",'"+var.getFormatName()+"','"+var.getFormatParameter()+"',"+
				          "'"+var.getFormatValue()+"',"+audio.getVersionId()+")";
					result = result & db.ExecuteSql(sql);
				}
				if(result) {
					Util.audit(audio, Constants.AUDIT_TYPE_ADD);
				} else {
					sql = "DELETE FROM flow.audiovar WHERE audioid = "+audio.getId();
					db.ExecuteSql(sql);
				}	
			}
			
		}
		db.finalize();
		
		return result;
		
	}

	@Override
	public boolean delete(AudioEntity audio) {
		log.debug("delete()");
		boolean result = true;
		DbConnection db = new DbConnection("AudioDAO-delete");
		
		String sql = "DELETE FROM flow.audiovar WHERE audioid = "+audio.getId();
		result = db.ExecuteSql(sql);
		sql = "DELETE FROM flow.audio WHERE id = '"+audio.getId()+"' ";
		             
		result = result & db.ExecuteSql(sql);
		db.finalize();
		if(result) {
			Util.audit(audio, Constants.AUDIT_TYPE_DELETE);
		}
		return result;
		
	}

}
