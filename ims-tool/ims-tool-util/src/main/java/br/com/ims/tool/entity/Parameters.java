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
@Table(name = "parameters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parameters.findAll", query = "SELECT p FROM Parameters p"),
    @NamedQuery(name = "Parameters.findById", query = "SELECT p FROM Parameters p WHERE p.id = :id"),
    @NamedQuery(name = "Parameters.findByName", query = "SELECT p FROM Parameters p WHERE p.name = :name"),
    @NamedQuery(name = "Parameters.findByDescription", query = "SELECT p FROM Parameters p WHERE p.description = :description"),
    @NamedQuery(name = "Parameters.findByType", query = "SELECT p FROM Parameters p WHERE p.type = :type"),
    @NamedQuery(name = "Parameters.findByValue", query = "SELECT p FROM Parameters p WHERE p.value = :value"),
    @NamedQuery(name = "Parameters.findByLoginid", query = "SELECT p FROM Parameters p WHERE p.loginid = :loginid"),
    @NamedQuery(name = "Parameters.findByStartdate", query = "SELECT p FROM Parameters p WHERE p.startdate = :startdate"),
    @NamedQuery(name = "Parameters.findByOwner", query = "SELECT p FROM Parameters p WHERE p.owner = :owner"),
    @NamedQuery(name = "Parameters.findByVersionid", query = "SELECT p FROM Parameters p WHERE p.versionid = :versionid")})
public class Parameters implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "name")
    private String name;
    @Size(max = 256)
    @Column(name = "description")
    private String description;
    @Size(max = 10)
    @Column(name = "type")
    private String type;
    @Size(max = 50)
    @Column(name = "value")
    private String value;
    @Size(max = 20)
    @Column(name = "loginid")
    private String loginid;
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Size(max = 15)
    @Column(name = "owner")
    private String owner;
    @Column(name = "versionid")
    private Integer versionid;

    public Parameters() {
    }

    public Parameters(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parameters)) {
            return false;
        }
        Parameters other = (Parameters) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Parameters[ id=" + id + " ]";
    }
    
}
