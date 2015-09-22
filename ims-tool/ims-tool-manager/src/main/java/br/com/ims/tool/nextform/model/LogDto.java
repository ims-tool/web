package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Date;

public class LogDto implements Serializable {

	private static final long serialVersionUID = 2667534164705878747L;
	
	private long id;
	private Date startDate;
	private Date stopDate;
	private String ucid;
	private String dnis;
	private String ani;
	private String instance;
	private String documento;
	private String protocolNumber;
	private String protocolId;
	private String finalStatus;
	private String context;
	private String track;
	private String perfil;
	
	private String ddd;
	private String cidade;
	private String uf;
	private String aging;
	private String vdn;
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getStopDate() {
		return this.stopDate;
	}
	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
	public String getUcid() {
		return this.ucid;
	}
	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
	public String getDnis() {
		return this.dnis;
	}
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	public String getAni() {
		return this.ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public String getInstance() {
		return this.instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String getDocumento() {
		return this.documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getProtocolNumber() {
		return this.protocolNumber;
	}
	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}
	public String getProtocolId() {
		return this.protocolId;
	}
	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}
	public String getFinalStatus() {
		return this.finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	public String getContext() {
		return this.context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getTrack() {
		return this.track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getPerfil() {
		return this.perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getAging() {
		return aging;
	}
	public void setAging(String aging) {
		this.aging = aging;
	}
	public String getVdn() {
		return vdn;
	}
	public void setVdn(String vdn) {
		this.vdn = vdn;
	}
	
	@Override
	public String toString() {
		return "LogDto [id=" + this.id + ", startDate=" + this.startDate + ", stopDate=" + this.stopDate + ", ucid=" + this.ucid + ", dnis=" + this.dnis + ", ani=" + this.ani + ", instance=" + this.instance + ", documento=" /**/
				+ this.documento + ", protocolNumber=" + this.protocolNumber + ", protocolId=" + this.protocolId + ", finalStatus=" + this.finalStatus + ", context=" + this.context + ", track=" + this.track +  /**/
				", perfil=" + this.perfil + ", DDD=" + this.ddd + ", cidade=" + this.cidade + ", UF=" + this.uf + ", aging=" + this.aging + ", VDN=" + this.vdn  /**/
				+ "]";
	}

}
