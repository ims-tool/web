package br.com.gvt.telefonia.ura.diagram.facade;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.Operation;
import br.com.gvt.telefonia.ura.diagram.model.OperationGroup;
import br.com.gvt.telefonia.ura.diagram.model.OperationParameters;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class FacadeOperation {
	
	private String name;
	private String description;
	private String tag;
	private String versionid = "";
	private List<OperationGroup> operationGroup;
	
	public FacadeOperation(){
		operationGroup = new ArrayList<OperationGroup>();
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
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
	public List<OperationGroup> getOperationGroup() {
		return operationGroup;
	}
	public void setOperationGroup(List<OperationGroup> operationGroup) {
		this.operationGroup = operationGroup;
	}
	
	private void popOperationGroup(HttpServletRequest request)
	{
		OperationGroup operationGroup = null;
		
		String[] operationGroupsDescription = request.getParameterValues("operationgroup.description");
		String[] operationGroupsMapId = request.getParameterValues("operationgroup.operationmapid");
		
		
		for(int x = 0; x < operationGroupsDescription.length; x++)
		{
			operationGroup = new OperationGroup();
			operationGroup.setDescription(operationGroupsDescription[x]);
			operationGroup.setOperationmapid(Long.parseLong(operationGroupsMapId[x]));
			operationGroup.setVersionid(versionid);
			getOperationGroup().add(operationGroup);
		}
	}
	
	private void saveOperationGroup(String insertId,String[] operationParametersData)
	{
		String inserGrouptId = "";
		String[] paramNameValues;

		SingletonDAO.getOperationGroupDAOInstance().deleteAllByOperationId(insertId);
		
		for(int x = 0; x < operationGroup.size(); x++)
		{
			OperationGroup group = operationGroup.get(x);
			
			group.setOperationid(Long.parseLong(insertId));
			group.setOrdernum(x);
			inserGrouptId = SingletonDAO.getOperationGroupDAOInstance().insert(group);
			
			if(!inserGrouptId.isEmpty() && !operationParametersData[x].isEmpty())
			{
				paramNameValues = operationParametersData[x].split("\\|");
				saveOperationParameters(inserGrouptId, paramNameValues);
			}
		}
	}
	
	private void saveOperationParameters(String inserGrouptId,String[] paramNameValues)
	{
		String paramName,paramValue = "";
		String[] auxParam;
		OperationParameters operationParameters = null;
	
		for(String data : paramNameValues)
		{
			auxParam = data.split(":");
			paramName = auxParam[0];
			paramValue = auxParam[1];
			
			operationParameters = new OperationParameters();
			operationParameters.setOperationgroupid(inserGrouptId);
			operationParameters.setParamname(paramName);
			operationParameters.setParamvalue(paramValue);
			operationParameters.setVersionid(versionid);
			SingletonDAO.getOperationParameterDAOInstance().insert(operationParameters);
		}
	}
	
	public void save(HttpServletRequest request)
	{
		String insertId = "";
		Form form = null;
		String operationId = request.getParameter("id");
		
		String[] operationParametersData = request.getParameterValues("operationgroup.parameters");
		
		Operation operation = new Operation();
		operation.setName(name);
		operation.setDescription(description);
		operation.setNextFormid(-1);
		operation.setTag(Long.parseLong(tag));
		operation.setVersionid(versionid);
		
		SingletonDAO.getInstance();
		
		if(!operationId.isEmpty()) {
			operation.setId(Long.parseLong(operationId));
			insertId = Long.toString(operation.getId());
			SingletonDAO.getOperationDAOInstance().update(operation);
		} else
			insertId = SingletonDAO.getOperationDAOInstance().insert(operation);
		
		popOperationGroup(request);
		saveOperationGroup(insertId,operationParametersData);
		
		form = SingletonDAO.getFormDAOInstance().findByFormId(insertId, Integer.toString(Form.FT_OPERATION)); 
		
		if(form == null)
			form = new Form();
		
		form.setName(name);
		form.setDescription(description);
		form.setFormType(Form.FT_OPERATION);
		form.setFormid(Long.parseLong(insertId));
		form.setVersionid(versionid);
		SingletonDAO.getFormDAOInstance().save(form);
	}
}
