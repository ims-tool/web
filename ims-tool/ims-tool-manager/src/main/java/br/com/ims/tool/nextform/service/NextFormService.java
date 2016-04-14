package br.com.ims.tool.nextform.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import br.com.ims.tool.nextform.model.AnnounceDto;
import br.com.ims.tool.nextform.model.AnswerDto;
import br.com.ims.tool.nextform.model.AudioDto;
import br.com.ims.tool.nextform.model.DecisionChanceDto;
import br.com.ims.tool.nextform.model.DecisionDto;
import br.com.ims.tool.nextform.model.DisconnectDto;
import br.com.ims.tool.nextform.model.FlowDto;
import br.com.ims.tool.nextform.model.MenuDto;
import br.com.ims.tool.nextform.model.MethodInvocationVO;
import br.com.ims.tool.nextform.model.NextFormDto;
import br.com.ims.tool.nextform.model.OperationDto;
import br.com.ims.tool.nextform.model.OperationGroupDto;
import br.com.ims.tool.nextform.model.OperationParametersDto;
import br.com.ims.tool.nextform.model.PromptAudioDto;
import br.com.ims.tool.nextform.model.PromptCollectDto;
import br.com.ims.tool.nextform.model.PromptDto;
import br.com.ims.tool.nextform.model.Request;
import br.com.ims.tool.nextform.model.ReturnDto;
import br.com.ims.tool.nextform.model.TransferDto;
import br.com.ims.tool.nextform.model.TransferRuleDto;
import br.com.ims.tool.nextform.persistence.NextFormDao;
import br.com.ims.tool.nextform.persistence.TransferDao;
import br.com.ims.tool.nextform.util.FormConstants;
import br.com.ims.tool.nextform.util.LogUtils;
import br.com.ims.tool.nextform.util.MapValues;
import br.com.ims.tool.nextform.util.MethodInvocationUtils;
import br.com.ims.tool.nextform.util.MethodsCatalog;
import br.com.ims.tool.nextform.util.UraUtils;

@Stateless
@Path("nextformid")
public class NextFormService {
	


	@POST
	@Produces({ "application/json" })
	@Consumes("application/json")
	public NextFormDto getNextFormId(String entity) {
		
		JSONObject jsonObj = new JSONObject(entity);
		String jsonContext = jsonObj.getString("context");
		NextFormDto nextForm = getNextFormByNextId(jsonContext, jsonObj.getLong("nextFormId"));
		return nextForm;
	}

	private NextFormDto getNextFormByNextId(String jsonContext, Long nextFormId) {
		NextFormDto nextForm = null;
		Long trackId = null;
		NextFormDao dao = new NextFormDao();
		
		try {
			nextForm = dao.getNextFormByNextId(nextFormId);
			
			jsonContext = LogUtils.createLog(jsonContext, nextForm);
			long logId = Long.parseLong(MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID));
			
			if (nextForm == null) {
				LogUtils.createLogDetail(FormConstants.FORM_NAO_ENCONTRADO, String.valueOf(nextFormId), logId);
				nextForm = dao.getNextFormByNextId(FormConstants.FORM_DEFAULT);
			}
			trackId = LogUtils.getTrackId();
			LogUtils.createTrack(jsonContext, nextForm, trackId);
			jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.TRACKID, String.valueOf(trackId), true);
			
			nextForm.setJsonContexto(jsonContext);
			
