package br.com.ims.dashboard.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class OracleConn {
	
	public static Logger log = Logger.getLogger(OracleConn.class);
	
	private static String driver = "oracle.jdbc.OracleDriver";  
    
	InitialContext ctx = null;
	Context envCtx = null;
	DataSource ds = null;
	Connection conn = null;
	Statement stmt = null;	
	ResultSet rs = null;

	public OracleConn(String base) {
		
		if(base.equalsIgnoreCase("SYSCMS")){
			
			String dataBase = "svcpho";  
		    String ip = "vipphoapl1"; //127.0.0.1  
		    String url = "jdbc:oracle:thin:@" + ip + ":1521/" + dataBase;  
		    String user = "syscms";  
		    String password = "syscms";
			try {
				/*ctx = new InitialContext();
				envCtx =  (Context)ctx.lookup("java:/comp/env");
				ds = (DataSource)envCtx.lookup("jdbc/syscms");
				conn = ds.getConnection();*/
				/*ctx = new InitialContext();
				ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/syscms");
				conn = ds.getConnection();*/
				Class.forName(driver); 
				conn = DriverManager.getConnection(url,user,password);  
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if(base.equalsIgnoreCase("RECALL")){
			try {
				ctx = new InitialContext();
				ds = (DataSource)ctx.lookup("jdbc/RECALL_APP");
				conn = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(base.equalsIgnoreCase("URA")){
			try {
				ctx = new InitialContext();
				ds = (DataSource)ctx.lookup("jdbc/URA_PROD");
				conn = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(base.equalsIgnoreCase("IVR_OWNER")){
			String dataBase = "ipura";  
		    String ip = "vipscancrs021"; //127.0.0.1  
		    String url = "jdbc:oracle:thin:@" + ip + ":1521/" + dataBase;  
		    String user = "IVR_PROD";  
		    String password = "yt3a7U89_A";
			
			try {
				/*ctx = new InitialContext();
				ds = (DataSource)ctx.lookup("jdbc/IVR_PROD");
				conn = ds.getConnection();*/
				Class.forName(driver); 
				conn = DriverManager.getConnection(url,user,password);  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void finalize() {
		try {rs.close();} catch(Exception e) {}
		try {stmt.close();} catch(Exception e) {}
		try {conn.close();} catch(Exception e) {}
		try {ctx.close();} catch(Exception e) {}
	}

	public boolean ExecuteSql(String sql) {
		try {
			stmt = conn.createStatement();
			stmt.execute(sql); 
			return true;
		} catch (Exception e) {
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
	    }
	    return rs;
	}
}
