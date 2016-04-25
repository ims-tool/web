package br.com.ims.flow.common;

import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.ims.flow.bean.LoginBean;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AbstractEntity;

@SuppressWarnings("serial")
public class Util implements Serializable {
	public static Logger log = Logger.getLogger(Util.class);
	
	
    public static Integer uid = 0;
	public static String getUID() {
    	return DAOFactory.getInstance().getSequenceDAO().getNextVal(Constants.SEQUENCE_UID);		
    }
	public static String getTAGID() {
    	return DAOFactory.getInstance().getSequenceDAO().getNextVal(Constants.SEQUENCE_TAG);		
    }
	public static String getVERSIONID() {
    	return DAOFactory.getInstance().getSequenceDAO().getNextVal(Constants.SEQUENCE_VERSION);		
    }
	public static java.util.Date dateFormat(String strDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if(strDate != null && strDate.length() > 0) {
			try {
				return dateFormat.parse(strDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Calendar.getInstance().getTime();
	}
	public static String getUserName() {
		String result = "";
		LoginBean bean = ServicesFactory.getInstance().getLoginBeanService().getBean();
		if(bean == null) {
			result = System.getProperty("user.name");
		} else {
			result = bean.getUsername();
		}
		/*Class<?> c = null;
	    Object   o = null;
	    Method  method = null;  
	    try{
		    if(System.getProperty("os.name").toLowerCase().contains("windows")){
	
		    	c = Class.forName("com.sun.security.auth.module.NTSystem");
		    	o = Class.forName("com.sun.security.auth.module.NTSystem").newInstance();
	
		    	method = c.getDeclaredMethod ("getName");
	
		    }
	
		    if(System.getProperty("os.name").toLowerCase().contains("linux")){
	
		    	c = Class.forName("com.sun.security.auth.module.UnixSystem");
	
		    	o = Class.forName("com.sun.security.auth.module.UnixSystem").newInstance();
	
		    	method = c.getDeclaredMethod ("getUsername");
	
		    }
	
		    if(System.getProperty("os.name").toLowerCase().contains("solaris")){
	
		    	c = Class.forName("com.sun.security.auth.module.SolarisSystem");
	
		    	o = Class.forName("com.sun.security.auth.module.SolarisSystem").newInstance();
	
		    	method = c.getDeclaredMethod ("getUsername");
	
		    }
	
		    if(c != null){
		    	result = (String) method.invoke(o);
		    }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }*/
	    return result;
	}
	public static String dateFormat(java.util.Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(date);
		
	}
	public static String describeObject(Object object) {
		log.debug("describeObject()");
		StringBuffer result = new StringBuffer();
		try {
			Method[] methods = object.getClass().getMethods();
			String tableName = object.getClass().getName().replace(Constants.CLASS_ENTITY_PATH, "").replace("Entity", "");
			
			result.append("Table: "+tableName+" {");
			
			for(Method method : methods) {
				
				if(method.getName().startsWith("get") && !method.getName().equalsIgnoreCase("getClass")) {
					String field = method.getName().substring(3);
					String value = "";
					if(method.getReturnType().getName().indexOf("List") > 0) {
						List<?> list =  (List<?>) method.invoke(object);
						value = "[";
						if(list != null) {
							for(Object obj: list) {
								value+="{"+describeObject(obj)+"}";								
							}
						}
						value += "]";
					} else if (!(method.getReturnType().getName().indexOf("String") > 0 || 
							   method.getReturnType().getName().indexOf("Integer") > 0 || 
							   method.getReturnType().getName().indexOf("int") > -1)) {
						Object obj = method.invoke(object);
						String objectName = method.getReturnType().getName().replace(Constants.CLASS_ENTITY_PATH, "").replace("Entity", "");
						if(obj == null) {
							value = objectName+"(NULL)";
						} else { 
							value = objectName+"("+((AbstractEntity)method.invoke(object)).getId()+")";
						}
					} else {
						if(method.invoke(object) != null)
							value = method.invoke(object).toString();
						else {
							value="NULL";
						}
					}
						
					result.append(field+"="+value+"|");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return result.toString();
		
	}
	
	public static void audit(Object entity, Integer typeId)  {
		log.debug("audit("+entity+","+typeId+")");
		try {
			JSONObject json = new JSONObject();
			json.put("userLogin", Util.getUserName());
			json.put("typeid",typeId);
			json.put("description","Audit IVR EDITOR");
			json.put("artifact",Constants.AUDIT_ARTIFACT);
			String originalvalue = Util.describeObject(entity);
			json.put("originalvalue",originalvalue);
			
			FacesContext ctxt = FacesContext.getCurrentInstance();
		    ExternalContext ext = ctxt.getExternalContext();
			
			String aux = "http://"+ext.getRequestServerName()+":"+ext.getRequestServerPort()+"/ims-tool-server/rest/logaudit/set";
			URL url = new URL(aux);
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);
			
			byte[] out = json.toString().getBytes();
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			try(OutputStream os = http.getOutputStream()) {
			    os.write(out);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		
	}
	//public static String get
    
	
     
}
