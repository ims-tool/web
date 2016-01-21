package br.com.gvt.telefonia.ura.diagram.facade;

import br.com.gvt.telefonia.ura.diagram.model.Announce;
import br.com.gvt.telefonia.ura.util.SingletonDAO;


public class FacadeAnnounce {
	
	private String id;
	private String name;
	private String description;
	private String flushprompt;
	private String prompt;
	private String tag;
	
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

	public String getFlushprompt() {
		return flushprompt;
	}

	public void setFlushprompt(String flushprompt) {
		this.flushprompt = flushprompt;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public void save()
	{
		Announce announce = null;
		
		if(id != null)
			announce = SingletonDAO.getAnnounceDAOInstance().findByPk(id);
		else
			announce = new Announce();
		
		announce.setName(name);
		announce.setDescription(description);
		announce.setFlushprompt(flushprompt);
		announce.setPrompt(Long.parseLong(prompt));
		announce.setTag(tag);
		
		SingletonDAO.getAnnounceDAOInstance().save(announce);
		
	}
	
}