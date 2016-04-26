package br.com.ims.tool.nextform.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.ims.tool.nextform.exception.DaoException;
import br.com.ims.tool.nextform.model.AnnounceDto;
import br.com.ims.tool.nextform.model.AnswerDto;
import br.com.ims.tool.nextform.model.AudioDto;
import br.com.ims.tool.nextform.model.AudioVarDto;
import br.com.ims.tool.nextform.model.ChoiceDto;
import br.com.ims.tool.nextform.model.ConditionDto;
import br.com.ims.tool.nextform.model.ConditionGroupDto;
import br.com.ims.tool.nextform.model.ConditionParametersDto;
import br.com.ims.tool.nextform.model.ConditionValueDto;
import br.com.ims.tool.nextform.model.DecisionChanceDto;
import br.com.ims.tool.nextform.model.DecisionConditionDto;
import br.com.ims.tool.nextform.model.DecisionDto;
import br.com.ims.tool.nextform.model.DecisionParametersDto;
import br.com.ims.tool.nextform.model.DisconnectDto;
import br.com.ims.tool.nextform.model.FlowDto;
import br.com.ims.tool.nextform.model.FormTypeDto;
import br.com.ims.tool.nextform.model.GrammarDto;
import br.com.ims.tool.nextform.model.MenuDto;
import br.com.ims.tool.nextform.model.MethodInvocationVO;
import br.com.ims.tool.nextform.model.NextFormDto;
import br.com.ims.tool.nextform.model.NoMatchInputDto;
import br.com.ims.tool.nextform.model.OperationDto;
import br.com.ims.tool.nextform.model.OperationGroupDto;
import br.com.ims.tool.nextform.model.OperationMapDto;
import br.com.ims.tool.nextform.model.OperationParametersDto;
import br.com.ims.tool.nextform.model.PromptAudioDto;
import br.com.ims.tool.nextform.model.PromptCollectDto;
import br.com.ims.tool.nextform.model.PromptDto;
import br.com.ims.tool.nextform.model.ReturnDto;
import br.com.ims.tool.nextform.model.TransferDto;
import br.com.ims.tool.nextform.service.MethodInvocation;
import br.com.ims.tool.nextform.util.ConnectionDB;
import br.com.ims.tool.nextform.util.FormConstants;
import br.com.ims.tool.nextform.util.LogUtils;
import br.com.ims.tool.nextform.util.MapValues;
import br.com.ims.tool.nextform.util.MethodInvocationUtils;
import br.com.ims.tool.nextform.util.UraConstants;
import br.com.ims.tool.nextform.util.UraUtils;

public class NextFormDao {

	private static Logger logger = Logger.getLogger(NextFormDao.class);

	public NextFormDto getNextFormByNextId(long nextId) {

		NextFormDto nextForm = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT NF.ID, NF.NAME, NF.DESCRIPTION, NF.FORMTYPE, NF.FORMID, NF.TAG, FT.ID, FT.NAME, NF.NEXTFORMDEFAULT "
					+ " FROM FLOW.FORM NF, FLOW.FORMTYPE FT  WHERE NF.ID = " + nextId + "  AND NF.FORMTYPE = FT.ID ";

			rs = conn.ExecuteQuery(query);

			if (rs.next()) {
				nextForm = new NextFormDto();

				nextForm.setId(rs.getLong(1));
				nextForm.setName(rs.getString(2));
				nextForm.setDescription(rs.getString(3));
				nextForm.setFormtype(rs.getLong(4));
				nextForm.setFormid(rs.getLong(5));
				nextForm.setTag(rs.getLong(6));
				nextForm.setNextFormDefault(rs.getLong(9));
				FormTypeDto formTypeDto = new FormTypeDto();
				formTypeDto.setId(rs.getLong(7));
				formTypeDto.setName(rs.getString(8));
				nextForm.setFormTypeDto(formTypeDto);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o NextForm de ID: " + nextId, e);
		} finally {
			conn.finalize();
		}
		return nextForm;
	}

