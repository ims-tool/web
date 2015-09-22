package br.com.ims.tool.nextform.util;

import java.io.Serializable;

public interface MapValues extends Serializable {

	public static final String DELIMITER = ".";

	public static final String ROW_ID = "rowId";
	public static final String START_DATE = "startDate";
	public static final String STOP_DATE = "stopDate";
	public static final String PROMPT_COLLECT = "_PromptCollect";
	public static final String MENU_OPTION = "_Menu";
	public static final String CONTADOR_TENTATIVAS = "CONTADOR_TENTATIVAS";
	
	public static final String HIGHANI_QTDE = "highAni.highAniQtde";
	public static final String TEM_FRAUDE = "fraude.temFraude";

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
	public static final String BLACK_LIST = "customer.blackList";
	public static final String TYPECUST = "customer.typeCustomer";
	
	public static final String CREDIT_SCORE = "customerScore.creditScore";
	
	public static final String CYBER_NRO_FATURAS = "cyber.NroFaturas";

	public static final String RPON = "instance.rpon";
	public static final String DDD = "instance.ddd";
	public static final String INSTANCE = "instance.numberPhone";
	public static final String UF = "instance.uf";
	public static final String CIDADE = "instance.infu_name_county";
	public static final String PERFIL = "instance.perfil";
	public static final String TV = "instance.tv";
	public static final String TV_HIBRIDA = "instance.tvHibrida";
	public static final String TV_DTH = "instance.tvDTH";
	public static final String INSTANCIA_STATUS = "instance.status";
	public static final String BANDALARGA = "instance.bandaLarga";
	public static final String TEM_EMAIL = "instance.temEmail";
	public static final String EMAIL = "instance.email";
	public static final String RECALL = "instance.recall";
	public static final String PROD_IDEN_ROW_ID = "instance.prodIdenRowId";
	public static final String INFU_TYPE_CUSTOMER = "instance.infuTypeCustomer";
	public static final String ARMARIO = "instance.armario";

	public static final String INTERACTION_VALUE = "interaction.value";

	public static final String SMART_URA_PARAMETRO = "smartUra.nome";
	public static final String SMART_URA_EH_CLIENTE_DE_RISCO = "smartUra.ehClienteDeRisco";

	public static final String MSGCONTIGENCIA = "temp.msgContingencia";
	public static final String RETURN_TYPE = "temp.returnType";
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

	public static final String TEM_TT_ATRIBUIDO = "ttAtribuido.has";
	public static final String ID_TT_ATRIBUIDO = "ttAtribuido.id";
	public static final String PROTOCOLO_TT_ATRIBUIDO = "ttAtribuido.protocolo";
	public static final String TT_ATRIBUIDO_TIPO = "ttAtribuido.tipo";
	public static final String TT_ATRIBUIDO_DENTRO_DO_PRAZO = "ttAtribuido.dentroDoPrazo";
	public static final String TT_ATRIBUIDO_DATA_VENCIMENTO = "ttAtribuido.dataVencimento";

	public static final String TEM_TT_FECHADO = "ttFechado.has";
	public static final String ID_TT_FECHADO = "ttFechado.id";
	public static final String TT_FECHADO_TIPO = "ttFechado.tipo";
	public static final String TT_FECHADO_DENTRO_DO_PRAZO = "ttFechado.dentroDoPrazo";

	public static final String QUANTIDADE_MASSIVAS = "massiva.quantidade";
	public static final String MASSIVA_CODES = "massiva.codes";
	public static final String MASSIVA_DATA_TERMINO = "massiva.data_termino_";

