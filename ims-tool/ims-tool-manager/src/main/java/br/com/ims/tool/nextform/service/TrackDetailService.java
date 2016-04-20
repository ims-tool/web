package br.com.ims.tool.nextform.service;


import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import br.com.ims.tool.nextform.util.LogUtils;
import br.com.ims.tool.nextform.util.MapValues;
import br.com.ims.tool.nextform.util.MethodInvocationUtils;

@Stateless
@Path("trackdetail")
public class TrackDetailService {
	


	@POST
	@Produces({ "application/json" })
	@Consumes("application/json")
	public String trackdetail(String entity) {
		String ret = "{\"return\":\"not ok\"}";
		
		try {
			JSONObject jsonObj = new JSONObject(entity);
			String jsonContext = jsonObj.getString("context");
			String paramName = jsonObj.getString("paramName");
			String paramValue = jsonObj.getString("paramValue");
	        LogUtils.createTrackDetail(paramName,paramValue, Long.valueOf(MethodInvocationUtils.getContextValue(jsonContext, MapValues.TRACKID)), Long.valueOf(MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID)));

			ret = "{\"return\":\"ok\"}";
		} catch (Exception e) {
			e.printStackTrace();
			ret = "{\"return\":\""+e.getStackTrace()+"\"}";
		}

		return ret;
	}


}
