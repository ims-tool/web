package br.com.gvt.telefonia.ura.diagram.facade;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.gvt.telefonia.ura.diagram.model.Condition;
import br.com.gvt.telefonia.ura.diagram.model.ConditionGroup;
import br.com.gvt.telefonia.ura.diagram.model.ConditionParameters;
import br.com.gvt.telefonia.ura.diagram.model.ConditionValue;
import br.com.gvt.telefonia.ura.util.Reflection;
import br.com.gvt.telefonia.ura.util.SingletonDAO;
import br.com.gvt.telefonia.ura.util.Util;

public class FacadeCondition {
	
	private String name;
	private String description;
	private String tag;
	private String versionid;
	private List<ConditionGroup> conditionGroup;
	
	public String getVersionid() {
		return versionid;
	}

	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public FacadeCondition(){
		conditionGroup = new ArrayList<ConditionGroup>();
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
	public List<ConditionGroup> getConditionGroup() {
		return conditionGroup;
	}
	public void setConditionGroup(List<ConditionGroup> conditionGroup) {
		this.conditionGroup = conditionGroup;
	}
	
	private void popConditionGroup(HttpServletRequest request)
	{
		ConditionGroup conditionGroup = null;
		
		String[] conditionGroupsDescription = request.getParameterValues("conditiongroup.description");
		String[] conditionGroupsMapId = request.getParameterValues("conditiongroup.conditionmapid");
		
		
		for(int x = 0; x < conditionGroupsDescription.length; x++)
		{
			conditionGroup = new ConditionGroup();
			conditionGroup.setDescription(conditionGroupsDescription[x]);
			conditionGroup.setConditionmapid(Long.parseLong(conditionGroupsMapId[x]));
			getConditionGroup().add(conditionGroup);
		}
	}
	
	private void saveConditionGroup(String insertId,HttpServletRequest request)
	{
		String[] paramNameValues;
		String insertGrouptId = "";
		String[] conditionParametersData = request.getParameterValues("conditiongroup.parameters");
		
		SingletonDAO.getConditionGroupDAOInstance().deleteAllByConditionId(insertId);

		for(int x = 0; x < conditionGroup.size(); x++)
		{
			ConditionGroup group = conditionGroup.get(x);
			
			group.setConditionid(Long.parseLong(insertId));
			group.setOrdernum(x);
			insertGrouptId = SingletonDAO.getConditionGroupDAOInstance().insert(group);
			
			if(!insertGrouptId.isEmpty())
			{
				saveConditionValues(insertGrouptId, request);
				if(!conditionParametersData[x].isEmpty())
				{
					paramNameValues = conditionParametersData[x].split("\\|");
					saveConditionParameters(insertGrouptId, paramNameValues);
				}	
			}
		}
	}
	
	private void saveConditionParameters(String inserGrouptId,String[] paramNameValues)
	{
		String paramName,paramValue = "";
		String[] auxParam;
		ConditionParameters conditionParameters = null;
	
		for(String data : paramNameValues)
		{
			auxParam = data.split(":");
			paramName = auxParam[0];
			paramValue = auxParam[1];
			
			conditionParameters = new ConditionParameters();
			conditionParameters.setConditiongroupid(Long.parseLong(inserGrouptId));
			conditionParameters.setParamname(paramName);
			conditionParameters.setParamvalue(paramValue);
			conditionParameters.setVersionid(versionid);
			SingletonDAO.getConditionParametersDAOInstance().insert(conditionParameters);
		}
	}
	
	public void save(HttpServletRequest request)
	{
		String insertId = "";
		
		String conditionId = request.getParameter("id");
		
		Condition condition = new Condition();
		condition.setName(name);
		condition.setDescription(description);
		condition.setVersionid(versionid);
		condition.setTag(tag);
		
		SingletonDAO.getInstance();
		
		if(!conditionId.isEmpty()) {
			condition.setId(Long.parseLong(conditionId));
			SingletonDAO.getConditionDAOInstance().update(condition);
			insertId = conditionId;
		} else
			insertId = SingletonDAO.getConditionDAOInstance().insert(condition);
		
		popConditionGroup(request);
		saveConditionGroup(insertId,request);
	}

	private void saveConditionValues(String insertGroupId, HttpServletRequest request)
	{
		SingletonDAO.getInstance();
		ConditionValue obj = null;
		
		String[] conditionValue = request.getParameterValues("conditionvalue");
		String[] conditionValueOperation = request.getParameterValues("conditionvalue.operation");
		String[] conditionValueValues = request.getParameterValues("conditionvalue.values");
		String[] conditionValueTagtrue = request.getParameterValues("conditionvalue.tagtrue");
		String[] conditionValueTagfalse = request.getParameterValues("conditionvalue.tagfalse");
		String[] values;

		for( int x = 0; x < conditionValue.length; x++ )
		{
			if(conditionValue[x].equalsIgnoreCase("true"))
			{
				obj = new ConditionValue();
				obj.setOperation(Util.parseOperation(conditionValueOperation[x]));
				obj.setOrdernum(x);
				values = conditionValueValues[x].split(",");
				
				for(int i = 0; i < values.length; i++)
					Reflection.invokeSetMethod(obj, "value"+(i+1), values[i]);
				
				obj.setTagtrue(conditionValueTagtrue[x]);
				obj.setTagfalse(conditionValueTagfalse[x]);
				obj.setConditiongroupid(Long.parseLong(insertGroupId));
				obj.setVersionid(versionid);
				SingletonDAO.getConditionValueDAOInstance().insert(obj);
			}
		}
	}
}
