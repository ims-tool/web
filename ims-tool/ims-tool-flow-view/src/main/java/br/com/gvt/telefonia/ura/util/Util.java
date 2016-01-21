package br.com.gvt.telefonia.ura.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.com.gvt.telefonia.ura.diagram.model.Announce;
import br.com.gvt.telefonia.ura.diagram.model.Condition;
import br.com.gvt.telefonia.ura.diagram.model.ConditionGroup;
import br.com.gvt.telefonia.ura.diagram.model.ConditionMap;
import br.com.gvt.telefonia.ura.diagram.model.ConditionValue;
import br.com.gvt.telefonia.ura.diagram.model.Decision;
import br.com.gvt.telefonia.ura.diagram.model.DecisionChance;
import br.com.gvt.telefonia.ura.diagram.model.DecisionGroup;
import br.com.gvt.telefonia.ura.diagram.model.Disconnect;
import br.com.gvt.telefonia.ura.diagram.model.Entity;
import br.com.gvt.telefonia.ura.diagram.model.Flow;
import br.com.gvt.telefonia.ura.diagram.model.Menu;
import br.com.gvt.telefonia.ura.diagram.model.Operation;
import br.com.gvt.telefonia.ura.diagram.model.PromptCollect;
import br.com.gvt.telefonia.ura.diagram.model.Transfer;

public class Util {

	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	public static String capitalize(String input) {
		   if (input == null || input.length() <= 0) {
		     return input;
		   }
		   char[] chars = new char[1];
		   input.getChars(0, 1, chars, 0);
		   if (Character.isUpperCase(chars[0])) {
		     return input;
		   } else {
		     StringBuilder buffer = new StringBuilder(input.length());
		     buffer.append(Character.toUpperCase(chars[0]));
		     buffer.append(input.toCharArray(), 1, input.length()-1);
		     return buffer.toString();
		   }
	}
	
	private static String filterNull(Object object)
	{
		return (object != null) ? object.toString() : ""; 
	}
	
	public static String htmlForm(Object obj)
	{
		String result = "";
		
		 Field[] fields = Reflection.getAttributes(obj);
		
		 String[] json = new String[fields.length];
		 
		 String name = "";
		 String value = "";
		 String inputType = "";
		 String attributeType = "";
		 
		 Method method = null;
		 try {
			 for(int x = 0; x < fields.length; x++){
		 		Field field = fields[x];
		 		name = field.getName().toString();
		 		attributeType = field.getType().toString();
		 		
		 		try{
			 		method = obj.getClass().getMethod("get"+Util.capitalize(name));
			 		value = Util.filterNull(method.invoke(obj));
		 		} catch(Exception e){
		 			method = obj.getClass().getMethod("get"+name);
			 		value = Util.filterNull(method.invoke(obj));
		 		}
		 		
		 		json[x] = "<div class=\"form-group\">" +
				"<label for=\"recipient-name\" class=\"control-label\">"+name+"</label>";
				
				inputType = (Util.isNumeric(value)) ? "number" : "text"; 
				json[x] += "<input type=\"text\" class=\"form-control\" name=\""+name+"\" value=\"" + value + "\" />"+
		 		"</div>";    
			 }
         	result = new String(
  				 new String(StringUtils.join(json,"")).getBytes("UTF-8"),
  				 "UTF-8"
           );
		 } catch (Exception e) {
			 e.getStackTrace();
		 }
		
		return result;
	}
	
	public static String appendDataToJson(String json,HashMap<String,String> params)
	{
		Iterator it = params.entrySet().iterator();
		json = json.substring(0,json.length()-1);
		
		 while (it.hasNext()) {
			 Map.Entry pair = (Map.Entry)it.next();
			 if(pair.getKey().toString().equalsIgnoreCase("defaultnextform"))
				 json += ",\"defaultnextform\": \"<a class='defaultnextform'>" + pair.getValue() + "</a>\"";
			 else
				 json += ",\""+pair.getKey()+"\": \"" + pair.getValue() + "\"";
		 }
		 json += "}";
		 
		return json;
		
	}
	
	public static String appendDataToJson(String json,String params)
	{
		json = json.substring(0,json.length()-1);
		params = params.substring(1,params.length());
		return json+","+params;
	}
	
	public static String toJson(List<HashMap<String,String>> list)
	{
		Iterator it = null;
		String result = "";
		String aux = "";
		StringBuffer resultBuffer = new StringBuffer();
		
		for(HashMap<String,String> node: list)
		{
			aux = "";
			it = node.entrySet().iterator();
			
			while (it.hasNext()) {
				 Map.Entry pair = (Map.Entry)it.next();
				 aux += "\""+pair.getKey()+"\": \"" + pair.getValue().toString().replaceAll("\"", "'") + "\",";
			}
			
			aux = aux.substring(0,aux.length()-1);
			resultBuffer.append("{"+aux+"},");
		}
		
		aux = resultBuffer.toString();
		
		result = "["+aux.substring(0,aux.length()-1)+"]";
		result = result.replace("\n", "").replace("\r", "");
		
		return result;
	}
	
