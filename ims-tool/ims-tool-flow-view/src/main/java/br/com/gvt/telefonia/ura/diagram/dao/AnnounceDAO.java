package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Announce;

public class AnnounceDAO extends DAO<Announce> {

	@Override
	public String getClassName() {
		return Announce.class.getSimpleName();
	}
}
