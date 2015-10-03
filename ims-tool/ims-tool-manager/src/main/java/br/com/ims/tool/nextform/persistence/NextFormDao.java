package br.com.ims.tool.nextform.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import br.com.ims.tool.nextform.model.AnnounceDto;
import br.com.ims.tool.nextform.model.AudioDto;
import br.com.ims.tool.nextform.model.AudioVarDto;
import br.com.ims.tool.nextform.model.ChoiceDto;
import br.com.ims.tool.nextform.model.DecisionChanceDto;
import br.com.ims.tool.nextform.model.DecisionDto;
import br.com.ims.tool.nextform.model.DecisionGroupDto;
import br.com.ims.tool.nextform.model.DecisionMapDto;
import br.com.ims.tool.nextform.model.DecisionParametersDto;
import br.com.ims.tool.nextform.model.DisconnectDto;
import br.com.ims.tool.nextform.model.FlowDto;
import br.com.ims.tool.nextform.model.FormTypeDto;
import br.com.ims.tool.nextform.model.GrammarDto;
import br.com.ims.tool.nextform.model.MenuDto;
import br.com.ims.tool.nextform.model.NextFormDto;
import br.com.ims.tool.nextform.model.NoMatchInputDto;
import br.com.ims.tool.nextform.model.OperationDto;
import br.com.ims.tool.nextform.model.OperationGroupDto;
import br.com.ims.tool.nextform.model.OperationMapDto;
import br.com.ims.tool.nextform.model.OperationParametersDto;
import br.com.ims.tool.nextform.model.PromptAudioDto;
import br.com.ims.tool.nextform.model.PromptCollectDto;
import br.com.ims.tool.nextform.model.PromptDto;
import br.com.ims.tool.nextform.model.TransferDto;
import br.com.ims.tool.nextform.util.ConnectionDB;
import br.com.ims.tool.nextform.util.MethodInvocationUtils;
import br.com.ims.tool.nextform.util.UraConstants;


public class NextFormDao {
	
