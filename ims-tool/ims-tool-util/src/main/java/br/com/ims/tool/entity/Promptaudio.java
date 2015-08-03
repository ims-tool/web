package br.com.ims.tool.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cesar
 */
@Entity
@Table(name = "promptaudio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promptaudio.findAll", query = "SELECT p FROM Promptaudio p"),
    @NamedQuery(name = "Promptaudio.findByPrompt", query = "SELECT p FROM Promptaudio p WHERE p.promptaudioPK.prompt = :prompt"),
    @NamedQuery(name = "Promptaudio.findByAudio", query = "SELECT p FROM Promptaudio p WHERE p.promptaudioPK.audio = :audio"),
    @NamedQuery(name = "Promptaudio.findByOrdernum", query = "SELECT p FROM Promptaudio p WHERE p.ordernum = :ordernum"),
    @NamedQuery(name = "Promptaudio.findByVersionid", query = "SELECT p FROM Promptaudio p WHERE p.versionid = :versionid")})
public class Promptaudio implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PromptaudioPK promptaudioPK;
    @Column(name = "ordernum")
    private Integer ordernum;
    @Column(name = "versionid")
    private Integer versionid;
    @JoinColumn(name = "audio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Audio audio1;
    @JoinColumn(name = "condition", referencedColumnName = "id")
    @ManyToOne
    private Condition condition;
    @JoinColumn(name = "prompt", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Prompt prompt1;

    public Promptaudio() {
    }

    public Promptaudio(PromptaudioPK promptaudioPK) {
        this.promptaudioPK = promptaudioPK;
    }

    public Promptaudio(int prompt, int audio) {
        this.promptaudioPK = new PromptaudioPK(prompt, audio);
    }

    public PromptaudioPK getPromptaudioPK() {
        return promptaudioPK;
    }

    public void setPromptaudioPK(PromptaudioPK promptaudioPK) {
        this.promptaudioPK = promptaudioPK;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public Audio getAudio1() {
        return audio1;
    }

    public void setAudio1(Audio audio1) {
        this.audio1 = audio1;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Prompt getPrompt1() {
        return prompt1;
    }

    public void setPrompt1(Prompt prompt1) {
        this.prompt1 = prompt1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promptaudioPK != null ? promptaudioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promptaudio)) {
            return false;
        }
        Promptaudio other = (Promptaudio) object;
        if ((this.promptaudioPK == null && other.promptaudioPK != null) || (this.promptaudioPK != null && !this.promptaudioPK.equals(other.promptaudioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Promptaudio[ promptaudioPK=" + promptaudioPK + " ]";
    }
    
}
