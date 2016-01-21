package br.com.gvt.telefonia.ura.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reflection {

	public static Field[] getAttributes(Object obj)
	{
		Class<?> c = obj.getClass();
		 return c.getDeclaredFields();
	}
	
	public static Method getMethod(Object obj,String name) {
		
		Method method = null;
		
		try{
			Method[] methods = obj.getClass().getDeclaredMethods();
			
			for(Method me : methods)
				if(me.getName().equalsIgnoreCase(name))
					method = me;
			
 		} catch(Exception e){
 			e.getStackTrace();
 		} 
 		
 		return method;
	}
	
	public static Object invokeMethod(Object obj,String name) {
		
		Object result = "";
		Method method = null;
		
		try{
			method = Reflection.getMethod(obj,name);
			result = method.invoke(obj);
			
 		} catch(Exception e){
 			e.getStackTrace();
 		} 
 		
 		return result;
	}
	
	public static String invokeGetMethod(Object obj,String name) {
		Object result = Reflection.invokeMethod(obj, "get"+name);
		
		if(result == null)
			result = "";
		
 		return result.toString();
	}
	
	public static Object invokeGetMethodObject(Object obj,String name) {
		
		return Reflection.invokeMethod(obj, "get"+name);
	}
	
	public static void invokeSetMethod(Object obj,String name, Object value) {
		
		Method method = null;
		
		try{
			String type = obj.getClass().getDeclaredField(name).getType().getSimpleName().toLowerCase();
			method = Reflection.getMethod(obj,"set"+name);
			
			if(type.endsWith("long"))
				value = Long.parseLong(value.toString());
			else
				value = value.toString();
			method.setAccessible(true);
			method.invoke(obj,value);
		} catch(Exception e2){
			e2.getStackTrace();
		} 
	}
	
	public static void setAll(Object obj,HashMap<String,String> parameter)
	{
		Field[] fields = Reflection.getAttributes(obj);
		String name = "";
		String value = "";
		
		for(Field field : fields)
		{
			name  = field.getName().toString();
			value = parameter.get(name);
			Reflection.invokeSetMethod( obj , name , value );
		}
	}
	
	public static List<String> getAttributesList(Object obj)
	{
		List<String> result = new ArrayList<String>();
		Field[] fields = Reflection.getAttributes(obj);
		String name = "";
		
		for(Field field : fields)
		{
			name  = field.getName().toString();
			result.add(name);
		}
		
		return result;
	}
	
	public static HashMap<String,Object> getAll(Object obj)
	{
		HashMap<String,Object> result = new HashMap<String,Object>();
		Field[] fields = Reflection.getAttributes(obj);
		String name = "";
		Object val = null;
		
		for(Field field : fields)
		{
			name  = field.getName().toString();
			val = Reflection.invokeGetMethod( obj , name);
			result.put(name, val);
		}
		
		return result;
	}
	
}
