package br.com.ims.facade;
import java.io.IOException;
import java.util.Calendar;
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
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.ims.control.ServiceHourCtrl;
import br.com.ims.control.UserControlCtrl;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;
import br.com.ims.tool.entity.User;
import br.com.ims.tool.exception.DaoException;

/**
 *
 * @author Cesar
 */
@Stateless
@Path("access")
public class UserControlFacadeREST extends AbstractFacade<ServiceHour> {
    @PersistenceContext(unitName = "ivrPersistence")
    private EntityManager em;

    public UserControlFacadeREST() {
        super(ServiceHour.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ServiceHour entity) {
        super.create(entity);
    }
    
    
    @POST
    @Path("/remove")
    @Consumes("application/json")
    public void remove(String entity) {

    	System.out.println(entity);
    	JSONObject jsonObj = new JSONObject(entity);
    	
    	User user = new User();
    		
    		user.setId((Integer) jsonObj.get("id"));

    	user.setName((String) jsonObj.get("name"));
    	user.setEmail((String) jsonObj.get("email"));
    	user.setLogin((String) jsonObj.get("login"));
    	
    	
    	UserControlCtrl.remove(user);
    	
    }
    
    @POST
    @Path("/update")
    @Consumes("application/json")
    public void update(String entity) {
    	
    	
    	JSONObject jsonObj = new JSONObject(entity);
    	
    	User user = new User();
    	if(StringUtils.isNotBlank((String)jsonObj.get("id"))){
    		user.setId((Integer) jsonObj.get("id"));
    	}else{
    		user.setId(-1);
    	}
    	user.setName((String) jsonObj.get("name"));
    	user.setEmail((String) jsonObj.get("email"));
    	user.setLogin((String) jsonObj.get("login"));
    	user.setPassword((String) jsonObj.get("pw1"));
    	
    	
    	UserControlCtrl.save(user);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, ServiceHour entity) {
        super.edit(entity);
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
    @Path("findType")
    @Produces("application/json")
    public String findType() {
    	
    	List<ServiceHourType> lista = ServiceHourCtrl.findType();
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
    @Path("findAllUser")
    @Produces("application/json")
    public String findAllUser() {
    	
    	List<User> lista = UserControlCtrl.findAll();
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
    
    
    
 
	
	  @Path("/login")	  
	  @POST
	  @Produces("application/json")	  
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response getSystemAccess(String object) throws JSONException {

		  /**
		   * initializing variables
		   */
		  
		  JSONObject objectIn = new JSONObject(object);
		  JSONObject objectOut = new JSONObject();
		  String response = "";
		  
		  
		  if(objectIn.has("login")) {
			  String userLogin = objectIn.getString("login"); 
			  String pass = null;
			  String system = null;
			  if(objectIn.has("password")) {				  
				  pass = objectIn.getString("password"); 
			  }
			  if(objectIn.has("system")){
				  system = objectIn.getString("system");
				  try {
					response =  UserControlCtrl.getSystemAccess(userLogin, pass, system);
					if(response.equalsIgnoreCase("OK")){
						objectOut = UserControlCtrl.getArtifactBySystem(userLogin, system);
					}
				} catch (DaoException e) {
					e.printStackTrace();
				}
			  }
		  }
		  objectOut.put("result", response);
		  
		  return Response.status(201).entity(objectOut.toString()).build();
		
	  }
	  
    
}
