package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.ims.flow.common.DbConnection;

public class SequenceDAO {
	private static SequenceDAO instance = null;
	private DbConnection db =  null;
	private SequenceDAO() {
		db =  new DbConnection("");		
	}
	
	public static SequenceDAO getInstance() {
		if(instance == null) {
			instance = new SequenceDAO();
		}
		return instance;
	}

	public String getNextVal(String sequenceName) {
		ResultSet rs = null;
		String result = "";
		try {
			
			rs = db.ExecuteQuery("select nextval('"+sequenceName+"')");
			if(rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};
			
		}
		return result;
	}

}
	