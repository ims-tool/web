package br.com.gvt.telefonia.ura.diagram.dao;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Audiovar;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class AudiovarDAO extends DAO<Audiovar> {
	@Override
	public String getClassName() {
		return Audiovar.class.getSimpleName();
	}
	
	public List<Audiovar> getAllByAudio(String id)
	{
		return findAllBy(" audioid = '"+id+"' ");
	}
	
	public void deleteAllByAudiovar(String id)
	{
		SingletonDAO.getAudiovarDAOInstance().delete(" audioid = '"+id+"' ");
	}
}
