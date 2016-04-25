package br.com.ims.flow.bean;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.primefaces.context.RequestContext;

import br.com.ims.flow.common.Constants;
 
 
/**
 * 
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
 
    private static final long serialVersionUID = 7765876811740798583L;
    public static Logger log = Logger.getLogger(LoginBean.class);
 
    private String username;
    private String password;
     
    private boolean loggedIn;
 
    @ManagedProperty(value="#{navigationBean}")
    private NavigationBean navigationBean;
     
    /**
     * Login operation.
     * @return
     */
    public String doLogin() {
        // Get every user from our sample database :)
    	try {
	    	JSONObject json = new JSONObject();
			json.put("login", username);
			json.put("password",password);
			json.put("system",Constants.SYSTEM_ID);
	
			FacesContext ctxt = FacesContext.getCurrentInstance();
		    ExternalContext ext = ctxt.getExternalContext();
			
			String aux = "http://"+ext.getRequestServerName()+":"+ext.getRequestServerPort()+"/ims-tool-server/rest/access/login";
			URL url = new URL(aux);
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);
			
			byte[] out = json.toString().getBytes();
			int length = out.length;
	
			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(out);
			os.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			in.close();
			
			json = new JSONObject(result.toString());
			if(json.getString("result").equals("OK")) {
			    loggedIn = true;
	            RequestContext context = RequestContext.getCurrentInstance();
	            context.addCallbackParam("loggedIn", loggedIn);
	            return navigationBean.redirectToIndex();
			}
             
			
    	}catch(Exception e) {
    		e.printStackTrace();
			log.error(e.getMessage(),e);
    	}
    	// Set login ERROR
        FacesMessage msg = new FacesMessage("Login error!", "IMS TOOLS");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
         
        // To to login page
        return navigationBean.toLogin();
         
    }
     
    /**
     * Logout operation.
     * @return
     */
    public String doLogout() {
        // Set the paremeter indicating that user is logged in to false
        loggedIn = false;
         
        return navigationBean.toLogin();
    }
 
    // ------------------------------
    // Getters & Setters
     
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public boolean isLoggedIn() {
        return loggedIn;
    }
 
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
 
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }
     
}