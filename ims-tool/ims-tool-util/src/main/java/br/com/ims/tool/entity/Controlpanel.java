package br.com.ims.tool.entity;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cesar
 */
@Entity
@Table(name = "controlpanel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Controlpanel.findAll", query = "SELECT c FROM Controlpanel c"),
    @NamedQuery(name = "Controlpanel.findById", query = "SELECT c FROM Controlpanel c WHERE c.id = :id"),
    @NamedQuery(name = "Controlpanel.findByMethodname", query = "SELECT c FROM Controlpanel c WHERE c.methodname = :methodname"),
    @NamedQuery(name = "Controlpanel.findByDescription", query = "SELECT c FROM Controlpanel c WHERE c.description = :description"),
    @NamedQuery(name = "Controlpanel.findByOwner", query = "SELECT c FROM Controlpanel c WHERE c.owner = :owner"),
    @NamedQuery(name = "Controlpanel.findByReferencedby", query = "SELECT c FROM Controlpanel c WHERE c.referencedby = :referencedby"),
    @NamedQuery(name = "Controlpanel.findByStatus", query = "SELECT c FROM Controlpanel c WHERE c.status = :status"),
    @NamedQuery(name = "Controlpanel.findByLoginid", query = "SELECT c FROM Controlpanel c WHERE c.loginid = :loginid"),
    @NamedQuery(name = "Controlpanel.findByStartdate", query = "SELECT c FROM Controlpanel c WHERE c.startdate = :startdate"),
    @NamedQuery(name = "Controlpanel.findByVersionid", query = "SELECT c FROM Controlpanel c WHERE c.versionid = :versionid"),
    @NamedQuery(name = "Controlpanel.findByTimeout", query = "SELECT c FROM Controlpanel c WHERE c.timeout = :timeout")})
public class Controlpanel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "methodname")
    private String methodname;
    @Size(max = 2000)
    @Column(name = "description")
    private String description;
    @Size(max = 100)
    @Column(name = "owner")
    private String owner;
    @Size(max = 2000)
    @Column(name = "referencedby")
    private String referencedby;
    @Size(max = 50)
    @Column(name = "status")
    private String status;
    @Size(max = 20)
    @Column(name = "loginid")
    private String loginid;
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Column(name = "versionid")
    private Integer versionid;
    @Column(name = "timeout")
    private Integer timeout;

    public Controlpanel() {
    }

    public Controlpanel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getReferencedby() {
        return referencedby;
    }

    public void setReferencedby(String referencedby) {
        this.referencedby = referencedby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Controlpanel)) {
            return false;
        }
        Controlpanel other = (Controlpanel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Controlpanel[ id=" + id + " ]";
    }
    
}
