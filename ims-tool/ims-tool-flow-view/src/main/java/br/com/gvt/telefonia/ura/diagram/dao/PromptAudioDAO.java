package br.com.gvt.telefonia.ura.diagram.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Entity;
import br.com.gvt.telefonia.ura.diagram.model.PromptAudio;
import br.com.gvt.telefonia.ura.util.Reflection;

public class PromptAudioDAO extends DAO<PromptAudio> {

	public List<PromptAudio> findByPrompt(String pk){
		
		return findAllBy(" prompt = '"+pk+"' order by ordernum ");
	}

	@Override
	public String getClassName() {
		return PromptAudio.class.getSimpleName();
	}
	
	public String insert(Entity<?> entity)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		Field[] fields = entity.getClass().getDeclaredFields();
		
		String sql = "insert into ivr_owner."+getClassName().toLowerCase()+" values (";
		for(Field attribute : fields)
		{
			if(!attribute.getName().equalsIgnoreCase("id"))
				sql += "'" + Reflection.invokeGetMethod(entity, attribute.getName())+"',";
		}
		
		sql = sql.substring(0,(sql.length()-1));
		sql += ")";
		
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			stmt.executeQuery();
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		return "";
	}

}
