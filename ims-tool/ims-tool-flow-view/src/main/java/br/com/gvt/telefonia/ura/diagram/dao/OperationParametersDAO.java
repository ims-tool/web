package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.OperationParameters;

public class OperationParametersDAO extends DAO<OperationParameters> {

	@Override
	public String getClassName() {
		return OperationParameters.class.getSimpleName();
	}

}
