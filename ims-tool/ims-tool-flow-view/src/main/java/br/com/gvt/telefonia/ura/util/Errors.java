package br.com.gvt.telefonia.ura.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Errors {
	
	private static Errors instance;
	private static List<String> erros;
	
	private Errors() {
		Errors.erros = new ArrayList<String>(); 
	}
	
	public static Errors getInstance(){
		if(instance == null){
			instance = new Errors();
		}
		return instance;
	}
	
	public boolean validate(Object obj,Object parent)
	{
		try {
		Method method = Reflection.getMethod(parent, "getId");
			if(obj == null){
				System.out.println(obj.getClass().getSimpleName() + " não encontrado " + method.invoke(parent).toString());
				Errors.erros.add(obj.getClass().getSimpleName() + " não encontrado " + method.invoke(parent).toString());
				return false;
			} else {
				return true;
			}
		
		} catch(Exception e)
		{
			return false;
		}
	}
	
	public void error(String error)
	{
		Errors.erros.add(error);
	}
	
	public List<String> getErrors()
	{
		return Errors.erros;
	}
}
