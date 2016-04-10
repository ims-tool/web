package br.com.ims.control;

import java.util.List;

import br.com.ims.persistence.MessageDao;
import br.com.ims.persistence.RouterDao;
import br.com.ims.tool.entity.Message;
import br.com.ims.tool.entity.RouterIvr;

public class RouterCtrl {

	public static RouterIvr getIvr(String dnis) {
		return RouterDao.getIvr(dnis);
		
	}
	

}
