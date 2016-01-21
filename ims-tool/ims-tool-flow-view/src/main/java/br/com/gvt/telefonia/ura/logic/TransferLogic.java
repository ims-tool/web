package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.Transfer;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class TransferLogic {
	public static void process(Form form,List<FluxElement> listElements, FluxElement parent)
	{
		Transfer transfer = SingletonDAO.getTransferDAOInstance().findByPk(Long.toString(form.getFormid()));
		
		FluxElement element = new FluxElement();
		element.setId(transfer.getId());
		element.setName(transfer.getName());
		element.setDescription(transfer.getDescription());
		element.setNext(form.getNextFormDefault());
		element.setTag(element.getTag());
		element.setForm(form.getId());
		element.setType(Transfer.class.getSimpleName());
		element.setNextFormDefault(form.getNextFormDefault());
		element.setFormName(form.getName());
		
		if(listElements.size() > 0)
			if(listElements.size() > 0)
				if(parent != null)
					element.setParent(parent);
		
		if(element.getId() > 0){
			listElements.add(element);
			try{
				FormLogic next = new FormLogic(element.getNext(),listElements,element);
				next.loadFormType();
			} catch(Exception e){}
		}
	}
}
