package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class PromptEntity extends AbstractEntity{
	
	private String name;
	private VersionEntity versionId;
	private List<PromptAudioEntity> audios;
	
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public VersionEntity getVersionId() {
		return versionId;
	}
	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}
	public List<PromptAudioEntity> getAudios() {
		return audios;
	}
	public void setAudios(List<PromptAudioEntity> audios) {
		this.audios = audios;
	}
	
	
			
}
