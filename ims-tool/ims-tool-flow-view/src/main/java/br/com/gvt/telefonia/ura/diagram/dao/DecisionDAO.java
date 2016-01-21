package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Decision;

public class DecisionDAO extends DAO<Decision> {

	@Override
	public String getClassName() {
		return Decision.class.getSimpleName();
	}

}
