package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.model.MapTypeEntity;

@SuppressWarnings("serial")
public class MapTypeDAO extends AbstractDAO<MapTypeEntity> {
	//private DbConnection db =  null;
	public static Logger log = Logger.getLogger(MapTypeDAO.class);
	private static MapTypeDAO instance = null;
	private MapTypeDAO() {
		//db = new DbConnection("ConditionMapDAO"); 			
	}
	
	public static MapTypeDAO getInstance() {
		if(instance == null) {
			instance = new MapTypeDAO();
		}
		return instance;
	}
	public List<MapTypeEntity> getByFilter(String where) {
		log.debug("getByFilter("+where+")");
		String sql = "SELECT mt.id mt_id, mt.name mt_name, mt.description mt_description "
				    + "FROM flow.maptype mt "
				    + "<WHERE> "
				    + "ORDER BY mt.name";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		} else {
			sql = sql.replace("<WHERE>", "");
		}
		List<MapTypeEntity> result = new ArrayList<MapTypeEntity>();
		ResultSet rs = null;
		DbConnection db = new DbConnection("MapTypeDAO-getByFilter");
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				
				MapTypeEntity mapType = new MapTypeEntity();
				mapType.setId(rs.getString("mt_id"));
				mapType.setName(rs.getString("mt_name"));
				mapType.setDescription(rs.getString("mt_description"));
				result.add(mapType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} finally {
			db.finalize();
			
		}
		
		return result;
	}
	
	public List<MapTypeEntity> getAll() {
		
		return this.getByFilter(null);
		
	}
	public MapTypeEntity get(String id) {
		List<MapTypeEntity> result = this.getByFilter("WHERE mt.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	
	public boolean save(MapTypeEntity entity) {
		return true;
	}

	@Override
	public boolean update(MapTypeEntity entity) {
		// TODO Auto-generated method stub
		return true;
		
	}

	@Override
	public boolean delete(MapTypeEntity entity) {
		return true;
	}

}
