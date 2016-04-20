package br.com.ims.dashboard.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.ims.dashboard.factory.DashboardFactory;
import br.com.ims.dashboard.model.Retencao;

/**
 * Servlet implementation class RelatorioAgentesServlet
 */
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static Logger log = Logger.getLogger(DashboardServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("title", "Cadastro de Log Ponto URA");
		String funct = request.getParameter("action") == null ? "vazio" : request.getParameter("action");
		if (funct.equalsIgnoreCase("vazio")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.html");
			try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
			}
		}
		if (funct.equalsIgnoreCase("buscaRetencao")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();
			HashMap<Integer,Retencao> listRetencao = dashboardController.verificaRetencao();
			/**
			 * mock
			 *
			HashMap<Integer,Retencao> listRetencao = new HashMap<Integer,Retencao>();
			Retencao ret = new Retencao();
			ret.setData("10:00");
			ret.setPorcentagem(new Double(32));
			listRetencao.put(0, ret);
			ret = new Retencao();
			ret.setData("10:10");
			ret.setPorcentagem(new Double(25));
			listRetencao.put(1, ret);
			ret = new Retencao();
			ret.setData("10:20");
			ret.setPorcentagem(new Double(20));
			listRetencao.put(2, ret);
			ret = new Retencao();
			ret.setData("10:30");
			ret.setPorcentagem(new Double(22));
			listRetencao.put(3, ret);
			/**
			 * fim mock
			 */
			String lista = formatarStringRetencao(listRetencao);
			outer.print(lista);
			
		}
		if (funct.equalsIgnoreCase("buscaWebService")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();
			HashMap<String,String> webService = dashboardController.verificaWebServices();
			/**
			 * mock
			 *
			HashMap<String,String> webService = new HashMap<String,String>();
			webService.put("OK", "95.05");
			webService.put("NOK", "4.95");
			/**
			 * fim mock		
			 */
			String lista = formatarStringWebService(webService);
			outer.print(lista);
			
		}
		if (funct.equalsIgnoreCase("buscaVolumeLigacaoMinuto")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();

			HashMap<String,Integer> volumeMinuto = dashboardController.verificaVolumeLigacaoMinuto();
			/**
			 * mock
			 *
			HashMap<String,Integer> volumeMinuto = new HashMap<String,Integer>();
			volumeMinuto.put("09/12/2015 10:00", 80);
			volumeMinuto.put("09/12/2015 10:01", 95);
			volumeMinuto.put("09/12/2015 10:02", 120);
			volumeMinuto.put("09/12/2015 10:03", 300);
			volumeMinuto.put("09/12/2015 10:04", 400);
			volumeMinuto.put("09/12/2015 10:05", 320);
			volumeMinuto.put("09/12/2015 10:06", 100);
			volumeMinuto.put("09/12/2015 10:07", 50);
			volumeMinuto.put("09/12/2015 10:08", 2);
			volumeMinuto.put("09/12/2015 10:09", 320);
			volumeMinuto.put("09/12/2015 10:10", 400);
			/**
			 * fim mock		
			 */
			String lista = formatarStringVolumeLigacaoMinuto(volumeMinuto);
			outer.print(lista);
			
		}
		if (funct.equalsIgnoreCase("buscaVolumeLigacaoURA")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();

			HashMap<String,Integer> volumeURA = dashboardController.verificaVolumeLigacaoURA();
			/**
			 * mock
			 *
			HashMap<String,Integer> volumeURA = new HashMap<String,Integer>();
			volumeURA.put("URA1", 1200);
			volumeURA.put("URA2", 5392);
			volumeURA.put("URA3", 504);
			/**
			 * fim mock		
			 */
			String lista = formatarStringVolumeLigacaoURA(volumeURA);
			outer.print(lista);
			
		}
			
		
	}
	
	
	private String formatarStringWebService(HashMap<String, String> webService) {
		String lista = "[[\"WebServices\", \"Porcentagem\"]";
		if(webService != null && webService.containsKey("OK") && webService.get("OK") != null) {
			lista = lista + ",[\"OK\", "+webService.get("OK").toString()+"]";
			lista = lista + ",[\"NOK\", "+webService.get("NOK").toString()+"]";
		}
		
		
		lista = lista + "]";
		
		return lista;
	}
	private String formatarStringRetencao(HashMap<Integer, Retencao> listRetencao) {
		String lista = "[[\"Data\", \"Porcentagem\", \"Media\"]";
		
		if(listRetencao != null) {
			for (int i = 0; i < listRetencao.size(); i++) {
				lista = lista + ",[\""+listRetencao.get(i).getData()+"\", "+listRetencao.get(i).getPorcentagem()+", 30]";
			}
		}
		lista = lista + "]";


		return lista;
	}
	private String formatarStringVolumeLigacaoMinuto(HashMap<String, Integer> volumeMinuto) {
		String lista = "[[\"Minuto\", \"Volume\"]";
		
		if(volumeMinuto != null) {
			for(java.util.Map.Entry<String, Integer> entry : volumeMinuto.entrySet() ){
				lista += ",[\""+entry.getKey()+"\","+entry.getValue()+"]";
			}
		}
		
		lista = lista + "]";


		return lista;
	}
	private String formatarStringVolumeLigacaoURA(HashMap<String, Integer> volumeURA) {
		String lista = "[[\"URA\", \"Volume\"]";
		
		if(volumeURA != null) {
			for(java.util.Map.Entry<String, Integer> entry : volumeURA.entrySet() ){
				lista += ",[\""+entry.getKey()+"\","+entry.getValue()+"]";
			}
		}
		
		lista = lista + "]";


		return lista;
	}


}