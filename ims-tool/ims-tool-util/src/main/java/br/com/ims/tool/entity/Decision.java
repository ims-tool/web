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
@Table(name = "decision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Decision.findAll", query = "SELECT d FROM Decision d"),
    @NamedQuery(name = "Decision.findById", query = "SELECT d FROM Decision d WHERE d.id = :id"),
    @NamedQuery(name = "Decision.findByName", query = "SELECT d FROM Decision d WHERE d.name = :name"),
    @NamedQuery(name = "Decision.findByDescription", query = "SELECT d FROM Decision d WHERE d.description = :description"),
    @NamedQuery(name = "Decision.findByTag", query = "SELECT d FROM Decision d WHERE d.tag = :tag"),
    @NamedQuery(name = "Decision.findByVersionid", query = "SELECT d FROM Decision d WHERE d.versionid = :versionid")})
public class Decision implements Serializable {
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
    @Column(name = "tag")
    private Integer tag;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "decisionid")
    private Collection<Decisiongroup> decisiongroupCollection;

    public Decision() {
    }

    public Decision(Integer id) {
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

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
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
        if (!(object instanceof Decision)) {
            return false;
        }
        Decision other = (Decision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Decision[ id=" + id + " ]";
    }
    
}