	public static final String FATURA_TEM_DADOS = "FaturaAtual.TemDados";
	public static final String FATURA_VALOR = "FaturaAtual.Valor";
	public static final String FATURA_VENCIDA_FLAG_MAIS_7_DIAS = "FaturaAtual.vencidaFlagAMais7Dias";
	public static final String FATURA_DIA = "FaturaAtual.Dia";
	public static final String FATURA_MES = "FaturaAtual.Mes";
	public static final String FATURA_DATA = "FaturaAtual.Data";
	public static final String FATURA_METODO_PGO = "Fatura.MetodoPgo";
	public static final String FATURA_ABERTA_VENCEU = "FaturaAberta.Venceu";
	public static final String FATURA_ABERTA_DATA_VENCIMENTO = "FaturaAberta.DataVencimento";
	public static final String FATURA_ABERTA_VENCIDA_X_DIAS = "FaturaAberta.vencidaXDias";
	public static final String FATURA_ABERTA_EMERGENCIAL = "FaturaAberta.emergencial";
	public static final String FATURA_ABERTA_VALOR = "FaturaAberta.Valor";
	
	public static final String ULTIMA_FAT_GER__TEM_DADOS = "UltFatGer.TemDados"; 
	public static final String ULTIMA_FAT_GER__MAIS_7_DIAS = "UltFatGer.vencidaAMais7Dias";
	public static final String ULTIMA_FAT_GER__DT_VENC = "UltFatGer.dtVenc";
	
	public static final String ULTIMA_FATURA_PAGA = "UltimaFatura.Paga";
	public static final String ULTIMA_FATURA_DATA_PGTO = "UltimaFatura.DtPGTO";
	public static final String ULTIMA_FATURA_VALOR = "UltimaFatura.Valor";

	public static final String PAG_FATURA = "Pagamento.Fatura";
	public static final String PAG_FATURA_DIA = "Pagamento.Dia";
	public static final String PAG_FATURA_MES = "Pagamento.Mes";

	public static final String AG_ACTIVIA_TEM_AGENDAMENTO = "AgActivia.temAgendamento";
	public static final String AG_ACTIVIA_ID_OS = "AgActivia.idOS";
	public static final String AG_ACTIVIA_PERIODO = "AgActivia.periodo";
	public static final String AG_ACTIVIA_DESCRICAO = "AgActivia.descricao";
	public static final String AG_ACTIVIA_DETALHE = "AgActivia.detalhe";
	public static final String AG_ACTIVIA_TECNOLOGIA = "AgActivia.tecnologia";
	public static final String AG_ACTIVIA_TIPO = "AgActivia.tipo";
	public static final String AG_ACTIVIA_TIPO_GERAL = "AgActivia.tipoGeral";
	public static final String AG_ACTIVIA_DT_AGENDAMENTO = "AgActivia.dtAgendamento";
	public static final String AG_ACTIVIA_OBSERVACAO = "AgActivia.observacao";
	public static final String AG_ACTIVIA_VENCIDO = "AgActivia.vencido";

	public static final String AG_TV_ACTIVIA_STATUS = "AgTVActivia.status";
	public static final String AG_TV_TEM_AGENDAMENTO = "AgTVActivia.temAgendamentoTv";
	public static final String AG_TV_TEM_TV = "AgTVActivia.temTv";

	public static final String CRIA_SS_GPS_PARAMETROS_PID = "criaSS.pId";

	public static final String PORTABILIDADE_POSSUI_PORT = "Portabilidade.PossuiPortabilidade";
	public static final String PORTABILIDADE_CAUSE_CODE = "Portabilidade.CauseCode";
	public static final String PORTABILIDADE_CAUSE_DESC = "Portabilidade.CauseDesc";
	public static final String PORTABILIDADE_CURRENT_STATE = "Portabilidade.CurrentState";
	public static final String PORTABILIDADE_PROCESS_TYPE = "Portabilidade.ProcessType";
	public static final String PORTABILIDADE_SCHEDULE_DATE = "Portabilidade.ScheduleDate";
	public static final String PORTABILIDADE_SCHEDULE_FORMAT = "Portabilidade.ScheduleFormat";
	public static final String PORTABILIDADE_SCHEDULE_HORA = "Portabilidade.ScheduleHora";
	public static final String PORTABILIDADE_DAYS_OVERDUE = "Portabilidade.DaysOverdue";

	public static final String AG_PORTABILIDADE_PERIODO = "AgPortabilidadePeriodo";

