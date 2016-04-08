package br.com.ims.flow.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.ims.flow.factory.DAOFactory;

@SuppressWarnings("serial")
public class Util implements Serializable {
    
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
		String result = System.getProperty("user.name");   
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
	//public static String get
    
	
     
}
