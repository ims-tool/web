package br.com.gvt.telefonia.ura.diagram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.LogPontos;
import br.com.gvt.telefonia.ura.util.Reflection;

public class LogPontosDAO extends DAO<LogPontos> {

	@Override
	public String getClassName() {
		return LogPontos.class.getSimpleName();
	}
	
	public List<LogPontos> findAll()
	{
		List<LogPontos> list = new ArrayList<LogPontos>();
		
		String sql = "select * from ura.log_pontos order by id";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> objectAttributes = null;  
		HashMap<String,String> parameter = null;
		try {			
			conn = new OracleConn("URA_PROD").getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					parameter = new HashMap<String,String>();
					
					LogPontos instance = new LogPontos();
					
					objectAttributes = Reflection.getAttributesList(instance);
					for(String element : objectAttributes)
						try {
							parameter.put(element,rs.getObject(element).toString());
						} catch(Exception e){
							e.getStackTrace();
						}
					
					Reflection.setAll(instance, parameter);
					
					list.add(instance);
				}
			}
			
		} catch (Exception e) {
			e.getStackTrace();
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		
		return list;
	}
	
	public List<LogPontos> findAllBy(String where)
	{
		List<LogPontos> list = new ArrayList<LogPontos>();
		
		String sql = "select * from ura.log_pontos where " + where;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> objectAttributes = null;  
		HashMap<String,String> parameter = null;
		try {			
			conn = new OracleConn("URA_PROD").getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					parameter = new HashMap<String,String>();
					LogPontos instance = new LogPontos();
					
					objectAttributes = Reflection.getAttributesList(instance);
					for(String element : objectAttributes)
						try {
							parameter.put(element,rs.getObject(element).toString());
						} catch(Exception e){}
					
					Reflection.setAll(instance, parameter);
					
					list.add(instance);
				}
			}
			
		} catch (Exception e) {
			e.getStackTrace();
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		
		return list;
	}
	
	public LogPontos findBy(String where)
	{
		LogPontos obj = null;
		
		String sql = "select * from ura.log_pontos where " + where;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> objectAttributes = null;  
		HashMap<String,String> parameter = new HashMap<String,String>();
		try {			
			conn = new OracleConn("URA_PROD").getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					obj = new LogPontos();
					
					objectAttributes = Reflection.getAttributesList(obj);
					for(String element : objectAttributes)
						try {
							parameter.put(element,rs.getObject(element).toString());
						} catch(Exception e){}
					
					Reflection.setAll(obj, parameter);
				}
			}
			
		} catch (Exception e) {
			e.getStackTrace();
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		
		return obj;
	}

}
