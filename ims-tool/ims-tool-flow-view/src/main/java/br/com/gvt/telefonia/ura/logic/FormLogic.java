package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Choice;
import br.com.gvt.telefonia.ura.diagram.model.DecisionChance;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.NovaUraSingleton;
import br.com.gvt.telefonia.ura.util.SingletonDAO;
import br.com.gvt.telefonia.ura.util.Util;

public class FormLogic {
	
	private Form form;
	public FluxElement parent;
	private List<FluxElement> listElements;
	private boolean referenciaCircular = false;
	private int limit;
	
	
	public FormLogic(String name,List<FluxElement> listElements)
	{
		
		this.form = SingletonDAO.getFormDAOInstance().findByName(name);
		this.listElements = listElements;
		
		init();
	}
	
	public FormLogic(String name,List<FluxElement> listElements,FluxElement parent)
	{
		
		this.form = SingletonDAO.getFormDAOInstance().findByName(name);
		this.listElements = listElements;
		this.parent = parent;
		init();
	}
	
	public FormLogic(long id,List<FluxElement> listElements)
	{
		this.form = SingletonDAO.getFormDAOInstance().findByPk(Long.toString(id));
		this.listElements = listElements;
		init();
	}
	
	public FormLogic(long id,List<FluxElement> listElements,FluxElement parent)
	{
		this.form = SingletonDAO.getFormDAOInstance().findByPk(Long.toString(id));
		this.listElements = listElements;
		this.parent = parent;
		init();
	}
	
	private boolean goDeep()
	{
		int count = 0;
		FluxElement auxParent = this.parent;
		if(this.parent != null)
			while(auxParent != null)
			{
				auxParent = auxParent.getParent();
				if(auxParent != null){
					if(auxParent.getType().equals(DecisionChance.class.getSimpleName()) || auxParent.getType().equals(Choice.class.getSimpleName()))
						continue;
					
					count++;
				}
			}
		
		if(count >= limit)
			return false;
		else
			return true;
	}
	
	private void init()
	{	
		limit = NovaUraSingleton.getInstance().getLevel();
		
		
		for(FluxElement obj : listElements){
			String parentName = obj.getUniqueId();
			if(obj.getParent() != null)
				parentName += obj.getParent().getId();
			
			String parentNameCompare = Util.getFormType(form.getFormType())+form.getId();
			if(parent != null)
				parentNameCompare += parent.getId();
			
			if(parentName.equalsIgnoreCase(parentNameCompare))
					referenciaCircular = true;
		}
		if(referenciaCircular){
			referenciaCircular = false;
			return;
		}
	}
	
	public FluxElement loadFormType() throws Exception
	{
		
		FluxElement element = new FluxElement();
		
		if(!goDeep()) return element;
		if(form.getId() == -1 || form.getId() == 0) return element;
		if(referenciaCircular) return element;
		
		try{
			switch( Integer.parseInt(Long.toString(form.getFormType())) )
			{
				case Form.FT_ANNOUNCE: 		AnnounceLogic.process(this.form, listElements,parent); 		break;
				case Form.FT_PROMPTCOLLECT: PromptCollectLogic.process(this.form, listElements,parent); break;
				case Form.FT_MENU: 			MenuLogic.process(this.form, listElements,parent); 			break;
				case Form.FT_FLOW:			FlowLogic.process(this.form, listElements,parent); 			break;
				case Form.FT_DECISION: 		DecisionLogic.process(this.form, listElements,parent); 		break;
				case Form.FT_OPERATION:		OperationLogic.process(form, listElements, parent); 		break;
				case Form.FT_TRANSFER: 		TransferLogic.process(form, listElements, parent);			break;
				case Form.FT_DISCONNECT:	DisconnectLogic.process(form, listElements, parent);		break;
				case Form.FT_FLOWINTERNAL: 	FlowInternalLogic.process(form, listElements, parent); 		break;
				case Form.FT_THREAD:		System.out.println("thread");								break;
			}
		} catch(Exception e)
		{
			listElements.get(listElements.size()-1).setNext(0);
			
			/*if(StringUtils.isNumeric(e.getMessage()))
				throw new Exception(e.getMessage());
			else
				throw new Exception(Long.toString(form.getId()));*/
		}
		return element;
	}
	
}
