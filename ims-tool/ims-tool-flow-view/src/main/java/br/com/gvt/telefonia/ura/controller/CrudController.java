package br.com.gvt.telefonia.ura.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.gvt.telefonia.ura.service.CrudService;

public class CrudController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private CrudService crud;
	
	public CrudController(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
		crud = new CrudService();
	}
	
	public void loadAction() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("loadElement"))
		{
			loadElement();
			
		} else if(action.equalsIgnoreCase("search")) {
			
			search();
			
		} else if(action.equalsIgnoreCase("searchNomatchInput")) {
		
			searchNoMatchInput();
		
		} else if(action.equalsIgnoreCase("loadAll")) {
			
			loadAll();
			
		} else if(action.equalsIgnoreCase("save"))
		{
			save();
			
		} else if(action.equalsIgnoreCase("saveElement")) {
			
			saveElement();
			
		} else if(action.equalsIgnoreCase("loadFullElement")) {
			
			loadFullElement();
			
		} else if(action.equalsIgnoreCase("loadAllNomatchinput")) {
			
			loadAllNomatchinput();
			
		}
	}
	
	public void loadElement() throws IOException
	{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(crud.loadElement(id,type));
	}
	
	public void search() throws IOException
	{
		String type = request.getParameter("type");
		String search = request.getParameter("q");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(crud.search(type,search));
	}
	
	public void searchNoMatchInput() throws IOException
	{
		String type = request.getParameter("type");
		String search = request.getParameter("q");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(crud.searchNoMatchInput(type,search));
	}
	
	public void loadAll() throws IOException
	{
		String type = request.getParameter("type");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(crud.loadAll(type));
	}
	
	public void save() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException 
	{
		String result = "";
		String type = request.getParameter("elementType");
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		result = crud.save(request);
		out.print(crud.loadElement(result, type));
		
	}
	
	public void saveElement() throws IOException
	{
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(crud.saveElement(request));
	}
	
	public void loadFullElement() throws IOException
	{
		String id = request.getParameter("id");
		String type = request.getParameter("type");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(crud.loadFullElement(id,type));
	}
	
	public void loadAllNomatchinput() throws IOException
	{
		String type = request.getParameter("type");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(crud.loadAllNomatchinput(type.toUpperCase()));		
	}
}
