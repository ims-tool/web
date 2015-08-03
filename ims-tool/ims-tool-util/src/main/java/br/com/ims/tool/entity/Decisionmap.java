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
@Table(name = "decisionmap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Decisionmap.findAll", query = "SELECT d FROM Decisionmap d"),
    @NamedQuery(name = "Decisionmap.findById", query = "SELECT d FROM Decisionmap d WHERE d.id = :id"),
    @NamedQuery(name = "Decisionmap.findByName", query = "SELECT d FROM Decisionmap d WHERE d.name = :name"),
    @NamedQuery(name = "Decisionmap.findByDescription", query = "SELECT d FROM Decisionmap d WHERE d.description = :description"),
    @NamedQuery(name = "Decisionmap.findByType", query = "SELECT d FROM Decisionmap d WHERE d.type = :type"),
    @NamedQuery(name = "Decisionmap.findByMethodreference", query = "SELECT d FROM Decisionmap d WHERE d.methodreference = :methodreference"),
    @NamedQuery(name = "Decisionmap.findByVersionid", query = "SELECT d FROM Decisionmap d WHERE d.versionid = :versionid"),
    @NamedQuery(name = "Decisionmap.findByLogActive", query = "SELECT d FROM Decisionmap d WHERE d.logActive = :logActive")})
public class Decisionmap implements Serializable {
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
    @Size(max = 10)
    @Column(name = "type")
    private String type;
    @Size(max = 100)
    @Column(name = "methodreference")
    private String methodreference;
    @Column(name = "versionid")
    private Integer versionid;
    @Column(name = "log_active")
    private Integer logActive;
    @OneToMany(mappedBy = "decisionmapid")
    private Collection<Decisiongroup> decisiongroupCollection;

    public Decisionmap() {
    }

    public Decisionmap(Integer id) {
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
    public Collection<Decisiongroup> getDecisiongroupCollection() {
        return decisiongroupCollection;
    }

    public void setDecisiongroupCollection(Collection<Decisiongroup> decisiongroupCollection) {
        this.decisiongroupCollection = decisiongroupCollection;
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
        if (!(object instanceof Decisionmap)) {
            return false;
        }
        Decisionmap other = (Decisionmap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Decisionmap[ id=" + id + " ]";
    }
    
}
