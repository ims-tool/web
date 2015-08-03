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
@Table(name = "operationmap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operationmap.findAll", query = "SELECT o FROM Operationmap o"),
    @NamedQuery(name = "Operationmap.findById", query = "SELECT o FROM Operationmap o WHERE o.id = :id"),
    @NamedQuery(name = "Operationmap.findByName", query = "SELECT o FROM Operationmap o WHERE o.name = :name"),
    @NamedQuery(name = "Operationmap.findByDescription", query = "SELECT o FROM Operationmap o WHERE o.description = :description"),
    @NamedQuery(name = "Operationmap.findByMethodreference", query = "SELECT o FROM Operationmap o WHERE o.methodreference = :methodreference"),
    @NamedQuery(name = "Operationmap.findByVersionid", query = "SELECT o FROM Operationmap o WHERE o.versionid = :versionid"),
    @NamedQuery(name = "Operationmap.findByLogActive", query = "SELECT o FROM Operationmap o WHERE o.logActive = :logActive")})
public class Operationmap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "name")
    private String name;
    @Size(max = 2000)
    @Column(name = "description")
    private String description;
    @Size(max = 100)
    @Column(name = "methodreference")
    private String methodreference;
    @Column(name = "versionid")
    private Integer versionid;
    @Column(name = "log_active")
    private Integer logActive;
    @OneToMany(mappedBy = "operationmapid")
    private Collection<Operationgroup> operationgroupCollection;

    public Operationmap() {
    }

    public Operationmap(Integer id) {
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

    public String getMethodreference() {
        return methodreference;
    }

    public void setMethodreference(String methodreference) {
        this.methodreference = methodreference;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public Integer getLogActive() {
        return logActive;
    }

    public void setLogActive(Integer logActive) {
        this.logActive = logActive;
    }

    @XmlTransient
    public Collection<Operationgroup> getOperationgroupCollection() {
        return operationgroupCollection;
    }

    public void setOperationgroupCollection(Collection<Operationgroup> operationgroupCollection) {
        this.operationgroupCollection = operationgroupCollection;
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
        if (!(object instanceof Operationmap)) {
            return false;
        }
        Operationmap other = (Operationmap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Operationmap[ id=" + id + " ]";
    }
    
}
