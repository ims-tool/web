package br.com.ims.tool.nextform.util;

import java.text.SimpleDateFormat;
import java.util.Collection;

public class UraUtils {
	
	public static boolean isNotNull(Object objeto)  {
		
		if (objeto == null) {
			return false;
		}
		return true;
	}
	
	public static boolean validateTypeText(String operacao,
			String resultado, String value1, Collection<String> lista) {
		try {
			if (FormConstants.OPERATION_IGUAL.equals(operacao)) {
				if (resultado.equals(value1)) {
					return true;
				}
			} else if (FormConstants.OPERATION_DIFERENTE.equals(operacao)) {
				if (!resultado.equals(value1)) {
					return true;
				}
				
			} else if (FormConstants.OPERATION_IN.equals(operacao)) {
				for (String str : lista) {
					if (resultado.equals(str)) {
						return true;
					}
				}
			} else if (FormConstants.OPERATION_NOT_IN.equals(operacao)) {
				for (String str : lista) {
					if (resultado.equals(str)) {
						return false;
					}
				}
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public static boolean validateTypeNumeric(String operacao,
			String resultado, String value1, String value2, Collection<String> lista) {
		
		try {
			
			if (FormConstants.OPERATION_IGUAL.equals(operacao)) {
				if (Float.valueOf(resultado).equals(Float.valueOf(value1))) {
					return true;
				}
			} else if (FormConstants.OPERATION_DIFERENTE.equals(operacao)) {
				if (! Float.valueOf(resultado).equals(Float.valueOf(value1))) {
					return true;
				}
				
			} else if (FormConstants.OPERATION_MENOR.equals(operacao)) {
				if (Float.valueOf(resultado) < Float.valueOf(value1)) {
					return true;
				}
				
			}  else if (FormConstants.OPERATION_MAIOR.equals(operacao)) {
				if (Float.valueOf(resultado) > Float.valueOf(value1)) {
					return true;
				}
				
			} else if (FormConstants.OPERATION_IN.equals(operacao)) {
				for (String str : lista) {
					if (Float.valueOf(resultado).equals(Float.valueOf(str))) {
						return true;
					}
				}
			} else if (FormConstants.OPERATION_NOT_IN.equals(operacao)) {
				for (String str : lista) {
					if (Float.valueOf(resultado).equals(Float.valueOf(str))) {
						return false;
					}
				}
				return true;
				
			} else if (FormConstants.OPERATION_BETWEEN.equals(operacao)) {
				
				if ((Float.valueOf(resultado) >= Float.valueOf(value1))
						&& (Float.valueOf(resultado) <= Float.valueOf(value2))) {
					return true;
				}
	
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public static boolean validateTypeDate(String operacao, String resultado,
			String value1, String value2, Collection<String> lista) {
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			if (FormConstants.OPERATION_IGUAL.equals(operacao)) {
				if (sdf.parse(resultado).equals(sdf.parse(value1))) {
					return true;
				}
			} else if (FormConstants.OPERATION_DIFERENTE.equals(operacao)) {
				if (! sdf.parse(resultado).equals(sdf.parse(value1))) {
					return true;
				}
				
			} else if (FormConstants.OPERATION_MENOR.equals(operacao)) {
				if (sdf.parse(resultado).before(sdf.parse(value1))) {
					return true;
				}
				
			}  else if (FormConstants.OPERATION_MAIOR.equals(operacao)) {
				if (sdf.parse(resultado).after(sdf.parse(value1))) {
					return true;
				}
				
			} else if (FormConstants.OPERATION_IN.equals(operacao)) {
				for (String str : lista) {
					if (sdf.parse(resultado).equals(str)) {
						return true;
					}
				}
			} else if (FormConstants.OPERATION_NOT_IN.equals(operacao)) {
				for (String str : lista) {
					if (sdf.parse(resultado).equals(str)) {
						return false;
					}
				}
				return true;
				
			} else if (FormConstants.OPERATION_BETWEEN.equals(operacao)) {
				
				if ((sdf.parse(resultado).after(sdf.parse(value1)))
						&& (sdf.parse(resultado).before(sdf.parse(value2)))) {
					return true;
				}
	
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public static void concatenaParametros(String name, String value, StringBuffer parametros) {
		
		if (value != null) {
			if (parametros != null && parametros.length() > 0) {
				parametros.append(";");
			}
			parametros.append(name).append("=").append(value);
		}
	}
	
	public static String gerarUUI(String jsonContext) {
		return MethodInvocationUtils.getContextValue(jsonContext, MapValues.INSTANCE)+"@"
		+MethodInvocationUtils.getContextValue(jsonContext, MapValues.ANI)+"@"
		+MethodInvocationUtils.getContextValue(jsonContext, MapValues.UCID)+"@"
		+MethodInvocationUtils.getContextValue(jsonContext, MapValues.PROTOCOLID)+"@"
		+MethodInvocationUtils.getContextValue(jsonContext, MapValues.START_DATE)+"@";
	}
	public static int transformaDataEmHoras(String data)
	{		
		int horas = 0;
		try
		{
			horas = Integer.valueOf(data.substring(0,2)) * 24;
			horas += Integer.valueOf(data.substring(3,5)) * 30 * 24;
			horas += Integer.valueOf(data.substring(6,10)) * 364 * 24;
			horas += Integer.valueOf(data.substring(11,13));		
			return horas;
		}
		catch(Exception e)
		{
			horas = 0;
		}
		return horas;		
	}
	
	
	public static String formataDataUraSan(String data){
		
		StringBuilder stringBuilder = new StringBuilder(data);  
		stringBuilder.insert(data.length() - 2, "/");
		stringBuilder.insert(data.length() - 4, "/"); 
		
		return stringBuilder.toString();
	}

}
