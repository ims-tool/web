package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Disconnect;

public class DisconnectDAO extends DAO<Disconnect> {

	@Override
	public String getClassName() {
		return Disconnect.class.getSimpleName();
	}
	
}
