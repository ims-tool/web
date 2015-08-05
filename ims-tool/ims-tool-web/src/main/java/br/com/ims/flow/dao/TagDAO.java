package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.TagEntity;

public class TagDAO extends AbstractDAO<TagEntity>{
	private List<TagEntity> listTags =  null;
	private static TagDAO instance = null;
	private TagDAO() {
		listTags = new ArrayList<TagEntity>(); 			
	}
	
	public static TagDAO getInstance() {
		if(instance == null) {
			instance = new TagDAO();
		}
		return instance;
	}
	
	public List<TagEntity> getAll() {
		
		return this.listTags;
		
	}
	public TagEntity get(String id) {
		for(TagEntity tag : this.listTags) {
			if(tag.getId().equals(id)) {
				return tag;
			}
		}
		return null;
	}
	public void save(TagEntity tag) {
		this.listTags.add(tag);
	}

	@Override
	public void update(TagEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TagEntity entity) {
		// TODO Auto-generated method stub
		
	}

}
