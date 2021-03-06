package br.com.ims.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.tags.ParamAware;

import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;
import br.com.ims.tool.util.ConnectionDB;


public class ServiceHourDao {
	
private static Logger logger = Logger.getLogger(ServiceHourDao.class);
	
	
	public static List<ServiceHour> findAll(String type) {
		
		List<ServiceHour> listServiceHour = new ArrayList<ServiceHour>();
		ServiceHour serviceHour = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "select id, weekday, type, changedate, starthour, stophour from flow.service_hour where type='"+type+"'";
			
			rs = conn.ExecuteQuery(query);
			
			while (rs.next()) {
				serviceHour = new ServiceHour();
				serviceHour.setId(rs.getInt(1));
				serviceHour.setWeekday(rs.getInt(2));
				serviceHour.setType(rs.getString(3));
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
				serviceHour.setLastchange(sdf.format(rs.getTimestamp(4)));
//				serviceHour.setChangedate(rs.getDate(4));
				serviceHour.setStarthour(rs.getString(5));
				serviceHour.setStophour(rs.getString(6));
				
				listServiceHour.add(serviceHour);
			
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar service hours ", e);
		} finally {
			conn.finalize();
		}
		return listServiceHour;
	}


	public static ServiceHour find(Integer id) {
		
		return null;
	}


	public static void save(ServiceHour entity) {
		ServiceHour serviceHour = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "select id, weekday, type, changedate, starthour, stophour  from flow.Service_Hour where id="+entity.getId();
			
			rs = conn.ExecuteQuery(query);
			
			if (!rs.next()) {
				serviceHour = new ServiceHour();
				serviceHour.setId(rs.getInt(1));
				serviceHour.setWeekday(rs.getInt(2));
				serviceHour.setType(rs.getString(3));
				serviceHour.setChangedate(rs.getDate(4));
				serviceHour.setStarthour(rs.getString(5));
				serviceHour.setStophour(rs.getString(6));
				//não implementado pois não existe a necessidade de salvar um novo parametro.
				query = "insert into flow.ServiceHour order by id";
			}else{
				query = "update flow.Service_Hour set stophour= '"+entity.getStophour()+"', starthour= '"+entity.getStarthour()+"', changedate = current_timestamp  where id="+entity.getId();
				conn.ExecuteSql(query);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar parametros ", e);
		} finally {
			conn.finalize();
		}
	}


	public static List<ServiceHourType> findType(String user) {
		
		List<ServiceHourType> listType = new ArrayList<ServiceHourType>();
		ServiceHourType type = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "select distinct type from flow.service_hour where type in "
					+ "(select a.description from access.artifact_access_type_user au, access.user u, access.area a "
					+ "where a.id = au.areaid and au.userid = u.id and u.login = '"+user+"' and au.artifactid = 2)";
			rs = conn.ExecuteQuery(query);
			int id = 0;
			while (rs.next()) {
				id++;
				type = new ServiceHourType();
				type.setId(id);
				type.setType(rs.getString(1));
				listType.add(type);
			
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar service hours ", e);
		} finally {
			conn.finalize();
		}
		return listType;
		
	}
	

}
