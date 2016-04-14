package br.com.ims.facade;

import java.text.SimpleDateFormat;
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

import org.json.JSONObject;

import br.com.ims.control.MessageCtrl;
import br.com.ims.control.RouterCtrl;
import br.com.ims.tool.entity.Message;
import br.com.ims.tool.entity.Router;
import br.com.ims.tool.entity.RouterIvr;

/**
 *
 * @author Cesar
 */
@Stateless
@Path("router")
public class RouterFacadeREST extends AbstractFacade<Router> {
    @PersistenceContext(unitName = "ivrPersistence")
    private EntityManager em;

    public RouterFacadeREST() {
        super(Router.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Router entity) {
        super.create(entity);
    }
    
    
    @POST
    @Path("/getIvr")
    @Consumes("application/json")
    @Produces("application/json")
    public RouterIvr getIvr(String entity) {
    	
    	JSONObject jsonObj = new JSONObject(entity);
    	String ani =  jsonObj.getJSONObject("call").getString("ani");
    	String ucid = jsonObj.getJSONObject("call").getString("ucid");
    	String dnis = jsonObj.getJSONObject("call").getString("dnis");
    	String uui = jsonObj.getJSONObject("call").getString("uui");
    	RouterIvr router = new RouterIvr();
    	
    	router = RouterCtrl.getIvr(dnis);
    	try {
    		router.setContext(router.getContext().replace("<UCID>", ucid));
		} catch (Exception e) {
		}
    	try {
    		router.setContext(router.getContext().replace("<ANI>", ani));
		} catch (Exception e) {
		}
    	try {
    		router.setContext(router.getContext().replace("<UUI>", uui));
		} catch (Exception e) {
		}
    	try {
    		router.setContext(router.getContext().replace("<DNIS>", dnis));
		} catch (Exception e) {
		}
    	try {
    		router.setContext(router.getContext().replace("<STARTDATE>", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
		} catch (Exception e) {
		}
    	
		return router;
    }
    

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Router entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Router find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Router> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Router> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
