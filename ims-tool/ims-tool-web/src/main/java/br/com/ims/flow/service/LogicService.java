package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.LogicEntity;

@SuppressWarnings("serial")
public class LogicService extends AbstractEntityService<LogicEntity>{
	
	public List<LogicEntity> getAll() {
		
		return DAOFactory.getInstance().getLogicDAO().getAll();
	}
	
	public LogicEntity getByName(String name) {
		
		return DAOFactory.getInstance().getLogicDAO().getByName(name);
	}
	
	public LogicEntity get(String id) {
		
		return DAOFactory.getInstance().getLogicDAO().get(id);
	}
	
	public boolean save(LogicEntity entity) {
		return DAOFactory.getInstance().getLogicDAO().save(entity);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		return false;
		/*
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
		}*/
	}

	@Override
	public boolean update(LogicEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getLogicDAO().update(entity);
		
	}

	@Override
	public boolean delete(LogicEntity entity) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getLogicDAO().delete(entity);
		
	}

	@Override
	public List<String[]> getUsed(String id) {
		// TODO Auto-generated method stub
		List<String[]> result = new ArrayList<String[]>();
		return result;
		/*
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
		}*/
		
	}

}
