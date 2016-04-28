package br.com.ims.dashboard.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;

import br.com.ims.dashboard.model.Retencao;
import br.com.ims.dashboard.util.DbConnection;


public class DashboardDAO {

	
	Logger log = Logger.getLogger(DashboardDAO.class);

	public HashMap<String,String> getHealthWebServices() {
		
		log.debug("[Dashboard IMS] - " + "iniciando busca por HealthWebServices");
		
		HashMap<String,String> webServices = new HashMap<String, String>();
		ResultSet rs = null;
		String sql = "";
		//OracleConn oracle = null;
		DbConnection db = null;
		sql = "SELECT 100-ROUND(100*(SUM(ERRO)/COUNT(1)),2) OK, ROUND(100*(SUM(ERRO)/COUNT(1)),2) NOK  FROM ( "+
			  "	  SELECT method_service,COUNT(1) TOTAL,SUM(TIMEOUT) TIMEOUT,100*(SUM(TIMEOUT)/COUNT(1)) PORCENTAGEM, "+ 
			  "	         CASE WHEN 100*(SUM(TIMEOUT)/COUNT(1)) > 0 THEN 1 ELSE 0 END ERRO  "+
			  "	  FROM (  "+
			  "	      Select   "+
			  "	       C.ID,  "+
			  "	       ts.method_service, "+
			  "	      CASE WHEN TS.TIME_EXEC >= C.TIMEOUT THEN 1 ELSE 0 END TIMEOUT "+
			  "	      from flow.trackservice ts "+
			  "	      JOIN flow.CONTROLPANEL C ON C.METHODNAME=TS.METHOD_SERVICE "+
			  "	      where ts.rowdate BETWEEN now()-interval '1 hour' and now() "+
			  "	  ) A GROUP BY  method_service "+   
			  "	) A ";

		try {
			db = new DbConnection("");
			//oracle = new OracleConn("IVR_OWNER");
			rs = db.ExecuteQuery(sql);
			
			if (rs.next()) {
				webServices.put("OK", rs.getString("OK"));
				webServices.put("NOK", rs.getString("NOK"));				
			}

		} catch (Exception e) {
			log.error("[Dashboard HealthWebServices] -" + e.getMessage(),e);
			return null;

		} finally {
			//oracle.finalize();
			db.finalize();

		}
		return webServices;
	}
	
