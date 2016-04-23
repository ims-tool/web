package br.com.ims.control;

import java.util.Date;
import java.util.List;

import br.com.ims.persistence.ParametersDao;
import br.com.ims.persistence.ReportDao;
import br.com.ims.tool.entity.CallLog;
import br.com.ims.tool.entity.Controlpanel;
import br.com.ims.tool.entity.ReportLog;

public class ReportCtrl {

	public static List<String> getTypeControlPanel() {
		
		return ReportDao.getTypeControlPanel();
		
	}

	public static List<Controlpanel> getControlPanelList(String group) {
		return ReportDao.getControlPanelList(group);
	}

	public static void save(Controlpanel cp) {
		ReportDao.save(cp);
		
	}

	public static List<String> getArtifactList(Date dateLog) {
		return ReportDao.getArtifactList(dateLog);
	}

	public static List<ReportLog> getLogList(Date dateLog, String artifact) {
		return ReportDao.getLogList(dateLog, artifact);
	}

	public static List<CallLog> getCallLogList(String datei, String datef, String ani, String dnis) {
		return ReportDao.getCallLogList(datei, datef, ani, dnis);
	}
	

}
