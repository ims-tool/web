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
@Table(name = "operation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operation.findAll", query = "SELECT o FROM Operation o"),
    @NamedQuery(name = "Operation.findById", query = "SELECT o FROM Operation o WHERE o.id = :id"),
    @NamedQuery(name = "Operation.findByName", query = "SELECT o FROM Operation o WHERE o.name = :name"),
    @NamedQuery(name = "Operation.findByDescription", query = "SELECT o FROM Operation o WHERE o.description = :description"),
    @NamedQuery(name = "Operation.findByTag", query = "SELECT o FROM Operation o WHERE o.tag = :tag"),
    @NamedQuery(name = "Operation.findByVersionid", query = "SELECT o FROM Operation o WHERE o.versionid = :versionid")})
public class Operation implements Serializable {
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
    @JoinColumn(name = "nextformid", referencedColumnName = "id")
    @ManyToOne
    private Form nextformid;
    @OneToMany(mappedBy = "operationid")
    private Collection<Operationgroup> operationgroupCollection;

    public Operation() {
    }

    public Operation(Integer id) {
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

    public Form getNextformid() {
        return nextformid;
    }

    public void setNextformid(Form nextformid) {
        this.nextformid = nextformid;
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
        if (!(object instanceof Operation)) {
            return false;
        }
        Operation other = (Operation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Operation[ id=" + id + " ]";
    }
    
}
