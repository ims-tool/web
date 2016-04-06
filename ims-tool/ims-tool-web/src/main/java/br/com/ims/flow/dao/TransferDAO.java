package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;
import br.com.ims.flow.model.TransferEntity;
import br.com.ims.flow.model.TransferRuleEntity;

@SuppressWarnings("serial")
public class TransferDAO extends AbstractDAO<TransferEntity>{
	private static TransferDAO instance = null;
	private DbConnection db =  null;
	private TransferDAO() {
		db =  new DbConnection("TransferDAO");
	}
	
	public static TransferDAO getInstance() {
		if(instance == null) {
			instance = new TransferDAO();
		}
		return instance;
	}
	private List<TransferRuleEntity> getTransferRules(String transferId) {
		String sql = "SELECT tr.id tr_id,tr.ordernum tr_ordernum,tr.transferid tr_transferid,tr.condition tr_condition,tr.prompt tr_prompt,tr.number tr_number, "+
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+	                 
	                 "FROM flow.transferrule tr "+
	                 "LEFT JOIN flow.tag t ON tr.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
	                 "WHERE tr.transferid ='"+transferId+"' "+
	                 "ORDER BY tr.ordernum ";
		List<TransferRuleEntity> result = new ArrayList<TransferRuleEntity>();
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				TagEntity tag = null;
				if(rs.getString("t_id") != null && rs.getString("t_id").length() > 0) {
					TagTypeEntity tagType = new TagTypeEntity();
					tagType.setId(rs.getString("tt_id"));
					tagType.setName(rs.getString("tt_name"));
					tagType.setDescription(rs.getString("tt_description"));
					
					tag = new TagEntity();
					tag.setId(rs.getString("t_id"));
					tag.setDescription(rs.getString("t_description"));
					tag.setType(tagType);
				}
				ConditionEntity condition = null;
				if(rs.getString("tr_condition") != null && rs.getString("tr_condition").length() > 0) {
					condition = ServicesFactory.getInstance().getConditionService().get(rs.getString("tr_condition"));
				}
				PromptEntity prompt = null;
				if(rs.getString("tr_prompt") != null && rs.getString("tr_prompt").length() > 0) {
					prompt = ServicesFactory.getInstance().getPromptService().get(rs.getString("tr_prompt"));
				}
				
				TransferRuleEntity transferRule = new TransferRuleEntity();
				transferRule.setId(rs.getString("tr_id"));
				transferRule.setOrderNum(rs.getInt("tr_ordernum"));
				transferRule.setTransferId(rs.getString("tr_transferid"));
				transferRule.setCondition(condition);
				transferRule.setPrompt(prompt);
				transferRule.setTag(tag);
				transferRule.setNumber(rs.getString("tr_number"));
				
				result.add(transferRule);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};
		}
		
		return result;
	}
	
	public List<TransferEntity> getByFilter(String where) {
		String sql = "SELECT tr.id tr_id,tr.name tr_name,tr.description tr_description, "+
				 "t.id t_id, t.description t_description, "+ 
				 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
				 "FROM flow.transfer tr "+
				 "LEFT JOIN flow.tag t ON tr.tag = t.id "+ 
				 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
				 "<WHERE> "+
				 "ORDER BY tr.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<TransferEntity> result = new ArrayList<TransferEntity>();
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				
				TagEntity tag = null;
				if(rs.getString("t_id") != null && rs.getString("t_id").length() > 0) {
					TagTypeEntity tagType = new TagTypeEntity();
					tagType.setId(rs.getString("tt_id"));
					tagType.setName(rs.getString("tt_name"));
					tagType.setDescription(rs.getString("tt_description"));
					
					tag = new TagEntity();
					tag.setId(rs.getString("t_id"));
					tag.setDescription(rs.getString("t_description"));
					tag.setType(tagType);
				}
				
				List<TransferRuleEntity> rules = this.getTransferRules(rs.getString("tr_id"));
				
				
				TransferEntity transfer = new TransferEntity();
				transfer.setId(rs.getString("tr_id"));
				transfer.setName(rs.getString("tr_name"));
				transfer.setDescription(rs.getString("tr_description"));
				transfer.setTransferRules(rules);
				transfer.setTag(tag);
				
				result.add(transfer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};
		}
		
		return result;
	}
	
	public List<TransferEntity> getAll() {
		return this.getByFilter(null);
		
		
	}
	public TransferEntity get(String id) {
		List<TransferEntity> result = this.getByFilter("WHERE tr.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(TransferEntity transfer) {
		boolean result = true;
		String sql = "INSERT INTO flow.transfer (id,name,description,tag,versionid) "+
					 "VALUES ('"+transfer.getId()+"','"+transfer.getName()+"','"+transfer.getDescription()+"',"+
					 (transfer.getTag() ==  null ? "NULL" : transfer.getTag().getId())+",'"+transfer.getVersionId().getId()+"') ";
		             
		result = db.ExecuteSql(sql);
		if(result) {
			for(TransferRuleEntity rule : transfer.getTransferRules()) {
				sql = "INSERT INTO flow.transferrule (id,ordernum,transferid,condition,tag,prompt,number,versionid) "+
					   "VALUES ('"+rule.getId()+"',"+rule.getOrderNum()+",'"+transfer.getId()+"',"
					  + (rule.getCondition() == null ? "NULL" : rule.getCondition().getId())+","
					  + (rule.getTag() == null ? "NULL" : rule.getTag().getId())+","
					  + (rule.getPrompt() == null ? "NULL" : rule.getPrompt().getId())+","
					  + "'"+rule.getNumber()+"','"+transfer.getVersionId().getId()+"')";
					
				result = result & db.ExecuteSql(sql);
				if(!result) {
					//rollback
					sql = "DELETE FROM flow.transferrule WHERE transferid = '"+transfer.getId()+"' ";
					db.ExecuteSql(sql);
					sql = "DELETE FROM flow.transfer WHERE id = '"+transfer.getId()+"' ";
					db.ExecuteSql(sql);
					break;
				}
			}
						
		}
		return result;

	}

	@Override
	public boolean update(TransferEntity transfer) {
		boolean result = true;
		String sql = "UPDATE flow.transfer SET name = '"+transfer.getName()+"',description='"+transfer.getDescription()+"',"
				+ "tag = "+(transfer.getTag() == null ? "NULL" : transfer.getTag().getId())+","
				+ "versionid = "+transfer.getVersionId().getId()+" "
				+ "WHERE id = "+transfer.getId();
		             
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.transferrule WHERE transferid = '"+transfer.getId()+"' ";
			result = db.ExecuteSql(sql);
			if(result) {
				for(TransferRuleEntity rule : transfer.getTransferRules()) {
					sql = "INSERT INTO flow.transferrule (id,ordernum,transferid,condition,tag,prompt,number,versionid) "+
							   "VALUES ('"+rule.getId()+"',"+rule.getOrderNum()+",'"+transfer.getId()+"',"
							  + (rule.getCondition() == null ? "NULL" : rule.getCondition().getId())+","
							  + (rule.getTag() == null ? "NULL" : rule.getTag().getId())+","
							  + (rule.getPrompt() == null ? "NULL" : rule.getPrompt().getId())+","
							  + "'"+rule.getNumber()+"','"+transfer.getVersionId().getId()+"')";
							
						result = result & db.ExecuteSql(sql);
						if(!result) {
							break;
						}
				}
			}
						
		}
		return result;
		
		
	}

	@Override
	public boolean delete(TransferEntity transfer) {
		boolean result = true;
		String sql = "DELETE FROM flow.transferrule WHERE transferid = '"+transfer.getId()+"' ";
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.transfer WHERE id = '"+transfer.getId()+"' ";
		             
			result = db.ExecuteSql(sql);
		}
		return result;
		
	}

}