	public HashMap<Integer,Retencao> retornaRetencao() {
		log.debug("[Dashboard IMS] - " + "iniciando busca por retencoes");
		Retencao retencao = null;
		HashMap<Integer,Retencao> retencoes = new HashMap<Integer, Retencao>();
		ResultSet rs = null;
		String sql = "";
		//OracleConn oracle = null;
		DbConnection db = null;

		sql = "SELECT TO_CHAR(STARTDATE,'HH24')||':00' DIA, "+ 
			  "COUNT(1) QTDE, "+
			  "SUM(CASE WHEN FINALSTATUS = 'R' THEN 1 ELSE 0 END) QTDE_RET, "+ 
			  "ROUND(100*(SUM(CASE WHEN FINALSTATUS = 'R' THEN 1 ELSE 0 END)) /COUNT(1),2) PORCENTAGEM "+
			  "FROM FLOW.LOG WHERE STARTDATE BETWEEN now()-interval '1 hour' AND now() "+
			  "GROUP BY TO_CHAR(STARTDATE,'HH24') "+
			  "ORDER BY TO_TIMESTAMP(TO_CHAR(STARTDATE,'HH24'),'HH24')";

		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
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
			
			//oracle.finalize();
			db.finalize();

		}
		return retencoes;
	}
	public HashMap<String,Integer> retornaVolumeLigacaoMinuto() {
		
		log.debug("[Dashboard IMS] - " + "iniciando busca por Volume de Ligacoes por Minuto");

		HashMap<String,Integer> volume = new HashMap<String,Integer>();
		ResultSet rs = null;
		String sql = "";
		//OracleConn oracle = null;
		DbConnection db = null;

		sql = "SELECT TO_CHAR(STARTDATE,'HH24:MI') DIA, "+ 
			  "COUNT(1) VOLUME "+
			  "FROM FLOW.LOG WHERE STARTDATE BETWEEN now()-interval '1 hour' AND now() "+
			  "GROUP BY TO_CHAR(STARTDATE,'HH24:MI') "+
			  "ORDER BY TO_TIMESTAMP(TO_CHAR(STARTDATE,'HH24:MI'),'HH24:MI') "; 

		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				volume.put( rs.getString("DIA"), rs.getInt("VOLUME"));
			}

		} catch (Exception e) {
			log.error("[Dashboard Volume Ligacao Minuto] -" + e.getMessage(),e);
			return null;

		} finally {
			//oracle.finalize();
			db.finalize();

		}
		return volume;
	}
	public HashMap<String,Integer> retornaVolumeLigacaoURA() {
		
		log.debug("[Dashboard IMS] - " + "iniciando busca por Volume de Ligacoes por URA");

		HashMap<String,Integer> volume = new HashMap<String,Integer>();
		ResultSet rs = null;
		String sql = "";
		
		DbConnection db = null;

		sql = "select r.description ,count(1) volume "+ 
			  "from flow.router r,flow.log l "+
			  "where l.STARTDATE BETWEEN now()-interval '1 hour' AND now() "+
			  "and l.dnis = r.dnis  "+
			  "group by r.description "+
			  "order by 2 desc "; 

		try {
			//oracle = new OracleConn("IVR_OWNER");
			db= new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				volume.put(rs.getString("description"), rs.getInt("volume"));				
			}

		} catch (Exception e) {
			log.error("[Dashboard Volume Ligacao URA] -" + e.getMessage(),e);
			return null;

		} finally {
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			db.finalize();

		}
		return volume;
	}
	
	public HashMap<String,Integer> getVolumeLigacaoEstado(String minutes) {
		
		log.debug("[Dashboard IMSMap] - " + "iniciando busca por Volume de Ligacoes por Estado");

		HashMap<String,Integer> volume = new HashMap<String,Integer>();
		ResultSet rs = null;
		String sql = "";
		
		DbConnection db = null;

		sql = "select ddd, count(*) qtde from flow.log l where l.startdate > localtimestamp - interval '"+minutes+" minutes' and l.ddd is not null group by ddd "; 

		try {
			db= new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				volume.put(rs.getString("ddd"), rs.getInt("qtde"));				
			}

		} catch (Exception e) {
			log.error("[Dashboard Volume Ligacao Estado] -" + e.getMessage(),e);
			return null;

		} finally {
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			db.finalize();

		}
		return volume;
	}

	
	public HashMap<String,Integer> retornaAcumulado() {
		
		log.debug("[Dashboard IMSMap] - " + "iniciando busca por Volume de Ligacoes por Estado");

		HashMap<String,Integer> volume = new HashMap<String,Integer>();
		ResultSet rs = null;
		String sql = "";
		
		DbConnection db = null;
		
		Calendar ch = Calendar.getInstance();
		Calendar c7 = Calendar.getInstance();
		c7.add(Calendar.HOUR, -7*24);
		Calendar c14 = Calendar.getInstance();
		c14.add(Calendar.HOUR, -14*24);
		
		SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		sql = " select 'Hoje' dia, count(*) qtde from flow.log l where l.startdate between '"+df0.format(ch.getTime())+"' and '"+df.format(ch.getTime())+"'  union all "
			+ " select '-7 dias' dia, count(*) qtde from flow.log l where l.startdate between '"+df0.format(c7.getTime())+"' and '"+df.format(c7.getTime())+"'  union all "
			+ " select '-14 dias' dia, count(*) qtde from flow.log l where l.startdate between '"+df0.format(c14.getTime())+"' and '"+df.format(c14.getTime())+"'  "; 

		try {
			db= new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				volume.put(rs.getString("dia"), rs.getInt("qtde"));				
			}

		} catch (Exception e) {
			log.error("[Dashboard Volume Ligacao Estado] -" + e.getMessage(),e);
			return null;

		} finally {
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			db.finalize();

		}
		return volume;
	}
	
	public HashMap<String,Integer> retornaMenuPrincipalUltimaHora() {
		
		log.debug("[Dashboard IMSMap] - " + "iniciando retornaMenuPrincipalUltimaHora");

		HashMap<String,Integer> volume = new HashMap<String,Integer>();
		ResultSet rs = null;
		String sql = "";
		
		DbConnection db = null;
		
		sql = " Select t.description||' tag: '||tg.tagid opcao, count(*) qtde"
			+ " from flow.tracktag tg "
			+ " left join flow.tag t on t.id=tg.tagid "
			+ " where tg.rowdate between now() - INTERVAL'1 hour' and now() "
			+ " and tg.tagid in (150,152,168,182,184,185,468,187,469,529,529) "
			+ " group by t.description||' tag: '||tg.tagid"; 

		try {
			db= new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				volume.put(rs.getString("opcao"), rs.getInt("qtde"));				
			}

		} catch (Exception e) {
			log.error("[Dashboard retornaMenuPrincipalUltimaHora] -" + e.getMessage(),e);
			return null;

		} finally {
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			db.finalize();

		}
		return volume;
	}	
	
	public HashMap<String,Integer> retornaMenuPrincipalAcumuladoDia() {
		
		log.debug("[Dashboard IMSMap] - " + "iniciando retornaMenuPrincipalAcumuladoDia");

		HashMap<String,Integer> volume = new HashMap<String,Integer>();
		ResultSet rs = null;
		String sql = "";
		
		DbConnection db = null;
		
		sql = " Select t.description||' tag: '||tg.tagid opcao, count(*) qtde"
			+ " from flow.tracktag tg "
			+ " left join flow.tag t on t.id=tg.tagid "
			+ " where tg.rowdate between date_trunc('day',now()) and now() "
			+ " and tg.tagid in (150,152,168,182,184,185,468,187,469,529,529) "
			+ " group by t.description||' tag: '||tg.tagid"; 

		try {
			db= new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				volume.put(rs.getString("opcao"), rs.getInt("qtde"));				
			}

		} catch (Exception e) {
			log.error("[Dashboard retornaMenuPrincipalAcumuladoDia] -" + e.getMessage(),e);
			return null;

		} finally {
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			db.finalize();

		}
		return volume;
	}		

	public String retornaFinalizacao() {
		
		log.debug("[Dashboard IMSMap] - " + "iniciando retornaFinalizacao");

		String volume = "";
		ResultSet rs = null;
		String sql = "";
		
		DbConnection db = null;
		
		sql = " Select X.MINUTO, ROUND(SUM(X.ATH)*100/(SUM(X.ATH)+SUM(X.ABN)+SUM(X.RET)+0.0000001),2) ATH, ROUND(SUM(X.ABN)*100/(SUM(X.ATH)+SUM(X.ABN)+SUM(X.RET)+0.0000001),2) ABN, ROUND(SUM(X.RET)*100/(SUM(X.ATH)+SUM(X.ABN)+SUM(X.RET)+0.0000001),2) RET FROM (Select to_char(l.startdate, 'HH24:MI') MINUTO,sum(case when finalstatus='T' then 1 else 0 end) ATH,sum(case when finalstatus='A' then 1 else 0 end) ABN,sum(case when finalstatus='R' then 1 else 0 end) RET from flow.log l where l.startdate between date_trunc('day',now()) and now()  group by to_char(l.startdate, 'HH24:MI'),finalstatus) X GROUP BY X.MINUTO ORDER BY 1"; 

		try {
			db= new DbConnection("");
			rs = db.ExecuteQuery(sql);
			volume = "[";
			volume += "[\"Minuto\",\"Ath\",\"Abn\",\"Ret\"]";
			while (rs.next()) {
				volume += ",[\""+rs.getString("MINUTO")+"\","+rs.getString("ATH")+","+rs.getString("ABN")+","+rs.getString("RET")+"]";
			}
			volume += "]";
		} catch (Exception e) {
			log.error("[Dashboard retornaFinalizacao] -" + e.getMessage(),e);
			return null;

		} finally {
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			db.finalize();

		}
		return volume;
	}	
	
}
