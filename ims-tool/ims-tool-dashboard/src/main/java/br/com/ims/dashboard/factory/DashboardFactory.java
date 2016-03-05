package br.com.ims.dashboard.factory;

import java.util.HashMap;

import br.com.ims.dashboard.dao.DashboardDAO;
import br.com.ims.dashboard.model.Retencao;

public class DashboardFactory {


	public HashMap<Integer,Retencao> verificaRetencao() {
		DashboardDAO dao = new DashboardDAO();
		return dao.retornaRetencao();
	}
	
	public HashMap<String,String> verificaWebServices() {
		DashboardDAO dao = new DashboardDAO();
		return dao.getHealthWebServices();
	}
	
	public HashMap<String,Integer> verificaVolumeLigacaoMinuto() {
		DashboardDAO dao = new DashboardDAO();		
		return dao.retornaVolumeLigacaoMinuto();
	}
	public HashMap<String,Integer> verificaVolumeLigacaoURA() {
		DashboardDAO dao = new DashboardDAO();		
		return dao.retornaVolumeLigacaoURA();
	}

}
