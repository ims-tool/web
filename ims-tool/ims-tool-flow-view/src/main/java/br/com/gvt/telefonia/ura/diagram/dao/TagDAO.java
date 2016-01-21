package br.com.gvt.telefonia.ura.diagram.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.gvt.telefonia.ura.diagram.model.Audio;
import br.com.gvt.telefonia.ura.diagram.model.Entity;
import br.com.gvt.telefonia.ura.diagram.model.Tag;
import br.com.gvt.telefonia.ura.util.Reflection;

public class TagDAO extends DAO<Audio> {
	@Override
	public String getClassName() {
		return Tag.class.getSimpleName();
	}
	
	public String insert(Entity<?> entity)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		Field[] fields = entity.getClass().getDeclaredFields();
		
		Integer lastTag = Integer.parseInt(lastInsertId());
		lastTag++;
		
		String sql = "insert into flow."+getClassName().toLowerCase()+" values ('"+lastTag+"',";
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
			return "";
		}
		
		return lastInsertId();
	}
}
