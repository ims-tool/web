package br.com.ims.tool.entity;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cesar
 */
@Entity
@Table(name = "track")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Track.findAll", query = "SELECT t FROM Track t"),
    @NamedQuery(name = "Track.findById", query = "SELECT t FROM Track t WHERE t.id = :id"),
    @NamedQuery(name = "Track.findByRowdate", query = "SELECT t FROM Track t WHERE t.rowdate = :rowdate"),
    @NamedQuery(name = "Track.findByFormid", query = "SELECT t FROM Track t WHERE t.formid = :formid"),
    @NamedQuery(name = "Track.findByTagid", query = "SELECT t FROM Track t WHERE t.tagid = :tagid"),
    @NamedQuery(name = "Track.findByVersionid", query = "SELECT t FROM Track t WHERE t.versionid = :versionid"),
    @NamedQuery(name = "Track.findByLogType", query = "SELECT t FROM Track t WHERE t.logType = :logType")})
public class Track implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "rowdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowdate;
    @Column(name = "formid")
    private Integer formid;
    @Column(name = "tagid")
    private Integer tagid;
    @Column(name = "versionid")
    private Integer versionid;
    @Size(max = 2)
    @Column(name = "log_type")
    private String logType;
    @JoinColumn(name = "logid", referencedColumnName = "id")
    @ManyToOne
    private Log logid;

    public Track() {
    }

    public Track(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRowdate() {
        return rowdate;
    }

    public void setRowdate(Date rowdate) {
        this.rowdate = rowdate;
    }

    public Integer getFormid() {
        return formid;
    }

    public void setFormid(Integer formid) {
        this.formid = formid;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Log getLogid() {
        return logid;
    }

    public void setLogid(Log logid) {
        this.logid = logid;
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
        if (!(object instanceof Track)) {
            return false;
        }
        Track other = (Track) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Track[ id=" + id + " ]";
    }
    
}
