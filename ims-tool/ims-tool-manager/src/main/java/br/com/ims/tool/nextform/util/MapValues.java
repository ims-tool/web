package br.com.ims.tool.nextform.util;

import java.io.Serializable;

public interface MapValues extends Serializable {

	public static final String DELIMITER = ".";

	//Adicionar qual o tipo de ura que está sendo usado.
	public static final String IVR = "ivr";
	public static final String ROW_ID = "rowId";
	public static final String START_DATE = "startDate";
	public static final String STOP_DATE = "stopDate";
	public static final String PROMPT_COLLECT = "_PromptCollect";
	public static final String MENU_OPTION = "_Menu";
	public static final String CONTADOR_TENTATIVAS = "CONTADOR_TENTATIVAS";
	
	public static final String TP_LOJA = "customer.loja";
	
	
	//Mensagens URA
	public static final String MSG_HAS_NEXT = "mensagem.hasNext";
	public static final String MSG_SPOT = "temp.MsgPonto";
	public static final String MSG_MESSAGES = "mensagem.mensagens";
	public static final String MSG_AUDIO = "temp.MsgId";
	public static final String MSG_COUNT = "temp.MsgCount";
	
	public static final String MSG_REPARO_ATENDIMENTO = "temp.MsgReparoAndamento";
	public static final String MSG_DESBLOQUEIO_ALEGA = "temp.MsgDesbloqueioAlega";
	
	public static final String MSG_CONT_PRODUTO = "msgContingencia.produto";
	public static final String MSG_CONT_AUDIO = "msgContingencia.audio";
	public static final String MSG_CONT_AUDIO_NAME = "msgContingencia.audioName";
	public static final String MSG_CONT_OUVIU_MSG = "msgContingencia.ouviuMsg";
	public static final String MSG_CONT_OPCAO = "msgContingencia.opcao";
	public static final String MSG_CONT_ATENDIMENTO = "msgContingencia.atendimento";
	public static final String MSG_CONT_ATND_ESP = "msgContingencia.atdeEsp";
	public static final String MSG_CONT_ATND_BL = "msgContingencia.atdeBL";
	public static final String MSG_CONT_ATND_TV = "msgContingencia.atdeTv";


	public static final String ANI = "call.ani";
	public static final String DNIS = "call.dnis";
	public static final String URA = "call.ura";
	public static final String UCID = "call.ucid";
	public static final String UII = "call.uii";
	public static final String LOG = "call.log";
	public static final String LOGID = "call.logId";
	public static final String TRACKID = "call.trackId";
	public static final String PROTOCOLNUMBER = "call.protocol";
	public static final String PROTOCOLID = "call.protocolId";
	public static final String CHAMADOR_CONTATO = "call.chamadorContato";
	public static final String VDN = "call.vdn";
	public static final String ORIGEM = "call.origem";
	public static final String PARTNER = "customer.partner";

	public static final String DOCUMENT = "customer.document";
	public static final String DOCUMENT_TYPE = "customer.documentType";
	public static final String EXTERNAL_ACCOUNT_ID = "customer.idenExternalAccountId";
	public static final String MASTER_ACCOUNT_ID = "customer.masterAccountId";
	public static final String SERVICE_ACCOUNT_ID = "customer.serviceAccountId";
	public static final String TEMPERATURE = "customer.temperature";
	public static final String AGING = "customer.aging";
	public static final String ETA = "customer.eta";
	public static final String INSCRICAO_ESTADUAL = "customer.inscricaoEstadual";
	public static final String ANO_BASE = "customer.anoBase";
	public static final String ACCOUNT_CATEGORY = "customer.accountCategory";
	
	public static final String DDD = "instance.ddd";
	public static final String INSTANCE = "instance.numberPhone";
	public static final String UF = "instance.uf";
	public static final String CIDADE = "instance.infu_name_county";
	public static final String PERFIL = "instance.perfil";

	public static final String SMART_URA_PARAMETRO = "smartUra.nome";
	public static final String SMART_URA_EH_CLIENTE_DE_RISCO = "smartUra.ehClienteDeRisco";

	public static final String MSGCONTIGENCIA = "temp.msgContingencia";
	public static final String RETURN_TYPE = "temp.returnType";

	
	public static final String MENSAGEM_EMERGENCIAL = "smartUra.MENSAGEM EMERGENCIAL";
	public static final String MENU_EMERGENCIAL = "smartUra.MENU EMERGENCIAL";
		
	public static final String OBS = "OBS";
	public static final String VARNAME = "VARNAME";
	public static final String PID = "pid";
	
	public static final String NAME_CUSTOMER = "customer.name";
	public static final String BIRTHDATE = "customer.birthdate";
		
	public static final String IDENTIFICACAO_TENTATIVA1 = "identificacao.tentativa1";
	public static final String IDENTIFICACAO_TENTATIVA2 = "identificacao.tentativa2";
	public static final String IDENTIFICACAO_SOLICITACOES_BC = "identificacao.solicitacoes";
	
	public static final String TEMP_NEXTFORMID = "temp.nextformid_";
	
			//Remover op 9 na segunda vocalização
	public static final String PROMPT_TRY = "PromptTry";
	
	public static final String EXTERNAL_SERVICE = "temp.externalService";
	
	public static final String TEMPO_MENU = "tempo.menu";
	
	public static final String TEMP_HORARIO = "temp.horario";
	public static final String SUNDAY_HOLIDAY = "sundayHoliday";
	
}
