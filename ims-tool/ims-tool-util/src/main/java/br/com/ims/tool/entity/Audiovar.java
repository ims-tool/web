package br.com.ims.tool.entity;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
@Table(name = "audiovar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audiovar.findAll", query = "SELECT a FROM Audiovar a"),
    @NamedQuery(name = "Audiovar.findById", query = "SELECT a FROM Audiovar a WHERE a.id = :id"),
    @NamedQuery(name = "Audiovar.findByParamname", query = "SELECT a FROM Audiovar a WHERE a.paramname = :paramname"),
    @NamedQuery(name = "Audiovar.findByParamvalue", query = "SELECT a FROM Audiovar a WHERE a.paramvalue = :paramvalue"),
    @NamedQuery(name = "Audiovar.findByVersionid", query = "SELECT a FROM Audiovar a WHERE a.versionid = :versionid")})
public class Audiovar implements Serializable {
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
    @JoinColumn(name = "audioid", referencedColumnName = "id")
    @ManyToOne
    private Audio audioid;

    public Audiovar() {
    }

    public Audiovar(Integer id) {
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

    public Audio getAudioid() {
        return audioid;
    }

    public void setAudioid(Audio audioid) {
        this.audioid = audioid;
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
        if (!(object instanceof Audiovar)) {
            return false;
        }
        Audiovar other = (Audiovar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Audiovar[ id=" + id + " ]";
    }
    
}

