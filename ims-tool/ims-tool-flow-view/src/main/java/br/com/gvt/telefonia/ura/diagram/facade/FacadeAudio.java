package br.com.gvt.telefonia.ura.diagram.facade;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import br.com.gvt.telefonia.ura.diagram.model.Audio;
import br.com.gvt.telefonia.ura.diagram.model.Audiovar;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.DaoFactory;
import br.com.gvt.telefonia.ura.util.Reflection;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class FacadeAudio {
	
	private String id;
	private String type;
	private String name;
	private String description;
	private String path;
	private String versionid;
	private String vars;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersionid() {
		return versionid;
	}

	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}

	public String getVars() {
		return vars;
	}

	public void setVars(String vars) {
		this.vars = vars;
	}

	public String save(HttpServletRequest request)
	{
		String result = "";
		String lastId = "";
		String typeElement = "";
		
		Form form = null;
		String id = request.getParameter("id");
		String type = request.getParameter("elementType");
		
		try{	

			id = request.getParameter("id");
			typeElement = request.getParameter("elementType");
			
			SingletonDAO.getInstance();
			br.com.gvt.telefonia.ura.diagram.model.Entity<?> entity = null;
			
			if(!request.getParameter("id").isEmpty())
				entity = SingletonDAO.getInstance().getAudioDAOInstance().findByPk(request.getParameter("id"));
			else
				entity = new Audio();
			
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
			
			SingletonDAO.getAudiovarDAOInstance().deleteAllByAudiovar(lastId);
			
			if(request.getParameter("type").equalsIgnoreCase("VAR"))
				insertVars(request.getParameter("vars"),lastId,request.getParameter("audioVarType"));
			
		} catch(Exception e)
		{
			e.getStackTrace();
		}
		
		return lastId;
	}
	
	public void insertVars(String insertVars, String lastId, String parameter)
	{
		String[] vars;
		String[] params;
		Audiovar audioVar = null;
		
		SingletonDAO.getInstance();
		
		String audioVarType = parameter;
		
		audioVar = new Audiovar();
		audioVar.setAudioid(lastId);
		audioVar.setParamname("formatname");
		audioVar.setParamvalue(audioVarType);
		audioVar.setVersionid(versionid);
		SingletonDAO.getAudiovarDAOInstance().insert(audioVar);
		
		if(!insertVars.isEmpty())
		{
			vars = insertVars.split("\\|");
			
			for(String var : vars)
			{
				params = var.split(":");
				audioVar = new Audiovar();
				audioVar.setAudioid(lastId);
				audioVar.setParamname(params[0]);
				audioVar.setParamvalue(params[1]);
				audioVar.setVersionid(versionid);
				SingletonDAO.getAudiovarDAOInstance().insert(audioVar);
			}
		}
	}
}
