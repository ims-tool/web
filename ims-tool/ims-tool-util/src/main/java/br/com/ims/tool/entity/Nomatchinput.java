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
@Table(name = "nomatchinput")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nomatchinput.findAll", query = "SELECT n FROM Nomatchinput n"),
    @NamedQuery(name = "Nomatchinput.findById", query = "SELECT n FROM Nomatchinput n WHERE n.id = :id"),
    @NamedQuery(name = "Nomatchinput.findByType", query = "SELECT n FROM Nomatchinput n WHERE n.type = :type"),
    @NamedQuery(name = "Nomatchinput.findByThreshold", query = "SELECT n FROM Nomatchinput n WHERE n.threshold = :threshold"),
    @NamedQuery(name = "Nomatchinput.findByPrompt", query = "SELECT n FROM Nomatchinput n WHERE n.prompt = :prompt"),
    @NamedQuery(name = "Nomatchinput.findByNextform", query = "SELECT n FROM Nomatchinput n WHERE n.nextform = :nextform"),
    @NamedQuery(name = "Nomatchinput.findByName", query = "SELECT n FROM Nomatchinput n WHERE n.name = :name"),
    @NamedQuery(name = "Nomatchinput.findByTag", query = "SELECT n FROM Nomatchinput n WHERE n.tag = :tag"),
    @NamedQuery(name = "Nomatchinput.findByVersionid", query = "SELECT n FROM Nomatchinput n WHERE n.versionid = :versionid")})
public class Nomatchinput implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 7)
    @Column(name = "type")
    private String type;
    @Column(name = "threshold")
    private Integer threshold;
    @Column(name = "prompt")
    private Integer prompt;
    @Column(name = "nextform")
    private Integer nextform;
    @Size(max = 30)
    @Column(name = "name")
    private String name;
    @Column(name = "tag")
    private Integer tag;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "noinput")
    private Collection<Promptcollect> promptcollectCollection;
    @OneToMany(mappedBy = "nomatch")
    private Collection<Promptcollect> promptcollectCollection1;
    @OneToMany(mappedBy = "noinput")
    private Collection<Menu> menuCollection;
    @OneToMany(mappedBy = "nomatch")
    private Collection<Menu> menuCollection1;

    public Nomatchinput() {
    }

    public Nomatchinput(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getPrompt() {
        return prompt;
    }

    public void setPrompt(Integer prompt) {
        this.prompt = prompt;
    }

    public Integer getNextform() {
        return nextform;
    }

    public void setNextform(Integer nextform) {
        this.nextform = nextform;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @XmlTransient
    public Collection<Promptcollect> getPromptcollectCollection() {
        return promptcollectCollection;
    }

    public void setPromptcollectCollection(Collection<Promptcollect> promptcollectCollection) {
        this.promptcollectCollection = promptcollectCollection;
    }

    @XmlTransient
    public Collection<Promptcollect> getPromptcollectCollection1() {
        return promptcollectCollection1;
    }

    public void setPromptcollectCollection1(Collection<Promptcollect> promptcollectCollection1) {
        this.promptcollectCollection1 = promptcollectCollection1;
    }

    @XmlTransient
    public Collection<Menu> getMenuCollection() {
        return menuCollection;
    }

    public void setMenuCollection(Collection<Menu> menuCollection) {
        this.menuCollection = menuCollection;
    }

    @XmlTransient
    public Collection<Menu> getMenuCollection1() {
        return menuCollection1;
    }

    public void setMenuCollection1(Collection<Menu> menuCollection1) {
        this.menuCollection1 = menuCollection1;
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
        if (!(object instanceof Nomatchinput)) {
            return false;
        }
        Nomatchinput other = (Nomatchinput) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Nomatchinput[ id=" + id + " ]";
    }
    
}