	public static String toJsoWithoutTags(Object obj)
	{
		String result = "";
		
		 Class<?> c = obj.getClass();
		 Field field = null;
		 Method method = null;
		 Field[] fields = c.getDeclaredFields();
		 Map<String, Object> temp = new HashMap<String, Object>();
		
		 List<String> json = new ArrayList<String>();
		 String name = "";
		 try {
			 String methodId =  Reflection.invokeGetMethod(obj, "id");
			 
			 for(int x = 0; x < fields.length; x++){
				 
		 		field = fields[x];
		 		name = field.getName().toString();
		 		try {
		 			method = obj.getClass().getMethod("get"+Util.capitalize(name));
		 			
		 			String className = "";
		 			if(obj instanceof DiagramElementInfo)
		 				className = Reflection.invokeGetMethod(obj, "type");
		 			else
		 				className = c.getSimpleName();
		 		
				 	json.add("\""+name+"\": \"" + method.invoke(obj) + "\"");
		 		 } catch (Exception e1) {
					 e1.getStackTrace();
				 }
			 }
			 result = "{"+StringUtils.join(json,",")+"}";
        	result = new String(
 				 new String("{"+StringUtils.join(json,",")+"}").getBytes("UTF-8"),
 				 "UTF-8"
          );
		 } catch (Exception e1) {
			 e1.getStackTrace();
		 }         
		
		result = result.replace("\n", "").replace("\r", "");
		return result;
	}
	
	public static String toJson(Object obj)
	{
		String result = "";
		
		 Class<?> c = obj.getClass();
		 Field field = null;
		 Method method = null;
		 Field[] fields = c.getDeclaredFields();
		 Map<String, Object> temp = new HashMap<String, Object>();
		
		 List<String> json = new ArrayList<String>();
		 String name = "";
		 try {
			 String methodId =  Reflection.invokeGetMethod(obj, "id");
			 
			 for(int x = 0; x < fields.length; x++){
				 
		 		field = fields[x];
		 		name = field.getName().toString();
		 		try {
		 			method = obj.getClass().getMethod("get"+Util.capitalize(name));
		 			
		 			String className = "";
		 			if(obj instanceof DiagramElementInfo)
		 				className = Reflection.invokeGetMethod(obj, "type");
		 			else
		 				className = c.getSimpleName();
		 		
			 		if(( Util.capitalize(name).equalsIgnoreCase("nextform") || Util.capitalize(name).equalsIgnoreCase("nextformid") ) ){
			 			if(!className.equalsIgnoreCase(DecisionGroup.class.getSimpleName()) && !className.equalsIgnoreCase(Decision.class.getSimpleName()) && !className.equalsIgnoreCase(Menu.class.getSimpleName()))
			 				json.add("\""+name+"\": \"<a class='defaultnextform'>" + method.invoke(obj) + "</a> <span class='glyphicon-pencil editar-nextform' elementType='"+className+"' elementId='"+methodId+"' elementNext='"+method.invoke(obj)+"'> Editar</span>\"");
			 				//json.add("\""+name+"\": \"<a class='defaultnextform'>" + method.invoke(obj) + "</a>\"");
			 		} 	else
				 			json.add("\""+name+"\": \"" + method.invoke(obj) + "\"");
		 		 } catch (Exception e1) {
					 e1.getStackTrace();
				 }
			 }
			 result = "{"+StringUtils.join(json,",")+"}";
         	result = new String(
  				 new String("{"+StringUtils.join(json,",")+"}").getBytes("UTF-8"),
  				 "UTF-8"
           );
		 } catch (Exception e1) {
			 e1.getStackTrace();
		 }         
		
		result = result.replace("\n", "").replace("\r", "");
		return result;
	}
	
	public static String getFormType(long type)
	{
		String result = "";
		switch(Integer.parseInt(Long.toString(type)))
		{
			case 1:
				result = Announce.class.getSimpleName();
			break;
			case 2:
				result = PromptCollect.class.getSimpleName();
			break;
			case 3:
				result = Menu.class.getSimpleName();
			break;
			case 4:
				result = Flow.class.getSimpleName();
			break;
			case 5:
				result = Decision.class.getSimpleName();
			break;
			case 6:
				result = Operation.class.getSimpleName();
			break;
			case 7:
				result = Transfer.class.getSimpleName();
			break;
			case 8:
				result = Disconnect.class.getSimpleName();
			break;
			case 9:
				result = Flow.class.getSimpleName();
			break;
			case 10:
				//result = Thread.class.getSimpleName();
			break;
		}

		return result;
	}
	
	
	public static int getFormType(String type)
	{
		int result = 0;

		if(type.equalsIgnoreCase(Announce.class.getSimpleName()))
			result = 1;
		else if(type.equalsIgnoreCase(PromptCollect.class.getSimpleName()))
			result = 2;
		else if(type.equalsIgnoreCase(Menu.class.getSimpleName()))
			result = 3;
		else if(type.equalsIgnoreCase(Flow.class.getSimpleName()))
			result = 4;
		else if(type.equalsIgnoreCase(Decision.class.getSimpleName()))
			result = 5;
		else if(type.equalsIgnoreCase(Operation.class.getSimpleName()))
			result = 6;
		else if(type.equalsIgnoreCase(Transfer.class.getSimpleName()))
			result = 7;
		else if(type.equalsIgnoreCase(Disconnect.class.getSimpleName()))
			result = 8;
		else if(type.equalsIgnoreCase(Flow.class.getSimpleName()))
			result = 9;

		return result;
	}
	
