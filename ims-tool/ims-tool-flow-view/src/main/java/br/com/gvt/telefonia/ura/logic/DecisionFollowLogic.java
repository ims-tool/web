package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Decision;
import br.com.gvt.telefonia.ura.diagram.model.DecisionChance;
import br.com.gvt.telefonia.ura.diagram.model.DecisionGroup;
import br.com.gvt.telefonia.ura.diagram.model.Flow;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.FluxException;
import br.com.gvt.telefonia.ura.util.SingletonDAO;
import br.com.gvt.telefonia.ura.util.Util;

public class DecisionFollowLogic {
	
	private static Form form;
	private static List<FluxElement> listElements;
	private static FluxElement parent;
	
	public static void process(Form form,List<FluxElement> listElements, FluxElement parent) throws Exception
	{
		DecisionFollowLogic.form = form;
		DecisionFollowLogic.listElements = listElements;
		DecisionFollowLogic.parent = parent;
		
		try{
		DecisionFollowLogic.loadDecision(
				SingletonDAO.getDecisionDAOInstance().findByPk(
						Long.toString(form.getFormid())
				)
		);
		} catch(FluxException e){
			throw new FluxException(e.getMessage());
		}
		catch(Exception e){
			throw new FluxException("Decision "+form.getId());
		}
	}
	
	private static void loadDecision(Decision decision) throws Exception
	{
		FluxElement element = new FluxElement();
		element.setId(decision.getId());
		element.setName(decision.getName());
		element.setDescription(decision.getDescription());
		element.setForm(DecisionFollowLogic.form.getId());
		//element.setNext(form.getNextFormDefault());
		element.setType(Flow.class.getSimpleName());
		element.setFormName(form.getName());
		element.setNextFormDefault(form.getNextFormDefault());
		
		if(DecisionFollowLogic.listElements.size() > 0){
				if(DecisionFollowLogic.parent != null)
					element.setParent(DecisionFollowLogic.parent);
		} else {
			element.setDescription(decision.getName()+ "<br />" + element.getDescription());
		}
		
		
		if(element.getId() > 0){
			//	System.out.println(element.getId());
				//listElements.add(element);
				//FormActiveRecord next = new FormActiveRecord(element.getNext(),listElements,element);
				//next.loadFormType();
		}

		DecisionFollowLogic.loadDecisionGroup(decision.getId(),element);

	}
	
	private static void loadDecisionGroup(long decisionid, FluxElement elementDecision) throws Exception
	{
		FluxElement parentGroupElement = null;
		DecisionGroup dGroup = SingletonDAO.getDecisionGroupDAOInstance().findFirstByDecision(Long.toString(decisionid));
		List<DecisionChance> listDecisionChance = null;
		
		
			
			FluxElement element = new FluxElement();	
			listDecisionChance = SingletonDAO.getDecisionChanceDAOInstance().findByGroup(Long.toString(dGroup.getId()));
			element = new FluxElement();
			element.setName(dGroup.getDescription());
			element.setDescription(dGroup.getDescription());
			element.setId(dGroup.getId());
			element.setType(DecisionGroup.class.getSimpleName());
			element.setForm(elementDecision.getForm());
			element.setTag(elementDecision.getTag());
			element.setFormName(elementDecision.getName());
			element.setNextFormDefault(elementDecision.getNextFormDefault());
			element.setParent(parent);
	
			if(DecisionFollowLogic.listElements.size() == 0){
				element.setDescription(elementDecision.getName()+ "<br />" + element.getDescription());
			}
					
			if(element.getId() > 0){
				//	System.out.println(element.getId());
					listElements.add(element);
					//FormActiveRecord next = new FormActiveRecord(element.getNext(),listElements,element);
					//next.loadFormType();
				}
					/*
					if(element.getId() > 0){
					//	System.out.println(element.getId());
						listElements.add(element);
						//FormActiveRecord next = new FormActiveRecord(element.getNext(),listElements,element);
						//next.loadFormType();
					}*/
					
/*			for(DecisionChance opc : listDecisionChance)
			{
				
				opcElement = new FluxElement();
				opcElement.setId(opc.getId());
				
				if(opc.getValue1() == null)
					opc.setValue1("Não");
				else
					opc.setValue1("Sim");
				
				opcElement.setName(opc.getValue1());
				opcElement.setDescription(opc.getValue1());
				opcElement.setForm(form.getId());
				opcElement.setNext(opc.getNextformid());
				opcElement.setType(DecisionChance.class.getSimpleName());
				opcElement.setParent(element);
				opcElement.setTag(opc.getTag() + " - " + opcElement.getName());
				opcElement.setFormName(elementDecision.getName());
				opcElement.setNextFormDefault(elementDecision.getNextFormDefault());
			}*/
			FluxElement clone = null;
			for(DecisionChance opc : listDecisionChance)
			{
				try{
					if(!opc.getDecisiongroupchild().isEmpty())
					{
							//String valChance = (opc.getValue1() == null) ? "No" : opc.getValue1();
							clone = (FluxElement) element.clone();
							//clone.setTag(opc.getTag() + " - " + valChance);
							clone.setTag(Util.tagDecisionChance(opc));
							DecisionFollowLogic.loadDecisionGroupFind(Long.parseLong(opc.getDecisiongroupchild()),clone);	
							continue;
					} else {
							//listElements.add(opcElement);
							//String valChance = (opc.getValue1() == null) ? "No" : opc.getValue1();
							clone = (FluxElement) element.clone();
							//clone.setTag(opc.getTag() + " - " + valChance);
							clone.setTag(Util.tagDecisionChance(opc));
							FormLogic next = new FormLogic(opc.getNextformid(),DecisionFollowLogic.listElements,clone);
							next.loadFormType();
					}
				} catch(FluxException e){
					throw new FluxException(e.getMessage());
				}
				catch(Exception e){
					throw new FluxException("decisiongroup: " + opc.getDecisiongroupid());
				}
			}
			
			listDecisionChance =  null;
			parentGroupElement = element;
	}
	
