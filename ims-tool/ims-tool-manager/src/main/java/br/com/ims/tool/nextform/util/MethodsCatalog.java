package br.com.ims.tool.nextform.util;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.avaya.pso.boticario.Z_CRMF_GET_CLASS_CLI2Stub;
import br.com.avaya.pso.boticario.Z_CRMF_GET_CLASS_CLI2Stub.Char2;
import br.com.avaya.pso.boticario.Z_CRMF_GET_CLASS_CLI2Stub.Z_CRMF_GET_CLASS_CLI2Response;
import br.com.ims.tool.nextform.model.MethodInvocationVO;
import br.com.ims.tool.nextform.persistence.MethodsCatalogDao;

public class MethodsCatalog {

	private static Logger logger = Logger.getLogger(MethodsCatalog.class);
	private static InitialContext ctx = null;

	public MethodInvocationVO getContextValue(String jsonContext, Map<String, String> parameters) {

		String contextValue = null;
		for (String key : parameters.keySet()) {

			if (parameters.get(key) != null) {

				String fieldValue = null;

				if (parameters.get(key).indexOf(".") == -1) {
					fieldValue = MethodInvocationUtils.getFieldValue(parameters.get(key));
				}

				if (fieldValue != null) {
					contextValue = MethodInvocationUtils.getContextValue(jsonContext, fieldValue);
				} else {
					contextValue = MethodInvocationUtils.getContextValue(jsonContext, parameters.get(key));
				}
			}
		}

		if (contextValue == null) {
			contextValue = UraConstants.NULL;
		}

		return MethodInvocationVO.getInstance(jsonContext, contextValue);
	}

	public MethodInvocationVO doCompareValues(String jsonContext, Map<String, String> parameters) {

		String retorno = null;
		long errorCode = FormConstants.NO_ERROR;
		try {
			String var1 = parameters.get("VAR1");
			String var2 = parameters.get("VAR2");

			if (var1.indexOf(".") == -1) {
				var1 = MethodInvocationUtils.getFieldValue(var1);
			}

			var1 = MethodInvocationUtils.getContextValue(jsonContext, var1);

			if (var2.indexOf(".") == -1) {
				var2 = MethodInvocationUtils.getFieldValue(var2);
			}

			var2 = MethodInvocationUtils.getContextValue(jsonContext, var2);

			String operation = parameters.get("OPERATION");
			boolean result = false;
			if (FormConstants.OPERATION_IGUAL.equals(operation)) {
				result = var1.equals(var2); // TODO está comparando string ao
											// invés de números
			} else if (FormConstants.OPERATION_DIFERENTE.equals(operation)) {
				result = !var1.equals(var2); // TODO está comparando string ao
												// invés de números
			} else if (FormConstants.OPERATION_MAIOR.equals(operation)) {
				if (StringUtils.isNumeric(var1) && StringUtils.isNumeric(var2)) {
					if (Float.parseFloat(var1) > Float.parseFloat(var2)) {
						result = true;
					}
				}
			} else if (FormConstants.OPERATION_MENOR.equals(operation)) {
				if (StringUtils.isNumeric(var1) && StringUtils.isNumeric(var2)) {
					if (Float.parseFloat(var1) < Float.parseFloat(var2)) {
						result = true;
					}
				}
			} else if (FormConstants.OPERATION_MENOR_IGUAL.equals(operation)) {
				if (Float.parseFloat(var1) <= Float.parseFloat(var2)) {
					result = true;
				}
			} else if (FormConstants.OPERATION_MAIOR_IGUAL.equals(operation)) {
				if (StringUtils.isNumeric(var1) && StringUtils.isNumeric(var2)) {
					if (Float.parseFloat(var1) >= Float.parseFloat(var2)) {
						result = true;
					}
				}
			} else if (FormConstants.OPERATION_EQUALS.equals(operation)) {
				result = var1.equalsIgnoreCase(var2);
			} else if (FormConstants.OPERATION_NUMBER_IGUAL.equals(operation)) {
				if (StringUtils.isNumeric(var1) && StringUtils.isNumeric(var2)) {
					if (Float.parseFloat(var1) == Float.parseFloat(var2)) {
						result = true;
					}
				}
			} else if (StringUtils.equalsIgnoreCase(FormConstants.OPERATION_BEFORE_DATE, StringUtils.trim(operation))) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				result = sdf.parse(var1).before(sdf.parse(var2));
			} else if (StringUtils.equalsIgnoreCase(FormConstants.OPERATION_AFTER_DATE, StringUtils.trim(operation))) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				result = sdf.parse(var1).after(sdf.parse(var2));
			}