	public AnnounceDto getAnnounceById(long idAnnounce, String jsonContext) throws Exception {
		AnnounceDto announce = null;

		ResultSet rs2 = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = " SELECT AN.ID, AN.NAME, AN.DESCRIPTION, AN.FLUSHPROMPT, AN.PROMPT, AN.NEXTFORM, PR.ID, PR.NAME, AN.TAG "
					+ " FROM FLOW.ANNOUNCE AN, FLOW.PROMPT PR  WHERE AN.ID = " + idAnnounce
					+ "  AND AN.PROMPT = PR.ID ";

			rs2 = conn.ExecuteQuery(query);
			if (rs2.next()) {
				announce = new AnnounceDto();
				announce.setId(rs2.getLong(1));
				announce.setName(rs2.getString(2));
				announce.setDescription(rs2.getString(3));
				announce.setFlushprompt(rs2.getLong(4));
				announce.setPrompt(rs2.getLong(5));
				announce.setNextForm(rs2.getLong(6));
				announce.setTag(rs2.getLong(9));

				PromptDto prompt = new PromptDto();
				prompt.setId(rs2.getLong(7));
				prompt.setName(rs2.getString(8));
				prompt.setListaPromptAudio(getListPromptAudioByPromptId(prompt.getId(), jsonContext));

				announce.setPromptDto(prompt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Erro ao Recuperar o Announce de ID: " + idAnnounce, e);
			throw new Exception("Erro ao Recuperar o Announce de ID: " + idAnnounce, e);
		} finally {
			conn.finalize();
		}
		return announce;
	}

	public PromptCollectDto getPromptCollectById(long idpromptCollect, String jsonContext) throws Exception {
		PromptCollectDto promptCollect = new PromptCollectDto();

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT PC.ID, PC.NAME, PC.DESCRIPTION, PC.GRAMMAR, PC.FLUSHPROMPT, PC.PROMPT,"
					+ " PC.NOINPUT, PC.NOMATCH, PC.FETCHTIMEOUT, PC.INTERDIGITTIMEOUT,   PC.TERMINATINGTIMEOUT,"
					+ " PC.TERMINATINGCHARACTER, PC.NEXTFORM, PR.ID,   PR.NAME, GR.ID, GR.NAME, GR.DESCRIPTION,"
					+ " GR.TYPE, GR.SIZEMAX, GR.SIZEMIN, PC.TAG, PC.NOINPUT_NEXTFORM, PC.NOINPUT_TAG, PC.NOMATCH_NEXTFORM, PC.NOMATCH_TAG "
					+ " FROM FLOW.PROMPTCOLLECT PC   LEFT OUTER JOIN FLOW.GRAMMAR GR ON "
					+ " PC.GRAMMAR = GR.ID  LEFT OUTER JOIN FLOW.PROMPT PR ON PC.PROMPT = PR.ID   WHERE PC.ID ="
					+ idpromptCollect;

			rs = conn.ExecuteQuery(query);
			if (rs.next()) {
				promptCollect.setId(rs.getLong(1));
				promptCollect.setName(rs.getString(2));
				promptCollect.setDescription(rs.getString(3));
				promptCollect.setGrammar(rs.getLong(4));
				promptCollect.setFlushPrompt(rs.getBoolean(5));
				promptCollect.setPrompt(rs.getLong(6));
				promptCollect.setNoInput(rs.getLong(7));
				promptCollect.setNoMatch(rs.getLong(8));
				promptCollect.setFetchTimeout(rs.getLong(9));
				promptCollect.setInterdigitTimeout(rs.getLong(10));
				promptCollect.setTerminatingTimeout(rs.getLong(11));
				promptCollect.setTerminatingCharacter(rs.getString(12));
				promptCollect.setNextForm(rs.getLong(13));

				promptCollect.setTag(rs.getLong(22));

				PromptDto prompt = new PromptDto();
				prompt.setId(rs.getLong(14));
				prompt.setName(rs.getString(15));
				prompt.setListaPromptAudio(getListPromptAudioByPromptId(prompt.getId(), jsonContext));

				promptCollect.setPromptDto(prompt);

				GrammarDto grammar = new GrammarDto();
				grammar.setId(rs.getLong(16));
				grammar.setName(rs.getString(17));
				grammar.setDescription(rs.getString(18));
				grammar.setType(rs.getString(19));
				grammar.setSizeMax(rs.getLong(20));
				grammar.setSizeMin(rs.getLong(21));

				promptCollect.setGrammarDto(grammar);

				promptCollect.setNoInputDto(getNoMatchInputById(promptCollect.getNoInput(), rs.getLong("NOINPUT_NEXTFORM"), rs.getLong("NOINPUT_TAG"), jsonContext));
				promptCollect.setNoMatchDto(getNoMatchInputById(promptCollect.getNoMatch(), rs.getLong("NOMATCH_NEXTFORM"), rs.getLong("NOMATCH_TAG"), jsonContext));
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o PromptCollect de ID: " + idpromptCollect, e);
			throw new Exception("Erro ao Recuperar o PromptCollect de ID: " + idpromptCollect, e);
		} finally {
			conn.finalize();
		}
		return promptCollect;
	}

	public Collection<PromptAudioDto> getListPromptAudioByPromptId(long promptId, String jsonContext) throws Exception {
		Collection<PromptAudioDto> listaPromptAudio = new ArrayList<PromptAudioDto>();

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = " SELECT PA.PROMPT, PA.AUDIO, PA.ORDERNUM, AU.ID, AU.TYPE, AU.NAME, AU.DESCRIPTION, "
					+ " AU.PATH, PA.CONDITION FROM FLOW.PROMPTAUDIO PA, FLOW.AUDIO AU  " + " WHERE PA.PROMPT = "
					+ promptId + "  AND PA.AUDIO = AU.ID  ORDER BY PA.ORDERNUM ";

			rs = conn.ExecuteQuery(query);
			while (rs.next()) {
				boolean condition = true;
				if (rs.getLong(9) > 0) {
					condition = validarCondition(jsonContext, rs.getLong(9)); // buscar
																				// condition
																				// resultado
																				// condition
				}
				if (condition) {
					PromptAudioDto promptAudio = new PromptAudioDto();
					promptAudio.setPrompt(rs.getLong(1));
					promptAudio.setAudio(rs.getLong(2));
					promptAudio.setPromptOrder(rs.getLong(3));

					AudioDto audio = new AudioDto();
					audio.setId(rs.getLong(4));
					audio.setType(rs.getString(5));
					audio.setName(rs.getString(6));
					audio.setDescription(rs.getString(7));
					String path = MethodInvocationUtils.getContextValue(jsonContext, MapValues.AUDIO_PATH)+ "/" +rs.getString(8) + "/";
					path = path.replace("///", "/");
					path = path.replace("//", "/");
					path = path.replace(":/", "://");
					audio.setPath(path);

					if (UraConstants.VAR.equalsIgnoreCase(audio.getType())) {
						audio.setListAudioVar(getListAudioVarByAudioId(audio.getId()));

						audio.setValue(MethodInvocationUtils.getContextValue(jsonContext, audio.getPath()));

					}
					if (UraConstants.VAR_TTS.equalsIgnoreCase(audio.getType())) {
						audio.setType(UraConstants.TTS);
						audio.setDescription(MethodInvocationUtils.getContextValue(jsonContext, audio.getPath()));
						audio.setName(MethodInvocationUtils.getContextValue(jsonContext, audio.getPath()));
					}

					if (UraConstants.V_WAV.equalsIgnoreCase(audio.getType())) {
						audio.setType(UraConstants.WAV);
						audio.setDescription(MethodInvocationUtils.getContextValue(jsonContext, audio.getName()));
						audio.setName(MethodInvocationUtils.getContextValue(jsonContext, audio.getName()));
						path = MethodInvocationUtils.getContextValue(jsonContext, MapValues.AUDIO_PATH)+ "/msg/";
						path = path.replace("///", "/");
						path = path.replace("//", "/");
						path = path.replace(":/", "://");
						audio.setPath(path);

						// Nao carrega o prompt audio com name vazio
						if ("".equals(audio.getName()))
							continue;
					}

					promptAudio.setAudioDto(audio);

					listaPromptAudio.add(promptAudio);
				}
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o PromptAudio de ID: " + promptId, e);
			throw new Exception("Erro ao Recuperar o PromptAudio de ID: " + promptId, e);
		} finally {
			conn.finalize();
		}
		return listaPromptAudio;
	}

	public Collection<AudioVarDto> getListAudioVarByAudioId(long audioId) throws Exception {
		Collection<AudioVarDto> listaAudioVar = new ArrayList<AudioVarDto>();

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = " SELECT ID, AUDIOID, PARAMNAME, PARAMVALUE FROM FLOW.AUDIOVAR WHERE AUDIOID = " + audioId;

			rs = conn.ExecuteQuery(query);

			while (rs.next()) {
				AudioVarDto audioVar = new AudioVarDto();
				audioVar.setId(rs.getLong(1));
				audioVar.setAudioId(rs.getLong(2));
				audioVar.setParamName(rs.getString(3));
				audioVar.setParamValue(rs.getString(4));

				listaAudioVar.add(audioVar);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o AudioVar de ID: " + audioId, e);
			throw new Exception("Erro ao Recuperar o AudioVar de ID: " + audioId, e);
		} finally {
			conn.finalize();
		}
		return listaAudioVar;
	}

	public NoMatchInputDto getNoMatchInputById(long id, long nextForm, long tag, String jsonContext) throws Exception {
		NoMatchInputDto noMatchInput = null;

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT NMI.ID, NMI.TYPE, NMI.THRESHOLD,  NMI.PROMPT, PR.ID, PR.NAME  "
					+ " FROM FLOW.NOMATCHINPUT NMI, FLOW.PROMPT PR  WHERE NMI.ID = " + id + "  AND NMI.PROMPT = PR.ID ";

			rs = conn.ExecuteQuery(query);
			if (rs.next()) {
				noMatchInput = new NoMatchInputDto();
				noMatchInput.setId(rs.getLong(1));
				noMatchInput.setType(rs.getString(2));
				noMatchInput.setThreshold(rs.getLong(3));
				noMatchInput.setPrompt(rs.getLong(4));
				noMatchInput.setNextForm(nextForm);
				noMatchInput.setTag(tag);

				PromptDto prompt = new PromptDto();
				prompt.setId(rs.getLong(5));
				prompt.setName(rs.getString(6));
				prompt.setListaPromptAudio(getListPromptAudioByPromptId(prompt.getId(), jsonContext));

				noMatchInput.setPromptDto(prompt);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o NextForm de ID: " + id, e);
			throw new Exception("Erro ao Recuperar o NextForm de ID: " + id, e);
		} finally {
			conn.finalize();
		}
		return noMatchInput;
	}

	public MenuDto getMenuByMenuId(long menuId, String jsonContext) throws Exception {
		MenuDto menuDto = null;

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT MN.ID, MN.NAME, MN.DESCRIPTION, MN.PROMPT, MN.NOINPUT, MN.NOMATCH,  MN.FETCHTIMEOUT,"
					+ " MN.TERMINATINGTIMEOUT, MN.TERMINATINGCHARACTER, PR.ID, PR.NAME, MN.NOINPUT_NEXTFORM, MN.NOINPUT_TAG, MN.NOMATCH_NEXTFORM, MN.NOMATCH_TAG "
					+ " FROM FLOW.MENU MN, FLOW.PROMPT PR  "
					+ " WHERE MN.ID = " + menuId + "  AND MN.PROMPT = PR.ID ";

			rs = conn.ExecuteQuery(query);
			if (rs.next()) {
				menuDto = new MenuDto();
				menuDto.setId(rs.getLong(1));
				menuDto.setName(rs.getString(2));
				menuDto.setDescription(rs.getString(3));
				menuDto.setPrompt(rs.getLong(4));
				menuDto.setNoInput(rs.getLong(5));
				menuDto.setNoMatch(rs.getLong(6));
				menuDto.setFetchTimeout(rs.getLong(7));
				menuDto.setTerminatingTimeout(rs.getLong(8));
				menuDto.setTerminatingCharacter(rs.getString(9));

				PromptDto prompt = new PromptDto();
				prompt.setId(rs.getLong(10));
				prompt.setName(rs.getString(11));
				prompt.setListaPromptAudio(getListPromptAudioByPromptId(prompt.getId(), jsonContext));

				menuDto.setPromptDto(prompt);

				menuDto.setNoMatchDto(getNoMatchInputById(menuDto.getNoMatch(), rs.getLong("NOMATCH_NEXTFORM"), rs.getLong("NOMATCH_TAG"), jsonContext));
				menuDto.setNoInputDto(getNoMatchInputById(menuDto.getNoInput(), rs.getLong("NOINPUT_NEXTFORM"), rs.getLong("NOINPUT_TAG"), jsonContext));

				menuDto.setListaChoiceDto(getListChoiceByMenuId(menuDto.getId(), jsonContext));
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o Menu de ID: " + menuId, e);
			throw new Exception("Erro ao Recuperar o Menu de ID: " + menuId, e);
		} finally {
			conn.finalize();
		}
		return menuDto;
	}

	public Collection<ChoiceDto> getListChoiceByMenuId(long menuId, String jsonContext) throws Exception {
		Collection<ChoiceDto> listaChoice = new ArrayList<ChoiceDto>();

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = " SELECT ID, NAME, MENU, DTMF, NEXTFORM, CONDITION, TAG FROM FLOW.CHOICE WHERE MENU = "
					+ menuId + " ORDER BY ID ";

			rs = conn.ExecuteQuery(query);

			while (rs.next()) {
				boolean condition = true;
				if (rs.getLong(6) > 0) {
					condition = validarCondition(jsonContext, rs.getLong(6)); // buscar
																				// condition
																				// resultado
																				// condition
				}
				if (condition) {
					ChoiceDto dto = new ChoiceDto();

					dto.setId(rs.getLong(1));
					dto.setName(rs.getString(2));
					dto.setMenu(rs.getLong(3));
					dto.setDtmf(rs.getString(4));
					dto.setNextForm(rs.getLong(5));
					dto.setTag(rs.getLong(7));

					listaChoice.add(dto);
				}
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar a Lista Choice de ID: " + menuId, e);
			throw new Exception("Erro ao Recuperar a Lista Choice de ID: " + menuId, e);
		} finally {
			conn.finalize();
		}
		return listaChoice;
	}

	public DecisionDto getDecisionById(long decisionId) throws Exception {
		DecisionDto dto = new DecisionDto();
		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = "SELECT d.id,d.name ,d.description, dc.id,dc.decisionid,dc.ordernum,dc.condition,dc.nextformid,dc.tag "
					+ "FROM flow.decision d " + "INNER JOIN flow.decisionchance dc ON dc.decisionid = d.id "
					+ "WHERE d.id = " + decisionId + " ORDER By dc.ordernum ";

			rs = conn.ExecuteQuery(query);

			Collection<DecisionChanceDto> listaDecisionChance = new ArrayList<DecisionChanceDto>();
			boolean first = true;
			while (rs.next()) {
				if (first) {
					dto.setId(rs.getLong(1));
					dto.setName(rs.getString(2));
					dto.setDescription(rs.getString(3));
					first = false;
				}

				DecisionChanceDto chance = new DecisionChanceDto();
				chance.setId(rs.getLong(4));
				chance.setCondition(rs.getLong(7));
				chance.setOrder(rs.getLong(6));
				chance.setNextForm(rs.getLong(8));
				chance.setTag(rs.getLong(9));
				listaDecisionChance.add(chance);
				
			}
			dto.setListaDecisionChance(listaDecisionChance);


		} catch (SQLException e) {
			logger.error("Erro ao Recuperar a Decision de ID: " + decisionId, e);
			throw new Exception("Erro ao Recuperar a Decision de ID: " + decisionId, e);
		} finally {
			conn.finalize();
		}

		return dto;
	}

	
	public Collection<DecisionParametersDto> getListDecisionParameters(long groupId) throws Exception {
		Collection<DecisionParametersDto> lista = new ArrayList<DecisionParametersDto>();

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = " SELECT ID, DECISIONGROUPID, PARAMNAME, PARAMVALUE "
					+ " FROM FLOW.DECISIONPARAMETERS WHERE DECISIONGROUPID = " + groupId + " ORDER BY ID ASC ";

			rs = conn.ExecuteQuery(query);
			while (rs.next()) {
				DecisionParametersDto dto = new DecisionParametersDto();

				dto.setId(rs.getLong(1));
				dto.setDecisionGroupId(rs.getLong(2));
				dto.setParamName(rs.getString(3).trim());
				dto.setParamValue(rs.getString(4).trim());

				lista.add(dto);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar a Lista DECISION Parameters de ID: " + groupId, e);
			throw new Exception("Erro ao Recuperar a Lista DECISION Parameters de ID: " + groupId, e);
		} finally {
			conn.finalize();
		}
		return lista;
	}

