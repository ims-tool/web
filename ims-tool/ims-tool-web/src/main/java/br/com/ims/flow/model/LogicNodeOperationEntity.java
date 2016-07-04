package br.com.ims.flow.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LogicNodeOperationEntity extends AbstractEntity implements Comparable<LogicNodeOperationEntity> {
	
	private Integer orderNum;
	private String logicNodeId;
	private String operation;

	private List<LogicNodeValueEntity> listLogicNodeValues;
	
	public LogicNodeOperationEntity() {
		listLogicNodeValues = new ArrayList<LogicNodeValueEntity>();
	}
	
	
	public Integer getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}


	public String getLogicNodeId() {
		return logicNodeId;
	}
	public void setLogicNodeId(String logicNodeId) {
		this.logicNodeId = logicNodeId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<LogicNodeValueEntity> getListLogicNodeValues() {
		return listLogicNodeValues;
	}

	public void setListLogicNodeValues(List<LogicNodeValueEntity> listLogicNodeValues) {
		this.listLogicNodeValues = listLogicNodeValues;
	}
	
	@Override
	public int compareTo(LogicNodeOperationEntity arg0) {
		// TODO Auto-generated method stub
		if( this.orderNum < arg0.getOrderNum()) {
			return -1;
		} else if(this.orderNum > arg0.getOrderNum()) {
			return 1;
		}
		return 0;
	}
	
	
}
