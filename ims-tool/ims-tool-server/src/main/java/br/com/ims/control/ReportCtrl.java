package br.com.ims.control;

import java.util.List;

import br.com.ims.persistence.ParametersDao;
import br.com.ims.persistence.ReportDao;
import br.com.ims.tool.entity.Controlpanel;

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
	

}
