package br.com.ims.tool.entity;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "trackdetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trackdetail.findAll", query = "SELECT t FROM Trackdetail t"),
    @NamedQuery(name = "Trackdetail.findById", query = "SELECT t FROM Trackdetail t WHERE t.id = :id"),
    @NamedQuery(name = "Trackdetail.findByTrackid", query = "SELECT t FROM Trackdetail t WHERE t.trackid = :trackid"),
    @NamedQuery(name = "Trackdetail.findByParamname", query = "SELECT t FROM Trackdetail t WHERE t.paramname = :paramname"),
    @NamedQuery(name = "Trackdetail.findByParamvalue", query = "SELECT t FROM Trackdetail t WHERE t.paramvalue = :paramvalue"),
    @NamedQuery(name = "Trackdetail.findByRowdate", query = "SELECT t FROM Trackdetail t WHERE t.rowdate = :rowdate"),
    @NamedQuery(name = "Trackdetail.findByVersionid", query = "SELECT t FROM Trackdetail t WHERE t.versionid = :versionid"),
    @NamedQuery(name = "Trackdetail.findByLogid", query = "SELECT t FROM Trackdetail t WHERE t.logid = :logid")})
public class Trackdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "trackid")
    private Integer trackid;
    @Size(max = 50)
    @Column(name = "paramname")
    private String paramname;
    @Size(max = 4000)
    @Column(name = "paramvalue")
    private String paramvalue;
    @Column(name = "rowdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowdate;
    @Column(name = "versionid")
    private Integer versionid;
    @Column(name = "logid")
    private Integer logid;

    public Trackdetail() {
    }

    public Trackdetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrackid() {
        return trackid;
    }

    public void setTrackid(Integer trackid) {
        this.trackid = trackid;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public String getParamvalue() {
        return paramvalue;
    }

    public void setParamvalue(String paramvalue) {
        this.paramvalue = paramvalue;
    }

    public Date getRowdate() {
        return rowdate;
    }

    public void setRowdate(Date rowdate) {
        this.rowdate = rowdate;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
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
        if (!(object instanceof Trackdetail)) {
            return false;
        }
        Trackdetail other = (Trackdetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Trackdetail[ id=" + id + " ]";
    }
    
}