	public OperationDto getOperationById(long operationId) throws Exception {
		OperationDto dto = new OperationDto();

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = " SELECT OP.ID, OP.NAME, OP.DESCRIPTION, OP.NEXTFORMID, "
					+ " OG.OPERATIONID, OG.ID, OG.ORDERNUM, OG.OPERATIONMAPID, OM.ID, OM.NAME,  "
					+ " OM.DESCRIPTION, OM.METHODREFERENCE, OP.TAG, OM.LOG_ACTIVE, CP.STATUS, CP.TIMEOUT, CP.INTERNALSERVICE "
					+ " FROM FLOW.OPERATION OP, FLOW.OPERATIONGROUP OG, FLOW.OPERATIONMAP OM "
					+ " LEFT JOIN FLOW.CONTROLPANEL CP ON OM.METHODREFERENCE = CP.METHODNAME " + " WHERE OP.ID =  "
					+ operationId + " AND OP.ID = OG.OPERATIONID " + " AND OG.OPERATIONMAPID = OM.ID "
					+ " ORDER BY OG.ORDERNUM ASC ";

			rs = conn.ExecuteQuery(query);

			Collection<OperationGroupDto> listaOperationGroup = new ArrayList<OperationGroupDto>();

			boolean first = true;
			while (rs.next()) {
				if (first) {
					dto.setId(rs.getLong(1));
					dto.setName(rs.getString(2).trim());
					dto.setDescription(rs.getString(3).trim());
					dto.setNextform(rs.getLong(4));
					dto.setTag(rs.getLong(13));
					first = false;
				}

				OperationGroupDto operationGroup = new OperationGroupDto();
				operationGroup.setOperationId(rs.getLong(5));
				operationGroup.setId(rs.getLong(6));
				operationGroup.setOrder(rs.getLong(7));
				operationGroup.setOperationMapId(rs.getLong(8));

				OperationMapDto operationMap = new OperationMapDto();
				operationMap.setId(rs.getLong(9));
				operationMap.setName(rs.getString(10).trim());
				operationMap.setDescription(rs.getString(11).trim());
				operationMap.setMethodReference(rs.getString(12).trim());

				operationMap.setLogActive(rs.getInt(14));
				try {
					operationMap.setActive("true".equalsIgnoreCase(rs.getString(15).trim()) ? true : false);
				} catch (Exception e) {
					logger.error("Método não cadastrado na tabela CONTROLPANEL: " + rs.getString(12).trim(), e);
					operationMap.setActive(true);
				}
				try {
					operationMap.setTimeout(rs.getInt(16));
					operationMap.setInternalService(rs.getBoolean(17));
				} catch (Exception e) {
					logger.error("Cadastro de método incompleto na tabela CONTROLPANEL: " + rs.getString(12).trim(), e);
					operationMap.setTimeout(10);
					operationMap.setInternalService(true);
				}

				// buscar lista de parameters
				operationGroup.setListaOperationParameters(getListOperationParameters(operationGroup.getId()));
				operationGroup.setOperationMap(operationMap);

				listaOperationGroup.add(operationGroup);
			}

			dto.setListaOperationGroup(listaOperationGroup);

		} catch (SQLException e) {
			logger.error("Erro ao Recuperar Operation de ID: " + operationId, e);
			throw new Exception("Erro ao Recuperar Operation de ID: " + operationId, e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.finalize();
		}

		return dto;
	}

