package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Announce;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.FluxException;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class AnnounceLogic {
	
	public static void process(Form form,List<FluxElement> listElements, FluxElement parent) throws Exception
	{
		try{
			Announce announce = SingletonDAO.getAnnounceDAOInstance().findByPk(Long.toString(form.getFormid()));
			
			FluxElement element = new FluxElement();
			element.setId(announce.getId());
			element.setDescription(announce.getDescription());
			element.setNext(announce.getNextform());
			element.setType(Announce.class.getSimpleName());
			element.setName(form.getName());
			element.setForm(form.getId());
			element.setTag(announce.getTag());
			element.setNextFormDefault(form.getNextFormDefault());
			element.setFormName(form.getName());
			
			if(element.getId() == 998)
				element.setAux("noNext");
			
			if(listElements.size() > 0)
					if(parent != null)
						element.setParent(parent);
			
			if(element.getId() > 0){
				listElements.add(element);
				FormLogic next = new FormLogic(element.getNext(),listElements,element);
				next.loadFormType();
			}
		} catch(FluxException e){
			throw new FluxException(e.getMessage());
		} catch(Exception e){
			throw new Exception("Announce "+form.getFormid());
		}
	}
	
}
