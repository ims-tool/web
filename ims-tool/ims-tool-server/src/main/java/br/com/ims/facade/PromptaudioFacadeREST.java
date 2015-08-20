package br.com.ims.facade;

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
import javax.ws.rs.core.PathSegment;

import br.com.ims.tool.entity.Promptaudio;
import br.com.ims.tool.entity.PromptaudioPK;

/**
 *
 * @author Cesar
 */
@Stateless
@Path("promptaudio")
public class PromptaudioFacadeREST extends AbstractFacade<Promptaudio> {
    @PersistenceContext(unitName = "ivrPersistence")
    private EntityManager em;

    private PromptaudioPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;prompt=promptValue;audio=audioValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        PromptaudioPK key = new PromptaudioPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> prompt = map.get("prompt");
        if (prompt != null && !prompt.isEmpty()) {
            key.setPrompt(new java.lang.Integer(prompt.get(0)));
        }
        java.util.List<String> audio = map.get("audio");
        if (audio != null && !audio.isEmpty()) {
            key.setAudio(new java.lang.Integer(audio.get(0)));
        }
        return key;
    }

    public PromptaudioFacadeREST() {
        super(Promptaudio.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Promptaudio entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, Promptaudio entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        PromptaudioPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Promptaudio find(@PathParam("id") PathSegment id) {
        PromptaudioPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Promptaudio> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Promptaudio> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
