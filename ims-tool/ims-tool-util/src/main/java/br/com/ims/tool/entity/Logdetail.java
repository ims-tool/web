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
@Table(name = "logdetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logdetail.findAll", query = "SELECT l FROM Logdetail l"),
    @NamedQuery(name = "Logdetail.findById", query = "SELECT l FROM Logdetail l WHERE l.id = :id"),
    @NamedQuery(name = "Logdetail.findByRowdate", query = "SELECT l FROM Logdetail l WHERE l.rowdate = :rowdate"),
    @NamedQuery(name = "Logdetail.findByParamname", query = "SELECT l FROM Logdetail l WHERE l.paramname = :paramname"),
    @NamedQuery(name = "Logdetail.findByParamvalue", query = "SELECT l FROM Logdetail l WHERE l.paramvalue = :paramvalue"),
    @NamedQuery(name = "Logdetail.findByVersionid", query = "SELECT l FROM Logdetail l WHERE l.versionid = :versionid")})
public class Logdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "rowdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowdate;
    @Size(max = 50)
    @Column(name = "paramname")
    private String paramname;
    @Size(max = 4000)
    @Column(name = "paramvalue")
    private String paramvalue;
    @Column(name = "versionid")
    private Integer versionid;
    @JoinColumn(name = "logid", referencedColumnName = "id")
    @ManyToOne
    private Log logid;

    public Logdetail() {
    }

    public Logdetail(Integer id) {
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

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
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
        if (!(object instanceof Logdetail)) {
            return false;
        }
        Logdetail other = (Logdetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Logdetail[ id=" + id + " ]";
    }
    
}
