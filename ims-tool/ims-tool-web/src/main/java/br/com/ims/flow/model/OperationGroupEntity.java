package br.com.ims.flow.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class OperationGroupEntity extends AbstractEntity{
	
	private Integer orderNum;
	private OperationMapEntity operationMap;
	private String description;	
	private List<OperationParameterEntity> listOperationParameters;
	
	public OperationGroupEntity() {
		listOperationParameters = new ArrayList<OperationParameterEntity>();
	}

	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public OperationMapEntity getOperationMap() {
		return operationMap;
	}

	public void setOperationMap(OperationMapEntity operationMap) {
		this.operationMap = operationMap;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<OperationParameterEntity> getListOperationParameters() {
		return listOperationParameters;
	}

	public void setListOperationParameters(List<OperationParameterEntity> listOperationParameters) {
		this.listOperationParameters = listOperationParameters;
	}
		
	
}