	public static final String AG_PORTABILIDADE_VENCIDO = "AgPortabilidadeVencido";

	public static final String BLOQUEIO_CLIENTE_BLOQ = "Bloqueio.ClienteBloqueado";

	public static final String SS_INFO = "ss.info";
	public static final String SS_ULTIMA_ROW_ID = "ss.ultimaRowId";
	public static final String SS_INFO_GERADA = "ss.infoGerada";

	public static final String ETA_ATIVIDADE = "Eta.Atividade";
	public static final String ETA_DATA_AGENDAMENTO = "Eta.DataAgendamento";
	public static final String ETA_PERIODO_FIM = "Eta.PeriodoFim";
	public static final String ETA_PERIODO_INICIO = "Eta.PeriodoInicio";
	public static final String ETA_STATUS = "Eta.Status";
	public static final String ETA_IS_TV = "Eta.IsTV";
	public static final String ETA_AGENDAMENTO_VENCIDO = "Eta.IsAgendamentoVencido";

	public static final String REINCIDENCIA = "reincidencia.has";
	public static final String REINCIDENCIA_TIPO_PRODUTO = "reincidencia.tipoProduto";

	public static final String DATA_VCTO_ALTERADA = "Altera_vcto.data_vcto_alterada";

	public static final String CONTA_CLIENTE = "Conta.Cliente";
	public static final String CONTA_EMAIL_CONTATO = "Conta.EmailContato";
	public static final String CONTA_DIA_VENCIMENTO = "Conta.DiaVencimento";
	public static final String CONTA_MES_ATUAL = "Conta.MesAtual";
	public static final String CONTA_ALTEROU_DT_VCTO_HJ = "Conta.AlterarDtVctoHj";
	public static final String CONTA_AGING = "Conta.Aging";
	
	public static final String CONTA_OPCAO_A = "Conta.opcaoA";
	public static final String CONTA_OPCAO_B = "Conta.opcaoB";
	public static final String CONTA_OPCAO_C = "Conta.opcaoC";
	public static final String CONTA_OPCAO_D = "Conta.opcaoD";
	public static final String CONTA_OPCAO_E = "Conta.opcaoE";
	public static final String CONTA_OPCAO_F = "Conta.opcaoF";
	public static final String CONTA_OPCAO_G = "Conta.opcaoG";
	public static final String CONTA_OPCAO_H = "Conta.opcaoH";
	
	
	public static final String CONTA_ALTERA_DT_VENC = "Conta.AlteraDtVenc";
	public static final String CONTA_ESCOLHA_DT_ALTERAR = "Conta.EscolhaDtParaAlterar";
	public static final String CONTA_DIA_ALTERADO_DT_VENC = "Conta.DiaAlteradoDtVenc";
	public static final String CONTA_MES_ALTERADO_DT_VENC = "Conta.MesAlteradoDtVenc";
	public static final String CONTA_PARCELAMENTO = "Conta.ParcelamentoAtivo";

	public static final String PE_EMAIL_ENVIADO = "PE.EmailEnviado";

	public static final String FREEDOM = "Freedom.isFreedom";

	public static final String PRODUTO_PRECO = "Produto.Preco";
	public static final String PRODUTO_TOUCHE = "Produto.Touche";

	public static final String DEVE_ATUALIZAR_CTATO = "temp.deveOuvirAtCtato";
	
	public static final String TEMP_TIPO_BA = "temp.tipoBA";
	
	public static final String TEMP_TENTATIVAS_BA = "temp.tentativasBA";
	public static final String TEMP_PRODUCT_PNAME1 = "temp.ProductPname1";
	public static final String TEMP_PRODUCT_PVALUE1 = "temp.ProductPvalue1";

	public static final String TEMP_PRODUCT_PNAME2 = "temp.ProductPname2";
	public static final String TEMP_PRODUCT_PVALUE2 = "temp.ProductPvalue2";

	public static final String TEMP_PRODUCT_PNAME3 = "temp.ProductPname3";
	public static final String TEMP_PRODUCT_PVALUE3 = "temp.ProductPvalue3";

