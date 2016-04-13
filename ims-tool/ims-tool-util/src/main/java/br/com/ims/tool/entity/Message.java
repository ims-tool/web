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
@Table(name = "flow.message")
@XmlRootElement
public class Message implements Serializable {
    
	private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String description;
    private String flag;
    private String datai;
    private String dataf;
    private String ddd_in;
    private String ddd_not_in;
    private String spot;
    private String msg_order;
    
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getDatai() {
		return datai;
	}
	public void setDatai(String datai) {
		this.datai = datai;
	}
	public String getDataf() {
		return dataf;
	}
	public void setDataf(String dataf) {
		this.dataf = dataf;
	}
	public String getDdd_in() {
		return ddd_in;
	}
	public void setDdd_in(String ddd_in) {
		this.ddd_in = ddd_in;
	}
	public String getDdd_not_in() {
		return ddd_not_in;
	}
	public void setDdd_not_in(String ddd_not_in) {
		this.ddd_not_in = ddd_not_in;
	}

	public String getSpot() {
		return spot;
	}
	public void setSpot(String spot) {
		this.spot = spot;
	}
	public String getMsg_order() {
		return msg_order;
	}
	public void setMsg_order(String msg_order) {
		this.msg_order = msg_order;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
