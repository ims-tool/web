package br.com.ims.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ListModel;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.servlet.tags.ParamAware;

import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;
import br.com.ims.tool.entity.User;
import br.com.ims.tool.entity.UserSystemArtifactPermission;
import br.com.ims.tool.exception.DaoException;
import br.com.ims.tool.util.ConnectionDB;
import br.com.ims.util.Artifact;


public class UserControlDao {
	
private static Logger logger = Logger.getLogger(UserControlDao.class);
	
	
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
	
	public String getSystemAccess(String login, String password, String system){
		
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		String response = "NOK";
		try {
			conn = new ConnectionDB();
			
			String query = "SELECT * FROM ACCESS.USER u, "
					+ "ACCESS.USER_ARTIFACT UA, ARTIFACT A, SYSTEM S "
					+ "WHERE upper(u.LOGIN) = '"+login.toUpperCase()+"' and upper(u.password) = '"+password.toUpperCase()+"' "
							+ "and u.id = ua.userid and a.id = ua.artifactid and s.id = a.systemid and "
							+ "s.id = "+system;
			
			rs = conn.ExecuteQuery(query);
			
			if(rs.next()) {
				response = "OK";				
			}
		} catch (Exception e) {
			response = "ERROR";
		}finally{
			conn.finalize();
		}
		return response;
	}
	
	public User getUser(String login) throws DaoException{
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		User user = null;
		
		try {
			if(isInternalUser()) {
				conn = new ConnectionDB("access").getConnection();
				stm = conn.prepareStatement(" SELECT id,login,password, name,email FROM user WHERE login = "+login);
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

	public JSONObject getArtifactBySystem(String userLogin, String system) {
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		String response = "";
		JSONObject objectOut = new JSONObject();
		Map<String, Artifact> mapArtifact = new HashMap<String, Artifact>(); 
		try {
			conn = new ConnectionDB();
			
			String query = "select a.id id, a.description, ua.userid, ua.acesstypeid, up.profileid "
					+ "from access.artifact a, access.user_artifact ua, access.user_profile up "
					+ "where up.userid = ua.userid and ua.artifactid = a.id and ua.userid = (select id from users where upper(login) = '"+userLogin.toUpperCase()+"') and a.systemid = "+ system;
			
			rs = conn.ExecuteQuery(query);
			
			while(rs.next()) {
				String id = rs.getString("description");
				
				if (mapArtifact.containsKey(id)){
					mapArtifact.get(id).add(rs.getInt("profileid"));
				}else{
					Artifact a = new Artifact();
					a.setArtifactid(rs.getInt("id"));
					a.setUserid(rs.getInt("userid"));
					a.setAccesstypeid(rs.getInt("acesstypeid"));
					a.add(rs.getInt("profileid"));
					mapArtifact.put(id, a);
				}
				
			}
		} catch (Exception e) {
			response = "ERROR";
		}finally{
			conn.finalize();
		}
		
		objectOut.put("artifact", mapArtifact);
		
		return objectOut;

	}

	
}
