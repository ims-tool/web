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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONObject;

import br.com.ims.control.ServiceHourCtrl;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;

/**
 *
 * @author Cesar
 */
@Stateless
@Path("servicehour")
public class ServiceHourFacadeREST extends AbstractFacade<ServiceHour> {
    @PersistenceContext(unitName = "ivrPersistence")
    private EntityManager em;

    public ServiceHourFacadeREST() {
        super(ServiceHour.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ServiceHour entity) {
        super.create(entity);
    }
    
    @POST
    @Path("/update")
    @Consumes("application/json")
    public void update(String entity) {
    	
    	
    	JSONObject jsonObj = new JSONObject(entity);
    	
    	ServiceHour serviceHour = new ServiceHour();
    	serviceHour.setId((Integer) jsonObj.get("id"));
    	serviceHour.setStarthour((String) jsonObj.get("starthour"));
    	serviceHour.setStophour((String) jsonObj.get("stophour"));
    	//serviceHour.setLastlogin((String) jsonObj.get("lastlogin"));
    	ServiceHourCtrl.save(serviceHour);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
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
    @Path("find/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findServiceHour(@PathParam("type") String type) {
    	
    	List<ServiceHour> listServiceHour = ServiceHourCtrl.findAll(type);
    	
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	String json = "";
		try {
			json = ow.writeValueAsString(listServiceHour);
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
    @Path("{from}/{to}")
    @Produces("application/json")
    public List<ServiceHour> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
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
    
}
