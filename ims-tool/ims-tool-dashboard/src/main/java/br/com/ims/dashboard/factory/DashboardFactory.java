package br.com.ims.dashboard.factory;

import java.util.HashMap;

import br.com.ims.dashboard.dao.DashboardDAO;
import br.com.ims.dashboard.model.Retencao;

public class DashboardFactory {


	public HashMap<Integer,Retencao> verificaRetencao() {
		DashboardDAO dao = new DashboardDAO();
		return dao.retornaRetencao();
	}
	
	public HashMap<String,String> verificaWebServicesTimeout() {
		DashboardDAO dao = new DashboardDAO();
		return dao.getWebServiceTimeout();
	}
	public HashMap<String,Integer> verificaWebServicesStatus() {
		DashboardDAO dao = new DashboardDAO();
		return dao.getWebServiceStatus();
	}
	
	public HashMap<String,Integer> verificaVolumeLigacaoMinuto() {
		DashboardDAO dao = new DashboardDAO();		
		return dao.retornaVolumeLigacaoMinuto();
	}
	public HashMap<String,Integer> verificaVolumeLigacaoURA() {
		DashboardDAO dao = new DashboardDAO();		
		return dao.retornaVolumeLigacaoURA();
	}
	public HashMap<String,Integer> getVolumeLigacaoEstado(String minutes) {
		DashboardDAO dao = new DashboardDAO();		
		return dao.getVolumeLigacaoEstado(minutes);
	}
	public HashMap<String,Integer> getAcumulado() {
		DashboardDAO dao = new DashboardDAO();		
		return dao.retornaAcumulado();
	}
	public HashMap<String,Integer> getMenuPrincipalUltimaHora() {
		DashboardDAO dao = new DashboardDAO();		
		return dao.retornaMenuPrincipalUltimaHora();
	}
	public HashMap<String,Integer> getMenuPrincipalAcumuladoDia() {
		DashboardDAO dao = new DashboardDAO();		
		return dao.retornaMenuPrincipalAcumuladoDia();
	}
	public String getFinalizacao() {
		DashboardDAO dao = new DashboardDAO();		
		return dao.retornaFinalizacao();
	}

}
