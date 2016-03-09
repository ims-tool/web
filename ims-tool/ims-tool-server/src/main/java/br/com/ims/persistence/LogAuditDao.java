package br.com.ims.persistence;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.ims.tool.entity.Audit;
import br.com.ims.tool.util.ConnectionDB;


public class LogAuditDao {
	
private static Logger logger = Logger.getLogger(LogAuditDao.class);
	

	public static void save(Audit logAudit) {
		
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();
			String query = "insert into log.audict (id, rowdate, typeid, userid, description, artifact, originalValue) values"
					+ "(nextval('serial'), current_timestamp, "+logAudit.getTypeid()+", "+logAudit.getUserid()+", "
							+ " '"+logAudit.getDescription()+"', '"+logAudit.getArtifact()+"', '"+ logAudit.getOriginalValue()+"')";
			
			conn.ExecuteQueryUpdate(query);
			
		} catch (SQLException e) {
			logger.error("Erro ao salvar log audit ", e);
		} finally {
			conn.finalize();
		}
		
	}
	

}
