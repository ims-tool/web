package br.com.gvt.telefonia.ura.service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.gvt.telefonia.ura.bussiness.FluxoBussiness;
import br.com.gvt.telefonia.ura.diagram.dao.DAO;
import br.com.gvt.telefonia.ura.diagram.dao.FormDAO;
import br.com.gvt.telefonia.ura.diagram.dao.OracleConn;
import br.com.gvt.telefonia.ura.diagram.dao.RouterDAO;
import br.com.gvt.telefonia.ura.diagram.model.Announce;
import br.com.gvt.telefonia.ura.diagram.model.Choice;
import br.com.gvt.telefonia.ura.diagram.model.Decision;
import br.com.gvt.telefonia.ura.diagram.model.DecisionChance;
import br.com.gvt.telefonia.ura.diagram.model.DecisionGroup;
import br.com.gvt.telefonia.ura.diagram.model.DecisionMap;
import br.com.gvt.telefonia.ura.diagram.model.DecisionParameters;
import br.com.gvt.telefonia.ura.diagram.model.Disconnect;
import br.com.gvt.telefonia.ura.diagram.model.Entity;
import br.com.gvt.telefonia.ura.diagram.model.Flow;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.Menu;
import br.com.gvt.telefonia.ura.diagram.model.NoMatchInput;
import br.com.gvt.telefonia.ura.diagram.model.Operation;
import br.com.gvt.telefonia.ura.diagram.model.PromptCollect;
import br.com.gvt.telefonia.ura.diagram.model.Router;
import br.com.gvt.telefonia.ura.diagram.model.Transfer;
import br.com.gvt.telefonia.ura.resource.FormResource;
import br.com.gvt.telefonia.ura.util.DaoFactory;
import br.com.gvt.telefonia.ura.util.NovaUraSingleton;
import br.com.gvt.telefonia.ura.util.Reflection;
import br.com.gvt.telefonia.ura.util.SingletonDAO;
import br.com.gvt.telefonia.ura.util.Util;

public class FluxoService {
	
	public static String[] types = {
			Menu.class.getSimpleName(),
			Decision.class.getSimpleName(),
			DecisionGroup.class.getSimpleName(),
			Operation.class.getSimpleName(),
			Disconnect.class.getSimpleName(),
			Flow.class.getSimpleName(),
			Choice.class.getSimpleName(),
			NoMatchInput.class.getSimpleName(),
			Transfer.class.getSimpleName(),
			Announce.class.getSimpleName(),
			PromptCollect.class.getSimpleName()
	};
	
	public String loadFluxo(String formId) throws Exception
	{
		FluxoBussiness fluxo = new FluxoBussiness();
		return fluxo.loadFluxo(formId);
	}
	
	public String loadFluxo(long formId) throws Exception
	{
		FluxoBussiness fluxo = new FluxoBussiness();
		return fluxo.loadFluxo(formId);
	}
	
	public String loadCondition(String id) throws Exception
	{
		FluxoBussiness fluxo = new FluxoBussiness();
		return fluxo.loadCondition(id);
	}
	
	public List<Form> loadParent(String formId) throws Exception
	{
		FluxoBussiness fluxo = new FluxoBussiness();
		return fluxo.loadParent(formId);
	}
	
	
	
	public String loadFluxoBack(String formId) throws Exception
	{
		FluxoBussiness fluxo = new FluxoBussiness();
		return fluxo.loadFluxoBack(formId);
	}
	
	public List<Router> loadRouters()
	{
		RouterDAO routerDAO = new RouterDAO();
		return routerDAO.findAllDistinct();
	}
	
