package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Menu;

public class MenuDAO extends DAO<Menu> {

	@Override
	public String getClassName() {
		return Menu.class.getSimpleName();
	}
	

}
