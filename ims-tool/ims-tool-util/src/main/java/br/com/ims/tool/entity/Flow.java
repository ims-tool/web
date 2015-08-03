package br.com.ims.tool.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "flow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flow.findAll", query = "SELECT f FROM Flow f"),
    @NamedQuery(name = "Flow.findById", query = "SELECT f FROM Flow f WHERE f.id = :id"),
    @NamedQuery(name = "Flow.findByName", query = "SELECT f FROM Flow f WHERE f.name = :name"),
    @NamedQuery(name = "Flow.findByDescription", query = "SELECT f FROM Flow f WHERE f.description = :description"),
    @NamedQuery(name = "Flow.findByFlowname", query = "SELECT f FROM Flow f WHERE f.flowname = :flowname"),
    @NamedQuery(name = "Flow.findByTag", query = "SELECT f FROM Flow f WHERE f.tag = :tag"),
    @NamedQuery(name = "Flow.findByVersionid", query = "SELECT f FROM Flow f WHERE f.versionid = :versionid")})
public class Flow implements Serializable {
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
    @Size(max = 100)
    @Column(name = "flowname")
    private String flowname;
    @Column(name = "tag")
    private Integer tag;
    @Column(name = "versionid")
    private Integer versionid;
    @JoinColumn(name = "nextform", referencedColumnName = "id")
    @ManyToOne
    private Form nextform;

    public Flow() {
    }

    public Flow(Integer id) {
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

    public String getFlowname() {
        return flowname;
    }

    public void setFlowname(String flowname) {
        this.flowname = flowname;
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

    public Form getNextform() {
        return nextform;
    }

    public void setNextform(Form nextform) {
        this.nextform = nextform;
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
        if (!(object instanceof Flow)) {
            return false;
        }
        Flow other = (Flow) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Flow[ id=" + id + " ]";
    }
    
}
