package br.com.ims.tool.nextform.service;

import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.ims.tool.nextform.model.MethodInvocationVO;
import br.com.ims.tool.nextform.model.RequestInvokeMethod;


@Stateless
@Path("invokeMethod")
public class InvokeMethodImpl {

	
	@POST
	@Produces({ "application/json" })
	public MethodInvocationVO getInvokeMethod(){
		
		RequestInvokeMethod request = new RequestInvokeMethod();
		
		MethodInvocation invocationService = new MethodInvocation();
		MethodInvocationVO result = null;
		System.out.println("teste");
		if(request.getInternalService()){
			result = invocationService.invoke(request.getContext(), request.getMethodName(), request.getParameters(), request.getTimeout(), request.getActive());
		}else{
			result = invocationService.invokeExternalService(request.getContext(), request.getMethodName(), request.getParameters(), request.getTimeout(), request.getActive());
		}
		
		return result;
	}
	
}
