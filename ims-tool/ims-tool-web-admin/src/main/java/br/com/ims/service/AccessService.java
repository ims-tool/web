package br.com.ims.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.ims.dao.AccessDao;
import br.com.ims.exception.DaoException;
import br.com.ims.model.User;



@Path("/Access")
public class AccessService {

	  public User login(String userLogin,String password) {
		  AccessDao dao = new AccessDao();  
		  User user = null;
		  try {
				user = dao.getUser(userLogin);
				if(user != null) {
					  if(dao.isInternalUser()) {
						  if(user.getPassword().equals(password)) {
							  return user;
						  } else {
							  return null;
						  }
					  }
				}
		  } catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
		  
		  return user;		  
	  }
	
	  @Path("/login")	  
	  @POST
	  @Produces("application/json")	  
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response login(String object) throws JSONException {

		  /**
		   * initializing variables
		   */
		  
		  JSONObject objectIn = new JSONObject(object);
		  
		  JSONObject objectOut = new JSONObject();
		  
		  objectOut.put("result", "false");
		  
		  if(objectIn.has("login")) {
			  String userLogin = objectOut.getString("login"); 
			  String pass = null;
			  if(objectIn.has("password")) {				  
				  pass = objectOut.getString("password"); 
			  }
			  User user = login(userLogin,pass);
			  if(user != null) {
				  objectOut.put("result", "true");
				  objectOut.put("user", user);
			  }
			  
		  }
		  
		  return Response.status(200).entity(objectOut.toString()).build();
		
		
		
	  }
	  

	 
	  
	  	

}