	public Collection<OperationParametersDto> getListOperationParameters(long groupId) throws Exception {
		Collection<OperationParametersDto> lista = new ArrayList<OperationParametersDto>();

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = " SELECT ID, OPERATIONGROUPID, PARAMNAME, PARAMVALUE "
					+ " FROM FLOW.OPERATIONPARAMETERS WHERE OPERATIONGROUPID = " + groupId + " ORDER BY ID ASC ";

			rs = conn.ExecuteQuery(query);
			while (rs.next()) {
				OperationParametersDto dto = new OperationParametersDto();

				dto.setId(rs.getLong(1));
				dto.setOperationGroupId(rs.getLong(2));
				dto.setParamName(rs.getString(3));
				dto.setParamValue(rs.getString(4));

				lista.add(dto);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar a Lista Operation Parameters de ID: " + groupId, e);
			throw new Exception("Erro ao Recuperar a Lista Operation Parameters de ID: " + groupId, e);
		} finally {
			conn.finalize();
		}
		return lista;
	}

	public FlowDto getFlowById(long flowId) throws Exception {
		FlowDto flow = null;

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT ID, NAME, DESCRIPTION, FLOWNAME, NEXTFORM, TAG FROM FLOW.FLOW WHERE ID = " + flowId;

			rs = conn.ExecuteQuery(query);
			if (rs.next()) {
				flow = new FlowDto();

				flow.setId(rs.getLong(1));
				flow.setName(rs.getString(2));
				flow.setDescription(rs.getString(3));
				flow.setFlowName(rs.getString(4));
				flow.setNextForm(rs.getLong(5));
				flow.setTag(rs.getLong(6));
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o Flow de ID: " + flowId, e);
			throw new Exception("Erro ao Recuperar o Flow de ID: " + flowId, e);
		} finally {
			conn.finalize();
		}
		return flow;

	}

