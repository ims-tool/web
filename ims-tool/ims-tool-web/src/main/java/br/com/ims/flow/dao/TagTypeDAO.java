package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.TagTypeEntity;

public class TagTypeDAO extends AbstractDAO<TagTypeEntity> {
	private List<TagTypeEntity> listTagTypes =  null;
	private static TagTypeDAO instance = null;
	private TagTypeDAO() {
		listTagTypes = new ArrayList<TagTypeEntity>(); 			
	}
	
	public static TagTypeDAO getInstance() {
		if(instance == null) {
			instance = new TagTypeDAO();
			TagTypeEntity tagType = new TagTypeEntity();
			tagType.setId("-1");
			tagType.setName("Retry");
			tagType.setDescription("Tag de Retry do IVR_OPERATOR");
			
			instance.listTagTypes.add(tagType);
			
			tagType = new TagTypeEntity();
			tagType.setId("1");
			tagType.setName("Retenção");
			tagType.setDescription("Tag de Retenção");
			
			instance.listTagTypes.add(tagType);
			
			tagType = new TagTypeEntity();
			tagType.setId("2");
			tagType.setName("Transferência BC");
			tagType.setDescription("Tag para identificar Transferência para BC");
			
			instance.listTagTypes.add(tagType);
			
			tagType = new TagTypeEntity();
			tagType.setId("3");
			tagType.setName("No Input");
			tagType.setDescription("Tag para eventos de No Input");
			
			instance.listTagTypes.add(tagType);
			
			tagType = new TagTypeEntity();
			tagType.setId("4");
			tagType.setName("No Match");
			tagType.setDescription("Tag para eventos de No Match");
			
			instance.listTagTypes.add(tagType);
			
			tagType = new TagTypeEntity();
			tagType.setId("5");
			tagType.setName("Navegação");
			tagType.setDescription("Tag para eventos de navegação");
			
			instance.listTagTypes.add(tagType);
			
			tagType = new TagTypeEntity();
			tagType.setId("6");
			tagType.setName("RetornoBC");
			tagType.setDescription("Tag de retorno ao BC");
			
			instance.listTagTypes.add(tagType);
			
			tagType = new TagTypeEntity();
			tagType.setId("7");
			tagType.setName("Roteamento URA BC");
			tagType.setDescription("Tag para identificar roteamento para URA BC");
			
			instance.listTagTypes.add(tagType);
			
			tagType = new TagTypeEntity();
			tagType.setId("300");
			tagType.setName("ReincCheckList");
			tagType.setDescription("Tag de Reincidencia de CheckList");
			
			instance.listTagTypes.add(tagType);
			
		}
		return instance;
	}
	
	public List<TagTypeEntity> getAll() {
		
		return this.listTagTypes;
		
	}
	public TagTypeEntity get(String id) {
		for(TagTypeEntity tagType : this.listTagTypes) {
			if(tagType.getId().equals(id)) {
				return tagType;
			}
		}
		return null;
	}
	public void save(TagTypeEntity tag) {
		this.listTagTypes.add(tag);
	}

	@Override
	public void update(TagTypeEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TagTypeEntity entity) {
		// TODO Auto-generated method stub
		
	}

}
