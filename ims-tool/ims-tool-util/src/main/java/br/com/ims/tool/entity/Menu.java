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
@Table(name = "menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
    @NamedQuery(name = "Menu.findById", query = "SELECT m FROM Menu m WHERE m.id = :id"),
    @NamedQuery(name = "Menu.findByName", query = "SELECT m FROM Menu m WHERE m.name = :name"),
    @NamedQuery(name = "Menu.findByDescription", query = "SELECT m FROM Menu m WHERE m.description = :description"),
    @NamedQuery(name = "Menu.findByFetchtimeout", query = "SELECT m FROM Menu m WHERE m.fetchtimeout = :fetchtimeout"),
    @NamedQuery(name = "Menu.findByTerminatingtimeout", query = "SELECT m FROM Menu m WHERE m.terminatingtimeout = :terminatingtimeout"),
    @NamedQuery(name = "Menu.findByTerminatingcharacter", query = "SELECT m FROM Menu m WHERE m.terminatingcharacter = :terminatingcharacter"),
    @NamedQuery(name = "Menu.findByVersionid", query = "SELECT m FROM Menu m WHERE m.versionid = :versionid")})
public class Menu implements Serializable {
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
    @Column(name = "fetchtimeout")
    private Integer fetchtimeout;
    @Column(name = "terminatingtimeout")
    private Integer terminatingtimeout;
    @Column(name = "terminatingcharacter")
    private Character terminatingcharacter;
    @Column(name = "versionid")
    private Integer versionid;
    @JoinColumn(name = "noinput", referencedColumnName = "id")
    @ManyToOne
    private Nomatchinput noinput;
    @JoinColumn(name = "nomatch", referencedColumnName = "id")
    @ManyToOne
    private Nomatchinput nomatch;
    @JoinColumn(name = "prompt", referencedColumnName = "id")
    @ManyToOne
    private Prompt prompt;

    public Menu() {
    }

    public Menu(Integer id) {
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

    public Integer getFetchtimeout() {
        return fetchtimeout;
    }

    public void setFetchtimeout(Integer fetchtimeout) {
        this.fetchtimeout = fetchtimeout;
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

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
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
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Menu[ id=" + id + " ]";
    }
    
}

