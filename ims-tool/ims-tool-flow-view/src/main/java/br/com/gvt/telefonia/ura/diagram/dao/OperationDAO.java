package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Operation;

public class OperationDAO extends DAO<Operation> {

	@Override
	public String getClassName() {
		return Operation.class.getSimpleName();
	}

}
