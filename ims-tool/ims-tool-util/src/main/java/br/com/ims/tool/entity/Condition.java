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
@Table(name = "condition")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Condition.findAll", query = "SELECT c FROM Condition c"),
    @NamedQuery(name = "Condition.findById", query = "SELECT c FROM Condition c WHERE c.id = :id"),
    @NamedQuery(name = "Condition.findByName", query = "SELECT c FROM Condition c WHERE c.name = :name"),
    @NamedQuery(name = "Condition.findByDescription", query = "SELECT c FROM Condition c WHERE c.description = :description"),
    @NamedQuery(name = "Condition.findByTag", query = "SELECT c FROM Condition c WHERE c.tag = :tag"),
    @NamedQuery(name = "Condition.findByVersionid", query = "SELECT c FROM Condition c WHERE c.versionid = :versionid")})
public class Condition implements Serializable {
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
    @OneToMany(mappedBy = "conditionid")
    private Collection<Conditiongroup> conditiongroupCollection;
    @OneToMany(mappedBy = "condition")
    private Collection<Promptaudio> promptaudioCollection;
    @OneToMany(mappedBy = "condition")
    private Collection<Choice> choiceCollection;

    public Condition() {
    }

    public Condition(Integer id) {
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

    @XmlTransient
    public Collection<Conditiongroup> getConditiongroupCollection() {
        return conditiongroupCollection;
    }

    public void setConditiongroupCollection(Collection<Conditiongroup> conditiongroupCollection) {
        this.conditiongroupCollection = conditiongroupCollection;
    }

    @XmlTransient
    public Collection<Promptaudio> getPromptaudioCollection() {
        return promptaudioCollection;
    }

    public void setPromptaudioCollection(Collection<Promptaudio> promptaudioCollection) {
        this.promptaudioCollection = promptaudioCollection;
    }

    @XmlTransient
    public Collection<Choice> getChoiceCollection() {
        return choiceCollection;
    }

    public void setChoiceCollection(Collection<Choice> choiceCollection) {
        this.choiceCollection = choiceCollection;
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
        if (!(object instanceof Condition)) {
            return false;
        }
        Condition other = (Condition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Condition[ id=" + id + " ]";
    }
    
}
