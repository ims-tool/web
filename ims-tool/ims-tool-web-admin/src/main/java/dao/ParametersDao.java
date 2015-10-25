package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ims.exception.DaoException;
import model.Parameters;



public class ParametersDao {
	
	private static Logger logger = LoggerFactory.getLogger(ParametersDao.class);
	
	public List<Parameters> getAll() throws DaoException {
		
		
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		
		List<Parameters> listaParameters = new ArrayList<Parameters>();
		
		try {
			conn = new ConnectionDB().getConnection();
			stm = conn.prepareStatement(" SELECT * FROM ivr_owner.parameters where owner = 'CO' ORDER BY ID ");
			rs = stm.executeQuery();
			
			while (rs.next()) {
				
				Parameters parameters = new Parameters();
				parameters.setId(rs.getLong("ID"));
				parameters.setName(rs.getString("NAME"));
				parameters.setDescription(rs.getString("DESCRIPTION"));
				parameters.setType(rs.getString("TYPE"));
				parameters.setValue(rs.getString("VALUE"));
				parameters.setStartdate(rs.getDate("startdate"));
				parameters.setOwner(rs.getString("OWNER"));
				/*parameters.setValidation(rs.getString("VALIDATION"));*/
				listaParameters.add(parameters);
			}
		} catch (SQLException e) {
			throw new DaoException("Erro ao Regras de Transferencia.", e);
		}finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
		
		return listaParameters;
	}
		
	public Parameters getById(Long id) throws DaoException {
		
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement stm = null;
		
		Parameters parameters = null;
		
		try {
			conn = new ConnectionDB().getConnection();
			stm = conn.prepareStatement(" SELECT * FROM ivr_owner.parameters where id = ? ORDER BY ID ");
			stm.setLong(1, id);
			
			rs = stm.executeQuery();
			
			while (rs.next()) {
				
				parameters = new Parameters();
				parameters.setId(rs.getLong("ID"));
				parameters.setName(rs.getString("NAME"));
				parameters.setDescription(rs.getString("DESCRIPTION"));
				parameters.setType(rs.getString("TYPE"));
				parameters.setValue(rs.getString("VALUE"));
				parameters.setStartdate(rs.getDate("startdate"));
				parameters.setOwner(rs.getString("OWNER"));
				/*parameters.setValidation(rs.getString("VALIDATION"));*/
			}
		} catch (SQLException e) {
			throw new DaoException("Erro ao Regras de Transferencia.", e);
		}finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
		
		return parameters;
	}
	
	public boolean save(Parameters Parameters )  throws DaoException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try{
			conn = new ConnectionDB().getConnection();
			stm = conn.prepareStatement("update ivr_owner.parameters set name=?,description=?,type=?,value=?,startdate=sysdate,loginid=?,owner=? where id = ?");
			stm.setString(1, Parameters.getName());
			stm.setString(2, Parameters.getDescription());
			stm.setString(3, Parameters.getType());
			stm.setString(4, Parameters.getValue());
			stm.setString(5, Parameters.getLoginid());
			stm.setString(6, Parameters.getOwner());
			stm.setLong(7, Parameters.getId());
			return stm.executeUpdate() > 0;
		} catch(SQLException e) {
			throw new DaoException("Erro ao salvar parametro", e);
			
		} finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
	}
	
}
