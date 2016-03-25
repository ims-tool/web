package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.GrammarEntity;

public class GrammarDAO extends AbstractDAO<GrammarEntity> {
	private DbConnection db = null;
	private static GrammarDAO instance = null;
	private GrammarDAO() {
		db = new DbConnection(""); 			
	}	
	public static GrammarDAO getInstance() {
		if(instance == null) {
			instance = new GrammarDAO();
		}
		return instance;
	}
	public List<GrammarEntity> getByFilter(String where) {
		
		String sql = "SELECT id,name,description,type,sizemax,sizemin "+
			     	 "FROM flow.grammar <WHERE> ORDER BY name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		
		List<GrammarEntity> result = new ArrayList<GrammarEntity>();
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				GrammarEntity grammar = new GrammarEntity();
				grammar.setId(rs.getString("id"));
				grammar.setName(rs.getString("name"));
				grammar.setDescription(rs.getString("description"));
				grammar.setType(rs.getString("type"));
				grammar.setSizeMax(rs.getInt("sizemax"));
				grammar.setSizeMin(rs.getInt("sizemin"));
				result.add(grammar);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};
		}
		
		return result;		
	}
	
	public List<GrammarEntity> getAll() {
		
		return this.getByFilter(null);		
	}
	public GrammarEntity get(String id) {
		List<GrammarEntity> result = this.getByFilter("WHERE id='"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public GrammarEntity getByName(String name) {
		List<GrammarEntity> result = this.getByFilter("WHERE lower(name)='"+name.toLowerCase()+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	public boolean save(GrammarEntity entity) {
		boolean result = true;
		String sql = "INSERT INTO flow.grammar (id,name,description,type,sizemax,sizemin,versionid) "+
					 "VALUES ('"+entity.getId()+"','"+entity.getName()+"','"+entity.getDescription()+"','"+entity.getType()+"','"+entity.getSizeMax()+"','"+entity.getSizeMin()+"',"+entity.getVersionId().getId()+") ";
		             
		result = db.ExecuteSql(sql);
		return result;
	}

	@Override
	public boolean update(GrammarEntity entity) {
		boolean result = true;
		String sql = "UPDATE flow.grammar SET name='"+entity.getName()+"',description='"+entity.getDescription()+"',type='"+entity.getType()+"',sizemax='"+entity.getSizeMax()+"',sizemin='"+entity.getSizeMin()+"',versionid='"+entity.getVersionId().getId()+"' "+
					 "WHERE id ='"+entity.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
		
	}

	@Override
	public boolean delete(GrammarEntity entity) {
		boolean result = true;
		String sql = "DELETE FROM flow.grammar WHERE id ='"+entity.getId()+"' ";
		             
		result = db.ExecuteSql(sql);
		return result;
	}

	

}