	private static Logger logger = Logger.getLogger(NextFormDao.class);
	
	
	public NextFormDto getNextFormByNextId(long nextId) {
		
		NextFormDto nextForm = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT NF.ID, NF.NAME, NF.DESCRIPTION, NF.FORMTYPE, NF.FORMID, NF.TAG, FT.ID, FT.NAME, NF.NEXTFORMDEFAULT " +
							" FROM FLOW.FORM NF, FLOW.FORMTYPE FT  WHERE NF.ID = "+nextId+"  AND NF.FORMTYPE = FT.ID ";
			
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
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			
			String query = " SELECT AN.ID, AN.NAME, AN.DESCRIPTION, AN.FLUSHPROMPT, AN.PROMPT, AN.NEXTFORM, PR.ID, PR.NAME, AN.TAG " +
							" FROM FLOW.ANNOUNCE AN, FLOW.PROMPT PR  WHERE AN.ID = "+idAnnounce+"  AND AN.PROMPT = PR.ID ";

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
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT PC.ID, PC.NAME, PC.DESCRIPTION, PC.GRAMMAR, PC.FLUSHPROMPT, PC.PROMPT," +
					" PC.NOINPUT, PC.NOMATCH, PC.FETCHTIMEOUT, PC.INTERDIGITTIMEOUT,   PC.TERMINATINGTIMEOUT," +
					" PC.TERMINATINGCHARACTER, PC.NEXTFORM, PR.ID,   PR.NAME, GR.ID, GR.NAME, GR.DESCRIPTION," +
					" GR.TYPE, GR.SIZEMAX, GR.SIZEMIN, PC.TAG  FROM FLOW.PROMPTCOLLECT PC   LEFT OUTER JOIN FLOW.GRAMMAR GR ON " +
					" PC.GRAMMAR = GR.ID  LEFT OUTER JOIN FLOW.PROMPT PR ON PC.PROMPT = PR.ID   WHERE PC.ID ="+idpromptCollect;

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

				promptCollect.setNoInputDto(getNoMatchInputById(promptCollect
						.getNoInput(), jsonContext));
				promptCollect.setNoMatchDto(getNoMatchInputById(promptCollect
						.getNoMatch(), jsonContext));
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

			String query = 	" SELECT PA.PROMPT, PA.AUDIO, PA.ORDERNUM, AU.ID, AU.TYPE, AU.NAME, AU.DESCRIPTION, " +
							" AU.PATH, PA.CONDITION FROM FLOW.PROMPTAUDIO PA, FLOW.AUDIO AU  " +
							" WHERE PA.PROMPT = "+promptId+"  AND PA.AUDIO = AU.ID  ORDER BY PA.ORDERNUM ";

			rs = conn.ExecuteQuery(query);
			while (rs.next()) {
				boolean condition = true;
				if (rs.getLong(9) > 0) {
					condition = validarCondition(jsonContext, rs.getLong(9)); //buscar condition resultado condition
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
					audio.setPath(rs.getString(8));
					
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
						audio.setDescription( MethodInvocationUtils.getContextValue(jsonContext, audio.getName()));
						audio.setName( MethodInvocationUtils.getContextValue(jsonContext, audio.getName()));
						
						//Nao carrega o prompt audio com name vazio
						if("".equals(audio.getName()))
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

			String query = " SELECT ID, AUDIOID, PARAMNAME, PARAMVALUE FROM FLOW.AUDIOVAR WHERE AUDIOID = "+audioId;

			rs = conn.ExecuteQuery(query);
			
			while (rs.next()) {
				AudioVarDto audioVar= new AudioVarDto();
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

	public NoMatchInputDto getNoMatchInputById(long id, String jsonContext) throws Exception {
		NoMatchInputDto noMatchInput = null;

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT NMI.ID, NMI.TYPE, NMI.THRESHOLD,  NMI.PROMPT, NMI.NEXTFORM, PR.ID, PR.NAME, NMI.TAG  " +
					" FROM FLOW.NOMATCHINPUT NMI, FLOW.PROMPT PR  WHERE NMI.ID = "+id+"  AND NMI.PROMPT = PR.ID ";

			rs = conn.ExecuteQuery(query);
			if (rs.next()) {
				noMatchInput = new NoMatchInputDto();
				noMatchInput.setId(rs.getLong(1));
				noMatchInput.setType(rs.getString(2));
				noMatchInput.setThreshold(rs.getLong(3));
				noMatchInput.setPrompt(rs.getLong(4));
				noMatchInput.setNextForm(rs.getLong(5));
				noMatchInput.setTag(rs.getLong(8));

				PromptDto prompt = new PromptDto();
				prompt.setId(rs.getLong(6));
				prompt.setName(rs.getString(7));
				prompt.setListaPromptAudio(getListPromptAudioByPromptId(prompt
						.getId(), jsonContext));

				noMatchInput.setPromptDto(prompt);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar o NextForm de ID: " + id, e);
			throw new Exception("Erro ao Recuperar o NextForm de ID: " + id,	e);
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
			String query = " SELECT MN.ID, MN.NAME, MN.DESCRIPTION, MN.PROMPT, MN.NOINPUT, MN.NOMATCH,  MN.FETCHTIMEOUT," +
					"MN.TERMINATINGTIMEOUT, MN.TERMINATINGCHARACTER, PR.ID, PR.NAME  FROM FLOW.MENU MN, FLOW.PROMPT PR  " +
					"WHERE MN.ID = "+menuId+"  AND MN.PROMPT = PR.ID ";

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
				prompt.setListaPromptAudio(getListPromptAudioByPromptId(prompt
						.getId(), jsonContext));

				menuDto.setPromptDto(prompt);

				menuDto.setNoMatchDto(getNoMatchInputById(menuDto.getNoMatch(), jsonContext));
				menuDto.setNoInputDto(getNoMatchInputById(menuDto.getNoInput(), jsonContext));

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

			String query = " SELECT ID, NAME, MENU, DTMF, NEXTFORM, CONDITION, TAG FROM FLOW.CHOICE WHERE MENU = "+menuId+" ORDER BY ID ";
			
			rs = conn.ExecuteQuery(query);

			while (rs.next()) {
				boolean condition = true;
				if (rs.getLong(6) > 0) {
					condition = validarCondition(jsonContext, rs.getLong(6)); //buscar condition resultado condition
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

			String query = 	" SELECT DC.ID, DC.NAME, DC. DESCRIPTION, " + 
							" DG.DECISIONID, DG.ID, DG.ORDERNUM, DG.DECISIONMAPID, DM.ID, DM.NAME, " +
							" DM.DESCRIPTION, DM.TYPE, DM.METHODREFERENCE, DM.LOG_ACTIVE, CP.STATUS, CP.TIMEOUT, CP.INTERNALSERVICE " +
							" FROM FLOW.DECISION DC, FLOW.DECISIONGROUP DG, FLOW.DECISIONMAP DM " +
							" LEFT JOIN FLOW.CONTROLPANEL CP ON DM.METHODREFERENCE = CP.METHODNAME  " +
							" WHERE DC.ID = "+decisionId+"   " +
							" AND DC.ID = DG.DECISIONID  " +
							" AND DG.DECISIONMAPID = DM.ID  " +
							" ORDER BY DG.ORDERNUM ASC ";

			rs = conn.ExecuteQuery(query);
			
			Collection<DecisionGroupDto> listaDecisionGroup = new ArrayList<DecisionGroupDto>();
			boolean first = true;
			while (rs.next()) {
				if (first) {
					dto.setId(rs.getLong(1));
					dto.setName(rs.getString(2));
					dto.setDescription(rs.getString(3));
					first = false;
				}
				
				DecisionGroupDto decisionGroup = new DecisionGroupDto();
				decisionGroup.setDecisionId(rs.getLong(4));
				decisionGroup.setId(rs.getLong(5));
				decisionGroup.setOrder(rs.getLong(6));
				decisionGroup.setDecisionMapId(rs.getLong(7));
				
				DecisionMapDto decisionMap = new DecisionMapDto();
				decisionMap.setId(rs.getLong(8));
				decisionMap.setName(rs.getString(9));
				decisionMap.setDescription(rs.getString(10));
				decisionMap.setType(rs.getString(11));
				decisionMap.setMethodReference(rs.getString(12));
				
				decisionMap.setLogActive(rs.getInt(13));
				decisionMap.setActive("true".equalsIgnoreCase(rs.getString(14)) ? true : false);
				decisionMap.setTimeout(rs.getInt(15));
				decisionMap.setInternalService(rs.getBoolean(16));
				
				//buscar lista de chance
				decisionGroup.setListaDecisionChance(getListDecisionChance(decisionGroup.getId()));
				//buscar lista de parameters
				decisionGroup.setListaDecisionParameters(getListDecisionParameters(decisionGroup.getId()));
				
				decisionGroup.setDecisionMap(decisionMap);
				listaDecisionGroup.add(decisionGroup);
			}
				
			dto.setListaDecisionGroup(listaDecisionGroup);
		
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar a Decision de ID: " + decisionId, e);
			throw new Exception("Erro ao Recuperar a Decision de ID: " + decisionId, e);
		} finally {
			conn.finalize();
		}
		
		return dto;
	}
	
	public Collection<DecisionChanceDto> getListDecisionChance(long groupId) throws Exception {
		Collection<DecisionChanceDto> listaChance = new ArrayList<DecisionChanceDto>();

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = 	" SELECT ID, DECISIONGROUPID, ORDERNUM, OPERATION, VALUE1, " +
							" VALUE2, VALUE3, VALUE4, VALUE5, VALUE6, VALUE7, VALUE8, " +
							" VALUE9, VALUE10, DECISIONGROUPCHILD, NEXTFORMID, TAG  " +
							" FROM FLOW.DECISIONCHANCE WHERE DECISIONGROUPID = "+groupId+" ORDER BY ORDERNUM ";
			rs = conn.ExecuteQuery(query);
			while (rs.next()) {
				DecisionChanceDto dto = new DecisionChanceDto();

				dto.setId(rs.getLong(1));
				dto.setDecisionGroupId(rs.getLong(2));
				dto.setOrder(rs.getLong(3));
				dto.setOperation(rs.getString(4));
				dto.setValue1(rs.getString(5));
				dto.setValue2(rs.getString(6));
				dto.setValue3(rs.getString(7));
				dto.setValue4(rs.getString(8));
				dto.setValue5(rs.getString(9));
				dto.setValue6(rs.getString(10));
				dto.setValue7(rs.getString(11));
				dto.setValue8(rs.getString(12));
				dto.setValue9(rs.getString(13));
				dto.setValue10(rs.getString(14));
				dto.setDecisionGroupChild(rs.getLong(15));
				dto.setNextForm(rs.getLong(16));
				dto.setTag(rs.getLong(17));
				listaChance.add(dto);
			}
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar a Lista DECISION CHANCE de ID: " + groupId, e);
			throw new Exception("Erro ao Recuperar a Lista DECISION CHANCE de ID: " + groupId, e);
		} finally {
			conn.finalize();
		}
		return listaChance;
	}
	
	public Collection<DecisionParametersDto> getListDecisionParameters(long groupId) throws Exception {
		Collection<DecisionParametersDto> lista = new ArrayList<DecisionParametersDto>();

		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = 	" SELECT ID, DECISIONGROUPID, PARAMNAME, PARAMVALUE " +
							" FROM FLOW.DECISIONPARAMETERS WHERE DECISIONGROUPID = "+groupId+" ORDER BY ID ASC ";

			rs = conn.ExecuteQuery(query);
			while (rs.next()) {
				DecisionParametersDto dto = new DecisionParametersDto();
				
				dto.setId(rs.getLong(1));
				dto.setDecisionGroupId(rs.getLong(2));
				dto.setParamName(rs.getString(3));
				dto.setParamValue(rs.getString(4));

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

			String query =  " SELECT OP.ID, OP.NAME, OP.DESCRIPTION, OP.NEXTFORMID, " +
							" OG.OPERATIONID, OG.ID, OG.ORDERNUM, OG.OPERATIONMAPID, OM.ID, OM.NAME,  " +
							" OM.DESCRIPTION, OM.METHODREFERENCE, OP.TAG, OM.LOG_ACTIVE, CP.STATUS, CP.TIMEOUT, CP.INTERNALSERVICE " +
							" FROM FLOW.OPERATION OP, FLOW.OPERATIONGROUP OG, FLOW.OPERATIONMAP OM " +
							" LEFT JOIN FLOW.CONTROLPANEL CP ON OM.METHODREFERENCE = CP.METHODNAME " +
							" WHERE OP.ID =  "+operationId +
							" AND OP.ID = OG.OPERATIONID " +
							" AND OG.OPERATIONMAPID = OM.ID " +
							" ORDER BY OG.ORDERNUM ASC ";

			rs = conn.ExecuteQuery(query);
			
			Collection<OperationGroupDto> listaOperationGroup = new ArrayList<OperationGroupDto>();
					
			boolean first = true;
			while (rs.next()) {
				if (first) {
					dto.setId(rs.getLong(1));
					dto.setName(rs.getString(2));
					dto.setDescription(rs.getString(3));
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
				operationMap.setName(rs.getString(10));
				operationMap.setDescription(rs.getString(11));
				operationMap.setMethodReference(rs.getString(12));
				
				operationMap.setLogActive(rs.getInt(14));
				operationMap.setActive("true".equalsIgnoreCase(rs.getString(15)) ? true : false);
				operationMap.setTimeout(rs.getInt(16));
				operationMap.setInternalService(rs.getBoolean(17));
				

				//buscar lista de parameters
				operationGroup.setListaOperationParameters(getListOperationParameters(operationGroup.getId()));
				operationGroup.setOperationMap(operationMap);
				
				listaOperationGroup.add(operationGroup);
			}
				
			dto.setListaOperationGroup(listaOperationGroup);
		
		} catch (SQLException e) {
			logger.error("Erro ao Recuperar Operation de ID: " + operationId, e);
			throw new Exception("Erro ao Recuperar Operation de ID: " + operationId, e);
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

			String query = 	" SELECT ID, OPERATIONGROUPID, PARAMNAME, PARAMVALUE " +
							" FROM FLOW.OPERATIONPARAMETERS WHERE OPERATIONGROUPID = "+groupId+" ORDER BY ID ASC ";

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
			throw new Exception(
					"Erro ao Recuperar a Lista Operation Parameters de ID: " + groupId, e);
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
			String query = " SELECT ID, NAME, DESCRIPTION, FLOWNAME, NEXTFORM, TAG FROM FLOW.FLOW WHERE ID = "+flowId;

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
			String query = " SELECT ID, NAME, DESCRIPTION, TRANSFERRULEID, TAG FROM FLOW.TRANSFER WHERE ID = "+ transferId;

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
			String query = " SELECT ID, NAME, DESCRIPTION, TAG FROM FLOW.DISCONNECT WHERE ID = "+disconnectId;

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
			String query = " SELECT ID FROM FLOW.FORM WHERE NAME = "+flowName;

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
	
	public PromptDto getPromptByPromptName(String name, String jsonContext) throws Exception {
		PromptDto prompt = null;
		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = " SELECT ID, NAME  FROM FLOW.PROMPT WHERE NAME = "+name;

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
	
	private boolean validarCondition(String context, long conditionId) {
		boolean retorno = false;
//		
//		try {
//			InitialContext ctx = new InitialContext();
//			ConditionsServiceRemote service = (ConditionsServiceRemote)
//					ctx.lookup("ejb.ConditionsServiceEJB#br.com.gvt.ura.conditions.service.ConditionsServiceRemote");
//			retorno = service.isConditionById(context, conditionId);
//			
//		} catch (NamingException e) {
//			logger.error("Erro ao validar Condition de ID: " + conditionId, e);
//			retorno = false;
//		} catch (Exception e) {
//			logger.error("Erro ao validar Condition de ID: " + conditionId, e);
//			retorno = false;
//		}
		
		return retorno;
	}
	

}
