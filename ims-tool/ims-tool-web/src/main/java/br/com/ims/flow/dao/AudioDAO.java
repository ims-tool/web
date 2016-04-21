package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.AudioEntity;

@SuppressWarnings("serial")
public class AudioDAO extends AbstractDAO<AudioEntity>{
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
	
	public List<AudioEntity> getByFilter(String where) {
		
		String sql = "SELECT id,type,name,description,path,property,versionid "+
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
				audio.setProperty(rs.getString("property"));
				audio.setVersionId(rs.getString("versionid"));
				result.add(audio);
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
		
		audit(audio);
		String sql = "INSERT INTO flow.audio (id,type,name,description,path,versionid) "+
					 "VALUES ('"+audio.getId()+"','"+audio.getType()+"','"+audio.getName()+"','"+audio.getDescription()+"','"+audio.getPath()+"',"+audio.getVersionId()+") ";
		             
		DbConnection db = new DbConnection("AudioDAO-save");
		result = db.ExecuteSql(sql);
		db.finalize();
		
		return result;
	}

	@Override
	public boolean update(AudioEntity audio) {
		boolean result = true;
		String sql = "UPDATE flow.audio SET type='"+audio.getType()+"',name='"+audio.getName()+"',description='"+audio.getDescription()+"',path='"+audio.getPath()+"',versionid='"+audio.getVersionId()+"' "+
					 "WHERE id = '"+audio.getId()+"' ";
		             
		DbConnection db = new DbConnection("AudioDAO-update");
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

	@Override
	public boolean delete(AudioEntity audio) {
		boolean result = true;
		String sql = "DELETE FROM flow.audio WHERE id = '"+audio.getId()+"' ";
		DbConnection db = new DbConnection("AudioDAO-delete");             
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

}