	public TransferDto getTransferById(long transferId) throws Exception {
		TransferDto transfer = null;

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT ID, NAME, DESCRIPTION, TRANSFERRULEID, TAG FROM FLOW.TRANSFER WHERE ID = "
					+ transferId;

			rs = conn.ExecuteQuery(query);

			if (rs.next()) {
				transfer = new TransferDto();

				transfer.setId(rs.getLong(1));
				transfer.setName(rs.getString(2));
				transfer.setDescription(rs.getString(3));
				transfer.setTransferRuleId(rs.getLong(4));
				transfer.setTag(rs.getLong(5));
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o Transfer de ID: " + transferId, e);
			throw new Exception("Erro ao Recuperar o Transfer de ID: " + transferId, e);
		} finally {
			conn.finalize();
		}
		return transfer;

	}

	public DisconnectDto getDisconnectById(long disconnectId) throws Exception {
		DisconnectDto disconnect = null;

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT ID, NAME, DESCRIPTION, TAG FROM FLOW.DISCONNECT WHERE ID = " + disconnectId;

			rs = conn.ExecuteQuery(query);

			if (rs.next()) {
				disconnect = new DisconnectDto();

				disconnect.setId(rs.getLong(1));
				disconnect.setName(rs.getString(2));
				disconnect.setDescription(rs.getString(3));
				disconnect.setTag(rs.getLong(4));
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o Transfer de ID: " + disconnectId, e);
			throw new Exception("Erro ao Recuperar o Transfer de ID: " + disconnectId, e);
		} finally {
			conn.finalize();
		}
		return disconnect;

	}

