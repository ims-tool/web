package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Grammar;

public class GrammarDAO extends DAO<Grammar> {

	@Override
	public String getClassName() {
		return Grammar.class.getSimpleName();
	}

}
