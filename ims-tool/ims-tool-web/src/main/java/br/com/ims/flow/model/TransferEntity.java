package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class TransferEntity extends AbstractFormEntity{
	
	private List<TransferRuleEntity> transferRules;
	private TagEntity tag;
	private VersionEntity versionId;
	
	
	public List<TransferRuleEntity> getTransferRules() {
		return transferRules;
	}
	public void setTransferRules(List<TransferRuleEntity> transferRules) {
		this.transferRules = transferRules;
	}
	public TagEntity getTag() {
		return tag;
	}
	public void setTag(TagEntity tag) {
		this.tag = tag;
	}
	public VersionEntity getVersionId() {
		return versionId;
	}
	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}
	
	
	
		
}
