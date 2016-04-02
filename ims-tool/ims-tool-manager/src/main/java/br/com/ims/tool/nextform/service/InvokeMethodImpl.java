package br.com.ims.tool.nextform.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.ims.tool.nextform.model.MethodInvocationVO;
import br.com.ims.tool.nextform.model.RequestInvokeMethod;


@Stateless
@Path("invokeMethod")
public class InvokeMethodImpl {

	
	/*
	 * 
	 * 
	 * {"context":"{\"call\":{\"log\":\"null;{logid:81}\",\"trackId\":\"331\",\"logId\":\"81\",\"dnis\":\"123\",\"ani\":\"4130570550\",\"ucid\":\"22222222\"},\"teste\":\"telefonia\"}"
		,"active":"true"
		,"methodName":"getContextValue"
		, "timeout":"10"
		, "internalService":"true"
		, "parameters":"param-teste"}
		
		Utilizar esse exemplo para fazer a chamado do serviço.
		Caso utilize mais de um parâmetro deve-se adicionar da seguinte forma: param-teste;instancia-instance"

	 * 
	 * */
	
	@POST
	@Produces({ "application/json" })
	public MethodInvocationVO getInvokeMethod(RequestInvokeMethod request){
		
		MethodInvocation invocationService = new MethodInvocation();
		MethodInvocationVO result = null;
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		String[] params = request.getParameters().split(";");
		
		for (String value : params) {
			String[] split = value.split("-");
			parameters.put(split[0], split[1]);
		}
			
		if(request.getInternalService()){
			result = invocationService.invoke(request.getContext(), request.getMethodName(), parameters, request.getTimeout(), request.getActive());
		}else{
			result = invocationService.invokeExternalService(request.getContext(), request.getMethodName(), parameters, request.getTimeout(), request.getActive());
		}
		
		return result;
	}
	
}
