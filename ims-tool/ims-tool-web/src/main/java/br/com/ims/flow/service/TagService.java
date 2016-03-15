package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.TagEntity;

public class TagService extends AbstractEntityService<TagEntity>{
	
	public List<TagEntity> getAll() {
		
		return DAOFactory.getInstance().getTagDAO().getAll();
	}
	
	public TagEntity get(String id) {
		
		return DAOFactory.getInstance().getTagDAO().get(id);
	}
	
	public boolean save(TagEntity tag) {
		return DAOFactory.getInstance().getTagDAO().save(tag);
	}
	
	public boolean update(TagEntity tag) {
		return DAOFactory.getInstance().getTagDAO().update(tag);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<FormEntity> forms = ServicesFactory.getInstance().getIvrEditorService().getBean().getListForm();
		for(FormEntity form :  forms) {
			if(form.getTag()!= null && form.getTag().getId().equals(id)) {
				return true;
			}					
		}
		return false;
	}

	@Override
	public boolean delete(TagEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getTagDAO().delete(object);
		
	}

}