	public static final String PHONETYPE_TO_UPDATE = "temp.phonetype";

	public static final String COMPRA_PACOTE_TV = "Compra.PacoteTV";
	public static final String COMPRA_PACOTE_TV_STATUS = "Compra.Status";
	
	
	public static final String TV_HBO = "Tv.Hbo";
	public static final String TV_PFC = "Tv.Pfc";
	public static final String TV_TELECINE = "Tv.Telecine";
	public static final String TV_COMBATE = "Tv.Combate";
	public static final String TV_INTERNACIONAL = "Tv.Iternacional";
	public static final String TV_ESPORTE = "Tv.Esporte";
	public static final String TV_SEXYHOT = "Tv.Sexyhot";
	public static final String TV_SOSTV = "Tv.Sostv";
	public static final String TV_QUANTIDADE_DE_PACOTES = "Tv.QuantidadePacotes";
	public static final String TV_DESIGNADOR = "Tv.Designador";
	public static final String TV_VALOR_CONSUMO = "Tv.ValorConsumo";

	public static final String TV_TELECINE_FILE_NAME = "Tv.TelecineFileName";
	public static final String TV_HBO_FILE_NAME = "Tv.HboFileName";
	public static final String TV_PFC_FILE_NAME = "Tv.PfcFileName";
	public static final String TV_COMBATE_FILE_NAME = "Tv.CombateFileName";
	public static final String TV_INTERNACIONAL_FILE_NAME = "Tv.InternacionalFileName";
	public static final String TV_SEXYHOT_FILE_NAME = "Tv.SexyhotFileName";
	public static final String TV_ESPORTE_FILE_NAME = "Tv.EsporteFileName";
	public static final String TV_SOSTV_FILE_NAME = "Tv.SostvFileName";
	
	public static final String TELEFONIA_NOME_PLANO = "telefonia.nomePlano";
	public static final String TELEFONIA_PLANO_FILE_NAME = "telefonia.planoFileName";
	public static final String BANDA_LARGA_VELOCIDADE = "bandaLarga.velocidade";
	public static final String TV_NOME_PLANO = "Tv.NomePlano";
	public static final String TV_PLANO_FILE_NAME = "Tv.PlanoFileName";
	
	public static final String TEMP_PRODUCT_CODE = "Temp.ProductCode";
	public static final String VP_RETORNO = "VP_RETORNO";
	public static final String CODBARRAS_RETORNO = "CODBARRAS_RETORNO";
	
	
	public static final String COMPRA_TV_PRECO_TELECINE = "CompraTv.PrecoTelecine";
	public static final String COMPRA_TV_PRECO_HBO = "CompraTv.PrecoHBO";
	public static final String COMPRA_TV_PRECO_ESPORTES = "CompraTv.PrecoEsportes";
	public static final String COMPRA_TV_PRECO_ADULTO = "CompraTv.PrecoAdulto";
	public static final String COMPRA_TV_PRECO_COMBATE = "CompraTv.PrecoCombate";
	public static final String COMPRA_TV_PRECO_SOS_TV = "CompraTv.PrecoSOSTv";
	public static final String COMPRA_TV_PRECO_INTER = "CompraTv.PrecoInter";
	public static final String COMPRA_TV_PRECO_PFC_A_1REGIONAL = "CompraTv.PrecoPfcA1Regional";
	public static final String COMPRA_TV_PRECO_PFC_A_2REGIONAIS = "CompraTv.PrecoPfcA2Regionais";
	public static final String COMPRA_TV_PRECO_PFC_B_1REGIONAL = "CompraTv.PrecoPfcB1Regional";
	public static final String COMPRA_TV_PRECO_PFC_B_2REGIONAIS = "CompraTv.PrecoPfcB2Regionais";
	public static final String COMPRA_TV_PRECO_PFC_AB_1REGIONAL = "CompraTv.PrecoPfcAB1Regional";
	public static final String COMPRA_TV_PRECO_PFC_AB_2REGIONAIS = "CompraTv.PrecoPfcAB2Regionais";
	public static final String COMPRA_TV_PRECO_PFC_AB_TODOSREGIONAIS = "CompraTv.PrecoPfcABTodosRegionais";

