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

import br.com.ims.control.LogAuditCtrl;
import br.com.ims.control.ServiceHourCtrl;
import br.com.ims.tool.entity.Audit;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;

/**
 *
 * @author Cesar
 */
@Stateless
@Path("logaudit")
public class LogAuditFacadeREST extends AbstractFacade<Audit> {
    @PersistenceContext(unitName = "ivrPersistence")
    private EntityManager em;

    public LogAuditFacadeREST() {
        super(Audit.class);
    }


    @POST
    @Path("/set")
    @Consumes("application/json")
    public void saveLog(String entity) {
    	
    	
    	JSONObject jsonObj = new JSONObject(entity);
    	
    	Audit logAudit = new Audit();
    	try {
    		logAudit.setArtifact((String) jsonObj.get("artifact"));
		} catch (Exception e) {
			logAudit.setArtifact("nc");
		}
    	try {
    		logAudit.setDescription((String) jsonObj.get("description"));
		} catch (Exception e) {
			logAudit.setDescription("nc");
		}
    	try {
    		logAudit.setOriginalValue((String) jsonObj.get("originalvalue"));
		} catch (Exception e) {
			logAudit.setOriginalValue("nc");
		}
    	try {
    		logAudit.setTypeid((Integer) jsonObj.get("typeid"));
		} catch (Exception e) {
			logAudit.setTypeid(-1);
		}
    	try {
    		logAudit.setUserLogin((String) jsonObj.get("userLogin"));
		} catch (Exception e) {
			logAudit.setUserLogin("nc");
		}
    	try {
    		logAudit.setArtifactid((Integer) jsonObj.get("artifactid"));
		} catch (Exception e) {
			logAudit.setArtifactid(-1);
		}
    	try {
    		logAudit.setValueid((Integer) jsonObj.get("valueid"));
		} catch (Exception e) {
			logAudit.setValueid(0);
		}
    	LogAuditCtrl.saveLog(logAudit);
    }


	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return null;
	}

        
}
