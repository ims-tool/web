package br.com.ims.control;

import java.util.List;

import br.com.ims.persistence.LogAuditDao;
import br.com.ims.persistence.ServiceHourDao;
import br.com.ims.tool.entity.Audit;
import br.com.ims.tool.entity.ServiceHour;
import br.com.ims.tool.entity.ServiceHourType;

public class LogAuditCtrl {

	public static void saveLog(Audit logAudit) {
		LogAuditDao.save(logAudit);
	}
	
	

}
