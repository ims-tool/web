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
@Table(name = "decisiongroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Decisiongroup.findAll", query = "SELECT d FROM Decisiongroup d"),
    @NamedQuery(name = "Decisiongroup.findById", query = "SELECT d FROM Decisiongroup d WHERE d.id = :id"),
    @NamedQuery(name = "Decisiongroup.findByOrdernum", query = "SELECT d FROM Decisiongroup d WHERE d.ordernum = :ordernum"),
    @NamedQuery(name = "Decisiongroup.findByDescription", query = "SELECT d FROM Decisiongroup d WHERE d.description = :description"),
    @NamedQuery(name = "Decisiongroup.findByVersionid", query = "SELECT d FROM Decisiongroup d WHERE d.versionid = :versionid")})
public class Decisiongroup implements Serializable {
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
    @OneToMany(mappedBy = "decisiongroupid")
    private Collection<Decisionchance> decisionchanceCollection;
    @OneToMany(mappedBy = "decisiongroupchild")
    private Collection<Decisionchance> decisionchanceCollection1;
    @OneToMany(mappedBy = "decisiongroupid")
    private Collection<Decisionparameters> decisionparametersCollection;
    @JoinColumn(name = "decisionid", referencedColumnName = "id")
    @ManyToOne
    private Decision decisionid;
    @JoinColumn(name = "decisionmapid", referencedColumnName = "id")
    @ManyToOne
    private Decisionmap decisionmapid;

    public Decisiongroup() {
    }

    public Decisiongroup(Integer id) {
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
    public Collection<Decisionchance> getDecisionchanceCollection() {
        return decisionchanceCollection;
    }

    public void setDecisionchanceCollection(Collection<Decisionchance> decisionchanceCollection) {
        this.decisionchanceCollection = decisionchanceCollection;
    }

    @XmlTransient
    public Collection<Decisionchance> getDecisionchanceCollection1() {
        return decisionchanceCollection1;
    }

    public void setDecisionchanceCollection1(Collection<Decisionchance> decisionchanceCollection1) {
        this.decisionchanceCollection1 = decisionchanceCollection1;
    }

    @XmlTransient
    public Collection<Decisionparameters> getDecisionparametersCollection() {
        return decisionparametersCollection;
    }

    public void setDecisionparametersCollection(Collection<Decisionparameters> decisionparametersCollection) {
        this.decisionparametersCollection = decisionparametersCollection;
    }

    public Decision getDecisionid() {
        return decisionid;
    }

    public void setDecisionid(Decision decisionid) {
        this.decisionid = decisionid;
    }

    public Decisionmap getDecisionmapid() {
        return decisionmapid;
    }

    public void setDecisionmapid(Decisionmap decisionmapid) {
        this.decisionmapid = decisionmapid;
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
        if (!(object instanceof Decisiongroup)) {
            return false;
        }
        Decisiongroup other = (Decisiongroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Decisiongroup[ id=" + id + " ]";
    }
    
}
