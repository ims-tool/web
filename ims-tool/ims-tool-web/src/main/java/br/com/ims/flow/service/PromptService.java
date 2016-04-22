package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AnnounceEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.PromptCollectEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.model.TransferEntity;
import br.com.ims.flow.model.TransferRuleEntity;

@SuppressWarnings("serial")
public class PromptService extends AbstractEntityService<PromptEntity>{
	
	public List<PromptEntity> getAll() {
		
		return DAOFactory.getInstance().getPromptDAO().getAll();
	}
	public List<PromptEntity> getAll(boolean lazy) {
		
		return DAOFactory.getInstance().getPromptDAO().getAll(lazy);
	}
	
	public PromptEntity get(String id) {
		
		return DAOFactory.getInstance().getPromptDAO().get(id);
	}
	public PromptEntity getByName(String name) {
		
		return DAOFactory.getInstance().getPromptDAO().getByName(name);
	}
	
	public boolean save(PromptEntity prompt) {
		return DAOFactory.getInstance().getPromptDAO().save(prompt);
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub

		if ((id == null) || (id.length() == 0)) {
			return false;
		}
		List<AnnounceEntity> announces = DAOFactory.getInstance().getAnnounceDAO()
				.getByFilter("WHERE a.prompt ='" + id + "' ", true);
		if (announces.size() > 0) {
			return true;
		}
		List<PromptCollectEntity> promptCollects = DAOFactory.getInstance().getPromptCollectDAO()
				.getByFilter("WHERE pc.prompt = '" + id + "' ", true);
		if (promptCollects.size() > 0) {
			return true;
		}
		List<MenuEntity> menus = DAOFactory.getInstance().getMenuDAO().getByFilter("WHERE m.prompt = '" + id + "' ",true);
		if (menus.size() > 0) {
			return true;
		}
		List<TransferRuleEntity> transferRules = DAOFactory.getInstance().getTransferDAO()
				.getTransferRules("WHERE tr.prompt ='" + id + "' ",true);
		if (transferRules.size() > 0) {
			return true;
		}
		return false;
	}
	public List<String[]> getUsed(String id) {
		List<String[]> result = new ArrayList<String[]>();
		if ((id == null) || (id.length() == 0)) {
			return result;
		}
		List<AnnounceEntity> announces = DAOFactory.getInstance().getAnnounceDAO()
				.getByFilter("WHERE a.prompt ='" + id + "' ", true);
		
		if (announces.size() > 0) {
			for (AnnounceEntity announce : announces) {
				if ((announce.getPrompt() != null) && (announce.getPrompt().getId().equals(id))) {
					String[] obj = new String[] { "Announce", announce.getName() };
					result.add(obj);
				}
			}
		}
		List<PromptCollectEntity> promptCollects = DAOFactory.getInstance().getPromptCollectDAO()
				.getByFilter("WHERE pc.prompt = '" + id + "' ", true);

		if (promptCollects.size() > 0) {
			for (PromptCollectEntity promptCollect : promptCollects) {
				if ((promptCollect.getPrompt() != null) && (promptCollect.getPrompt().getId().equals(id))) {
					String[] obj = new String[] { "PromptCollect", promptCollect.getName() };
					result.add(obj);
				}
			}
		}
		List<MenuEntity> menus = DAOFactory.getInstance().getMenuDAO().getByFilter("WHERE m.prompt = '" + id + "' ", true);
		if (menus.size() > 0) {
			for (MenuEntity menu : menus) {
				String[] obj = { "Menu", menu.getName() };
				result.add(obj);
			}
		}
		List<TransferRuleEntity> transferRules = DAOFactory.getInstance().getTransferDAO()
				.getTransferRules("WHERE tr.prompt ='" + id + "' ",true);
		if (transferRules.size() > 0) {
			List<String> transferId = new ArrayList<String>();
			for (TransferRuleEntity rule : transferRules) {
				if (!transferId.contains(rule.getTransferId())) {
					transferId.add(rule.getTransferId());
					TransferEntity transfer = DAOFactory.getInstance().getTransferDAO().get(rule.getTransferId(), true);
					String[] obj = { "Transfer", transfer.getName() };
					result.add(obj);
				}
			}
		}
		return result;
	}

	@Override
	public boolean update(PromptEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getPromptDAO().update(object);
	}

	@Override
	public boolean delete(PromptEntity object) {
		// TODO Auto-generated method stub
		return DAOFactory.getInstance().getPromptDAO().delete(object);
	}

}
