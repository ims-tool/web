package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AnswerEntity;

@SuppressWarnings("serial")
public class AnswerService extends AbstractEntityService <AnswerEntity>{
	
	public List<AnswerEntity> getAll() {
		
		return DAOFactory.getInstance().getAnswerDAO().getAll();
	}
	
	public AnswerEntity get(String id) {
		
		return DAOFactory.getInstance().getAnswerDAO().get(id);
	}
	
	public boolean save(AnswerEntity answer) {
		return DAOFactory.getInstance().getAnswerDAO().save(answer);
	}
	
	public boolean isUsed(String id) {
		return true;
	}
	public List<String[]> getUsed(String id) {
		
		return null;
	}

	@Override
	public boolean update(AnswerEntity answer) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getAnswerDAO().update(answer);
		
	}

	@Override
	public boolean delete(AnswerEntity answer) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getAnswerDAO().delete(answer);
		
	}

}
