package br.com.ims.flow.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.com.ims.facade.AudioFacadeREST;
import br.com.ims.flow.common.BaseBean;
import br.com.ims.tool.entity.Audio;


//ConfigurableBeanFactory.SCOPE_SINGLETON, ConfigurableBeanFactory.SCOPE_PROTOTYPE,
//WebApplicationContext.SCOPE_REQUEST, WebApplicationContext.SCOPE_SESSION
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
@Named (value= "mbSaveAudio")
public class MbSaveAudioBean extends BaseBean {
	
	private static final long serialVersionUID = 2920522296856799398L;
	
	
	@Inject
	private AudioFacadeREST audioService;
	
	@Inject
	private MbAudioBean mbAudioBean;
	
	private Audio audio;
	
	private String title;
	
	public MbSaveAudioBean() {
		this.audio = new Audio();
	}

	public Audio getAudio() {
		return this.audio;
	}

	public void setAudio(Audio audio) {
		this.audio = audio;
	}
	
	public void add() {
		this.title = "Add";
	}

	public void update() {
		this.audio = this.mbAudioBean.getSelectedAudio();
		this.title = "Update";
	}

	public void save() {
		if (this.audio != null) {
			if (this.audio.getId() == null) {
				// Add
				this.audioService.create(this.audio);
			} else {
				// Update
				this.audioService.edit(this.audio);
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	

}
