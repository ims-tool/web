package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Parameters;
import service.ParametersService;


@Controller
@RequestMapping("/parameters")
public class ParametersController {
	
	
	@Autowired
	ParametersService service;
	
	@RequestMapping()
	public String index(HttpServletRequest request, ModelMap model) {
		
		List<Parameters> listParameters = service.getAll();
		model.addAttribute(Constants.PARAM_PARAMETERS, listParameters);			
		model.addAttribute(Constants.PARAM_TITLE, Constants.TITULO_PARAMETRO_LISTAGEM);
		
		return Constants.TELA_PARAMETRO_LISTAGEM;
	}
	
	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public String editar(HttpServletRequest request, ModelMap model, String msgErro,
			@PathVariable("id") Long id) {
		
		List<Parameters> listParameters = service.getAll();
		model.addAttribute(Constants.PARAM_PARAMETERS, listParameters);
		
		Parameters parameters = service.getById(id);
		model.addAttribute(Constants.PARAM_PARAMETER_ROW, parameters);
		return Constants.TELA_PARAMETERS_EDICAO;
	}
	
	@RequestMapping(value = "/editar/{id}", method = RequestMethod.POST)
	public String salvar(HttpServletRequest request, ModelMap model, String msgErro,
			@PathVariable("id") Long id) {
		
		List<Parameters> listParameters = service.getAll();
		model.addAttribute(Constants.PARAM_PARAMETERS, listParameters);
		model.addAttribute(Constants.PARAM_TITLE, Constants.TITULO_PARAMETERS_EDICAO);
		Parameters parameters  = service.getById(id);
			
		
		parameters.setName(request.getParameter("name"));
		parameters.setDescription(request.getParameter("description"));
		parameters.setType(request.getParameter("type"));
		parameters.setValue(request.getParameter("value"));
			
		if(service.save(parameters)){
			request.getSession().setAttribute("successMessage", "Parametro salvo com sucesso!");
			return "redirect:/parameters";
		} else {
			request.getSession().setAttribute("errorMessage", "Erro ao salvar o parâmetro");
			model.addAttribute(Constants.PARAM_TITLE, Constants.TITULO_PARAMETERS_EDICAO);
			return Constants.TELA_PARAMETERS_EDICAO;
		}

	}
		
}
