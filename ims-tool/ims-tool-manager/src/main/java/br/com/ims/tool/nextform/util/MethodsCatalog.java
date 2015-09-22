package br.com.ims.tool.nextform.util;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.ims.tool.nextform.model.MethodInvocationVO;


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
				result = var1.equals(var2); // TODO está comparando string ao invés de números
			} else if (FormConstants.OPERATION_DIFERENTE.equals(operation)) {
				result = !var1.equals(var2); // TODO está comparando string ao invés de números
			} else if (FormConstants.OPERATION_MAIOR.equals(operation)) {
				if(StringUtils.isNumeric(var1) && StringUtils.isNumeric(var2)){
					if (Float.parseFloat(var1) > Float.parseFloat(var2)) {
						result = true;
					}					
				}
			} else if (FormConstants.OPERATION_MENOR.equals(operation)) {
				if(StringUtils.isNumeric(var1) && StringUtils.isNumeric(var2)){
					if (Float.parseFloat(var1) < Float.parseFloat(var2)) {
						result = true;
					}					
				}
			} else if (FormConstants.OPERATION_MENOR_IGUAL.equals(operation)) {
				if (Float.parseFloat(var1) <= Float.parseFloat(var2)) {
					result = true;
				}
			} else if (FormConstants.OPERATION_MAIOR_IGUAL.equals(operation)) {
				if(StringUtils.isNumeric(var1) && StringUtils.isNumeric(var2)){
					if (Float.parseFloat(var1) >= Float.parseFloat(var2)) {
						result = true;
					}					
				}
			} else if (FormConstants.OPERATION_EQUALS.equals(operation)) {
				result = var1.equalsIgnoreCase(var2);
			} else if (FormConstants.OPERATION_NUMBER_IGUAL.equals(operation)) {
				if(StringUtils.isNumeric(var1) && StringUtils.isNumeric(var2)){
					if (Float.parseFloat(var1) == Float.parseFloat(var2)) {
						result = true;
					}					
				}
			}else if( StringUtils.equalsIgnoreCase(FormConstants.OPERATION_BEFORE_DATE, StringUtils.trim(operation)) ){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				result = sdf.parse(var1).before(sdf.parse(var2));
			}else if( StringUtils.equalsIgnoreCase(FormConstants.OPERATION_AFTER_DATE, StringUtils.trim(operation)) ){
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
			if (!Boolean.parseBoolean(parameters.get("UPDATE_VALUE").toLowerCase())) 	{
				updateValue = false;
			}
		}
		jsonContext = MethodInvocationUtils.setContextValue(jsonContext, parameters.get("VARNAME"), parameters.get("VARVALUE"), updateValue);
		return MethodInvocationVO.getInstance(jsonContext);
	}

	public MethodInvocationVO setContextValuesUsingPattern(String jsonContext, Map<String, String> parameters) {

		jsonContext = MethodInvocationUtils.setContextValues(jsonContext, parameters.get("VARPATTERN"), parameters.get("VARVALUE"));
		return MethodInvocationVO.getInstance(jsonContext);
	}
	
	public static MethodInvocationVO servicesDisconnect(String jsonContext, Map<String, String> parameters) {
		MethodInvocationVO methodInvocationVO = MethodInvocationVO.getInstance();
		methodInvocationVO.setJsonContext(jsonContext);
		methodInvocationVO.setValue(UraConstants.NO);
		try {
				methodInvocationVO.setValue(UraConstants.YES);
		}
		catch (Exception e) {
			methodInvocationVO.setValue(UraConstants.NULL);
				logger.error("Falha - servicesDisconnect: " + e.getMessage());
				e.printStackTrace();
				}

		methodInvocationVO.setJsonContext(jsonContext);
		
		return methodInvocationVO;
	}



}
