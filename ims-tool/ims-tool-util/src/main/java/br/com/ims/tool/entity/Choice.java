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
@Table(name = "choice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Choice.findAll", query = "SELECT c FROM Choice c"),
    @NamedQuery(name = "Choice.findById", query = "SELECT c FROM Choice c WHERE c.id = :id"),
    @NamedQuery(name = "Choice.findByName", query = "SELECT c FROM Choice c WHERE c.name = :name"),
    @NamedQuery(name = "Choice.findByMenu", query = "SELECT c FROM Choice c WHERE c.menu = :menu"),
    @NamedQuery(name = "Choice.findByDtmf", query = "SELECT c FROM Choice c WHERE c.dtmf = :dtmf"),
    @NamedQuery(name = "Choice.findByNextform", query = "SELECT c FROM Choice c WHERE c.nextform = :nextform"),
    @NamedQuery(name = "Choice.findByTag", query = "SELECT c FROM Choice c WHERE c.tag = :tag"),
    @NamedQuery(name = "Choice.findByVersionid", query = "SELECT c FROM Choice c WHERE c.versionid = :versionid")})
public class Choice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "name")
    private String name;
    @Column(name = "menu")
    private Integer menu;
    @Column(name = "dtmf")
    private Character dtmf;
    @Column(name = "nextform")
    private Integer nextform;
    @Column(name = "tag")
    private Integer tag;
    @Column(name = "versionid")
    private Integer versionid;
    @JoinColumn(name = "condition", referencedColumnName = "id")
    @ManyToOne
    private Condition condition;

    public Choice() {
    }

    public Choice(Integer id) {
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

    public Integer getMenu() {
        return menu;
    }

    public void setMenu(Integer menu) {
        this.menu = menu;
    }

    public Character getDtmf() {
        return dtmf;
    }

    public void setDtmf(Character dtmf) {
        this.dtmf = dtmf;
    }

    public Integer getNextform() {
        return nextform;
    }

    public void setNextform(Integer nextform) {
        this.nextform = nextform;
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

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
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
        if (!(object instanceof Choice)) {
            return false;
        }
        Choice other = (Choice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Choice[ id=" + id + " ]";
    }
    
}
