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
			String query = "insert into log.audit (id, rowdate, typeid, userid, description, artifact, original_value, valueid, artifactid) values"
					+ "(nextval('serial'),  CURRENT_TIMESTAMP(2), "+logAudit.getTypeid()+", (select id from access.user where login = '"+logAudit.getUserLogin()+"'), "
							+ " '"+logAudit.getDescription()+"', '"+logAudit.getArtifact()+"', '"+ logAudit.getOriginalValue()+"', "+logAudit.getValueid()+", "+logAudit.getArtifactid()+")";
			
			conn.ExecuteQueryUpdate(query);
			
		} catch (SQLException e) {
			logger.error("Erro ao salvar log audit ", e);
		} finally {
			conn.finalize();
		}
		
	}
	

}
