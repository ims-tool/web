package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Flow;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.NovaUraSingleton;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class FlowLogic {
	public static void process(Form form,List<FluxElement> listElements, FluxElement parent) throws Exception
	{
		Flow flow = SingletonDAO.getFlowDAOInstance().findByPk(Long.toString(form.getFormid()));
		
		
		Form formAux = SingletonDAO.getFormDAOInstance().findByName(flow.getFlowname());
		
		FluxElement element = new FluxElement();
		element.setId(flow.getId());
		element.setName(flow.getDescription());
		element.setDescription(flow.getDescription());
		
		// se for o primeiro elemento da lista abre o flow (flowname) se não segue para o nextform
		/*if(listElements.size() > 0){
			if(flow.getNextForm() != null)
				element.setNext(Long.parseLong(flow.getNextForm()));
		} else */
		
		if(NovaUraSingleton.getInstance().getOpenFlow())
			element.setNext(formAux.getId());
		else if(form.getFormType() == Form.FT_FLOWINTERNAL)
			element.setNext(Long.parseLong(flow.getNextForm()));
		else
			element.setAux("flowExternal");
		
		element.setType(Flow.class.getSimpleName());
		element.setForm(form.getId());
		element.setFormName(form.getName());
		element.setNextFormDefault(form.getNextFormDefault());
		
		if(listElements.size() > 0){
				if(parent != null)
					element.setParent(parent);
		}  else {
			element.setDescription(form.getName()+ "<br />" + flow.getDescription());
		}
		
		if( element.getId() > 0 ){
			
			listElements.add(element);
			
			if(form.getFormType() == Form.FT_FLOWINTERNAL)
			{
				FormLogic next = new FormLogic(element.getNext(),listElements,element);
				next.loadFormType();
			} else {
				FluxElement fluxElement = new FluxElement();
				fluxElement.setName(flow.getFlowname());
				fluxElement.setDescription(flow.getFlowname());
				fluxElement.setForm(-1);
				fluxElement.setFormName(flow.getFlowname());
				fluxElement.setNext(-1);
				fluxElement.setParent(element);
				listElements.add(fluxElement);
			}
		}
	}
}
