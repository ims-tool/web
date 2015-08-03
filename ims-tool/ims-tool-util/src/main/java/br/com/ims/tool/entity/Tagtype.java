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
@Table(name = "tagtype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tagtype.findAll", query = "SELECT t FROM Tagtype t"),
    @NamedQuery(name = "Tagtype.findById", query = "SELECT t FROM Tagtype t WHERE t.id = :id"),
    @NamedQuery(name = "Tagtype.findByName", query = "SELECT t FROM Tagtype t WHERE t.name = :name"),
    @NamedQuery(name = "Tagtype.findByDescription", query = "SELECT t FROM Tagtype t WHERE t.description = :description"),
    @NamedQuery(name = "Tagtype.findByVersionid", query = "SELECT t FROM Tagtype t WHERE t.versionid = :versionid")})
public class Tagtype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "name")
    private String name;
    @Size(max = 1000)
    @Column(name = "description")
    private String description;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "tagtypeid")
    private Collection<Tag> tagCollection;

    public Tagtype() {
    }

    public Tagtype(Integer id) {
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

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    @XmlTransient
    public Collection<Tag> getTagCollection() {
        return tagCollection;
    }

    public void setTagCollection(Collection<Tag> tagCollection) {
        this.tagCollection = tagCollection;
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
        if (!(object instanceof Tagtype)) {
            return false;
        }
        Tagtype other = (Tagtype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Tagtype[ id=" + id + " ]";
    }
    
}

