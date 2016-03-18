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
import br.com.ims.flow.model.VersionEntity;

public class PromptDAO extends AbstractDAO<PromptEntity> {
	private List<PromptEntity> listPrompts =  null;
	private static PromptDAO instance = null;
	private PromptDAO() {
		listPrompts = new ArrayList<PromptEntity>(); 			
	}
	
	public static PromptDAO getInstance() {
		if(instance == null) {
			instance = new PromptDAO();
		}
		return instance;
	}
	private List<PromptAudioEntity> getPromptAudio(String idPrompt) {
		DbConnection db = new DbConnection("");
		String sql = "SELECT prompt,audio,ordernum,condition,versionid FROM flow.promptaudio ORDER BY ordernum";
		List<PromptAudioEntity> result = new ArrayList<PromptAudioEntity>(); 
		try {
			ResultSet rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				PromptAudioEntity promptAudio = new PromptAudioEntity ();
				
				promptAudio.setPromptId(rs.getString("prompt"));
				AudioEntity audio = ServicesFactory.getInstance().getAudioService().get(rs.getString("audio"));
				promptAudio.setAudio(audio);
				promptAudio.setOrderNum(rs.getInt("ordernum"));
				ConditionEntity condition = ServicesFactory.getInstance().getConditionService().get(rs.getString("condition")); 
				promptAudio.setCondition(condition);
				VersionEntity version = ServicesFactory.getInstance().getVersionService().get(rs.getString("versionid"));
				promptAudio.setVersionId(version);
				
				result.add(promptAudio);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.finalize();
		}
		
		return result;
	}
	public List<PromptEntity> getAll() {
		
		DbConnection db = new DbConnection("");
		String sql = "SELECT id,name,versionid FROM flow.prompt ORDER BY name";
		List<PromptEntity> result = new ArrayList<PromptEntity>(); 
		try {
			ResultSet rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				PromptEntity prompt = new PromptEntity ();
				prompt.setId(rs.getString("id"));
				prompt.setName(rs.getString("name"));
				List<PromptAudioEntity> promptAudioList = getPromptAudio(rs.getString("id"));
				prompt.setAudios(promptAudioList);
				VersionEntity version = ServicesFactory.getInstance().getVersionService().get(rs.getString("versionid"));
				prompt.setVersionId(version);
				result.add(prompt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.finalize();
		}
		
		return result;
	}
	public PromptEntity get(String id) {
		for(PromptEntity prompt : this.listPrompts) {
			if(prompt.getId().equals(id)) {
				return prompt;
			}
		}
		return null;
	}
	public boolean save(PromptEntity prompt) {
		this.listPrompts.add(prompt);
		return true;
	}

	@Override
	public boolean update(PromptEntity entity) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean delete(PromptEntity entity) {
		// TODO Auto-generated method stub
		return true;
		
	}

}
