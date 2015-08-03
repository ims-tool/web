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
@Table(name = "operationgroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operationgroup.findAll", query = "SELECT o FROM Operationgroup o"),
    @NamedQuery(name = "Operationgroup.findById", query = "SELECT o FROM Operationgroup o WHERE o.id = :id"),
    @NamedQuery(name = "Operationgroup.findByOrdernum", query = "SELECT o FROM Operationgroup o WHERE o.ordernum = :ordernum"),
    @NamedQuery(name = "Operationgroup.findByDescription", query = "SELECT o FROM Operationgroup o WHERE o.description = :description"),
    @NamedQuery(name = "Operationgroup.findByVersionid", query = "SELECT o FROM Operationgroup o WHERE o.versionid = :versionid")})
public class Operationgroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "ordernum")
    private Integer ordernum;
    @Size(max = 100)
    @Column(name = "description")
    private String description;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "operationgroupid")
    private Collection<Operationparameters> operationparametersCollection;
    @JoinColumn(name = "operationid", referencedColumnName = "id")
    @ManyToOne
    private Operation operationid;
    @JoinColumn(name = "operationmapid", referencedColumnName = "id")
    @ManyToOne
    private Operationmap operationmapid;

    public Operationgroup() {
    }

    public Operationgroup(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
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
    public Collection<Operationparameters> getOperationparametersCollection() {
        return operationparametersCollection;
    }

    public void setOperationparametersCollection(Collection<Operationparameters> operationparametersCollection) {
        this.operationparametersCollection = operationparametersCollection;
    }

    public Operation getOperationid() {
        return operationid;
    }

    public void setOperationid(Operation operationid) {
        this.operationid = operationid;
    }

    public Operationmap getOperationmapid() {
        return operationmapid;
    }

    public void setOperationmapid(Operationmap operationmapid) {
        this.operationmapid = operationmapid;
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
        if (!(object instanceof Operationgroup)) {
            return false;
        }
        Operationgroup other = (Operationgroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Operationgroup[ id=" + id + " ]";
    }
    
}
