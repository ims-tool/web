package br.com.gvt.telefonia.ura.diagram.facade;

import java.util.ArrayList;
import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Choice;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.Menu;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class FacadeMenu {
	
	private String id;
	private String name;
	private String description;
	private String noinput;
	private String nomatch;
	private String prompt;
	private String versionid;
	private List<Choice> choice;
	
	public String getVersionid() {
		return versionid;
	}

	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public FacadeMenu()
	{
		choice = new ArrayList<Choice>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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


	public String getNoinput() {
		return noinput;
	}


	public void setNoinput(String noinput) {
		this.noinput = noinput;
	}


	public String getNomatch() {
		return nomatch;
	}


	public void setNomatch(String nomatch) {
		this.nomatch = nomatch;
	}


	public String getPrompt() {
		return prompt;
	}


	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}


	public List<Choice> getChoice() {
		return choice;
	}


	public void setChoice(List<Choice> choice) {
		this.choice = choice;
	}
	
	public boolean save()
	{
		String insertId = "";
		
		Menu menu = new Menu();
		menu.setName(name);
		menu.setDescription(description);
		menu.setNoinput(Long.parseLong(noinput));
		menu.setNomatch(Long.parseLong(nomatch));
		menu.setPrompt(Long.parseLong(prompt));
		menu.setVersionid(versionid);
		
		SingletonDAO.getInstance();
		
		if(id != null && !id.isEmpty()){
			insertId = id;
			menu.setId(Long.parseLong(id));
			SingletonDAO.getMenuDAOInstance().update(menu);
		} else {
			insertId = SingletonDAO.getMenuDAOInstance().insert(menu);
		}
		
		SingletonDAO.getChoiceDAOInstance().delete(" menu = '"+insertId+"'");
		
		for(Choice obj : choice){
			obj.setMenu(Long.parseLong(insertId));
			obj.setVersionid(versionid);
			SingletonDAO.getChoiceDAOInstance().insert(obj);
		}
		
		Form form = null;
		
		form = SingletonDAO.getFormDAOInstance().findByFormId(insertId, Integer.toString(Form.FT_MENU));
		if(form == null)
			form = new Form();
		
		form.setName(name);
		form.setDescription(description);
		form.setFormType(Form.FT_MENU);
		form.setFormid(Long.parseLong(insertId));
		form.setVersionid(versionid);
		SingletonDAO.getFormDAOInstance().save(form);
		
		return true;
	}
	
}
