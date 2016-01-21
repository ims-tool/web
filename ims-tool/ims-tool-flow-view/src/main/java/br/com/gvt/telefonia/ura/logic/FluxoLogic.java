package br.com.gvt.telefonia.ura.logic;

import java.util.ArrayList;
import java.util.List;

import br.com.gvt.telefonia.ura.export.IExport;
import br.com.gvt.telefonia.ura.util.Errors;
import br.com.gvt.telefonia.ura.util.FluxElement;

public class FluxoLogic {
	
	private IExport output;
	private List<FluxElement> listElements;
	
	private boolean isFirst = true;
	private String formFoco = "";
	private String formFocoType = "";
	
	public FluxoLogic(IExport output)
	{
		this.output = output;
		this.listElements = new ArrayList<FluxElement>();
	}
	
	private String checkErrors()
	{
		String errors = "";
		if(Errors.getInstance().getErrors().size() > 0)
			for(String error : Errors.getInstance().getErrors())
				errors += error+"<br>";
		
		if(!errors.equalsIgnoreCase(""))
			return "Errors["+errors+"]";
		else
			return "";
		
	}
	
	public String export(String formId) throws Exception
	{
		FormLogic form = new FormLogic(formId,listElements);
		form.loadFormType();
		String result = output.output(listElements);
		result += checkErrors();
		
		return result;
	}
	
	public String export(long formId) throws Exception
	{
		FormLogic form = new FormLogic(formId,listElements);
		form.loadFormType();
		String result = output.output(listElements);
		result += checkErrors();
		
		return result;
	}

	public String exportCondition(String id) throws Exception {
		ConditionLogic.process(id,listElements);
		String result = output.output(listElements);
		
		return result;
	}
}