	public long getFormIdByFlowName(String flowName) throws Exception {
		Long retorno = null;

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT ID FROM FLOW.FORM WHERE name = '" + flowName+"'";

			rs = conn.ExecuteQuery(query);
			if (rs.next()) {
				retorno = rs.getLong(1);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o Form de Name: " + flowName, e);
			throw new Exception("Erro ao Recuperar o Form de Name: " + flowName, e);
		} finally {
			conn.finalize();
		}
		return retorno;

	}

	public PromptDto getPromptById(long promptId, String jsonContext) throws Exception {
		PromptDto promptDto = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT ID, NAME  FROM FLOW.PROMPT WHERE ID = " + promptId;

			rs = conn.ExecuteQuery(query);
			if (rs.next()) {
				promptDto = new PromptDto();
				promptDto.setId(rs.getLong(1));
				promptDto.setName(rs.getString(2));
				promptDto.setListaPromptAudio(getListPromptAudioByPromptId(promptDto.getId(), jsonContext));

			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o Prompt de ID: " + promptId, e);
			throw new Exception("Erro ao Recuperar o Prompt de ID: " + promptId, e);
		} finally {
			conn.finalize();
		}
		return promptDto;
	}
	public PromptDto getPromptByPromptName(String name, String jsonContext) throws Exception {
		PromptDto prompt = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT ID, NAME  FROM FLOW.PROMPT WHERE NAME = " + name;

			rs = conn.ExecuteQuery(query);
			if (rs.next()) {
				prompt = new PromptDto();
				prompt.setId(rs.getLong(1));
				prompt.setName(rs.getString(2));
				prompt.setListaPromptAudio(getListPromptAudioByPromptId(prompt.getId(), jsonContext));

			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o Prompt de Name: " + name, e);
			throw new Exception("Erro ao Recuperar o Prompt de Name: " + name, e);
		} finally {
			conn.finalize();
		}
		return prompt;
	}

