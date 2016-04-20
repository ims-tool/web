package br.com.ims.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.ims.tool.entity.Access;
import br.com.ims.tool.entity.AccessByUser;
import br.com.ims.tool.entity.User;
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
		Boolean isNewUser = Boolean.FALSE;
		try {
			conn = new ConnectionDB();
			String query = "";
			if(StringUtils.isNotBlank(password)){
				query = "SELECT * FROM ACCESS.USER u, "
						+ "ACCESS.artifact_access_type_user UA, ACCESS.artifact A, ACCESS.SYSTEM S "
						+ "WHERE upper(u.LOGIN) = '"+login.toUpperCase()+"' and upper(u.password) = '"+password.toUpperCase()+"' "
						+ "and u.id = ua.userid and a.id = ua.artifactid and s.id = a.systemid and "
						+ "s.id = "+system;
			}else{
				query = "SELECT * FROM ACCESS.USER u, "
						+ "ACCESS.artifact_access_type_user UA, ACCESS.artifact A, ACCESS.SYSTEM S "
						+ "WHERE upper(u.LOGIN) = '"+login.toUpperCase()+"' and (u.password is null or u.password = '') "
						+ "and u.id = ua.userid and a.id = ua.artifactid and s.id = a.systemid and "
						+ "s.id = "+system;
				
			}
			
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
		List<Artifact> listArtifact = new ArrayList<Artifact>();
			
		try{	
			
			conn = new ConnectionDB();
			String query = "select a.id id, a.description, ua.userid, ua.acesstypeid, ua.areaid, at.priority "
					+ "from access.artifact a, access.artifact_access_type_user ua, access.access_type at "
					+ "where ua.acesstypeid = at.id and a.id = ua.artifactid and  ua.userid = (select id from access.user where upper(login) = '"+userLogin.toUpperCase()+"') and a.systemid = "+ system;
			
			rs = conn.ExecuteQuery(query);
			
			while(rs.next()) {
				
					Artifact a = new Artifact();
					a.setArtifactid(rs.getInt("id"));
					a.setDescription(rs.getString("description"));
					a.setUserid(rs.getInt("userid"));
					a.setAccesstypeid(rs.getInt("acesstypeid"));
					a.setAreaid(rs.getInt("areaid"));
					a.setPriority(rs.getInt("priority"));
					listArtifact.add(a);
				}
		} catch (Exception e) {
			e.printStackTrace();
			response = "ERROR";
		}finally{
			conn.finalize();
		}
		
		objectOut.put("artifact", listArtifact);
		
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
			
		}else if (user.getId() == -2){
			sql = "update access.user set password = '"+user.getPassword()+"' where login = '"+user.getLogin()+"'"; 
		}else{
			sql = "update access.user set name= '"+user.getName()+"', email= '"+user.getEmail()+"', login = '"+user.getLogin()+"', password = '"+user.getPassword()+"' where id="+user.getId(); 
		}
		
		conn = new ConnectionDB();
		
		try {
			conn.ExecuteQueryUpdate(sql);
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
		Integer id = user.getId();
		
		sql  = "delete from access.user_area ua where userid ="+id;
		conn = new ConnectionDB();
		
		try {
			conn.ExecuteQueryUpdate(sql);
		} catch (Exception e) {
		} finally {
			conn.finalize();
		}
		
		sql  = "delete from access.artifact_access_type_user ua where userid ="+id;
		conn = new ConnectionDB();
		
		try {
			conn.ExecuteQueryUpdate(sql);
		} catch (Exception e) {
		} finally {
			conn.finalize();
		}
		
		sql  = "delete from access.user ua where id ="+id;
		conn = new ConnectionDB();
		
		try {
			conn.ExecuteQueryUpdate(sql);
		} catch (Exception e) {
		} finally {
			conn.finalize();
		}
		
	}

	public List<AccessByUser> getAccessByUser(Integer id) {
		
		
		String sql = "select  s.description as system, a.description as artifact, at.description as access_type, ar.description as area from "+ 
		"access.artifact_access_type_user au, "+
		"access.artifact a, "+
		"access.system s, "+
		"access.access_type at, "+
		"access.area ar "+ 
		"where "+  
			"a.id = au.artifactid and "+
			"s.id = a.systemid and "+
			"at.id = au.acesstypeid and "+
			"ar.id = au.areaid and "+
		"userid ="+id ;
		ResultSet rs = null;
		ConnectionDB conn = null;
		List<AccessByUser> list = new ArrayList<AccessByUser>();
		conn = new ConnectionDB();
		
		try {
			rs = conn.ExecuteQuery(sql);
			while(rs.next()){
				AccessByUser abu = new AccessByUser();
				
				abu.setSystem(rs.getString(1));
				abu.setArtifact(rs.getString(2));
				abu.setAccessType(rs.getString(3));
				abu.setArea(rs.getString(4));
				list.add(abu);
				
			}
		} catch (Exception e) {
		} finally {
			conn.finalize();
		}
		
		return list;
	}

	public List<String> findSystem() {
		
		List<String> system = new ArrayList<String>();
		String sql = "select description from access.system";
				ResultSet rs = null;
				ConnectionDB conn = null;
				conn = new ConnectionDB();
				
				try {
					rs = conn.ExecuteQuery(sql);
					while(rs.next()){
						system.add(rs.getString(1));
					}
				} catch (Exception e) {
				} finally {
					conn.finalize();
				}
				
		return system;
	}



	public List<String> findArtifactBySystem(String system) {
		List<String> artifacts = new ArrayList<String>();
		String sql = "select a.description from access.artifact a, access.system s where s.id = a.systemid and s.description like '"+system+"'";
				ResultSet rs = null;
				ConnectionDB conn = null;
				conn = new ConnectionDB();
				
				try {
					rs = conn.ExecuteQuery(sql);
					while(rs.next()){
						artifacts.add(rs.getString(1));
					}
				} catch (Exception e) {
				} finally {
					conn.finalize();
				}
				
		return artifacts;
	}
	
	public List<String> findAccessType() {
		
		List<String> accessTypes = new ArrayList<String>();
		String sql = "select description from access.access_type";
				ResultSet rs = null;
				ConnectionDB conn = null;
				conn = new ConnectionDB();
				
				try {
					rs = conn.ExecuteQuery(sql);
					while(rs.next()){
						accessTypes.add(rs.getString(1));
					}
				} catch (Exception e) {
				} finally {
					conn.finalize();
				}
				
		return accessTypes;
	}



	public List<String> findArea() {

		List<String> accessTypes = new ArrayList<String>();
		String sql = "select description from access.area";
				ResultSet rs = null;
				ConnectionDB conn = null;
				conn = new ConnectionDB();
				
				try {
					rs = conn.ExecuteQuery(sql);
					while(rs.next()){
						accessTypes.add(rs.getString(1));
					}
				} catch (Exception e) {
				} finally {
					conn.finalize();
				}
				
		return accessTypes;
	}



	public String saveAccess(Access access, String area) {
		
		String status = "OK";
			String sql = "insert into access.artifact_access_type_user (userid, artifactid, acesstypeid, areaid) values ( "+access.getUserid()+", "
					+ "(select id from access.artifact where description = '"+access.getArtifact()+"'), "
							+ "(select id from access.access_type where description = '"+access.getAccessType()+"'), "
									+ "(select id from access.area where description = '"+area+"'))";
			ResultSet rs = null;
			ConnectionDB conn = null;
			conn = new ConnectionDB();
			
			try {
				conn.ExecuteQueryUpdate(sql);
				
			} catch (Exception e) {
				status = "ERROR";
			} finally {
				conn.finalize();
			}
				
		return status;
		
	}



	public static void removeAccess(Access access) {
		
		String sql = "delete from access.artifact_access_type_user where userid = "+access.getUserid()+" "
				+ "and artifactid = (select id from access.artifact where description = '"+access.getArtifact()+"') "
						+ "and acesstypeid = (select id from access.access_type where description = '"+access.getAccessType()+"') "
								+ "and areaid = (select id from access.area where description = '"+access.getAreaList().get(0)+"')";
								
		ResultSet rs = null;
		ConnectionDB conn = null;
		conn = new ConnectionDB();
		
		try {
			conn.ExecuteQueryUpdate(sql);
			
		} catch (Exception e) {
			
		} finally {
			conn.finalize();
		}
	}

	
}
