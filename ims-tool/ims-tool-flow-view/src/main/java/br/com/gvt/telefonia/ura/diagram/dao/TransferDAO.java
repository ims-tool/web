package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Transfer;

public class TransferDAO extends DAO<Transfer> {

	@Override
	public String getClassName() {
		return Transfer.class.getSimpleName();
	}

}
