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
@Path("tracktag")
public class TrackTagService {
	


	@POST
	@Produces({ "application/json" })
	@Consumes("application/json")
	public String tracktag(String entity) {
		String ret = "{\"return\":\"not ok\"}";
		
		try {
			JSONObject jsonObj = new JSONObject(entity);
			String jsonContext = jsonObj.getString("context");
			String tag = jsonObj.getString("tag");
			LogUtils.createTrackTag(LogUtils.getTrackServiceId(), Long.valueOf(MethodInvocationUtils.getContextValue(jsonContext, MapValues.TRACKID)), Long.valueOf(MethodInvocationUtils.getContextValue(jsonContext, MapValues.LOGID)), Long.valueOf(tag));

			ret = "{\"return\":\"ok\"}";
		} catch (Exception e) {
			e.printStackTrace();
			ret = "{\"return\":\""+e.getStackTrace()+"\"}";
		}

		return ret;
	}


}
