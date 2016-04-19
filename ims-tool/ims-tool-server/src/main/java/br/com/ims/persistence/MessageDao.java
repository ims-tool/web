package br.com.ims.persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ParseConversionEvent;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.tags.ParamAware;

import antlr.StringUtils;
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

	public static List<Message> findAll() {
		List<Message> listMessage = new ArrayList<Message>();
		Message message = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		String path = getPath();
		try {
			conn = new ConnectionDB();
			String query = "select m.id, m.name, m.description, m.flag, m.datai, m.dataf, m.ddd_in,"
					+ " m.ddd_not_in, m.spot spot, m.msg_order from flow.mensagem m order by m.id";

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
				message.setPath(path + message.getId().toString()+".wav");
				message.setPath(message.getPath().trim());
				listMessage.add(message);

			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar mensagens ", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.finalize();
		}
		return listMessage;
	}

	

	public static String getNexIdMessage() {

		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		String nextIdMessage = "";
		try {
			conn = new ConnectionDB();
			String query = "select max(m.id)+1 nextId from flow.mensagem m";

			rs = conn.ExecuteQuery(query);

			while (rs.next()) {
				nextIdMessage = String.valueOf(rs.getInt(1));
			}
		} catch (SQLException e) {
			logger.error("Erro ao recuperar next id", e);
		} finally {
			conn.finalize();
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return nextIdMessage;
	}

	public static void save(Message message) {


		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		Boolean isNewData = Boolean.TRUE;
		try {
			conn = new ConnectionDB();
			String query = "select * from flow.mensagem m where id = " + message.getId();

			rs = conn.ExecuteQuery(query);

			if (rs.next()) {
				isNewData = Boolean.FALSE;
			}

		} catch (SQLException e) {
			logger.error("Erro ao Recuperar mensagens ", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.finalize();
		}

		// Nova mensagem
		if (isNewData) {

			PreparedStatement pstmt = null;
			ConnectionDB conn2 = null;
			try {
				conn2 = new ConnectionDB();

				String query = "insert into flow.mensagem (id, name, description, flag, datai, dataf, ddd_in, ddd_not_in, spot, msg_order)"
						+ " values (?,?,?,?,?,?,?,?,?,?)";

				pstmt = conn2.getPreparedStatement(query);

				pstmt.setInt(1, message.getId());
				pstmt.setString(2, message.getName());
				pstmt.setString(3, message.getDescription());
				pstmt.setString(4, message.getFlag());
				pstmt.setTimestamp(5, formatDate(message.getDatai()));
				pstmt.setTimestamp(6, formatDate(message.getDataf()));
				pstmt.setString(7, message.getDdd_in());
				pstmt.setString(8, message.getDdd_not_in());
				pstmt.setString(9, message.getSpot());
				if(org.apache.commons.lang3.StringUtils.isNotBlank(message.getMsg_order())){
					pstmt.setInt(10, Integer.valueOf(message.getMsg_order()));
				}else{
					pstmt.setInt(10, 999);
				}

				conn2.executeQueryUpdate(pstmt);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Erro ao Recuperar mensagens ", e);
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e1) {
				}
				conn2.finalize();
			}
		} else {
			PreparedStatement pstmt = null;
			try {
				conn = new ConnectionDB();
				String query = "update flow.mensagem  set name = ?, description = ?, flag = ?, datai = ?, dataf = ?, ddd_in = ?,  ddd_not_in = ?, spot = ?, msg_order = ? where id = ?";
				
				pstmt = conn.getPreparedStatement(query);

				pstmt.setString(1, message.getName());
				pstmt.setString(2, message.getDescription());
				pstmt.setString(3, message.getFlag());
				pstmt.setTimestamp(4, formatDate(message.getDatai()));
				pstmt.setTimestamp(5, formatDate(message.getDataf()));
				pstmt.setString(6, message.getDdd_in());
				pstmt.setString(7, message.getDdd_not_in());
				pstmt.setString(8, message.getSpot());
				if(org.apache.commons.lang3.StringUtils.isNotBlank(message.getMsg_order())){
					pstmt.setInt(9, Integer.valueOf(message.getMsg_order()));
				}else{
					pstmt.setInt(9, 999);
				}
				pstmt.setInt(10, message.getId());

				conn.executeQueryUpdate(pstmt);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Erro ao Recuperar mensagens ", e);
			} finally {
				conn.finalize();
			}
		}

	}

	private static Timestamp formatDate(String datai) {
		java.util.Date parse = null;
		try {
			if(datai.contains("/")){
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				parse = dateFormat.parse(datai);
			}else{
				SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
				parse = dateFormat.parse(datai);
			}

		} catch (Exception e) {// this generic but you can control another types
								// of exception
			e.printStackTrace();
		}

		return new Timestamp(parse.getTime());
	}

	public static List<String> findSpotList() {
		
		List<String> spotList = new ArrayList<String>();
		String spot ="";
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "select NAME from flow.spot order by name";

			rs = conn.ExecuteQuery(query);

			while (rs.next()) {
				spot = rs.getString(1);
				spotList.add(spot);

			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar Spot ", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.finalize();
		}
		return spotList;
	}

	public static void remove(Integer id) {
		
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "delete from flow.mensagem where id="+id;

			conn.ExecuteQueryUpdate(query);

		} catch (SQLException e) {
			logger.error("Erro deletar mensagem", e);
		} finally {
			conn.finalize();
		}
		
	}
	
	private static String getPath() {
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		String path = "http://localhost:8080/_audios/msg/";
		try {
			conn = new ConnectionDB();
			String query = "select value from flow.parameters where name like '%path_msg%'";

			rs = conn.ExecuteQuery(query);
			
			if(rs.next()){
				path = rs.getString(1);
			}

		} catch (SQLException e) {
			logger.error("Erro deletar mensagem", e);
		} finally {
			try {rs.close();} catch (SQLException e) {}
			conn.finalize();
		}
		return path;
	}

}
