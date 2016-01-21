package br.com.gvt.telefonia.ura.diagram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Entity;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.Reflection;
import br.com.gvt.telefonia.ura.util.SingletonDAO;
import br.com.gvt.telefonia.ura.util.Util;

public class FormDAO extends DAO<Form>{
	
	
	public List<Form> parentForms(String id)
	{
			String formname = SingletonDAO.getFormDAOInstance().findByPk(id).getName();
			
			String sql = "SELECT * from " +
					"( " +
					"select   DISTINCT ivr_owner.GETFORMREFER('"+formname+"',F_ID) ORDEM, " +
					"f_id,  f_name, " +
					"case when f_type in (4,9) then ft_name||' ('||fwname||')' " +
					"  when f_type = 7 then ft_name||' ('||TransfRule||')' " +
					"else ft_name end Tipo " +
					"from " +
					"( " +
					" " +
					" " +
					"select   f.id F_Id, f.name F_name, f.formtype F_Type,ft.name FT_Name, " +
					"case when f.formtype=1 then an.nextform " +
					"     when f.formtype=2 then pc.nextform " +
					"     when f.formtype=3 then  c.nextform " +
					"     when f.formtype in (4,9) then fw.nextform " +
					"     when f.formtype=5  then dc.nextformid " +
					"       when f.formtype=6  then o.nextformid " +
					"       end to_NextForm, " +
					"        " +
					"dg.DecisionId DecId, dm.id DmID, dg.OrderNum DgOrder,  dg.id dgID, dm.name DmName, " +
					"dc.decisiongroupid DCdgid, dc.ordernum DCOrder, dc.operation DCoperation, dc.value1 DCValue1,  " +
					"dc.decisiongroupchild DecChild, dc.nextformid DecNextForm, dm.methodreference DMmethod, " +
					"dp.paramname DPparamname, dp.paramvalue DPparamvalue, " +
					" " +
					"og.OperationId OpID, om.id OmID,og.OrderNum OpOrder,  " +
					"og.id OgID, om.name OpName,op.paramname OpParamName, op.paramValue OpParamValue,  " +
					"om.methodreference OMmethod, " +
					" " +
					"fw.id, fw.flowname fwname,  " +
					" " +
					"c.dtmf, c.name opcao_dtmf, " +
					" " +
					"dt.id, dt.name, " +
					" " +
					"t.id, t.name, t.transferruleid TransfRule,  " +
					"        " +
					"case  when f.formtype=2 then   ni2.nextform " +
					"      when f.formtype=3 then   ni.nextform " +
					"       end to_NextFormNOimputMatch, " +
					"        " +
					"      " +
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
					" " +
					"co.name COname " +
					", F.VERSIONID F_VERSIONID " +
					", f.nextformdefault F_NextFormDefault " +
					"        " +
					"from ivr_owner.form f " +
					"join ivr_owner.formtype ft on ft.id=f.formtype " +
					"left join ivr_owner.announce an on an.id=f.formid and f.formtype=1 " +
					"left join ivr_owner.PromptCollect pc on pc.id=f.formid and f.formtype=2 " +
					"left join ivr_owner.Menu mn on mn.id=f.formid and f.formtype=3         " +
					"left join ivr_owner.NoMatchInput ni on  (mn.NOINPUT=ni.id or mn.NOMATCH=ni.id) " +
					"left join ivr_owner.NoMatchInput ni2 on pc.id=f.formid and  (pc.NOINPUT=ni2.id or pc.NOMATCH=ni2.id) " +
					"left join ivr_owner.choice c on c.menu=mn.id and f.formtype=3  " +
					"left join ivr_owner.Decision d on d.id=f.formid  and f.formtype=5  " +
					"left join ivr_owner.decisiongroup dg on dg.DecisionId=d.id " +
					"left join ivr_owner.DecisionChance dc on dg.id=dc.decisiongroupID " +
					"left join ivr_owner.decisionmap dm on dm.id=dg.decisionMapId " +
					"left join ivr_owner.DecisionParameters dp on dp.decisionGroupId=dg.id  " +
					"left join ivr_owner.Operation o on o.id = f.formid and f.formtype=6  " +
					"left join ivr_owner.OperationGroup og on og.OperationId=o.id " +
					"left join ivr_owner.Operationmap om on om.id=og.OperationMapId " +
					"left join ivr_owner.OperationParameters op on op.OperationGroupId=og.id " +
					"left join ivr_owner.Flow fw on fw.id=f.formid and f.formtype in (4,9) " +
					"left join ivr_owner.Disconnect dt on dt.id=f.formid and f.formtype=8 " +
					"left join ivr_owner.Transfer t on t.id=f.formid and f.formtype=7  " +
					"left join ivr_owner.Condition Co on (co.id = f.condition or co.id=c.condition) " +
					"where f.id in " +
					"( " +
					"select IDForm from " +
					"( " +
					"select   1 key,             " +
					"case when f.formtype=1 then an.nextform  " +
					"     when f.formtype=2 then pc.nextform  " +
					"     when f.formtype=3 then  c.nextform " +
					"     when f.formtype in (4,9) then fw.nextform " +
					"     when f.formtype=5  then dc.nextformid " +
					"     when f.formtype=6  then o.nextformid " +
					"       end IDForm      " +
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
					" " +
					"where upper(f.name)=upper('"+formname+"') " +
					"union " +
					"select 0 key, f1.id " +
					"from ivr_owner.form f1 " +
					" " +
					"where upper(f1.name)=upper('"+formname+"') " +
					"union " +
					"select 2 key, ni.nextform " +
					"from ivr_owner.form f1 " +
					" " +
					"join ivr_owner.formtype ft on ft.id=f1.formtype " +
					"left join ivr_owner.PromptCollect pc on pc.id=f1.formid and f1.formtype=2 " +
					"left join ivr_owner.Menu mn on mn.id=f1.formid and f1.formtype=3       " +
					"left join ivr_owner.NoMatchInput ni on  (mn.NOINPUT=ni.id or mn.NOMATCH=ni.id) " +
					"where upper(f1.name)=upper('"+formname+"') " +
					"union " +
					"select 2 key, ni2.nextform " +
					"from ivr_owner.form f1 " +
					" " +
					"join ivr_owner.formtype ft on ft.id=f1.formtype " +
					"left join ivr_owner.PromptCollect pc on pc.id=f1.formid and f1.formtype=2 " +
					"left join ivr_owner.Menu mn on mn.id=f1.formid and f1.formtype=3       " +
					"left join ivr_owner.NoMatchInput ni2 on pc.id=f1.formid and  (pc.NOINPUT=ni2.id or pc.NOMATCH=ni2.id) " +
					"where upper(f1.name)=upper('"+formname+"') " +
					"union " +
					"select -1 key, ReferId " +
					"from " +
					"( " +
					"select * from " +
					"( " +
					"select f.id ReferId,               " +
					"case when f.formtype=1 then an.nextform " +
					"     when f.formtype=2 then pc.nextform " +
					"     when f.formtype=3 then  c.nextform " +
					"     when f.formtype in (4,9) then fw.nextform " +
					"     when f.formtype=5  then dc.nextformid " +
					"     when f.formtype=6  then o.nextformid " +
					"       end nextID      " +
					"from ivr_owner.form f " +
					"join ivr_owner.formtype ft on ft.id=f.formtype " +
					"left join ivr_owner.announce an on an.id=f.formid and f.formtype=1 " +
					"left join ivr_owner.PromptCollect pc on pc.id=f.formid and f.formtype=2 " +
					"left join ivr_owner.Menu mn on mn.id=f.formid and f.formtype=3         " +
					"left join ivr_owner.NoMatchInput ni on  (mn.NOINPUT=ni.id or mn.NOMATCH=ni.id) " +
					"left join ivr_owner.NoMatchInput ni2 on pc.id=f.formid and  (pc.NOINPUT=ni2.id or pc.NOMATCH=ni2.id) " +
					"left join ivr_owner.choice c on c.menu=mn.id and f.formtype=3  " +
					"left join ivr_owner.Decision d on d.id=f.formid  and f.formtype=5  " +
					"left join ivr_owner.decisiongroup dg on dg.DecisionId=d.id " +
					"left join ivr_owner.DecisionChance dc on dg.id=dc.decisiongroupID " +
					"left join ivr_owner.decisionmap dm on dm.id=dg.decisionMapId " +
					"left join ivr_owner.DecisionParameters dp on dp.decisionGroupId=dg.id  " +
					"left join ivr_owner.Operation o on o.id = f.formid and f.formtype=6  " +
					"left join ivr_owner.OperationGroup og on og.OperationId=o.id " +
					"left join ivr_owner.Operationmap om on om.id=og.OperationMapId " +
					"left join ivr_owner.OperationParameters op on op.OperationGroupId=og.id " +
					"left join ivr_owner.Flow fw on fw.id=f.formid and f.formtype in (4,9) " +
					"left join ivr_owner.Disconnect dt on dt.id=f.formid and f.formtype=8 " +
					"left join ivr_owner.Transfer t on t.id=f.formid and f.formtype=7  " +
					"left join ivr_owner.Condition Co on (co.id = f.condition or co.id=c.condition) " +
					" " +
					"union " +
					"select f1.id, ni.nextform ReferId " +
					"from ivr_owner.form f1 " +
					"join ivr_owner.formtype ft on ft.id=f1.formtype " +
					"left join ivr_owner.PromptCollect pc on pc.id=f1.formid and f1.formtype=2 " +
					"left join ivr_owner.Menu mn on mn.id=f1.formid and f1.formtype=3       " +
					"left join ivr_owner.NoMatchInput ni on  (mn.NOINPUT=ni.id or mn.NOMATCH=ni.id) " +
					" " +
					"union " +
					"select f1.id, ni2.nextform ReferId " +
					"from ivr_owner.form f1 " +
					"join ivr_owner.formtype ft on ft.id=f1.formtype " +
					"left join ivr_owner.PromptCollect pc on pc.id=f1.formid and f1.formtype=2 " +
					"left join ivr_owner.Menu mn on mn.id=f1.formid and f1.formtype=3       " +
					"left join ivr_owner.NoMatchInput ni2 on pc.id=f1.formid and  (pc.NOINPUT=ni2.id or pc.NOMATCH=ni2.id) " +
					" " +
					"union " +
					"select F.ID, (select f3.id from ivr_owner.form f3 where f3.name=fw.flowname) ReferId " +
					"from ivr_owner.FORM F " +
					"join ivr_owner.flow fw ON f.formid=fw.id  " +
					"WHERE F.FORMTYPE IN (9) " +
					" " +
					") r " +
					"where r.nextID = ( " +
					"select f1.id " +
					"from ivr_owner.form f1 " +
					"where upper(f1.name)=upper('"+formname+"'))  " +
					") " +
					" " +
					") " +
					" " +
					") " +
					" " +
					") " +
					"where f_id > 900 " +
					") " +
					"order by 1, 2, 3 ";
			
			
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			List<Form> result = new ArrayList<Form>(); 
			
			try {			
				conn = new OracleConn().getConnection(); 
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				
				
				if(rs != null){
					while(rs.next()){
						if(rs.getInt("ORDEM") < 0)
							result.add(SingletonDAO.getInstance().getFormDAOInstance().findByPk(rs.getString("F_ID")));
					}
				}
				
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
			
			return result;
	}
	
	
	public Form findByFormId(String form){
		
		return findBy(" formid = '"+form+"' ");
	}
	
	public Form findByFormId(String form,String type){
		
		int formType = Util.getFormType(type);
		return findBy(" formid = '"+form+"' and formtype = '"+formType+"' ");
	}
	
	public List<Form> findAll()
	{
		Form obj = null;
		List<Form> result = new ArrayList<Form>();
		
		String sql = "select * from ivr_owner.form order by name asc";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			
			if(rs != null){
				while(rs.next()){
					obj = new Form();
					obj.setId(rs.getLong("id"));
					obj.setName(rs.getString("name"));
					obj.setDescription(rs.getString("description"));
					obj.setFormType(rs.getLong("formtype"));
					obj.setFormid(rs.getLong("formid"));
					obj.setNextFormDefault(rs.getLong("nextformdefault"));
					result.add(obj);
				}
			}
			
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
		
		return result;
	}
	
	public String insert(Form entity)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String sql = "insert into ivr_owner."+getClassName().toLowerCase()+" values (IVR_OWNER.GET_UID,";
		sql += "'" + entity.getName()+"',";
		sql += "'" + entity.getDescription()+"',";
		sql += "'" + entity.getFormType()+"',";
		sql += "'" + entity.getFormid()+"',";
		sql += "'" + entity.getTag()+"',";
		sql += "'" + entity.getCondition()+"',";
		sql += "'" + entity.getNextFormDefault()+"',";
		sql += "'" + entity.getVersionid()+"'";
		sql += ")";
		
		System.out.println(sql);
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			stmt.executeQuery();
			
		} catch (Exception e) {
			e.getStackTrace();
			return "";
		}
		
