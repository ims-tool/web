package br.com.ims.tool.nextform.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.ims.tool.nextform.model.MethodInvocationVO;
import br.com.ims.tool.nextform.util.FormConstants;
import br.com.ims.tool.nextform.util.MapValues;
import br.com.ims.tool.nextform.util.MethodInvocationUtils;
import br.com.ims.tool.nextform.util.UraConstants;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MethodInvocation  {

	private static Logger logger = Logger.getLogger(MethodInvocation.class);
	MethodInvocationVO methodInvocationVO;
	
	public MethodInvocationVO invoke(final String jsonContext, final String methodName, final Map<String, String> parameters, int timeout, boolean isAtivo) {
		
		String instancia = MethodInvocationUtils.getContextValue(jsonContext, MapValues.INSTANCE);
		methodInvocationVO = MethodInvocationVO.getInstance(jsonContext, UraConstants.NULL);
		
		long time = System.currentTimeMillis();		
		
		try {
			if (isAtivo) {
				ExecutorService executor = Executors.newSingleThreadExecutor();
				Future<String> future = executor.submit(new Callable() {
				    public String call() throws Exception {
				    	execTimeout(jsonContext, methodName, parameters);
						return "OK";
				    }
				});
				timeout = timeout > 0 ? timeout : 5;
				future.get(timeout, TimeUnit.SECONDS);
			} else {
				methodInvocationVO.setErrorCode(FormConstants.ERROR_METHOD_OFF);
			}
		} catch (TimeoutException e ) {
			logger.error("Método " + methodName + " interrompido", e);
			methodInvocationVO.setErrorCode(FormConstants.ERROR_INTERRUPTED_EXCEPTION);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Erro ao executar o método " + methodName, e);
			methodInvocationVO.setErrorCode(FormConstants.ERROR_GENERIC_EXCEPTION);
		}
		
		methodInvocationVO.setTimeService((System.currentTimeMillis() - time) / 1000.0);
		logger.info(instancia + ": " + " Method: " + methodName + " Tempo: " + methodInvocationVO.getTimeService());
		
		return methodInvocationVO;
	}
	
	public MethodInvocationVO execTimeout(String jsonContext, String methodName, Map<String, String> parameters) throws Exception {
		try {
			Class<?> cls = Class.forName("br.com.ims.tool.nextform.util.MethodsCatalog");
			Method method = cls.getDeclaredMethod(methodName, String.class, Map.class);
			
			if (parameters == null) {
				parameters = new HashMap<String, String>();
			}
			
			Object obj = cls.newInstance();
			methodInvocationVO = (MethodInvocationVO) method.invoke(obj, jsonContext, parameters);

		} catch (NoSuchMethodException e) {
			logger.error("Método " + methodName + " não encontrado", e);
		} 
		return methodInvocationVO;
	}


	public MethodInvocationVO invokeExternalService(final String jsonContext, final String methodName, final Map<String, String> parameters, Integer timeout, Boolean isAtivo) {
		
		String instancia = MethodInvocationUtils.getContextValue(jsonContext, MapValues.INSTANCE);
		methodInvocationVO = MethodInvocationVO.getInstance(jsonContext, UraConstants.NULL);
		
		long time = System.currentTimeMillis();		
		final Integer paramTimeOut = timeout > 0 ? timeout : 5;
		try {
			if (isAtivo) {
				ExecutorService executor = Executors.newSingleThreadExecutor();
				Future<String> future = executor.submit(new Callable() {
				    public String call() throws Exception {
				    	execTimeoutExternalService(jsonContext, methodName, parameters,paramTimeOut);
						return "OK";
				    }
				}); 
				future.get(paramTimeOut, TimeUnit.SECONDS);
			} else {
				methodInvocationVO.setErrorCode(FormConstants.ERROR_METHOD_OFF);
			}
		} catch (TimeoutException e ) {
			logger.error("Método " + methodName + " interrompido", e);
			methodInvocationVO.setErrorCode(FormConstants.ERROR_INTERRUPTED_EXCEPTION);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Erro ao executar o método " + methodName, e);
			methodInvocationVO.setErrorCode(FormConstants.ERROR_GENERIC_EXCEPTION);
		}
		
		methodInvocationVO.setTimeService((System.currentTimeMillis() - time) / 1000.0);
		logger.info(instancia + ": " + " Method: " + methodName + " Tempo: " + methodInvocationVO.getTimeService());
		
		return methodInvocationVO;
		
	}
	
	public MethodInvocationVO execTimeoutExternalService(String jsonContext, String methodName, Map<String, String> parameters, Integer timeout) throws Exception {
		try {

	        Client client = Client.create();

	        //criar properties para buscar o endpoint correto
	        WebResource webResource = client.resource("http://localhost:7001/ims-integration-service/InvokeMethod/execute");

	        //crriando Json de entrada
	        String input = "{\"method\": \""+methodName+"\",\"timeout\": \""+timeout.toString()+"\",\"active\":\"true\",\"context\": "+jsonContext;
	        
	        if(parameters == null) {
	        	input+= "}";
	        } else {
	        	input+= ",\"parameters\": "+makeJsonParameters(parameters)+" }";
	        }
	        
	        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

	        if (response.getStatus() != 200) {
	        	logger.error("Failed : HTTP error code : "+ response.getStatus());
	            throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
	        }	        
	        
	        
	        JSONObject output = new JSONObject(response.getEntity(String.class));
	        
	        methodInvocationVO.setErrorCode(Long.valueOf(output.get("returnCode").toString()));
	        methodInvocationVO.setValue(output.get("returnValue").toString());
	        methodInvocationVO.setJsonContext(output.get("context").toString());

	    } catch (Exception e) {

	        e.printStackTrace();

	    }

	    
		
		
		return methodInvocationVO;
	}
	private String makeJsonParameters(Map<String, String> parameters) {
		String retorno = "{";
		boolean first = true;
		for(java.util.Map.Entry<String, String> entry : parameters.entrySet() ) {
			if(!first) {
				retorno+= ", ";
			} else {
				first = false;
			}
			retorno+= "\""+entry.getKey().toString()+"\": \""+entry.getValue().toString()+"\"";
		}
		retorno += "}";
		return retorno;
	}

}
