package br.com.ims.dashboard.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.dashboard.model.LogUraModel;
import br.com.ims.dashboard.util.DbConnection;


public class LogUraDAO {

	
	Logger log = Logger.getLogger(LogUraDAO.class);

	public String getDnisByName(String name) {
		String result = "";
		log.debug("[LogUra] - " + "getDnisByName("+name+") "); 
		ResultSet rs = null;
		//OracleConn oracle = null;
		DbConnection db = null;
		
		String sql = "SELECT dnis FROM flow.router WHERE formname ='"+name+"' ";
		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
			if(rs.next()) {
				result = rs.getString("dnis");
			}
		} catch (Exception e) {
			log.error("[getDnisByName] -" + e.getMessage(),e);
		} finally {
			
			db.finalize();

		}
		
		return result;
	}
	private boolean isNumber(String str) {
		if(str == null) {
			return false;
		}; 
		return str.matches("-?\\d+");
	}
	public List<LogUraModel> getLogUra(String datahoraI, String datahoraF,String telefone, String dnis, String formulario,String finalizacao, String tags, String vdn ) {
		
		log.debug("[LogUra] - " + "getLogUra");
		
		List<LogUraModel> retorno = new ArrayList<LogUraModel>();
		ResultSet rs = null;
		String sql = "";
		//OracleConn oracle = null;
		DbConnection db = null;
		
		sql = "SELECT /* +rule */  * "+
			"FROM "+
			"	( "+
			"	Select  /* +rule */ DISTINCT "+
			"	 l.id LogID "+
			"	,l.ucid IDENTIFICADOR "+
			"	,l.ani  CHAMADOR "+
			"	,l.instance INSTANCIA "+
			"	,l.document DOCUMENTO "+
			"	, L.PERFIL "+
			"	,L.PROTOCOLID \"SS PROT. INICIAL\" "+
			"	,L.PROTOCOLNUMBER \"PROTOCOLO NUMBER\" "+
			"	,'' \"ARMARIO\" "+
			"	,l.cidade CIDADE "+
			"	,l.uf ESTADO "+
			"	, to_char(l.startdate,'dd/mm/yyyy hh24:mi:ss') \"Data Inicial\", to_char(l.stopdate,'dd/mm/yyyy hh24:mi:ss') \"Data Final\" "+
			"	, l.dnis \"NCHAMADO\" "+
			"	, l.finalstatus FINALIZACAO "+
			"	, TS.RESULT_CALL  \"VDN TRANSFERIDO\" "+
			"	, L.Context CONTEXTO "+
			"	from flow.log l "+
			"	join flow.track t "+
			"	     on t.rowdate BETWEEN TO_TIMESTAMP('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS') AND TO_TIMESTAMP('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+interval '15 minute' "+
			"	    and t.logid=l.id "+
			"	JOIN flow.trackservice ts  "+
			"	   ON ts.rowdate BETWEEN l.startdate-interval '1 minute' AND l.startdate+interval '15 minute' "+ 
			"	   and ts.logid=l.id and TS.TRACKID=T.ID  and ts.method_service = 'Transfer�ncia VDN' "+
			"	where l.startdate BETWEEN TO_TIMESTAMP('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS') AND TO_TIMESTAMP('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+interval '59 seconds' "+
			"	<DNIS> "+
			"	<INSTANCIA> "+ 
			"	<TAG> "+
			"	union all "+
			"	Select  /* + rule */ DISTINCT "+
			"	l.id LogID "+
			"	,l.ucid IDENTIFICADOR "+
			"	,l.ani  CHAMADOR "+ 
			"	,l.instance INSTANCIA "+
			"	,l.document DOCUMENTO "+
			"	, L.PERFIL "+
			"	,L.PROTOCOLID \"SS PROT. INICIAL\" "+
			"	,L.PROTOCOLNUMBER \"PROTOCOLO NUMBER\" "+
			"	,'' \"ARMARIO\" "+
			"	,L.CIDADE CIDADE "+
			"	,L.UF ESTADO "+
			"	, to_char(l.startdate,'dd/mm/yyyy hh24:mi:ss') \"Data Inicial\", to_char(l.stopdate,'dd/mm/yyyy hh24:mi:ss') \"Data Final\" "+
			"	, l.dnis \"NCHAMADO\" "+
			"	, l.finalstatus FINALIZACAO "+
			"	, '' "+
			"	, L.Context CONTEXTO "+
			"	from flow.log l "+
			"	join flow.track t  "+
			"	     on t.rowdate BETWEEN TO_TIMESTAMP('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS') AND TO_TIMESTAMP('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+interval '15 minute' "+
			"	     and t.logid=l.id "+
			"	where l.startdate BETWEEN TO_TIMESTAMP('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS') AND TO_TIMESTAMP('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+interval '59 seconds' "+
			"	<DNIS> "+
			"	<INSTANCIA> "+
			"	AND (l.finalstatus<>'T' or l.finalstatus is null) "+
			"	<TAG> "+ 			
			"	) X "+
			"	WHERE ('"+vdn+"'=\"VDN TRANSFERIDO\" OR UPPER('"+vdn+"')='TODOS') "+
			"	AND (UPPER('"+finalizacao+"')=FINALIZACAO OR UPPER('"+finalizacao+"')='TODOS')  "+
			"	order by 1 ";
		
		if(isNumber(dnis)) {
			sql = sql.replace("<DNIS>", "AND l.DNIS = "+dnis+" ");
		}
		if(isNumber(telefone)) {
			sql = sql.replace("<INSTANCIA>", "AND ( L.ANI LIKE '%"+telefone+"%'  OR l.instance LIKE '%"+telefone+"%') ");
		}
		if(!tags.equalsIgnoreCase("TODOS") && tags.length() > 0) {
			String filter = "and (L.ID IN (SELECT TT1.LOGID  "+
							" 	FROM flow.tracktag tt1  "+
							"	WHERE TT1.ROWDATE BETWEEN TO_TIMESTAMP('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS')-interval '1 minute' AND TO_TIMESTAMP('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+interval '15 minute' "+                                     
							"	and tt1.tagid in("+tags+")   "+
							"	) ) ";
			sql = sql.replace("<TAG>", filter);
		}
		sql = sql.replace("<DNIS>","").replace("<INSTANCIA>", "").replace("<TAG>", "");
		
		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				LogUraModel log = new LogUraModel();
				log.setLogId(rs.getInt("LogID"));
				log.setUcid(rs.getString("IDENTIFICADOR"));
				log.setChamador(rs.getString("CHAMADOR"));
				log.setInstancia(rs.getString("INSTANCIA"));
				log.setDocumento(rs.getString("DOCUMENTO"));
				log.setPerfil(rs.getString("PERFIL"));
				log.setProtocolo_inicial(rs.getString("SS PROT. INICIAL"));
				log.setProtocolo_numero(rs.getString("PROTOCOLO NUMBER"));
				log.setArmario(rs.getString("ARMARIO"));
				log.setCidade(rs.getString("CIDADE"));
				log.setEstado(rs.getString("ESTADO"));
				log.setData_inicial(rs.getString("Data Inicial"));
				log.setData_final(rs.getString("Data Final"));
				log.setNumero_chamado(rs.getString("NCHAMADO"));
				log.setFinalizacao(rs.getString("FINALIZACAO"));
				log.setVdn_transferido(rs.getString("VDN TRANSFERIDO"));
				log.setContexto(rs.getString("CONTEXTO"));
				
				retorno.add(log);				
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
		LogUraModel log = new LogUraModel();
		log.setVdn_transferido("2001033");
		log.setChamador("4130301212");
		log.setCidade("CURITIBA");
		log.setContexto("contextocontextoconloogtextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontexto");
		log.setData_final("20/12/2015 22:20:00");
		log.setData_inicial("25/12/2015 22:00:00");
		log.setDocumento("31131131100");
		log.setEstado("PR");
		log.setFinalizacao("Transferida");
		log.setInstancia("4130301212");
		log.setLogId( BigInteger.valueOf(11111));
		log.setNumero_chamado("414500");
		log.setPerfil("cliente");
		log.setProtocolo_inicial("xpto");
		log.setProtocolo_numero("111");
		log.setUcid("000011111111");
		
		
		retorno.add(log);
		
		log = new LogUraModel();
		//log.setVdn_transferido("2001033");
		log.setChamador("4130301313");
		log.setCidade("CURITIBA");
		log.setContexto("contextocontextoconloogtextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontexto");
		log.setData_final("20/12/2015 22:10:00");
		log.setData_inicial("25/12/2015 22:15:00");
		log.setDocumento("96096096011");
		log.setEstado("PR");
		log.setFinalizacao("Abandonada");
		log.setInstancia("4130301313");
		log.setLogId( BigInteger.valueOf(11111));
		log.setNumero_chamado("414500");
		log.setPerfil("cliente");
		log.setProtocolo_inicial("ouol");
		log.setProtocolo_numero("222");
		log.setUcid("000022222222");
		
		log = new LogUraModel();
		//log.setVdn_transferido("2001033");
		log.setChamador("4135351010");
		log.setCidade("CURITIBA");
		log.setContexto("contextocontextoconloogtextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontextocontexto");
		log.setData_final("20/12/2015 22:16:00");
		log.setData_inicial("25/12/2015 22:20:00");
		log.setDocumento("058058058000");
		log.setEstado("PR");
		log.setFinalizacao("Retida");
		log.setInstancia("4135351010");
		log.setLogId( BigInteger.valueOf(11111));
		log.setNumero_chamado("414500");
		log.setPerfil("cliente");
		log.setProtocolo_inicial("abc");
		log.setProtocolo_numero("333");
		log.setUcid("0000333333333");
		
		
		retorno.add(log);
		
		
		/**
		 * Fim mock
		 */
		return retorno;
	}
	

	

}
