package br.com.ims.tool.entity;


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
@Table(name = "form")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Form.findAll", query = "SELECT f FROM Form f"),
    @NamedQuery(name = "Form.findById", query = "SELECT f FROM Form f WHERE f.id = :id"),
    @NamedQuery(name = "Form.findByName", query = "SELECT f FROM Form f WHERE f.name = :name"),
    @NamedQuery(name = "Form.findByDescription", query = "SELECT f FROM Form f WHERE f.description = :description"),
    @NamedQuery(name = "Form.findByFormid", query = "SELECT f FROM Form f WHERE f.formid = :formid"),
    @NamedQuery(name = "Form.findByTag", query = "SELECT f FROM Form f WHERE f.tag = :tag"),
    @NamedQuery(name = "Form.findByCondition", query = "SELECT f FROM Form f WHERE f.condition = :condition"),
    @NamedQuery(name = "Form.findByNextformdefault", query = "SELECT f FROM Form f WHERE f.nextformdefault = :nextformdefault"),
    @NamedQuery(name = "Form.findByVersionid", query = "SELECT f FROM Form f WHERE f.versionid = :versionid")})
public class Form implements Serializable {
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
    @Column(name = "formid")
    private Integer formid;
    @Column(name = "tag")
    private Integer tag;
    @Column(name = "condition")
    private Integer condition;
    @Column(name = "nextformdefault")
    private Integer nextformdefault;
    @Column(name = "versionid")
    private Integer versionid;
    @OneToMany(mappedBy = "nextformid")
    private Collection<Decisionchance> decisionchanceCollection;
    @OneToMany(mappedBy = "nextform")
    private Collection<Flow> flowCollection;
    @OneToMany(mappedBy = "nextform")
    private Collection<Promptcollect> promptcollectCollection;
    @OneToMany(mappedBy = "nextform")
    private Collection<Announce> announceCollection;
    @JoinColumn(name = "formtype", referencedColumnName = "id")
    @ManyToOne
    private Formtype formtype;
    @OneToMany(mappedBy = "nextformid")
    private Collection<Operation> operationCollection;

    public Form() {
    }

    public Form(Integer id) {
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

    public Integer getFormid() {
        return formid;
    }

    public void setFormid(Integer formid) {
        this.formid = formid;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Integer getNextformdefault() {
        return nextformdefault;
    }

    public void setNextformdefault(Integer nextformdefault) {
        this.nextformdefault = nextformdefault;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    @XmlTransient
    public Collection<Decisionchance> getDecisionchanceCollection() {
        return decisionchanceCollection;
    }

    public void setDecisionchanceCollection(Collection<Decisionchance> decisionchanceCollection) {
        this.decisionchanceCollection = decisionchanceCollection;
    }

    @XmlTransient
    public Collection<Flow> getFlowCollection() {
        return flowCollection;
    }

    public void setFlowCollection(Collection<Flow> flowCollection) {
        this.flowCollection = flowCollection;
    }

    @XmlTransient
    public Collection<Promptcollect> getPromptcollectCollection() {
        return promptcollectCollection;
    }

    public void setPromptcollectCollection(Collection<Promptcollect> promptcollectCollection) {
        this.promptcollectCollection = promptcollectCollection;
    }

    @XmlTransient
    public Collection<Announce> getAnnounceCollection() {
        return announceCollection;
    }

    public void setAnnounceCollection(Collection<Announce> announceCollection) {
        this.announceCollection = announceCollection;
    }

    public Formtype getFormtype() {
        return formtype;
    }

    public void setFormtype(Formtype formtype) {
        this.formtype = formtype;
    }

    @XmlTransient
    public Collection<Operation> getOperationCollection() {
        return operationCollection;
    }

    public void setOperationCollection(Collection<Operation> operationCollection) {
        this.operationCollection = operationCollection;
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
        if (!(object instanceof Form)) {
            return false;
        }
        Form other = (Form) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Form[ id=" + id + " ]";
    }
    
}
