package br.com.ims.control;

import java.util.List;

import br.com.ims.persistence.ServiceHourDao;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;

public class ServiceHourCtrl {
	
	public static List<ServiceHour> findAll(String type){
		return ServiceHourDao.findAll(type);
	}

	public static ServiceHour find(Integer id) {
		return ServiceHourDao.find(id);
	}

	public static void save(ServiceHour entity) {
		ServiceHourDao.save(entity);
	}
	
	public static List<ServiceHourType> findType(String user) {
		return ServiceHourDao.findType(user);
	}

}
