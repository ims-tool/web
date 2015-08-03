package br.com.ims.flow.common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Util implements Serializable {
    
    public static Integer uid = 0;
	public static String getUID() {
    	/*String retorno = "";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
    	
    	Random random = new Random();		
		Integer value = random.nextInt(9);
		
		retorno =  sdf.format(Calendar.getInstance().getTime())+String.valueOf(value);*/
		uid++;
		
		return String.valueOf(uid);
    }
    
	
     
}