			retorno = null;
			if (result) {
				retorno = UraConstants.YES;
			} else {
				retorno = UraConstants.NO;
			}
		} catch (Exception e) {
			errorCode = FormConstants.ERROR_GENERIC_EXCEPTION;
			logger.error("Erro ao comparar valores.", e);
		}

		if (retorno == null) {
			retorno = UraConstants.NULL;
		}

		return MethodInvocationVO.getInstance(jsonContext, retorno, errorCode);
	}

	public MethodInvocationVO setContextValue(String jsonContext, Map<String, String> parameters) {

		boolean updateValue = true;
		if (parameters.containsKey("UPDATE_VALUE")) {
			if (!Boolean.parseBoolean(parameters.get("UPDATE_VALUE").toLowerCase())) {
				updateValue = false;
			}
		}
		jsonContext = MethodInvocationUtils.setContextValue(jsonContext, parameters.get("VARNAME"),
				parameters.get("VARVALUE"), updateValue);
		return MethodInvocationVO.getInstance(jsonContext);
	}

	public MethodInvocationVO setContextValuesUsingPattern(String jsonContext, Map<String, String> parameters) {

		jsonContext = MethodInvocationUtils.setContextValues(jsonContext, parameters.get("VARPATTERN"),
				parameters.get("VARVALUE"));
		return MethodInvocationVO.getInstance(jsonContext);
	}

	public static MethodInvocationVO servicesDisconnect(String jsonContext, Map<String, String> parameters) {
		MethodInvocationVO methodInvocationVO = MethodInvocationVO.getInstance();
		methodInvocationVO.setJsonContext(jsonContext);
		methodInvocationVO.setValue(UraConstants.NO);
		try {
			methodInvocationVO.setValue(UraConstants.YES);
		} catch (Exception e) {
			methodInvocationVO.setValue(UraConstants.NULL);
			logger.error("Falha - servicesDisconnect: " + e.getMessage());
			e.printStackTrace();
		}

		methodInvocationVO.setJsonContext(jsonContext);

		return methodInvocationVO;
	}

	public MethodInvocationVO getServiceHour(String jsonContext, Map<String, String> parameters) {

		String serviceHour = MethodInvocationUtils.getContextValue(jsonContext, MapValues.TEMP_HORARIO);
		MethodsCatalogDao dao = new MethodsCatalogDao();
		MethodInvocationVO methodInvocationVO = MethodInvocationVO.getInstance();
		try {
			if (dao.inServiceHour(serviceHour)) {
				methodInvocationVO.setValue(UraConstants.YES);
			} else {
				methodInvocationVO.setValue(UraConstants.NO);
			}
		} catch (Exception e) {
			logger.error("Erro getServiceHour ", e);
			methodInvocationVO.setValue(UraConstants.NO);
		}
		methodInvocationVO.setErrorCode(0);
		methodInvocationVO.setJsonContext(jsonContext);

		return methodInvocationVO;
	}

	public MethodInvocationVO getSAPClient(String jsonContext, Map<String, String> parameters) {

		MethodInvocationVO methodInvocationVO = MethodInvocationVO.getInstance();
		methodInvocationVO.setJsonContext(jsonContext);
		methodInvocationVO.setValue(UraConstants.FAIL);

		String transactionURL = "http://sappcrm.boticario.net:8002/sap/bc/srt/rfc/sap/z_crmf_get_class_cli2/600/z_crmf_get_class_cli2/binding";
		String transactionTimeout = "10";
		String partner = MethodInvocationUtils.getContextValue(jsonContext, MapValues.PROMPT_COLLECT);

		try {
			br.com.avaya.pso.boticario.Z_CRMF_GET_CLASS_CLI2Stub.Char10 partner10 = new br.com.avaya.pso.boticario.Z_CRMF_GET_CLASS_CLI2Stub.Char10();
			Options options = new Options();
			options.setTimeOutInMilliSeconds(Integer.parseInt(transactionTimeout) * 1000);
			options.setTo(new EndpointReference(transactionURL));
			Z_CRMF_GET_CLASS_CLI2Stub stub = new Z_CRMF_GET_CLASS_CLI2Stub(transactionURL);
			stub._getServiceClient().setOptions(options);
			Z_CRMF_GET_CLASS_CLI2Stub.Z_CRMF_GET_CLASS_CLI2 partnerCLI2 = new Z_CRMF_GET_CLASS_CLI2Stub.Z_CRMF_GET_CLASS_CLI2();
			partner10.setChar10(partner);
			partnerCLI2.setI_PARTNER(partner10);
			Z_CRMF_GET_CLASS_CLI2Response response = stub.z_CRMF_GET_CLASS_CLI2(partnerCLI2);

			Char2 classif = response.getE_CLASSIFIC();
			br.com.avaya.pso.boticario.Z_CRMF_GET_CLASS_CLI2Stub.Numeric2 estr = response.getE_TP_ESTR();
			br.com.avaya.pso.boticario.Z_CRMF_GET_CLASS_CLI2Stub.Numeric2 neg = response.getE_TP_NEG();
			br.com.avaya.pso.boticario.Z_CRMF_GET_CLASS_CLI2Stub.Numeric2 sist = response.getE_TP_SIST();

			String E_CLASSIFIATEND = classif.getChar2();
			String E_TP_ESTRS = estr.getNumeric2();
			String E_TP_NEGS = neg.getNumeric2();
			String E_TP_SISTS = sist.getNumeric2();

			Integer E_TP_ESTR = Integer.valueOf(E_TP_ESTRS);
			Integer E_TP_NEG = Integer.valueOf(E_TP_NEGS);
			Integer E_TP_SIST = Integer.valueOf(E_TP_SISTS);
			String tp_loja = "";

			if (E_TP_NEG == 1 && E_TP_ESTR == 3) { // BOTICÁRIO LOJA
				tp_loja = UraConstants.TP_BOTICARIO_LOJA;
			} else if (E_TP_NEG == 4 && (E_TP_ESTR == 1 || E_TP_ESTR == 2 || E_TP_ESTR == 3)) {// EUDORA
																								// LOJA
				tp_loja = UraConstants.TP_EUDORA_LOJA;
			} else if (E_TP_NEG == 3 && (E_TP_ESTR == 1 || E_TP_ESTR == 2 || E_TP_ESTR == 3)) {// QDB
				tp_loja = UraConstants.TP_QDB;
			} else if (E_TP_NEG == 2 && (E_TP_ESTR == 1 || E_TP_ESTR == 2 || E_TP_ESTR == 3)) { // TBB
				tp_loja = UraConstants.TP_TBB;
			} else if (E_TP_NEG == 5 && E_TP_ESTR == 3) { // BOTICÁRIO
															// INTERBELLE
				tp_loja = UraConstants.TP_BOTICARIO_INTERBELLE;
			} else if (E_TP_NEG == 6 && (E_TP_ESTR == 1 || E_TP_ESTR == 2 || E_TP_ESTR == 3)) { // QDB
																								// INTERBELLE
				tp_loja = UraConstants.TP_QDB_INTERBELLE;
			} else if ((E_TP_NEG == 1 && E_TP_ESTR == 1) || (E_TP_NEG == 5 && E_TP_ESTR == 1)) { // BOTICÁRIO
																									// VD
				tp_loja = UraConstants.TP_BOTICARIO_VD;
			} else if ((E_TP_NEG == 1 && E_TP_ESTR == 2) || (E_TP_NEG == 5 && E_TP_ESTR == 2)
					|| (E_TP_NEG == 7 && (E_TP_ESTR == 1 || E_TP_ESTR == 2 || E_TP_ESTR == 3))) { // BOTICÁRIO
																									// HIBRIDO
				tp_loja = UraConstants.TP_HIBRIDO;
			}

			methodInvocationVO.setValue(UraConstants.SUCCESS);
			jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.TP_LOJA, tp_loja, true);
			jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.CLASSIFICACAO_TRANSF, E_CLASSIFIATEND, true);

		} catch (AxisFault e) {
			methodInvocationVO.setValue(UraConstants.FAIL);
		} catch (RemoteException e) {
			methodInvocationVO.setValue(UraConstants.FAIL);
		} catch (Exception ex) {
			ex.printStackTrace();
			methodInvocationVO.setValue(UraConstants.FAIL);
		}

		methodInvocationVO.setJsonContext(jsonContext);

		return methodInvocationVO;
	}

	public MethodInvocationVO getHoliday(String jsonContext, Map<String, String> parameters) {

		String ivr = MethodInvocationUtils.getContextValue(jsonContext, MapValues.IVR);
		MethodsCatalogDao dao = new MethodsCatalogDao();
		MethodInvocationVO methodInvocationVO = MethodInvocationVO.getInstance();
		try {
			if (dao.inHoliday(ivr)) {
				methodInvocationVO.setValue(UraConstants.YES);
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.SUNDAY_HOLIDAY,
						UraConstants.YES, true);
			} else {
				methodInvocationVO.setValue(UraConstants.NO);
				Date date = new Date();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(date);
				if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
					jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.SUNDAY_HOLIDAY,
							UraConstants.YES, true);
				} else {
					jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.SUNDAY_HOLIDAY,
							UraConstants.NO, true);
				}
			}

		} catch (Exception e) {
			logger.error("Erro getHoliday ", e);
			methodInvocationVO.setValue(UraConstants.NO);
		}
		methodInvocationVO.setErrorCode(0);
		methodInvocationVO.setJsonContext(jsonContext);

		return methodInvocationVO;
	}
	
	public MethodInvocationVO getParameter(String jsonContext, Map<String, String> parameters) {

		MethodsCatalogDao dao = new MethodsCatalogDao();
		MethodInvocationVO methodInvocationVO = MethodInvocationVO.getInstance();
		String flag = parameters.get("VALUE");
		
		try {
			if (dao.getParameter(flag)) {
				methodInvocationVO.setValue(UraConstants.YES);
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.SUNDAY_HOLIDAY,
						UraConstants.YES, true);
			} else {
				methodInvocationVO.setValue(UraConstants.NO);
				Date date = new Date();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(date);
				if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
					jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.SUNDAY_HOLIDAY,
							UraConstants.YES, true);
				} else {
					jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.SUNDAY_HOLIDAY,
							UraConstants.NO, true);
				}
			}

		} catch (Exception e) {
			logger.error("Erro getHoliday ", e);
			methodInvocationVO.setValue(UraConstants.NO);
		}
		methodInvocationVO.setErrorCode(0);
		methodInvocationVO.setJsonContext(jsonContext);

		return methodInvocationVO;
	}
	
 
	public MethodInvocationVO getMensagensURA(String jsonContext, Map<String, String> parameters) {

		MethodInvocationVO methodInvocationVO = MethodInvocationVO.getInstance();
		methodInvocationVO.setValue(UraConstants.NO);

		Boolean hasNext = Boolean.parseBoolean(MethodInvocationUtils.getContextValue(jsonContext, MapValues.MSG_HAS_NEXT));
		String ddd = MethodInvocationUtils.getContextValue(jsonContext, MapValues.DDD);

		// Caso não esteja setado o ddd no contexto, pegar os dois primeiros
		// digitos do ANI.
		if (StringUtils.isBlank(ddd)) {
			String ani = MethodInvocationUtils.getContextValue(jsonContext, MapValues.ANI);

			if (StringUtils.isNotBlank(ani)) {
				ddd = ani.substring(0, 2);
			}
		}

		if (!hasNext) {
			// Buscar novas mensagens no banco
			try {
				MethodsCatalogDao dao = new MethodsCatalogDao();
				Map<Integer, String> mapAudio = dao.getMessage(MethodInvocationUtils.getContextValue(jsonContext, MapValues.MSG_SPOT), ddd);

				if (mapAudio.size() > 0) {
					jsonContext = setMessageContext(mapAudio, jsonContext);
					hasNext = Boolean.TRUE;
					methodInvocationVO.setErrorCode(0);
					methodInvocationVO.setValue(UraConstants.YES);
				} else {
					jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_COUNT, "-1", true);
					jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_HAS_NEXT, "false", true);
					methodInvocationVO.setErrorCode(0);
					methodInvocationVO.setValue(UraConstants.NO);
				}

			} catch (Exception e) {
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_COUNT, "-1", true);
				methodInvocationVO.setErrorCode(-1);
				methodInvocationVO.setValue(UraConstants.ERRO);
			}

		} else {
			// Já existe mensagem sendo trabalhada
			jsonContext = updateMessageContext(jsonContext);
			if(MethodInvocationUtils.getContextValue(jsonContext, MapValues.MSG_HAS_NEXT).equalsIgnoreCase("true")){
				methodInvocationVO.setValue(UraConstants.YES);
				methodInvocationVO.setErrorCode(0);
			}else{
				methodInvocationVO.setValue(UraConstants.NO);
				methodInvocationVO.setErrorCode(0);
			}
		}

		methodInvocationVO.setJsonContext(jsonContext);
		return methodInvocationVO;
	}

	private String setMessageContext(Map<Integer, String> mapAudio, String jsonContext) {

		String audios = "";
		// Primeira vez set context
		audios = setAudioList(mapAudio, audios);
		jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_MESSAGES, audios, true);
		jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_AUDIO, mapAudio.get(1), true);
		jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_HAS_NEXT, "true", true);
		jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_COUNT,
				String.valueOf(mapAudio.size()), true);
		
		return jsonContext;

	}

	private String setAudioList(Map<Integer, String> mapAudio, String audios) {

		for (int j = 1; j <= mapAudio.size(); j++) {
			if (j == 1) {
				audios = mapAudio.get(j);
			} else {
				audios = audios + ";" + mapAudio.get(j);
			}
		}
		return audios;
	}

	private String updateMessageContext(String jsonContext) {

		Integer count = Integer.parseInt(MethodInvocationUtils.getContextValue(jsonContext, MapValues.MSG_COUNT));
		String audios = MethodInvocationUtils.getContextValue(jsonContext, MapValues.MSG_MESSAGES);
		String[] arrayAudio = audios.split(";");
		try {
			if (arrayAudio.length > 1) {
				// Tem mais mensagem para vocalizar
				for (int i = 1; i < arrayAudio.length; i++) {
					if (i == 1) {
						audios = arrayAudio[i];
					} else {
						audios = audios + ";" + arrayAudio[i];
					}
				}
				count--;
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_COUNT,String.valueOf(count), true);
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_AUDIO, arrayAudio[1], true);
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_MESSAGES, audios, true);
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_HAS_NEXT, "true", true);
				
			} else if (arrayAudio.length == 1) {

				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_COUNT, "0", true);
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_HAS_NEXT, "false", true);
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_AUDIO, "", true);
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_MESSAGES, "", true);
			} else {

				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_COUNT, "-1", true);
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_HAS_NEXT, "false",true);
			}
		} catch (Exception e) {
			jsonContext = MethodInvocationUtils.setContextValue(jsonContext, MapValues.MSG_COUNT, "-1", true);
		}
		return jsonContext;
	}


}
