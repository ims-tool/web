package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Collection;

public class ConditionGroupDto implements Serializable {

	private static final long serialVersionUID = -5620205652847767052L;
	
	private long id;
	private long conditionId;
	private long order;
	private long conditionMapId;
	private ConditionMapDto conditionMap;
	private Collection<ConditionValueDto> listaConditionValue;
	private Collection<ConditionParametersDto> listaConditionParameters;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getConditionId() {
		return conditionId;
	}
	public void setConditionId(long conditionId) {
		this.conditionId = conditionId;
	}
	public long getOrder() {
		return order;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	public long getConditionMapId() {
		return conditionMapId;
	}
	public void setConditionMapId(long conditionMapId) {
		this.conditionMapId = conditionMapId;
	}
	public ConditionMapDto getConditionMap() {
		return conditionMap;
	}
	public void setConditionMap(ConditionMapDto conditionMap) {
		this.conditionMap = conditionMap;
	}
	public Collection<ConditionValueDto> getListaConditionValue() {
		return listaConditionValue;
	}
	public void setListaConditionValue(
			Collection<ConditionValueDto> listaConditionValue) {
		this.listaConditionValue = listaConditionValue;
	}
	public Collection<ConditionParametersDto> getListaConditionParameters() {
		return listaConditionParameters;
	}
	public void setListaConditionParameters(
			Collection<ConditionParametersDto> listaConditionParameters) {
		this.listaConditionParameters = listaConditionParameters;
	}
	@Override
	public String toString() {
		return "ConditionGroupDto [id=" + id + ", conditionId=" + conditionId
				+ ", order=" + order + ", conditionMapId=" + conditionMapId
				+ ", conditionMap=" + conditionMap + ", listaConditionValue="
				+ listaConditionValue + ", listaConditionParameters="
				+ listaConditionParameters + "]";
	}
	
	

}
