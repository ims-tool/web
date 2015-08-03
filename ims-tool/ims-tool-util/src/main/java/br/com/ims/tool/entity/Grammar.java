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
@Table(name = "grammar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grammar.findAll", query = "SELECT g FROM Grammar g"),
    @NamedQuery(name = "Grammar.findById", query = "SELECT g FROM Grammar g WHERE g.id = :id"),
    @NamedQuery(name = "Grammar.findByName", query = "SELECT g FROM Grammar g WHERE g.name = :name"),
    @NamedQuery(name = "Grammar.findByDescription", query = "SELECT g FROM Grammar g WHERE g.description = :description"),
    @NamedQuery(name = "Grammar.findByType", query = "SELECT g FROM Grammar g WHERE g.type = :type"),
    @NamedQuery(name = "Grammar.findBySizemax", query = "SELECT g FROM Grammar g WHERE g.sizemax = :sizemax"),
    @NamedQuery(name = "Grammar.findBySizemin", query = "SELECT g FROM Grammar g WHERE g.sizemin = :sizemin"),
    @NamedQuery(name = "Grammar.findByVersionid", query = "SELECT g FROM Grammar g WHERE g.versionid = :versionid")})
public class Grammar implements Serializable {
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
    @Size(max = 15)
    @Column(name = "type")
    private String type;
    @Column(name = "sizemax")
    private Integer sizemax;
    @Column(name = "sizemin")
    private Integer sizemin;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "grammar")
    private Collection<Promptcollect> promptcollectCollection;

    public Grammar() {
    }

    public Grammar(Integer id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSizemax() {
        return sizemax;
    }

    public void setSizemax(Integer sizemax) {
        this.sizemax = sizemax;
    }

    public Integer getSizemin() {
        return sizemin;
    }

    public void setSizemin(Integer sizemin) {
        this.sizemin = sizemin;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    @XmlTransient
    public Collection<Promptcollect> getPromptcollectCollection() {
        return promptcollectCollection;
    }

    public void setPromptcollectCollection(Collection<Promptcollect> promptcollectCollection) {
        this.promptcollectCollection = promptcollectCollection;
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
        if (!(object instanceof Grammar)) {
            return false;
        }
        Grammar other = (Grammar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Grammar[ id=" + id + " ]";
    }
    
}
