package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.NoMatchInputEntity;

public class NoMatchInputDAO extends AbstractDAO<NoMatchInputEntity> {
	private List<NoMatchInputEntity> listNoMatchInputs =  null;
	private static NoMatchInputDAO instance = null;
	private NoMatchInputDAO() {
		listNoMatchInputs = new ArrayList<NoMatchInputEntity>(); 			
	}
	
	public static NoMatchInputDAO getInstance() {
		if(instance == null) {
			instance = new NoMatchInputDAO();
		}
		return instance;
	}
	public List<NoMatchInputEntity> getByFilter(String where) {

		return this.listNoMatchInputs;		
	}
	public List<NoMatchInputEntity> getAll() {
		
		return this.listNoMatchInputs;		
	}
	public NoMatchInputEntity get(String id) {
		for(NoMatchInputEntity noMatchInput : this.listNoMatchInputs) {
			if(noMatchInput.getId().equals(id)) {
				return noMatchInput;
			}
		}
		return null;
	}
	public boolean save(NoMatchInputEntity entity) {
		this.listNoMatchInputs.add(entity);
		return true;
	}

	@Override
	public boolean update(NoMatchInputEntity entity) {
		// TODO Auto-generated method stub
		return true;
		
	}

	@Override
	public boolean delete(NoMatchInputEntity entity) {
		// TODO Auto-generated method stub
		return true;
		
	}

	

}
