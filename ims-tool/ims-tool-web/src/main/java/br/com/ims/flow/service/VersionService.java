package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AudioEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.GrammarEntity;
import br.com.ims.flow.model.NoMatchInputEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.VersionEntity;

@SuppressWarnings("serial")
public class VersionService extends AbstractEntityService<VersionEntity>{
	
	public List<VersionEntity> getAll() {
		
		return DAOFactory.getInstance().getVersionDAO().getAll();
	}
	
	public ConditionEntity getByName(String name) {
		
		return DAOFactory.getInstance().getConditionDAO().getByName(name);
	}
	
	public VersionEntity get(String id) {
		
		return DAOFactory.getInstance().getVersionDAO().get(id);
	}
	
	public boolean save(VersionEntity entity) {
		return DAOFactory.getInstance().getVersionDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll(true);
		for(FormEntity form :  forms) {
			
			if(form.getVersionId() != null && form.getVersionId().equals(id)) {
				return true;
				
			}
			
		}
		List<NoMatchInputEntity> noMatchInputs = DAOFactory.getInstance().getNoMatchInputDAO().getAll(true);
		for(NoMatchInputEntity ni :  noMatchInputs) {
			
			if(ni.getVersionId() != null && ni.getVersionId().equals(id)) {
				return true;
				
			}
			
		}
		List<ConditionEntity> conditions = DAOFactory.getInstance().getConditionDAO().getAll();
		for(ConditionEntity condition :  conditions) {
			
			if(condition.getVersionId() != null && condition.getVersionId().equals(id)) {
				return true;
				
			}
			
		}
		List<PromptEntity> prompts = DAOFactory.getInstance().getPromptDAO().getAll(true);
		for(PromptEntity prompt :  prompts) {
			
			if(prompt.getVersionId() != null && prompt.getVersionId().equals(id)) {
				return true;
				
			}
			
		}
		List<AudioEntity> audios = DAOFactory.getInstance().getAudioDAO().getAll();
		for(AudioEntity audio :  audios) {
			
			if(audio.getVersionId() != null && audio.getVersionId().equals(id)) {
				return true;
				
			}
			
		}
		List<GrammarEntity> grammars = DAOFactory.getInstance().getGrammarDAO().getAll();
		for(GrammarEntity grammar :  grammars) {
			
			if(grammar.getVersionId() != null && grammar.getVersionId().equals(id)) {
				return true;
				
			}
			
		}
		
		return false;
	}
		
	@Override
	public boolean update(VersionEntity object) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean delete(VersionEntity object) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String[]> getUsed(String id) {
		// TODO Auto-generated method stub
		List<String []> result = new ArrayList<String[]>();
		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll(true);
		for(FormEntity form :  forms) {
			
			if(form.getVersionId() != null && form.getVersionId().equals(id)) {
				String [] obj = {form.getFormType().getName(),form.getName()};
				result.add(obj);
				
			}
			
		}
		List<NoMatchInputEntity> noMatchInputs = DAOFactory.getInstance().getNoMatchInputDAO().getAll();
		for(NoMatchInputEntity ni :  noMatchInputs) {
			
			if(ni.getVersionId() != null && ni.getVersionId().equals(id)) {
				String [] obj = {"NoMatchInput - "+ni.getType(),ni.getName()};
				result.add(obj);
				
			}
			
		}
		List<ConditionEntity> conditions = DAOFactory.getInstance().getConditionDAO().getAll(true);
		for(ConditionEntity condition :  conditions) {
			
			if(condition.getVersionId() != null && condition.getVersionId().equals(id)) {
				String [] obj = {"Condition",condition.getName()};
				result.add(obj);
				
			}
			
		}
		List<PromptEntity> prompts = DAOFactory.getInstance().getPromptDAO().getAll(true);
		for(PromptEntity prompt :  prompts) {
			
			if(prompt.getVersionId() != null && prompt.getVersionId().equals(id)) {
				String [] obj = {"Prompt",prompt.getName()};
				result.add(obj);
				
			}
			
		}
		List<AudioEntity> audios = DAOFactory.getInstance().getAudioDAO().getAll();
		for(AudioEntity audio :  audios) {
			
			if(audio.getVersionId() != null && audio.getVersionId().equals(id)) {
				String [] obj = {"Audio",audio.getName()};
				result.add(obj);
				
			}
			
		}
		List<GrammarEntity> grammars = DAOFactory.getInstance().getGrammarDAO().getAll();
		for(GrammarEntity grammar :  grammars) {
			
			if(grammar.getVersionId() != null && grammar.getVersionId().equals(id)) {
				String [] obj = {"Grammar",grammar.getName()};
				result.add(obj);
				
			}
			
		}
		
		return result;
	}

}
