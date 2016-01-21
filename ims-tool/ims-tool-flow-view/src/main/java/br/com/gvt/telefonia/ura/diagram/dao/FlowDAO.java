package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Flow;

public class FlowDAO extends DAO<Flow> {

	@Override
	public String getClassName() {
		return Flow.class.getSimpleName();
	}

}
