package br.com.ims.tool.entity;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Cesar
 */
@Embeddable
public class TrackservicePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rowdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowdate;

    public TrackservicePK() {
    }

    public TrackservicePK(int id, Date rowdate) {
        this.id = id;
        this.rowdate = rowdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRowdate() {
        return rowdate;
    }

    public void setRowdate(Date rowdate) {
        this.rowdate = rowdate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (rowdate != null ? rowdate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrackservicePK)) {
            return false;
        }
        TrackservicePK other = (TrackservicePK) object;
        if (this.id != other.id) {
            return false;
        }
        if ((this.rowdate == null && other.rowdate != null) || (this.rowdate != null && !this.rowdate.equals(other.rowdate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.TrackservicePK[ id=" + id + ", rowdate=" + rowdate + " ]";
    }
    
}
