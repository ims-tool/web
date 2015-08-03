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
@Table(name = "formtype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formtype.findAll", query = "SELECT f FROM Formtype f"),
    @NamedQuery(name = "Formtype.findById", query = "SELECT f FROM Formtype f WHERE f.id = :id"),
    @NamedQuery(name = "Formtype.findByName", query = "SELECT f FROM Formtype f WHERE f.name = :name"),
    @NamedQuery(name = "Formtype.findByVersionid", query = "SELECT f FROM Formtype f WHERE f.versionid = :versionid")})
public class Formtype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "formtype")
    private Collection<Form> formCollection;

    public Formtype() {
    }

    public Formtype(Integer id) {
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

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    @XmlTransient
    public Collection<Form> getFormCollection() {
        return formCollection;
    }

    public void setFormCollection(Collection<Form> formCollection) {
        this.formCollection = formCollection;
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
        if (!(object instanceof Formtype)) {
            return false;
        }
        Formtype other = (Formtype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Formtype[ id=" + id + " ]";
    }
    
}
