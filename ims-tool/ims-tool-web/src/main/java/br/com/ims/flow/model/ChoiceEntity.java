package br.com.ims.flow.model;

import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class ChoiceEntity extends AbstractFormEntity implements Comparable<ChoiceEntity>{
	
	private String name;
	private String menuId;
	private String dtmf;
	private ConditionEntity condition;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getDtmf() {
		return dtmf;
	}
	public void setDtmf(String dtmf) {
		this.dtmf = dtmf;
	}

	public ConditionEntity getCondition() {
		return condition;
	}
	public void setCondition(ConditionEntity condition) {
		this.condition = condition;
	}
	@Override
	public int compareTo(ChoiceEntity o) {
		// TODO Auto-generated method stub
		if(this.dtmf != null && o.dtmf != null) {
			int a;
			int b;
			if(Pattern.matches("[0-9]", this.dtmf)) {
				a = Integer.valueOf(this.dtmf);
			} else {
				if(this.dtmf.equals("#")) {
					a = 10;
				} else {
					a = 11;
				}
			}
			if(Pattern.matches("[0-9]", o.dtmf)) {
				b = Integer.valueOf(o.dtmf);
			} else {
				if(o.dtmf.equals("#")) {
					b = 10;
				} else {
					b = 11;
				}
			}
			
			if(a < b) {
				return -1;
			} else if(a > b) {
				return 1;
			}
		}
		return 0;
	}
	
	
				
}
