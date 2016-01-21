package br.com.gvt.telefonia.ura.diagram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Router;

public class RouterDAO extends DAO<Router> {
		
	public List<Router> findAllDistinct()
	{
		Router obj = null;
		List<Router> routerList = new ArrayList<Router>();
		String sql = "select distinct formname,description from ivr_owner.router";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					obj = new Router();
					obj.setDnis(rs.getString("dnis"));
					obj.setFormname(rs.getString("formname"));
					obj.setDescription(rs.getString("description"));
					routerList.add(obj);
				}
			}
			
		} catch (Exception e) {
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
		
		return routerList;
		
	}
	
	public Router findBy(String where)
	{
		Router obj = null;
		
		String sql = "select * from ivr_owner.router where " + where;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					obj = new Router();
					obj.setDnis(rs.getString("dnis"));
					obj.setFormname(rs.getString("formname"));
					obj.setDescription(rs.getString("description"));
				}
			}
			
		} catch (Exception e) {
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

	@Override
	public String getClassName() {
		return Router.class.getSimpleName();
	}

}
