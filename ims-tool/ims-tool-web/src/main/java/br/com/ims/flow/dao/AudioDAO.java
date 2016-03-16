package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AudioEntity;
import br.com.ims.flow.model.VersionEntity;

public class AudioDAO extends AbstractDAO<AudioEntity>{
	private static AudioDAO instance = null;
	private AudioDAO() {
		 			
	}
	
	public static AudioDAO getInstance() {
		if(instance == null) {
			instance = new AudioDAO();
		}
		return instance;
	}
	
	public List<AudioEntity> getAll() {
		
		db = new DbConnection("");
		String sql = "SELECT id,type,name,description,path,property,versionid FROM flow.audio ORDER BY name";
		List<AudioEntity> result = new ArrayList<AudioEntity>(); 
		try {
			ResultSet rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				AudioEntity audio = new AudioEntity();
				audio.setId(rs.getString("id"));
				audio.setType(rs.getString("type"));
				audio.setName(rs.getString("name"));
				audio.setPath(rs.getString("path"));
				audio.setProperty(rs.getString("property"));
				VersionEntity version = ServicesFactory.getInstance().getVersionService().get(rs.getString("versionid"));
				audio.setVersionId(version);
				result.add(audio);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.finalize();
		}
		
		return result;
		
	}
	public AudioEntity get(String id) {
		db = new DbConnection("");
		String sql = "SELECT id,type,name,description,path,property,versionid "+
		             "FROM flow.audio WHERE id = '"+id+"' ";
		AudioEntity audio = null;
		try {
			ResultSet rs = db.ExecuteQuery(sql);
			if(rs.next()) {
				
				audio = new AudioEntity();
				audio.setId(rs.getString("id"));
				audio.setType(rs.getString("type"));
				audio.setName(rs.getString("name"));
				audio.setPath(rs.getString("path"));
				audio.setProperty(rs.getString("property"));
				VersionEntity version = ServicesFactory.getInstance().getVersionService().get(rs.getString("versionid"));
				audio.setVersionId(version);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.finalize();
		}
		return audio;
		
	}
	public AudioEntity getByName(String name) {
		db = new DbConnection("");
		String sql = "SELECT id,type,name,description,path,property,versionid "+
		             "FROM flow.audio WHERE lower(name) = '"+name.toLowerCase()+"' ";
		AudioEntity audio = null;
		try {
			ResultSet rs = db.ExecuteQuery(sql);
			if(rs.next()) {
				
				audio = new AudioEntity();
				audio.setId(rs.getString("id"));
				audio.setType(rs.getString("type"));
				audio.setName(rs.getString("name"));
				audio.setPath(rs.getString("path"));
				audio.setProperty(rs.getString("property"));
				VersionEntity version = ServicesFactory.getInstance().getVersionService().get(rs.getString("versionid"));
				audio.setVersionId(version);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.finalize();
		}
		return audio;
	}
	public boolean save(AudioEntity audio) {
		boolean result = true;
		db = new DbConnection("");
		String sql = "INSERT INTO flow.audio (id,type,name,description,path,versionid) "+
					 "VALUES ('"+audio.getId()+"','"+audio.getType()+"','"+audio.getName()+"','"+audio.getDescription()+"','"+audio.getPath()+"',"+audio.getVersionId().getId()+") ";
		             
		
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
	}

	@Override
	public boolean update(AudioEntity audio) {
		boolean result = true;
		db = new DbConnection("");
		String sql = "UPDATE flow.audio SET type='"+audio.getType()+"',name='"+audio.getName()+"',description='"+audio.getDescription()+"',path='"+audio.getPath()+"',versionid='"+audio.getVersionId().getId()+"' "+
					 "WHERE id = '"+audio.getId()+"' ";
		             
		
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

	@Override
	public boolean delete(AudioEntity audio) {
		boolean result = true;
		db = new DbConnection("");
		String sql = "DELETE FROM flow.audio WHERE id = '"+audio.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		db.finalize();
		return result;
		
	}

}
