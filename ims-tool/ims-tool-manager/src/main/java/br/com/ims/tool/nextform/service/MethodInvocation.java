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

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import br.com.ims.tool.nextform.model.MethodInvocationVO;
import br.com.ims.tool.nextform.util.FormConstants;
import br.com.ims.tool.nextform.util.MapValues;
import br.com.ims.tool.nextform.util.MethodInvocationUtils;
import br.com.ims.tool.nextform.util.UraConstants;

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
			logger.error("Erro ao executar o método " + methodName, e);
			methodInvocationVO.setErrorCode(FormConstants.ERROR_GENERIC_EXCEPTION);
		}
		
		methodInvocationVO.setTimeService((System.currentTimeMillis() - time) / 1000.0);
		logger.info(instancia + ": " + " Method: " + methodName + " Tempo: " + methodInvocationVO.getTimeService());
		
		return methodInvocationVO;
	}
	
	public MethodInvocationVO execTimeout(String jsonContext, String methodName, Map<String, String> parameters) throws Exception {
		try {
			Class<?> cls = Class.forName("br.com.gvt.ura.methodinvocationmanager.model.MethodsCatalog");
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

}
