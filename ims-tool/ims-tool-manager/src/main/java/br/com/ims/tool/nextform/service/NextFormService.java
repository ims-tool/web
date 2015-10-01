package br.com.ims.tool.nextform.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.print.DocFlavor.READER;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.ims.tool.nextform.model.AnnounceDto;
import br.com.ims.tool.nextform.model.AudioDto;
import br.com.ims.tool.nextform.model.DecisionChanceDto;
import br.com.ims.tool.nextform.model.DecisionDto;
import br.com.ims.tool.nextform.model.DecisionGroupDto;
import br.com.ims.tool.nextform.model.DecisionParametersDto;
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
import br.com.ims.tool.nextform.model.Response;
import br.com.ims.tool.nextform.model.TransferDto;
import br.com.ims.tool.nextform.model.TransferenciaIn;
import br.com.ims.tool.nextform.model.TransferenciaOut;
import br.com.ims.tool.nextform.persistence.NextFormDao;
import br.com.ims.tool.nextform.persistence.TransferenciaDao;
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
	public NextFormDto getNextFormId(Request request) {
		String jsonContext = request.getContext();
		NextFormDto nextForm = getNextFormByNextId(jsonContext, request.getNextId());
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
				
				
								
				TransferenciaDao transferenciaDAO = new TransferenciaDao();
				
				
				TransferDto transfer = dao.getTransferById(nextForm.getFormid());
				
				TransferenciaIn transferenciaIn = new TransferenciaIn();
				transferenciaIn.setPontoLog(String.valueOf(transfer.getTransferRuleId()));
				
				StringBuffer parametros = new StringBuffer();
			 	UraUtils.concatenaParametros("ANI", MethodInvocationUtils.getContextValue(jsonContext, MapValues.ANI), parametros);
				UraUtils.concatenaParametros("INSTANCIA", MethodInvocationUtils.getContextValue(jsonContext, MapValues.INSTANCE), parametros);
				UraUtils.concatenaParametros("DNIS", MethodInvocationUtils.getContextValue(jsonContext, MapValues.DNIS), parametros);
				UraUtils.concatenaParametros("DOCUMENTO", MethodInvocationUtils.getContextValue(jsonContext, MapValues.DOCUMENT), parametros);
				UraUtils.concatenaParametros("PERFIL", MethodInvocationUtils.getContextValue(jsonContext, MapValues.PERFIL), parametros);
				UraUtils.concatenaParametros("URA", MethodInvocationUtils.getContextValue(jsonContext, MapValues.URA), parametros);
				UraUtils.concatenaParametros("BLOQUEIO", MethodInvocationUtils.getContextValue(jsonContext, MapValues.BLOQUEIO_CLIENTE_BLOQ), parametros);
				
				transferenciaIn.setParametros(parametros.toString());
				TransferenciaOut transferenciaOut = null;
				long errorCode = FormConstants.NO_ERROR;
				
				try {
					transferenciaOut = transferenciaDAO.getTransferencia(transferenciaIn);
				} catch (Exception e) {
					errorCode = FormConstants.ERROR_REMOTE_EXCEPTION;
				}
				
				
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.VDN, transferenciaOut.getVdn(), true);
				nextForm.setJsonContexto(jsonContext);
				
				transfer.setVdn(transferenciaOut.getVdn());
				transfer.setPrompt(dao.getPromptByPromptName(transferenciaOut.getMsg(), jsonContext));
				transfer.setUUI(UraUtils.gerarUUI(jsonContext));
				nextForm.setTransfer(transfer);
				
				long tsId = LogUtils.getTrackServiceId();
				
				LogUtils.createTrackService(tsId, trackId, 1, "Transferência VDN",
						transferenciaOut.getVdn(), String.valueOf(transfer.getTransferRuleId()), errorCode, logId, 0);
				
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
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		nextForm.setJsonContexto(jsonContext);
		return nextForm;
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
					serviceReturn = invocationService.invoke(context, operationGroup.getOperationMap().getMethodReference(), map, operationGroup.getOperationMap().getTimeout(), operationGroup.getOperationMap().isActive());
					
					context = serviceReturn.getJsonContext();
					
					if  (operationGroup.getOperationMap().getLogActive() > 0) {
						LogUtils.createTrackService(LogUtils.getTrackServiceId(), trackId, operationGroup.getId(), operationGroup.getOperationMap()
								.getMethodReference(), serviceReturn.getValue(), param, serviceReturn.getErrorCode(), logId, serviceReturn.getTimeService());
					}
					
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
		Long decisionGroupChild = null;
		MethodInvocationVO serviceReturn = null;
		NextFormDao dao = new NextFormDao();

		DecisionDto decision = null;
		try {
			decision = dao.getDecisionById(nextForm.getFormid());
		} catch (Exception e) {
			
		}
		
		for (DecisionGroupDto decisionGroup : decision.getListaDecisionGroup()) {
			
			long trackServiceId = LogUtils.getTrackServiceId();
			
			if ((!UraUtils.isNotNull(decisionGroupChild)) || decisionGroupChild == decisionGroup.getId()) {

				decisionGroupChild = null;
				Map<String, String> map = null;
				if (UraUtils.isNotNull(decisionGroup.getListaDecisionParameters())
						&& !decisionGroup.getListaDecisionParameters().isEmpty()) {
					map = new HashMap<String, String>();
					for (DecisionParametersDto decisionParameters : decisionGroup.getListaDecisionParameters()) {
						map.put(decisionParameters.getParamName(), decisionParameters.getParamValue());
					}
				}
				String param = null;
				if (UraUtils.isNotNull(map)) {
					param = map.toString();
				}
				try {
					MethodInvocation invocationService = new MethodInvocation();
					serviceReturn =  invocationService.invoke(nextForm.getJsonContexto(), decisionGroup.getDecisionMap().getMethodReference(), map, decisionGroup.getDecisionMap().getTimeout(), decisionGroup.getDecisionMap().isActive());
					nextForm.setJsonContexto(serviceReturn.getJsonContext());
					
					if (decisionGroup.getDecisionMap().getLogActive() > 0) {
						LogUtils.createTrackService(trackServiceId, trackId, decisionGroup.getId(), decisionGroup
								.getDecisionMap().getMethodReference(), serviceReturn.getValue(),
								param, serviceReturn.getErrorCode(), logId, serviceReturn.getTimeService());
					}
					
				} catch (Exception e) {
					
					LogUtils.createTrackService(trackServiceId, trackId, decisionGroup.getId(), decisionGroup
							.getDecisionMap().getMethodReference(), e.getMessage(),param, FormConstants.ERROR_GENERIC_EXCEPTION, logId, 0);
					LogUtils.createLogDetail(FormConstants.ERRO, e.getMessage(), logId);
					serviceReturn = MethodInvocationVO.getInstance(nextForm.getJsonContexto());
				}
				
				for (DecisionChanceDto decisionChance : decisionGroup.getListaDecisionChance()) {

					if (UraUtils.isNotNull(decisionChance.getOperation())) {
						if (processaOperacao(decisionChance.getOperation(),
								decisionGroup.getDecisionMap().getType(), serviceReturn.getValue(), decisionChance, serviceReturn.getJsonContext(), logId)) {
							
							
							if (UraUtils.isNotNull(decisionChance) && decisionChance.getTag() > 0) {
								LogUtils.createTrackTag(trackServiceId, trackId, logId, decisionChance.getTag());
							}
							
							if(decisionChance.getNextForm() == -1){
								try {
									String tempNextFormId = MethodInvocationUtils.getContextValue(serviceReturn.getJsonContext(), MapValues.TEMP_NEXTFORMID);
									if(tempNextFormId == null)
										tempNextFormId = "-1";
									
										return getNextFormByNextId(serviceReturn.getJsonContext(), Long.parseLong(tempNextFormId));
								} catch (Exception e) {
									LogUtils.createLogDetail(FormConstants.ERRO, e.getMessage(), logId);
									continue;
								}
							}else if (decisionChance.getNextForm() > 0) {
								try {
									return getNextFormByNextId(serviceReturn.getJsonContext(), decisionChance.getNextForm());
								} catch (Exception e) {
									LogUtils.createLogDetail(FormConstants.ERRO, e.getMessage(), logId);
									continue;
								}
							} else {
								decisionGroupChild = decisionChance.getDecisionGroupChild();
								break;
							}
						}
					} else {
						
						if (UraUtils.isNotNull(decisionChance) && decisionChance.getTag() > 0) {
							LogUtils.createTrackTag(trackServiceId, trackId, logId, decisionChance.getTag());
						}
						
						if(decisionChance.getNextForm() == -1){
							try {
								String tempNextFormId = MethodInvocationUtils.getContextValue(serviceReturn.getJsonContext(), MapValues.TEMP_NEXTFORMID);
								if(tempNextFormId != null)
									return getNextFormByNextId(serviceReturn.getJsonContext(), Long.parseLong(tempNextFormId));
								else
									decisionGroupChild = decisionChance.getDecisionGroupChild();
							} catch (Exception e) {
								LogUtils.createLogDetail(FormConstants.ERRO, e.getMessage(), logId);
								continue;
							}
						}else if (decisionChance.getNextForm() > 0) {
							try {
								return getNextFormByNextId(serviceReturn.getJsonContext(), decisionChance.getNextForm());
							} catch (Exception e) {
								LogUtils.createLogDetail(FormConstants.ERRO, e.getMessage().toString(), logId);
								continue;
							}
						} else {
							decisionGroupChild = decisionChance.getDecisionGroupChild();
							break;
						}
					}
				}
			}

		}
		NextFormDto nextformdefault = null;
		try {
			nextformdefault = getNextFormByNextId(serviceReturn.getJsonContext(), FormConstants.FORM_DEFAULT);
		} catch (Exception e) {
			LogUtils.createLogDetail(FormConstants.ERRO, e.getMessage(), logId);
		}
		return nextformdefault;
	}
	

	private boolean processaOperacao(String operacao, String type,
			String resultado, DecisionChanceDto decisionChance, String jsonContext, long logId) {
		try {
			if (FormConstants.TYPE_TEXT.equals(type)) {
				return UraUtils.validateTypeText(operacao, resultado,
						decisionChance.getValue1(), getValuesNotNull(decisionChance, jsonContext));
			} else if (FormConstants.TYPE_NUMERIC.equals(type)) {
				return UraUtils.validateTypeNumeric(
						operacao, resultado, decisionChance.getValue1(),
							decisionChance.getValue2(), getValuesNotNull(decisionChance, jsonContext));
			} else if (FormConstants.TYPE_DATE.equals(type)) {
				return UraUtils.validateTypeDate(
						operacao, resultado, decisionChance.getValue1(),
							decisionChance.getValue2(), getValuesNotNull(decisionChance, jsonContext));
			}
		} catch (Exception e) {
			LogUtils.createLogDetail("processaOperacao.decisionChance", e.getMessage(), logId);
			return false;
		}
		return false;

	}
	
	
	private Collection<String> getValuesNotNull(DecisionChanceDto decisionChance, String jsonContext)  {
		Collection<String> lista = new ArrayList<String>();
		
		try {

			if (UraUtils.isNotNull(decisionChance.getValue1())) {
				lista.add(decisionChance.getValue1());
			}
			if (UraUtils.isNotNull(decisionChance.getValue2())) {
				lista.add(decisionChance.getValue2());
			}
			if (UraUtils.isNotNull(decisionChance.getValue3())) {
				lista.add(decisionChance.getValue3());
			}
			if (UraUtils.isNotNull(decisionChance.getValue4())) {
				lista.add(decisionChance.getValue4());
			}
			if (UraUtils.isNotNull(decisionChance.getValue5())) {
				lista.add(decisionChance.getValue5());
			}
			if (UraUtils.isNotNull(decisionChance.getValue6())) {
				lista.add(decisionChance.getValue6());
			}
			if (UraUtils.isNotNull(decisionChance.getValue7())) {
				lista.add(decisionChance.getValue7());
			}
			if (UraUtils.isNotNull(decisionChance.getValue8())) {
				lista.add(decisionChance.getValue8());
			}
			if (UraUtils.isNotNull(decisionChance.getValue9())) {
				lista.add(decisionChance.getValue9());
			}
			if (UraUtils.isNotNull(decisionChance.getValue10())) {
				lista.add(decisionChance.getValue10());
			}
		} catch (Exception e) {
			LogUtils.createLogDetail("getValuesNotNull.decisionChance", e.getMessage(), Long.parseLong(MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID)));
		}
		return lista;
		
	}

}