	public boolean validarCondition(String context, long conditionId) throws Exception {
		boolean retorno = false;
		ConditionDto condition = null;
		Long trackId = null;
		try {
			condition = new ConditionDao().getConditionsById(conditionId);

			trackId = LogUtils.getTrackId();
			LogUtils.createTrackCondition(context, trackId);

			for (ConditionGroupDto conditionGroup : condition.getListaConditionGroup()) {

				retorno = false;

				Map<String, String> map = null;

				if (UraUtils.isNotNull(conditionGroup.getListaConditionParameters()) && !conditionGroup.getListaConditionParameters().isEmpty()) {
					map = new HashMap<String, String>();
					for (ConditionParametersDto conditionParameters : conditionGroup.getListaConditionParameters()) {
						map.put(conditionParameters.getParamName(), conditionParameters.getParamValue());
					}
				}

				MethodInvocation invocationService = new MethodInvocation();

				MethodInvocationVO serviceReturn = invocationService.invoke(context, conditionGroup.getConditionMap().getMethodReference(), map, conditionGroup.getConditionMap().getTimeout(), conditionGroup.getConditionMap().isActive());

				String param = null;
				if (UraUtils.isNotNull(map)) {
					param = map.toString();
				}
				long trackServiceId = LogUtils.getTrackServiceId();
				if (conditionGroup.getConditionMap().getLogActive() > 0) {
					LogUtils.createTrackService(trackServiceId, trackId, conditionGroup.getId(),conditionGroup.getConditionMap().getMethodReference(), serviceReturn.getValue(), param, serviceReturn.getErrorCode(), Long.parseLong(MethodInvocationUtils.getContextValue(context, MapValues.LOGID)), serviceReturn.getTimeService());
				}
				for (ConditionValueDto conditionValue : conditionGroup.getListaConditionValue()) {

					if (UraUtils.isNotNull(conditionValue.getOperation())) {

						if (processaOperacao(conditionValue.getOperation(), conditionGroup.getConditionMap().getType(), serviceReturn.getValue(), conditionValue)) {

							if (conditionValue.getTagTrue() > 0) {
								LogUtils.createTrackTag(trackServiceId, trackId,Long.valueOf(MethodInvocationUtils.getContextValue(context, MapValues.LOGID)), conditionValue.getTagTrue());
							}
							return true;

						} else {
							if (conditionValue.getTagFalse() > 0) {
								LogUtils.createTrackTag(trackServiceId, trackId,
										Long.valueOf(MethodInvocationUtils.getContextValue(context, MapValues.LOGID)),
										conditionValue.getTagFalse());
							}
						}

					} else {
						if (conditionValue.getTagFalse() > 0) {
							LogUtils.createTrackTag(trackServiceId, trackId,
									Long.valueOf(MethodInvocationUtils.getContextValue(context, MapValues.LOGID)),
									conditionValue.getTagFalse());
						}
					}

				}

			}

		} catch (DaoException e) {
			logger.error("Erro ao Recuperar a Condition para o Id: " + conditionId, e);
			throw new Exception("Erro ao Recuperar a Condition para o Id: " + conditionId, e);

		} catch (Exception e) {
			logger.error("Erro ao Validar a Condition para o Id: " + conditionId, e);
			throw new Exception("Erro ao Validar a Condition para o Id: " + conditionId, e);
		}

		return retorno;
	}
	
	public DecisionConditionDto validarDecisionCondition(String context, long conditionId) throws Exception {
		ConditionDto condition = null;
		Long trackId = null;
		DecisionConditionDto dCDto = new DecisionConditionDto();
		dCDto.setCondition(Boolean.FALSE);
		try {
			condition = new ConditionDao().getConditionsById(conditionId);

			trackId = LogUtils.getTrackId();
			LogUtils.createTrackCondition(context, trackId);

			for (ConditionGroupDto conditionGroup : condition.getListaConditionGroup()) {
				dCDto.setCondition(Boolean.FALSE);

				Map<String, String> map = null;

				if (UraUtils.isNotNull(conditionGroup.getListaConditionParameters()) && !conditionGroup.getListaConditionParameters().isEmpty()) {
					map = new HashMap<String, String>();
					for (ConditionParametersDto conditionParameters : conditionGroup.getListaConditionParameters()) {
						map.put(conditionParameters.getParamName(), conditionParameters.getParamValue());
					}
				}

				MethodInvocation invocationService = new MethodInvocation();

				MethodInvocationVO serviceReturn = invocationService.invoke(context, conditionGroup.getConditionMap().getMethodReference(), map, conditionGroup.getConditionMap().getTimeout(), conditionGroup.getConditionMap().isActive());
				dCDto.setJsonContext(serviceReturn.getJsonContext());
				String param = null;
				if (UraUtils.isNotNull(map)) {
					param = map.toString();
				}
				long trackServiceId = LogUtils.getTrackServiceId();
				if (conditionGroup.getConditionMap().getLogActive() > 0) {
					LogUtils.createTrackService(trackServiceId, trackId, conditionGroup.getId(),conditionGroup.getConditionMap().getMethodReference(), serviceReturn.getValue(), param, serviceReturn.getErrorCode(), Long.parseLong(MethodInvocationUtils.getContextValue(context, MapValues.LOGID)), serviceReturn.getTimeService());
				}
				for (ConditionValueDto conditionValue : conditionGroup.getListaConditionValue()) {

					if (UraUtils.isNotNull(conditionValue.getOperation())) {

						if (processaOperacao(conditionValue.getOperation(), conditionGroup.getConditionMap().getType(), serviceReturn.getValue(), conditionValue)) {

							if (conditionValue.getTagTrue() > 0) {
								LogUtils.createTrackTag(trackServiceId, trackId,Long.valueOf(MethodInvocationUtils.getContextValue(context, MapValues.LOGID)), conditionValue.getTagTrue());
							}
							dCDto.setCondition(Boolean.TRUE);
							return dCDto;

						} else {
							if (conditionValue.getTagFalse() > 0) {
								LogUtils.createTrackTag(trackServiceId, trackId,
										Long.valueOf(MethodInvocationUtils.getContextValue(context, MapValues.LOGID)),
										conditionValue.getTagFalse());
							}
						}

					} else {
						if (conditionValue.getTagFalse() > 0) {
							LogUtils.createTrackTag(trackServiceId, trackId,
									Long.valueOf(MethodInvocationUtils.getContextValue(context, MapValues.LOGID)),
									conditionValue.getTagFalse());
						}
					}

				}

			}

		} catch (DaoException e) {
			logger.error("Erro ao Recuperar a Decision Condition para o Id: " + conditionId, e);
			throw new Exception("Erro ao Recuperar a Decision  Condition para o Id: " + conditionId, e);

		} catch (Exception e) {
			logger.error("Erro ao Validar a Decision  Condition para o Id: " + conditionId, e);
			throw new Exception("Erro ao Validar a Decision  Condition para o Id: " + conditionId, e);
		}

		return dCDto;
	}
	

