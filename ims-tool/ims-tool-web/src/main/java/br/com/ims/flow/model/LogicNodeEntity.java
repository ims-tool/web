package br.com.ims.flow.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LogicNodeEntity extends AbstractEntity{
	
	private Integer orderNum;
	private String logicId;
	private String name;
	private String description;
	private LogicMapEntity logicMap;
	private String gotoFalse;
	private TagEntity tagFalse;
	private String gotoTrue;
	private TagEntity tagTrue;
		
	private List<LogicNodeOperationEntity> listOperation;
	private List<LogicNodeParameterEntity> listParameter;
	
	private boolean delete;
	
	public LogicNodeEntity() {
		listOperation = new ArrayList<LogicNodeOperationEntity>();
		listParameter = new ArrayList<LogicNodeParameterEntity>();
		delete = false;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getLogicId() {
		return logicId;
	}
	public void setLogicId(String logicId) {
		this.logicId = logicId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LogicMapEntity getLogicMap() {
		return logicMap;
	}
	public void setLogicMap(LogicMapEntity logicMap) {
		this.logicMap = logicMap;
	}
	public String getGotoFalse() {
		return gotoFalse;
	}
	public void setGotoFalse(String gotoFalse) {
		this.gotoFalse = gotoFalse;
	}
	public TagEntity getTagFalse() {
		return tagFalse;
	}
	public void setTagFalse(TagEntity tagFalse) {
		this.tagFalse = tagFalse;
	}
	public String getGotoTrue() {
		return gotoTrue;
	}
	public void setGotoTrue(String gotoTrue) {
		this.gotoTrue = gotoTrue;
	}
	public TagEntity getTagTrue() {
		return tagTrue;
	}
	public void setTagTrue(TagEntity tagTrue) {
		this.tagTrue = tagTrue;
	}
	public List<LogicNodeOperationEntity> getListOperation() {
		return listOperation;
	}
	public void setListOperation(List<LogicNodeOperationEntity> listOperation) {
		this.listOperation = listOperation;
	}
	public List<LogicNodeParameterEntity> getListParameter() {
		return listParameter;
	}
	public void setListParameter(List<LogicNodeParameterEntity> listParameter) {
		this.listParameter = listParameter;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	
	
	
}
