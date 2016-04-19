package br.com.ims.dashboard.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.dashboard.model.WsVolumeModel;
import br.com.ims.dashboard.util.DbConnection;


public class WsVolumeDAO {

	
	Logger log = Logger.getLogger(WsVolumeDAO.class);

	
	
	public List<WsVolumeModel> getWsVolume(String datahoraI, String datahoraF,String limite_timeout, String num_chamadas_minima ) {
		
		log.debug("[WsVolumeDaAO] - " + "getWsVolume");
		
		List<WsVolumeModel> retorno = new ArrayList<WsVolumeModel>();
		ResultSet rs = null;
		String sql = "";
		//OracleConn oracle = null;
		DbConnection db = null;
		
		sql = "SELECT ID, METHOD_SERVICE, CHAMADAS_SERVICO, TOTAL_TIMEOUT "+
				", ROUND(TME*100/TIME_OUT,2) PERCENT_USOU_TIMEOUT "+
				", PERCENT_COM_TIMEOUT "+
				", CASE WHEN PERCENT_COM_TIMEOUT >= "+limite_timeout+" THEN 'NOK' ELSE 'OK' END STATUS "+
				"FROM "+
				"( "+
				"	Select ID, METHOD_SERVICE,MAX(TOTAL_CHAMADAS) CHAMADAS_SERVICO,MAX(TIMEOUT_PICO),MAX(TIMEOUT) TIME_OUT "+
				"	,ROUND(AVG(TIME_EXEC),3) TME "+
				"	, SUM(CASE WHEN TIME_EXEC>=TIMEOUT THEN 1 ELSE 0 END) TOTAL_TIMEOUT "+
				"	, ROUND(SUM(CASE WHEN TIME_EXEC>=TIMEOUT THEN 1 ELSE 0 END)*100/MAX(TOTAL_CHAMADAS),2) PERCENT_COM_TIMEOUT "+
				"	FROM "+
				"	( "+
				"		Select "+ 
				"		C.ID, ts.method_service "+
				"		, count(*) over (partition by C.ID,ts.method_service) TOTAL_CHAMADAS "+
				"		, MAX(TIME_EXEC) over (partition by C.ID,ts.method_service) TIMEOUT_PICO "+
				"		, C.TIMEOUT TIMEOUT "+
				"		, TS.TIME_EXEC "+
				"		from flow.trackservice ts "+
				"		JOIN flow.CONTROLPANEL C ON C.METHODNAME=TS.METHOD_SERVICE "+
				"		where ts.rowdate BETWEEN TO_DATE('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS') AND  TO_DATE('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS') "+
				"	) A "+
				"	GROUP BY  ID, METHOD_SERVICE "+
				") A "+
				"WHERE PERCENT_COM_TIMEOUT >-1 "+
				"AND CHAMADAS_SERVICO > "+num_chamadas_minima+" "+
				"order by 2,1; "; 
				 
		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				
				WsVolumeModel ws = new WsVolumeModel();
				
				ws.setId(rs.getInt("ID"));				
				ws.setMethod_service(rs.getString("METHOD_SERVICE") );
				ws.setChamadas_servico(rs.getString("CHAMADAS_SERVICO"));
				ws.setPercent_com_timeout(rs.getString("TOTAL_TIMEOUT"));
				ws.setPercent_usou_timeout(rs.getString("PERCENT_USOU_TIMEOUT"));
				ws.setTotal_timeout(rs.getString("PERCENT_COM_TIMEOUT"));
				ws.setStatus(rs.getString("STATUS"));
				
				retorno.add(ws);			
			}

		} catch (Exception e) {
			log.error("[Dashboard Volume Ligacao Minuto] -" + e.getMessage(),e);
			return null;

		} finally {
			db.finalize();

		}
		
		/**
		 * Mock
		 *
		WsVolumeModel ws = new WsVolumeModel();
		
		ws.setId(BigInteger.valueOf(1));
		ws.setMethod_service("getCustomer");
		ws.setChamadas_servico("1000");
		ws.setPercent_com_timeout("10");
		ws.setPercent_usou_timeout("10");
		ws.setTotal_timeout("100");
		ws.setStatus("NOK");
		
		retorno.add(ws);
		
		ws = new WsVolumeModel();
		
		ws.setId(BigInteger.valueOf(1));
		ws.setMethod_service("getBill");
		ws.setChamadas_servico("200");
		ws.setPercent_com_timeout("0");
		ws.setPercent_usou_timeout("0");
		ws.setTotal_timeout("0");
		ws.setStatus("OK");
		
		retorno.add(ws);
		
		ws = new WsVolumeModel();
		
		ws.setId(BigInteger.valueOf(1));
		ws.setMethod_service("getDocument");
		ws.setChamadas_servico("1000");
		ws.setPercent_com_timeout("1");
		ws.setPercent_usou_timeout("1");
		ws.setTotal_timeout("10");
		ws.setStatus("OK");
		
		retorno.add(ws);
		
		
		/**
		 * Fim mock
		 */
		return retorno;
	}
	

	

}
