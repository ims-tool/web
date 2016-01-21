package br.com.gvt.telefonia.ura.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import br.com.gvt.telefonia.ura.diagram.dao.OracleConn;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.NovaUraSingleton;

public class FormReport {
	
	private Map<String,FormInfo> tagReports;
	
	private static FormReport instance;
	
	private FormReport(){
		tagReports = new HashMap<String,FormInfo>();
	}
	
	public static FormReport getInstance()
	{
		if(instance == null)
			instance = new FormReport();
		
		return instance;
			
	}
	
	public void loadFormReport(Form form)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String auxDateIni = "";
		String auxDateFim = "";
		
			if(NovaUraSingleton.getInstance().getTempo() == null)
				return;
			else if(NovaUraSingleton.getInstance().getTempo().isEmpty())
				return;
			String dateIni,dateFim;
			String tempo = NovaUraSingleton.getInstance().getTempo();
			String[] tempoAux;
			if(tempo.split(",").length > 1){
				tempoAux = tempo.split(",");
				dateIni = "TO_DATE('"+tempoAux[0]+":00','DD/MM/YYYY HH24:MI:SS')";
				auxDateIni = tempoAux[0];
				dateFim = "TO_DATE('"+tempoAux[1]+":59','DD/MM/YYYY HH24:MI:SS')";
				auxDateFim = tempoAux[1];
			} else {
				dateIni = "sysdate-"+NovaUraSingleton.getInstance().getTempo()+"/1440";//TO_DATE('06/07/2017 17:32:00','DD/MM/YYYY HH24:MI:SS')
				dateFim = "sysdate";//TO_DATE('06/07/2017 17:59:00','DD/MM/YYYY HH24:MI:SS')+59/86400
				
				try{
					conn = new OracleConn().getConnection(); 
					stmt = conn.prepareStatement("select to_CHAR("+dateIni+",'DD/mm/YYYY HH24:mi:ss') dateIni,to_CHAR("+dateFim+",'DD/mm/YYYY HH24:mi:ss') dateFim from dual");
					rs = stmt.executeQuery();
					
					if(rs != null)
						while(rs.next()){
							auxDateIni = rs.getString("dateIni");
							auxDateFim = rs.getString("dateFim");
							dateIni= "TO_DATE('"+rs.getString("dateIni")+"','DD/MM/YYYY HH24:MI:SS')";
							dateFim= "TO_DATE('"+rs.getString("dateFim")+"','DD/MM/YYYY HH24:MI:SS')";
						}
					
				} catch(Exception e){
					e.getStackTrace();
				}
				
			}
			
			
			
			
			String sql = "SELECT " +
				"ROUND( " +
				"( " +
				"select count(*) " +
				"from ivr_owner.tracktag tg " +
				"where tg.rowdate BETWEEN "+dateIni+" AND "+dateFim+" " +
				"and tg.tagid=to_char(x.TagInterno) " +
				") " +
				"*100/(X.TOTAL+0.00001),2) \"PORCTAG\", " +
				"( " +
				"select count(*) " +
				"from ivr_owner.tracktag tg " +
				"where tg.rowdate BETWEEN "+dateIni+" AND "+dateFim+" " +
				"AND TG.TAGID=to_char(x.TagInterno) " +
				") " +
				"\"TOTALTAG\",X.* " +
				"FROM " +
				"( " +
				"select   " +
				"(select count(*)  " +
				"from ivr_owner.track t " +
				"join ivr_owner.log l on l.id=t.logid " +
				"where t.rowdate BETWEEN "+dateIni+" AND "+dateFim+" " +
				"and t.formid  =f_id) \"TOTAL\", " +
				"f_id, " +
				" f_name,f_type,  " +
				"case when f_type in (4,9) then ft_name||' ('||fwname||')' " +
				"  when f_type = 7 then ft_name||' ('||TransfRule||')' " +
				"else ft_name end \"Tipo Form/flow destino\", " +
				"COname \"Condição\", " +
				"dtmf, opcao_dtmf,  " +
				"to_NextForm, ivr_owner.GETFORM(TO_NEXTFORM) TO_FORM,  " +
				"to_NextFormNOimputMatch, ivr_owner.GETFORM(to_NextFormNOimputMatch) TO_FORM_NOIMPUTMATCH, " +
				"DECODE (F_TYPE,5,DPparamname,OpParamName) DO_ParamName , DECODE (F_TYPE,5,DPparamvalue,OpParamValue) DO_ParamValue, " +
				"DecChild, DECODE (F_TYPE,5,DecId,OpID) DO_ID,  DECODE (F_TYPE,5,DMmethod,OMmethod) DO_Method, " +
				" DECODE (F_TYPE,5,DmID,OmID) DO_MapID ,  DECODE (F_TYPE,5,DgOrder,OpOrder) DO_order, " +
				" DECODE (F_TYPE,5,dgID,OgID) DO_grpId ,  DECODE (F_TYPE,5,DmName,OpName) DO_Name, " +
				" DCdgid,  DCOrder,  DCoperation, DCValue1,   " +
				"Ftag \"TAG-Form\",  " +
				"case when f_type=1 then ANtag " +
				"     when f_type=2 then PCtag " +
				"     when f_type=3 then  Ctag " +
				"     when f_type in (4,9) then FWtag " +
				"     when f_type=5  then DCtag " +
				"     when f_type=6  then Otag " +
				"     when f_type=7  then Ttag " +
				"     when f_type=8  then DTtag " +
				"       end TagInterno,         " +
				"case  when f_type=2 then NI2tag " +
				"     when f_type=3 then  NItag " +
				"       end \"TAG-NimputMatch\" " +
				"from " +
				"( " +
				"select  distinct f.id F_Id, f.name F_name, f.formtype F_Type,ft.name FT_Name, " +
				"case when f.formtype=1 then an.nextform " +
				"     when f.formtype=2 then pc.nextform " +
				"     when f.formtype=3 then  c.nextform " +
				"     when f.formtype in (4,9) then fw.nextform " +
				"     when f.formtype=5  then dc.nextformid " +
				"       when f.formtype=6  then o.nextformid " +
				"       end to_NextForm,       " +
				"dg.DecisionId DecId, dm.id DmID, dg.OrderNum DgOrder,  dg.id dgID, dm.name DmName, " +
				"dc.decisiongroupid DCdgid, dc.ordernum DCOrder, dc.operation DCoperation, dc.value1 DCValue1,  " +
				"dc.decisiongroupchild DecChild, dc.nextformid DecNextForm, dm.methodreference DMmethod, " +
				"dp.paramname DPparamname, dp.paramvalue DPparamvalue, " +
				"og.OperationId OpID, om.id OmID,og.OrderNum OpOrder,  " +
				"og.id OgID, om.name OpName,op.paramname OpParamName, op.paramValue OpParamValue,  " +
				"om.methodreference OMmethod, " +
				"fw.id, fw.flowname fwname,  " +
				"c.dtmf, c.name opcao_dtmf, " +
				"dt.id, dt.name, " +
				"t.id, t.name, t.transferruleid TransfRule,  " +
				"case  when f.formtype=2 then   ni2.nextform " +
				"      when f.formtype=3 then   ni.nextform " +
				"       end to_NextFormNOimputMatch, " +
				"f.tag Ftag,  " +
				"c.tag Ctag, " +
				"ni.tag NItag, " +
				"ni2.tag NI2tag, " +
				"pc.tag PCtag, " +
				"an.tag ANtag, " +
				"t.tag Ttag, " +
				"dt.tag DTtag, " +
				"fw.tag FWtag, " +
				"dc.tag DCtag, " +
				"o.tag Otag, " +
				"co.name COname " +
				"from ivr_owner.form f " +
				"join ivr_owner.formtype ft on ft.id=f.formtype " +
				"left join ivr_owner.announce an on an.id=f.formid and f.formtype=1 " +
				"left join ivr_owner.PromptCollect pc on pc.id=f.formid and f.formtype=2 " +
				"left join ivr_owner.Menu mn on mn.id=f.formid and f.formtype=3         " +
				"left join ivr_owner.NoMatchInput ni on  (mn.NOINPUT=ni.id or mn.NOMATCH=ni.id) " +
				"left join ivr_owner.NoMatchInput ni2 on pc.id=f.formid and  (pc.NOINPUT=ni2.id or pc.NOMATCH=ni2.id) " +
				"left join ivr_owner.choice c on c.menu=mn.id and f.formtype=3 " +
				"left join ivr_owner.Decision d on d.id=f.formid  and f.formtype=5 " +
				"left join ivr_owner.decisiongroup dg on dg.DecisionId=d.id " +
				"left join ivr_owner.DecisionChance dc on dg.id=dc.decisiongroupID " +
				"left join ivr_owner.decisionmap dm on dm.id=dg.decisionMapId " +
				"left join ivr_owner.DecisionParameters dp on dp.decisionGroupId=dg.id  " +
				"left join ivr_owner.Operation o on o.id = f.formid and f.formtype=6 " +
				"left join ivr_owner.OperationGroup og on og.OperationId=o.id " +
				"left join ivr_owner.Operationmap om on om.id=og.OperationMapId " +
				"left join ivr_owner.OperationParameters op on op.OperationGroupId=og.id " +
				"left join ivr_owner.Flow fw on fw.id=f.formid and f.formtype in (4,9) " +
				"left join ivr_owner.Disconnect dt on dt.id=f.formid and f.formtype=8 " +
				"left join ivr_owner.Transfer t on t.id=f.formid and f.formtype=7  " +
				"left join ivr_owner.Condition Co on (co.id = f.condition or co.id=c.condition) " +
				"WHERE UPPER(F.NAME) =UPPER('"+form.getName()+"') " +
				") " +
				"where f_id>900 " +
				") x " +
				"order by f_id, dtmf,DO_order, DCOrder, DO_ID, DO_order ";
			
			
			FormInfo formInfo = null;
			
			try {			
				conn = new OracleConn().getConnection(); 
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				
				if(rs != null){
					while(rs.next()){
						formInfo = new FormInfo();
						formInfo.setFormid(form.getId());
						formInfo.setFormname(form.getName());
						formInfo.setDateIni(auxDateIni);
						formInfo.setDateFim(auxDateFim);
						formInfo.setTag(rs.getString("TAGINTERNO"));
						formInfo.setTotalTag(rs.getInt("TOTALTAG"));
						formInfo.setPorc(rs.getDouble("PORCTAG"));
						tagReports.put(formInfo.getTag(), formInfo);
					}
				}
				
				//System.out.println(tagReports.size());
				
			} catch (Exception e) {
				e.getStackTrace();
			}finally{
				try {
					if(rs != null){
						rs.close();
					}
					if(stmt != null){
						stmt.close();
					}
					if(conn != null){
						conn.close();
					}
				} catch (SQLException e) {
				}
			}
	}
	
	public FormInfo get(String tag){
		return tagReports.get(tag);
	}
}
