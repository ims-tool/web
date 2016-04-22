package br.com.ims.flow.dao;

import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.AbstractEntity;

@SuppressWarnings("serial")
public abstract class AbstractDAO <T extends AbstractEntity> implements Serializable {
	public static Logger log = Logger.getLogger(AbstractDAO.class);
	
	public abstract List<T> getByFilter(String where);
	
	public abstract List<T> getAll();
	
	public abstract T get(String id);
	
	public abstract boolean save(T entity);
	
	public abstract boolean update(T entity);
	
	public abstract boolean delete(T entity);
	
	protected void audit(T entity, Integer typeId)  {
		log.debug("audit("+entity+","+typeId+")");
		StringBuffer result = new StringBuffer();
		try {
			Method[] methods = entity.getClass().getMethods();
			String tableName = entity.getClass().getName().replace(Constants.CLASS_ENTITY_PATH, "").replace("Entity", "");
			for(Method method : methods) {
				if(method.getName().startsWith("get") && !method.getName().equalsIgnoreCase("getClass")) {
					String field = method.getName().substring(3);
					
					result.append(field+"="+method.invoke(entity)+",");
				}
			}
			JSONObject json = new JSONObject();
			json.put("userLogin", Util.getUserName());
			json.put("typeid",typeId);
			json.put("description","Module:"+tableName);
			json.put("artifact",Constants.AUDIT_ARTIFACT);
			json.put("originalvalue",result.toString());
			
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

}
