package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Audio;
import br.com.gvt.telefonia.ura.diagram.model.Choice;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.NoMatchInput;
import br.com.gvt.telefonia.ura.diagram.model.Prompt;
import br.com.gvt.telefonia.ura.diagram.model.PromptAudio;
import br.com.gvt.telefonia.ura.diagram.model.PromptCollect;
import br.com.gvt.telefonia.ura.util.Errors;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class PromptCollectLogic {
	
	public static void process(Form form,List<FluxElement> listElements, FluxElement parent) throws Exception
	{
		
		Prompt prompt = null;
		Choice choice = null;
		Audio audio = null;
		FluxElement opcElement = null;
		NoMatchInput noMatchInput = null;
		
		FormLogic nextNoInput = null;
		FormLogic nextNoMatch = null;
		
		List<Choice> listChoice = null;
		List<PromptAudio> listPromptAudio  = null;
		
		PromptCollect promptCollect = SingletonDAO.getPromptCollectDAOInstance().findByPk(Long.toString(form.getFormid()));
		if(!Errors.getInstance().validate(promptCollect, form))
			return;
		
		prompt = SingletonDAO.getPromptDAOInstance().findByPk(Long.toString(promptCollect.getPrompt()));
		listPromptAudio = SingletonDAO.getPromptAudioDAOInstance().findByPrompt(Long.toString( prompt.getId() ));
		
		String descriptionCollect = "";
		for(PromptAudio promptAudioObj : listPromptAudio){
			audio = SingletonDAO.getAudioDAOInstance().findByPk(Long.toString(promptAudioObj.getAudio()));
			
			if(!Errors.getInstance().validate(audio, promptAudioObj))
				return;
				
			descriptionCollect += audio.getDescription()+"<br />";
			
		}
		listChoice = SingletonDAO.getChoiceDAOInstance().findByMenu(Long.toString(promptCollect.getId()));
		
		FluxElement element = new FluxElement();
		
		element.setId(promptCollect.getId());
		element.setName(promptCollect.getName());
		element.setDescription(descriptionCollect);
		element.setType(PromptCollect.class.getSimpleName());
		element.setNext(promptCollect.getNextform());
		element.setForm(form.getId());
		if(parent != null)
			element.setParent(parent);
		
		if(element.getId() > 0){
			listElements.add(element);
			FormLogic next = new FormLogic(element.getNext(),listElements,element);
			next.loadFormType();
		}
		
		for(Choice opc : listChoice)
		{
			opcElement = new FluxElement();
			opcElement.setId(opc.getId());
			opcElement.setName(opc.getName());
			opcElement.setDescription(opc.getName());
			opcElement.setNext(Long.parseLong(opc.getNextform()));
			opcElement.setTag(opc.getTag());
			opcElement.setParent(element);
			opcElement.setTag(opc.getTag());
			opcElement.setType(Choice.class.getSimpleName());
			listElements.add(opcElement);
			
			FormLogic next = new FormLogic(opcElement.getNext(),listElements,opcElement);
			next.loadFormType();
		}
		
		listChoice = null;
		
		
		noMatchInput = null;
		noMatchInput = SingletonDAO.getNoMatchInputDAOInstance().findByPk(Long.toString(promptCollect.getNoinput()));
		noMatchInput.setName("aguardou");
		
		opcElement = new FluxElement();
		opcElement.setId(noMatchInput.getId());
		opcElement.setForm(form.getId());
		opcElement.setName(noMatchInput.getName());
		opcElement.setDescription(noMatchInput.getName());
		opcElement.setNext(noMatchInput.getNextform());
		opcElement.setTag(noMatchInput.getTag());
		opcElement.setParent(element);
		opcElement.setTag(noMatchInput.getTag());
		opcElement.setType(NoMatchInput.class.getSimpleName());
		listElements.add(opcElement);
		nextNoInput = new FormLogic(opcElement.getNext(),listElements,opcElement);
		nextNoInput.loadFormType();
		
		choice = SingletonDAO.getChoiceDAOInstance().findByPk(Long.toString(promptCollect.getNomatch()));
		if(choice != null){
			opcElement.setName("X");
			opcElement = new FluxElement();
			opcElement.setId(choice.getId());
			opcElement.setForm(form.getId());
			opcElement.setName(choice.getName());
			opcElement.setDescription(choice.getName());
			opcElement.setNext(Long.parseLong(choice.getNextform()));
			opcElement.setTag(choice.getTag());
			opcElement.setParent(element);
			opcElement.setTag(choice.getTag());
			opcElement.setType(Choice.class.getSimpleName());
			listElements.add(opcElement);
			nextNoMatch = new FormLogic(opcElement.getNext(),listElements,opcElement);
			nextNoMatch.loadFormType();
		}
	}
}