	public static final String SEGUNDA_VIA_TRANSF_INTERNA = "SegundaVia.TransfInterna";
	public static final String SEGUNDA_VIA_EXISTE_FATURA_GERADA = "SegundaVia.ExisteFatGerada";
	public static final String SEGUNDA_VIA_NUMERO_FATURAS = "SegundaVia.NroFaturas";
	public static final String SEGUNDA_VIA_MES_1 = "SegundaVia.Mes1";
	public static final String SEGUNDA_VIA_MES_2 = "SegundaVia.Mes2";
	public static final String SEGUNDA_VIA_MES_3 = "SegundaVia.Mes3";
	public static final String SEGUNDA_VIA_TEM_SS_FATURAMENTO = "SegundaVia.TemSSFaturamento";
	public static final String SEGUNDA_VIA_TEM_SS_ALT_COBRANCA = "SegundaVia.TemSSAltCobranca";
	public static final String SEGUNDA_VIA_TEM_SS_ALT_INSTALACAO = "SegundaVia.TemSSAltInstalacao";
	public static final String SEGUNDA_VIA_ENVIOU_EMAIL = "SegundaVia.EnviouEmail";
	public static final String SEGUNDA_VIA_QTDE_FATURAS_ESCOLHIDAS = "SegundaVia.QtdeFaturasEscolhidas";
	public static final String SEGUNDA_VIA_STR_OPCAO_1 = "SegundaVia.StrOpcao1";
	public static final String SEGUNDA_VIA_STR_OPCAO_2_MES_1 = "SegundaVia.StrOpcao2Mes1";
	public static final String SEGUNDA_VIA_STR_OPCAO_2_MES_2 = "SegundaVia.StrOpcao2Mes2";
	public static final String SEGUNDA_VIA_STR_OPCAO_2_MES_3 = "SegundaVia.StrOpcao2Mes3";
	
	public static final String CODIGO_BARRAS_CODIGO_FATURA = "CodigoBarras.CodigoFatura";
	public static final String CODIGO_BARRAS_VALOR_FATURA = "CodigoBarras.ValorFatura";
	public static final String CODIGO_BARRAS_DATA_FATURA = "CodigoBarras.DataFatura";
	public static final String CODIGO_BARRAS_VCTO_FATURA = "CodigoBarras.VctoFatura";
	public static final String CODIGO_BARRAS_LINHA_DIGITAVEL = "CodigoBarras.LinhaDigitavel";
	
	public static final String MENSAGEM_EMERGENCIAL = "smartUra.MENSAGEM EMERGENCIAL";
	public static final String MENU_EMERGENCIAL = "smartUra.MENU EMERGENCIAL";
	
	public static final String SS_CANCELAMENTO_ID = "cancelamento.id";
	public static final String SS_CANCELAMENTO_STATUS = "cancelamento.status";
	public static final String SS_CANCELAMENTO_FECHAMENTO_1 = "cancelamento.fechamento1";
	public static final String SS_CANCELAMENTO_FECHAMENTO_2 = "cancelamento.fechamento2";
	public static final String SS_CANCELAMENTO_FECHAMENTO_3 = "cancelamento.fechamento3";
	public static final String SS_CANCELAMENTO_FECHAMENTO_4 = "cancelamento.fechamento4";
	public static final String SS_CANCELAMENTO_ORIGEM = "cancelamento.origem";
	public static final String SS_CANCELAMENTO_PROT_ATENDIMENTO = "cancelamento.protocolo";

	public static final String PESQUISA_R1 = "pesquisa.r1";
	public static final String PESQUISA_R2 = "pesquisa.r2";
	public static final String PESQUISA_R3 = "pesquisa.r3";
	public static final String PESQUISA_R4 = "pesquisa.r4";
	public static final String PESQUISA_R5 = "pesquisa.r5";
	
	public static final String PESQ_ID = "pesquisa.id";
	
