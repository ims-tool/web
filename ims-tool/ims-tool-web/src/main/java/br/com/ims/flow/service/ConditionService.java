package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.DecisionChanceEntity;
import br.com.ims.flow.model.DecisionEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.PromptAudioEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TransferEntity;
import br.com.ims.flow.model.TransferRuleEntity;

@SuppressWarnings("serial")
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

		if ((id == null) || (id.length() == 0)) {
			return false;
		}
		List<ChoiceEntity> choices = DAOFactory.getInstance().getMenuDAO()
				.getChoicesByFilter("WHERE c.condition = '" + id + "' ",true);
		if (choices.size() > 0) {
			return true;
		}
		List<DecisionChanceEntity> decisions = DAOFactory.getInstance().getDecisionDAO()
				.getChancesByFilter("WHERE dc.condition = '" + id + "' ",true);
		if (decisions.size() > 0) {
			return true;
		}
		List<TransferRuleEntity> transfers = DAOFactory.getInstance().getTransferDAO()
				.getTransferRules("WHERE tr.condition ='" + id + "' ",true);
		if (transfers.size() > 0) {
			return true;
		}
		List<PromptAudioEntity> audios = DAOFactory.getInstance().getPromptDAO()
				.getPromptAudio("WHERE pa.condition = '" + id + "' ",true);
		if (audios.size() > 0) {
			return true;
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

	@Override
	public List<String[]> getUsed(String id) {
		// TODO Auto-generated method stub
		List<String[]> result = new ArrayList<String[]>();
		if ((id == null) || (id.length() == 0)) {
			return result;
		}
		List<ChoiceEntity> choices = DAOFactory.getInstance().getMenuDAO()
				.getChoicesByFilter("WHERE c.condition = '" + id + "' ",true);
		MenuEntity menu;
		if (choices.size() > 0) {
			List<String> menuId = new ArrayList<String>();
			for (ChoiceEntity choice : choices) {
				if (!menuId.contains(choice.getMenuId())) {
					menu = DAOFactory.getInstance().getMenuDAO().get(choice.getMenuId(), true);
					String[] object = { "Menu", menu.getName() };
					result.add(object);
				}
			}
		}
		List<DecisionChanceEntity> decisions = DAOFactory.getInstance().getDecisionDAO()
				.getChancesByFilter("WHERE dc.condition = '" + id + "' ",true);
		DecisionEntity decision;
		if (decisions.size() > 0) {
			List<String> decisionId = new ArrayList<String>();
			for (DecisionChanceEntity chance : decisions) {
				if (!decisionId.contains(chance.getDecisionId())) {
					decision = DAOFactory.getInstance().getDecisionDAO().get(chance.getDecisionId(), true);
					String[] object = { "Decision", decision.getName() };
					result.add(object);
				}
			}
		}
		List<TransferRuleEntity> transfers = DAOFactory.getInstance().getTransferDAO()
				.getTransferRules("WHERE tr.condition ='" + id + "' ",true);
		TransferEntity transfer;
		if (transfers.size() > 0) {
			List<String> transferId = new ArrayList<String>();
			for (TransferRuleEntity rule : transfers) {
				if (!transferId.contains(rule.getTransferId())) {
					transfer = DAOFactory.getInstance().getTransferDAO().get(rule.getTransferId(), true);
					String[] object = { "Transfer", transfer.getName() };
					result.add(object);
				}
			}
		}
		List<PromptAudioEntity> audios = DAOFactory.getInstance().getPromptDAO().getPromptAudio("WHERE pa.condition = '" + id + "' ",true);
		if (audios.size() > 0) {
			List<String> promptId = new ArrayList<String>();
			for (PromptAudioEntity pa : audios) {
				if (!promptId.contains(pa.getPromptId())) {
					PromptEntity prompt = DAOFactory.getInstance().getPromptDAO().get(pa.getPromptId(), true);
					String[] object = { "Prompt", prompt.getName() };
					result.add(object);
				}
			}
		}
		return result;
	}

}
