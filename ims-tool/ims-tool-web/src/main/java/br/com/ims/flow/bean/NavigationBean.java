package br.com.ims.flow.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 
@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {
 
    private static final long serialVersionUID = 1520318172495977648L;
 
    /**
     * Redirect to login page.
     * @return Login page name.
     */
    public String redirectToLogin() {
        return "/login.xhtml?faces-redirect=true";
    }
     
    /**
     * Go to login page.
     * @return Login page name.
     */
    public String toLogin() {
        return "/login.xhtml";
    }
     
     
    /**
     * Redirect to welcome page.
     * @return Welcome page name.
     */
    public String redirectToIndex() {
        return "/pages/index.faces";
    }
     
    /**
     * Go to welcome page.
     * @return Welcome page name.
     */
    public String toIndex() {
        return "/pages/index.faces";
    }
     
}