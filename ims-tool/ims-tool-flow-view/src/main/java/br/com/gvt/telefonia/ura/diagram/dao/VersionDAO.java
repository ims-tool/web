package br.com.gvt.telefonia.ura.diagram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.gvt.telefonia.ura.diagram.model.Entity;
import br.com.gvt.telefonia.ura.diagram.model.Version;
import br.com.gvt.telefonia.ura.util.Reflection;

public class VersionDAO extends DAO<Version> {
	@Override
	public String getClassName() {
		return Version.class.getSimpleName();
	}
	
	public String insert(Entity<?> entity)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String description = Reflection.invokeGetMethod(entity, "description");
		String deploydate = Reflection.invokeGetMethod(entity, "deploydate");
		
		String sql = "insert into flow."+getClassName().toLowerCase()+" values (flow.GET_UID,";
		sql += "'" + description +"',to_date('" + deploydate + "','dd/mm/yyyy HH24:mi:ss')";
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
	
	public void update(Entity<?> entity)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String description = Reflection.invokeGetMethod(entity, "description");
		String deploydate = Reflection.invokeGetMethod(entity, "deploydate");
		
		String sql = "update flow."+getClassName().toLowerCase()+" set description='"+description+"',deploydate='"+deploydate+"' ";
		sql += " where id = '" + Reflection.invokeGetMethod(entity, "id") + "'";
		
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			stmt.executeQuery();
			
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
