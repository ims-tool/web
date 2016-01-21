package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Condition;
import br.com.gvt.telefonia.ura.diagram.model.ConditionGroup;
import br.com.gvt.telefonia.ura.diagram.model.ConditionValue;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.FluxException;
import br.com.gvt.telefonia.ura.util.SingletonDAO;
import br.com.gvt.telefonia.ura.util.Util;

public class ConditionLogic {
	
	private static List<FluxElement> listElements;
	private static FluxElement parent;
	
	public static void process(String id,List<FluxElement> listElements) throws Exception
	{
		ConditionLogic.listElements = listElements;
		
		try{
			SingletonDAO.getInstance();
			ConditionLogic.loadCondition( SingletonDAO.getConditionDAOInstance().findByPk(id) );
		} catch(FluxException e){
			throw new FluxException(e.getMessage());
		}
		catch(Exception e){
			throw new FluxException("Condition "+id);
		}
	}
	
	private static void loadCondition(Condition condition) throws Exception
	{
		ConditionLogic.loadConditionGroup(condition.getId());
	}
	
	private static void loadConditionGroup(long conditionid) throws Exception
	{
		List<ConditionGroup> listGroup = SingletonDAO.getConditionGroupDAOInstance().findAllByCondition(Long.toString(conditionid));
		
		for(ConditionGroup cGroup: listGroup)
		{
			List<ConditionValue> listConditionValue = null;
		
			FluxElement element = new FluxElement();
			element = new FluxElement();
			element.setName(cGroup.getDescription());
			element.setDescription(cGroup.getDescription());
			element.setId(cGroup.getId());
			element.setType(ConditionGroup.class.getSimpleName());
			if(parent != null)
				element.setParent((FluxElement) parent.clone());
			
			element.setAux("noNext");
	
					
			if(element.getId() > 0){
				//	System.out.println(element.getId());
					listElements.add(element);
					parent = element;
					//FormActiveRecord next = new FormActiveRecord(element.getNext(),listElements,element);
					//next.loadFormType();
				}
					
			FluxElement conditionValue = null;
			FluxElement conditionValueFalse = null;
			listConditionValue = SingletonDAO.getConditionValueDAOInstance().findByConditionGroup(Long.toString(cGroup.getId()));
			for(ConditionValue cValue : listConditionValue)
			{
				conditionValue = new FluxElement();
				conditionValue.setId(cValue.getId()+Long.parseLong(cValue.getTagtrue()));
				conditionValue.setName("TAG TRUE " + cValue.getTagtrue() + " " + cValue.getOperation() + " " + Util.getValues(cValue));
				conditionValue.setDescription("TAG TRUE " + cValue.getTagtrue() + " " + cValue.getOperation() + " " + Util.getValues(cValue));
				conditionValue.setType(ConditionValue.class.getSimpleName());
				conditionValue.setTag("TAG TRUE " + cValue.getTagtrue() + " " + cValue.getOperation() + " " + Util.getValues(cValue));
				conditionValue.setFormName(cGroup.getDescription());
				conditionValue.setParent(element);
				conditionValue.setAux("noNext");
				
				conditionValueFalse = new FluxElement();
				conditionValueFalse.setId(cValue.getId()+Long.parseLong(cValue.getTagfalse()));
				conditionValueFalse.setName("TAG FALSE " + cValue.getTagfalse() + " " + cValue.getOperation() + " " + Util.getValues(cValue));
				conditionValueFalse.setDescription("TAG FALSE " + cValue.getTagfalse() + " " + cValue.getOperation() + " " + Util.getValues(cValue));
				conditionValueFalse.setType(ConditionValue.class.getSimpleName());
				conditionValueFalse.setTag("TAG FALSE " + cValue.getTagfalse() + " " + cValue.getOperation() + " " + Util.getValues(cValue));
				conditionValueFalse.setFormName(cGroup.getDescription());
				conditionValueFalse.setParent(element);
				conditionValueFalse.setAux("noNext");
				
				parent = conditionValueFalse;
				
				listElements.add(conditionValue);
				listElements.add(conditionValueFalse);
				
				
			}
			
		}
	}
}
