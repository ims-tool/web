package br.com.gvt.telefonia.ura.diagram.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.gvt.telefonia.ura.diagram.model.Entity;
import br.com.gvt.telefonia.ura.util.Reflection;

public abstract class DAO<T extends Entity<?>> {
	
	public abstract String getClassName();
	
	public String lastInsertId()
	{
		String last = "";
		String sql = "select MAX(ID) as last from FLOW."+getClassName().toLowerCase();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					last = rs.getString("last");
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
		
		return last;
	}
	
	public String insert(Entity<?> entity)
	{
		String lastId = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		Field[] fields = entity.getClass().getDeclaredFields();
		
		String sql = "insert into FLOW."+getClassName().toLowerCase()+" values (FLOW.GET_UID,";
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
			System.out.println(e.getStackTrace());
			e.getStackTrace();
			return "";
		}finally{
			try {
				
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		
		lastId = lastInsertId();
		Reflection.invokeSetMethod(entity, "id", lastId);
		
		return lastId;
	}
	
	public void update(Entity<?> entity)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		Field[] fields = entity.getClass().getDeclaredFields();
		
		String sql = "update FLOW."+getClassName().toLowerCase()+" set ";
		for(Field attribute : fields)
		{
			if(!attribute.getName().equalsIgnoreCase("id"))
			sql += attribute.getName() + "='" + Reflection.invokeGetMethod(entity, attribute.getName())+"',";
		}
		
		sql = sql.substring(0,(sql.length()-1));
		sql += " where id = '" + Reflection.invokeGetMethod(entity, "id") + "'";
		
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			stmt.executeQuery();
			
		} catch (Exception e) {
			e.getStackTrace();
		}finally{
			try {
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}
	
	public String save(Entity<T> entity)
	{
		String result = "";
		try{
			
			if(Long.parseLong(Reflection.invokeGetMethod(entity, "id")) > 0)
			{
				result = Reflection.invokeGetMethod(entity, "id");
				update(entity);
			}else
				result = insert(entity);
			
		} catch(Exception e)
		{
			e.getStackTrace();
		}
		
		return result;
	}
	
	public boolean delete(String where){
		
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String sql = "delete from FLOW."+getClassName().toLowerCase()+" where "+where;
		
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			stmt.executeQuery();
			result = true;
			
		} catch (Exception e) {
			e.getStackTrace();
		}finally{
			try {
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		
		return result;
	}
	
	public T findByAttributes(HashMap<String, String> param)
	{
		String sql = "";
		Iterator<?> it = param.entrySet().iterator();
		
	    while (it.hasNext()) {
	    	
	        @SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry)it.next();
	        
	        if(!sql.equalsIgnoreCase(""))
	        	sql += " and ";
	        
	        sql += " " + pairs.getKey() + " = " + pairs.getValue() + " ";
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
		return findBy(sql);
	}
	
	public T findByName(String formName){
		
		return findBy(" name= '"+formName+"' ");
	}
	
	@SuppressWarnings("unchecked")
	public T findBy(String where)
	{
		T obj = null;
		
		String sql = "select * from FLOW."+getClassName().toLowerCase()+" where " + where;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> objectAttributes = null;  
		HashMap<String,String> parameter = new HashMap<String,String>();
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					obj = (T) Class.forName("br.com.gvt.telefonia.ura.diagram.model."+getClassName()).newInstance();
					
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
	
	
	@SuppressWarnings("unchecked")
	public List<T> findAllBy(String where)
	{
		List<T> list = new ArrayList<T>();
		
		String sql = "select * from FLOW."+getClassName().toLowerCase()+" where " + where;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> objectAttributes = null;  
		HashMap<String,String> parameter = null;
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					parameter = new HashMap<String,String>();
					T instance = (T) Class.forName("br.com.gvt.telefonia.ura.diagram.model."+getClassName()).newInstance();
					
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
	
	
	public List<T> findAll()
	{
		List<T> list = new ArrayList<T>();
		
		String sql = "select * from FLOW."+getClassName().toLowerCase();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> objectAttributes = null;  
		HashMap<String,String> parameter = null;
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					parameter = new HashMap<String,String>();
					
					@SuppressWarnings("unchecked")
					T instance = (T) Class.forName("br.com.gvt.telefonia.ura.diagram.model."+getClassName()).newInstance();
					
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
	
	public T findByPk(String id)
	{
		return findBy(" id = '"+id+"' ");
	}
}