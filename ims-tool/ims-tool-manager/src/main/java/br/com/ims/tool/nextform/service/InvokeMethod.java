package br.com.ims.tool.nextform.service;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.com.ims.tool.nextform.model.MethodInvocationVO;


@WebService
public class InvokeMethod {

	@WebMethod
    @WebResult(name="methodinvocationOut")
	public MethodInvocationVO getInvokeMethod(@WebParam(name="active") Boolean active, @WebParam(name="context") String context, @WebParam(name="methodName") String methodName, @WebParam(name="timeout") Integer timeout, @WebParam(name="internalService") Boolean internalService,@WebParam(name="parameters") Map<String, String> parameters){
		
		MethodInvocation invocationService = new MethodInvocation();
		MethodInvocationVO result = null;
		if(internalService){
			result = invocationService.invoke(context, methodName, parameters, timeout, active);
		}else{
			result = invocationService.invokeExternalService(context, methodName, parameters, timeout, active);
		}
		
		return result;
	}
	
}
