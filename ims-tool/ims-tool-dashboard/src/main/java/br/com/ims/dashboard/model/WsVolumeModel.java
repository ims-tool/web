package br.com.ims.dashboard.model;

public class WsVolumeModel {

	private Integer id;
	private String method_service;
	private String chamadas_servico;
	private String total_timeout;
	private String percent_usou_timeout;
	private String percent_com_timeout;
	private String status;


	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMethod_service() {
		return method_service;
	}
	public void setMethod_service(String method_service) {
		this.method_service = method_service;
	}
	public String getChamadas_servico() {
		return chamadas_servico;
	}
	public void setChamadas_servico(String chamadas_servico) {
		this.chamadas_servico = chamadas_servico;
	}
	public String getTotal_timeout() {
		return total_timeout;
	}
	public void setTotal_timeout(String total_timeout) {
		this.total_timeout = total_timeout;
	}
	public String getPercent_usou_timeout() {
		return percent_usou_timeout;
	}
	public void setPercent_usou_timeout(String percent_usou_timeout) {
		this.percent_usou_timeout = percent_usou_timeout;
	}
	public String getPercent_com_timeout() {
		return percent_com_timeout;
	}
	public void setPercent_com_timeout(String percent_com_timeout) {
		this.percent_com_timeout = percent_com_timeout;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
