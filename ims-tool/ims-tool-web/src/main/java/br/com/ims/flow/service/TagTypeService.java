package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

public class TagTypeService extends AbstractEntityService<TagTypeEntity>{
	
	public List<TagTypeEntity> getAll() {
		
		return DAOFactory.getInstance().getTagTypeDAO().getAll();
	}
	
	public TagTypeEntity get(String id) {
		
		return DAOFactory.getInstance().getTagTypeDAO().get(id);
	}
	
	public void save(TagEntity tag) {
		DAOFactory.getInstance().getTagDAO().save(tag);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void save(TagTypeEntity object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TagTypeEntity object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TagTypeEntity object) {
		// TODO Auto-generated method stub
		
	}

}
