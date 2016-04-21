package br.com.ims.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.ims.tool.entity.Controlpanel;
import br.com.ims.tool.entity.ReportLog;
import br.com.ims.tool.util.ConnectionDB;

public class ReportDao {

	private static Logger logger = Logger.getLogger(ReportDao.class);


	public static List<String> getTypeControlPanel() {
		List<String> listType = new ArrayList<String>();
		listType.add("TODOS");
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "select distinct(owner) from flow.controlpanel";

			rs = conn.ExecuteQuery(query);
			
			while(rs.next()){
				listType.add(rs.getString(1));
			}

		} catch (SQLException e) {
			logger.error("Erro ao recuperar type control panel", e);
		} finally {
			try {rs.close();} catch (SQLException e) {}
			conn.finalize();
		}
		return listType;
	}


	public static List<Controlpanel> getControlPanelList(String group) {
		List<Controlpanel> listType = new ArrayList<Controlpanel>();
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		String query = "";
		try {
			conn = new ConnectionDB();
			if((StringUtils.isNotBlank(group))&&(!group.equalsIgnoreCase("TODOS"))){
				query = "SELECT id, methodname, description, owner, referencedby, status, loginid, startdate, versionid, timeout FROM flow.controlpanel where upper(owner) = upper('"+group+"') order by id";
			}else{
				query = "SELECT id, methodname, description, owner, referencedby, status, loginid, startdate, versionid, timeout FROM flow.controlpanel order by id"; 
			}

			rs = conn.ExecuteQuery(query);
			
			while(rs.next()){
				Controlpanel cp = new Controlpanel();
				cp.setId(rs.getInt(1));
				cp.setMethodname(rs.getString(2));
				cp.setDescription(rs.getString(3));
				cp.setOwner(rs.getString(4));
				cp.setReferencedby(rs.getString(5));
				cp.setStatus(rs.getString(6));
				cp.setLoginid(rs.getString(7));
//				cp.setStartdate(rs.getDate(8));
				cp.setDatetime(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp(8)));
				cp.setVersionid(rs.getInt(9));
				cp.setTimeout(rs.getInt(10));
				listType.add(cp);
			}

		} catch (SQLException e) {
			logger.error("Erro ao recuperar lista control panel", e);
		} finally {
			try {rs.close();} catch (SQLException e) {}
			conn.finalize();
		}
		return listType;
	}


	public static void save(Controlpanel cp) {
		
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "update flow.controlpanel set status = ?, loginid = ?, startdate = CURRENT_TIMESTAMP , timeout = ? where id = ?";
			stm = conn.getPreparedStatement(query);
			stm.setString(1, cp.getStatus());
			stm.setString(2, cp.getLoginid());
			stm.setInt(3, cp.getTimeout());
			stm.setInt(4, cp.getId());

			conn.executeQueryUpdate(stm);
		} catch (SQLException e) {
			logger.error("Erro ao recuperar type control panel", e);
		} finally {
			try {
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.finalize();
		}
		
	}


	public static List<String> getArtifactList(Date dateLog) {
		List<String> listArtifact = new ArrayList<String>();
		ConnectionDB conn = null;
		ResultSet rs = null;
		try {
			conn = new ConnectionDB();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(dateLog);
			c.add(Calendar.DATE, 1);  // number of days to add
			
//			String query = "select  a.rowdate, a.id, u.login, c.description, a.description, a.artifact, a.original_value, a.valueid, a.artifactid  from log.audit a, access.user u, log.type_change c where a.userid = u.id and c.id = a.typeid and rowdate between '"+sdf.format(dateLog)+"' and '"+sdf.format(c.getTime())+"'";
			
			String query = "select  distinct(artifact) from log.audit where rowdate between '"+sdf.format(dateLog)+"' and '"+sdf.format(c.getTime())+"'";
			
			rs = conn.ExecuteQuery(query);
			
			while(rs.next()){
				listArtifact.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			logger.error("Erro ao recuperar type control panel", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn.finalize();
		}
		return listArtifact;
	}


	public static List<ReportLog> getLogList(Date dateLog, String artifact) {
		List<ReportLog> listReportlog = new ArrayList<ReportLog>();
		ConnectionDB conn = null;
		ResultSet rs = null;
		try {
			conn = new ConnectionDB();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(dateLog);
			c.add(Calendar.DATE, 1);  // number of days to add
			
			String query = "select  a.rowdate, a.id, u.login, c.description, a.description, a.artifact, a.original_value, a.valueid, a.artifactid  from log.audit a, access.user u, log.type_change c where a.userid = u.id and c.id = a.typeid and rowdate between '"+sdf.format(dateLog)+"' and '"+sdf.format(c.getTime())+"' "
					+ " and a.artifact = '"+artifact+"'" ;
			
			rs = conn.ExecuteQuery(query);
			
			while(rs.next()){
				ReportLog rl = new ReportLog();
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				rl.setRowdate(sdf2.format(rs.getTimestamp(1)));
				rl.setId(rs.getInt(2));
				rl.setLogin(rs.getString(3));
				rl.setType(rs.getString(4));
				rl.setDescription(rs.getString(5));
				rl.setArtifact(rs.getString(6));
				rl.setOriginalValue(rs.getString(7));
				rl.setValueid(rs.getInt(8));
				rl.setArtifactid(rs.getInt(9));
				listReportlog.add(rl);
				
			}
			
		} catch (SQLException e) {
			logger.error("Erro ao recuperar log list report", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn.finalize();
		}
		return listReportlog;
	}

}