	private static void loadDecisionGroupFind(long decisiongroupid, FluxElement elementDecision) throws Exception
	{
		DecisionGroup decisionGroup = SingletonDAO.getDecisionGroupDAOInstance().findByPk(Long.toString(decisiongroupid));
		FluxElement element = new FluxElement();
		
		List<DecisionChance> listDecisionChance = null;
		
		DecisionGroup dGroup = decisionGroup;
		
		listDecisionChance = SingletonDAO.getDecisionChanceDAOInstance().findByGroup(Long.toString(dGroup.getId()));
		element = new FluxElement();
		element.setName(dGroup.getDescription());
		element.setDescription(dGroup.getDescription());
		element.setId(dGroup.getId());
		element.setType(DecisionGroup.class.getSimpleName());
		element.setForm(elementDecision.getForm());
		element.setParent(elementDecision);
		element.setFormName(elementDecision.getName());
		element.setTag(elementDecision.getTag());
		element.setNextFormDefault(elementDecision.getNextFormDefault());

		if(element.getId() > 0){
			//	System.out.println(element.getId());
				listElements.add(element);
				//FormActiveRecord next = new FormActiveRecord(element.getNext(),listElements,element);
				//next.loadFormType();
			}
		/*
		if(element.getId() > 0){
		//	System.out.println(element.getId());
			listElements.add(element);
			//FormActiveRecord next = new FormActiveRecord(element.getNext(),listElements,element);
			//next.loadFormType();
		}*/
/*		
		
		for(DecisionChance opc : listDecisionChance)
		{
			
			opcElement = new FluxElement();
			opcElement.setId(opc.getId());
			
			if(opc.getValue1() == null)
				opc.setValue1("Não");
			else
				opc.setValue1("Sim");
			
			opcElement.setName(opc.getValue1());
			opcElement.setDescription(opc.getValue1());
			opcElement.setForm(form.getId());
			opcElement.setNext(opc.getNextformid());
			opcElement.setType(DecisionChance.class.getSimpleName());
			opcElement.setParent(element);
			opcElement.setTag(opc.getTag() + " - " + opcElement.getName());
			opcElement.setFormName(elementDecision.getName());
			opcElement.setNextFormDefault(elementDecision.getNextFormDefault());
		}*/
		
		FluxElement clone = null;
		for(DecisionChance opc : listDecisionChance)
		{
			try{
				if(!opc.getDecisiongroupchild().isEmpty())
				{
						element.setParent(elementDecision);
						//String valChance = (opc.getValue1() == null) ? "No" : opc.getValue1();
						clone = (FluxElement) element.clone();
						//clone.setTag(opc.getTag() + " - " + valChance);
						clone.setTag(Util.tagDecisionChance(opc));
						DecisionFollowLogic.loadDecisionGroupFind(Long.parseLong(opc.getDecisiongroupchild()),clone);	
						continue;
				} else {
						//listElements.add(opcElement);
						//String valChance = (opc.getValue1() == null) ? "No" : opc.getValue1();
						clone = (FluxElement) element.clone();
						//clone.setTag(opc.getTag() + " - " + valChance);
						clone.setTag(Util.tagDecisionChance(opc));
						FormLogic next = new FormLogic(opc.getNextformid(),DecisionFollowLogic.listElements,clone);
						next.loadFormType();
				}
			} catch(FluxException e){
				throw new FluxException(e.getMessage());
			}
			catch(Exception e){
				throw new FluxException("decisiongroup: " + opc.getDecisiongroupid());
			}
		}
	}
}
