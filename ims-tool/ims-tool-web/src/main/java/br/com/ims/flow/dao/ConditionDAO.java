package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.ConditionGroupEntity;
import br.com.ims.flow.model.ConditionMapEntity;
import br.com.ims.flow.model.ConditionParameterEntity;
import br.com.ims.flow.model.ConditionValueEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class ConditionDAO extends AbstractDAO<ConditionEntity> {
	//private DbConnection db =  null;
	public static Logger log = Logger.getLogger(ConditionDAO.class);
	private static ConditionDAO instance = null;
	private ConditionDAO() {
		//db =  new DbConnection("ConditionDAO"); 			
	}
	
	public static ConditionDAO getInstance() {
		if(instance == null) {
			instance = new ConditionDAO();
		}
		return instance;
	}
	
	private List<ConditionParameterEntity> getConditionParameters(String conditionGroupId) {
		log.debug("getConditionParameters("+conditionGroupId+")");
		String sql = "SELECT cp.id cp_id,cp.conditiongroupid cp_conditiongroupid,cp.paramname cp_paramname,cp.paramvalue cp_paramvalue, cp.versionid cp_versionid "+
				 "FROM flow.conditionparameters cp "+
               "WHERE cp.conditiongroupid ='"+conditionGroupId+"' ";
		List<ConditionParameterEntity> result = new ArrayList<ConditionParameterEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("ConditionDAO-getConditionParameters");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				ConditionParameterEntity cp = new 	ConditionParameterEntity();
				cp.setId(rs.getString("cp_id"));
				cp.setConditionGroupId(rs.getString("cp_conditiongroupid"));
				cp.setParamName(rs.getString("cp_paramname"));
				cp.setParamValue(rs.getString("cp_paramvalue"));
				cp.setVersionId(rs.getString("cp_versionid"));
				result.add(cp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
			/*try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};*/
		}
		
		return result;
	}
	private List<ConditionValueEntity> getConditionValues(String conditionGroupId) {
		log.debug("getConditionValues("+conditionGroupId+")");
		String sql = "SELECT cv.id cv_id,cv.conditiongroupid cv_conditiongroupid,cv.ordernum cv_ordernum,cv.operation cv_operation,cv.versionid cv_versionid, "
				    + "cv.value1 cv_value1, cv.value2 cv_value2,cv.value3 cv_value3,cv.value4 cv_value4,cv.value5 cv_value5,"
				    + "cv.value6 cv_value6, cv.value7 cv_value7,cv.value8 cv_value8,cv.value9 cv_value9,cv.value10 cv_value10,"
				    + "tr.id tr_id, tr.description tr_description, "
				    + "ttr.id ttr_id, ttr.name ttr_name,ttr.description ttr_description, "
				    + "tf.id tf_id, tf.description tf_description, "
				    + "ttf.id ttf_id, ttf.name ttf_name,ttf.description ttf_description "
				    + "FROM flow.conditionvalue cv "
				    + "LEFT JOIN flow.tag tr ON cv.tagtrue = tr.id "
					+ "LEFT JOIN flow.tagtype ttr ON tr.tagtypeid = ttr.id "
					+ "LEFT JOIN flow.tag tf ON cv.tagfalse = tf.id "
					+ "LEFT JOIN flow.tagtype ttf ON tf.tagtypeid = ttf.id "
                    + "WHERE cv.conditiongroupid ='"+conditionGroupId+"' ";
		List<ConditionValueEntity> result = new ArrayList<ConditionValueEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("ConditionDAO-getConditionValues");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				
				TagEntity tagTrue = null;
				if(rs.getString("tr_id") != null && rs.getString("tr_id").length() > 0) {
					TagTypeEntity tagType = new TagTypeEntity();
					tagType.setId(rs.getString("ttr_id"));
					tagType.setName(rs.getString("ttr_name"));
					tagType.setDescription(rs.getString("ttr_description"));
					
					tagTrue = new TagEntity();
					tagTrue.setId(rs.getString("tr_id"));
					tagTrue.setDescription(rs.getString("tr_description"));
					tagTrue.setType(tagType);
				}
				TagEntity tagFalse = null;
				if(rs.getString("tf_id") != null && rs.getString("tf_id").length() > 0) {
					TagTypeEntity tagType = new TagTypeEntity();
					tagType.setId(rs.getString("ttf_id"));
					tagType.setName(rs.getString("ttf_name"));
					tagType.setDescription(rs.getString("ttf_description"));
					
					tagFalse = new TagEntity();
					tagFalse.setId(rs.getString("tf_id"));
					tagFalse.setDescription(rs.getString("tf_description"));
					tagFalse.setType(tagType);
				}
				
				ConditionValueEntity cv = new 	ConditionValueEntity();
				cv.setId(rs.getString("cv_id"));
				cv.setConditionGroupId(rs.getString("cv_conditiongroupid"));
				cv.setOrderNum(rs.getInt("cv_ordernum"));
				cv.setOperation(rs.getString("cv_operation"));
				cv.setValue1(rs.getString("cv_value1"));
				cv.setValue2(rs.getString("cv_value2"));
				cv.setValue3(rs.getString("cv_value3"));
				cv.setValue4(rs.getString("cv_value4"));
				cv.setValue5(rs.getString("cv_value5"));
				cv.setValue6(rs.getString("cv_value6"));
				cv.setValue7(rs.getString("cv_value7"));
				cv.setValue8(rs.getString("cv_value8"));
				cv.setValue9(rs.getString("cv_value9"));
				cv.setValue10(rs.getString("cv_value10"));
				cv.setTagTrue(tagTrue);
				cv.setTagFalse(tagFalse);
				cv.setVersionId(rs.getString("cv_versionid"));
				
				
				result.add(cv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
			/*
			try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};*/
		}
		
		return result;
	}
	
	public List<ConditionGroupEntity> getConditionGroups(String where,boolean lazy) {
		log.debug("getConditionGroups("+where+","+lazy+")");
		String sql = "SELECT cg.id cg_id,cg.conditionid cg_conditionid,cg.ordernum cg_ordernum,cg.description cg_description,cg.versionid cg_versionid, "+
				 "cm.id cm_id, cm.name cm_name,cm.description cm_description, cm.type cm_type, cm.methodreference cm_methodreference, cm.log_active cm_log_active "+
				 "FROM flow.conditiongroup cg "+
                "INNER JOIN flow.conditionmap cm ON cg.conditionmapid = cm.id "+ 
                "<WHERE> "+
                "ORDER BY cg.ordernum ";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<ConditionGroupEntity> result = new ArrayList<ConditionGroupEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("ConditionDAO-getConditionGroups");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				ConditionMapEntity conditionMap = new ConditionMapEntity();
				conditionMap.setId(rs.getString("cm_id"));
				conditionMap.setName(rs.getString("cm_name"));
				conditionMap.setDescription(rs.getString("cm_description"));
				conditionMap.setType(rs.getString("cm_type"));
				conditionMap.setMethodReference(rs.getString("cm_methodreference"));
				conditionMap.setLogActive(rs.getInt("cm_log_active"));
				
				List<ConditionParameterEntity> cp = new ArrayList<ConditionParameterEntity>() ;
				List<ConditionValueEntity> cv = new ArrayList<ConditionValueEntity>();
				if(!lazy) {
					cp.addAll(this.getConditionParameters(rs.getString("cg_id")));
					cv.addAll(this.getConditionValues(rs.getString("cg_id")));
				}
				
				
				ConditionGroupEntity cg = new ConditionGroupEntity();
				cg.setId(rs.getString("cg_id"));
				cg.setConditionId(rs.getString("cg_conditionid"));
				cg.setOrderNum(rs.getInt("cg_ordernum"));
				cg.setDescription(rs.getString("cg_description"));
				cg.setConditionMap(conditionMap);
				cg.setListConditionParameters(cp);
				cg.setListConditionValues(cv);
				cg.setVersionId(rs.getString("cg_versionid"));
				result.add(cg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
			/*try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};*/
		}
		
		return result;			
	}
	public List<ConditionEntity> getByFilter(String where) {
		return this.getByFilter(where, false);
	}
	public List<ConditionEntity> getByFilter(String where,boolean lazy) {
		log.debug("getByFilter("+where+","+lazy+")");
		String sql = "SELECT c.id c_id,c.name c_name,c.description c_description,c.versionid c_versionid, "+
				 "t.id t_id, t.description t_description, "+ 
				 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
				 "FROM flow.condition c "+
				 "LEFT JOIN flow.tag t ON c.tag = t.id "+ 
				 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
				 "<WHERE> "+
				 "ORDER BY c.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<ConditionEntity> result = new ArrayList<ConditionEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("ConditionDAO-getByFilter");
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
				
				List<ConditionGroupEntity> groups = new ArrayList<ConditionGroupEntity>();
				if(!lazy) {
					groups.addAll(this.getConditionGroups("WHERE cg.conditionid ='"+rs.getString("c_id")+"' ",lazy));
				}
				
				
				ConditionEntity condition = new ConditionEntity();
				condition.setId(rs.getString("c_id"));
				condition.setName(rs.getString("c_name"));
				condition.setDescription(rs.getString("c_description"));
				condition.setListConditionGroup(groups);
				condition.setTag(tag);	
				condition.setVersionId(rs.getString("c_versionid")); 
				
				result.add(condition);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
		/*	try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};*/
		}
		
		return result;
	}
	public List<ConditionEntity> getAll() {
		
		return this.getByFilter(null);
		
	}
	public List<ConditionEntity> getAll(boolean lazy) {
		
		return this.getByFilter(null,lazy);
		
	}
	public ConditionEntity get(String id) {
		List<ConditionEntity> result = this.getByFilter("WHERE c.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public ConditionEntity get(String id,boolean lazy) {
		List<ConditionEntity> result = this.getByFilter("WHERE c.id = '"+id+"'",lazy);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public ConditionEntity getByName(String name) {
		List<ConditionEntity> result = this.getByFilter("WHERE lower(c.name) = '"+name.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(ConditionEntity entity) {
		boolean result = true;
		log.debug("save()");
		String sql = "INSERT INTO flow.condition (id,name,description,tag,versionid) "+
					 "VALUES ('"+entity.getId()+"','"+entity.getName()+"','"+entity.getDescription()+"',"
					 		+(entity.getTag() == null ? "NULL" : entity.getTag().getId())+","
					 		+entity.getVersionId()+")";
		DbConnection db = new DbConnection("ConditionDAO-save");
		try {
			result = db.ExecuteSql(sql);
			if(result) {
				for(ConditionGroupEntity cg : entity.getListConditionGroup()) {
					
					
					sql = "INSERT INTO flow.conditiongroup (id,conditionid,ordernum,conditionmapid,description, versionid) "+
						   "VALUES ('"+cg.getId()+"','"+entity.getId()+"','"+cg.getOrderNum()+"','"+cg.getConditionMap().getId()+"','"+cg.getDescription()+"','"+entity.getVersionId()+"')";
						   
						   
					result = result & db.ExecuteSql(sql);
					if(!result) {
						//rollback
						sql = "DELETE FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' ";
						db.ExecuteSql(sql);
						sql = "DELETE FROM flow.condition WHERE id = '"+entity.getId()+"' ";
						db.ExecuteSql(sql);
						return result;
					} else {
						for(ConditionParameterEntity cp : cg.getListConditionParameters()) {
							sql = "INSERT INTO flow.conditionparameters (id,conditiongroupid,paramname,paramvalue,versionid) "+
									   "VALUES ('"+cp.getId()+"','"+cg.getId()+"','"+cp.getParamName()+"','"+cp.getParamValue()+"','"+entity.getVersionId()+"')";
							result = result & db.ExecuteSql(sql);
							if(!result) {
								//rollback
								sql = "DELETE FROM flow.conditionparameters WHERE conditiongroupid in (SELECT id FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' )  ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.condition WHERE id = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								return result;
							}
						}
						for(ConditionValueEntity cv : cg.getListConditionValues()) {
							sql = "INSERT INTO flow.conditionvalue (id,conditiongroupid,ordernum,operation,"
									+ "value1,value2,value3,value4,value5,value6,value7,value8,value9,value10,tagtrue,tagfalse,versionid) "
									+ "VALUES ('"+cv.getId()+"','"+cg.getId()+"','"+cv.getOrderNum()+"','"+cv.getOperation()+"','"+cv.getValue1()+"',"
									+ (cv.getValue2() == null || cv.getValue2().length() == 0? "NULL" : "'"+cv.getValue2()+"'")+","
									+ (cv.getValue3() == null || cv.getValue3().length() == 0? "NULL" : "'"+cv.getValue3()+"'")+","
									+ (cv.getValue4() == null || cv.getValue4().length() == 0? "NULL" : "'"+cv.getValue4()+"'")+","
									+ (cv.getValue5() == null || cv.getValue5().length() == 0? "NULL" : "'"+cv.getValue5()+"'")+","
									+ (cv.getValue6() == null || cv.getValue6().length() == 0? "NULL" : "'"+cv.getValue6()+"'")+","
									+ (cv.getValue7() == null || cv.getValue7().length() == 0? "NULL" : "'"+cv.getValue7()+"'")+","
									+ (cv.getValue8() == null || cv.getValue8().length() == 0? "NULL" : "'"+cv.getValue8()+"'")+","
									+ (cv.getValue9() == null || cv.getValue9().length() == 0? "NULL" : "'"+cv.getValue9()+"'")+","
									+ (cv.getValue10() == null || cv.getValue10().length() == 0? "NULL" : "'"+cv.getValue10()+"'")+","
									+ (cv.getTagTrue() == null ? "NULL" : cv.getTagTrue().getId())+","
									+ (cv.getTagFalse() == null ? "NULL" : cv.getTagFalse().getId())+",'"+entity.getVersionId()+"') ";
							result = result & db.ExecuteSql(sql);
							if(!result) {
								//rollback
								sql = "DELETE FROM flow.conditionparameters WHERE conditiongroupid in (SELECT id FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' )  ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.conditionvalue WHERE conditiongroupid in (SELECT id FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' )  ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.condition WHERE id = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								return result;
							}
							
						}
						
					}
				}
				Util.audit(entity, Constants.AUDIT_TYPE_ADD);			
			}
		} finally {
			db.finalize();
			
		}
		return result;
	}

	@Override
	public boolean update(ConditionEntity entity) {
		boolean result = true;
		log.debug("update()");
		String sql = "UPDATE flow.condition SET name = '"+entity.getName()+"',description = '"+entity.getDescription()+"',"
				   + "tag = "+(entity.getTag() == null ? "NULL"  : entity.getTag().getId())+","
				   + "versionid  =  '"+entity.getVersionId()+"' "
				   + "WHERE id = "+entity.getId();
		DbConnection db = new DbConnection("ConditionDAO-update");
		try {
			result = db.ExecuteSql(sql);
			if(result) {
				sql = "DELETE FROM flow.conditionparameters WHERE conditiongroupid in (SELECT id FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' )  ";
				
				result = db.ExecuteSql(sql);
				if(!result) {
					return result;
				}
				sql = "DELETE FROM flow.conditionvalue WHERE conditiongroupid in (SELECT id FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' )  ";
				
				result = db.ExecuteSql(sql);
				if(!result) {
					return result;
				}
				sql = "DELETE FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' ";			
				result = db.ExecuteSql(sql);
				
				if(!result) {
					return result;
				}
				
				for(ConditionGroupEntity cg : entity.getListConditionGroup()) {
					
					sql = "INSERT INTO flow.conditiongroup (id,conditionid,ordernum,conditionmapid,description, versionid) "+
							   "VALUES ('"+cg.getId()+"','"+entity.getId()+"','"+cg.getOrderNum()+"','"+cg.getConditionMap().getId()+"','"+cg.getDescription()+"','"+entity.getVersionId()+"')";
							   
							   
					result = result & db.ExecuteSql(sql);
					
					if(!result) {
						return result;
					}
					
					for(ConditionParameterEntity cp : cg.getListConditionParameters()) {
						sql = "INSERT INTO flow.conditionparameters (id,conditiongroupid,paramname,paramvalue,versionid) "+
								   "VALUES ('"+cp.getId()+"','"+cg.getId()+"','"+cp.getParamName()+"','"+cp.getParamValue()+"','"+entity.getVersionId()+"')";
						result = result & db.ExecuteSql(sql);
						if(!result) {
							return result;
						}
					}
					for(ConditionValueEntity cv : cg.getListConditionValues()) {
						sql = "INSERT INTO flow.conditionvalue (id,conditiongroupid,ordernum,operation,"
								+ "value1,value2,value3,value4,value5,value6,value7,value8,value9,value10,tagtrue,tagfalse,versionid) "
								+ "VALUES ('"+cv.getId()+"','"+cg.getId()+"','"+cv.getOrderNum()+"','"+cv.getOperation()+"','"+cv.getValue1()+"',"
								+ (cv.getValue2() == null || cv.getValue2().length() == 0? "NULL" : "'"+cv.getValue2()+"'")+","
								+ (cv.getValue3() == null || cv.getValue3().length() == 0? "NULL" : "'"+cv.getValue3()+"'")+","
								+ (cv.getValue4() == null || cv.getValue4().length() == 0? "NULL" : "'"+cv.getValue4()+"'")+","
								+ (cv.getValue5() == null || cv.getValue5().length() == 0? "NULL" : "'"+cv.getValue5()+"'")+","
								+ (cv.getValue6() == null || cv.getValue6().length() == 0? "NULL" : "'"+cv.getValue6()+"'")+","
								+ (cv.getValue7() == null || cv.getValue7().length() == 0? "NULL" : "'"+cv.getValue7()+"'")+","
								+ (cv.getValue8() == null || cv.getValue8().length() == 0? "NULL" : "'"+cv.getValue8()+"'")+","
								+ (cv.getValue9() == null || cv.getValue9().length() == 0? "NULL" : "'"+cv.getValue9()+"'")+","
								+ (cv.getValue10() == null || cv.getValue10().length() == 0? "NULL" : "'"+cv.getValue10()+"'")+","
								+ (cv.getTagTrue() == null ? "NULL" : cv.getTagTrue().getId())+","
								+ (cv.getTagFalse() == null ? "NULL" : cv.getTagFalse().getId())+",'"+entity.getVersionId()+"') ";
						result = result & db.ExecuteSql(sql);
						if(!result) {
							return result;
						}
					}
				}
							
			}
			Util.audit(entity, Constants.AUDIT_TYPE_UPDATE);
		} finally {
			db.finalize();
		}
		return result;
		
	}

	@Override
	public boolean delete(ConditionEntity entity) {
		log.debug("delete()");
		String sql = "DELETE FROM flow.conditionparameters WHERE conditiongroupid in (SELECT id FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' )  ";
		DbConnection db = new DbConnection("ConditionDAO-getConditionValues");
		boolean result = db.ExecuteSql(sql);
		try {
			if(!result) {
				return result;
			}
			sql = "DELETE FROM flow.conditionvalue WHERE conditiongroupid in (SELECT id FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' )  ";
			
			result = db.ExecuteSql(sql);
			if(!result) {
				return result;
			}
			sql = "DELETE FROM flow.conditiongroup WHERE conditionid = '"+entity.getId()+"' ";			
			result = db.ExecuteSql(sql);
			
			if(!result) {
				return result;
			}
			sql = "DELETE FROM flow.condition WHERE id = '"+entity.getId()+"' ";
			result = db.ExecuteSql(sql);
			Util.audit(entity, Constants.AUDIT_TYPE_DELETE);
		}finally {
			db.finalize();
		}
		return result;
		
	}

}
