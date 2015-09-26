package br.com.ims.tool.nextform.util;

import org.apache.log4j.Logger;

import br.com.ims.tool.nextform.model.LogDetailDto;
import br.com.ims.tool.nextform.model.LogDto;
import br.com.ims.tool.nextform.model.NextFormDto;
import br.com.ims.tool.nextform.model.TrackDto;
import br.com.ims.tool.nextform.model.TrackServiceDto;
import br.com.ims.tool.nextform.persistence.LogDao;

public class LogUtils {
	
	private static Logger logger = Logger.getLogger(LogUtils.class);
	
	private static LogDao obtemLogDao() {
		LogDao dao = new LogDao();
		return dao;
	}
	
	public static String createLog(String jsonContext, NextFormDto nextForm) {
		String strLogId = null;
		
			strLogId = MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID);
			Long logId =  null;
		
		
		try {
			if (!UraUtils.isNotNull(strLogId) || "".equals(strLogId)) {
				logId = obtemLogDao().buscarLogId();
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.LOGID, Long.toString(logId), false);
				jsonContext = addLogContext(jsonContext, logId);
				
			} else {
				return jsonContext;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				logId = obtemLogDao().buscarLogId();
				jsonContext = addLogContext(jsonContext, logId);
			} catch (Exception e1) {}
			jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.LOGID, Long.toString(logId), false);
		}
		LogDto log = new LogDto();
		log.setId(logId);
		log.setUcid(MethodInvocationUtils.getContextValue(jsonContext, MapValues.UCID));
		try {
			Long ani = Long.parseLong(MethodInvocationUtils.getContextValue(jsonContext, MapValues.ANI));
			log.setAni(ani.toString());
		} catch (Exception e){
			logger.info("Erro ao Recuperar ANI para a [instancia] "
					+ MethodInvocationUtils.getContextValue(jsonContext, MapValues.INSTANCE));
			log.setAni(null);
			e.printStackTrace();
		}
		log.setDnis(MethodInvocationUtils.getContextValue(jsonContext, MapValues.DNIS));
		log.setDocumento(MethodInvocationUtils.getContextValue(jsonContext, MapValues.DOCUMENT));
		log.setInstance(MethodInvocationUtils.getContextValue(jsonContext, MapValues.INSTANCE));
		log.setProtocolId(MethodInvocationUtils.getContextValue(jsonContext, MapValues.PROTOCOLID));
		log.setProtocolNumber(MethodInvocationUtils.getContextValue(jsonContext, MapValues.PROTOCOLNUMBER));
		
		log.setDdd(MethodInvocationUtils.getContextValue(jsonContext, MapValues.DDD));
		log.setCidade(MethodInvocationUtils.getContextValue(jsonContext, MapValues.CIDADE));
		log.setUf(MethodInvocationUtils.getContextValue(jsonContext, MapValues.UF));
		log.setAging(MethodInvocationUtils.getContextValue(jsonContext, MapValues.AGING));
		log.setVdn(MethodInvocationUtils.getContextValue(jsonContext, MapValues.VDN));
		
		String perfil = MethodInvocationUtils.getContextValue(jsonContext, MapValues.PERFIL);
		if (perfil == null || perfil.isEmpty()) {
		}
		log.setPerfil(perfil);
		log.setContext(jsonContext);
		
		try {
			obtemLogDao().inserirLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonContext;
	}
	
	public static void createTrack(String jsonContext, NextFormDto nextForm, long trackId) {
		
		TrackDto track = new TrackDto();
		try {
			long logId = Long.parseLong(MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID));
			
			track.setId(trackId);
			track.setLogId(logId);
			track.setFormId(nextForm.getId());
			track.setTagId(nextForm.getTag());
			
			obtemLogDao().inserirTrack(track);
			
			if (nextForm.getTag() > 0) {
				createTrackTag(getTrackServiceId(), trackId, logId, nextForm.getTag());
			}
			
		} catch (Exception e) {e.printStackTrace();}
		
	}
	
	public static void createTrackCondition(String jsonContext, long trackId) {
		
		TrackDto track = new TrackDto();
		try {
			track.setId(trackId);
			track.setLogId(Long.parseLong(MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID)));
			track.setFormId(555);
			
			obtemLogDao().inserirTrack(track);
			
		} catch (Exception e) {e.printStackTrace();}
		
	}
	
	public static void createTrackService(long id, long trackId, long groupid,
			String method_service, String result_call, String parameters_in, long errorCode, long logId, double time_exec) {
		
		TrackServiceDto track = new TrackServiceDto();
		try {
			track.setId(id);
			track.setTrackid(trackId);
			track.setGroupid(groupid);
			track.setMethod_service(method_service);
			track.setResult_call(result_call);
			track.setParameters_in(parameters_in);
			track.setErrorCode(errorCode);
			track.setLogId(logId);
			track.setTime_exec(time_exec);
			
			obtemLogDao().inserirTrackService(track);
			
		} catch (Exception e) {e.printStackTrace();}
		
	}
	
	public static void createTrackTag(long id, long trackId, long logId, long tagId) {
		try {
			obtemLogDao().inserirTrackTag(id, trackId, logId, tagId);
		} catch (Exception e) {e.printStackTrace();}
		
	}
	
	public static void finalizaLog(String logId, String jsonContext, String status) {

		try {
			obtemLogDao().updateLog(logId, jsonContext, status);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void createLogDetail(String paramName, String paramValue, long logId) {
		LogDetailDto logDetail = new LogDetailDto();
		
		try {
			logDetail.setLogId(logId);
			
			logDetail.setParamName(paramName);
			logDetail.setParamValue(paramValue);
			obtemLogDao().inserirLogDetail(logDetail);
			
		} catch (Exception e) {e.printStackTrace();}
		
	}

	public static void createTrackDetail(String paramName, String paramValue, long trackId, long logId) {
		try {
			
			obtemLogDao().inserirTrackDetail(trackId, paramName, paramValue, logId);
			
		} catch (Exception e) {e.printStackTrace();}
		
	}
	public static String addLogContext(String jsonContext, long idLog) {
		
		String log = MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOG);
		log = log +";{logid:"+idLog+"}";

		return MethodInvocationUtils.setContextValue(jsonContext, MapValues.LOG, log, true);
	}
	public static long getTrackId() throws Exception {
		return obtemLogDao().buscarTrackId();
	}
	public static long getTrackServiceId() {
		Long retorno = null;
		try {
			retorno = obtemLogDao().buscarTrackServiceId();
		} catch (Exception e) {e.printStackTrace();}
		return retorno;
	}
	public static boolean  isRetencao(long logId) {
		boolean retorno = false;
		try {
			retorno = obtemLogDao().isRetencao(logId);
		} catch (Exception e) {e.printStackTrace();}
		return retorno;
	}
	

}
