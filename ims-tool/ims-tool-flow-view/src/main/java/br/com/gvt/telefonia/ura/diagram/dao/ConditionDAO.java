package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Condition;

public class ConditionDAO extends DAO<Condition> {

	@Override
	public String getClassName() {
		return Condition.class.getSimpleName();
	}
}
