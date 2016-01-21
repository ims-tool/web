package br.com.gvt.telefonia.ura.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.gvt.telefonia.ura.bussiness.FluxoBussiness;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.Router;
import br.com.gvt.telefonia.ura.service.FluxoService;
import br.com.gvt.telefonia.ura.util.NovaUraSingleton;

public class DiagramController {
	
	private FluxoService fluxoService;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public DiagramController(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
		this.fluxoService = new FluxoService();
	}
	
	public void loadAction() throws IOException, ServletException
	{
		NovaUraSingleton.getInstance().setOpenFlow(false);
		
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("loadForms"))
			loadForms();
			
		else if (action.equalsIgnoreCase("export")) 
			export();
			
		else if(action.equalsIgnoreCase("loadElement"))
			loadElement();
			
		else if(action.equalsIgnoreCase("loadFormFields"))
			loadFormFields();
			
		else if(action.equalsIgnoreCase("saveForm"))
			saveForm();
		else if(action.equalsIgnoreCase("saveNextForm"))
			saveNextForm();
		else if(action.equalsIgnoreCase("saveNextFormDefault"))
			saveNextFormDefault();
		else if(action.equalsIgnoreCase("showCondition"))
			showCondition();
		else if(action.equalsIgnoreCase("showFlow"))
			showFlow();
			
	}
	
	public void loadForms() throws IOException
	{
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(fluxoService.loadForms());
	}
	
	public void export() 
	{
		try{
			String form = request.getParameter("id");
			response.setContentType("application/force-download");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition","attachment; filename=\"diagrama.graphml\"");
			
			PrintWriter out = response.getWriter();
			FluxoBussiness fluxo = new FluxoBussiness();
			out.print(fluxo.loadFluxoYed(form));
		} catch(Exception e){
			e.getStackTrace();
		}
	}
	
	public void loadElement() throws IOException 
	{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String formidParam = request.getParameter("formid");
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(fluxoService.loadElement(id,type,formidParam));
	}
	
	public void loadFormFields() throws IOException 
	{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String operation = request.getParameter("operation");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(fluxoService.loadFormFields(id,type,operation));
	}
	
	public void saveForm() throws IOException 
	{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String operation = request.getParameter("operation");
		PrintWriter out = response.getWriter();
		out.print(fluxoService.saveForm(id,type,operation));
	}
	
	public void saveNextForm() throws IOException 
	{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String nextform = request.getParameter("nextform");
		String version = request.getParameter("version");
		
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(fluxoService.saveNextForm(id,type,nextform,version));
	}
	
	public void saveNextFormDefault() throws IOException 
	{
		String id = request.getParameter("id");
		String nextform = request.getParameter("nextform");
		String version = request.getParameter("version");
		
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(fluxoService.saveNextFormDefault(id,nextform,version));
	}

	public void loadDiagram() throws ServletException, IOException {
		
		List<Form> parentForm = null;
		List<Router> routerList = null;
		
		String diagram = "";
		String viewPath = "/diagram.jsp";
		String formId = "";
		formId = request.getParameter("form");
		
		String base = request.getParameter("base");
		
		try{
		if(base != null)
			NovaUraSingleton.getInstance().setDatasource(base);
		
			if(formId != null){
				request.setAttribute("formId",formId);
				
				diagram = fluxoService.loadFluxo(Long.parseLong(formId));
				parentForm = fluxoService.loadParent(formId);
				diagram = diagram.replace("�", "");
				
			}else{
				viewPath = "/home.jsp";
				routerList = fluxoService.loadRouters();
			}
			
		} catch(Exception e){
			diagram = "0[Formulário " + e.getMessage() + " não encontrado];style 0 fill:#FFB2B2;";
		}
		
		request.setAttribute("diagram",diagram);
		request.setAttribute("parentForm",parentForm);
		request.setAttribute("formId",formId);
		request.setAttribute("routerList",routerList);
		request.getRequestDispatcher(viewPath).forward(request, response);
		
	}
	
	public void showCondition()
	{
		String diagram = null;
		String viewPath = "/diagramCondition.jsp";
		String id = request.getParameter("condition");
		
		try{
			
			diagram = fluxoService.loadCondition(id);
			request.setAttribute("diagram",diagram);
			request.getRequestDispatcher(viewPath).forward(request, response);
			
		} catch( Exception e ){
			
			e.getStackTrace();
		}
	}
	
	public void showFlow() throws ServletException, IOException
	{
		
		NovaUraSingleton.getInstance().setOpenFlow(true);
		loadDiagram();
	}
}
