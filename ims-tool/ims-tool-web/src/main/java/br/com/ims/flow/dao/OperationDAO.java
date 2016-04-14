package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.OperationEntity;
import br.com.ims.flow.model.OperationGroupEntity;
import br.com.ims.flow.model.OperationMapEntity;
import br.com.ims.flow.model.OperationParameterEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class OperationDAO extends AbstractDAO<OperationEntity>{
	private static OperationDAO instance = null;
	//private DbConnection db =  null;
	private OperationDAO() {
		//db =  new DbConnection("OperationDAO");
	}
	
	public static OperationDAO getInstance() {
		if(instance == null) {
			instance = new OperationDAO();
		}
		return instance;
	}
	private List<OperationParameterEntity> getOperationParameters(String operationGroupId) {
		String sql = "SELECT op.id op_id,op.operationgroupid op_operationgroupid,op.paramname op_paramname,op.paramvalue op_paramvalue, op.versionid op_versionid "+
				 "FROM flow.operationparameters op "+
                "WHERE op.operationgroupid ='"+operationGroupId+"' ";
		List<OperationParameterEntity> result = new ArrayList<OperationParameterEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("OperationDAO-getOperationParameters");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				OperationParameterEntity op = new 	OperationParameterEntity();
				op.setId(rs.getString("op_id"));
				op.setOperationGroupId(rs.getString("op_operationgroupid"));
				op.setParamName(rs.getString("op_paramname"));
				op.setParamValue(rs.getString("op_paramvalue"));
				op.setVersionId(rs.getString("op_versionid"));
				result.add(op);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.finalize();
		}
		
		return result;
	}
	private List<OperationGroupEntity> getOperationGroups(String operationId) {
		String sql = "SELECT og.id og_id,og.operationid og_operationid,og.ordernum og_ordernum,og.description og_description, og.versionid og_versionid ,"+
					 "om.id om_id, om.name om_name,om.description om_description, om.methodreference om_methodreference, om.log_active om_log_active "+
					 "FROM flow.operationgroup og "+
	                 "INNER JOIN flow.operationmap om ON og.operationmapid = om.id "+ 
	                 "WHERE og.operationid ='"+operationId+"' "+
	                 "ORDER BY og.ordernum ";
		List<OperationGroupEntity> result = new ArrayList<OperationGroupEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("OperationDAO-getOperationGroups");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				OperationMapEntity operationMap = new OperationMapEntity();
				operationMap.setId(rs.getString("om_id"));
				operationMap.setName(rs.getString("om_name"));
				operationMap.setDescription(rs.getString("om_description"));
				operationMap.setMethodReference(rs.getString("om_methodreference"));
				operationMap.setLogActive(rs.getInt("om_log_active"));
				
				List<OperationParameterEntity> op = this.getOperationParameters(rs.getString("og_id"));
				
				OperationGroupEntity og = new OperationGroupEntity();
				og.setId(rs.getString("og_id"));
				og.setOperationId(rs.getString("og_operationid"));
				og.setOrderNum(rs.getInt("og_ordernum"));
				og.setDescription(rs.getString("og_description"));
				og.setOperationMap(operationMap);
				og.setListOperationParameters(op);
				og.setVersionId(rs.getString("og_versionid"));
								
				result.add(og);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.finalize();
		}
		
		return result;
	}
	
	public List<OperationEntity> getByFilter(String where) {
		String sql = "SELECT o.id o_id,o.name o_name,o.description o_description,o.nextformid o_nextformid,o.versionid o_versionid, "+
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
		List<OperationEntity> result = new ArrayList<OperationEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("OperationDAO-getByFilter");
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
				
				List<OperationGroupEntity> groups = this.getOperationGroups(rs.getString("o_id"));
				
				
				OperationEntity operation = new OperationEntity();
				operation.setId(rs.getString("o_id"));
				operation.setName(rs.getString("o_name"));
				operation.setDescription(rs.getString("o_description"));
				operation.setNextForm(rs.getString("o_nextformid"));
				operation.setListOperationGroup(groups);
				operation.setTag(tag);	
				operation.setVersionId(rs.getString("o_versionid"));
				 
				
				result.add(operation);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			db.finalize();
		}
		
		return result;
	}
	
	public List<OperationEntity> getAll() {
		return this.getByFilter(null);
		
		
	}
	public OperationEntity get(String id) {
		List<OperationEntity> result = this.getByFilter("WHERE o.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(OperationEntity entity) {
		boolean result = true;
		String sql = "INSERT INTO flow.operation (id,name,description,tag,nextformid,versionid) "+
					 "VALUES ('"+entity.getId()+"','"+entity.getName()+"','"+entity.getDescription()+"',"
					 		+(entity.getTag() == null ? "NULL" : entity.getTag().getId())+","
					 		+ entity.getNextForm()+","
					 		+entity.getVersionId()+")";
				    
		DbConnection db = new DbConnection("OperationDAO-save");
		try{
			result = db.ExecuteSql(sql);
			if(result) {
				for(OperationGroupEntity og : entity.getListOperationGroup()) {
					
					
					sql = "INSERT INTO flow.operationgroup (id,operationid,ordernum,operationmapid,description, versionid) "+
						   "VALUES ('"+og.getId()+"','"+entity.getId()+"','"+og.getOrderNum()+"','"+og.getOperationMap().getId()+"','"+og.getDescription()+"','"+entity.getVersionId()+"')";
						   
						   
					result = result & db.ExecuteSql(sql);
					if(!result) {
						//rollback
						sql = "DELETE FROM flow.operationgroup WHERE operationid = '"+entity.getId()+"' ";
						db.ExecuteSql(sql);
						sql = "DELETE FROM flow.operation WHERE id = '"+entity.getId()+"' ";
						db.ExecuteSql(sql);
						return result;
					} else {
						for(OperationParameterEntity op : og.getListOperationParameters()) {
							sql = "INSERT INTO flow.operationparameters (id,operationgroupid,paramname,paramvalue,versionid) "+
									   "VALUES ('"+op.getId()+"','"+og.getId()+"','"+op.getParamName()+"','"+op.getParamValue()+"','"+entity.getVersionId()+"')";
							result = result & db.ExecuteSql(sql);
							if(!result) {
								//rollback
								sql = "DELETE FROM flow.operationparameters WHERE operationgroupid in (SELECT id FROM flow.operationgroup WHERE operationid = '"+entity.getId()+"' )  ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.operationgroup WHERE operationid = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.operation WHERE id = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								return result;
							}
						}
						
						
					}
				}
							
			}
		} finally {
			db.finalize();
		}
		return result;

	}

	@Override
	public boolean update(OperationEntity entity) {
		boolean result = true;
		String sql = "UPDATE flow.operation SET name = '"+entity.getName()+"',description = '"+entity.getDescription()+"',"
				   + "tag = "+(entity.getTag() == null ? "NULL"  : entity.getTag().getId())+",nextformid='"+entity.getNextForm()+"',"
				   + "versionid  =  '"+entity.getVersionId()+"' "
				   + "WHERE id = "+entity.getId();
		DbConnection db = new DbConnection("OperationDAO-update");
		try {
			result = db.ExecuteSql(sql);
			if(result) {
				sql = "DELETE FROM flow.operationparameters WHERE operationgroupid in (SELECT id FROM flow.operationgroup WHERE operationid = '"+entity.getId()+"' )  ";
				
				result = db.ExecuteSql(sql);
				if(!result) {
					return result;
				}
				sql = "DELETE FROM flow.operationgroup WHERE operationid = '"+entity.getId()+"' ";			
				result = db.ExecuteSql(sql);
				
				if(!result) {
					return result;
				}
				
				for(OperationGroupEntity og : entity.getListOperationGroup()) {
					
					
					sql = "INSERT INTO flow.operationgroup (id,operationid,ordernum,operationmapid,description, versionid) "+
						   "VALUES ('"+og.getId()+"','"+entity.getId()+"','"+og.getOrderNum()+"','"+og.getOperationMap().getId()+"','"+og.getDescription()+"','"+entity.getVersionId()+"')";
						   
						   
					result = result & db.ExecuteSql(sql);
					if(!result) {					
						return result;
					} else {
						for(OperationParameterEntity op : og.getListOperationParameters()) {
							sql = "INSERT INTO flow.operationparameters (id,operationgroupid,paramname,paramvalue,versionid) "+
									   "VALUES ('"+op.getId()+"','"+og.getId()+"','"+op.getParamName()+"','"+op.getParamValue()+"','"+entity.getVersionId()+"')";
							result = result & db.ExecuteSql(sql);
							if(!result) {							
								return result;
							}
						}
						
						
					}
				}
							
			}
		} finally {
			db.finalize();
		}
		return result;
		
	}

	@Override
	public boolean delete(OperationEntity entity) {
		boolean result = true;
		String sql = "DELETE FROM flow.operationparameters WHERE operationgroupid in (SELECT id FROM flow.operationgroup WHERE operationid = '"+entity.getId()+"' )  ";
		DbConnection db = new DbConnection("OperationDAO-delete");
		try {
			result = db.ExecuteSql(sql);
			if(!result)
				return result;
			sql = "DELETE FROM flow.operationgroup WHERE operationid = '"+entity.getId()+"' ";
			result = db.ExecuteSql(sql);
			if(!result)
				return result;
			sql = "DELETE FROM flow.operation WHERE id = '"+entity.getId()+"' ";
			result = db.ExecuteSql(sql);
		} finally {
			db.finalize();
		}
		return result;
		
		
		
	}

}
