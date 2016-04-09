package br.com.ims.tool.nextform.model;

import java.io.Serializable;
import java.util.Collection;

public class DecisionDto implements Serializable {

	private static final long serialVersionUID = 2216816817797146086L;
	
	private long id;
	private String name;
	private String description;
	private Collection<DecisionChanceDto> listaDecisionChance;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	
	public Collection<DecisionChanceDto> getListaDecisionChance() {
		return listaDecisionChance;
	}
	public void setListaDecisionChance(Collection<DecisionChanceDto> listaDecisionChance) {
		this.listaDecisionChance = listaDecisionChance;
	}
	@Override
	public String toString() {
		return "DecisionDto [id=" + id + ", name=" + name + ", description="
				+ description + ", listaDecisionGroup=" + listaDecisionChance
				+ "]";
	}
	
	
}
