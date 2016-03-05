package br.com.ims.dashboard.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import br.com.ims.dashboard.model.Retencao;
import br.com.ims.dashboard.util.OracleConn;


public class DashboardDAO {

	
	Logger log = Logger.getLogger(DashboardDAO.class);

	public HashMap<String,String> getHealthWebServices() {
		
		log.debug("[Dashboard Telefonia] - " + "iniciando busca por HealthWebServices");
		
		HashMap<String,String> webServices = new HashMap<String, String>();
		ResultSet rs = null;
		String sql = "";
		OracleConn oracle = null;

		sql = "SELECT 100-ROUND(100*(SUM(ERRO)/COUNT(1)),2) OK, ROUND(100*(SUM(ERRO)/COUNT(1)),2) NOK  FROM ( "+
			  "	  SELECT method_service,COUNT(1) TOTAL,SUM(TIMEOUT) TIMEOUT,100*(SUM(TIMEOUT)/COUNT(1)) PORCENTAGEM, "+ 
			  "	         CASE WHEN 100*(SUM(TIMEOUT)/COUNT(1)) > 0 THEN 1 ELSE 0 END ERRO  "+
			  "	  FROM (  "+
			  "	      Select   "+
			  "	       C.ID,  "+
			  "	       ts.method_service, "+
			  "	      CASE WHEN TS.TIME_EXEC >= C.TIMEOUT THEN 1 ELSE 0 END TIMEOUT "+
			  "	      from ivr_owner.trackservice ts "+
			  "	      JOIN IVR_OWNER.CONTROLPANEL C ON C.METHODNAME=TS.METHOD_SERVICE "+
			  "	      where ts.rowdate BETWEEN sysdate-1/24 and sysdate "+
			  "	  ) GROUP BY  method_service "+   
			  "	) ";

		try {
			oracle = new OracleConn("IVR_OWNER");
			rs = oracle.ExecuteQuery(sql);
			
			if (rs.next()) {
				webServices.put("OK", rs.getString("OK"));
				webServices.put("NOK", rs.getString("NOK"));				
			}

		} catch (Exception e) {
			log.error("[Dashboard HealthWebServices] -" + e.getMessage(),e);
			return null;

		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			oracle.finalize();

		}
		return webServices;
	}
	
	public HashMap<Integer,Retencao> retornaRetencao() {
		log.debug("[Dashboard Telefonia] - " + "iniciando busca por reten��es");
		Retencao retencao = null;
		HashMap<Integer,Retencao> retencoes = new HashMap<Integer, Retencao>();
		ResultSet rs = null;
		String sql = "";
		OracleConn oracle = null;

		sql = "SELECT SUBSTR(TO_CHAR(STARTDATE,'DD/MM/YYYY HH24:MI'),1,15)||'0' DIA, "+ 
			  "COUNT(1) QTDE, "+
			  "SUM(CASE WHEN FINALSTATUS = 'R' THEN 1 ELSE 0 END) QTDE_RET, "+ 
			  "ROUND(100*(SUM(CASE WHEN FINALSTATUS = 'R' THEN 1 ELSE 0 END) /COUNT(1)),2) PORCENTAGEM "+
			  "FROM LOG WHERE STARTDATE BETWEEN SYSDATE -1/24 AND SYSDATE "+
			  "GROUP BY SUBSTR(TO_CHAR(STARTDATE,'DD/MM/YYYY HH24:MI'),1,15) "+
			  "ORDER BY TO_DATE(SUBSTR(TO_CHAR(STARTDATE,'DD/MM/YYYY HH24:MI'),1,15)||'0','DD/MM/YYYY HH24:MI')";

		try {
			oracle = new OracleConn("IVR_OWNER");
			rs = oracle.ExecuteQuery(sql);
			int i = 0;
			while (rs.next()) {
				retencao = new Retencao();
				retencao.setData(rs.getString("DIA"));
				retencao.setPorcentagem(rs.getDouble("PORCENTAGEM"));
				retencoes.put(i, retencao);
				i++;
			}

		} catch (Exception e) {
			log.error("[Dashboard Retencao] -" + e.getMessage(),e);
			return null;

		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			oracle.finalize();

		}
		return retencoes;
	}
	public HashMap<String,Integer> retornaVolumeLigacaoMinuto() {
		
		log.debug("[Dashboard Telefonia] - " + "iniciando busca por Volume de Liga��es por Minuto");

		HashMap<String,Integer> volume = new HashMap<String,Integer>();
		ResultSet rs = null;
		String sql = "";
		OracleConn oracle = null;

		sql = "SELECT TO_CHAR(STARTDATE,'DD/MM/YYYY HH24:MI') DIA, "+ 
			  "COUNT(1) VOLUME "+
			  "FROM LOG WHERE STARTDATE BETWEEN SYSDATE -1/24 AND SYSDATE "+
			  "GROUP BY TO_CHAR(STARTDATE,'DD/MM/YYYY HH24:MI') "+
			  "ORDER BY TO_DATE(TO_CHAR(STARTDATE,'DD/MM/YYYY HH24:MI'),'DD/MM/YYYY HH24:MI') "; 

		try {
			oracle = new OracleConn("IVR_OWNER");
			rs = oracle.ExecuteQuery(sql);
			
			while (rs.next()) {
				volume.put(rs.getString("DIA"), rs.getInt("VOLUME"));				
			}

		} catch (Exception e) {
			log.error("[Dashboard Volume Ligacao Minuto] -" + e.getMessage(),e);
			return null;

		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			oracle.finalize();

		}
		return volume;
	}
	public HashMap<String,Integer> retornaVolumeLigacaoURA() {
		
		log.debug("[Dashboard Telefonia] - " + "iniciando busca por Volume de Liga��es por URA");

		HashMap<String,Integer> volume = new HashMap<String,Integer>();
		ResultSet rs = null;
		String sql = "";
		OracleConn oracle = null;

		sql = "select r.description ,count(1) volume "+ 
			  "from ivr_prod.router r,log l "+
			  "where l.STARTDATE BETWEEN SYSDATE-1/24 AND SYSDATE "+
			  "and l.dnis = r.dnis "+
			  "group by r.description "+
			  "order by count(1) desc "; 

		try {
			oracle = new OracleConn("IVR_OWNER");
			rs = oracle.ExecuteQuery(sql);
			
			while (rs.next()) {
				volume.put(rs.getString("description"), rs.getInt("volume"));				
			}

		} catch (Exception e) {
			log.error("[Dashboard Volume Ligacao URA] -" + e.getMessage(),e);
			return null;

		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			oracle.finalize();

		}
		return volume;
	}
	

}
