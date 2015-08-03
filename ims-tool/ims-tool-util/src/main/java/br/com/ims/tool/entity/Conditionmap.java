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
@Table(name = "conditionmap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conditionmap.findAll", query = "SELECT c FROM Conditionmap c"),
    @NamedQuery(name = "Conditionmap.findById", query = "SELECT c FROM Conditionmap c WHERE c.id = :id"),
    @NamedQuery(name = "Conditionmap.findByName", query = "SELECT c FROM Conditionmap c WHERE c.name = :name"),
    @NamedQuery(name = "Conditionmap.findByDescription", query = "SELECT c FROM Conditionmap c WHERE c.description = :description"),
    @NamedQuery(name = "Conditionmap.findByType", query = "SELECT c FROM Conditionmap c WHERE c.type = :type"),
    @NamedQuery(name = "Conditionmap.findByMethodreference", query = "SELECT c FROM Conditionmap c WHERE c.methodreference = :methodreference"),
    @NamedQuery(name = "Conditionmap.findByVersionid", query = "SELECT c FROM Conditionmap c WHERE c.versionid = :versionid"),
    @NamedQuery(name = "Conditionmap.findByLogActive", query = "SELECT c FROM Conditionmap c WHERE c.logActive = :logActive")})
public class Conditionmap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id")
    private String id;
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

    public Conditionmap() {
    }

    public Conditionmap(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conditionmap)) {
            return false;
        }
        Conditionmap other = (Conditionmap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Conditionmap[ id=" + id + " ]";
    }
    
}
