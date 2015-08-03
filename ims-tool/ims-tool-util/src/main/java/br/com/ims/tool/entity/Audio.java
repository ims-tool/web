package br.com.ims.tool.entity;


import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "audio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audio.findAll", query = "SELECT a FROM Audio a"),
    @NamedQuery(name = "Audio.findById", query = "SELECT a FROM Audio a WHERE a.id = :id"),
    @NamedQuery(name = "Audio.findByType", query = "SELECT a FROM Audio a WHERE a.type = :type"),
    @NamedQuery(name = "Audio.findByName", query = "SELECT a FROM Audio a WHERE a.name = :name"),
    @NamedQuery(name = "Audio.findByDescription", query = "SELECT a FROM Audio a WHERE a.description = :description"),
    @NamedQuery(name = "Audio.findByPath", query = "SELECT a FROM Audio a WHERE a.path = :path"),
    @NamedQuery(name = "Audio.findByProperty", query = "SELECT a FROM Audio a WHERE a.property = :property"),
    @NamedQuery(name = "Audio.findByVersionid", query = "SELECT a FROM Audio a WHERE a.versionid = :versionid")})
public class Audio implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private BigDecimal id;
    @Size(max = 5)
    @Column(name = "type")
    private String type;
    @Size(max = 256)
    @Column(name = "name")
    private String name;
    @Size(max = 4000)
    @Column(name = "description")
    private String description;
    @Size(max = 128)
    @Column(name = "path")
    private String path;
    @Size(max = 512)
    @Column(name = "property")
    private String property;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "audioid")
    private Collection<Audiovar> audiovarCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "audio1")
    private Collection<Promptaudio> promptaudioCollection;

    public Audio() {
    }

    public Audio(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    @XmlTransient
    public Collection<Audiovar> getAudiovarCollection() {
        return audiovarCollection;
    }

    public void setAudiovarCollection(Collection<Audiovar> audiovarCollection) {
        this.audiovarCollection = audiovarCollection;
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
        if (!(object instanceof Audio)) {
            return false;
        }
        Audio other = (Audio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Audio[ id=" + id + " ]";
    }
    
}