	public static final String OBS = "OBS";
	public static final String VARNAME = "VARNAME";
	public static final String PID = "pid";
	
	public static final String PESQUISA_CHAMADA_ENTROU_PELA_URA = "pesquisa.chamadaViaUra";
	
	public static final String ALEGA_PGTO_STATUS = "alegaPgto.status";
	public static final String ALEGA_PGTO_BLACK_COUNT = "alegaPgto.blackCount";
	public static final String ALEGA_PGTO_DENTRO_DO_PRAZO = "alegaPgto.dentroDoPrazo";
	public static final String ALEGOU_PGTO_HORA = "alegouPgto.hora";
	
	public static final String TEMP_TAG = "temp.tag_";
	
	public static final String FIRMWARE_STATUS = "firmware.status";
	
	public static final String S5 = "S5";
	public static final String SNG_PRODUTO_DESCRICAO = "sng.produtoDescricao";
	public static final String SNG_PRODUTO_STATUS = "sng.produtoStatus";
	
	public static final String DACC_ID_BANCO = "dacc.idBanco";
	public static final String DACC_NOME_BANCO = "dacc.nomeBanco";
	public static final String DACC_AGENCIA = "dacc.agencia";
	public static final String DACC_DV_AGENCIA = "dacc.dvAgencia";
	public static final String DACC_CONTA = "dacc.conta";
	public static final String DACC_DV_CONTA = "dacc.dv_conta";
	
	public static final String DACC_VENCIDO = "dacc.vencido";
	public static final String DACC_SOLICITACAO_VENCE ="dacc.solicitacaoVence";
	public static final String DACC_MENU = "dacc.menu";
	
	public static final String DACC_OPERACAO = "dacc.operacao";
	public static final String DACC_AGENCIA_DIGITADA = "dacc.agenciaDigitada";
	public static final String DACC_AGENCIA_DG_DIGITADA = "dacc.agenciaDigitoDigitada";
	public static final String DACC_CONTA_DIGITADA = "dacc.contaDigitada";
	public static final String DACC_CONTA_DG_DIGITADA = "dacc.contaDigitoDigitada";
	
	public static final String DACC_BONUS = "dacc.bonus";
	public static final String NAME_CUSTOMER = "customer.name";
	public static final String BIRTHDATE = "customer.birthdate";
	public static final String CLASSIFICACAO_POS_VENDAS = "customer.classificacaoPosVendas";
	public static final String CICLO_FATURAMENTO = "customer.cicloFaturamento";
	public static final String TYPE_CUSTOMER = "customer.type";
	public static final String ESTADO = "customer.estado";
	public static final String NUMERO_EQUIPAMENTO = "instance.numEquipamento";
	public static final String DESIGNADOR = "instance.designador";
	public static final String DESIGNADOR_TV = "instance.designadorTv";
	public static final String FLAG_CICLO = "instance.flagCiclo";
	public static final String FLAG_TURBONET = "instance.flagTurbonet";
	public static final String FLAG_TV= "instance.flagTv";
	public static final String AGENCY_CODE = "agency.code";
	public static final String AGENCY_FONE = "agency.fone";
	public static final String AGENCY_LOGPONTO = "agency.logPonto";
	public static final String AGENCY_OPENDATE = "agency.openDate";
	public static final String AGENCY_CLOSEDATE = "agency.closeDate";
	public static final String AGENCY_STATUSMSG = "agency.statusMsg";
	public static final String AGENCY_STATUSREINCIDENCIA = "agency.statusReincidencia";
	public static final String AGENCY_WORKTIME = "agency.workTime";
	public static final String AGENCY_STATUSROTE = "agency.statusRote";
	public static final String AGENCY_MESSAGE = "agency.message";
	public static final String AGENCY_VDN = "agency.vdn";
	
	public static final String AGENCY_OUVIU_MSG = "agency.ouviuMsg";
	
	public static final String CANCELAMENTO_DOC_INSTANCIA_STATUS = "cancelamento.docInstanciaStatus";
	
