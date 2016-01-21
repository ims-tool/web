package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Disconnect;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class DisconnectLogic {
	public static void process(Form form,List<FluxElement> listElements, FluxElement parent)
	{
		Disconnect disconnect = SingletonDAO.getDisconnectDAOInstance().findByPk(Long.toString(form.getFormid()));
		
		FluxElement element = new FluxElement();
		element.setId(disconnect.getId());
		element.setName(disconnect.getName());
		element.setDescription(disconnect.getDescription());
		element.setTag(disconnect.getTag());
		element.setType(Disconnect.class.getSimpleName());
		element.setFormName(form.getName());
		element.setNextFormDefault(form.getNextFormDefault());
		element.setAux("noNext");
		
		if(listElements.size() > 0)
			if(listElements.size() > 0)
				if(parent != null)
					element.setParent(parent);
		
		if(element.getId() > 0){
			listElements.add(element);
		}
	}
}
