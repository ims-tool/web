package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Prompt;

public class PromptDAO extends DAO<Prompt> {

	@Override
	public String getClassName() {
		return Prompt.class.getSimpleName();
	}

}
