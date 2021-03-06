package br.com.ims.tool.nextform.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ConnectionDB {
	public static Logger log = Logger.getLogger(ConnectionDB.class);
	

	InitialContext ctx = null;
	DataSource ds = null;
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	public ConnectionDB() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/flow");
			conn = ds.getConnection();
		} catch (Exception e) {
			log.error("ConnectionDB()-"+e.getMessage(),e);
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return conn;
	}

	public void finalize() {
		try {
			rs.close();
		} catch (Exception e) {
		}
		try {
			stmt.close();
		} catch (Exception e) {
		}
		try {
			conn.close();
		} catch (Exception e) {
		}
		try {
			ctx.close();
		} catch (Exception e) {
		}
		try {
			pstmt.close();
		} catch (Exception e) {
		}
	}

	public boolean ExecuteSql(String sql) {
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(sql);
			log.error("ExecuteSql("+sql+")-"+e.getMessage(),e);
			return false;
		}
	}

	public ResultSet ExecuteQuery(String sql) throws SQLException {
		rs = null;
		stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			log.error("ExecuteQuery("+sql+")-"+e.getMessage(),e);
			e.printStackTrace();
		}
		return rs;
	}

	public PreparedStatement getPreparedStatement(String sql)
			throws SQLException {
		return conn.prepareStatement(sql);
	}

	public ResultSet executeQuery(PreparedStatement pst) {
		rs = null;
		try {
			rs = pst.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("executeQuery("+pst.toString()+")-"+e.getMessage(),e);
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
			}
		}
		return rs;
	}
	
	public void executeInsert(PreparedStatement pst) {
		try {
			pst.execute();			

		} catch (SQLException e) {
			log.error("executeInsert("+pst.toString()+")-"+e.getMessage(),e);
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
			}
		}
	}

}
