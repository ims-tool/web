package br.com.gvt.telefonia.ura.diagram.dao;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Choice;

public class ChoiceDAO extends DAO<Choice> {
	
	
	public List<Choice> findByMenu(String id){
		
		return findAllBy(" menu = '"+id+"' ");
	}

	@Override
	public String getClassName() {
		return Choice.class.getSimpleName();
	}
	
	

}
