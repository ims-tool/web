package br.com.gvt.telefonia.ura.diagram.dao;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.ConditionValue;

public class ConditionValueDAO extends DAO<ConditionValue> {

	@Override
	public String getClassName() {
		return ConditionValue.class.getSimpleName();
	}
	
	public List<ConditionValue> findByConditionGroup(String group)
	{
		return findAllBy(" conditiongroupid = '"+group+"' order by ordernum asc");
	}

	
}
