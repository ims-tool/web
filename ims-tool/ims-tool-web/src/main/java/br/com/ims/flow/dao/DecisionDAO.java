package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.DecisionChanceEntity;
import br.com.ims.flow.model.DecisionEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

public class DecisionDAO extends AbstractDAO<DecisionEntity>{
	private static DecisionDAO instance = null;
	private DbConnection db =  null;
	private DecisionDAO() {
		db =  new DbConnection("");
	}
	
	public static DecisionDAO getInstance() {
		if(instance == null) {
			instance = new DecisionDAO();
		}
		return instance;
	}
	

	public DecisionChanceEntity getChance(String id) {
		List<DecisionChanceEntity> result = getChancesByFilter("WHERE dc.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	
	private List<DecisionChanceEntity> getChancesByFilter(String where) {
		String sql = "SELECT dc.id dc_id,dc.decisionid dc_decisionid,dc.ordernum dc_ordernum,dc.condition dc_condition,dc.nextformid dc_nextformid, "+
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+	                 
	                 "FROM flow.decisionchance dc "+
	                 "LEFT JOIN flow.tag t ON dc.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
	                 "<WHERE> "+
	                 "ORDER BY dc.ordernum ";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<DecisionChanceEntity> result = new ArrayList<DecisionChanceEntity>();
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
				if(rs.getString("dc_condition") != null && rs.getString("dc_condition").length() > 0) {
					condition = ServicesFactory.getInstance().getConditionService().get(rs.getString("dc_condition"));
				}
				
				DecisionChanceEntity chance = new DecisionChanceEntity();
				chance.setId(rs.getString("dc_id"));
				chance.setDecisionId(rs.getString("dc_decisionid"));
				chance.setOrderNum(rs.getInt("dc_ordernum"));
				chance.setCondition(condition); 
				chance.setNextForm(rs.getString("dc_nextformid"));
				chance.setTag(tag);
								
				result.add(chance);
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
	
	public List<DecisionEntity> getByFilter(String where) {
		String sql = "SELECT d.id d_id,d.name d_name,d.description d_description, "+
				 "t.id t_id, t.description t_description, "+ 
				 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
				 "FROM flow.decision d "+
				 "LEFT JOIN flow.tag t ON d.tag = t.id "+ 
				 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
				 "<WHERE> "+
				 "ORDER BY d.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<DecisionEntity> result = new ArrayList<DecisionEntity>();
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
				
				List<DecisionChanceEntity> chances = this.getChancesByFilter("WHERE dc.decisionid ='"+rs.getString("d_id")+"' " );
				
				
				DecisionEntity decision = new DecisionEntity();
				decision.setId(rs.getString("d_id"));
				decision.setName(rs.getString("d_name"));
				decision.setDescription(rs.getString("d_description"));
				decision.setListDecisionChance(chances);
				decision.setTag(tag);	
				
				result.add(decision);
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
	
	public List<DecisionEntity> getAll() {
		return this.getByFilter(null);
		
		
	}
	public DecisionEntity get(String id) {
		List<DecisionEntity> result = this.getByFilter("WHERE d.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(DecisionEntity entity) {
		boolean result = true;
		String sql = "INSERT INTO flow.decision (id,name,description,tag,versionid) "+
					 "VALUES ('"+entity.getId()+"','"+entity.getName()+"','"+entity.getDescription()+"',"
					 		+(entity.getTag() == null ? "NULL" : entity.getTag().getId())+","
					 		+entity.getVersionId().getId()+")";
				    
		             
		result = db.ExecuteSql(sql);
		if(result) {
			for(DecisionChanceEntity chance : entity.getListDecisionChance()) {
				sql = "INSERT INTO flow.decisionchance (id,decisionid,ordernum,condition,nextformid,tag,versionid) "+
					   "VALUES ('"+chance.getId()+"','"+entity.getId()+"','"+chance.getOrderNum()+"',"+
					   (chance.getCondition() == null ? "NULL" : chance.getCondition().getId())+","+chance.getNextForm()+","+
					   (chance.getTag() == null ? "NULL" : chance.getTag().getId())+","+entity.getVersionId().getId()+") ";
				result = result & db.ExecuteSql(sql);
				if(!result) {
					//rollback
					sql = "DELETE FROM flow.decisionchance WHERE decisionid = '"+entity.getId()+"' ";
					db.ExecuteSql(sql);
					sql = "DELETE FROM flow.decision WHERE id = '"+entity.getId()+"' ";
					db.ExecuteSql(sql);
					break;
				}
			}
						
		}
		return result;

	}

	@Override
	public boolean update(DecisionEntity entity) {
		boolean result = true;
		String sql = "UPDATE flow.decision SET name = '"+entity.getName()+"',description='"+entity.getDescription()+"',"
				+ "tag = "+(entity.getTag() == null ? "NULL" : entity.getTag().getId())+", "
				+ "versionid = "+entity.getVersionId().getVersionId()+" "
				+ "WHERE id = "+entity.getId();
		
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.decisionchance WHERE decisionid = '"+entity.getId()+"' ";
			result = db.ExecuteSql(sql);
			if(result) {
				for(DecisionChanceEntity chance : entity.getListDecisionChance()) {
					sql = "INSERT INTO flow.decisionchance (id,decisionid,ordernum,condition,nextformid,tag,versionid) "+
							   "VALUES ('"+chance.getId()+"','"+entity.getId()+"','"+chance.getOrderNum()+"',"+
							   (chance.getCondition() == null ? "NULL" : chance.getCondition().getId())+","+chance.getNextForm()+","+
							   (chance.getTag() == null ? "NULL" : chance.getTag().getId())+","+entity.getVersionId().getId()+") ";
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
	public boolean delete(DecisionEntity entity) {
		boolean result = true;
		String sql = "DELETE FROM flow.decisionchance WHERE decisionid = '"+entity.getId()+"' ";
		result = db.ExecuteSql(sql);
		if(result) {
			sql = "DELETE FROM flow.decision WHERE id = '"+entity.getId()+"' ";
		             
			result = db.ExecuteSql(sql);
		}
		return result;
		
	}

}
