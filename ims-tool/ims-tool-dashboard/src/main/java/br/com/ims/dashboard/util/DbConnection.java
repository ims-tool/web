package br.com.ims.dashboard.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class DbConnection implements Serializable{
	public static Logger log = Logger.getLogger(DbConnection.class);
	
	InitialContext ctx = null;
	Context envCtx = null;
	DataSource ds = null;
	Connection conn = null;
	Statement stmt = null;	
	ResultSet rs = null;
	
	public DbConnection(String database) {
		
		try {
			//System.out.println("Constructing:"+database);
			ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/flow");
			conn = ds.getConnection();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	public void finalize() {
		//System.out.println("finalizing");
		try {rs.close();} catch(Exception e) {}
		try {stmt.close();} catch(Exception e) {}
		try {ctx.close();} catch(Exception e) {}
		try {conn.close();} catch(Exception e) {}
		
	}
	public boolean ExecuteSql(String sql) {
		try {
			stmt = conn.createStatement();
			stmt.execute(sql); 
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(sql);
			log.error(e.getMessage(), e);
			log.debug(sql);
			return false;
		}
	}

	public ResultSet ExecuteQuery(String sql) throws SQLException {
		rs = null;
		stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			
	    } catch (Exception e) {
	      e.printStackTrace();
		  log.error(e.getMessage(), e);
		  log.debug(sql);
	      System.out.println(sql);
	    }
	    return rs;
	}
}
