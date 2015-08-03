package br.com.ims.tool.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cesar
 */
@Entity
@Table(name = "router")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Router.findAll", query = "SELECT r FROM Router r"),
    @NamedQuery(name = "Router.findByDnis", query = "SELECT r FROM Router r WHERE r.dnis = :dnis"),
    @NamedQuery(name = "Router.findByDescription", query = "SELECT r FROM Router r WHERE r.description = :description"),
    @NamedQuery(name = "Router.findByFormname", query = "SELECT r FROM Router r WHERE r.formname = :formname"),
    @NamedQuery(name = "Router.findByContext", query = "SELECT r FROM Router r WHERE r.context = :context"),
    @NamedQuery(name = "Router.findByVersionid", query = "SELECT r FROM Router r WHERE r.versionid = :versionid")})
public class Router implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dnis")
    private Integer dnis;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "formname")
    private String formname;
    @Size(max = 4000)
    @Column(name = "context")
    private String context;
    @Column(name = "versionid")
    private Integer versionid;

    public Router() {
    }

    public Router(Integer dnis) {
        this.dnis = dnis;
    }

    public Router(Integer dnis, String formname) {
        this.dnis = dnis;
        this.formname = formname;
    }

    public Integer getDnis() {
        return dnis;
    }

    public void setDnis(Integer dnis) {
        this.dnis = dnis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dnis != null ? dnis.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Router)) {
            return false;
        }
        Router other = (Router) object;
        if ((this.dnis == null && other.dnis != null) || (this.dnis != null && !this.dnis.equals(other.dnis))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Router[ dnis=" + dnis + " ]";
    }
    
}
