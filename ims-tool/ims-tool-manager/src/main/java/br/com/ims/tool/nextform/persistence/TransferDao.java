package br.com.ims.tool.nextform.persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.ims.tool.nextform.model.TransferRuleDto;
import br.com.ims.tool.nextform.util.ConnectionDB;



public class TransferDao {

	public List<TransferRuleDto> getListTransferRule(long transferId) {
		
		List<TransferRuleDto> result = new ArrayList<TransferRuleDto>();
		
		ResultSet rs = null;
		ConnectionDB conn = null;        
        
        try {
			
			conn = new ConnectionDB();
					// REGRAS
			String sql = "SELECT ID, ORDERNUM,TRANSFERID,CONDITION,TAG,PROMPT,NUMBER FROM FLOW.TRANSFERRULE WHERE TRANSFERID="+transferId+" ORDER BY ORDERNUM ";
			
			rs = conn.ExecuteQuery(sql);
			
			while (rs.next()) {
				TransferRuleDto rule = new TransferRuleDto();
				rule.setId(rs.getLong(1));
				rule.setOrder(rs.getInt(2));
				rule.setTransferId(rs.getLong(3));
				rule.setCondition(rs.getLong(4));
				rule.setTag(rs.getLong(5));
				rule.setPrompt(rs.getLong(6));
				rule.setNumber(rs.getString(7));
				
				
				result.add(rule);
				
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			conn.finalize();
		}
		
		
		return result;
	}

}
