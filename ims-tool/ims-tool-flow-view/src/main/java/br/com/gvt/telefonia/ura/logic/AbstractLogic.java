package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Entity;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.DaoFactory;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.FluxException;
import br.com.gvt.telefonia.ura.util.Reflection;

public abstract class AbstractLogic {
	
	public static String className(){
		return null;
	}
	
	public static void process(Form form,List<FluxElement> listElements, FluxElement parent) throws Exception
	{
		try{
			Entity<?> entity = DaoFactory.getInstance().getDao(className()).findByPk(Long.toString(form.getFormid()));
			
			FluxElement element = new FluxElement();
			element.setId( Long.parseLong(Reflection.invokeGetMethodObject(entity,"getId").toString()) );
			element.setDescription( Reflection.invokeGetMethodObject(entity,"getDescription").toString() );
			element.setNext(form.getNextFormDefault());
			element.setTag(element.getTag());
			element.setForm(form.getId());
			element.setType(className());
			element.setNextFormDefault(form.getNextFormDefault());
			element.setFormName(form.getName());
			
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
			throw new Exception(className() + " " +form.getFormid());
		}
	}
	
}
