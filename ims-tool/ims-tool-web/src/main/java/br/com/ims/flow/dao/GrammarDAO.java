package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.GrammarEntity;

public class GrammarDAO extends AbstractDAO<GrammarEntity> {
	private List<GrammarEntity> listGrammar =  null;
	private static GrammarDAO instance = null;
	private GrammarDAO() {
		listGrammar = new ArrayList<GrammarEntity>(); 			
	}	
	public static GrammarDAO getInstance() {
		if(instance == null) {
			instance = new GrammarDAO();
		}
		return instance;
	}
	public List<GrammarEntity> getByFilter(String where) {
		
		return this.listGrammar;		
	}
	
	public List<GrammarEntity> getAll() {
		
		return this.listGrammar;		
	}
	public GrammarEntity get(String id) {
		for(GrammarEntity grammar : this.listGrammar) {
			if(grammar.getId().equals(id)) {
				return grammar;
			}
		}
		return null;
	}
	public GrammarEntity getByName(String name) {
		for(GrammarEntity grammar : this.listGrammar) {
			if(grammar.getName().equalsIgnoreCase(name)) {
				return grammar;
			}
		}
		return null;
	}
	public boolean save(GrammarEntity entity) {
		this.listGrammar.add(entity);
		return true;
	}

	@Override
	public boolean update(GrammarEntity entity) {
		// TODO Auto-generated method stub
		return true;
		
	}

	@Override
	public boolean delete(GrammarEntity entity) {
		// TODO Auto-generated method stub
		return true;
	}

	

}
