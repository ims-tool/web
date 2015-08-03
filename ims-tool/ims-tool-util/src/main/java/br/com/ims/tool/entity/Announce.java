
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
@Table(name = "announce")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Announce.findAll", query = "SELECT a FROM Announce a"),
    @NamedQuery(name = "Announce.findById", query = "SELECT a FROM Announce a WHERE a.id = :id"),
    @NamedQuery(name = "Announce.findByName", query = "SELECT a FROM Announce a WHERE a.name = :name"),
    @NamedQuery(name = "Announce.findByDescription", query = "SELECT a FROM Announce a WHERE a.description = :description"),
    @NamedQuery(name = "Announce.findByFlushprompt", query = "SELECT a FROM Announce a WHERE a.flushprompt = :flushprompt"),
    @NamedQuery(name = "Announce.findByTag", query = "SELECT a FROM Announce a WHERE a.tag = :tag"),
    @NamedQuery(name = "Announce.findByVersionid", query = "SELECT a FROM Announce a WHERE a.versionid = :versionid")})
public class Announce implements Serializable {
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
    @Column(name = "flushprompt")
    private Character flushprompt;
    @Column(name = "tag")
    private Integer tag;
    @Column(name = "versionid")
    private Integer versionid;
    @JoinColumn(name = "nextform", referencedColumnName = "id")
    @ManyToOne
    private Form nextform;
    @JoinColumn(name = "prompt", referencedColumnName = "id")
    @ManyToOne
    private Prompt prompt;

    public Announce() {
    }

    public Announce(Integer id) {
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

    public Character getFlushprompt() {
        return flushprompt;
    }

    public void setFlushprompt(Character flushprompt) {
        this.flushprompt = flushprompt;
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

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
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
        if (!(object instanceof Announce)) {
            return false;
        }
        Announce other = (Announce) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Announce[ id=" + id + " ]";
    }
    
}

