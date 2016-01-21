package br.com.gvt.telefonia.ura.util;

import br.com.gvt.telefonia.ura.diagram.model.Entity;

public class EntityFactory {
	
	private static EntityFactory instance = null;
	
	private EntityFactory(){}
	
	public static EntityFactory getInstance()
	{
		if(EntityFactory.instance == null)
			EntityFactory.instance = new EntityFactory();
		
		return EntityFactory.instance;
	}
	
	public Entity<?> getEntity(String name) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		return (Entity<?>) Class.forName("br.com.gvt.telefonia.ura.diagram.model."+name).newInstance();
	}
	
	
}
