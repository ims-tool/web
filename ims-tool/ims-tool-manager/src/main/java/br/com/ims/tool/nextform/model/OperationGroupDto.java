package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Collection;

public class OperationGroupDto implements Serializable {

	private static final long serialVersionUID = -1610203762078102445L;
	
	private long id;
	private long operationId;
	private long order;
	private long operationMapId;
	private OperationMapDto operationMap;
	private Collection<OperationParametersDto> listaOperationParameters;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOperationId() {
		return operationId;
	}
	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}
	public long getOrder() {
		return order;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	public long getOperationMapId() {
		return operationMapId;
	}
	public void setOperationMapId(long operationMapId) {
		this.operationMapId = operationMapId;
	}
	public OperationMapDto getOperationMap() {
		return operationMap;
	}
	public void setOperationMap(OperationMapDto operationMap) {
		this.operationMap = operationMap;
	}
	public Collection<OperationParametersDto> getListaOperationParameters() {
		return listaOperationParameters;
	}
	public void setListaOperationParameters(
			Collection<OperationParametersDto> listaOperationParameters) {
		this.listaOperationParameters = listaOperationParameters;
	}
	@Override
	public String toString() {
		return "OperationGroupDto [id=" + id + ", operationId=" + operationId
				+ ", order=" + order + ", operationMapId=" + operationMapId
				+ ", operationMap=" + operationMap
				+ ", listaOperationParameters=" + listaOperationParameters
				+ "]";
	}

}
