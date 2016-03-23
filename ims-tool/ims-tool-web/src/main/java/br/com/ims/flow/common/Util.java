package br.com.ims.flow.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

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
	public static String dateFormat(java.util.Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(date);
		
	}
	//public static String get
    
	
     
}
