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
@XmlRootElement
public class RouterIvr implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long nextFormId;
    
    private String context;

	public Long getNextFormId() {
		return nextFormId;
	}

	public void setNextFormId(Long nextFormId) {
		this.nextFormId = nextFormId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
    
    
    
    
}
