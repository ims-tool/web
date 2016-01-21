package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.Operation;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class OperationLogic {
	
	public static void process(Form form,List<FluxElement> listElements, FluxElement parent) throws Exception
	{
		Operation operation = SingletonDAO.getOperationDAOInstance().findByPk(Long.toString(form.getFormid()));
		
		FluxElement element = new FluxElement();
		element.setId(operation.getId());
		
		if(listElements.size() > 0)
			if(parent != null)
				element.setParent(parent);
		
		element.setName(operation.getDescription());
		element.setDescription(operation.getDescription());
		element.setNext(operation.getNextFormid());
		element.setNextFormDefault(form.getNextFormDefault());
		element.setType(Operation.class.getSimpleName());
		element.setForm(form.getId());
		if(element.getId() > 0){
			listElements.add(element);
			FormLogic next = new FormLogic(element.getNext(),listElements,element);
			next.loadFormType();
		}
	}
}
