package br.com.gvt.telefonia.ura.diagram.dao;

import br.com.gvt.telefonia.ura.diagram.model.Audio;

public class AudioDAO extends DAO<Audio> {
	@Override
	public String getClassName() {
		return Audio.class.getSimpleName();
	}
}
