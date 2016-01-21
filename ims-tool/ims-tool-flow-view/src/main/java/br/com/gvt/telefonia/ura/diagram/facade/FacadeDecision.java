package br.com.gvt.telefonia.ura.diagram.facade;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.gvt.telefonia.ura.diagram.model.Decision;
import br.com.gvt.telefonia.ura.diagram.model.DecisionChance;
import br.com.gvt.telefonia.ura.diagram.model.DecisionGroup;
import br.com.gvt.telefonia.ura.diagram.model.DecisionParameters;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.Reflection;
import br.com.gvt.telefonia.ura.util.SingletonDAO;
import br.com.gvt.telefonia.ura.util.Util;

public class FacadeDecision {
	
	private String name;
	private String description;
	private String tag;
	private String versionid;
	private List<DecisionGroup> decisionGroup;
	
	public FacadeDecision(){
		decisionGroup = new ArrayList<DecisionGroup>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public List<DecisionGroup> getDecisionGroup() {
		return decisionGroup;
	}
	public void setDecisionGroup(List<DecisionGroup> decisionGroup) {
		this.decisionGroup = decisionGroup;
	}
	
	private void popDecisionGroup(HttpServletRequest request)
	{
		DecisionGroup decisionGroup = null;
		
		String[] decisionGroupsDescription = request.getParameterValues("decisiongroup.description");
		String[] decisionGroupsMapId = request.getParameterValues("decisiongroup.decisionmapid");
		
		
		for(int x = 0; x < decisionGroupsDescription.length; x++)
		{
			decisionGroup = new DecisionGroup();
			decisionGroup.setOrdernum(x);
			decisionGroup.setDescription(decisionGroupsDescription[x]);
			decisionGroup.setDecisionmapid(Long.parseLong(decisionGroupsMapId[x]));
			decisionGroup.setVersionid(Long.parseLong(versionid));
			getDecisionGroup().add(decisionGroup);
		}
	}
	
	private void saveDecisionGroup(String insertId,HttpServletRequest request)
	{
		String[] paramNameChances;
		String insertGrouptId = "";
		String[] decisionParametersData = request.getParameterValues("decisiongroup.parameters");

		SingletonDAO.getDecisionGroupDAOInstance().deleteAllByDecisionId(insertId);
		
		for(int x = 0; x < decisionGroup.size(); x++)
		{
			DecisionGroup group = decisionGroup.get(x);
			
			group.setDecisionid(Long.parseLong(insertId));
			group.setOrdernum(x);
			insertGrouptId = SingletonDAO.getDecisionGroupDAOInstance().insert(group);
			
			if(!insertGrouptId.isEmpty())
			{
				saveDecisionChances(insertGrouptId, request);
				if(!decisionParametersData[x].isEmpty())
				{
					paramNameChances = decisionParametersData[x].split("\\|");
					saveDecisionParameters(insertGrouptId, paramNameChances);
				}	
			}
		}
	}
	
	private void saveDecisionParameters(String inserGrouptId,String[] paramNameChances)
	{
		String paramName,paramValue = "";
		String[] auxParam;
		DecisionParameters decisionParameters = null;
	
		for(String data : paramNameChances)
		{
			auxParam = data.split(":");
			paramName = auxParam[0];
			paramValue = auxParam[1];
			
			decisionParameters = new DecisionParameters();
			decisionParameters.setDecisiongroupid(Long.parseLong(inserGrouptId));
			decisionParameters.setParamname(paramName);
			decisionParameters.setParamvalue(paramValue);
			decisionParameters.setVersionid(versionid);
			SingletonDAO.getDecisionParametersDAOInstance().insert(decisionParameters);
		}
	}
	
	public void save(HttpServletRequest request)
	{
		String insertId = "";
		Form form = null;
		Decision decision = null;
		String decisionId = request.getParameter("id");
		
		if(!decisionId.isEmpty())
			decision = SingletonDAO.getDecisionDAOInstance().findByPk(decisionId);
		else
			decision = new Decision();
		
		decision.setName(name);
		decision.setDescription(description);
		decision.setTag(tag);
		decision.setVersionid(versionid);
		
		SingletonDAO.getInstance();
		
		if(!decisionId.isEmpty())
			decision.setId(Long.parseLong(decisionId));
			
		SingletonDAO.getDecisionDAOInstance().save(decision);
		
		insertId = Long.toString(decision.getId());
		
		if(!insertId.isEmpty())
		{
			popDecisionGroup(request);
			saveDecisionGroup(insertId,request);
		}
		
		if(!insertId.isEmpty())
		{
			form = new Form();
		} else {
			form = SingletonDAO.getFormDAOInstance().findBy(" formid = '"+decision.getId()+"' and formtype = '"+Form.FT_DECISION+"' ");
		}
		
		form.setName(name);
		form.setDescription(description);
		form.setFormType(Form.FT_DECISION);
		form.setFormid(Long.parseLong(insertId));
		form.setVersionid(versionid);
		SingletonDAO.getFormDAOInstance().save(form);
		
	}

	private void saveDecisionChances(String insertGroupId, HttpServletRequest request)
	{
		DecisionChance obj = null;
		String decisionValue = request.getParameter("decisionChance");
		String[] decisionChance = decisionValue.split("\\|");
		String[] element;
		String[] values;
		
		for( int x = 0; x < decisionChance.length; x++ )
		{
			element = decisionChance[x].split(":");
			obj = new DecisionChance();
			obj.setDecisiongroupid(Long.parseLong(insertGroupId));
			obj.setOrdernum(x);
			obj.setOperation(Util.parseOperation(element[0]));
			
			values = element[1].split(",");
			for(int i = 0; i < values.length; i++){
				Reflection.invokeSetMethod(obj, "value"+(i+1), values[i]);
			}
			
			obj.setTag(element[2]);
			
			if(element.length > 3)
				if(!element[3].isEmpty())
				obj.setDecisiongroupchild(element[3]);
			
			if(element.length > 4)
				if(!element[4].isEmpty())
				obj.setNextformid(Long.parseLong(element[4]));
			
			obj.setVersionid(versionid);
			
			SingletonDAO.getDecisionChanceDAOInstance().insert(obj);
		}
	}
}