			if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_ANNOUNCE) {
				AnnounceDto announce = dao.getAnnounceById(nextForm.getFormid(), jsonContext);
				
				
				if (UraUtils.isNotNull(announce) && announce.getTag() > 0) {
					LogUtils.createTrackTag(LogUtils.getTrackServiceId(), trackId, logId, announce.getTag());
				}
				
				if ("VocMsgContigencia".equalsIgnoreCase(announce.getName())) {
					Collection<PromptAudioDto> listaPromptAudio = new ArrayList<PromptAudioDto>();
					
					PromptAudioDto promptAudio = new PromptAudioDto();
					promptAudio.setPrompt(announce.getPrompt());
					promptAudio.setAudio(-1);
					promptAudio.setPromptOrder(0);
	
					AudioDto audio = new AudioDto();
					audio.setId(0);
					audio.setType("WAV");
					audio.setName(MethodInvocationUtils.getContextValue(jsonContext, MapValues.MSG_CONT_AUDIO_NAME));
					audio.setDescription("Msg Contingencia");
					audio.setPath(MethodInvocationUtils.getContextValue(jsonContext, MapValues.MSG_CONT_AUDIO));
					
					promptAudio.setAudioDto(audio);
					listaPromptAudio.add(promptAudio);
					PromptDto promptDto= announce.getPromptDto();
					promptDto.setListaPromptAudio(listaPromptAudio);
					
					announce.setPromptDto(promptDto);
				}
				nextForm.setAnnounce(announce);
				
			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_PROMPT_COLLECT) {
				PromptCollectDto promptCollect = dao.getPromptCollectById(nextForm.getFormid(), jsonContext);
				nextForm.setPromptCollect(promptCollect);
				
				if (UraUtils.isNotNull(promptCollect) && promptCollect.getTag() > 0) {					
					LogUtils.createTrackTag(LogUtils.getTrackServiceId(), trackId, logId, promptCollect.getTag());
				}
				
			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_MENU) {
				MenuDto menu =
						dao.getMenuByMenuId(nextForm.getFormid(), jsonContext);
				nextForm.setMenu(menu);
				
			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_FLOW) {
				
				FlowDto flow =
						dao.getFlowById(nextForm.getFormid());
				nextForm.setFlow(flow);
				
				if (UraUtils.isNotNull(flow) && flow.getTag() > 0) {
					LogUtils.createTrackTag(LogUtils.getTrackServiceId(), trackId, logId, flow.getTag());
				}

			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_FLOW_INTERNAL) {
				
				FlowDto flow =
						dao.getFlowById(nextForm.getFormid());
				
				flow.setFlowFirstForm(dao.getFormIdByFlowName(flow.getFlowName()));
				nextForm.setFlow(flow);
				
				if (UraUtils.isNotNull(flow) && flow.getTag() > 0) {
					LogUtils.createTrackTag(LogUtils.getTrackServiceId(), trackId, logId, flow.getTag());
				}
				
			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_DECISION) {
				
				nextForm = processDecision(nextForm, trackId, logId);
				if (nextForm == null) {
					LogUtils.createLogDetail(FormConstants.FORM_NAO_ENCONTRADO, String.valueOf(nextFormId), logId);

				}
				
			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_OPERATION) {
				
				nextForm = processOperation(nextForm, trackId, logId);
				
			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_TRANSFER) {
				
			
				TransferDto transfer = dao.getTransferById(nextForm.getFormid());
				TransferRuleDto rule = processTransfer(transfer.getId(),jsonContext);
				int errorCode = 0;
				if(rule != null) {
					
					jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.VDN, rule.getNumber(), true);
					nextForm.setJsonContexto(jsonContext);
					
					transfer.setVdn(rule.getNumber());
					transfer.setTag(rule.getTag());
					transfer.setPrompt(null);
					if(rule.getPrompt() >  0) {
						transfer.setPrompt(dao.getPromptById(rule.getPrompt(), jsonContext));
					}
				} else {
					errorCode = -1;
				}
				transfer.setUUI(MethodInvocationUtils.getContextValue(jsonContext, MapValues.PARTNER));
				//transfer.setUUI(UraUtils.gerarUUI(jsonContext));
				nextForm.setTransfer(transfer);
				
				long tsId = LogUtils.getTrackServiceId();
				
				LogUtils.createTrackService(tsId, trackId, 1, "Transferência VDN",
						rule.getNumber(), String.valueOf(transfer.getTransferRuleId()), errorCode, logId, 0);
				
				LogUtils.finalizaLog(MethodInvocationUtils.getContextValue(
						jsonContext, MapValues.LOGID), jsonContext, FormConstants.TRANSFER_STATUS);
				
				if (UraUtils.isNotNull(transfer) && transfer.getTag() > 0) {
					LogUtils.createTrackTag(tsId, trackId, logId, transfer.getTag());
				}
				
			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_DISCONNECT) {
				
				MethodsCatalog.servicesDisconnect(jsonContext, null);
				
				DisconnectDto disconnect = dao.getDisconnectById(nextForm.getFormid());
				nextForm.setDisconnect(disconnect);
				
				//verifica se ligação passou por tag de retenção
				if (LogUtils.isRetencao(Long.parseLong(MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID)))) {
					LogUtils.finalizaLog(MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID), jsonContext, FormConstants.RETENCAO_STATUS);
				} else {
					LogUtils.finalizaLog(MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID), jsonContext, FormConstants.ABANDONO_STATUS);
				}
				if (UraUtils.isNotNull(disconnect) && disconnect.getTag() > 0) {
					LogUtils.createTrackTag(LogUtils.getTrackServiceId(), trackId, logId, disconnect.getTag());
				}
			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_ANSWER){
				
					AnswerDto answer = dao.getAnwserById(nextForm.getFormid(), jsonContext);
					
					if (UraUtils.isNotNull(answer)) {
						if(answer.getTag() > 0) {
							LogUtils.createTrackTag(LogUtils.getTrackServiceId(), trackId, logId, answer.getTag());
						}
						
						nextForm = getNextFormByNextId(jsonContext, answer.getNextForm());
					}					
			} else if (nextForm.getFormTypeDto().getId() == FormConstants.TYPE_RETURN){
				
				ReturnDto return_ = dao.getReturnById(nextForm.getFormid(), jsonContext);
				
				if (UraUtils.isNotNull(return_)) {
					if(return_.getTag() > 0) {
						LogUtils.createTrackTag(LogUtils.getTrackServiceId(), trackId, logId, return_.getTag());
					}
				}		
				nextForm.setReturn_(return_);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		nextForm.setJsonContexto(jsonContext);
		return nextForm;
	}
	

	private TransferRuleDto processTransfer(long transferId, String jsonContexto) throws Exception {
		NextFormDao dao = new NextFormDao();
		TransferDao transferDao = new TransferDao();
		
		List<TransferRuleDto> listTransferRule = transferDao.getListTransferRule(transferId);
		
		
		for (TransferRuleDto rule : listTransferRule) {
			
			boolean condition = true;
			if(rule.getCondition() > 0) {
				try {
					condition = dao.validarCondition(jsonContexto, rule.getCondition());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 				
			} 
			if(condition) {
				return rule;
			}

		}
		return null;
	}
	
	private NextFormDto processOperation(NextFormDto nextForm, long trackId, long logId) throws Exception {
		MethodInvocationVO serviceReturn = null;
		OperationDto operation = null;
		String param = null;
		String context = null;
		NextFormDao dao = new NextFormDao();
		
		try {
			
			operation = dao.getOperationById(nextForm.getFormid());
			
			if (UraUtils.isNotNull(operation) && operation.getTag() > 0) {
				LogUtils.createTrackTag(LogUtils.getTrackServiceId(), trackId, logId, operation.getTag());
			}
			context = nextForm.getJsonContexto();
			
			for (OperationGroupDto operationGroup : operation.getListaOperationGroup()) {
				
				Map<String, String> map = null;
				if (UraUtils.isNotNull(operationGroup.getListaOperationParameters())
						&& !operationGroup.getListaOperationParameters().isEmpty()) {
					map = new HashMap<String, String>();
					for (OperationParametersDto operationParameters : operationGroup.getListaOperationParameters()) {
						map.put(operationParameters.getParamName(), operationParameters.getParamValue());
					}
				}
				if (UraUtils.isNotNull(map)) {
					param = map.toString();
				}
				try {
					MethodInvocation invocationService = new MethodInvocation();
					if (operationGroup.getOperationMap().isInternalService()){
						serviceReturn = invocationService.invoke(context, operationGroup.getOperationMap().getMethodReference(), map, operationGroup.getOperationMap().getTimeout(), operationGroup.getOperationMap().isActive());
					}else{
						serviceReturn = invocationService.invokeExternalService(context, operationGroup.getOperationMap().getMethodReference(), map, operationGroup.getOperationMap().getTimeout(), operationGroup.getOperationMap().isActive());
					}
					
					context = serviceReturn.getJsonContext();
					
//					if  (operationGroup.getOperationMap().getLogActive() > 0) {
//						LogUtils.createTrackService(LogUtils.getTrackServiceId(), trackId, operationGroup.getId(), operationGroup.getOperationMap().getMethodReference(), serviceReturn.getValue(), param, serviceReturn.getErrorCode(), logId, serviceReturn.getTimeService());
//					}
					
				} catch (Exception e) {
					LogUtils.createTrackService(LogUtils.getTrackServiceId(), trackId, operationGroup.getId(), e.getMessage(), serviceReturn.getValue(), param, FormConstants.ERROR_GENERIC_EXCEPTION, logId, 0);
					LogUtils.createLogDetail(FormConstants.ERRO, e.getMessage(), logId);
					throw new Exception("Erro ao Comparar o retorno do Serviço: " + nextForm.getId(), e);
				}
				
				
			}
			
		}catch (Exception e) {
			LogUtils.createLogDetail(FormConstants.ERRO, e.getMessage(), logId);
			throw new Exception("Erro ao Recuperar Next Form para o nextId: " + nextForm.getId(), e);
		}
		
		return getNextFormByNextId(context, operation.getNextform());
	}
	
	
	private NextFormDto processDecision(NextFormDto nextForm, long trackId, long logId) {
		
		NextFormDao dao = new NextFormDao();

		DecisionDto decision = null;
		try {
			decision = dao.getDecisionById(nextForm.getFormid());
		} catch (Exception e) {
		}
		
		for (DecisionChanceDto decisionChance : decision.getListaDecisionChance()) {
			
			long trackServiceId = LogUtils.getTrackServiceId();
			boolean condition = true;
			if(decisionChance.getCondition() > 0) {
				try {
					condition = dao.validarCondition(nextForm.getJsonContexto(), decisionChance.getCondition());
				} catch (Exception e) {
					e.printStackTrace();
				} 				
			} 
			if(condition) {
				if (UraUtils.isNotNull(decisionChance) && decisionChance.getTag() > 0) {
					LogUtils.createTrackTag(trackServiceId, trackId, logId, decisionChance.getTag());
				}
				return getNextFormByNextId(nextForm.getJsonContexto(), decisionChance.getNextForm());
			}

		}
		NextFormDto nextformdefault = null;
		try {
			nextformdefault = getNextFormByNextId(nextForm.getJsonContexto(), FormConstants.FORM_DEFAULT);
		} catch (Exception e) {
			LogUtils.createLogDetail(FormConstants.ERRO, e.getMessage(), logId);
		}
		return nextformdefault;
	}
	

	
	
	
	

}
