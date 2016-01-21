package br.com.gvt.telefonia.ura.diagram.facade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import br.com.gvt.telefonia.ura.diagram.dao.MenuDAO;
import br.com.gvt.telefonia.ura.diagram.dao.OracleConn;
import br.com.gvt.telefonia.ura.diagram.model.Menu;
import br.com.gvt.telefonia.ura.util.Reflection;

public class FacadePromptCollect {

	public boolean save(Object obj,String parent, String parentType)
	{
		Menu instanceParent = null;
		MenuDAO menuDAO = new MenuDAO();
		instanceParent = menuDAO.findByPk(parent);
		
		HashMap<String,Object> values = Reflection.getAll(obj);
		
		String params = "";
		params+= "'" + values.get("vName") + "',";
		params+= "'" + values.get("vDescription") + "',";
		params+= "'" + values.get("vGrammar") + "',";
		params+= "'" + values.get("vFlushPrompt") + "',";
		params+= "'" + values.get("vTypeAudio") + "',";
		params+= "'" + values.get("vAudioPath") + "',";
		params+= "'" + values.get("vAudio1") + "',";
		params+= "'" + values.get("vAudio2") + "',";
		params+= "'" + values.get("vAudio3") + "',";
		params+= "'" + values.get("vAudio4") + "',";
		params+= "'" + values.get("vAudio5") + "',";
		params+= "'" + values.get("vAudio6") + "',";
		params+= "'" + values.get("vAudio7") + "',";
		params+= "'" + values.get("vAudio8") + "',";
		params+= "'" + values.get("vAudio9") + "',";
		params+= "'" + values.get("vAudio0") + "',";
		params+= "'" + values.get("vNoImputPrompt") + "',";
		params+= "'" + values.get("vNoMatchPrompt") + "',";    
		
		String sql = "select PROMPTCOLLECTADD("+params+") as id from dual";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		long id = 0;
		
		try {		
			OracleConn oracle = new OracleConn();
			conn = new OracleConn().getConnection(); 
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
						id = rs.getLong("id");
				}
				
				if(id > 0)
				{
					oracle.ExecuteQuery("update "+parentType+" set nextform = '"+id+"' where id = '"+parent+"'");
				}
				System.out.println(id);
			}
			
			if(rs != null)
				return true;
		} catch(Exception e){
			e.getStackTrace();
		}
		
		return false;
	}
}
