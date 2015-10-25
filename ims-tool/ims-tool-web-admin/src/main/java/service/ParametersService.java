package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ims.exception.DaoException;
import dao.ParametersDao;
import model.Parameters;



@Service
public class ParametersService {
	
	@Autowired
	ParametersDao dao;
	
	public ParametersService(){
		dao = new ParametersDao();
	}
	
	public List<Parameters> getAll() {
		try {
			return dao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Parameters getById(Long id) {
		try {
			return dao.getById(id);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean save(Parameters p) {
		try {
			return dao.save(p);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