	public String export(String form)
	{
		String result = "";
		
		FormDAO formDAO = new FormDAO();
		Form formElement = formDAO.findByPk(form);
		String level = Integer.toString(NovaUraSingleton.getInstance().getLevel());
		//String level = Integer.toString(5);
		
		String[] params = {formElement.getName(),level};
		OracleConn conn = new OracleConn();
		try {
			result = br.com.gvt.telefonia.add.controller.Main.getFlowXML(params, conn.getConnection());
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String loadFormFields(String id,String type,String operationParam)
	{
		String result = "";
		
		
		if(operationParam.equalsIgnoreCase("add"))
			id = null;
		
		try{
			if(Arrays.asList(FluxoService.types).contains(type))
			{
				//Entity<?> entity = (Entity<?>) ((id != null) ? DaoFactory.getInstance().getDao(type).findByPk(id) : Class.forName("br.com.gvt.telefonia.ura.diagram.model."+type).newInstance());
				result = FormResource.load(type);
			}
		} catch(Exception e){
			e.getStackTrace();
		}
		
		return result;
	}
	
	public String saveForm(HashMap<String,String> parameter)
	{
		String result = null;
		try{
			String className = parameter.get("type");
			String prefix = "br.com.gvt.telefonia.ura.diagram";
			String prefixFacade = "br.com.gvt.telefonia.ura.diagram.facade.";
			String packageName = prefix+".model.plsql.";
			Object obj = Class.forName(packageName+className).newInstance();
			Reflection.setAll(obj,parameter);
			
			Object objFacade = Class.forName(prefixFacade+"Facade"+className).newInstance();
			Method method = Reflection.getMethod(objFacade, "save");
			
			result = method.invoke(objFacade, obj,parameter.get("parent"),parameter.get("parentType")).toString();
				
			
		} catch(Exception e){
			e.getStackTrace();
		}
		
		return result;
	}
	
	public String saveForm(String id,String type,String operationParam)
	{
		String result = "";
		
		if(operationParam.equalsIgnoreCase("add"))
			id = null;
		
		if(Arrays.asList(FluxoService.types).contains(type))
		{
			result = FormResource.load(type);
		}
		
		return result;
	}
	
	public String loadForms()
	{
		String utf8 = "";
		FormDAO formDAO = new FormDAO();
		List<Form> listForm = formDAO.findAll();
		
		String[] result = new String[listForm.size()];
		for(int x = 0; x < listForm.size(); x++){
			Form obj = listForm.get(x);
			result[x] = "{\"id\": "+obj.getId()+", \"name\": \""+obj.getName()+" (" + Util.getFormType(obj.getFormType()) + ") " + obj.getId() +"\" }";
		}
		
		try {
			utf8 = new String(StringUtils.join(result,",").getBytes("UTF-8"),"UTF-8");
		} catch(Exception e){
			e.getStackTrace();
		}
		
		return "["+utf8.replaceAll("\\p{C}", "")+"]";
	}
	
	public String loadElement(String id,String type,String formid)
	{
		String result = "";
		String json = "";
		HashMap<String,String> modelMap = new HashMap<String, String>();
		HashMap<String,String> defaultnextformMap = new HashMap<String, String>();
		
		try{
			if(Arrays.asList(FluxoService.types).contains(type))
			{
				DAO<?> dao = DaoFactory.getInstance().getDao(type);
				Entity<?> entity = dao.findByPk(id);
				result = Util.toJson(Util.elementInfo(entity));
				
				Form form = (Form) DaoFactory.getInstance().getDao(Form.class.getSimpleName()).findByPk(formid);
				Form defaultnextform = SingletonDAO.getFormDAOInstance().findByPk(Long.toString(form.getNextFormDefault()));
				
				modelMap.put("formname",form.getName());
				
				modelMap.put("defaultnextform","<a class='defaultnextform'>" + form.getNextFormDefault() + "</a> <span class='glyphicon-pencil editar-defaultnextform' elementType='Form' elementId='"+form.getId()+"' elementNext='"+form.getNextFormDefault()+"'> Editar</span>");
				
				defaultnextformMap.put("defaultnextformname",defaultnextform.getName());
				
				if(type.equalsIgnoreCase(Menu.class.getSimpleName())) {
					defaultnextformMap.put("mais detalhes","<a href='http://telefonia/recall/Editor/mdin_menus2.aspx?FORM="+form.getName()+"' target='_blank'>clique aqui</a>");
					
				} else if(type.equalsIgnoreCase(Announce.class.getSimpleName())) {
					
					defaultnextformMap.put("mais detalhes","<a href='http://telefonia/recall/Editor/MDIN_ANNOUNCE2.aspx?FORM="+form.getName()+"' target='_blank'>clique aqui</a>");
					
				} else if(
						type.equalsIgnoreCase(DecisionGroup.class.getSimpleName()) || 
						type.equalsIgnoreCase(Operation.class.getSimpleName()) || 
						type.equalsIgnoreCase(DecisionChance.class.getSimpleName()) || 
						type.equalsIgnoreCase(Decision.class.getSimpleName())
				) {
					
					if(type.equalsIgnoreCase(DecisionGroup.class.getSimpleName())){
						DecisionGroup objGroup = (DecisionGroup) dao.findByPk(id);
						DecisionMap objResult = (DecisionMap) DaoFactory.getInstance().getDao("DecisionMap").findByPk(Long.toString(objGroup.getDecisionmapid()));
						List<DecisionParameters> parameters = (List<DecisionParameters>) DaoFactory.getInstance().getDao("DecisionParameters").findAllBy(" decisiongroupid = '"+objGroup.getId()+"'");
						
						defaultnextformMap.put("method",objResult.getMethodreference());
						
						String params = "";
						
						for(DecisionParameters elem : parameters)
							params += elem.getParamname() +  " -> " + elem.getParamvalue() + "<br />";
						
						defaultnextformMap.put("parameters",params); 
					}
					
					defaultnextformMap.put("mais detalhes","<a href='http://telefonia/recall/Editor/MDIN_FORM_FLUXO.aspx?FORM="+form.getName()+"' target='_blank'>clique aqui</a>");
					
				} else if(type.equalsIgnoreCase(Choice.class.getSimpleName())) {
					Choice choice = (Choice) dao.findByPk(id);
					if(choice.getCondition() != null)
						defaultnextformMap.put("Condition","<a href='javascript:APP.openCondition("+choice.getCondition()+");' target='_blank'>"+choice.getCondition()+"</a>");
					
				} else if (type.equalsIgnoreCase(Flow.class.getSimpleName())) {
					defaultnextformMap.put("Tipo flow",(form.getFormType() == 9 ? "Flow Interno" : "Flow Externo"));
					defaultnextformMap.put("flowname",Reflection.invokeGetMethod(entity, "flowname"));
					defaultnextformMap.put("Visualizar","<a href='javascript:APP.showFrameFlow("+formid+")'>Visualizar</a>");
					
				}
				
				json = "{\"form\": \""+Long.toString(form.getId())+"\"}";
				json = Util.appendDataToJson(json,modelMap);
				json = Util.appendDataToJson(json,result);
				json = Util.appendDataToJson(json,defaultnextformMap);
				
				
			}
		} catch(Exception e){
			e.getStackTrace();
		}
		
		return json;
	}

	public String saveNextForm(String id, String type, String nextform, String version) {
		
		Entity entity = DaoFactory.getInstance().getDao(type).findByPk(id);
		try{
			Reflection.invokeSetMethod(entity, "nextform", nextform);
			Reflection.invokeSetMethod(entity, "nextForm", nextform);
			Reflection.invokeSetMethod(entity, "nextformid", nextform);
			Reflection.invokeSetMethod(entity, "nextFormid", nextform);
			Reflection.invokeSetMethod(entity, "versionid", version);
		} catch(Exception e){
			e.getStackTrace();
		} finally {
			DaoFactory.getInstance().getDao(type).update(entity);
		}
		
		return "{\"status\": true}";
	}
	
	public String saveNextFormDefault(String id, String nextform, String version) {
		
		SingletonDAO.getInstance();
		Form form = SingletonDAO.getFormDAOInstance().findByPk(id);
		form.setNextFormDefault(Long.parseLong(nextform));
		form.setVersionid(version);
		SingletonDAO.getFormDAOInstance().update(form);
		return "{\"status\": true}";
	}
}
