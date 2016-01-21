package br.com.gvt.telefonia.ura.diagram.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.gvt.telefonia.ura.controller.CrudController;

/**
 * Servlet implementation class Crud
 */
public class Crud extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Crud() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		CrudController crud = new CrudController(request,response);
		try{
			if(request.getParameter("action") != null)
				crud.loadAction();
		} catch(Exception e){
			e.getStackTrace();
		}
		//request.getRequestDispatcher("/crud.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		CrudController crud = new CrudController(request,response);
		
		try{
			if(request.getParameter("action") != null)
				crud.loadAction();
		} catch(Exception e){
			e.getStackTrace();
		}
	}

}
