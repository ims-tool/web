package br.com.ims.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.tags.ParamAware;

import br.com.ims.tool.entity.Parameters;
import br.com.ims.tool.util.ConnectionDB;


public class ParametersDao {
	
private static Logger logger = Logger.getLogger(ParametersDao.class);
	
	
	public static List<Parameters> findAll() {
		
		List<Parameters> listParameters = new ArrayList<Parameters>();
		Parameters parameter = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "select * from flow.parameters";
			
			rs = conn.ExecuteQuery(query);
			
			if (rs.next()) {
				parameter = new Parameters();
				parameter.setId(rs.getInt(1));
				parameter.setName(rs.getString(2));
				parameter.setDescription(rs.getString(3));
				parameter.setType(rs.getString(4));
				parameter.setValue(rs.getString(5));
				parameter.setLoginid(rs.getString(6));
				parameter.setStartdate(rs.getDate(7));
				parameter.setOwner(rs.getString(8));
				parameter.setVersionid(rs.getInt(9));
				
				listParameters.add(parameter);
			
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar parametros ", e);
		} finally {
			conn.finalize();
		}
		return listParameters;
	}
	

}
