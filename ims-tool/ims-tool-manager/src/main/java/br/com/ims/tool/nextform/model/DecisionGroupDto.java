package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Collection;

public class DecisionGroupDto implements Serializable {

	private static final long serialVersionUID = -6127398872740191118L;
	
	private long id;
	private long decisionId;
	private long order;
	private long decisionMapId;
	private DecisionMapDto decisionMap;
	private Collection<DecisionChanceDto> listaDecisionChance;
	private Collection<DecisionParametersDto> listaDecisionParameters;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDecisionId() {
		return decisionId;
	}
	public void setDecisionId(long decisionId) {
		this.decisionId = decisionId;
	}
	public long getOrder() {
		return order;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	public long getDecisionMapId() {
		return decisionMapId;
	}
	public void setDecisionMapId(long decisionMapId) {
		this.decisionMapId = decisionMapId;
	}
	public DecisionMapDto getDecisionMap() {
		return decisionMap;
	}
	public void setDecisionMap(DecisionMapDto decisionMap) {
		this.decisionMap = decisionMap;
	}
	public Collection<DecisionChanceDto> getListaDecisionChance() {
		return listaDecisionChance;
	}
	public void setListaDecisionChance(
			Collection<DecisionChanceDto> listaDecisionChance) {
		this.listaDecisionChance = listaDecisionChance;
	}
	public Collection<DecisionParametersDto> getListaDecisionParameters() {
		return listaDecisionParameters;
	}
	public void setListaDecisionParameters(
			Collection<DecisionParametersDto> listaDecisionParameters) {
		this.listaDecisionParameters = listaDecisionParameters;
	}
	@Override
	public String toString() {
		return "DecisionGroupDto [id=" + id + ", decisionId=" + decisionId
				+ ", order=" + order + ", decisionMapId=" + decisionMapId
				+ ", decisionMap=" + decisionMap + ", listaDecisionChance="
				+ listaDecisionChance + ", listaDecisionParameters="
				+ listaDecisionParameters + "]";
	}
}
