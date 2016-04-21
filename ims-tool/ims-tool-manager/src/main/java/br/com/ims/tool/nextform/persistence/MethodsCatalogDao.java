package br.com.ims.tool.nextform.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.ims.tool.nextform.exception.DaoException;
import br.com.ims.tool.nextform.util.ConnectionDB;

public class MethodsCatalogDao {

	private static Logger logger = Logger.getLogger(MethodsCatalogDao.class);

	public boolean inServiceHour(String serviceHour) throws DaoException {

		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement stm = null;
		String startHour = "";
		String stopHour = "";
		Boolean inServiceHour = Boolean.FALSE;

		try {
			conn = new ConnectionDB().getConnection();
			stm = conn.prepareStatement(
					"select starthour, stophour from flow.service_hour t where t.weekday = extract(dow from  current_date)  and t.type = ?");
			stm.setString(1, serviceHour);

			rs = stm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					
					startHour = rs.getString(1);
					
					stopHour = rs.getString(2);
					
					Date date = new Date();   // given date
					Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
					calendar.setTime(date);   // assigns calendar to given date 
					Integer hourNow = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
					Integer minuteNow = calendar.get(Calendar.MINUTE);       // gets month number, NOTE this is zero based!
					
					String nowHour = hourNow.toString()+":"+minuteNow;
					
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					
					
					try {
						Date dateNow = sdf.parse(nowHour);
						
						if (dateNow.before(sdf.parse(startHour))) {
							inServiceHour = Boolean.FALSE;
						} else if (dateNow.after(sdf.parse(stopHour))){
							inServiceHour = Boolean.FALSE;
						}else{
							inServiceHour = Boolean.TRUE;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erro ao recuperar horario de atendimento ", e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
			}
			try {
				stm.close();
			} catch (SQLException e) {
			}
		}

		return inServiceHour;
	}

	public boolean inHoliday(String ivr) throws DaoException {
		
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement stm = null;
		String listHoliday = "";
		Boolean inHoliday = Boolean.FALSE;

		try {
			conn = new ConnectionDB().getConnection();
			stm = conn.prepareStatement(
					"select value from flow.parameters where name like 'feriado%' and owner = ?");
			stm.setString(1, ivr);

			rs = stm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					listHoliday = rs.getString(1);
					Date date = new Date();
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(date); 
					Integer day = calendar.get(Calendar.DAY_OF_MONTH);
					Integer month = calendar.get(Calendar.MONTH) + 1;
					String dayS, monthS;
					if(day < 10){
						dayS = "0"+day.toString();
					}else{
						dayS = day.toString();
					}
					
					if(month < 10){
						monthS = "0"+month.toString();
					}else{
						monthS = month.toString();
					}
					
					String nowHour = dayS+monthS;
					String[] list = listHoliday.split("#");
					
					for (String data : list) {
						if(data.equalsIgnoreCase(nowHour)){
							inHoliday = Boolean.TRUE;
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erro ao recuperar horario de atendimento ", e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
			}
			try {
				stm.close();
			} catch (SQLException e) {
			}
		}

		return inHoliday;
		
	}

	public Map<Integer, String> getMessage(String contextValue, String ddd) throws DaoException {
		
			
			ResultSet rs = null;
			Connection conn = null;
			PreparedStatement stm = null;
			Map<Integer, String> mapAudio = new HashMap<Integer, String>();
			Integer i = 0;
				
				try { 
					conn = new ConnectionDB().getConnection();
					
					stm = conn.prepareStatement("select name, flag, datai, dataf, ddd_in, ddd_not_in from flow.mensagem where spot = (select id from flow.spot t where name like ? ) and flag = 'A' order by msg_order");
					stm.setString(1, "%" + contextValue + "%");
					
					rs = stm.executeQuery();
					
					while(rs.next()){
						
						if(getDate(rs.getString("datai"), rs.getString("dataf"))){
							if(getDDD(rs.getString("ddd_in"), rs.getString("ddd_not_in"), ddd)){
								i++;
								mapAudio.put(i, rs.getString("nome").replace(".wav", ""));
							}
						}
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DaoException("Erro ao recuperar audios mensagens ",e);
				} finally {
					try {
						conn.close();
					} catch (SQLException e1) {
					}
					try {
						stm.close();
					} catch (SQLException e) {
					}
				}
			return mapAudio;
		
	}
	
private boolean getDDD(String ddd_in, String ddd_not_in, String ddd) {
		
		Boolean isDDDIn = Boolean.TRUE;
		Boolean isDDDNotIn = Boolean.TRUE;
		Boolean result = Boolean.FALSE;
		
		if(StringUtils.isBlank(ddd_in)){
			isDDDIn = Boolean.FALSE;
		}
		
		if(StringUtils.isBlank(ddd_not_in)){
			isDDDNotIn = Boolean.FALSE;
		}
		
		if(isDDDIn){
			if(ddd_in.contains(ddd)){
				result = Boolean.TRUE;
			}
		}
		
		if(isDDDNotIn){
			if(!ddd_not_in.contains(ddd)){
				result = Boolean.TRUE;
			}else{
				result = Boolean.FALSE;
			}
		}
		
		if(!isDDDIn && !isDDDNotIn){
			result = Boolean.TRUE;
		}
		
		return result;
		
	}

	private boolean getDate(String dataI, String dataF) throws DaoException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isBlank(dataI)||StringUtils.isBlank(dataF)){
			return Boolean.TRUE;
		}
		Calendar cDataI = Calendar.getInstance();
		Calendar cDataF = Calendar.getInstance();
		
		try {
			cDataI.setTime(dateFormat.parse(dataI));
			cDataF.setTime(dateFormat.parse(dataF));
			
			if(cDataF.after(Calendar.getInstance()) && cDataI.before(Calendar.getInstance())){
				return Boolean.TRUE;
			}
		} catch (ParseException e) {
			throw new DaoException("Erro ao realizar parse data getDate",e);
		}
		
		return false;
	}

	public boolean getParameter(String flag) throws DaoException {
		
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement stm = null;
		Boolean isFlagAtivo = Boolean.FALSE;

		try {
			conn = new ConnectionDB().getConnection();
			stm = conn.prepareStatement("select value from flow.parameters where name = ?");
			stm.setString(1, flag);

			rs = stm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					if(rs.getString(1).equalsIgnoreCase("TRUE")){
						isFlagAtivo = Boolean.TRUE;
					}
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erro ao recuperar flag ", e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
			}
			try {
				stm.close();
			} catch (SQLException e) {
			}
		}

		return isFlagAtivo;
	}


	

}
