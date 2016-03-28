package br.com.ims.tool.nextform.service;


import java.math.BigDecimal;
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

import br.com.ims.tool.entity.Form;
import br.com.ims.tool.entity.Version;
import br.com.ims.tool.nextform.model.Response;

/**
 *
 * @author Cesar
 */
@Stateless
@Path("nextformid")
public class NextFormFacadeREST extends AbstractFacade<Form> {
    @PersistenceContext(unitName = "ivrPersistence")
    private EntityManager em;

    public NextFormFacadeREST() {
        super(Form.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Form find(@PathParam("id") BigDecimal id) {
        return super.find(id);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
