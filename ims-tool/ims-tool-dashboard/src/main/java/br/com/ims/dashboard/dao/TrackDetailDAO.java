package br.com.ims.dashboard.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.dashboard.model.TagModel;
import br.com.ims.dashboard.model.TrackDetailModel;
import br.com.ims.dashboard.model.TrackServiceModel;
import br.com.ims.dashboard.util.DbConnection;


public class TrackDetailDAO {

	
	Logger log = Logger.getLogger(TrackDetailDAO.class);
	

	public String getContext(String logId) {
		
		log.debug("[TrackDetail] - " + "getContext("+logId+")");
		
		String context = null;
		ResultSet rs = null;
		String sql = "";
		//OracleConn oracle = null;
		DbConnection db = null;
		
		sql = "select flow.context from log where id = "+logId;
		
		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			if (rs.next()) {
				context = rs.getString("context");				
			}

		} catch (Exception e) {
			log.error("[Dashboard Volume Ligacao Minuto] -" + e.getMessage(),e);
			return null;

		} finally {
			
			db.finalize();

		}
		
		/**
		 * Mock
		 *
		context = "The story begins as Don Vito Corleone, the head of a New York Mafia family, oversees his daughter's wedding. His beloved son Michael has just come home from the war, but does not intend to become part of his father's business. Through Michael's life the nature of the family business becomes clear. The business of the family is just like the head of the family, kind and benevolent to those who give respect, but given to ruthless violence whenever anything stands against the good of the family.";		
		/**
		 * Fim mock
		 */
		return context;
	}
	
	public List<TrackDetailModel> getCheckList(String logId) {
		log.debug("[TrackDetail] - " + "getCheckList("+logId+")");
		
		List<TrackDetailModel> retorno = new ArrayList<TrackDetailModel>();
		ResultSet rs = null;
		String sql = "select f.id formid, f.name formName, t.id trackid, to_char(t.rowdate,'dd/mm/yyyy hh24:mi:ss') rowdate, to_char(l.startdate,'dd/mm/yyyy hh24:mi:ss') startdate, to_char(l.stopdate,'dd/mm/yyyy hh24:mi:ss') stopdate, t.tagid, f.description, ft.name formtypename, ft.id formtypeid "+
				      "from flow.log l "+
				      "join flow.track t on t.logid = l.id and t.rowdate between l.startdate and l.stopdate "+
				      "join flow.form f on f.id = t.formid "+
				      "join flow.formtype ft on ft.id = f.formtype "+
				      "where l.id = "+logId+" "+
				      "and t.log_type = 'C' "+
				      "order by t.rowdate "; 
		//OracleConn oracle = null;
		DbConnection db = null;
		
		
		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				 
				TrackDetailModel track = new TrackDetailModel();
				track.setFormId(rs.getLong("formid"));
				track.setFormName(rs.getString("formName"));
				track.setTrackId(rs.getLong("trackid"));
				track.setRowdate(rs.getString("rowdate"));
				track.setStartdate(rs.getString("startdate"));
				track.setStopdate(rs.getString("stopdate"));
				track.setTagId(rs.getInt("tagid"));
				track.setDescription(rs.getString("description"));
				track.setFormTypeName(rs.getString("formtypename"));
				track.setFormTypeId(rs.getInt("formtypeid"));
				
				track.setService(this.getTrackServiceDetail(track.getTrackId(),track.getStartdate(), track.getStopdate(),"checklist", track.getFormTypeId()));
				
				retorno.add(track);
								
			}

		} catch (Exception e) {
			log.error("[TrackDetail -> getCheckList] -" + e.getMessage(),e);
			return null;

		} finally {
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			db.finalize();

		}
		
		/**
		 * Mock
		 *
		TrackDetailModel track = new TrackDetailModel();
		track.setId(BigInteger.valueOf(1000));
		track.setData(Calendar.getInstance().getTime());
		track.setTag(10);
		track.setDescription("Descrição Tag10");
		
		retorno.add(track);
		
		track = new TrackDetailModel();
		track.setId(BigInteger.valueOf(1001));
		track.setData(Calendar.getInstance().getTime());
		track.setTag(11);
		track.setDescription("Descrição Tag11");
		
		retorno.add(track);
		
		track = new TrackDetailModel();
		track.setId(BigInteger.valueOf(1002));
		track.setData(Calendar.getInstance().getTime());
		track.setTag(12);
		track.setDescription("Descrição Tag12");
		
		retorno.add(track);
		;
		/**
		 * Fim mock
		 */
		return retorno;
	}
	
	public List<TrackDetailModel> getTrackDetail(String logId) {
		
		log.debug("[TrackDetail] - " + "getTrackDetail("+logId+")");
		
		List<TrackDetailModel> retorno = new ArrayList<TrackDetailModel>();
		ResultSet rs = null;
		String sql = "select f.id formid, f.name formName, t.id trackid, to_char(t.rowdate,'dd/mm/yyyy hh24:mi:ss') rowdate, to_char(l.startdate,'dd/mm/yyyy hh24:mi:ss') startdate, to_char(l.stopdate,'dd/mm/yyyy hh24:mi:ss') stopdate, t.tagid, f.description, ft.name formtypename, ft.id formtypeid "+
					 "from flow.log l "+
					 "join flow.track t on t.logid = l.id and t.rowdate between l.startdate and l.stopdate "+
					 "join flow.form f on f.id = t.formid "+
					 "join flow.formtype ft on ft.id = f.formtype "+
					 "where l.id = "+logId+" "+ 
					 "and t.log_type is null "+
					 "order by t.rowdate ";
		//OracleConn oracle = null;
		DbConnection db = null;
		
		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				 
				TrackDetailModel track = new TrackDetailModel();
				track.setFormId(rs.getLong("formid"));
				track.setFormName(rs.getString("formName"));
				track.setTrackId(rs.getLong("trackid"));
				track.setRowdate(rs.getString("rowdate"));
				track.setStartdate(rs.getString("startdate"));
				track.setStopdate(rs.getString("stopdate"));
				track.setTagId(rs.getInt("tagid"));
				track.setDescription(rs.getString("description"));
				track.setFormTypeName(rs.getString("formtypename"));
				track.setFormTypeId(rs.getInt("formtypeid"));
				
				track.setService(this.getTrackServiceDetail(track.getTrackId(),track.getStartdate(), track.getStopdate(),"trackdetail", track.getFormTypeId()));
				
				retorno.add(track);
								
			}

		} catch (Exception e) {
			log.error("[TrackDetail -> getTrackDetail] -" + e.getMessage(),e);
			return null;

		} finally {
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			db.finalize();

		}
		/**
		 * Mock
		 *
		TrackDetailModel track = new TrackDetailModel();
		track.setId(BigInteger.valueOf(1000));
		track.setData(Calendar.getInstance().getTime());
		track.setTag(10);
		track.setDescription("Descrição Tag10");
		
		retorno.add(track);
		
		track = new TrackDetailModel();
		track.setId(BigInteger.valueOf(1001));
		track.setData(Calendar.getInstance().getTime());
		track.setTag(11);
		track.setDescription("Descrição Tag11");
		
		retorno.add(track);
		
		track = new TrackDetailModel();
		track.setId(BigInteger.valueOf(1002));
		track.setData(Calendar.getInstance().getTime());
		track.setTag(12);
		track.setDescription("Descrição Tag12");
		
		retorno.add(track);
		;
		/**
		 * Fim mock
		 */
		return retorno;
	}
	
	public List<TrackServiceModel> getTrackServiceDetail(Long trackId, String startdate,String stopdate, String tipo, Integer formTypeId) {
		log.debug("[TrackDetail] - " + "getTrackServiceDetail("+trackId+","+startdate+","+stopdate+","+tipo+","+formTypeId+")");
		
		List<TrackServiceModel> retorno = new ArrayList<TrackServiceModel>();
		String sql = "";
		
		
		ResultSet rs = null;
		if(tipo.equalsIgnoreCase("checklist")) {
			sql = "select tr.method_service, tr.parameters_in, tr.result_call, tr.errorcodeid, tr.description "+
		          "from ( "+
		          "		select ts.id id, ts.method_service, ts.parameters_in parameters_in, ts.description, "+
		          "		ts.result_call result_call, ts.errorcodeid, ts.groupid groupid,ts.rowdate rowdate "+
		          "		from  flow.trackservice ts "+
		          "		where "+
		          "		ts.rowdate between to_date('"+startdate+"','dd/mm/yyyy hh24:mi:ss') and to_date('"+stopdate+"','dd/mm/yyyy hh24:mi:ss')+(1/24/60) and "+
		          "		ts.trackid = "+trackId+" "+
		          "		UNION ALL "+
		          "		select tg.id id, 'TAG' method_service, '' parameters_in, '' description, "+
		          "		TO_CHAR(tg.tagid) result_call, 0, 0, tg.rowdate rowdate "+
		          "		from flow.tracktag tg "+
		          "		where "+
		          "		tg.rowdate between to_date('"+startdate+"','dd/mm/yyyy hh24:mi:ss') and to_date('"+stopdate+"','dd/mm/yyyy hh24:mi:ss')+(1/24/60) and "+
		          "		tg.trackid = "+trackId+" "+ 
		          ") tr "+
		          "	order by rowdate";
		} else {
			if(formTypeId.equals(5)) {
				sql = "select '_' method_service, dg.description, tr.parameters_in, tr.result_call, tr.errorcodeid "+
		              "from ( "+
					  "		select * "+
					  "		from ( "+
		              "			select ts.id id, ts.method_service, ts.parameters_in parameters_in, "+
		              "         ts.result_call result_call, ts.errorcodeid, ts.groupid groupid,ts.rowdate rowdate "+
		              " 	    from flow.trackservice ts "+
		              "		    where "+
		              "		    ts.rowdate between to_date('"+startdate+"','dd/mm/yyyy hh24:mi:ss') and to_date('"+stopdate+"','dd/mm/yyyy hh24:mi:ss')+(1/24/60) and "+
		              "	        ts.trackid = "+trackId+" "+
		              "		    UNION ALL "+
		              "		    select tg.id id, 'TAG' method_service, '' parameters_in, "+
		              "         to_char(tg.tagid) result_call, 0, 0, tg.rowdate rowdate "+
		              "		    from flow.tracktag tg "+
		              "		    where tg.rowdate between to_date('"+startdate+"','dd/mm/yyyy hh24:mi:ss') and to_date('"+stopdate+"','dd/mm/yyyy hh24:mi:ss')+(1/24/60) and "+
		              "		    tg.trackid = "+trackId+" "+
		              "		) "+
		              ") tr "+
		              "join decisiongroup dg on dg.id = tr.groupid "+
		              "where dg.description is not null "+
		              "order by tr.rowdate";
			} else if(formTypeId.equals(6)) {
				sql = "select '_' method_service, og.description, tr.parameters_in, tr.result_call, tr.errorcodeid "+
					  "from ( "+
					  "		select * "+
					  "		from ( "+
					  "			select ts.id id, ts.method_service, ts.parameters_in parameters_in, "+
                      "			ts.result_call result_call, ts.errorcodeid, ts.groupid groupid,ts.rowdate rowdate "+
                      "			from flow.trackservice ts "+
                      "			where ts.rowdate between to_date('"+startdate+"','dd/mm/yyyy hh24:mi:ss') and to_date('"+stopdate+"','dd/mm/yyyy hh24:mi:ss')+(1/24/60) "+
                      "			and ts.trackid = "+trackId+" "+
                      "			UNION ALL "+
                      "			select tg.id id, 'TAG' method_service, '' parameters_in, "+
                      " 		to_char(tg.tagid) result_call, 0, 0, tg.rowdate rowdate "+
                      "			from flow.tracktag tg "+
                      "			where tg.rowdate between to_date('"+startdate+"','dd/mm/yyyy hh24:mi:ss') and to_date('"+stopdate+"','dd/mm/yyyy hh24:mi:ss')+(1/24/60) "+
                      "			and tg.trackid = "+trackId+" "+
                      "		) "+
                      ") tr "+
                      "join operationgroup og on og.id = tr.groupid "+
                      "where og.description is not null "+   
                      "order by tr.rowdate"; 
			} else {
				sql = "select tr.method_service,'_' description, tr.parameters_in, tr.result_call, tr.errorcodeid "+
					  "from ( "+
					  "		select ts.id id, ts.method_service, ts.parameters_in parameters_in, "+
					  "		ts.result_call result_call, ts.errorcodeid, ts.groupid groupid,ts.rowdate rowdate "+
					  "		from  flow.trackservice ts "+
					  "		where ts.rowdate between to_date('"+startdate+"','dd/mm/yyyy hh24:mi:ss') and to_date('"+stopdate+"','dd/mm/yyyy hh24:mi:ss')+(1/24/60) "+
					  "		and ts.trackid = "+trackId+" "+ 
					  "		UNION ALL "+
					  "		select tg.id id, 'TAG' method_service, '' parameters_in, "+
					  "		TO_CHAR(tg.tagid) result_call, 0, 0, tg.rowdate rowdate "+
					  "		from flow.tracktag tg "+
					  "		where tg.rowdate between to_date('"+startdate+"','dd/mm/yyyy hh24:mi:ss') and to_date('"+stopdate+"','dd/mm/yyyy hh24:mi:ss')+(1/24/60) "+
					  "		and tg.trackid = "+trackId+" "+
					  ") tr "+
					  "order by rowdate";
			}
		}
		
		//OracleConn oracle = null;
		DbConnection db = null;
		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			while (rs.next()) {
				 
				TrackServiceModel track = new TrackServiceModel();
				track.setMethodService(rs.getString("method_service"));
				track.setParametersIn(rs.getString("parameters_in"));
				track.setResultCall(rs.getString("result_call"));
				track.setErrorCodeId(rs.getInt("errorcodeid"));
				track.setDescription(rs.getString("description"));
				
				
				if(track.getMethodService().equalsIgnoreCase("TAG")) {
					TagModel tag = this.getTag(Integer.valueOf(track.getResultCall()));
					if(tag != null) {
						track.setTag(tag);
					} else {
						track.getTag().setId(Integer.valueOf(track.getResultCall()));
						track.getTag().setDescription("### TAG N�O CADASTRADA ###");
					}
				} else {
					track.getTag().setDescription(track.getResultCall());					
				}
				
				
				retorno.add(track);
								
			}

		} catch (Exception e) {
			log.error("[TrackDetail -> getTrackServiceDetail] -" + e.getMessage(),e);			

		} finally {
			db.finalize();

		}
		return retorno;
	}

	public TagModel getTag(Integer tagId) {
		ResultSet rs = null;		
		//OracleConn oracle = null;
		DbConnection db = null;
		String sql = "select id, tagtypeid,description from flow.tag where id ="+tagId;
		TagModel tag = null;
		try {
			//oracle = new OracleConn("IVR_OWNER");
			db = new DbConnection("");
			rs = db.ExecuteQuery(sql);
			
			if (rs.next()) {
				 
				tag = new TagModel();
				tag.setId(rs.getInt("id"));
				tag.setTagTypeId(rs.getInt("tagtypeid"));
				tag.setDescription(rs.getString("description"));
								
								
			}

		} catch (Exception e) {
			log.error("[TrackDetail -> getTrackDetail] -" + e.getMessage(),e);
			return null;

		} finally {
			
			db.finalize();

		}
		return tag;
	}
	

}
