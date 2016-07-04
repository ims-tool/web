package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.model.LogicEntity;
import br.com.ims.flow.model.LogicMapEntity;
import br.com.ims.flow.model.LogicNodeEntity;
import br.com.ims.flow.model.LogicNodeOperationEntity;
import br.com.ims.flow.model.LogicNodeParameterEntity;
import br.com.ims.flow.model.LogicNodeValueEntity;
import br.com.ims.flow.model.MapTypeEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

@SuppressWarnings("serial")
public class LogicDAO extends AbstractDAO<LogicEntity> {
	//private DbConnection db =  null;
	public static Logger log = Logger.getLogger(LogicDAO.class);
	private static LogicDAO instance = null;
	private LogicDAO() {
		//db =  new DbConnection("ConditionDAO"); 			
	}
	
	public static LogicDAO getInstance() {
		if(instance == null) {
			instance = new LogicDAO();
		}
		return instance;
	}
	
	private List<LogicNodeParameterEntity> getLogicNodeParameter(String logicNodeId) {
		log.debug("getLogicNodeParameter("+logicNodeId+")");
		String sql = "SELECT lp.id lp_id,lp.logicnodeid lp_logicnodeid,lp.paramname lp_paramname,lp.paramvalue lp_paramvalue, lp.versionid lp_versionid "+
				 "FROM flow.logicnodeparameter lp "+
               "WHERE cp.logicnodeid ='"+logicNodeId+"' ";
		List<LogicNodeParameterEntity> result = new ArrayList<LogicNodeParameterEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("LogicDAO-getLogicNodeParameter");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				LogicNodeParameterEntity lp = new 	LogicNodeParameterEntity();
				lp.setId(rs.getString("lp_id"));
				lp.setLogicNodeId(rs.getString("lp_logicnodeid"));
				lp.setParamName(rs.getString("lp_paramname"));
				lp.setParamValue(rs.getString("lp_paramvalue"));
				lp.setVersionId(rs.getString("lp_versionid"));
				result.add(lp);
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

	private List<LogicNodeOperationEntity> getLogicNodeOperation(String logicNodeId, boolean lazy) {
		log.debug("getLogicNodeOperation("+logicNodeId+")");
		String sql = "SELECT lo.id lo_id,lo.logicnodeid lo_logicnodeid,lo.ordernum lo_order,lo.operation lo_operation,lo.versionid lo_versionid "+
				 "FROM flow.logicnodeoperation lo "+
               "WHERE lo.logicnodeid ='"+logicNodeId+"' ";
		List<LogicNodeOperationEntity> result = new ArrayList<LogicNodeOperationEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("LogicDAO-getLogicNodeOperation");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				LogicNodeOperationEntity lo = new 	LogicNodeOperationEntity();
				lo.setId(rs.getString("lo_id"));
				lo.setLogicNodeId(rs.getString("lo_logicnodeid"));
				lo.setOrderNum(rs.getInt("lo_order"));
				lo.setOperation(rs.getString("lo_operation"));
				if(!lazy) {
					lo.setListLogicNodeValues(this.getLogicNodeValue(rs.getString("lo_id")));
				}
				lo.setVersionId(rs.getString("lp_versionid"));
				result.add(lo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
			
		}
		
		return result;
	}
	
	private List<LogicNodeValueEntity> getLogicNodeValue(String logicOperationId) {
		log.debug("getLogicNodeValue("+logicOperationId+")");
		String sql = "SELECT lv.id lv_id,lv.logicoperationid lv_logicoperationid,lv.orderNum lv_order,lv.operation lv_operation,lv.versionid lv_versionid, "
				    + "lv.value1 lv_value1, lv.value2 lv_value2,lv.value3 lv_value3,lv.value4 lv_value4,lv.value5 cv_value5,"
				    + "lv.value6 lv_value6, lv.value7 lv_value7,lv.value8 lv_value8,lv.value9 lv_value9,lv.value10 lv_value10,"
				    + "FROM flow.logicnodevalue lv "
				    + "WHERE lv.logicoperationid ='"+logicOperationId+"' ";
		List<LogicNodeValueEntity> result = new ArrayList<LogicNodeValueEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("ConditionDAO-getLogicNodeValue");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				
				LogicNodeValueEntity lv = new LogicNodeValueEntity();
				lv.setId(rs.getString("lv_id"));
				lv.setLogicOperationId(rs.getString("lv_logicoperationid"));
				lv.setOrderNum(rs.getInt("lv_order"));
				lv.setOperation(rs.getString("lv_operation"));
				lv.setValue1(rs.getString("lv_value1"));
				lv.setValue2(rs.getString("lv_value2"));
				lv.setValue3(rs.getString("lv_value3"));
				lv.setValue4(rs.getString("lv_value4"));
				lv.setValue5(rs.getString("lv_value5"));
				lv.setValue6(rs.getString("lv_value6"));
				lv.setValue7(rs.getString("lv_value7"));
				lv.setValue8(rs.getString("lv_value8"));
				lv.setValue9(rs.getString("lv_value9"));
				lv.setValue10(rs.getString("lv_value10"));
				lv.setVersionId(rs.getString("lv_versionid"));
				
				
				result.add(lv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
			
		}
		
		return result;
	}
	
	public List<LogicNodeEntity> getLogicNode(String where,boolean lazy) {
		log.debug("getLogicNode("+where+","+lazy+")");
		String sql = "SELECT ln.id ln_id,ln.logicid ln_logicid,ln.name ln_name, ln.description ln_description, "+
				 "ln.orderNum ln_order,ln.gotofalse ln_gotofalse,ln.gotofalse ln_gototrue, ln.versionid ln_versionid, "+
				 "lm.id lm_id, lm.name lm_name,lm.description lm_description,lm.maptypeid lm_maptypeid,lm.methodreference lm_methodreference,lm.returntype lm_returtype, lm.log_active lm_log_active, "+
				 "tf.id tf_id, tf.description tf_description, "+ 
				 "ttf.id ttf_id, ttf.name ttf_name,ttf.description ttf_description, "+
				 "tt.id tt_id, tt.description tt_description, "+ 
				 "ttt.id ttt_id, ttt.name ttt_name,ttt.description ttt_description "+
				 "FROM flow.logicnode ln "+
                 "INNER JOIN flow.logicmap lm ON lm.id = ln.logicmapid "+
				 "INNER JOIN flow.maptype mt ON mt.id = lm.maptypeid "+
                 "LEFT JOIN flow.tag tf ON ln.tagfalse = tf.id "+ 
				 "LEFT JOIN flow.tagtype ttf ON tf.tagtypeid = ttf.id "+
				 "LEFT JOIN flow.tag tt ON ln.tagtrue = tt.id "+ 
				 "LEFT JOIN flow.tagtype ttt ON tt.tagtypeid = ttt.id "+
                 "<WHERE> "+
                 "ORDER BY ln.orderNum ";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<LogicNodeEntity> result = new ArrayList<LogicNodeEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("LogicDAO-getLogicNode");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				TagEntity tagfalse = null;
				TagEntity tagtrue = null;
				if(rs.getString("tf_id") != null && rs.getString("tf_id").length() > 0) {
					TagTypeEntity tagType = new TagTypeEntity();
					tagType.setId(rs.getString("ttf_id"));
					tagType.setName(rs.getString("ttf_name"));
					tagType.setDescription(rs.getString("ttf_description"));
					
					tagfalse = new TagEntity();
					tagfalse.setId(rs.getString("tf_id"));
					tagfalse.setDescription(rs.getString("tf_description"));
					tagfalse.setType(tagType);
				}
				if(rs.getString("tt_id") != null && rs.getString("tt_id").length() > 0) {
					TagTypeEntity tagType = new TagTypeEntity();
					tagType.setId(rs.getString("ttt_id"));
					tagType.setName(rs.getString("ttt_name"));
					tagType.setDescription(rs.getString("ttt_description"));
					
					tagtrue = new TagEntity();
					tagtrue.setId(rs.getString("tt_id"));
					tagtrue.setDescription(rs.getString("tt_description"));
					tagtrue.setType(tagType);
				}
				
				
				LogicMapEntity logicMap = new LogicMapEntity();
				logicMap.setId(rs.getString("lm_id"));
				logicMap.setName(rs.getString("lm_name"));
				logicMap.setDescription(rs.getString("lm_description"));
				
				MapTypeEntity mapType = new MapTypeEntity();
				mapType.setId(rs.getString("mt_id"));
				mapType.setName(rs.getString("mt_name"));
				mapType.setDescription(rs.getString("mt_description"));
				logicMap.setMapType(mapType);
				logicMap.setMethodReference(rs.getString("lm_methodreference"));
				logicMap.setReturnType(rs.getString("lm_returtype"));
				logicMap.setLogActive(rs.getInt("lm_log_active"));
				
				
				List<LogicNodeParameterEntity> lp = new ArrayList<LogicNodeParameterEntity>() ;
				List<LogicNodeOperationEntity> lo = new ArrayList<LogicNodeOperationEntity>();
				if(!lazy) {
					lp.addAll(this.getLogicNodeParameter(rs.getString("ln_id")));
					lo.addAll(this.getLogicNodeOperation(rs.getString("ln_id"), lazy));
					
				}
				LogicNodeEntity ln = new LogicNodeEntity();
				ln.setId(rs.getString("ln_id"));
				ln.setLogicId(rs.getString("ln_logicid"));
				ln.setName(rs.getString("ln_name"));
				ln.setDescription(rs.getString("ln_description"));
				ln.setOrderNum(rs.getInt("ln_order"));
				ln.setGotoFalse(rs.getString("ln_gotofalse"));
				ln.setGotoTrue(rs.getString("ln_gototrue"));
				ln.setVersionId(rs.getString("ln_versionid"));
				ln.setLogicMap(logicMap);
				ln.setTagFalse(tagfalse);
				ln.setTagTrue(tagtrue);
				result.add(ln);
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
	public List<LogicEntity> getByFilter(String where) {
		return this.getByFilter(where, false);
	}
	public List<LogicEntity> getByFilter(String where,boolean lazy) {
		log.debug("getByFilter("+where+","+lazy+")");
		String sql = "SELECT l.id l_id,l.name l_name,l.description l_description,l.versionid l_versionid, "+
				 "t.id t_id, t.description t_description, "+ 
				 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
				 "FROM flow.logic l "+
				 "LEFT JOIN flow.tag t ON l.tag = t.id "+ 
				 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+
				 "<WHERE> "+
				 "ORDER BY l.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<LogicEntity> result = new ArrayList<LogicEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("LogicDAO-getByFilter");
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
				
				List<LogicNodeEntity> nodes = new ArrayList<LogicNodeEntity>();
				if(!lazy) {
					nodes.addAll(this.getLogicNode("WHERE ln.logicid ='"+rs.getString("l_id")+"' ",lazy));
				}
				
				
				LogicEntity logic = new LogicEntity();
				logic.setId(rs.getString("l_id"));
				logic.setName(rs.getString("l_name"));
				logic.setDescription(rs.getString("l_description"));
				logic.setListLogicNode(nodes);
				logic.setTag(tag);	
				logic.setVersionId(rs.getString("l_versionid")); 
				
				result.add(logic);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
		
		}
		
		return result;
	}
	public List<LogicEntity> getAll() {
		
		return this.getByFilter(null);
		
	}
	public List<LogicEntity> getAll(boolean lazy) {
		
		return this.getByFilter(null,lazy);
		
	}
	public LogicEntity get(String id) {
		List<LogicEntity> result = this.getByFilter("WHERE l.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public LogicEntity get(String id,boolean lazy) {
		List<LogicEntity> result = this.getByFilter("WHERE l.id = '"+id+"'",lazy);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public LogicEntity getByName(String name) {
		List<LogicEntity> result = this.getByFilter("WHERE lower(l.name) = '"+name.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	public boolean save(LogicEntity entity) {
		boolean result = true;
		log.debug("save()");
		String sql = "INSERT INTO flow.logic (id,name,description,tag,versionid) "+
					 "VALUES ('"+entity.getId()+"','"+entity.getName()+"','"+entity.getDescription()+"',"
					 		+(entity.getTag() == null ? "NULL" : entity.getTag().getId())+","
					 		+entity.getVersionId()+")";
		DbConnection db = new DbConnection("LogicDAO-save");
		try {
			result = db.ExecuteSql(sql);
			if(result) {
				
				for(LogicNodeEntity ln : entity.getListLogicNode()) {
					
					sql = "INSERT INTO flow.logicnode (id,logicid,name,description,logicmapid,ordernum,gotofalse, tagfalse,gototrue, tagtrue, versionid) "+
						   "VALUES ('"+ln.getId()+"','"+entity.getId()+"','"+ln.getName()+"','"+ln.getDescription()+"',"
						 + "'"+ln.getLogicMap().getId()+"','"+ln.getOrderNum()+"',"
						 + (ln.getGotoFalse() == null ? "NULL" : "'"+ln.getGotoFalse()+"'")+","
						 + (ln.getTagFalse() == null ? "NULL" : ln.getTagFalse().getId())+","
						 + (ln.getGotoTrue() == null ? "NULL" : "'"+ln.getGotoTrue()+"'")+","
						 + (ln.getTagTrue() == null ? "NULL" : ln.getTagTrue().getId())+","
						 + ln.getVersionId()+")";
						   
						   
					result = result & db.ExecuteSql(sql);
					if(!result) {
						//rollback
						sql = "DELETE FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' ";
						db.ExecuteSql(sql);
						sql = "DELETE FROM flow.logic WHERE id = '"+entity.getId()+"' ";
						db.ExecuteSql(sql);
						return result;
					} else {
						
						for(LogicNodeParameterEntity lp : ln.getListParameter()) {
							sql = "INSERT INTO flow.logicnodeparameter (id,logicnodeid,paramname,paramvalue,versionid) "+
									   "VALUES ('"+lp.getId()+"','"+ln.getId()+"','"+lp.getParamName()+"','"+lp.getParamValue()+"','"+entity.getVersionId()+"')";
							result = result & db.ExecuteSql(sql);
							if(!result) {
								//rollback
								sql = "DELETE FROM flow.logicnodeparameter WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' )  ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.logic WHERE id = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								return result;
							}
						}
						
						for(LogicNodeOperationEntity lo : ln.getListOperation()) {
							sql = "INSERT INTO flow.logicnodeoperation (id,logicnodeid,ordernum,operation,versionid) "
									+ "VALUES ('"+lo.getId()+"','"+ln.getId()+"','"+lo.getOrderNum()+"','"+lo.getOperation()+"',"+entity.getVersionId()+") ";
							result = result & db.ExecuteSql(sql);
							if(!result) {
								//rollback
								sql = "DELETE FROM flow.logicnodeparameter WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' )  ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.logicnodeoperation WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' )  ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								sql = "DELETE FROM flow.logic WHERE id = '"+entity.getId()+"' ";
								db.ExecuteSql(sql);
								return result;
								
							} else {
								for(LogicNodeValueEntity lv: lo.getListLogicNodeValues()) {
									sql = "INSERT INTO flow.logicnodevalue (id,logicoperationid,ordernum,resultservice,resultcontext, operation,"
											+ "value1,value2,value3,value4,value5,value6,value7,value8,value9,value10,versionid) "
											+ "VALUES ('"+lv.getId()+"','"+lo.getId()+"','"+ln.getOrderNum()+"',"+lv.getResultService()+","
											+ (lv.getResultContext() == null || lv.getResultContext().length() == 0 ? "NULL" : "'"+lv.getResultContext()+"'")+","
											+ "'"+lv.getOperation()+"','"+lv.getValue1()+"', "
											+ (lv.getValue2() == null || lv.getValue2().length() == 0? "NULL" : "'"+lv.getValue2()+"'")+","
											+ (lv.getValue3() == null || lv.getValue3().length() == 0? "NULL" : "'"+lv.getValue3()+"'")+","
											+ (lv.getValue4() == null || lv.getValue4().length() == 0? "NULL" : "'"+lv.getValue4()+"'")+","
											+ (lv.getValue5() == null || lv.getValue5().length() == 0? "NULL" : "'"+lv.getValue5()+"'")+","
											+ (lv.getValue6() == null || lv.getValue6().length() == 0? "NULL" : "'"+lv.getValue6()+"'")+","
											+ (lv.getValue7() == null || lv.getValue7().length() == 0? "NULL" : "'"+lv.getValue7()+"'")+","
											+ (lv.getValue8() == null || lv.getValue8().length() == 0? "NULL" : "'"+lv.getValue8()+"'")+","
											+ (lv.getValue9() == null || lv.getValue9().length() == 0? "NULL" : "'"+lv.getValue9()+"'")+","
											+ (lv.getValue10() == null || lv.getValue10().length() == 0? "NULL" : "'"+lv.getValue10()+"'")+","
											+ "'"+entity.getVersionId()+"') ";
									result = result & db.ExecuteSql(sql);
									if(!result) {
										//rollback
										sql = "DELETE FROM flow.logicnodeparameter WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' )  ";
										db.ExecuteSql(sql);
										sql = "DELETE FROM flow.logicnodevalue WHERE logicoperationid = '"+lo.getId()+"' ";
										db.ExecuteSql(sql);
										sql = "DELETE FROM flow.logicnodeoperation WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' )  ";
										db.ExecuteSql(sql);
										sql = "DELETE FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' ";
										db.ExecuteSql(sql);
										sql = "DELETE FROM flow.logic WHERE id = '"+entity.getId()+"' ";
										db.ExecuteSql(sql);
										return result;
										
									}
								}
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
	public boolean update(LogicEntity entity) {
		boolean result = true;
		log.debug("update()");
		String sql = "UPDATE flow.logic SET name = '"+entity.getName()+"',description = '"+entity.getDescription()+"',"
				   + "tag = "+(entity.getTag() == null ? "NULL"  : entity.getTag().getId())+","
				   + "versionid  =  '"+entity.getVersionId()+"' "
				   + "WHERE id = "+entity.getId();
		
		DbConnection db = new DbConnection("LogicDAO-update");
		try {
			result = db.ExecuteSql(sql);
			if(result) {
				sql = "DELETE FROM flow.logicnodeparameter WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' )  ";
				result = db.ExecuteSql(sql);
				if(!result) {
					return result;
				}
				sql = "DELETE FROM flow.logicnodevalue WHERE logicoperationid in (SELECT id FROM flow.logicnodeoperation WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' ))";
				result = db.ExecuteSql(sql);
				if(!result) {
					return result;
				}
				sql = "DELETE FROM flow.logicnodeoperation WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' )  ";
				result = db.ExecuteSql(sql);
				if(!result) {
					return result;
				}
				for(LogicNodeEntity ln : entity.getListLogicNode()) {
					if(ln.isDelete()) {
						sql = "DELETE FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' ";
						result = db.ExecuteSql(sql);
						if(!result) {
							return result;
						}
					} else {
						sql = "UPDATE flow.logicnode SET name = '"+ln.getName()+"',description = '"+ln.getDescription()+"',"
							   + "logicmapid='"+ln.getLogicMap().getId()+"',ordernum='"+ln.getOrderNum()+"',"
							   + "gotofalse="+(ln.getGotoFalse() == null || ln.getGotoFalse().length() == 0 ? "NULL" : ln.getGotoFalse())+","
							   + "tagfalse="+(ln.getTagFalse() == null ? "NULL" : ln.getTagFalse().getId())+","
							   + "gototrue="+(ln.getGotoTrue() == null || ln.getGotoTrue().length() == 0 ? "NULL" : ln.getGotoTrue())+","
							   + "tagtrue="+(ln.getTagTrue() == null ? "NULL" : ln.getTagTrue().getId())+","
							   + "versionid  =  '"+entity.getVersionId()+"' "
							   + "WHERE id = "+entity.getId();
						result = result & db.ExecuteSql(sql);
						if(result) {
							
							for(LogicNodeParameterEntity lp : ln.getListParameter()) {
								sql = "INSERT INTO flow.logicnodeparameter (id,logicnodeid,paramname,paramvalue,versionid) "+
										   "VALUES ('"+lp.getId()+"','"+ln.getId()+"','"+lp.getParamName()+"','"+lp.getParamValue()+"','"+entity.getVersionId()+"')";
								result = result & db.ExecuteSql(sql);
								if(!result) {
									return result;
								}
							}
							
							for(LogicNodeOperationEntity lo : ln.getListOperation()) {
								sql = "INSERT INTO flow.logicnodeoperation (id,logicnodeid,ordernum,operation,versionid) "
										+ "VALUES ('"+lo.getId()+"','"+ln.getId()+"','"+lo.getOrderNum()+"','"+lo.getOperation()+"',"+entity.getVersionId()+") ";
								result = result & db.ExecuteSql(sql);
								if(!result) {
									return result;
									
								} else {
									for(LogicNodeValueEntity lv: lo.getListLogicNodeValues()) {
										sql = "INSERT INTO flow.logicnodevalue (id,logicoperationid,ordernum,resultservice,resultcontext, operation,"
												+ "value1,value2,value3,value4,value5,value6,value7,value8,value9,value10,versionid) "
												+ "VALUES ('"+lv.getId()+"','"+lo.getId()+"','"+ln.getOrderNum()+"',"+lv.getResultService()+","
												+ (lv.getResultContext() == null || lv.getResultContext().length() == 0 ? "NULL" : "'"+lv.getResultContext()+"'")+","
												+ "'"+lv.getOperation()+"','"+lv.getValue1()+"', "
												+ (lv.getValue2() == null || lv.getValue2().length() == 0? "NULL" : "'"+lv.getValue2()+"'")+","
												+ (lv.getValue3() == null || lv.getValue3().length() == 0? "NULL" : "'"+lv.getValue3()+"'")+","
												+ (lv.getValue4() == null || lv.getValue4().length() == 0? "NULL" : "'"+lv.getValue4()+"'")+","
												+ (lv.getValue5() == null || lv.getValue5().length() == 0? "NULL" : "'"+lv.getValue5()+"'")+","
												+ (lv.getValue6() == null || lv.getValue6().length() == 0? "NULL" : "'"+lv.getValue6()+"'")+","
												+ (lv.getValue7() == null || lv.getValue7().length() == 0? "NULL" : "'"+lv.getValue7()+"'")+","
												+ (lv.getValue8() == null || lv.getValue8().length() == 0? "NULL" : "'"+lv.getValue8()+"'")+","
												+ (lv.getValue9() == null || lv.getValue9().length() == 0? "NULL" : "'"+lv.getValue9()+"'")+","
												+ (lv.getValue10() == null || lv.getValue10().length() == 0? "NULL" : "'"+lv.getValue10()+"'")+","
												+ "'"+entity.getVersionId()+"') ";
										result = result & db.ExecuteSql(sql);
										if(!result) {
											return result;
											
										}
									}
								}
								
							}
							
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
	public boolean delete(LogicEntity entity) {
		log.debug("delete()");
		DbConnection db = new DbConnection("LogicDAO-delete");
		boolean result = true;
		try {
		
			String sql = "DELETE FROM flow.logicnodeparameter WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' )  ";
			result = db.ExecuteSql(sql);
			if(!result) {
				return result;
			}
			sql = "DELETE FROM flow.logicnodevalue WHERE logicoperationid in (SELECT id FROM flow.logicnodeoperation WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' ))";
			result = db.ExecuteSql(sql);
			if(!result) {
				return result;
			}
			sql = "DELETE FROM flow.logicnodeoperation WHERE logicnodeid in (SELECT id FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' )  ";
			result = db.ExecuteSql(sql);
			if(!result) {
				return result;
			}
			sql = "DELETE FROM flow.logicnode WHERE logicid = '"+entity.getId()+"' ";
			result = db.ExecuteSql(sql);
			if(!result) {
				return result;
			}
			sql = "DELETE FROM flow.logic WHERE id = '"+entity.getId()+"' ";
			result = db.ExecuteSql(sql);
			
			Util.audit(entity, Constants.AUDIT_TYPE_DELETE);
		}finally {
			db.finalize();
		}
		return result;
		
		
		
	}

}
