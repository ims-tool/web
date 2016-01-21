package br.com.gvt.telefonia.ura.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import br.com.gvt.telefonia.ura.diagram.model.Announce;
import br.com.gvt.telefonia.ura.diagram.model.Choice;
import br.com.gvt.telefonia.ura.diagram.model.Condition;
import br.com.gvt.telefonia.ura.diagram.model.Decision;
import br.com.gvt.telefonia.ura.diagram.model.DecisionChance;
import br.com.gvt.telefonia.ura.diagram.model.DecisionGroup;
import br.com.gvt.telefonia.ura.diagram.model.Flow;
import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.diagram.model.Menu;
import br.com.gvt.telefonia.ura.report.FormInfo;
import br.com.gvt.telefonia.ura.report.FormReport;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.NovaUraSingleton;
import br.com.gvt.telefonia.ura.util.SingletonDAO;
import br.com.gvt.telefonia.ura.util.Util;


public class MermaidExport implements IExport {

	
	private boolean isFirst = true;
	private String formFoco = "";
	private String formFocoType = "";
	
	private Map<Long, FluxElement> auxElementObj = new HashMap<Long, FluxElement>();;
	
	@Override
	public String output(List<FluxElement> listElements) { 
		
		String auxDesc[];
		String finalDesc = "";
		for(FluxElement element : listElements)
		{
			element.setDescription(element.getDescription().replace("\n", "<br />"));
			element.setDescription(WordUtils.wrap(element.getDescription(),250,"<br />",false));
			element.setDescription(element.getDescription().replaceAll("<a.*(<br />).*</a>", " "));
		}
		
		List<String> resultString = new ArrayList<String>();
		for(FluxElement element : listElements)
			if(element.getId() > 0){
					
					resultString.add(pattern(element));
			}
		
		for(FluxElement element : listElements)
			if(element.getId() > 0){
				resultString.add(patternColor(element));
				resultString.add(checkNextForm(element));
			}
		
		HashSet<String> uniqueValues = new HashSet<String>(resultString);
		String result = StringUtils.join(uniqueValues,"");
		
		return result.replaceAll("\\p{C}", "");
	}
	
	private String treatmentPattern(FluxElement element)
	{
		String result = "";
		
		if(element.getName() != null){
			element.setName(element.getName().replace("(", ""));
			element.setName(element.getName().replace(")", ""));
			element.setName(element.getName().replace("[", ""));
			element.setName(element.getName().replace("]", ""));
		}
		
		if(element.getDescription() != null){
			element.setDescription(element.getDescription().replace("(", ""));
			element.setDescription(element.getDescription().replace(")", ""));
			element.setDescription(element.getDescription().replace("[", ""));
			element.setDescription(element.getDescription().replace("]", ""));
		}
		
		
		String link ="";
		if(NovaUraSingleton.getInstance().getTempo() == null)
			link = "/telefonia-ivr-flow-editor/Main?form="+element.getForm();
		else
			link = "/telefonia-ivr-flow-editor/Main?form="+element.getForm()+"&tempo="+NovaUraSingleton.getInstance().getTempo();
		
		if(element.getType().equalsIgnoreCase(Decision.class.getSimpleName()) || element.getType().equalsIgnoreCase(DecisionGroup.class.getSimpleName())){
			result = "{<a id=\""+element.getId()+"\" elementType=\""+element.getType()+"\" href=\""+link+"\" class=\"link decision\">"+ element.getDescription()+"</a>}";
			//result += "style "+element.getId()+" fill:#C6FF8D;\n";
			
		} else if(element.getType().equalsIgnoreCase(Announce.class.getSimpleName()) || element.getType().equalsIgnoreCase(Menu.class.getSimpleName())){
			result = "[<a id=\""+element.getId()+"\" elementType=\""+element.getType()+"\" href=\""+link+"\">--"+element.getName()+"--<br />"+element.getDescription()+"</a>]";
			
		} else if(element.getType().equalsIgnoreCase(Choice.class.getSimpleName())){
			
			SingletonDAO.getInstance();
			String conditionName = null;
			Choice choice = (Choice) SingletonDAO.getChoiceDAOInstance().findByPk(Long.toString(element.getId()));
			String condition = Util.conditionClause(choice.getCondition());
			
			if(condition.isEmpty())
				result = "((<a id=\""+element.getId()+"\" elementType=\""+element.getType()+"\" href=\""+link+"\">"+ element.getDescription()+"</a>))";
			else{
				conditionName = SingletonDAO.getConditionDAOInstance().findByPk(choice.getCondition()).getName();
				result = "((<a id=\""+element.getId()+"\" elementName=\""+conditionName+"\" elementType=\""+element.getType()+"\" href=\""+link+"\" data-toggle=\"tooltip\" data-placement=\"top\" title=\""+condition+"\">"+ element.getDescription()+"</a>))";
			}
			
		} else if(element.getType().equalsIgnoreCase(DecisionChance.class.getSimpleName())){
			
			String aux = (element.getDescription() == null) ? "N�o" : element.getDescription();
			result = "(<a id=\""+element.getId()+"\" elementType=\""+element.getType()+"\" href=\""+link+"\">"+aux+"</a>)";
			
		} else {
			result = "(<a id=\""+element.getId()+"\" elementType=\""+element.getType()+"\" href=\""+link+"\">"+  element.getDescription()+"</a>)";
		}
		
		return element.getId()+element.getType()+result;
	}
	
