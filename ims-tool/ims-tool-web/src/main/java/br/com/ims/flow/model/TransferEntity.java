package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class TransferEntity extends AbstractFormEntity{
	
	private List<TransferRuleEntity> transferRules;

	public List<TransferRuleEntity> getTransferRules() {
		return transferRules;
	}

	public void setTransferRules(List<TransferRuleEntity> transferRules) {
		this.transferRules = transferRules;
	}
	
	
	
	
	
		
}
