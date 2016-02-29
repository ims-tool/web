package br.com.ims.dashboard.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.dashboard.model.LogUraModel;
import br.com.ims.dashboard.util.OracleConn;


public class LogUraDAO {

	
	Logger log = Logger.getLogger(LogUraDAO.class);

	
	
	public List<LogUraModel> getLogUra(String datahoraI, String datahoraF,String telefone, String dnis, String formulario,String finalizacao, String tags, String vdn ) {
		
		log.debug("[LogUra] - " + "getLogUra");
		
		List<LogUraModel> retorno = new ArrayList<LogUraModel>();
		ResultSet rs = null;
		String sql = "";
		OracleConn oracle = null;
		
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
			"	,'' \"ARMÁRIO\" "+
			"	,l.cidade CIDADE "+
			"	,l.uf ESTADO "+
			"	, to_char(l.startdate,'dd/mm/yyyy hh24:mi:ss') \"Data Inicial\", to_char(l.stopdate,'dd/mm/yyyy hh24:mi:ss') \"Data Final\" "+
			"	, l.dnis \"Nº CHAMADO\" "+
			"	, l.finalstatus FINALIZACAO "+
			"	, TS.RESULT_CALL  \"VDN TRANSFERIDO\" "+
			"	, L.Context CONTEXTO "+
			"	from log l "+
			"	join form f on  (upper(f.name) = upper('"+dnis+"') OR upper('"+dnis+"') ='TODOS') "+
			"	join track t "+
			"	     on t.rowdate BETWEEN TO_DATE('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS') AND TO_DATE('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+15/1440 "+
			"	    and t.logid=l.id and t.formid=f.id "+
			"	JOIN trackservice ts  "+
			"	   ON ts.rowdate BETWEEN l.startdate-1/1440 AND l.startdate+15/1440 "+ 
			"	   and ts.logid=l.id and TS.TRACKID=T.ID  and ts.method_service = 'Transferência VDN' "+
			"	where l.startdate BETWEEN TO_DATE('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS') AND TO_DATE('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+59/86400 "+
			"	AND ('"+dnis+"' = l.DNIS OR '"+dnis+"' = 'TODOS') "+
			"	AND ( L.ANI LIKE '%"+telefone+"%'  OR l.instance LIKE '%"+telefone+"%'  OR upper('"+telefone+"')='TODOS') "+ 
			"	and (L.ID IN (SELECT TT1.LOGID  "+
			"	       FROM tracktag tt1  "+
			"	       WHERE TT1.ROWDATE BETWEEN TO_DATE('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS')-1/1440 AND TO_DATE('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+15/1440 "+                                     
			"	             and tt1.tagid in("+tags+") "+
			"	             ) OR "+tags+"=0) "+
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
			"	,'' \"ARMÁRIO\" "+
			"	,L.CIDADE CIDADE "+
			"	,L.UF ESTADO "+
			"	, to_char(l.startdate,'dd/mm/yyyy hh24:mi:ss') \"Data Inicial\", to_char(l.stopdate,'dd/mm/yyyy hh24:mi:ss') \"Data Final\" "+
			"	, l.dnis \"Nº CHAMADO\" "+
			"	, l.finalstatus FINALIZACAO "+
			"	, '' "+
			"	, L.Context CONTEXTO "+
			"	from log l "+
			"	join form f on  (upper(f.name) = upper('"+formulario+"') OR upper('"+formulario+"') ='TODOS') "+
			"	join track t  "+
			"	     on t.rowdate BETWEEN TO_DATE('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS') AND TO_DATE('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+15/1440 "+
			"	     and t.logid=l.id and t.formid=f.id  "+
			"	where l.startdate BETWEEN TO_DATE('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS') AND TO_DATE('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+59/86400 "+
			"	AND ('"+dnis+"' = l.DNIS OR '"+dnis+"' = 'TODOS') "+
			"	AND ( L.ANI LIKE '%"+telefone+"%'  OR l.instance LIKE '%"+telefone+"%'  OR upper('"+telefone+"')='TODOS')  "+
			"	AND (l.finalstatus<>'T' or l.finalstatus is null) "+
			"	and (L.ID IN (SELECT TT1.LOGID  "+
			"	       FROM tracktag tt1  "+
			"	       WHERE TT1.ROWDATE BETWEEN TO_DATE('"+datahoraI+"','DD/MM/YYYY HH24:MI:SS')-1/1440 AND TO_DATE('"+datahoraF+"','DD/MM/YYYY HH24:MI:SS')+15/1440 "+                                     
			"	             and tt1.tagid in("+tags+")   "+
			"	             ) OR "+tags+"=0) "+ 			
			"	) X "+
			"	WHERE ('"+vdn+"'=\"VDN TRANSFERIDO\" OR UPPER('"+vdn+"')='TODOS') "+
			"	AND (UPPER('"+finalizacao+"')=FINALIZACAO OR UPPER('"+finalizacao+"')='TODOS')  "+
			"	order by 1 "; 
		
		try {
			oracle = new OracleConn("IVR_OWNER");
			rs = oracle.ExecuteQuery(sql);
			
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
				log.setArmario(rs.getString("ARMÁRIO"));
				log.setCidade(rs.getString("CIDADE"));
				log.setEstado(rs.getString("ESTADO"));
				log.setData_inicial(rs.getString("Data Inicial"));
				log.setData_final(rs.getString("Data Final"));
				log.setNumero_chamado(rs.getString("Nº CHAMADO"));
				log.setFinalizacao(rs.getString("FINALIZACAO"));
				log.setVdn_transferido(rs.getString("VDN TRANSFERIDO"));
				log.setContexto(rs.getString("CONTEXTO"));
				
				retorno.add(log);				
			}

		} catch (Exception e) {
			log.error("[Dashboard Volume Ligacao Minuto] -" + e.getMessage(),e);
			return null;

		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			oracle.finalize();

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
