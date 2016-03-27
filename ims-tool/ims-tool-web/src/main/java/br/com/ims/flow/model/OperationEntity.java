package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class OperationEntity extends AbstractFormEntity{
	
	private List<OperationGroupEntity> listOperationGroup;
	
	
	
	
	public List<OperationGroupEntity> getListOperationGroup() {
		return listOperationGroup;
	}
	public void setListOperationGroup(List<OperationGroupEntity> listOperationGroup) {
		this.listOperationGroup = listOperationGroup;
	}

	

	
	

	
	
}
