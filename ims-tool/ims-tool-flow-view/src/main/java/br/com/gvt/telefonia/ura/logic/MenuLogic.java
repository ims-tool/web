package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Audio;
import br.com.gvt.telefonia.ura.diagram.model.Choice;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.Menu;
import br.com.gvt.telefonia.ura.diagram.model.NoMatchInput;
import br.com.gvt.telefonia.ura.diagram.model.Prompt;
import br.com.gvt.telefonia.ura.diagram.model.PromptAudio;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class MenuLogic {
	
	public static void process(Form form,List<FluxElement> listElements, FluxElement parent) throws Exception
	{
		Audio audio = null;
		NoMatchInput noMatchInput = null;
		FormLogic nextNoInput = null;
		FormLogic nextNoMatch = null;
		
		FluxElement opcElement= new FluxElement();
		
		Menu obj = SingletonDAO.getMenuDAOInstance().findByPk(Long.toString(form.getFormid()));
		Prompt prompt = SingletonDAO.getPromptDAOInstance().findByPk(Long.toString(obj.getPrompt()));
		List<PromptAudio> listPromptAudio = SingletonDAO.getPromptAudioDAOInstance().findByPrompt(Long.toString( prompt.getId() ));
		 
		String description = "";
		for(PromptAudio promptAudioObj : listPromptAudio){
			if(promptAudioObj.getCondition() != null)
				description += "#<span class=\"red\">"+SingletonDAO.getConditionDAOInstance().findByPk(promptAudioObj.getCondition()).getName()+"#</span> -- ";
			
			audio = SingletonDAO.getAudioDAOInstance().findByPk(Long.toString(promptAudioObj.getAudio()));
			description += audio.getDescription()+"<br />";
			
		}
		List<Choice> listChoice = SingletonDAO.getChoiceDAOInstance().findByMenu(Long.toString(obj.getId()));
		
		FluxElement element= new FluxElement();
		element.setId(obj.getId());
		element.setName(obj.getName());
		element.setDescription(description);
		element.setType(Menu.class.getSimpleName());
		element.setForm(form.getId());
		element.setFormName(form.getName());
		element.setNextFormDefault(form.getNextFormDefault());
		
		if(parent != null)
			element.setParent(parent);
		
		if(element.getId() > 0){
			listElements.add(element);
		}
		
		for(Choice opc : listChoice)
		{
			opcElement = new FluxElement();
			opcElement.setId(opc.getId());
			
			if(opc.getCondition() != null){
				opcElement.setAux(opc.getCondition());
			}
			
			opcElement.setName(opc.getDtmf());
			opcElement.setDescription(opc.getDtmf());
			
			if(opc.getNextform() != null)
				opcElement.setNext(Long.parseLong(opc.getNextform()));
			
			opcElement.setTag(opc.getTag());
			if(element.getType().equalsIgnoreCase(Menu.class.getSimpleName()))
				opcElement.setParent(element);
			opcElement.setTag(opc.getTag());
			opcElement.setType(Choice.class.getSimpleName());
			
			if(opc.getNextform() != null)
				opcElement.setForm(Long.parseLong(opc.getNextform()));
			
			element.setFormName(form.getName());
			element.setNextFormDefault(form.getNextFormDefault());
			listElements.add(opcElement);
			
		}
		
		for(Choice opc : listChoice)
		{
			opcElement = new FluxElement();
			opcElement.setId(opc.getId());
			opcElement.setName(opc.getDtmf());
			opcElement.setDescription(opc.getDtmf());
			if(opc.getNextform() != null)
				opcElement.setNext(Long.parseLong(opc.getNextform()));
			opcElement.setTag(opc.getTag());
			
			if(opc.getCondition() != null){
				opcElement.setAux(opc.getCondition());
			}
			
			if(element.getType().equalsIgnoreCase(Menu.class.getSimpleName()))
				opcElement.setParent(element);
			
			opcElement.setTag(opc.getTag());
			opcElement.setType(Choice.class.getSimpleName());
			if(opc.getNextform() != null)
				opcElement.setForm(Long.parseLong(opc.getNextform()));
			opcElement.setFormName(form.getName());
			opcElement.setNextFormDefault(form.getNextFormDefault());
			
			FormLogic next = new FormLogic(opcElement.getNext(),listElements,opcElement);
			next.loadFormType();
			
		}
		
		//listChoice = null;
		
		noMatchInput = null;
		noMatchInput = SingletonDAO.getNoMatchInputDAOInstance().findByPk(Long.toString(obj.getNoinput()));
		
		noMatchInput.setName("aguardou");
		
		opcElement = new FluxElement();
		opcElement.setId(noMatchInput.getId());
		opcElement.setName(noMatchInput.getName());
		opcElement.setDescription(noMatchInput.getName());
		opcElement.setNext(noMatchInput.getNextform());
		opcElement.setTag(noMatchInput.getTag());
		opcElement.setParent(element);
		opcElement.setTag(noMatchInput.getTag());
		opcElement.setType(NoMatchInput.class.getSimpleName());
		opcElement.setForm(form.getId());
		opcElement.setFormName(form.getName());
		opcElement.setNextFormDefault(form.getNextFormDefault());
		listElements.add(opcElement);
		nextNoInput = new FormLogic(opcElement.getNext(),listElements,opcElement);
		nextNoInput.loadFormType();
		
		noMatchInput = SingletonDAO.getNoMatchInputDAOInstance().findByPk(Long.toString(obj.getNomatch()));
		opcElement = new FluxElement();
		noMatchInput.setName("X");
		opcElement.setId(noMatchInput.getId());
		opcElement.setName(noMatchInput.getName());
		opcElement.setDescription(noMatchInput.getName());
		opcElement.setNext(noMatchInput.getNextform());
		opcElement.setTag(noMatchInput.getTag());
		opcElement.setParent(element);
		opcElement.setTag(noMatchInput.getTag());
		opcElement.setType(NoMatchInput.class.getSimpleName());
		opcElement.setForm(form.getId());
		opcElement.setFormName(form.getName());
		opcElement.setNextFormDefault(form.getNextFormDefault());
		listElements.add(opcElement);
		nextNoMatch = new FormLogic(opcElement.getNext(),listElements,opcElement);
		nextNoMatch.loadFormType();
	}
}
