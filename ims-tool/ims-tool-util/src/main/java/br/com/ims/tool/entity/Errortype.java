package br.com.ims.tool.entity;


import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "errortype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Errortype.findAll", query = "SELECT e FROM Errortype e"),
    @NamedQuery(name = "Errortype.findById", query = "SELECT e FROM Errortype e WHERE e.id = :id"),
    @NamedQuery(name = "Errortype.findByDescription", query = "SELECT e FROM Errortype e WHERE e.description = :description"),
    @NamedQuery(name = "Errortype.findByVersionid", query = "SELECT e FROM Errortype e WHERE e.versionid = :versionid")})
public class Errortype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "description")
    private String description;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "errortypeid")
    private Collection<Errorcode> errorcodeCollection;

    public Errortype() {
    }

    public Errortype(Integer id) {
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

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    @XmlTransient
    public Collection<Errorcode> getErrorcodeCollection() {
        return errorcodeCollection;
    }

    public void setErrorcodeCollection(Collection<Errorcode> errorcodeCollection) {
        this.errorcodeCollection = errorcodeCollection;
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
        if (!(object instanceof Errortype)) {
            return false;
        }
        Errortype other = (Errortype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Errortype[ id=" + id + " ]";
    }
    
}
