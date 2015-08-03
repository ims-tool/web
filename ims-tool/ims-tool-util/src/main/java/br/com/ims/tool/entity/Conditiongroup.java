package br.com.ims.tool.entity;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
@Table(name = "conditiongroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conditiongroup.findAll", query = "SELECT c FROM Conditiongroup c"),
    @NamedQuery(name = "Conditiongroup.findById", query = "SELECT c FROM Conditiongroup c WHERE c.id = :id"),
    @NamedQuery(name = "Conditiongroup.findByOrdernum", query = "SELECT c FROM Conditiongroup c WHERE c.ordernum = :ordernum"),
    @NamedQuery(name = "Conditiongroup.findByConditionmapid", query = "SELECT c FROM Conditiongroup c WHERE c.conditionmapid = :conditionmapid"),
    @NamedQuery(name = "Conditiongroup.findByDescription", query = "SELECT c FROM Conditiongroup c WHERE c.description = :description"),
    @NamedQuery(name = "Conditiongroup.findByVersionid", query = "SELECT c FROM Conditiongroup c WHERE c.versionid = :versionid")})
public class Conditiongroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "ordernum")
    private Integer ordernum;
    @Column(name = "conditionmapid")
    private Integer conditionmapid;
    @Size(max = 100)
    @Column(name = "description")
    private String description;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "conditiongroupid")
    private Collection<Conditionvalue> conditionvalueCollection;
    @JoinColumn(name = "conditionid", referencedColumnName = "id")
    @ManyToOne
    private Condition conditionid;
    @OneToMany(mappedBy = "conditiongroupid")
    private Collection<Conditionparameters> conditionparametersCollection;

    public Conditiongroup() {
    }

    public Conditiongroup(Integer id) {
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

    public Integer getConditionmapid() {
        return conditionmapid;
    }

    public void setConditionmapid(Integer conditionmapid) {
        this.conditionmapid = conditionmapid;
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
    public Collection<Conditionvalue> getConditionvalueCollection() {
        return conditionvalueCollection;
    }

    public void setConditionvalueCollection(Collection<Conditionvalue> conditionvalueCollection) {
        this.conditionvalueCollection = conditionvalueCollection;
    }

    public Condition getConditionid() {
        return conditionid;
    }

    public void setConditionid(Condition conditionid) {
        this.conditionid = conditionid;
    }

    @XmlTransient
    public Collection<Conditionparameters> getConditionparametersCollection() {
        return conditionparametersCollection;
    }

    public void setConditionparametersCollection(Collection<Conditionparameters> conditionparametersCollection) {
        this.conditionparametersCollection = conditionparametersCollection;
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
        if (!(object instanceof Conditiongroup)) {
            return false;
        }
        Conditiongroup other = (Conditiongroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Conditiongroup[ id=" + id + " ]";
    }
    
}


