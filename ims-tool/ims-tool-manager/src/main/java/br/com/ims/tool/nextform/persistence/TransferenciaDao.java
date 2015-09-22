package br.com.ims.tool.nextform.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import br.com.ims.tool.nextform.model.TransferenciaIn;
import br.com.ims.tool.nextform.model.TransferenciaOut;
import br.com.ims.tool.nextform.model.TransferenciaRegras;
import br.com.ims.tool.nextform.util.ConnectionDB;



public class TransferenciaDao {

	public TransferenciaOut getTransferencia(TransferenciaIn transferenciaIn) {
		TransferenciaOut transferenciaOut = new TransferenciaOut();
		
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
        
        
        
		Hashtable<String,String> vTransferenciaIn = new Hashtable<String,String>();
		Hashtable<Integer,TransferenciaRegras> vRegras = new Hashtable<Integer,TransferenciaRegras>();
		Hashtable<String,String> vSaidas = new Hashtable<String,String>();
        String sql = "";
        String vLog = "";
        int id = 0;
        String vdnPar = "";
        String logPar = "";
        String msgPar = "";
		try {
			
			conn = new ConnectionDB();
			
			// Parse Parametros
			String[] rec = transferenciaIn.getParametros().split(";");
			for (int i = 0; i < rec.length; i++) {
				try {
					String[] x = rec[i].split("=");
					if(x[0].equalsIgnoreCase("VDN")){
						vdnPar = x[1].toUpperCase();
					}
					if(x[0].equalsIgnoreCase("LOG")){
						logPar = x[1].toUpperCase();
					}
					if(x[0].equalsIgnoreCase("MSG")){
						msgPar = x[1].toUpperCase();
					}
					vTransferenciaIn.put(x[0].toUpperCase(), x[1].toUpperCase());
				} catch (Exception e) {
				}
			}

			// REGRAS
			sql += " SELECT tr.logroot, tr.regragrupo, tr.regra, tr.filho, tr.log, tc.nome, tc.campo, tc.tipo, tr.operacao, tr.valor1, tr.valor2, tr.valor3, tr.valor4, tr.valor5, tr.valor6, tr.valor7, tr.valor8, tr.valor9, tr.valor10, tr.saida ";
			sql += " FROM URA.TRANSFERENCIA_REGRAS TR LEFT JOIN URA.TRANSFERENCIA_CAMPOS TC ON TC.ID = TR.CAMPO WHERE LOGROOT = '"+transferenciaIn.getPontoLog()+"' ";
			sql += " ORDER BY REGRAGRUPO, REGRA ";
			
			stm = conn.getPreparedStatement(sql);
			rs = stm.executeQuery();
			
			while (rs.next()) {
				vRegras.put(id++, new TransferenciaRegras(rs.getString("logroot"), rs.getString("regragrupo"), rs.getString("regra"), rs.getString("filho"), rs.getString("log"), rs.getString("nome"), rs.getString("campo"), rs.getString("tipo"), rs.getString("operacao"), rs.getString("valor1"), rs.getString("valor2"), rs.getString("valor3"), rs.getString("valor4"), rs.getString("valor5"), rs.getString("valor6"), rs.getString("valor7"), rs.getString("valor8"), rs.getString("valor9"), rs.getString("valor10"), rs.getString("saida")));
			}
			
			// PROCESSAMENTO
			boolean vAchou = false;
			boolean vOK = false;
			String vSaida = "-1";
			String vRegraGrupo = "1";
			int vRegra = 0;
			
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			while (!vAchou  && vRegra < vRegras.size()) {
				if (vRegraGrupo.equals(vRegras.get(vRegra).getRegragrupo())) {
					if (!vRegras.get(vRegra).getOperacao().isEmpty()) {
						String vAspa = vRegras.get(vRegra).getTipo().equals("NUMERO")?"":"'";
						String vValor = vTransferenciaIn.get(vRegras.get(vRegra).getNome());
						if (vRegras.get(vRegra).getTipo().equals("SQL") && !vTransferenciaIn.containsKey(vRegras.get(vRegra).getNome())) {							
							sql = vRegras.get(vRegra).getCampo();
							while (sql.indexOf("{") > 0) {
								int i = sql.indexOf("{");
								int f = sql.indexOf("}")+1;
								String key = sql.substring(i+1, f-1);
								try {
									sql = sql.replace(sql.substring(i,f), vTransferenciaIn.get(key));
								} catch (Exception e) {
									sql = sql.replace(sql.substring(i,f), "");
								}
							}		
							//rs.close();

							stm = conn.getPreparedStatement(sql);
							rs = stm.executeQuery();
							if (rs.next()) {
								vValor = rs.getString(1);
								vTransferenciaIn.put(vRegras.get(vRegra).getNome().toUpperCase(),vValor.toUpperCase());
							}
						}
						if (vRegras.get(vRegra).getOperacao().equals("IN")) {
							Set<String> valores = new HashSet<String>(Arrays.asList(new String[] {vRegras.get(vRegra).getValor1(),vRegras.get(vRegra).getValor2(),vRegras.get(vRegra).getValor3(),vRegras.get(vRegra).getValor4(),vRegras.get(vRegra).getValor5(),vRegras.get(vRegra).getValor5(),vRegras.get(vRegra).getValor6(),vRegras.get(vRegra).getValor7(),vRegras.get(vRegra).getValor8(),vRegras.get(vRegra).getValor9(),vRegras.get(vRegra).getValor10()}));
							vOK = valores.contains(vValor);
						} else if (vRegras.get(vRegra).getOperacao().equals("BETWEEN")) {
							vOK = (Boolean)engine.eval(vRegras.get(vRegra).getValor1()+" <= "+vValor+" && "+vValor+" <="+vRegras.get(vRegra).getValor2());
						} else { 
							vOK = (Boolean)engine.eval(vAspa+vValor+vAspa+" "+vRegras.get(vRegra).getOperacao()+" "+vAspa+vRegras.get(vRegra).getValor1()+vAspa);
						}
					} else {
						vOK = true;
					}

					if (vOK) {
						vLog += (vRegras.get(vRegra).getLog().isEmpty()?"":vRegras.get(vRegra).getLog()+"|");
						vSaida = vRegras.get(vRegra).getSaida();
						vAchou = vRegras.get(vRegra).getFilho().isEmpty() && !vRegras.get(vRegra).getSaida().isEmpty(); 
						if (!vAchou) {
							vRegraGrupo = vRegras.get(vRegra).getFilho().isEmpty()?vRegraGrupo:vRegras.get(vRegra).getFilho();
						}
					}
				}
				vRegra++;
			}
			//rs.close();
			// SAIDAS
			sql = " SELECT TSP.NOME PARAM_NAME, TS.PARAM_VALUE FROM URA.TRANSFERENCIA_SAIDAS TS JOIN URA.TRANSFERENCIA_SAIDAS_PAR TSP ON TSP.ID = TS.PARAM_NAME WHERE TS.ID = " + vSaida;
			
			stm = conn.getPreparedStatement(sql);
			rs = stm.executeQuery();
			
			while (rs.next()) {
				vSaidas.put(rs.getString("PARAM_NAME"), rs.getString("PARAM_VALUE"));
			}			
			
			try{transferenciaOut.setLog(vLog+vSaidas.get("LOG").toString()+"{PontoLog: "+transferenciaIn.getPontoLog()+" / IdSaida: "+vSaida+"}");}catch(Exception e){transferenciaOut.setLog(vLog+"-1");}
			try{transferenciaOut.setVdn(vSaidas.get("VDN").toString());}catch(Exception e){transferenciaOut.setVdn("");}
			try{transferenciaOut.setMsg(vSaidas.get("MSG").toString());}catch(Exception e){transferenciaOut.setMsg("");}
			try{transferenciaOut.setExtra("");}catch(Exception e){transferenciaOut.setExtra("");}
		} catch (Exception e) {
			transferenciaOut.setLog("-2");
			transferenciaOut.setVdn("");
			transferenciaOut.setMsg("");
			transferenciaOut.setExtra("");
		}  finally {
			try {stm.close();} catch (SQLException e) {e.printStackTrace();}
			conn.finalize();
		}
		
		//TESTE ENTRADAS JA COM PARAMETROS DE SAIDA
		try {
			if(vdnPar != ""){
				transferenciaOut.setVdn(vdnPar);
			}
			if(msgPar != ""){
				transferenciaOut.setMsg(msgPar);
			}
			if(logPar != ""){
				transferenciaOut.setLog(logPar);
			}
		} catch (Exception e) {
			transferenciaOut.setLog("-3");
			transferenciaOut.setVdn("");
			transferenciaOut.setMsg("");
			transferenciaOut.setExtra("");
		}
		
		return transferenciaOut;
	}

}
