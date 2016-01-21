package br.com.gvt.telefonia.ura.resource;

import java.util.HashMap;
import java.util.Map.Entry;

import br.com.gvt.telefonia.ura.diagram.dao.AudioDAO;
import br.com.gvt.telefonia.ura.util.Reflection;
import br.com.gvt.telefonia.ura.util.Util;

public class FormResource {
	
	public String fieldTemplate(String name,String type,String value)
	{
		String result = "<div class=\"form-group\">" + "<label for=\"name\" class=\"control-label\">"+name+"</label>";
		result += "<input type=\""+type+"\" class=\"form-control\" name=\""+name+"\" value=\"" + value + "\" />"+ "</div>";
		return result;
	}
	
	public String fieldTemplate(String name,String type,HashMap<Long,String> values)
	{
		String result = "";
		
		result = "<div class=\"form-group\">" + "<label for=\"name\" class=\"control-label\">"+name+"</label>";
			result += "<select name=\""+name+"\" class=\"select2 form-control\">";
				for (Entry<Long, String> keyvalue : values.entrySet()) 
				{
					result += "<option value=\""+keyvalue.getKey()+"\">"+keyvalue.getValue()+"</option>";
				}
			result += "</select>";
		result += "</div>";
		return result;
	}
	
	public String fieldTemplate(String name,String type)
	{
		String result = "";
		result = "<div class=\"form-group\">" + "<label for=\"name\" class=\"control-label\">"+name+"</label>";
		result += "<input type=\""+type+"\" class=\"form-control\" name=\""+name+"\" />"+ "</div>";
		return result;
	}
	
	public String Announce()
	{
		String result = "";
		try{
			AudioDAO audioDAO = new AudioDAO();
			HashMap<Long,String> audios = Util.listToKeyValue(audioDAO.findAll(),"id","name");
			result = fieldTemplate("name", "text");
			result += fieldTemplate("nextform", "text");
			result += fieldTemplate("audio", "select",audios);
		} catch(Exception e){
			e.getStackTrace();
		}
		return result;
	}
	
	public static String load(String type)
	{
		return Reflection.invokeMethod(new FormResource(),type).toString();
	}
	
}
