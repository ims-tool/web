package br.com.ims.tool.entity;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Cesar
 */
@Embeddable
public class PromptaudioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "prompt")
    private int prompt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "audio")
    private int audio;

    public PromptaudioPK() {
    }

    public PromptaudioPK(int prompt, int audio) {
        this.prompt = prompt;
        this.audio = audio;
    }

    public int getPrompt() {
        return prompt;
    }

    public void setPrompt(int prompt) {
        this.prompt = prompt;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) prompt;
        hash += (int) audio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromptaudioPK)) {
            return false;
        }
        PromptaudioPK other = (PromptaudioPK) object;
        if (this.prompt != other.prompt) {
            return false;
        }
        if (this.audio != other.audio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.PromptaudioPK[ prompt=" + prompt + ", audio=" + audio + " ]";
    }
    
}