	public static final String IDENTIFICACAO_TENTATIVA1 = "identificacao.tentativa1";
	public static final String IDENTIFICACAO_TENTATIVA2 = "identificacao.tentativa2";
	public static final String IDENTIFICACAO_SOLICITACOES_BC = "identificacao.solicitacoes";
	public static final String DIRECIONA_BC = "identificacao.direcionaBC";

	public static final String TEMP_NEXTFORMID = "temp.nextformid_";
	
	public static final String PARCELAMENTO_FAT_ACUMULADA = "parcelamento.temFaturaAcumulada";
	public static final String PARCELAMENTO_OUVIU_MENU = "parcelamento.OuviuMenu";

	//Parcelamento - CustomerScore AACTG
	public static final String PARCELAMENTO_ACCTG = "parcelamento.acctg";
	
	//Renomeados
	//Parcelamento - Acordo Vigente
	public static final String ACORDO_VIGENTE_ATIVO = "avg.ativo";
	public static final String ACORDO_VIGENTE_INTERACTION_STATUS = "avg.intStatus";
	public static final String ACORDO_VIGENTE_NRO_CONTRATO_ACORDO = "avg.nroAcordo";
	public static final String ACORDO_VIGENTE_FATURA_NRO = "avg.nroFatura";
	public static final String ACORDO_VIGENTE_FATURA_VALOR = "avg.faturaValor";
	public static final String ACORDO_VIGENTE_FATURA_DATA = "avg.faturaData";
	public static final String ACORDO_VIGENTE_FATURA_CODIGO_BARRAS = "avg.FaturaCodBarras";
	public static final String ACORDO_VIGENTE_FATURA_TIPO_BOLETO = "avg.tipoBoleto";
	public static final String ACORDO_VIGENTE_ACORDO_A_VISTA = "avg.acordoAVista";
	public static final String ACORDO_VIGENTE_BOLETO_VENCIDO = "avg.boletoVencido";
	public static final String ACORDO_VIGENTE_BOLETO_VENCIDO_7_DIAS = "avg.BoletoVencido7Dias";
	public static final String ACORDO_VIGENTE_BOLETO_STR_AUDIO_MES = "avg.strAudioMes";
		
	//Parcelamento - Dados Cyber
	public static final String DADOS_CADASTRAIS_CYBER_EMAIL = "cyber.email";
	public static final String DADOS_CADASTRAIS_CYBER_TEM_EMAIL = "cyber.temEmail";
	
	
	//Parcelamento - Faturas/Politicas Cliente Cyber
	public static final String CYBER_ERROR = "fatCyber.error";
	public static final String CYBER_ERROR_STATUS_CODE = "fatCyber.errorStatusCode";
	
	public static final String TYPE_CURRENT_PAYMENT_AGREEMENT = "fatCyber.grupoAcordo";
	public static final String TYPE_AGREEMENT_TERM_OR_CONDITION = "fatCyber.termOrCondition";
	public static final String CHANNEL_PERFORMING_REQUEST = "fatCyber.performRequest";
	
	//Faturas
	public static final String TOTAL_DE_FATURAS_CYBER = "fatCyber.totalFatCyber";
	
	public static final String FATURA_CYBER_ID = "fatCyber.fatCyberId";//"faturasClienteCyber.faturaCyberId"; //nova
	public static final String FATURA_CYBER_NRO = "fatCyber.fatCyberNro";
	public static final String FATURA_CYBER_DATA = "fatCyber.fatCyberData";
	public static final String FATURA_CYBER_VALOR = "fatCyber.fatCyberValor";
	

	//Atualiza variavel do announce dinamico
	public static final String VAR_FATURA_CYBER_DATA = "fatCyber.varFatCyberData";
	public static final String VAR_FATURA_CYBER_VALOR = "fatCyber.varFatCyberValor";
	public static final String VAR_FATURA_CYBER_ID = "fatCyber.varFatCyberId";
	public static final String VAR_FATURA_CYBER_NRO = "fatCyber.varFatCyberNro";


