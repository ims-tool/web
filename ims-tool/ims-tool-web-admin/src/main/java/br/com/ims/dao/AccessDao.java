package br.com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ims.exception.DaoException;
import br.com.ims.model.Parameters;
import br.com.ims.model.User;
import br.com.ims.model.UserSystemArtifactPermission;



public class AccessDao {
	
	//private static Logger logger = LoggerFactory.getLogger(AccessDao.class);
	
	public boolean isInternalUser() throws DaoException{
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		boolean retorno = true;
		
		try {
			conn = new ConnectionDB("access").getConnection();
			stm = conn.prepareStatement(" SELECT internal_user FROM access_type");
			rs = stm.executeQuery();
			
			if(rs.next()) {
				
				retorno = rs.getBoolean("internal_user");				
			}
		} catch (SQLException e) {
			throw new DaoException("Erro AccessDao:isInternalUser", e);
		}finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
		return retorno;
		
	}
	
	public User getUser(String login) throws DaoException{
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		User user = null;
		
		try {
			if(isInternalUser()) {
				conn = new ConnectionDB("access").getConnection();
				stm = conn.prepareStatement(" SELECT id,login,password, name,email FROM users WHERE login = "+login);
				rs = stm.executeQuery();
				
				if(rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setLogin(rs.getString("login"));
					user.setPassword(rs.getString("password"));
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
									
				}
			} else {
				user = new User();
				user.setId(1);
				user.setLogin(login);
				user.setPassword(login);
				user.setName("Usuario AD");
				user.setEmail("Usuario AD");
				//pega usuarioAD
			}
			if(user != null) {
				user.setAccess(getAccess(login));
			}
			
		} catch (SQLException e) {
			throw new DaoException("Erro AccessDao:isInternalUser", e);
		}finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
		return user;
	}
		
	private List<UserSystemArtifactPermission> getAccess(String login) throws DaoException{
		
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		List<UserSystemArtifactPermission> access = new ArrayList<UserSystemArtifactPermission>();
		
		try {
				
			conn = new ConnectionDB("access").getConnection();
			stm = conn.prepareStatement(" select s.name as system, a.name artifact,ua.accesspermissionid permission "+  
										" from user_access ua,system_artifact sa, system s, artifact a "+ 
										" WHERE ua.systemartifactid = sa.id " +
										" and sa.systemid = s.id " +
										" and sa.artifactid = a.id "+
										" and ua.login = '"+login+"' ");
			rs = stm.executeQuery();
			
			while(rs.next()) {
				UserSystemArtifactPermission obj = new UserSystemArtifactPermission();
				obj.setSystem(rs.getString("system"));
				obj.setArtifact(rs.getString("artifact"));
				obj.setPermission(rs.getString("permission"));
				access.add(obj);
									
			}
			
			
		} catch (SQLException e) {
			throw new DaoException("Erro AccessDao:getAccess", e);
		}finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
		return access;
	}
	
	
}
