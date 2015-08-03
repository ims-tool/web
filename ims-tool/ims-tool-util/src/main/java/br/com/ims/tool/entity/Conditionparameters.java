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
@Table(name = "conditionparameters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conditionparameters.findAll", query = "SELECT c FROM Conditionparameters c"),
    @NamedQuery(name = "Conditionparameters.findById", query = "SELECT c FROM Conditionparameters c WHERE c.id = :id"),
    @NamedQuery(name = "Conditionparameters.findByParamname", query = "SELECT c FROM Conditionparameters c WHERE c.paramname = :paramname"),
    @NamedQuery(name = "Conditionparameters.findByParamvalue", query = "SELECT c FROM Conditionparameters c WHERE c.paramvalue = :paramvalue"),
    @NamedQuery(name = "Conditionparameters.findByVersionid", query = "SELECT c FROM Conditionparameters c WHERE c.versionid = :versionid")})
public class Conditionparameters implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "paramname")
    private String paramname;
    @Size(max = 500)
    @Column(name = "paramvalue")
    private String paramvalue;
    @Column(name = "versionid")
    private Integer versionid;
    @JoinColumn(name = "conditiongroupid", referencedColumnName = "id")
    @ManyToOne
    private Conditiongroup conditiongroupid;

    public Conditionparameters() {
    }

    public Conditionparameters(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Conditiongroup getConditiongroupid() {
        return conditiongroupid;
    }

    public void setConditiongroupid(Conditiongroup conditiongroupid) {
        this.conditiongroupid = conditiongroupid;
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
        if (!(object instanceof Conditionparameters)) {
            return false;
        }
        Conditionparameters other = (Conditionparameters) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Conditionparameters[ id=" + id + " ]";
    }
    
}
