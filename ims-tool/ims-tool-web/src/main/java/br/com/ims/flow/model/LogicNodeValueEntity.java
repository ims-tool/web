package br.com.ims.flow.model;


@SuppressWarnings("serial")
public class LogicNodeValueEntity extends AbstractEntity implements Comparable <LogicNodeValueEntity>{
	
	private String logicOperationId;
	private Integer orderNum;
	private Integer resultService;
	private String resultContext;
	private String operation;
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String value5;
	private String value6;
	private String value7;
	private String value8;
	private String value9;
	private String value10;
	private TagEntity tagTrue;
	private TagEntity tagFalse;
	
	public LogicNodeValueEntity() {
		this.operation = "=";
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public String getValue6() {
		return value6;
	}
	public void setValue6(String value6) {
		this.value6 = value6;
	}
	public String getValue7() {
		return value7;
	}
	public void setValue7(String value7) {
		this.value7 = value7;
	}
	public String getValue8() {
		return value8;
	}
	public void setValue8(String value8) {
		this.value8 = value8;
	}
	public String getValue9() {
		return value9;
	}
	public void setValue9(String value9) {
		this.value9 = value9;
	}
	public String getValue10() {
		return value10;
	}
	public void setValue10(String value10) {
		this.value10 = value10;
	}
	
	public TagEntity getTagTrue() {
		return tagTrue;
	}
	public void setTagTrue(TagEntity tagTrue) {
		this.tagTrue = tagTrue;
	}
	public TagEntity getTagFalse() {
		return tagFalse;
	}
	public void setTagFalse(TagEntity tagFalse) {
		this.tagFalse = tagFalse;
	}
	@Override
	public int compareTo(LogicNodeValueEntity arg0) {
		// TODO Auto-generated method stub
		if( this.orderNum < arg0.getOrderNum()) {
			return -1;
		} else if(this.orderNum > arg0.getOrderNum()) {
			return 1;
		}
		return 0;
	}





	public String getLogicOperationId() {
		return logicOperationId;
	}





	public void setLogicOperationId(String logicOperationId) {
		this.logicOperationId = logicOperationId;
	}









	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getResultService() {
		return resultService;
	}





	public void setResultService(Integer resultService) {
		this.resultService = resultService;
	}





	public String getResultContext() {
		return resultContext;
	}





	public void setResultContext(String resultContext) {
		this.resultContext = resultContext;
	}
	
	
	
	
}
