package br.com.ims.flow.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

@SuppressWarnings("serial")
public class Util implements Serializable {
    
    public static Integer uid = 0;
	public static String getUID() {
    	String retorno = "";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
    	
    	Random random = new Random();		
		Integer value = random.nextInt(9);
		
		retorno =  sdf.format(Calendar.getInstance().getTime())+String.valueOf(value);
		
		return retorno;
		//uid++;
		
		//return String.valueOf(uid);
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
	public static String dateFormat(java.util.Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(date);
		
	}
	//public static String get
    
	
     
}
