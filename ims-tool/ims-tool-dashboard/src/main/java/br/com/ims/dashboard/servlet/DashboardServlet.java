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
		if (funct.equalsIgnoreCase("buscaWebServiceTimeout")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();
			HashMap<String,String> webService = dashboardController.verificaWebServicesTimeout();
			/**
			 * mock
			 *
			HashMap<String,String> webService = new HashMap<String,String>();
			webService.put("OK", "95.05");
			webService.put("NOK", "4.95");
			/**
			 * fim mock		
			 */
			String lista = formatarStringWebServiceTimeout(webService);
			outer.print(lista);
			
		}
		if (funct.equalsIgnoreCase("buscaWebServiceStatus")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();
			HashMap<String,Integer> webService = dashboardController.verificaWebServicesStatus();
			
			String lista = formatarStringWebServiceStatus(webService);
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
		if (funct.equalsIgnoreCase("getVolumeLigacaoEstado")) {
			String minutes = request.getParameter("minutes") == null ? "15" : request.getParameter("minutes");
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();

			HashMap<String,Integer> volumeLigacaoEstado = dashboardController.getVolumeLigacaoEstado(minutes);
			
			String lista = formatarStringVolumeLigacaoEstado(volumeLigacaoEstado);
			outer.print(lista);
			
		}
		if (funct.equalsIgnoreCase("getAcumulado")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();

			HashMap<String,Integer> acumulado = dashboardController.getAcumulado();
			
			String lista = formatarStringAcumulado(acumulado);
			outer.print(lista);
		}
		if (funct.equalsIgnoreCase("getMenuPrincipalUltimaHora")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();

			HashMap<String,Integer> resultado = dashboardController.getMenuPrincipalUltimaHora();
			
			String lista = formatarStringMenuPrincipal(resultado);
			outer.print(lista);
		}			
		if (funct.equalsIgnoreCase("getMenuPrincipalAcumuladoDia")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();

			HashMap<String,Integer> resultado = dashboardController.getMenuPrincipalAcumuladoDia();
			
			String lista = formatarStringMenuPrincipal(resultado);
			outer.print(lista);
		}			
		if (funct.equalsIgnoreCase("getFinalizacao")) {
			PrintWriter outer = response.getWriter();
			DashboardFactory dashboardController = new DashboardFactory();
			outer.print(dashboardController.getFinalizacao());
		}						
	}
	
	
	private String formatarStringWebServiceTimeout(HashMap<String, String> webService) {
		String lista = "[[\"WebServices\", \"Porcentagem\"]";
		if(webService != null && webService.containsKey("OK") && webService.get("OK") != null) {
			lista = lista + ",[\"OK\", "+webService.get("OK").toString()+"]";
			lista = lista + ",[\"TIMEOUT\", "+webService.get("NOK").toString()+"]";
		}
		
		
		lista = lista + "]";
		
		return lista;
	}
	private String formatarStringWebServiceStatus(HashMap<String, Integer> webService) {
		String lista = "[[\"Status\", \"Qtde\"]";
		if(webService != null) {
			for(java.util.Map.Entry<String, Integer> entry : webService.entrySet() ){
				lista += ",[\""+entry.getKey()+"\","+entry.getValue()+"]";
			}
			
		}
		lista = lista + "]";
				
		return lista;
	}
	private String formatarStringRetencao(HashMap<Integer, Retencao> listRetencao) {
		String lista = "[[\"Data\", \"Porcentagem\", \"Meta\"]";
		
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

	private String formatarStringVolumeLigacaoEstado(HashMap<String, Integer> volumeURA) {
		
		HashMap<String, String> localidades = new HashMap<>();
		localidades.put("11", "São Paulo");
		localidades.put("12", "S. José dos Campos");
		localidades.put("13", "Santos");
		localidades.put("14", "Baurú");
		localidades.put("15", "Sorocaba");
		localidades.put("16", "Ribeirão Preto");
		localidades.put("17", "S. José do Rio Preto");
		localidades.put("18", "Presidente Prudente");
		localidades.put("19", "Campinas");
		localidades.put("21", "Rio de Janeiro");
		localidades.put("22", "Campos");
		localidades.put("24", "Volta Redonda");
		localidades.put("27", "Vitória");
		localidades.put("28", "Cach. de Itapemirim");
		localidades.put("31", "Belo Horizonte");
		localidades.put("32", "Juiz de Fora");
		localidades.put("33", "Gov. Valadares");
		localidades.put("34", "Uberlândia");
		localidades.put("35", "Varginha");
		localidades.put("37", "Divinópolis");
		localidades.put("38", "Montes Claros");
		localidades.put("41", "Curitiba");
		localidades.put("42", "Ponta Grossa");
		localidades.put("43", "Londrina");
		localidades.put("44", "Maringá");
		localidades.put("45", "Foz do Iguaçú");
		localidades.put("46", "Pato Branco");
		localidades.put("47", "Joinville");
		localidades.put("48", "Florianópolis");
		localidades.put("49", "Chapecó");
		localidades.put("51", "Porto Alegre");
		localidades.put("53", "Pelotas");
		localidades.put("54", "Caxias do Sul");
		localidades.put("55", "Santa Maria");
		localidades.put("61", "Brasília*");
		localidades.put("62", "Goiânia");
		localidades.put("63", "Palmas");
		localidades.put("64", "Rio Verde");
		localidades.put("65", "Cuiabá");
		localidades.put("66", "Rondonópolis");
		localidades.put("67", "Campo Grande");
		localidades.put("68", "Rio Branco");
		localidades.put("69", "Porto Velho");
		localidades.put("71", "Salvador");
		localidades.put("73", "Ilhéus");
		localidades.put("74", "Juazeiro");
		localidades.put("75", "Feira de Santana");
		localidades.put("77", "Barreiras");
		localidades.put("79", "Aracajú");
		localidades.put("81", "Recife");
		localidades.put("82", "Maceió");
		localidades.put("83", "João Pessoa");
		localidades.put("84", "Natal");
		localidades.put("85", "Fortaleza");
		localidades.put("86", "Teresina");
		localidades.put("87", "Petrolina");
		localidades.put("88", "Juazeiro do Norte");
		localidades.put("89", "Picos");
		localidades.put("91", "Belém");
		localidades.put("92", "Manaus");
		localidades.put("93", "Santarém");
		localidades.put("94", "Marabá");
		localidades.put("95", "Boa Vista");
		localidades.put("96", "Macapá");
		localidades.put("97", "Coari");
		localidades.put("98", "São Luis");
		localidades.put("99", "Imperatriz");
		
		
		String lista = "[[\"Cidade\", \"DDD\", \"Qtde\"]";
		
		if(volumeURA != null) {
			for(java.util.Map.Entry<String, Integer> entry : volumeURA.entrySet() ){
				
				lista += ",[\""+localidades.get(entry.getKey())+"\","+entry.getKey()+","+entry.getValue()+"]";
			}
		}
		
		lista = lista + "]";


		return lista;
	}

	private String formatarStringAcumulado(HashMap<String, Integer> volumeURA) {
		String lista = "[[\"Data\", \"Volume\"]";
		
		if(volumeURA != null) {
			for(java.util.Map.Entry<String, Integer> entry : volumeURA.entrySet() ){
				lista += ",[\""+entry.getKey()+"\","+entry.getValue()+"]";
			}
		}
		
		lista = lista + "]";


		return lista;
	}
	
	private String formatarStringMenuPrincipal(HashMap<String, Integer> volume) {
		String lista = "[[\"Opção\", \"Qtde\"]";
		
		if(volume != null) {
			for(java.util.Map.Entry<String, Integer> entry : volume.entrySet() ){
				lista += ",[\""+entry.getKey()+"\","+entry.getValue()+"]";
			}
		}
		
		lista = lista + "]";


		return lista;
	}
	
}