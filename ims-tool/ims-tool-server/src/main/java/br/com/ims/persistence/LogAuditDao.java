package br.com.ims.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.tags.ParamAware;

import br.com.ims.tool.entity.Audit;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;
import br.com.ims.tool.util.ConnectionDB;


public class LogAuditDao {
	
private static Logger logger = Logger.getLogger(LogAuditDao.class);
	

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



	public static void save(Audit logAudit) {
		// TODO Auto-generated method stub
		
	}
	

}