	private String taggedArrow(FluxElement element)
	{
		FormInfo info = null;
		String tag = "";
		if( element.getParent().getTag() != null && FormReport.getInstance().get(element.getParent().getTag().toString().split(" ")[0]) != null ){
			info = FormReport.getInstance().get(element.getParent().getTag().toString().split(" ")[0]);
			tag = (element.getParent().getTag() != null) ? "|Tag " + element.getParent().getTag() + info.toString() + "|" : "";
		} else
			tag = (element.getParent().getTag() != null) ? "|Tag " + element.getParent().getTag() + "|" : "";
		
		return treatmentPattern(element.getParent())+"-->"+tag+treatmentPattern(element)+";\n";
	}
	
	private String pattern(FluxElement element)
	{
		String retorno = "";
		
		if(element.getParent() != null)
		{
			retorno = taggedArrow(element);
		} else {
			retorno = treatmentPattern(element)+";\n";
		}
		
		if(element.getType().equalsIgnoreCase(Form.class.getSimpleName()))
		{
			if(element.getParent() != null)
					retorno = element.getParent().getId()+element.getParent().getType()+"("+element.getParent().getDescription()+")-->"+element.getId()+element.getType()+"("+element.getDescription()+");\n";
		}

		return retorno;
	}
	
	private String patternColor(FluxElement element)
	{
		String retorno = "";
		if(element.getType().equalsIgnoreCase(Announce.class.getSimpleName()) || element.getType().equalsIgnoreCase(Menu.class.getSimpleName()))
			retorno += "style "+element.getId()+element.getType()+" fill:#C6FF8D;\n";
		else if(element.getType().equalsIgnoreCase(Flow.class.getSimpleName()))
			retorno += "style "+element.getId()+element.getType()+" fill:#EEF6FF;\n";
		else if(element.getType().equalsIgnoreCase(Choice.class.getSimpleName()) && !element.getAux().isEmpty())
			retorno +="style "+element.getId()+element.getType()+" stroke:#CC0000;\n";
		return retorno;
	}
	
	private String checkNextForm(FluxElement element)
	{
		String result = "";
		if(!element.getType().equalsIgnoreCase(Decision.class.getSimpleName()) && 
		   !element.getType().equalsIgnoreCase(DecisionGroup.class.getSimpleName()) &&
		   !element.getType().equalsIgnoreCase(Menu.class.getSimpleName()) &&
		   !element.getType().equalsIgnoreCase(Condition.class.getSimpleName()) 
		 )
			if( (element.getNext() == 0 || element.getNext() == -1 ) && !element.getAux().equalsIgnoreCase("noNext"))
				result = treatmentPattern(element)+"-->|Tag"+element.getTag()+"|"+element.getId()+"error(X);\n style "+element.getId()+"error fill:#FF0000;\n";
		return result;
	}
	
}
