package br.com.gvt.telefonia.ura.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.gvt.telefonia.ura.diagram.facade.FacadeAudio;
import br.com.gvt.telefonia.ura.diagram.facade.FacadeCondition;
import br.com.gvt.telefonia.ura.diagram.facade.FacadeDecision;
import br.com.gvt.telefonia.ura.diagram.facade.FacadeMenu;
import br.com.gvt.telefonia.ura.diagram.facade.FacadeOperation;
import br.com.gvt.telefonia.ura.diagram.facade.FacadePrompt;
import br.com.gvt.telefonia.ura.diagram.model.Announce;
import br.com.gvt.telefonia.ura.diagram.model.Audio;
import br.com.gvt.telefonia.ura.diagram.model.Audiovar;
import br.com.gvt.telefonia.ura.diagram.model.Choice;
import br.com.gvt.telefonia.ura.diagram.model.Condition;
import br.com.gvt.telefonia.ura.diagram.model.ConditionGroup;
import br.com.gvt.telefonia.ura.diagram.model.ConditionParameters;
import br.com.gvt.telefonia.ura.diagram.model.ConditionValue;
import br.com.gvt.telefonia.ura.diagram.model.Decision;
import br.com.gvt.telefonia.ura.diagram.model.DecisionChance;
import br.com.gvt.telefonia.ura.diagram.model.DecisionGroup;
import br.com.gvt.telefonia.ura.diagram.model.DecisionParameters;
import br.com.gvt.telefonia.ura.diagram.model.Disconnect;
import br.com.gvt.telefonia.ura.diagram.model.Entity;
import br.com.gvt.telefonia.ura.diagram.model.Flow;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.LogPontos;
import br.com.gvt.telefonia.ura.diagram.model.Menu;
import br.com.gvt.telefonia.ura.diagram.model.NoMatchInput;
import br.com.gvt.telefonia.ura.diagram.model.Operation;
import br.com.gvt.telefonia.ura.diagram.model.OperationGroup;
import br.com.gvt.telefonia.ura.diagram.model.OperationParameters;
import br.com.gvt.telefonia.ura.diagram.model.Prompt;
import br.com.gvt.telefonia.ura.diagram.model.PromptAudio;
import br.com.gvt.telefonia.ura.diagram.model.PromptCollect;
import br.com.gvt.telefonia.ura.diagram.model.Tag;
import br.com.gvt.telefonia.ura.diagram.model.Transfer;
import br.com.gvt.telefonia.ura.diagram.model.Version;
import br.com.gvt.telefonia.ura.util.DaoFactory;
import br.com.gvt.telefonia.ura.util.EntityFactory;
import br.com.gvt.telefonia.ura.util.Reflection;
import br.com.gvt.telefonia.ura.util.SingletonDAO;
import br.com.gvt.telefonia.ura.util.Util;

