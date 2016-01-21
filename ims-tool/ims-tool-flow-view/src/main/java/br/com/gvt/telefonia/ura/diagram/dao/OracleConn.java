package br.com.gvt.telefonia.ura.diagram.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import br.com.gvt.telefonia.ura.util.NovaUraSingleton;

public class OracleConn {
	
	InitialContext ctx = null;
	DataSource ds = null;
	Connection conn = null;
	Statement stmt = null;	
	ResultSet rs = null;

	public OracleConn() {
		this(NovaUraSingleton.getInstance().getDatasource());
	}
	public OracleConn(String base) {
		
		if(base.equals("URA_PROD"))
			try {
				ctx = new InitialContext();
				//ds = (DataSource)ctx.lookup("jdbc/INTERFACE_NOVAURA");
				ds = (DataSource)ctx.lookup("jdbc/URA_PROD");
				conn = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro ao criar conexï¿½o (OracleConn)"+e.getMessage());
			}
		else if(base.equalsIgnoreCase("IVR_PROD"))
			try {
				ctx = new InitialContext();
				//ds = (DataSource)ctx.lookup("jdbc/INTERFACE_NOVAURA");
				ds = (DataSource)ctx.lookup("java:comp/env/jdbc/flow");
				conn = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro ao criar conexão (OracleConn)"+e.getMessage());
			}
		else if(base.equalsIgnoreCase("IVR_PROD_PROD"))
			try {
				ctx = new InitialContext();
				//ds = (DataSource)ctx.lookup("jdbc/INTERFACE_NOVAURA");
				ds = (DataSource)ctx.lookup("jdbc/IVR_PROD_PROD");
				conn = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro ao criar conexï¿½o (OracleConn)"+e.getMessage());
			}
	}

	public void finalize() {
		try { if(rs != null) rs.close();} catch(Exception e) {}
		try {if(stmt != null) stmt.close();} catch(Exception e) {}
		try {if(conn != null) conn.close();} catch(Exception e) {}
		try {if(ctx != null) ctx.close();} catch(Exception e) {}
	}

	public boolean ExecuteSql(String sql) {
		boolean answer = false;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql); 
			answer = true;
		} catch (Exception e) {
		}
		return answer;
	}

	public ResultSet ExecuteQuery(String sql) throws SQLException {
		rs = null;
		stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
	    } catch (Exception e) {
	    }
	    return rs;
	}
	
	public boolean ExecutePLSQL(String sql) throws SQLException {
		
		boolean result = true;
		try {
			CallableStatement cs = conn.prepareCall(sql);
			cs.execute();
	    } catch (Exception e) {
	      e.printStackTrace();
	      result = false;
	    }
	    return result;
	}
	
	public Connection getConnection()throws SQLException{
		return this.conn;
	}
}
