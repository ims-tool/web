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

	
	public String getSystemAccess(String login, String password, String system){
		
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		String response = "NOK";
		try {
			conn = new ConnectionDB();
			
			String query = "SELECT * FROM ACCESS.USER u, "
					+ "ACCESS.artifact_access_type_user UA, ARTIFACT A, SYSTEM S "
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

	public List<User> findAll() {
		
		ConnectionDB conn = null;
		ResultSet rs = null;
		
		List<User> access = new ArrayList<User>();
		
		try {
				
			conn = new ConnectionDB();
			String query = "select * from ACCESS.user a";
			
			rs = conn.ExecuteQuery(query);
			
			while(rs.next()) {
				User obj = new User();
				obj.setId(rs.getInt(1));
				obj.setLogin(rs.getString(2));
				obj.setPassword(rs.getString(3));
				obj.setName(rs.getString(4));
				obj.setEmail(rs.getString(5));
				access.add(obj);
			}
			
		} catch (SQLException e) {
			try {
				throw new DaoException("Erro AccessDao:getAccess", e);
			} catch (DaoException e1) {
				e1.printStackTrace();
			}
		}finally {
			conn.finalize();
		}
		return access;
	}

	public User getUser(String userLogin) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void save(User user) {
		
		String sql = "";
		ResultSet rs = null;
		ConnectionDB conn = null;
		
		if(user.getId() == -1){
			//new user
			sql  = "insert into access.user (name, email, login, password, id) values ('"+user.getName()+"', '"+user.getEmail()+"', '"+user.getLogin()+"' , '"+user.getPassword()+"', nextval('serial'))";
			
		}else{
			sql = "update access.user set name= '"+user.getName()+"', email= '"+user.getEmail()+"', login = '"+user.getLogin()+"', password = '"+user.getPassword()+"' where id="+user.getId(); 
		}
		
		conn = new ConnectionDB();
		
		try {
			rs = conn.ExecuteQuery(sql);
		} catch (SQLException e) {
			try {
				throw new DaoException("Erro AccessDao:getAccess", e);
			} catch (DaoException e1) {
				
			}
		} finally {
			conn.finalize();
		}
		
	}

	public static void remove(User user) {
		
		String sql = "";
		ResultSet rs = null;
		ConnectionDB conn = null;
		
			sql  = "";
			
		
		conn = new ConnectionDB();
		
		try {
			rs = conn.ExecuteQuery(sql);
		} catch (SQLException e) {
			try {
				throw new DaoException("Erro AccessDao:getAccess", e);
			} catch (DaoException e1) {
				
			}
		} finally {
			conn.finalize();
		}
		
	}

	
}
