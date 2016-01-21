package br.com.gvt.telefonia.ura.diagram.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.gvt.telefonia.ura.controller.DiagramController;
import br.com.gvt.telefonia.ura.util.NovaUraSingleton;


/**
 * Servlet implementation class Main
 */
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void level(HttpServletRequest request)
    {
    	try{
			String levelStr = request.getParameter("level");
			int level = Integer.parseInt(levelStr);
			if(level > 0)
				NovaUraSingleton.getInstance().setLevel(level);
    	} catch(Exception e){}
    }
    
    private void tempo(HttpServletRequest request)
    {
    	try{
    		NovaUraSingleton.getInstance().setTempo(request.getParameter("tempo"));
    	} catch(Exception e){}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		level(request);
		tempo(request);
		
		DiagramController controller = new DiagramController(request,response);
		String action = request.getParameter("action");
		
		try{
			if(action != null)
				controller.loadAction();
			else
				controller.loadDiagram();
			
		} catch(Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		DiagramController controller = new DiagramController(request,response);
		controller.loadAction();
		
		/*
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		FluxoService fluxoService = new FluxoService();
		HashMap<String,String> parameter;
		
		if(action.equalsIgnoreCase("saveForm"))
		{
			parameter = new HashMap<String,String> ();
			Enumeration<String> names = request.getParameterNames();
			while(names.hasMoreElements()){
				String key = names.nextElement();
				parameter.put(key, request.getParameter(key));
			}
			
			PrintWriter out = response.getWriter();
			String result = fluxoService.saveForm(parameter);
			out.print(result);
		}*/
	}

}
