package br.com.ims.facade;

import java.io.IOException;
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
import br.com.ims.control.ServiceHourCtrl;
import br.com.ims.tool.entity.Message;
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
@Path("message")
public class MessageFacadeREST extends AbstractFacade<ServiceHour> {
	@PersistenceContext(unitName = "ivrPersistence")
	private EntityManager em;

	public MessageFacadeREST() {
		super(ServiceHour.class);
	}

	@POST
	@Override
	@Consumes({ "application/xml", "application/json" })
	public void create(ServiceHour entity) {
		super.create(entity);
	}

	@POST
	@Path("/update")
	@Consumes("application/json")
	public void update(String entity) {

		JSONObject jsonObj = new JSONObject(entity);
		Message message = new Message();
		message.setId(jsonObj.getInt("id"));
		try {
			message.setName(jsonObj.getString("name"));
		} catch (Exception e) {
			message.setName("");
		}
		try {
			message.setDescription(jsonObj.getString("description"));
		} catch (Exception e) {
			message.setDescription("");
		}
		try {
			message.setFlag(jsonObj.getString("flag"));
		} catch (Exception e) {
			message.setFlag("I");
		}
		try {
			message.setDatai(jsonObj.getString("datai"));
		} catch (Exception e) {
			message.setDatai("010120000000");
		}
		try {
			message.setDataf(jsonObj.getString("dataf"));
		} catch (Exception e) {
			message.setDataf("010120000000");
		}
		try {
			message.setDdd_in(jsonObj.getString("ddd_in"));
		} catch (Exception e) {
			message.setDdd_in("");
		}
		try {
			message.setDdd_not_in(jsonObj.getString("ddd_not_in"));
		} catch (Exception e) {
			message.setDdd_not_in("");
		}
		try {
			message.setSpot(jsonObj.getString("spot"));
		} catch (Exception e) {
			message.setSpot("");
		}
		try {
			Integer i = jsonObj.getInt("msg_order");
			message.setMsg_order(i.toString());
		} catch (Exception e) {
			message.setMsg_order("999");
		}

		MessageCtrl.save(message);
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
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String find(@PathParam("id") Integer id) {
		ServiceHour p = new ServiceHour();
		p = ServiceHourCtrl.find(id);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(p);
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
	@Path("findType/{user}")
	@Produces("application/json")
	public String findType(@PathParam("user") String user) {
		List<ServiceHourType> lista = ServiceHourCtrl.findType(user);
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
	@Path("findAll")
	@Produces("application/json")
	public String findListMessage() {
		List<Message> lista = MessageCtrl.findAll();
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
	@Path("findSpot")
	@Produces("application/json")
	public String findSpotList() {
		List<String> lista = MessageCtrl.findSpotList();
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

	@GET
	@Path("/nextidMessage")
	@Produces("application/json")
	public String getNextidMessage() {
		String lista = MessageCtrl.getNexIdMessage();
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
	@Path("findSpotList")
	@Produces("application/json")
	public String getSpot() {
		List<String> lista = MessageCtrl.findSpotList();
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

	@POST
	@Path("/remove")
	@Consumes("application/json")
	public void remove(String entity) {

		JSONObject jsonObj = new JSONObject(entity);
		Message message = new Message();
		message.setId(jsonObj.getInt("id"));

		MessageCtrl.remove(message.getId());
	}

}
