package br.com.ims.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.tags.ParamAware;

import br.com.ims.tool.entity.Message;
import br.com.ims.tool.entity.MessageType;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;
import br.com.ims.tool.util.ConnectionDB;


public class MessageDao {
	
private static Logger logger = Logger.getLogger(MessageDao.class);
	

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


	public static List<Message> findAll() {
		List<Message> listMessage = new ArrayList<Message>();
		Message message = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "select m.id, m.name, m.description, m.flag, m.datai, m.dataf, m.ddd_in,"
					+ " m.ddd_not_in, s.name spot, m.msg_order from flow.mensagem m, flow.spot s where m.spot = s.id ";
			
			rs = conn.ExecuteQuery(query);
			
			while (rs.next()) {
				message = new Message();
				message.setId(rs.getInt(1));
				message.setName(rs.getString(2));
				message.setDescription(rs.getString(3));
				message.setFlag(rs.getString(4));
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				message.setDatai(sdf.format(rs.getTimestamp(5)));
				message.setDataf(sdf.format(rs.getTimestamp(6)));
				message.setDdd_in(rs.getString(7));
				message.setDdd_not_in(rs.getString(8));
				message.setSpot(rs.getString(9));
				message.setMsg_order(rs.getString(10));
				
				listMessage.add(message);
			
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar mensagens ", e);
		} finally {
			conn.finalize();
		}
		return listMessage;
	}



	

}