	//Políticas
	public static final String TOTAL_DE_POLITICAS_CYBER = "polCyber.totalPolCyber";
	
	public static final String VAR_CYBER_TEM_A_VISTA_POLITICA1 = "polCyber.varTemAVista";
	
	public static final String VAR_CYBER_CODIGO_POLITICA1 = "pol.varCodP1";
	public static final String VAR_CYBER_VCTO_ENTRADA1 = "pol.varVctoE1";
	public static final String VAR_CYBER_VALOR_ENTRADA1 = "pol.varValorE1";
	public static final String VAR_CYBER_QTDE_PARCELAS1 = "pol.varQtdeP1";
	public static final String VAR_CYBER_VALOR_PARCELAS1 = "pol.varValorP1";
	
	public static final String VAR_CYBER_CODIGO_POLITICA2 = "pol.varCodP2";
	public static final String VAR_CYBER_VCTO_ENTRADA2 = "pol.varVctoE2";
	public static final String VAR_CYBER_VALOR_ENTRADA2 = "pol.varValorE2";
	public static final String VAR_CYBER_QTDE_PARCELAS2 = "pol.varQtdeP2";
	public static final String VAR_CYBER_VALOR_PARCELAS2 = "pol.varValorP2";

	public static final String VAR_CYBER_CODIGO_POLITICA3 = "pol.varCodP3";
	public static final String VAR_CYBER_VCTO_ENTRADA3 = "pol.varVctoE3";
	public static final String VAR_CYBER_VALOR_ENTRADA3 = "pol.varValorE3";
	public static final String VAR_CYBER_QTDE_PARCELAS3 = "pol.varQtdeP3";
	public static final String VAR_CYBER_VALOR_PARCELAS3 = "pol.varValorP3";

	public static final String VAR_CYBER_CODIGO_POLITICA4 = "pol.varCodP4";
	public static final String VAR_CYBER_VCTO_ENTRADA4 = "pol.varVctoE4";
	public static final String VAR_CYBER_VALOR_ENTRADA4 = "pol.varValorE4";
	public static final String VAR_CYBER_QTDE_PARCELAS4 = "pol.varQtdeP4";
	public static final String VAR_CYBER_VALOR_PARCELAS4 = "pol.varValorP4";

	
	public static final String CYBER_CODIGO_POLITICA_ESCOLHIDA = "polEscolhida.Cod";
	public static final String CYBER_VCTO_ENTRADA_POLITICA_ESCOLHIDA = "polEscolhida.VctoEnt";
	public static final String CYBER_VALOR_ENTRADA_POLITICA_ESCOLHIDA = "polEscolhida.ValorEnt";
	public static final String CYBER_QTDE_PARCELAS_POLITICA_ESCOLHIDA = "polEscolhida.QtdeParc";
	public static final String CYBER_VALOR_PARCELAS_POLITICA_ESCOLHIDA = "polEscolhida.ValorParc";
	
	public static final String CYBER_CHOICE_MENU_POLITICA_ESCOLHIDA = "pol.ChoicePolEscolhida";
	
	//EmailSiebel
	public static final String SIEBEL_EMAIL = "siebel.emailSiebel";


	
	public static final String URASAN_ACAO = "urasan.acao";
	public static final String URASAN_MATRICULA = "urasan.matricula";
	public static final String URASAN_RG = "urasan.rg";
	public static final String URASAN_NASCIMENTO = "urasan.nascimento";
	public static final String URASAN_NOVASENHA = "urasan.novaSenha";
	public static final String URASAN_TENTATIVA = "urasan.tentativa";
	
	public static final String BENEFICIO_RETORNO = "beneficio.termino";
	public static final String BENEFICIO_STR_AUDIO_MES = "beneficio.strAudioMes";

	//Remover op 9 na segunda vocalização
	public static final String PROMPT_TRY = "PromptTry";
	
	//Ura FAX
	public static final String DOC_FAX = "temp.DocumentFax";
	
	public static final String TEL_FAX = "temp.TelefoneFax";
	
	public static final String VDN_FAX = "temp.VdnFax";
	
}
