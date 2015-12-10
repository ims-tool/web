package br.com.ims.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			String query = "select * from flow.Service_Hour where type='"+type+"'";
			
			rs = conn.ExecuteQuery(query);
			
			while (rs.next()) {
				serviceHour = new ServiceHour();
				serviceHour.setId(rs.getInt(1));
				serviceHour.setStarthour(rs.getString(2));
				serviceHour.setStophour(rs.getString(3));
				serviceHour.setType(rs.getString(4));
				serviceHour.setLastlogin(rs.getString(5));
				serviceHour.setChangedate(rs.getDate(6));
				serviceHour.setWeekday(rs.getInt(7));
				
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
			String query = "select * from flow.Service_Hour where id="+entity.getId();
			
			rs = conn.ExecuteQuery(query);
			
			if (!rs.next()) {
				serviceHour = new ServiceHour();
				serviceHour.setId(rs.getInt(1));
				serviceHour.setStarthour(rs.getString(2));
				serviceHour.setStophour(rs.getString(3));
				serviceHour.setType(rs.getString(4));
				serviceHour.setLastlogin(rs.getString(5));
				serviceHour.setChangedate(rs.getDate(6));
				serviceHour.setWeekday(rs.getInt(7));
				//não implementado pois não existe a necessidade de salvar um novo parametro.
				query = "insert into flow.ServiceHour order by id";
			}else{
				query = "update flow.Service_Hour set stophour= '"+entity.getStophour()+"', starthour= '"+entity.getStarthour()+"', lastlogin= '"+entity.getLastlogin()+"', changedate = current_timestamp  where id="+entity.getId();
				conn.ExecuteSql(query);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar parametros ", e);
		} finally {
			conn.finalize();
		}
	}


	public static List<ServiceHourType> findType() {
		
		List<ServiceHourType> listType = new ArrayList<ServiceHourType>();
		ServiceHourType type = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "select distinct type  from flow.Service_Hour";
			
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
