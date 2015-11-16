package br.com.ims.facade;
import java.io.IOException;
import java.util.ArrayList;
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
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONObject;

import br.com.ims.control.ParametersCtrl;
import br.com.ims.tool.entity.Parameters;
import br.com.ims.util.Parametros;

/**
 *
 * @author Cesar
 */
@Stateless
@Path("parameters")
public class ParametersFacadeREST extends AbstractFacade<Parameters> {
    @PersistenceContext(unitName = "ivrPersistence")
    private EntityManager em;

    public ParametersFacadeREST() {
        super(Parameters.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Parameters entity) {
        super.create(entity);
    }
    
    @POST
    @Path("/update")
    @Consumes("application/json")
    public void update(String entity) {
    	
    	JSONObject jsonObj = new JSONObject(entity);
    	Parameters parameter = new Parameters();
    	parameter.setId((Integer) jsonObj.get("id"));
    	parameter.setValue((String) jsonObj.get("value"));
    	ParametersCtrl.save(parameter);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Parameters entity) {
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
    	Parameters p = new Parameters();
    	p = ParametersCtrl.find(id);
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
    @Path("findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public String findParameters() {
    	
    	List<Parameters> listParameters = ParametersCtrl.findAll();
    	
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	String json = "";
		try {
			json = ow.writeValueAsString(listParameters);
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
    public List<Parameters> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
