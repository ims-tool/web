package br.com.ims.tool.entity;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cesar
 */
@Entity
@Table(name = "log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l"),
    @NamedQuery(name = "Log.findById", query = "SELECT l FROM Log l WHERE l.id = :id"),
    @NamedQuery(name = "Log.findByStartdate", query = "SELECT l FROM Log l WHERE l.startdate = :startdate"),
    @NamedQuery(name = "Log.findByStopdate", query = "SELECT l FROM Log l WHERE l.stopdate = :stopdate"),
    @NamedQuery(name = "Log.findByUcid", query = "SELECT l FROM Log l WHERE l.ucid = :ucid"),
    @NamedQuery(name = "Log.findByDnis", query = "SELECT l FROM Log l WHERE l.dnis = :dnis"),
    @NamedQuery(name = "Log.findByAni", query = "SELECT l FROM Log l WHERE l.ani = :ani"),
    @NamedQuery(name = "Log.findByInstance", query = "SELECT l FROM Log l WHERE l.instance = :instance"),
    @NamedQuery(name = "Log.findByDocument", query = "SELECT l FROM Log l WHERE l.document = :document"),
    @NamedQuery(name = "Log.findByProtocolinteger", query = "SELECT l FROM Log l WHERE l.protocolinteger = :protocolinteger"),
    @NamedQuery(name = "Log.findByProtocolid", query = "SELECT l FROM Log l WHERE l.protocolid = :protocolid"),
    @NamedQuery(name = "Log.findByFinalstatus", query = "SELECT l FROM Log l WHERE l.finalstatus = :finalstatus"),
    @NamedQuery(name = "Log.findByContext", query = "SELECT l FROM Log l WHERE l.context = :context"),
    @NamedQuery(name = "Log.findByVersionid", query = "SELECT l FROM Log l WHERE l.versionid = :versionid"),
    @NamedQuery(name = "Log.findByPerfil", query = "SELECT l FROM Log l WHERE l.perfil = :perfil"),
    @NamedQuery(name = "Log.findByVdn", query = "SELECT l FROM Log l WHERE l.vdn = :vdn"),
    @NamedQuery(name = "Log.findByDdd", query = "SELECT l FROM Log l WHERE l.ddd = :ddd"),
    @NamedQuery(name = "Log.findByUf", query = "SELECT l FROM Log l WHERE l.uf = :uf"),
    @NamedQuery(name = "Log.findByCidade", query = "SELECT l FROM Log l WHERE l.cidade = :cidade"),
    @NamedQuery(name = "Log.findByAging", query = "SELECT l FROM Log l WHERE l.aging = :aging")})
public class Log implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "startdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Column(name = "stopdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopdate;
    @Size(max = 30)
    @Column(name = "ucid")
    private String ucid;
    @Column(name = "dnis")
    private Integer dnis;
    @Column(name = "ani")
    private Integer ani;
    @Size(max = 20)
    @Column(name = "instance")
    private String instance;
    @Size(max = 20)
    @Column(name = "document")
    private String document;
    @Size(max = 20)
    @Column(name = "protocolinteger")
    private String protocolinteger;
    @Size(max = 10)
    @Column(name = "protocolid")
    private String protocolid;
    @Column(name = "finalstatus")
    private Character finalstatus;
    @Size(max = 4000)
    @Column(name = "context")
    private String context;
    @Column(name = "versionid")
    private Integer versionid;
    @Column(name = "perfil")
    private Integer perfil;
    @Size(max = 20)
    @Column(name = "vdn")
    private String vdn;
    @Size(max = 2)
    @Column(name = "ddd")
    private String ddd;
    @Size(max = 2)
    @Column(name = "uf")
    private String uf;
    @Size(max = 100)
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "aging")
    private Integer aging;
    @OneToMany(mappedBy = "logid")
    private Collection<Logdetail> logdetailCollection;
    @OneToMany(mappedBy = "logid")
    private Collection<Track> trackCollection;

    public Log() {
    }

    public Log(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getStopdate() {
        return stopdate;
    }

    public void setStopdate(Date stopdate) {
        this.stopdate = stopdate;
    }

    public String getUcid() {
        return ucid;
    }

    public void setUcid(String ucid) {
        this.ucid = ucid;
    }

    public Integer getDnis() {
        return dnis;
    }

    public void setDnis(Integer dnis) {
        this.dnis = dnis;
    }

    public Integer getAni() {
        return ani;
    }

    public void setAni(Integer ani) {
        this.ani = ani;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getProtocolinteger() {
        return protocolinteger;
    }

    public void setProtocolinteger(String protocolinteger) {
        this.protocolinteger = protocolinteger;
    }

    public String getProtocolid() {
        return protocolid;
    }

    public void setProtocolid(String protocolid) {
        this.protocolid = protocolid;
    }

    public Character getFinalstatus() {
        return finalstatus;
    }

    public void setFinalstatus(Character finalstatus) {
        this.finalstatus = finalstatus;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public Integer getPerfil() {
        return perfil;
    }

    public void setPerfil(Integer perfil) {
        this.perfil = perfil;
    }

    public String getVdn() {
        return vdn;
    }

    public void setVdn(String vdn) {
        this.vdn = vdn;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getAging() {
        return aging;
    }

    public void setAging(Integer aging) {
        this.aging = aging;
    }

    @XmlTransient
    public Collection<Logdetail> getLogdetailCollection() {
        return logdetailCollection;
    }

    public void setLogdetailCollection(Collection<Logdetail> logdetailCollection) {
        this.logdetailCollection = logdetailCollection;
    }

    @XmlTransient
    public Collection<Track> getTrackCollection() {
        return trackCollection;
    }

    public void setTrackCollection(Collection<Track> trackCollection) {
        this.trackCollection = trackCollection;
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
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Log[ id=" + id + " ]";
    }
    
}
