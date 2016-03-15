package br.com.ims.flow.service;

import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.DecisionChanceEntity;
import br.com.ims.flow.model.DecisionEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.PromptAudioEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TransferEntity;
import br.com.ims.flow.model.TransferRuleEntity;

public class ConditionService extends AbstractEntityService<ConditionEntity>{
	
	public List<ConditionEntity> getAll() {
		
		return DAOFactory.getInstance().getConditionDAO().getAll();
	}
	
	public ConditionEntity getByName(String name) {
		
		return DAOFactory.getInstance().getConditionDAO().getByName(name);
	}
	
	public ConditionEntity get(String id) {
		
		return DAOFactory.getInstance().getConditionDAO().get(id);
	}
	
	public boolean save(ConditionEntity entity) {
		return DAOFactory.getInstance().getConditionDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		List<FormEntity> forms = DAOFactory.getInstance().getFormDAO().getAll();
		for(FormEntity form :  forms) {
			
			
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_MENU)) {
				MenuEntity menu = (MenuEntity)form.getFormId();
				if(menu.getChoices() != null) {
					for(ChoiceEntity choice : menu.getChoices()) {
						if(choice.getCondition() != null &&
								choice.getCondition().getId().equals(id))
							return true;
					}
				}
				
			}
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_DECISION)) {
				DecisionEntity decision= (DecisionEntity)form.getFormId();
				if(decision.getListDecisionChance() != null) {
					for(DecisionChanceEntity chance : decision.getListDecisionChance()) {
						if(chance.getCondition() != null &&
								chance.getCondition().equals(id)) {
							return true;							
						}
					}
				}
			}
			if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_TRANSFER)) {
				TransferEntity  transfer = (TransferEntity)form.getFormId();
				if(transfer.getTransferRules() != null) {
					for(TransferRuleEntity rule : transfer.getTransferRules()) {
						if(rule.getCondition() != null &&
								rule.getCondition().getId().equals(id)) {
							return true;
						}
					}
				}
			}
		}
		List<PromptEntity> prompts = ServicesFactory.getInstance().getPromptService().getAll();
		for(PromptEntity prompt :  prompts) {
			if(prompt.getAudios() != null) {
				for(PromptAudioEntity audio : prompt.getAudios()) {
					if(audio.getCondition() != null && 
							audio.getCondition().getId().equals(id)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean update(ConditionEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getConditionDAO().update(entity);
		
	}

	@Override
	public boolean delete(ConditionEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getConditionDAO().delete(entity);
		
	}

}
