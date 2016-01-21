package br.com.gvt.telefonia.ura.util;

import br.com.gvt.telefonia.ura.diagram.dao.DAO;

public class DaoFactory {
	private static DaoFactory instance;
	
	private DaoFactory() {}
	
	public static DaoFactory getInstance(){
		if(instance == null){
			instance = new DaoFactory();
		}
		return instance;
	}
	
	public DAO<?> getDao(String name)
	{
		try {
			return (DAO<?>) Reflection.invokeGetMethodObject(SingletonDAO.getInstance(),name+"DAOInstance");
		} catch(Exception e){
			return null;
		}
	}
}