		return lastInsertId();
	}
	
	public void update(Form entity)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String formId = Reflection.invokeGetMethod(entity, "id");
		
		String sql = "update ivr_owner."+getClassName().toLowerCase()+" set ";
		sql += "name = '" + entity.getName()+"',";
		sql += "description = '" + entity.getDescription()+"',";
		sql += "formtype  = '" + entity.getFormType()+"',";
		sql += "formid = '" + entity.getFormid()+"',";
		sql += "tag = '" + entity.getTag()+"',";
		sql += "condition = '" + entity.getCondition()+"',";
		sql += "nextformdefault = '" + entity.getNextFormDefault()+"',";
		sql += "versionid = '" + entity.getVersionid()+"'";
		sql += " where id = '"+formId+"'";
		
		System.out.println(sql);
		try {			
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			stmt.executeQuery();
			
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	public String save(Form entity)
	{
		String result = "";
		try{
			
			if(Long.parseLong(Reflection.invokeGetMethod(entity, "id")) > 0)
			{
				result = Reflection.invokeGetMethod(entity, "id");
				update(entity);
			}else
				result = insert(entity);
			
		} catch(Exception e)
		{
			e.getStackTrace();
		}
		
		return result;
	}


	@Override
	public String getClassName() {
		return Form.class.getSimpleName();
	}


	public Entity<?> searchByName(String name) {
		return findBy(" UPPER(name) like UPPER('%"+name+"%') ");
	}
	

}
