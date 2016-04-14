package br.com.ims.tool.operator.web.service;

import java.util.Stack;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/interaction")
public class Interaction {
	@GET
	@Path("/get/{context}/{formId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNextForm(@PathParam("context") String context,
			@PathParam("formId") long formId) {
		JSONObject newContext = null;
		JSONObject form = null;
		String nextFormId = String.valueOf(formId);
		
		if (formId == -2) {
			//Chamada Ass�ncrona
			AssyncClick2Call sC2c = null;
			try {
				newContext = new JSONObject(context);
				String destino = newContext.getString("_TransferDestino");
				if(destino.startsWith("41")){
					destino = destino.substring(2);
				}
				sC2c = new AssyncClick2Call("0" + destino, newContext.getString("_TransferVDN"), "1-xxxxx");
				sC2c.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			// getNextForm
			try {
				newContext = new JSONObject(context);

				if (formId == -1) {
					Stack<String> stack = new Stack<String>();
					if (!newContext.isNull("_FlowInternalStack")) {
						String[] list = (newContext.get("_FlowInternalStack")
								.toString().split("_"));
						for (String string : list) {
							stack.add(string);
						}
					}
					nextFormId = stack.pop();
					System.out.println("*****************: " + nextFormId);
					String ret = "i";
					for (String string : stack) {
						ret += "_" + string;
					}
					ret = ret.replace("i_", "");
					newContext.put("_FlowInternalStack", ret);
				}

				Client client = Client.create();

		        WebResource webResource = client.resource("http://vmdwin062:8080/ims-tool-manager/nextform/nextformid");

		        JSONObject object = new JSONObject();
		        object.put("context", context);
		        object.put("nextId", formId);
		        
		        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, object.toString());

		        if (response.getStatus() != 200) {
		            throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		        }

		        form = new JSONObject(response.getEntity(String.class));
		        
		        newContext = new JSONObject(form.get("jsonContexto").toString());
				
				
				while (form.get("formtype").equals(9) || 
						(form.get("formtype").equals(1) && form.getJSONObject("announce").get("nextForm").equals(-1))) {
					System.out.println("FlowInternal or Return");
					if (form.get("formtype").equals(9)) {
						Stack<String> stack = new Stack<String>();
						if (!newContext.isNull("_FlowInternalStack")) {
							String[] list = (newContext.get("_FlowInternalStack").toString().split("_"));
							for (String string : list) {
								stack.add(string);
							}
						}
						System.out.println(">>> "+ form.getJSONObject("flow").getString("nextForm"));
						stack.add(form.getJSONObject("flow").getString("nextForm"));

						String ret = "i";
						for (String string : stack) {
							ret += "_" + string;
						}
						ret = ret.replace("i_", "");
						newContext.put("_FlowInternalStack", ret);

						object = new JSONObject();
				        object.put("context", newContext.toString());
				        object.put("nextId", form.getJSONObject("flow").get("flowFirstForm").toString());
				        
				        response = webResource.type("application/json").post(ClientResponse.class, object.toString());

				        if (response.getStatus() != 200) {
				            throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
				        }

				        form = new JSONObject(response.getEntity(String.class));
				        
				        newContext = new JSONObject(form.get("jsonContexto").toString());
						
						System.out.println(">>> id=9 : " + form.toString());
						System.out.println("  Context: "+ newContext.toString());
					} else {
						Stack<String> stack = new Stack<String>();
						if (!newContext.isNull("_FlowInternalStack")) {
							String[] list = (newContext.get(
									"_FlowInternalStack").toString().split("_"));
							for (String string : list) {
								stack.add(string);
							}
						}
						nextFormId = stack.pop();
						System.out.println("*****************: " + nextFormId);
						String ret = "i";
						for (String string : stack) {
							ret += "_" + string;
						}
						ret = ret.replace("i_", "");
						newContext.put("_FlowInternalStack", ret);

						object = new JSONObject();
				        object.put("context", newContext.toString());
				        object.put("nextId", nextFormId);
				        
				        response = webResource.type("application/json").post(ClientResponse.class, object.toString());

				        if (response.getStatus() != 200) {
				            throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
				        }

				        form = new JSONObject(response.getEntity(String.class));
				        
				        newContext = new JSONObject(form.get("jsonContexto").toString());
											
						
						System.out.println(">>> id=0 : " + form.toString());
						System.out.println("  Context: "+ newContext.toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Constroi JSON de resposta
		JSONObject jInteraction = null;
		try {
			jInteraction = new JSONObject();
			jInteraction.put("context", newContext);
			jInteraction.put("form", form);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(" ");
		return jInteraction.toString();

	}

}
