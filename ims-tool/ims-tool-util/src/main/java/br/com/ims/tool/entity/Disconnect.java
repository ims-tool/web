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
@Table(name = "disconnect")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disconnect.findAll", query = "SELECT d FROM Disconnect d"),
    @NamedQuery(name = "Disconnect.findById", query = "SELECT d FROM Disconnect d WHERE d.id = :id"),
    @NamedQuery(name = "Disconnect.findByName", query = "SELECT d FROM Disconnect d WHERE d.name = :name"),
    @NamedQuery(name = "Disconnect.findByDescription", query = "SELECT d FROM Disconnect d WHERE d.description = :description"),
    @NamedQuery(name = "Disconnect.findByTag", query = "SELECT d FROM Disconnect d WHERE d.tag = :tag"),
    @NamedQuery(name = "Disconnect.findByVersionid", query = "SELECT d FROM Disconnect d WHERE d.versionid = :versionid")})
public class Disconnect implements Serializable {
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

    public Disconnect() {
    }

    public Disconnect(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disconnect)) {
            return false;
        }
        Disconnect other = (Disconnect) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Disconnect[ id=" + id + " ]";
    }
    
}
