package br.com.ims.facade;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONObject;

import br.com.ims.control.MessageCtrl;
import br.com.ims.control.ReportCtrl;
import br.com.ims.control.ServiceHourCtrl;
import br.com.ims.tool.entity.CallLog;
import br.com.ims.tool.entity.Controlpanel;
import br.com.ims.tool.entity.Message;
import br.com.ims.tool.entity.ReportLog;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.core.Response;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 *
 * @author Cesar
 */
@Stateless
@Path("report")
public class ReportFacadeREST extends AbstractFacade<ServiceHour> {
	@PersistenceContext(unitName = "ivrPersistence")
	private EntityManager em;

	public ReportFacadeREST() {
		super(ServiceHour.class);
	}

	@POST
	@Override
	@Consumes({ "application/xml", "application/json" })
	public void create(ServiceHour entity) {
		super.create(entity);
	}

	@POST
	@Path("/updateControlPanel")
	@Consumes("application/json")
	public void update(String entity) {

		JSONObject jsonObj = new JSONObject(entity);
		Controlpanel cp = new Controlpanel();
		cp.setId(jsonObj.getInt("id"));
		cp.setLoginid(jsonObj.getString("loginid"));
		cp.setStatus(jsonObj.getString("status"));
		cp.setTimeout(jsonObj.getInt("timeout"));
		
		ReportCtrl.save(cp);
	}

	@PUT
	@Path("{id}")
	@Consumes({ "application/xml", "application/json" })
	public void edit(@PathParam("id") Integer id, ServiceHour entity) {
		super.edit(entity);
	}

	@DELETE
	@Path("{id}")
	public void remove(@PathParam("id") Integer id) {
		super.remove(super.find(id));
	}

	

	@GET
	@Path("getTypeControlPanel")
	@Produces("application/json")
	public String getTypeControlPanel() {
		List<String> lista = ReportCtrl.getTypeControlPanel();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(lista);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;

	}

	@GET
	@Path("getControlPanelList/{group}")
	@Produces("application/json")
	public String getControlPanelList(@PathParam("group") String group) {
		
		List<Controlpanel> lista = ReportCtrl.getControlPanelList(group);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(lista);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;

	}


	@GET
	@Path("{from}/{to}")
	@Produces("application/json")
	public List<ServiceHour> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
		return super.findRange(new int[] { from, to });
	}

	@GET
	@Path("count")
	@Produces("text/plain")
	public String countREST() {
		return String.valueOf(super.count());
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}



	@POST
	@Path("/remove")
	@Consumes("application/json")
	public void remove(String entity) {

		JSONObject jsonObj = new JSONObject(entity);
		Message message = new Message();
		message.setId(jsonObj.getInt("id"));

		MessageCtrl.remove(message.getId());
	}
	
	@GET
	@Path("getArtifactList/{data}")
	@Produces("application/json")
	public String getArtifactList(@PathParam("data") Date dateLog) {
		List<String> lista = ReportCtrl.getArtifactList(dateLog);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(lista);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@GET
	@Path("getLogList/{data}/{artifact}")
	@Produces("application/json")
	public String getLogList(@PathParam("data") Date dateLog, @PathParam("artifact") String artifact) {
		
		List<ReportLog> lista = ReportCtrl.getLogList(dateLog, artifact);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(lista);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@GET
	@Path("getCallLogList/{datei}/{datef}/{ani}/{dnis}")
	@Produces("application/json")
	public String getCallLogList(@PathParam("datei") String datei,@PathParam("datef") String datef, @PathParam("ani") String ani, @PathParam("dnis") String dnis) {
		
		List<CallLog> lista = ReportCtrl.getCallLogList(datei, datef, ani, dnis);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(lista);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	

}