	public static String fileToString(String filename) throws Exception
	{
		File file = new File(filename);
		file.exists();
		FileReader fileReader = new FileReader(file); 
	    BufferedReader reader = new BufferedReader(fileReader);
	    StringBuilder builder = new StringBuilder();
	    String line;    

	    // For every line in the file, append it to the string builder
	    while((line = reader.readLine()) != null)
	    {
	        builder.append(line);
	    }

	    reader.close();
	    return builder.toString();
	}
	
	public static <T extends Entity<?>> HashMap<Long,String> listToKeyValue(List<T> list,String key,String value)
	{
		Long setKey;
		String setValue;
		
		HashMap<Long, String> result = new HashMap<Long,String>();
		
		try{
		for(Entity<?> obj : list)
		{
			setKey = Long.parseLong(Reflection.invokeGetMethod(obj,key));
			setValue = Reflection.invokeGetMethod(obj,value);
			result.put(setKey, setValue);
		}
		} catch(Exception e){
			e.getStackTrace();
		}
		
		return result;
	}
	
	public static String getValues(Entity<?> opc)
	{
		String value = "";
		String result = "";
		StringBuffer resultBuffer = new StringBuffer();
		
		for(int x = 1; x <= 10; x++)
		{
			value = Reflection.invokeGetMethod(opc, "value"+x);
			if(!value.isEmpty())
				resultBuffer.append(value + ",");
		}
		
		result = resultBuffer.toString();
		return (!result.isEmpty()) ? result.substring(0,result.length()-1) : result;
	}
	
	public static String tagDecisionChance(DecisionChance opc)
	{
		String valChance = (opc.getValue1() == null) ? "No" : Util.getValues(opc);
		return opc.getTag() + " "+opc.getOperation() +" " + valChance;
	}
	
	public static String conditionClause(String id)
	{
		String result = "";
		ConditionMap conditionMap = null;
		List<ConditionValue> listConditionValue = null;
		Condition condition = (Condition) SingletonDAO.getConditionDAOInstance().findByPk(id);
		List<ConditionGroup> listConditionGroup = SingletonDAO.getConditionGroupDAOInstance().findAllBy("conditionid = '"+id+"'");
		
		for(ConditionGroup obj : listConditionGroup)
		{
			//result +=  condition.getName();
			conditionMap = SingletonDAO.getConditionMapDAOInstance().findByPk(Long.toString(obj.getConditionmapid()));
			listConditionValue = SingletonDAO.getConditionValueDAOInstance().findAllBy("conditiongroupid = '"+obj.getId()+"' ");
			for(ConditionValue condValue : listConditionValue)
				result += obj.getDescription() + " " + condValue.getOperation() + " " + condValue.getValue1() + " - TAGTRUE:" +condValue.getTagtrue()+ " TAGFALSE:" + condValue.getTagfalse() + "<br />";
		}	
		return result;
	}

	public static String parseOperation(String operation) {
		
		String result = "";
		
		if(operation.equalsIgnoreCase("Igual")){
			result = "=";
		} else if(operation.equalsIgnoreCase("MaiorIgual")){
			result = ">=";
		} else if(operation.equalsIgnoreCase("MenorIgual")){
			result = "<=";
		} else if(operation.equalsIgnoreCase("Maior")){
			result = ">";
		} else if(operation.equalsIgnoreCase("Menor")){
			result = "<";
		} else if(operation.equalsIgnoreCase("IN")){
			result = "IN";
		} else if(operation.equalsIgnoreCase("Diferente")){
			result = "<>";
		}
		
		return result;
	}
	
	public static DiagramElementInfo elementInfo(Entity <?> entity)
	{
		DiagramElementInfo element = new DiagramElementInfo();
		element.setType(entity.getClass().getSimpleName());
		
		String name = "";
		String nextform = "";
		
			try{
				element.setId(Reflection.invokeGetMethod(entity, "id"));
				name = Reflection.invokeGetMethod(entity, "name");
				nextform = Reflection.invokeGetMethod(entity, "nextform");
				if(name.isEmpty())
					name = Reflection.invokeGetMethod(entity, "description");
				if(nextform.isEmpty())
					nextform = Reflection.invokeGetMethod(entity, "nextformid");	
				element.setNome(name);
				element.setNextform(nextform);
			} catch(Exception e) {
				e.getStackTrace();
			}
		
		return element;
	}
}