public class CrudService {
	
	
	public String loadElement(String id, String type)
	{
		Entity<?> entity = null;
		String result = "";
		SingletonDAO.getInstance();
		
		if( type.equalsIgnoreCase(Form.class.getSimpleName()) ) {
			if(id.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
				entity = SingletonDAO.getFormDAOInstance().findByPk(id);
			else
			entity = SingletonDAO.getFormDAOInstance().searchByName(id);
		} else
			entity = DaoFactory.getInstance().getDao(type).findByPk(id);
		
		result = Util.toJson(entity);
		
		if(type.equalsIgnoreCase(Flow.class.getSimpleName()))
		{
			Form flow = SingletonDAO.getFormDAOInstance().findBy(" formtype IN(4,9) and formid = '"+id+"' ");
			result = result.substring(0, result.length()-1)+",\"tipo\": \""+flow.getFormType()+"\" }";
		}
		
		return result;
	}
	
	
	public String loadAll(String type)
	{
		String result = "";
		HashMap<String,String> auxHashMap = new HashMap<String,String>();
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>(); 
		
		List<Entity<?>> entityList = (List<Entity<?>>) DaoFactory.getInstance().getDao(type).findAll();
		
		for(Entity<?> obj : entityList)
		{
			auxHashMap = new HashMap<String,String>();
			auxHashMap.put("id", Reflection.invokeGetMethod(obj, "id"));
			
			if(type.equalsIgnoreCase(Tag.class.getSimpleName()))
				auxHashMap.put("name", Reflection.invokeGetMethod(obj, "description"));
			else if(type.equalsIgnoreCase(LogPontos.class.getSimpleName()))
				auxHashMap.put("name", Reflection.invokeGetMethod(obj, "nome"));
			else
				auxHashMap.put("name", Reflection.invokeGetMethod(obj, "name"));
			
			list.add(auxHashMap);
		}
		
		return Util.toJson(list);
	}
	
	
	public String loadFullElement(String id, String type) {
		String result = "";
		
		Entity<?> entity = DaoFactory.getInstance().getDao(type).findByPk(id);
		result += Util.toJsoWithoutTags(entity);
		result = result.substring(0,result.length()-1);
		
		String audioInfo = "";
		HashMap<String,String> map = null; 
		Audio auxAudio = null;
		
		if(type.equalsIgnoreCase(Prompt.class.getSimpleName()))
		{
			result += ",\"audios\":[ ";
			
			List<PromptAudio> list = SingletonDAO.getPromptAudioDAOInstance().findByPrompt(id);
			
			for(PromptAudio obj : list){
				map = new HashMap<String,String>();
				map.put("condition", obj.getCondition());
				String audioId = Long.toString(obj.getAudio());
				auxAudio = SingletonDAO.getAudioDAOInstance().findByPk(audioId);
				audioInfo = Util.toJson( auxAudio );
				audioInfo = audioInfo.substring(0,audioInfo.length()-1);
				audioInfo += ",\"vars\":[ ";
				if(auxAudio.getType().equalsIgnoreCase("VAR"))
				{
					List<Audiovar> vars = SingletonDAO.getAudiovarDAOInstance().getAllByAudio(Long.toString(auxAudio.getId()));
					for(Audiovar objAudiovar : vars)
						audioInfo += Util.toJsoWithoutTags( objAudiovar ) + ",";	
				}
				audioInfo = audioInfo.substring(0,audioInfo.length()-1) + "]}";
				result += Util.appendDataToJson(audioInfo, map) + ",";
			}
			
			result = result.substring(0,result.length()-1) + "]";
			
		} else if(type.equalsIgnoreCase(Menu.class.getSimpleName())){
			
			result += ",\"choices\":[ ";
			
			List<Choice> list = SingletonDAO.getChoiceDAOInstance().findByMenu(id);
			
			for(Choice obj : list){
				result += Util.toJsoWithoutTags( obj ) + ",";
			}
			
			result = result.substring(0,result.length()-1) + "]";
		} else if(type.equalsIgnoreCase(Audio.class.getSimpleName())){
			
			result += ",\"vars\":[ ";
			if(Reflection.invokeGetMethod(entity, "type").equalsIgnoreCase("VAR"))
			{
				List<Audiovar> vars = SingletonDAO.getAudiovarDAOInstance().getAllByAudio(id);
				
				
				for(Audiovar obj : vars){
					result += Util.toJsoWithoutTags( obj ) + ",";
				}
			}
			result = result.substring(0,result.length()-1) + "]";
			
		} else if(type.equalsIgnoreCase(Decision.class.getSimpleName())){
			result = loadDecision(id,result);
		} else if (type.equalsIgnoreCase(Condition.class.getSimpleName())){
			result = loadCondition(id,result);
		} else if (type.equalsIgnoreCase(Operation.class.getSimpleName())){
			result = loadOperation(id,result);
		}
		
		return result+"}";
	}
	
	private String loadDecision(String id, String result)
	{
		List<DecisionChance> listChances = null;
		List<DecisionParameters> listParameters = null;
		
		result += ",\"decisiongroup\":[ ";
		
		List<DecisionGroup> list = SingletonDAO.getDecisionGroupDAOInstance().findAllBy(" decisionid = '"+id+"' ");
		
		for(DecisionGroup obj : list){
			result += Util.toJson( obj );
			result = result.substring(0,result.length()-1);
			result += ",";
			listChances = SingletonDAO.getDecisionChanceDAOInstance().findAllBy(" decisiongroupid = '"+obj.getId()+"' ");
			listParameters = SingletonDAO.getDecisionParametersDAOInstance().findAllBy(" decisiongroupid = '"+obj.getId()+"' ");
		
			result += "\"decisionchance\":[ ";
			for(DecisionChance chance : listChances){
				result += Util.toJson( chance ) + ",";
			}
			result = result.substring(0,result.length()-1) + "],";
			
			result += "\"decisionparameters\":[ ";
			for(DecisionParameters param : listParameters){
				result += Util.toJson( param ) + ",";
			}
			result = result.substring(0,result.length()-1) + "]},";
			
		}
		
		return result.substring(0,result.length()-1) + "]";
	}
	
	private String loadCondition(String id, String result)
	{
		List<ConditionValue> listValues = null;
		List<ConditionParameters> listParameters = null;
		
		result += ",\"conditiongroup\":[ ";
		
		List<ConditionGroup> list = SingletonDAO.getConditionGroupDAOInstance().findAllBy(" conditionid = '"+id+"' ");
		
		for(ConditionGroup obj : list){
			result += Util.toJson( obj );
			result = result.substring(0,result.length()-1);
			result += ",";
			listValues = SingletonDAO.getConditionValueDAOInstance().findAllBy(" conditiongroupid = '"+obj.getId()+"' ");
			listParameters = SingletonDAO.getConditionParametersDAOInstance().findAllBy(" conditiongroupid = '"+obj.getId()+"' ");
		
			result += "\"conditionvalue\":[ ";
			for(ConditionValue chance : listValues){
				result += Util.toJson( chance ) + ",";
			}
			result = result.substring(0,result.length()-1) + "],";
			
			result += "\"conditionparameters\":[ ";
			for(ConditionParameters param : listParameters){
				result += Util.toJson( param ) + ",";
			}
			result = result.substring(0,result.length()-1) + "]},";
			
		}
		
		return result.substring(0,result.length()-1) + "]";
	}
	
	private String loadOperation(String id, String result)
	{
		List<OperationParameters> listParameters = null;
		
		result += ",\"operationgroup\":[ ";
		
		List<OperationGroup> list = SingletonDAO.getOperationGroupDAOInstance().findAllBy(" operationid = '"+id+"' ");
		
		for(OperationGroup obj : list){
			result += Util.toJson( obj );
			result = result.substring(0,result.length()-1);
			result += ",";
			listParameters = SingletonDAO.getOperationParameterDAOInstance().findAllBy(" operationgroupid = '"+obj.getId()+"' ");
		
			result += "\"conditionparameters\":[ ";
			for(OperationParameters param : listParameters){
				result += Util.toJson( param ) + ",";
			}
			result = result.substring(0,result.length()-1)+"],";
			
			result += "\"conditionmap\": ";
			result += Util.toJson(SingletonDAO.getOperationMapDAOInstance().findByPk(Long.toString(obj.getOperationmapid())));
			result += ",";
			
			result = result.substring(0,result.length()-1) + "},";
		}
		
		return result.substring(0,result.length()-1) + "]";
	}

	public String loadAllNomatchinput (String type)
	{
		String result = "";
		StringBuffer resultBuffer = new StringBuffer();
		
		SingletonDAO.getInstance();
		List<NoMatchInput> entityList = SingletonDAO.getNoMatchInputDAOInstance().findAllBy(" type= '"+type+"'");
		
		for(NoMatchInput obj : entityList)
			resultBuffer.append(Util.toJson(obj)+",");
		
		result = resultBuffer.toString();
		
		return "["+result.substring(0,result.length()-1)+"]";
	}
	
	public String search(String type, String search) {
		
		String field = "";
		String result = "";
		boolean nameAsId = false;
		
		if(type.equalsIgnoreCase("FlowName"))
		{
			type = "Form";
			nameAsId = true;
		}
		
		HashMap<String,String> auxHashMap = new HashMap<String,String>();
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		
		if(type.equalsIgnoreCase(Tag.class.getSimpleName()) || type.equalsIgnoreCase(Version.class.getSimpleName()) || type.equalsIgnoreCase(DecisionGroup.class.getSimpleName()))
			field = "description";
		else if(type.equalsIgnoreCase(LogPontos.class.getSimpleName()))
			field = "nome";
		else
			field = "name";
		
		List<Entity<?>> entityList = (List<Entity<?>>) DaoFactory.getInstance().getDao(type).findAllBy(" LOWER("+field+") like LOWER('%"+search+"%') or id like '%"+search+"%' ");
		
		String idElement = "";
		
		for(Entity<?> obj : entityList)
		{
			String typeForm = "";
			
			if(obj.getClass().getSimpleName().equalsIgnoreCase(Form.class.getSimpleName()))
				typeForm = " " + Util.getFormType(Long.parseLong(Reflection.invokeGetMethod(obj, "formtype")));
			
			auxHashMap = new HashMap<String,String>();
			idElement = Reflection.invokeGetMethod(obj, "id");
			
			if(nameAsId)
				auxHashMap.put("id", Reflection.invokeGetMethod(obj, field));
			else
				auxHashMap.put("id", idElement);
			
			auxHashMap.put("text", Reflection.invokeGetMethod(obj, field) + " ("+idElement+") " + typeForm);
			list.add(auxHashMap);
		}
		
		if(list.size() > 0)
			return Util.toJson(list);
		else 
			return "[]";
	}
	
	public String searchNoMatchInput(String type, String search) {
		
		HashMap<String,String> auxHashMap = new HashMap<String,String>();
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		SingletonDAO.getInstance();
		List<NoMatchInput> entityList = SingletonDAO.getNoMatchInputDAOInstance().findAllBy(" ( LOWER(name) like LOWER('%"+search+"%') OR id like '%"+search+"%' ) and LOWER(type) = LOWER('"+type+"') ");
		
		for(NoMatchInput obj : entityList)
		{
			auxHashMap = new HashMap<String,String>();
			auxHashMap.put("id", Long.toString(obj.getId()));
			auxHashMap.put("text", obj.getName() + " ("+obj.getId()+") ");
			list.add(auxHashMap);
		}
		
		if(list.size() > 0)
			return Util.toJson(list);
		else 
			return "[]";
	}
	
	public String save(HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		String lastId = "";
		String result = "{}";
		String id = request.getParameter("id");
		String type = request.getParameter("elementType");
		br.com.gvt.telefonia.ura.diagram.model.Entity<?> entity = null;
		
		if(!request.getParameter("id").isEmpty())
			entity = DaoFactory.getInstance().getDao(type).findByPk(request.getParameter("id"));
		else
			entity = EntityFactory.getInstance().getEntity(type);
		
		Field[] attributes = Reflection.getAttributes(entity);
		
		for(Field attr : attributes)
			try{
				if(request.getParameter(attr.getName()).equalsIgnoreCase("null"))
					continue;
				
				Reflection.invokeSetMethod(entity, attr.getName(), request.getParameter(attr.getName()));
			} catch (Exception e){
				e.getStackTrace();
			}
		
		if(Long.parseLong(Reflection.invokeGetMethod(entity, "id")) > 0){
			DaoFactory.getInstance().getDao(type).update(entity);
			lastId = Reflection.invokeGetMethod(entity, "id");
		} else{
			lastId = DaoFactory.getInstance().getDao(type).insert(entity);
		}
		
		return lastId;
	}

	public String saveElement(HttpServletRequest request) {
		
		String result = "";
		String type = request.getParameter("elementType");
		
		if(type.equalsIgnoreCase(Prompt.class.getSimpleName()))
			savePrompt(request);
		
		if(type.equalsIgnoreCase(Announce.class.getSimpleName()))
			saveAnnounce(request);
		
		if(type.equalsIgnoreCase(Menu.class.getSimpleName()))
			saveMenu(request);
		
		if(type.equalsIgnoreCase(Transfer.class.getSimpleName()))
			saveTransfer(request);
		
		if(type.equalsIgnoreCase(Disconnect.class.getSimpleName()))
			saveDisconnect(request);
		
		if(type.equalsIgnoreCase(Flow.class.getSimpleName()))
			saveFlow(request);
		
		if(type.equalsIgnoreCase(Operation.class.getSimpleName()))
			saveOperation(request);
		
		if(type.equalsIgnoreCase(Condition.class.getSimpleName()))
			saveCondition(request);
		
		if(type.equalsIgnoreCase(Decision.class.getSimpleName()))
			saveDecision(request);
		
		if(type.equalsIgnoreCase(PromptCollect.class.getSimpleName()))
			savePromptCollect(request);
		
		if(type.equalsIgnoreCase(Audio.class.getSimpleName()))
			result = loadElement(saveAudio(request),Audio.class.getSimpleName());
		
		if(result == "")
			result = "{\"status\": true}";
		
		return result;
	}
	
	public void savePrompt(HttpServletRequest request)
	{
		String audiosParam = request.getParameter("audios");
		
		FacadePrompt facadePrompt = new FacadePrompt();
		
		if(!request.getParameter("id").equalsIgnoreCase(""))
			facadePrompt.setId(request.getParameter("id"));
			
		facadePrompt.setName(request.getParameter("name"));
		facadePrompt.setVersionid(request.getParameter("versionid"));
		facadePrompt.setAudio(audiosParam.split(","));
		
		facadePrompt.save();
	}
	
	public void saveOperation(HttpServletRequest request)
	{		
		FacadeOperation operation = new FacadeOperation();
		
		operation.setName(request.getParameter("name"));
		operation.setDescription(request.getParameter("description"));
		operation.setTag(request.getParameter("tag"));
		operation.setVersionid(request.getParameter("versionid"));
		
		operation.save(request);
	}
	
	public void saveAnnounce(HttpServletRequest request)
	{
		saveElementAndForm(request);
	}
	
	public void saveCondition(HttpServletRequest request)
	{
		FacadeCondition condition = new FacadeCondition();
		condition.setName(request.getParameter("name"));
		condition.setDescription(request.getParameter("description"));
		condition.setTag(request.getParameter("tag"));
		condition.setVersionid(request.getParameter("versionid"));
		condition.save(request);
	}
	
	public void savePromptCollect(HttpServletRequest request)
	{
		saveElementAndForm(request);
	}
	
	public void saveDecision(HttpServletRequest request)
	{
		FacadeDecision decision = new FacadeDecision();
		
		decision.setName(request.getParameter("name"));
		decision.setDescription(request.getParameter("description"));
		decision.setTag(request.getParameter("tag"));
		decision.setVersionid(request.getParameter("versionid"));
		
		decision.save(request);
	}
	
	public String saveAudio(HttpServletRequest request)
	{
		FacadeAudio audio = new FacadeAudio();
		audio.setType(request.getParameter("type"));
		audio.setName(request.getParameter("name"));
		audio.setDescription(request.getParameter("description"));
		audio.setPath(request.getParameter("path"));
		audio.setVersionid(request.getParameter("versionid"));
		return audio.save(request);
	}

	public String saveMenu(HttpServletRequest request)
	{
		String type = request.getParameter("elementType");
		String result = "";
		Choice objChoice = null;
		FacadeMenu menu = new FacadeMenu();
		menu.setId(request.getParameter("id"));
		menu.setName(request.getParameter("name"));
		menu.setDescription(request.getParameter("description"));
		menu.setPrompt(request.getParameter("prompt"));
		menu.setNoinput(request.getParameter("noinput"));
		menu.setNomatch(request.getParameter("nomatch"));
		menu.setVersionid(request.getParameter("versionid"));
		
		String[] choices = request.getParameter("choice").split("@");
		for(String paramChoice : choices)
		{
			String[] param = paramChoice.split("\\|");
			objChoice = new Choice();
			objChoice.setName(param[0]);
			objChoice.setDtmf(param[1]);
			objChoice.setTag(param[2]);
			objChoice.setNextform("0");
			
			if(param.length > 3){
				if(param[3] != null)
					if(!param[3].equalsIgnoreCase("null"))
				objChoice.setCondition(param[3]);
			}
				
			objChoice.setVersionid(menu.getVersionid());
			menu.getChoice().add(objChoice);
		}
		
		menu.save();
		return result;
	}
	
	public void saveTransfer(HttpServletRequest request)
	{
		saveElementAndForm(request);
	}
	
	public void saveDisconnect(HttpServletRequest request)
	{
		saveElementAndForm(request);
	}
	
	public void saveFlow(HttpServletRequest request)
	{
		saveElementAndForm(request);
	}
	
	private void saveElementAndForm(HttpServletRequest request)
	{
		String result = "";
		Form form = null;
		String id = request.getParameter("id");
		String type = request.getParameter("elementType");
		
		try{
			result = save(request);
			
			if(!result.isEmpty())
			{
				form = new Form();
				form.setFormid(Long.parseLong(result));
				
			} else {
				String idForm = Reflection.invokeGetMethod(DaoFactory.getInstance().getDao(type).findByPk(id), "id");
				form = SingletonDAO.getFormDAOInstance().findByFormId(idForm,type);
			}
			

			
			form.setName(request.getParameter("name"));
			form.setDescription(request.getParameter("description"));
			form.setFormType(Util.getFormType(type));
			
			if(type.equalsIgnoreCase(Flow.class.getSimpleName()))
				form.setFormType(Integer.parseInt(request.getParameter("tipo")));
			
			form.setVersionid(request.getParameter("versionid"));
			
			SingletonDAO.getFormDAOInstance().save(form);

		} catch(Exception e)
		{
			e.getStackTrace();
		}
	}
}
