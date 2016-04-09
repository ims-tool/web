package br.com.ims.tool.nextform.util;

public interface FormConstants {
	
	public static final long TYPE_ANSWER = 0L;
	public static final long TYPE_ANNOUNCE = 1L;
	public static final long TYPE_PROMPT_COLLECT = 2L;
	public static final long TYPE_MENU = 3L;
	public static final long TYPE_FLOW = 4L;
	public static final long TYPE_DECISION= 5L;
	public static final long TYPE_OPERATION = 6L;
	public static final long TYPE_TRANSFER= 7L;
	public static final long TYPE_DISCONNECT = 8L;
	public static final long TYPE_FLOW_INTERNAL = 9L;
	public static final long TYPE_NOMATCHINPUT= 12L;
	public static final long TYPE_CHOICE = 13L;
	public static final long TYPE_DECISIONCHANCE = 14L;
	public static final long TYPE_RETURN = 15L;
	public static final long TYPE_NOMATCH = 16L;
	public static final long TYPE_NOINPUT = 17L;
	
	public static final long FORM_DEFAULT = 1667L;
	
	public static final String TYPE_TEXT = "TEXT";
	public static final String TYPE_NUMERIC = "NUMERIC";
	public static final String TYPE_DATE = "DATE";
	
	public static final String OPERATION_IGUAL = "=";
	public static final String OPERATION_MAIOR = ">";
	public static final String OPERATION_MENOR = "<";
	public static final String OPERATION_MENOR_IGUAL = "<=";
	public static final String OPERATION_MAIOR_IGUAL = ">=";
	public static final String OPERATION_DIFERENTE = "<>";
	public static final String OPERATION_IN = "IN";
	public static final String OPERATION_NOT_IN = "NOT IN";
	public static final String OPERATION_BETWEEN = "BETWEEN";
	public static final String OPERATION_EQUALS = "EQUALS";
	public static final String OPERATION_NUMBER_IGUAL = "==";
	public static final String OPERATION_BEFORE_DATE = "before";
	public static final String OPERATION_AFTER_DATE = "after";	
	
	public static final String NEXT_FORM = "NEXT FORM";
	public static final String ABANDONO_STATUS = "A";
	public static final String RETENCAO_STATUS = "R";
	public static final String TRANSFER_STATUS = "T";
	
	public static final String FORM_NAO_ENCONTRADO = "FORM_NAO_ENCONTRADO";
	public static final String CONTEXTO = "CONTEXTO";
	public static final String ERRO = "ERRO";
	public static final String TAG = "TAG";
	
	public static final long NO_ERROR = 0L;
	public static final long ERROR_GENERIC_EXCEPTION = -1L;
	public static final long ERROR_REMOTE_EXCEPTION = -2L;
	public static final long ERROR_NULLPOINTER_EXCEPTION = -3L;
	public static final long ERROR_SQL_EXCEPTION = -4L;
	public static final long ERROR_PARSE_EXCEPTION = -5L;
	public static final long ERROR_TIMEOUT_EXCEPTION = -6L;
	public static final long ERROR_SEM_REGISTROS = -7L;
	public static final long ERROR_S8_INVOICE_ACCOUNT_NULL = -8L;
	public static final long ERROR_S5_INVOICE_ACCOUNT_NULL = -9L;
	public static final long ERROR_EJB_EXCEPTION = -10L;
	public static final long ERROR_BUSINESS_EXCEPTION = -11L;
	public static final long ERROR_METHOD_OFF= -12L;
	public static final long ERROR_INTERRUPTED_EXCEPTION= -13L;
	
}
