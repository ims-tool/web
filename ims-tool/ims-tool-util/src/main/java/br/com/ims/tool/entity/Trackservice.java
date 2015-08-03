package br.com.ims.tool.entity;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cesar
 */
@Entity
@Table(name = "trackservice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trackservice.findAll", query = "SELECT t FROM Trackservice t"),
    @NamedQuery(name = "Trackservice.findById", query = "SELECT t FROM Trackservice t WHERE t.trackservicePK.id = :id"),
    @NamedQuery(name = "Trackservice.findByTrackid", query = "SELECT t FROM Trackservice t WHERE t.trackid = :trackid"),
    @NamedQuery(name = "Trackservice.findByGroupid", query = "SELECT t FROM Trackservice t WHERE t.groupid = :groupid"),
    @NamedQuery(name = "Trackservice.findByMethodService", query = "SELECT t FROM Trackservice t WHERE t.methodService = :methodService"),
    @NamedQuery(name = "Trackservice.findByResultCall", query = "SELECT t FROM Trackservice t WHERE t.resultCall = :resultCall"),
    @NamedQuery(name = "Trackservice.findByParametersIn", query = "SELECT t FROM Trackservice t WHERE t.parametersIn = :parametersIn"),
    @NamedQuery(name = "Trackservice.findByRowdate", query = "SELECT t FROM Trackservice t WHERE t.trackservicePK.rowdate = :rowdate"),
    @NamedQuery(name = "Trackservice.findByVersionid", query = "SELECT t FROM Trackservice t WHERE t.versionid = :versionid"),
    @NamedQuery(name = "Trackservice.findByDescription", query = "SELECT t FROM Trackservice t WHERE t.description = :description"),
    @NamedQuery(name = "Trackservice.findByLogid", query = "SELECT t FROM Trackservice t WHERE t.logid = :logid")})
public class Trackservice implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TrackservicePK trackservicePK;
    @Column(name = "trackid")
    private Integer trackid;
    @Column(name = "groupid")
    private Integer groupid;
    @Size(max = 100)
    @Column(name = "method_service")
    private String methodService;
    @Size(max = 400)
    @Column(name = "result_call")
    private String resultCall;
    @Size(max = 800)
    @Column(name = "parameters_in")
    private String parametersIn;
    @Column(name = "versionid")
    private Integer versionid;
    @Size(max = 100)
    @Column(name = "description")
    private String description;
    @Column(name = "logid")
    private Integer logid;
    @JoinColumn(name = "errorcodeid", referencedColumnName = "id")
    @ManyToOne
    private Errorcode errorcodeid;

    public Trackservice() {
    }

    public Trackservice(TrackservicePK trackservicePK) {
        this.trackservicePK = trackservicePK;
    }

    public Trackservice(int id, Date rowdate) {
        this.trackservicePK = new TrackservicePK(id, rowdate);
    }

    public TrackservicePK getTrackservicePK() {
        return trackservicePK;
    }

    public void setTrackservicePK(TrackservicePK trackservicePK) {
        this.trackservicePK = trackservicePK;
    }

    public Integer getTrackid() {
        return trackid;
    }

    public void setTrackid(Integer trackid) {
        this.trackid = trackid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getMethodService() {
        return methodService;
    }

    public void setMethodService(String methodService) {
        this.methodService = methodService;
    }

    public String getResultCall() {
        return resultCall;
    }

    public void setResultCall(String resultCall) {
        this.resultCall = resultCall;
    }

    public String getParametersIn() {
        return parametersIn;
    }

    public void setParametersIn(String parametersIn) {
        this.parametersIn = parametersIn;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public Errorcode getErrorcodeid() {
        return errorcodeid;
    }

    public void setErrorcodeid(Errorcode errorcodeid) {
        this.errorcodeid = errorcodeid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trackservicePK != null ? trackservicePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trackservice)) {
            return false;
        }
        Trackservice other = (Trackservice) object;
        if ((this.trackservicePK == null && other.trackservicePK != null) || (this.trackservicePK != null && !this.trackservicePK.equals(other.trackservicePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Trackservice[ trackservicePK=" + trackservicePK + " ]";
    }
    
}
