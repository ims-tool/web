package br.com.ims.tool.nextform.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.apache.log4j.Logger;

import br.com.ims.tool.nextform.exception.DaoException;
import br.com.ims.tool.nextform.model.LogDetailDto;
import br.com.ims.tool.nextform.model.LogDto;
import br.com.ims.tool.nextform.model.TrackDto;
import br.com.ims.tool.nextform.model.TrackServiceDto;
import br.com.ims.tool.nextform.util.ConnectionDB;
import br.com.ims.tool.nextform.util.MapValues;
import br.com.ims.tool.nextform.util.MethodInvocationUtils;

public class LogDao {
	
	private static Logger logger = Logger.getLogger(LogDao.class);
	
	public void inserirLog(LogDto log) throws DaoException{
		
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB().getConnection();

			
			String query = " INSERT INTO FLOW.LOG (ID, STARTDATE, STOPDATE, UCID, DNIS, ANI, INSTANCE, DOCUMENT, CONTEXT, PERFIL, DDD, CIDADE, UF, VDN) " +
				       " VALUES (?, localtimestamp, NULL, ?, ?, ?, ?, ?, ?,  NULL, ?, ?, ?, ?) ";
			

			stm = conn.prepareStatement(query);
			stm.setLong(1, log.getId());
			stm.setString(2, log.getUcid());
			try {
				stm.setLong(3, validNumber(log.getDnis()));
			} catch (Exception e) {
				stm.setLong(3, 0L);
			}
			try {
				stm.setLong(4, validNumber(log.getAni()));
			} catch (Exception e) {
				stm.setLong(4, 0L);
			}
			stm.setString(5, log.getInstance());
			stm.setString(6, log.getDocumento());
			stm.setString(7, log.getContext());
			stm.setString(8, log.getDdd());
			stm.setString(9, log.getCidade());
			stm.setString(10, log.getUf());
			stm.setString(11, log.getVdn());

			stm.executeUpdate();

		} catch (SQLException e) {
			logger.error("Erro ao Inserir Log ", e);
			logger.info(log.toString());
			e.printStackTrace();
			throw new DaoException("Erro ao Inserir Log ", e);
		} finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
		
	}
	
	public void updateLog(String logId, String contexto, String status) throws DaoException {
		
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		

		String instancia = MethodInvocationUtils.getContextValue(contexto, MapValues.INSTANCE);
		try {
			if(instancia == null || instancia.isEmpty()) {
				instancia = MethodInvocationUtils.getContextValue(contexto, MapValues.ANI);;
			}
		} catch (Exception e) {}
		
		String ddd = MethodInvocationUtils.getContextValue(contexto, MapValues.DDD);
		try {
			if(ddd == null || ddd.isEmpty()) {
				ddd = instancia.substring(0, 2);
			}
		} catch (Exception e) {
			throw new DaoException("Erro ao Inserir Log ", e);
		}
		
		
		try {
			conn = new ConnectionDB();

			String query = " UPDATE FLOW.LOG SET STOPDATE = localtimestamp, FINALSTATUS = ?, CONTEXT = ?," +
					   "  DDD = ?, CIDADE = ?, UF = ?, VDN = ?, " +
					   " INSTANCE = ?, ANI = ? " +	
					   " WHERE ID = ? ";

			stm = conn.getPreparedStatement(query);
			
			stm.setString(1, status);
			stm.setString(2, contexto);
			stm.setString(3, ddd);
			stm.setString(4, MethodInvocationUtils.getContextValue(contexto, MapValues.CIDADE));
			stm.setString(5, MethodInvocationUtils.getContextValue(contexto, MapValues.UF));
			stm.setString(6, MethodInvocationUtils.getContextValue(contexto, MapValues.VDN));
			stm.setString(7, instancia);
			stm.setLong(8, validNumber(MethodInvocationUtils.getContextValue(contexto, MapValues.ANI)));
			
			stm.setLong(9, Long.valueOf(logId));
			
			stm.executeUpdate();

		} catch (SQLException e) {
			logger.error("Erro ao Finalizar Log LogId= "+logId, e);
			throw new DaoException("Erro ao Finalizar Log ", e);
		} finally {
			conn.finalize();
			try {stm.close();} catch (SQLException e) {}
		}
		
	}
	
	

	public void inserirLogDetail(LogDetailDto logDetail) throws DaoException {
		
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();

			String query = " INSERT INTO FLOW.LOGDETAIL (ID, LOGID, ROWDATE, PARAMNAME, PARAMVALUE) VALUES (NEXTVAL ('FLOW.SEQ_LOGDETAIL'), ?, localtimestamp, ?, ?) ";
			
			stm = conn.getPreparedStatement(query);
			stm.setLong(1, logDetail.getLogId());
			stm.setString(2, logDetail.getParamName());
			stm.setString(3, logDetail.getParamValue());

			conn.executeInsert(stm);

		} catch (SQLException e) {
			logger.error("Erro ao Inserir LogDetail ", e);
			logger.info(logDetail.toString());
			throw new DaoException("Erro ao Inserir LogDetail ", e);
		} finally {
			conn.finalize();
			try {stm.close();} catch (SQLException e) {}
		}
		
	}
	
	public long buscarLogId() throws Exception {
		
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;

		Long retorno = null;
		try {
			conn = new ConnectionDB();
			
			String query = "SELECT NEXTVAL ('FLOW.SEQ_LOG')";

			stm = conn.getPreparedStatement(query);
			rs = stm.executeQuery();
			
			if (rs.next()) {
				retorno = rs.getLong(1);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar id do Log", e);
			throw new DaoException("Erro ao Recuperar id do Log", e);
		} finally {
			conn.finalize();
			try {stm.close();} catch (SQLException e) {}
		}
		return retorno;
	}
	
	public long buscarTrackId() throws DaoException {
		
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;

		Long retorno = null;
		try {
			conn = new ConnectionDB();
			
			Random gerador = new Random();
			
			String query = "SELECT NEXTVAL ('flow.SEQ_TRACK')";

			stm = conn.getPreparedStatement(query);
			rs = stm.executeQuery();
			
			if (rs.next()) {
				retorno = Long.parseLong(gerador.nextInt(10) + "" + rs.getLong(1));
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar id do Track", e);
			throw new DaoException("Erro ao Recuperar id do Track", e);
		} finally {
			conn.finalize();
			try {stm.close();} catch (SQLException e) {}
		}
		return retorno;
	}
	
	public long buscarTrackServiceId() throws DaoException {
		
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;

		Long retorno = null;
		try {
			conn = new ConnectionDB();
			
			Random gerador = new Random();
			
			String query = "SELECT NEXTVAL ('flow.SEQ_TRACKSERVICE')";

			stm = conn.getPreparedStatement(query);
			rs = stm.executeQuery();
			
			if (rs.next()) {
				retorno = Long.parseLong(gerador.nextInt(10) + "" + rs.getLong(1));
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar id do Track", e);
			throw new DaoException("Erro ao Recuperar id do Track", e);
		} finally {
			conn.finalize();
			try {stm.close();} catch (SQLException e) {}
		}
		return retorno;
	}
	
	public void inserirTrack(TrackDto track) throws DaoException {
		
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB().getConnection();

			String query = " INSERT INTO FLOW.TRACK (ID, LOGID, ROWDATE, FORMID, TAGID, LOG_TYPE) VALUES (?, ?, localtimestamp, ?, ?, ?) ";

			stm = conn.prepareStatement(query);
			stm.setLong(1, track.getId());
			stm.setLong(2, track.getLogId());
			stm.setLong(3, track.getFormId());
			stm.setLong(4, track.getTagId());
			stm.setString(5, track.getLogType());

			stm.executeUpdate();

		} catch (SQLException e) {
			logger.error("Erro ao Inserir Track ", e);
			logger.info(track.toString());
			throw new DaoException("Erro ao Inserir Track ", e);
		} finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
		
	}
	
	public void inserirTrackService(TrackServiceDto track) throws DaoException {
		
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();

			
			String query = " INSERT INTO FLOW.TRACKSERVICE (ID, TRACKID, GROUPID, METHOD_SERVICE, RESULT_CALL, PARAMETERS_IN, ROWDATE, ERRORCODEID, DESCRIPTION, LOGID, TIME_EXEC)" +
					   " VALUES (?, ?, ?, ?, ?, ?, localtimestamp, ?, ?, ?, ?) ";

			stm = conn.getPreparedStatement(query);
			stm.setLong(1, track.getId());
			stm.setLong(2, track.getTrackid());
			stm.setLong(3, track.getGroupid());
			stm.setString(4, track.getMethod_service());
			stm.setString(5, track.getResult_call());
			stm.setString(6, track.getParameters_in());
			stm.setLong(7, track.getErrorCode());
			stm.setString(8, track.getDescription());
			stm.setLong(9, track.getLogId());
			stm.setDouble(10, track.getTime_exec());

			conn.executeInsert(stm);

		} catch (SQLException e) {
			logger.error("Erro ao Inserir TRACKSERVICE ", e);
			logger.info(track.toString());
			throw new DaoException("Erro ao Inserir TRACKSERVICE ", e);
		} finally {
			conn.finalize();
			try {stm.close();} catch (SQLException e) {}
		}
		
	}
	
	public void inserirTrackDetail(long trackId, String paramName, String paramValue, long logId) throws DaoException {
			
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB().getConnection();
	
			String query = " INSERT INTO FLOW.TRACKDETAIL (ID, TRACKID, PARAMNAME, PARAMVALUE, ROWDATE, LOGID) " +
						   " VALUES (NEXTVAL ('flow.SEQ_TRACKDETAIL'), ?, ?, ?, localtimestamp, ?) ";
	
			stm = conn.prepareStatement(query);
			stm.setLong(1, trackId);
			stm.setString(2, paramName);
			stm.setString(3, paramValue);
			stm.setLong(4, logId);
	
			stm.executeUpdate();
	
		} catch (SQLException e) {
			logger.error("Erro ao Inserir TRACKDETAIL ", e);
			throw new DaoException("Erro ao Inserir TRACKDETAIL ", e);
		} finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
			
	}
	
	public String getRecallForm(String instance, String tag, String parameter, String status) throws DaoException{
		String form = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		String horasRechamada = "";
		
		try {
			conn = new ConnectionDB();
			
			stm = conn.getPreparedStatement("select FLOW.GETPAR(?) as horasRechamada");
			stm.setString(1, parameter);
			
			rs = stm.executeQuery();
			if (rs.next()) {
				horasRechamada = rs.getString("horasRechamada");
			}
			
			String query = "select tagid " +
			 "from " +
			 "( " +
			 "select rownum, x.* from " + 
			 "  ( " +
			 "  Select L.ID, L.INSTANCE,  L.STARTDATE,L.STOPDATE, tt.tagid, tt.rowdate " + 
			 "           from ivr_owner.log l,ivr_owner.tracktag tt  " +
			 "      where l.startdate BETWEEN sysdate-"+horasRechamada+"/24 AND SYSDATE  " +
			 "      and l.instance=?  " +
			 "      and l.finalstatus = ? " +
			 "      and tt.rowdate BETWEEN sysdate-"+horasRechamada+"/24 AND SYSDATE " + 
			 "      AND TT.LOGID=L.Id " +
			 "      and tt.TAGID IN("+tag+") " +
			 "      order by l.startdate desc " +
			 ")x where rownum=1 " +
			 ")";
			
			stm = conn.getPreparedStatement(query);
			stm.setString(1, instance);
			stm.setString(2, status);

			rs = stm.executeQuery();
			if(rs.isBeforeFirst())
				if (rs.next()) {
					form = rs.getString("tagid");
				}
			
		} catch (SQLException e) {
			logger.error("Erro ao consultar instancia", e);
			throw new DaoException("Erro ao consultar instancia", e);
		}  finally {
			conn.finalize();
			try {stm.close();} catch (SQLException e) {}
		}
		
		return form;
	}
	
	public String getRecallForm(String instance, String parameter) throws DaoException{
		String form = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try {
			conn = new ConnectionDB();

			String query = "select formid " +
					"from " +
					"( " +
					"select rownum, x.* " +
					"from " +
					"( " +
					"Select L.ID, L.INSTANCE,  L.STARTDATE,L.STOPDATE, " +
					"T.ROWDATE, T.FORMID, FLOW.GETFORM(T.FORMID),T.TAGID " +
					", f.name, f.formtype " +
					"from ivr_owner.log l " +
					"join ivr_owner.TRACK t on t.logid=l.id AND T.ROWDATE BETWEEN L.STARTDATE AND L.STOPDATE " +
					"JOIN ivr_owner.form f on f.id=t.formid and f.formtype=7 " +
					"where l.startdate BETWEEN sysdate-ivr_owner.GETPAR(?)/24 AND SYSDATE " +
					"and l.instance=? " +
					"and l.finalstatus='T' " +
					"order by rowdate desc " +
					") x " +
					"where rownum=1 " +
					")";

			stm = conn.getPreparedStatement(query);
			stm.setString(1, parameter);
			stm.setString(2, instance);
			rs = stm.executeQuery();
			if (rs.next()) {
				form = rs.getString("formid");
			}
			
		} catch (SQLException e) {
			logger.error("Erro ao consultar instancia", e);
			throw new DaoException("Erro ao consultar instancia ", e);
		}  finally {
			conn.finalize();
			try {stm.close();} catch (SQLException e) {}
		}
		
		return form;
	}
	
	public void inserirTrackTag(long id, long trackId, long logId, long tagId) throws DaoException {
		
		Connection conn = null;
		PreparedStatement stm = null;
		
		try {
			conn = new ConnectionDB().getConnection();

			String query = " INSERT INTO FLOW.TRACKTAG (ID, TRACKID, LOGID, TAGID, ROWDATE) " +
			   " VALUES (?, ?, ?, ?, localtimestamp) ";
			
			stm = conn.prepareStatement(query);
			stm.setLong(1, id);
			stm.setLong(2, trackId);
			stm.setLong(3, logId);
			stm.setLong(4, tagId);
//			stm.executeUpdate();

		} catch (SQLException e) {
			logger.error("Erro ao Inserir TRACKTAG ", e);
			throw new DaoException("Erro ao Inserir TRACKTAG ", e);
		}  finally {
			try {conn.close();} catch (SQLException e1) {}
			try {stm.close();} catch (SQLException e) {}
		}
		
	}
	
public boolean isRetencao(long logId) throws DaoException {
		
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;

		boolean retorno = false;
		try {
			conn = new ConnectionDB();
			String query = 	" SELECT 1 FROM FLOW.TRACKTAG TRT, FLOW.TAG TAG WHERE LOGID = ? " +
							" AND TRT.TAGID = TAG.ID AND TAG.TAGTYPEID = 1 ";

			stm = conn.getPreparedStatement(query);
			stm.setLong(1, logId);
			rs = stm.executeQuery();
			
			if (rs.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar Log para validar se e de retencao", e);
			throw new DaoException("Erro ao Recuperar Log para validar se e de retencao", e);
		} finally {
			conn.finalize();
			try {stm.close();} catch (SQLException e) {}
		}
		return retorno;
	}
	
	
	private Long validNumber(String value) {
		
		if(value != null){
			Long i = null;
			
			try {
				i = Long.parseLong(value);
				return i; 
			} catch (Exception e) {
				logger.error("Erro ao validar numero valor= "+value, e);
			}
		}
		return null;
	}

}
