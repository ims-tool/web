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
@Table(name = "promptcollect")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promptcollect.findAll", query = "SELECT p FROM Promptcollect p"),
    @NamedQuery(name = "Promptcollect.findById", query = "SELECT p FROM Promptcollect p WHERE p.id = :id"),
    @NamedQuery(name = "Promptcollect.findByName", query = "SELECT p FROM Promptcollect p WHERE p.name = :name"),
    @NamedQuery(name = "Promptcollect.findByDescription", query = "SELECT p FROM Promptcollect p WHERE p.description = :description"),
    @NamedQuery(name = "Promptcollect.findByFlushprompt", query = "SELECT p FROM Promptcollect p WHERE p.flushprompt = :flushprompt"),
    @NamedQuery(name = "Promptcollect.findByFetchtimeout", query = "SELECT p FROM Promptcollect p WHERE p.fetchtimeout = :fetchtimeout"),
    @NamedQuery(name = "Promptcollect.findByInterdigittimeout", query = "SELECT p FROM Promptcollect p WHERE p.interdigittimeout = :interdigittimeout"),
    @NamedQuery(name = "Promptcollect.findByTerminatingtimeout", query = "SELECT p FROM Promptcollect p WHERE p.terminatingtimeout = :terminatingtimeout"),
    @NamedQuery(name = "Promptcollect.findByTerminatingcharacter", query = "SELECT p FROM Promptcollect p WHERE p.terminatingcharacter = :terminatingcharacter"),
    @NamedQuery(name = "Promptcollect.findByTag", query = "SELECT p FROM Promptcollect p WHERE p.tag = :tag"),
    @NamedQuery(name = "Promptcollect.findByVersionid", query = "SELECT p FROM Promptcollect p WHERE p.versionid = :versionid")})
public class Promptcollect implements Serializable {
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
    @Column(name = "fetchtimeout")
    private Integer fetchtimeout;
    @Column(name = "interdigittimeout")
    private Integer interdigittimeout;
    @Column(name = "terminatingtimeout")
    private Integer terminatingtimeout;
    @Column(name = "terminatingcharacter")
    private Character terminatingcharacter;
    @Column(name = "tag")
    private Integer tag;
    @Column(name = "versionid")
    private Integer versionid;
    @JoinColumn(name = "nextform", referencedColumnName = "id")
    @ManyToOne
    private Form nextform;
    @JoinColumn(name = "grammar", referencedColumnName = "id")
    @ManyToOne
    private Grammar grammar;
    @JoinColumn(name = "noinput", referencedColumnName = "id")
    @ManyToOne
    private Nomatchinput noinput;
    @JoinColumn(name = "nomatch", referencedColumnName = "id")
    @ManyToOne
    private Nomatchinput nomatch;
    @JoinColumn(name = "prompt", referencedColumnName = "id")
    @ManyToOne
    private Prompt prompt;

    public Promptcollect() {
    }

    public Promptcollect(Integer id) {
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

    public Integer getFetchtimeout() {
        return fetchtimeout;
    }

    public void setFetchtimeout(Integer fetchtimeout) {
        this.fetchtimeout = fetchtimeout;
    }

    public Integer getInterdigittimeout() {
        return interdigittimeout;
    }

    public void setInterdigittimeout(Integer interdigittimeout) {
        this.interdigittimeout = interdigittimeout;
    }

    public Integer getTerminatingtimeout() {
        return terminatingtimeout;
    }

    public void setTerminatingtimeout(Integer terminatingtimeout) {
        this.terminatingtimeout = terminatingtimeout;
    }

    public Character getTerminatingcharacter() {
        return terminatingcharacter;
    }

    public void setTerminatingcharacter(Character terminatingcharacter) {
        this.terminatingcharacter = terminatingcharacter;
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

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    public Nomatchinput getNoinput() {
        return noinput;
    }

    public void setNoinput(Nomatchinput noinput) {
        this.noinput = noinput;
    }

    public Nomatchinput getNomatch() {
        return nomatch;
    }

    public void setNomatch(Nomatchinput nomatch) {
        this.nomatch = nomatch;
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
        if (!(object instanceof Promptcollect)) {
            return false;
        }
        Promptcollect other = (Promptcollect) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Promptcollect[ id=" + id + " ]";
    }
    
}