	private boolean processaOperacao(String operacao, String type, String resultado, ConditionValueDto conditionValue) {
		try {
			if (FormConstants.TYPE_TEXT.equals(type)) {
				return UraUtils.validateTypeText(operacao, resultado, conditionValue.getValue1(),
						getValuesNotNull(conditionValue));
			} else if (FormConstants.TYPE_NUMERIC.equals(type)) {
				return UraUtils.validateTypeNumeric(operacao, resultado, conditionValue.getValue1(),
						conditionValue.getValue2(), getValuesNotNull(conditionValue));
			} else if (FormConstants.TYPE_DATE.equals(type)) {
				return UraUtils.validateTypeDate(operacao, resultado, conditionValue.getValue1(),
						conditionValue.getValue2(), getValuesNotNull(conditionValue));
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	private Collection<String> getValuesNotNull(ConditionValueDto conditionValue) throws Exception {
		Collection<String> lista = new ArrayList<String>();
		if (UraUtils.isNotNull(conditionValue.getValue1())) {
			lista.add(conditionValue.getValue1());
		}
		if (UraUtils.isNotNull(conditionValue.getValue2())) {
			lista.add(conditionValue.getValue2());
		}
		if (UraUtils.isNotNull(conditionValue.getValue3())) {
			lista.add(conditionValue.getValue3());
		}
		if (UraUtils.isNotNull(conditionValue.getValue4())) {
			lista.add(conditionValue.getValue4());
		}
		if (UraUtils.isNotNull(conditionValue.getValue5())) {
			lista.add(conditionValue.getValue5());
		}
		if (UraUtils.isNotNull(conditionValue.getValue6())) {
			lista.add(conditionValue.getValue6());
		}
		if (UraUtils.isNotNull(conditionValue.getValue7())) {
			lista.add(conditionValue.getValue7());
		}
		if (UraUtils.isNotNull(conditionValue.getValue8())) {
			lista.add(conditionValue.getValue8());
		}
		if (UraUtils.isNotNull(conditionValue.getValue9())) {
			lista.add(conditionValue.getValue9());
		}
		if (UraUtils.isNotNull(conditionValue.getValue10())) {
			lista.add(conditionValue.getValue10());
		}
		return lista;

	}

	public AnswerDto getAnwserById(long formid, String jsonContext) throws Exception {

		AnswerDto answer = null;

		ResultSet rs2 = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = " SELECT a.id, a.name, a.description, a.nextform, a.tag "
					+ " FROM FLOW.ANSWER A WHERE A.ID = " + formid;

			rs2 = conn.ExecuteQuery(query);
			if (rs2.next()) {
				answer = new AnswerDto();
				answer.setId(rs2.getLong(1));
				answer.setName(rs2.getString(2));
				answer.setDescription(rs2.getString(3));
				answer.setNextForm(rs2.getLong(4));
				answer.setTag(rs2.getLong(5));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Erro ao Recuperar o Answer de ID: " + formid, e);
			throw new Exception("Erro ao Recuperar o Answer de ID: " + formid, e);
		} finally {
			conn.finalize();
		}
		return answer;

	}

	public ReturnDto getReturnById(long formid, String jsonContext) throws Exception {

		ReturnDto retorno = null;

		ResultSet rs2 = null;
		ConnectionDB conn = null;
	
		try {
			conn = new ConnectionDB();

			String query = " SELECT a.id, a.name, a.description, a.tag " + " FROM FLOW.RETURN A WHERE A.ID = " + formid;

			rs2 = conn.ExecuteQuery(query);
			if (rs2.next()) {
				retorno = new ReturnDto();
				retorno.setId(rs2.getLong(1));
				retorno.setName(rs2.getString(2));
				retorno.setDescription(rs2.getString(3));
				retorno.setTag(rs2.getLong(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Erro ao Recuperar o Return de ID: " + formid, e);
			throw new Exception("Erro ao Recuperar o Return de ID: " + formid, e);
		} finally {
			conn.finalize();
		}
		return retorno;
	}

}
