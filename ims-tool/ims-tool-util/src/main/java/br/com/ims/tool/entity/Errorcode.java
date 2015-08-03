package br.com.ims.tool.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cesar
 */
@Entity
@Table(name = "errorcode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Errorcode.findAll", query = "SELECT e FROM Errorcode e"),
    @NamedQuery(name = "Errorcode.findById", query = "SELECT e FROM Errorcode e WHERE e.id = :id"),
    @NamedQuery(name = "Errorcode.findByDescription", query = "SELECT e FROM Errorcode e WHERE e.description = :description"),
    @NamedQuery(name = "Errorcode.findByErrorlevel", query = "SELECT e FROM Errorcode e WHERE e.errorlevel = :errorlevel"),
    @NamedQuery(name = "Errorcode.findByVersionid", query = "SELECT e FROM Errorcode e WHERE e.versionid = :versionid")})
public class Errorcode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "description")
    private String description;
    @Column(name = "errorlevel")
    private Integer errorlevel;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "errorcodeid")
    private Collection<Trackservice> trackserviceCollection;
    @JoinColumn(name = "errortypeid", referencedColumnName = "id")
    @ManyToOne
    private Errortype errortypeid;

    public Errorcode() {
    }

    public Errorcode(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getErrorlevel() {
        return errorlevel;
    }

    public void setErrorlevel(Integer errorlevel) {
        this.errorlevel = errorlevel;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    @XmlTransient
    public Collection<Trackservice> getTrackserviceCollection() {
        return trackserviceCollection;
    }

    public void setTrackserviceCollection(Collection<Trackservice> trackserviceCollection) {
        this.trackserviceCollection = trackserviceCollection;
    }

    public Errortype getErrortypeid() {
        return errortypeid;
    }

    public void setErrortypeid(Errortype errortypeid) {
        this.errortypeid = errortypeid;
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
        if (!(object instanceof Errorcode)) {
            return false;
        }
        Errorcode other = (Errorcode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Errorcode[ id=" + id + " ]";
    }
    
}
