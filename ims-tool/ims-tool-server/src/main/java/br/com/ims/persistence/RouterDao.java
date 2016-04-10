package br.com.ims.persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ParseConversionEvent;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.tags.ParamAware;

import br.com.ims.tool.entity.Message;
import br.com.ims.tool.entity.MessageType;
import br.com.ims.tool.entity.RouterIvr;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;
import br.com.ims.tool.util.ConnectionDB;

public class RouterDao {

	private static Logger logger = Logger.getLogger(RouterDao.class);

	public static RouterIvr getIvr(String dnis) {
		
		RouterIvr router = new RouterIvr();
		ResultSet rs = null;
		ConnectionDB conn = null;
		PreparedStatement stm = null;
		try {
			conn = new ConnectionDB();
			String query = "select r.context, f.id from flow.router r, flow.form f where f.name = formname and r.dnis = '"+dnis+"'";

			rs = conn.ExecuteQuery(query);

			while (rs.next()) {
				router.setContext(rs.getString(1));
				router.setNextFormId(rs.getLong(2));
			}
		} catch (SQLException e) {
			logger.error("Erro ao router ", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.finalize();
		}
		return router;
	}

}
