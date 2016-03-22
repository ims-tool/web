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
import br.com.ims.flow.model.OperationEntity;
import br.com.ims.flow.model.OperationGroupEntity;
import br.com.ims.flow.model.OperationMapEntity;
import br.com.ims.flow.model.OperationParameterEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

public class OperationDAO extends AbstractDAO<OperationEntity>{
	private static OperationDAO instance = null;
	private DbConnection db =  null;
	private OperationDAO() {
		db =  new DbConnection("");
	}
	
	public static OperationDAO getInstance() {
		if(instance == null) {
			instance = new OperationDAO();
		}
		return instance;
	}
	private List<OperationParameterEntity> getOperationParameters(String operationGroupId) {
		String sql = "SELECT op.id op_id,op.operationgroupid op_operationgroupid,op.paramname op_paramname,op.paramvalue op_paramvalue "+
				 "FROM flow.operationparameters op "+
                "WHERE op.operationgroupid ='"+operationGroupId+"' ";
		List<OperationParameterEntity> result = new ArrayList<OperationParameterEntity>();
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				OperationParameterEntity op = new 	OperationParameterEntity();
				op.setId(rs.getString("op_id"));
				op.setOperationGroupId(rs.getString("op_operationgroupid"));
				op.setParamName(rs.getString("op_paramname"));
				op.setParamValue(rs.getString("op_paramvalue"));
				result.add(op);
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
	private List<OperationGroupEntity> getOperationGroups(String operationId) {
		String sql = "SELECT og.id og_id,og.operationid og_operationid,og.ordernum og_ordernum,og.description og_description,og.versionid og_versionid, "+
					 "om.id om_id, om.name om_name,om.description om_description, om.methodreference om_methodreference, om.versionid om_versioid,om.log_active om_log_active "+
					 "FROM flow.operationgroup og "+
	                 "INNER JOIN flow.operationmap om ON og.operationmapid = om.id "+ 
	                 "WHERE og.operationid ='"+operationId+"' "+
	                 "ORDER BY og.ordernum ";
		List<DecisionChanceEntity> result = new ArrayList<DecisionChanceEntity>();
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				OperationMapEntity operationMap = new OperationMapEntity();
				operationMap.setId(rs.getString("om_id"));
				operationMap.setName(rs.getString("om_name"));
				//continuar aqui
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
					condition = ServicesFactory.getInstance().getConditionService().get(rs.getString("c_condition"));
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
	
	public List<OperationEntity> getByFilter(String where) {
		String sql = "SELECT o.id o_id,o.name o_name,o.description o_description,o.nextformid o_nextformid, "+
				 "t.id t_id, t.description t_description, "+ 
				 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
				 "FROM flow.operation o "+
				 "LEFT JOIN flow.tag t ON o.tag = t.id "+ 
				 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
				 "<WHERE> "+
				 "ORDER BY o.name";
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
				
				List<DecisionChanceEntity> chances = this.getChances(rs.getString("d_id"));
				
				
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
		List<DecisionEntity> result = this.getByFilter("WHERE d.id = "+id);
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
					   "VALUES ('"+chance.getId()+"','"+chance.getDecisionId()+"','"+chance.getOrderNum()+"',"+
					   (chance.getCondition() == null ? "NULL" : chance.getCondition().getId())+","+chance.getNextForm()+","+
					   (chance.getTag() == null ? "NULL" : chance.getTag().getId())+","+chance.getVersionId().getId()+") ";
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
							   "VALUES ('"+chance.getId()+"','"+chance.getDecisionId()+"','"+chance.getOrderNum()+"',"+
							   (chance.getCondition() == null ? "NULL" : chance.getCondition().getId())+","+chance.getNextForm()+","+
							   (chance.getTag() == null ? "NULL" : chance.getTag().getId())+","+chance.getVersionId().getId()+") ";
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
