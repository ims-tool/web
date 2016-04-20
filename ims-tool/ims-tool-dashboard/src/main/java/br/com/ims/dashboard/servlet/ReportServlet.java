package br.com.ims.dashboard.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.ims.dashboard.dao.LogUraDAO;
import br.com.ims.dashboard.dao.TrackDetailDAO;
import br.com.ims.dashboard.dao.WsVolumeDAO;
import br.com.ims.dashboard.service.TrackDetailService;

/**
 * Servlet implementation class RelatorioAgentesServlet
 */
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static Logger log = Logger.getLogger(ReportServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportServlet() {
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
		request.setAttribute("title", "Report");
		String menu = request.getParameter("menu") == null ? "vazio" : request.getParameter("menu");
		SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		if (menu.equalsIgnoreCase("vazio")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.xhtml");
			try {
				
				dispatcher.forward(request, response);
			} catch (Exception e) {
			}
		}		
		if (menu.equalsIgnoreCase("report")) {
			String submenu = request.getParameter("submenu") == null ? "logura" : request.getParameter("submenu");
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/report/"+submenu+".xhtml");
			
			if(request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("consultar") ) {
				
				if(submenu.equalsIgnoreCase("logura")) {
					/**
					 * Recuperar filtros
					 */
					String datahoraI = request.getParameter("datahoraI") == null || request.getParameter("datahoraI").length() == 0 ? formatDateTime.format(Calendar.getInstance().getTime()) :  request.getParameter("datahoraI").toString();
					String datahoraF = request.getParameter("datahoraF") == null || request.getParameter("datahoraF").length() == 0 ? formatDateTime.format(Calendar.getInstance().getTime()) :  request.getParameter("datahoraF").toString();
					String telefone = request.getParameter("telefone") == null || request.getParameter("telefone").length() == 0 ? "TODOS" :  request.getParameter("telefone").toString(); 
					String dnis = request.getParameter("dnis") == null || request.getParameter("dnis").length() == 0 ? "TODOS" :  request.getParameter("dnis").toString();
					String flowName = request.getParameter("flowName") == null || request.getParameter("flowName").length() == 0 ? "TODOS" :  request.getParameter("flowName").toString();
					String formulario = request.getParameter("formulario") == null || request.getParameter("formulario").length() == 0 ? "TODOS" :  request.getParameter("formulario").toString(); 
					String finalizacao = request.getParameter("finalizacao") == null || request.getParameter("finalizacao").length() == 0 ? "TODOS" :  request.getParameter("finalizacao").toString(); 
					String tags = request.getParameter("tags") == null || request.getParameter("tags").length() == 0 ? "0" :  request.getParameter("tags").toString(); 
					String vdn = request.getParameter("vdn") == null || request.getParameter("vdn").length() == 0 ? "TODOS" :  request.getParameter("vdn").toString();
					

					LogUraDAO dao = new LogUraDAO();
					if(!flowName.equals("TODOS")) {
						dnis = dao.getDnisByName(flowName);
					}
					
					request.setAttribute("datahoraI", datahoraI);
					request.setAttribute("datahoraF", datahoraF);
					request.setAttribute("telefone", telefone);
					request.setAttribute("dnis", dnis);
					request.setAttribute("formulario", formulario);
					request.setAttribute("finalizacao", finalizacao);
					request.setAttribute("tags", tags);
					request.setAttribute("vdn", vdn);
					
					
					request.setAttribute("logList", dao.getLogUra(datahoraI,datahoraF,telefone,dnis,formulario,finalizacao,tags,vdn));
				}
				else if(submenu.equalsIgnoreCase("wsvolume")) {
					/**
					 * Recuperar filtros
					 */
					String datahoraI = request.getParameter("datahoraI") == null || request.getParameter("datahoraI").length() == 0 ? formatDateTime.format(Calendar.getInstance().getTime()) :  request.getParameter("datahoraI").toString();
					String datahoraF = request.getParameter("datahoraF") == null || request.getParameter("datahoraF").length() == 0 ? formatDateTime.format(Calendar.getInstance().getTime()) :  request.getParameter("datahoraF").toString();
					String limite_timeout = request.getParameter("limite_timeout") == null || request.getParameter("limite_timeout").length() == 0 ? "0" :  request.getParameter("limite_timeout").toString(); 
					String num_chamadas_minima = request.getParameter("num_chamadas_minima") == null || request.getParameter("num_chamadas_minima").length() == 0 ? "0" :  request.getParameter("num_chamadas_minima").toString(); 
					
					request.setAttribute("datahoraI", datahoraI);
					request.setAttribute("datahoraF", datahoraF);
					request.setAttribute("limite_timeout", limite_timeout);
					request.setAttribute("num_chamadas_minima", num_chamadas_minima);
					
					
					WsVolumeDAO dao = new WsVolumeDAO();
	
					request.setAttribute("wsList", dao.getWsVolume(datahoraI, datahoraF, limite_timeout, num_chamadas_minima)) ;
				} else if(submenu.equalsIgnoreCase("trackdetail")) {
					/**
					 * Recuperar filtros
					 */
					String logId = request.getParameter("logid") == null ? "0" :  request.getParameter("logid").toString();
					
					request.setAttribute("logid", logId);
					
					TrackDetailService service = new TrackDetailService();
					TrackDetailDAO dao = new TrackDetailDAO(); 
	
					request.setAttribute("context",service.getContext(logId));
					request.setAttribute("checklist",dao.getCheckList(logId));
					request.setAttribute("trackdetail",dao.getTrackDetail(logId));
					
				}
			}	
			try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
			
		
	}
	
	


}