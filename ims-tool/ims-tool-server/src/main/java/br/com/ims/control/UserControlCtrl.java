package br.com.ims.control;

import java.util.List;

import org.json.JSONObject;

import br.com.ims.persistence.ServiceHourDao;
import br.com.ims.persistence.UserControlDao;
import br.com.ims.tool.entity.AccessByUser;
import br.com.ims.tool.entity.AccessType;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;
import br.com.ims.tool.entity.User;
import br.com.ims.tool.exception.DaoException;

public class UserControlCtrl {
	
	public static List<User> findAll(){
		UserControlDao dao = new UserControlDao();
		return dao.findAll();
	}

	public static ServiceHour find(Integer id) {
		return ServiceHourDao.find(id);
	}

	public static void save(User user) {
		UserControlDao.save(user);
	}
	
	public static List<ServiceHourType> findType() {
		return ServiceHourDao.findType();
	}

	public User getUser(String userLogin) throws DaoException {
		UserControlDao dao = new UserControlDao();
		return dao.getUser(userLogin);
	}

	
	public static String getSystemAccess(String login, String password, String system) throws DaoException {
		UserControlDao dao = new UserControlDao();
		return dao.getSystemAccess(login, password, system);
	}

	public static JSONObject getArtifactBySystem(String userLogin, String system) {
		UserControlDao dao = new UserControlDao();
		return dao.getArtifactBySystem(userLogin, system);
	}

	public static void remove(User user) {
		UserControlDao.remove(user);
	}

	public static List<AccessByUser> findAccessByUser(Integer id) {
		UserControlDao dao = new UserControlDao();
		return dao.getAccessByUser(id);
	}

	public static List<String> findSystem() {
		UserControlDao dao = new UserControlDao();
		return dao.findSystem();
	}

	public static List<String> findArtifactBySystem(String system) {
		UserControlDao dao = new UserControlDao();
		return dao.findArtifactBySystem(system);
	}

	public static List<String> findAccessType() {
		UserControlDao dao = new UserControlDao();
		return dao.findAccessType();
	}

	public static List<String> findArea() {
		UserControlDao dao = new UserControlDao();
		return dao.findArea();
	}

}
