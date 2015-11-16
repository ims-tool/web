package br.com.ims.control;

import java.util.List;

import br.com.ims.persistence.ParametersDao;
import br.com.ims.tool.entity.Parameters;

public class ParametersCtrl {
	
	public static List<Parameters> findAll(){
		return ParametersDao.findAll();
	}

	public static Parameters find(Integer id) {
		return ParametersDao.find(id);
	}

	public static void save(Parameters entity) {
		ParametersDao.save(entity);
	}

}
