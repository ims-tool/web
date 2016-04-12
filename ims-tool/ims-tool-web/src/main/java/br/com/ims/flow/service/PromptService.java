package br.com.ims.flow.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.factory.DAOFactory;
import br.com.ims.flow.model.AnnounceEntity;
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

		List<AnnounceEntity> announces = DAOFactory.getInstance().getAnnounceDAO().getAll();
		for(AnnounceEntity announce: announces) {
			if(announce.getPrompt() != null && announce.getPrompt().getId().equals(id)) {
				return true;
			}
		}
		List<PromptCollectEntity> promptCollects = DAOFactory.getInstance().getPromptCollectDAO().getAll();
		for(PromptCollectEntity promptCollect: promptCollects) {
			if(promptCollect.getPrompt() != null && promptCollect.getPrompt().getId().equals(id)) {
				return true;
			}
		}
		List<TransferEntity> Transfers = DAOFactory.getInstance().getTransferDAO().getAll();
		for(TransferEntity transfer: Transfers) {
			if(transfer.getTransferRules() != null) {
				for(TransferRuleEntity transferRule : transfer.getTransferRules()) {
					if(transferRule.getPrompt() != null && transferRule.getPrompt().getId().equals(id)) {
						return true;
					}
				}
			}
		}
		
		
		return false;
	}
	public List<String[]> getUsed(String id) {
		List<String[]> result = new ArrayList<String[]>();
		List<AnnounceEntity> announces = DAOFactory.getInstance().getAnnounceDAO().getAll();
		for(AnnounceEntity announce: announces) {
			if(announce.getPrompt() != null && announce.getPrompt().getId().equals(id)) {
				String [] obj = {"Announce",announce.getName()};
				result.add(obj);
			}
		}
		List<PromptCollectEntity> promptCollects = DAOFactory.getInstance().getPromptCollectDAO().getAll();
		for(PromptCollectEntity promptCollect: promptCollects) {
			if(promptCollect.getPrompt() != null && promptCollect.getPrompt().getId().equals(id)) {
				String [] obj = {"PromptCollect",promptCollect.getName()};
				result.add(obj);
			}
		}
		List<TransferEntity> Transfers = DAOFactory.getInstance().getTransferDAO().getAll();
		for(TransferEntity transfer: Transfers) {
			boolean found = false;
			
			if(transfer.getTransferRules() != null) {
				for(int index  = 0; index < transfer.getTransferRules().size() && !found; index++) {
					TransferRuleEntity transferRule = transfer.getTransferRules().get(index);
					if(transferRule.getPrompt() != null && transferRule.getPrompt().getId().equals(id)) {
						String [] obj = {"Transfer",transfer.getName()};
						result.add(obj);
						found = true;
					}
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
