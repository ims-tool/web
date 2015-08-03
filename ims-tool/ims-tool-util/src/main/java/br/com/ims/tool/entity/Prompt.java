package br.com.ims.tool.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "prompt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prompt.findAll", query = "SELECT p FROM Prompt p"),
    @NamedQuery(name = "Prompt.findById", query = "SELECT p FROM Prompt p WHERE p.id = :id"),
    @NamedQuery(name = "Prompt.findByName", query = "SELECT p FROM Prompt p WHERE p.name = :name"),
    @NamedQuery(name = "Prompt.findByVersionid", query = "SELECT p FROM Prompt p WHERE p.versionid = :versionid")})
public class Prompt implements Serializable {
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
    @OneToMany(mappedBy = "prompt")
    private Collection<Promptcollect> promptcollectCollection;
    @OneToMany(mappedBy = "prompt")
    private Collection<Menu> menuCollection;
    @OneToMany(mappedBy = "prompt")
    private Collection<Announce> announceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prompt1")
    private Collection<Promptaudio> promptaudioCollection;

    public Prompt() {
    }

    public Prompt(Integer id) {
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
    public Collection<Promptcollect> getPromptcollectCollection() {
        return promptcollectCollection;
    }

    public void setPromptcollectCollection(Collection<Promptcollect> promptcollectCollection) {
        this.promptcollectCollection = promptcollectCollection;
    }

    @XmlTransient
    public Collection<Menu> getMenuCollection() {
        return menuCollection;
    }

    public void setMenuCollection(Collection<Menu> menuCollection) {
        this.menuCollection = menuCollection;
    }

    @XmlTransient
    public Collection<Announce> getAnnounceCollection() {
        return announceCollection;
    }

    public void setAnnounceCollection(Collection<Announce> announceCollection) {
        this.announceCollection = announceCollection;
    }

    @XmlTransient
    public Collection<Promptaudio> getPromptaudioCollection() {
        return promptaudioCollection;
    }

    public void setPromptaudioCollection(Collection<Promptaudio> promptaudioCollection) {
        this.promptaudioCollection = promptaudioCollection;
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
        if (!(object instanceof Prompt)) {
            return false;
        }
        Prompt other = (Prompt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Prompt[ id=" + id + " ]";
    }
    
}
