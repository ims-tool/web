package br.com.ims.flow.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.com.ims.facade.AudioFacadeREST;
import br.com.ims.flow.common.ObjectParser;
import br.com.ims.tool.entity.Audio;


@Scope(value=WebApplicationContext.SCOPE_SESSION)
@Named (value= "mbAudio")
public class MbAudioBean {
	

	@Inject
	private AudioFacadeREST audioService;
	private List<Audio> audios;
	private Integer id;
	private Audio selectedAudio;
	
	
	public MbAudioBean() {
		audios = new ArrayList<Audio>();
	}
	
	public void onLoad(){
		this.audios = this.audioService.findAll();
	}

	public List<Audio> getAudios() {
		return audios;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Audio getSelectedAudio() {
		return selectedAudio;
	}

	public void setSelectedAudio(Audio selectedAudio) {
		this.selectedAudio = selectedAudio;
	}
	
	public void selectAudio(SelectEvent evt) {
		try {
			if (evt.getObject() != null) {
				this.selectedAudio = ObjectParser.JSON_MAPPER.convertValue(evt.getObject(), Audio.class);
			} else {
				this.selectedAudio = null;
			}
		} catch (Exception e) {
			this.selectedAudio = null;
		}
		
	}
	
	public void unselectAudio(){
		this.selectedAudio = null;
	}
	
	public void delete(){
		if(selectedAudio != null){
			this.audioService.remove(selectedAudio.getId());
		}
	}
	
	
	

}
