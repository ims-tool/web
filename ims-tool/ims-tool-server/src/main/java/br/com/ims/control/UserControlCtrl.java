package br.com.ims.control;

import java.util.List;

import org.json.JSONObject;

import br.com.ims.persistence.ServiceHourDao;
import br.com.ims.persistence.UserControlDao;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;
import br.com.ims.tool.entity.User;
import br.com.ims.tool.exception.DaoException;

public class UserControlCtrl {
	
	public static List<ServiceHour> findAll(String type){
		return ServiceHourDao.findAll(type);
	}

	public static ServiceHour find(Integer id) {
		return ServiceHourDao.find(id);
	}

	public static void save(ServiceHour entity) {
		ServiceHourDao.save(entity);
	}
	
	public static List<ServiceHourType> findType() {
		return ServiceHourDao.findType();
	}

	public User getUser(String userLogin) throws DaoException {
		UserControlDao dao = new UserControlDao();
		return dao.getUser(userLogin);
	}

	public boolean isInternalUser() throws DaoException {
		UserControlDao dao = new UserControlDao();
		return dao.isInternalUser();
	}
	
	public static String getSystemAccess(String login, String password, String system) throws DaoException {
		UserControlDao dao = new UserControlDao();
		return dao.getSystemAccess(login, password, system);
	}

	public static JSONObject getArtifactBySystem(String userLogin, String system) {
		UserControlDao dao = new UserControlDao();
		return dao.getArtifactBySystem(userLogin, system);
	}

}
