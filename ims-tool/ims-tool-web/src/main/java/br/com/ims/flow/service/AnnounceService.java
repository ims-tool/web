package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AnnounceEntity;

@SuppressWarnings("serial")
public class AnnounceService extends AbstractEntityService <AnnounceEntity>{
	
	public List<AnnounceEntity> getAll() {
		
		return DAOFactory.getInstance().getAnnounceDAO().getAll();
	}
	
	public AnnounceEntity get(String id) {
		
		return DAOFactory.getInstance().getAnnounceDAO().get(id);
	}
	
	public boolean save(AnnounceEntity announce) {
		return DAOFactory.getInstance().getAnnounceDAO().save(announce);
	}
	
	public boolean isUsed(String id) {
		return true;
	}
	public List<String[]> getUsed(String id) {
		
		return null;
	}

	@Override
	public boolean update(AnnounceEntity announce) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getAnnounceDAO().update(announce);
		
	}

	@Override
	public boolean delete(AnnounceEntity announce) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getAnnounceDAO().delete(announce);
		
	}

}
