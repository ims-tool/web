package br.com.gvt.telefonia.ura.bussiness;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.export.IExport;
import br.com.gvt.telefonia.ura.export.MermaidExport;
import br.com.gvt.telefonia.ura.export.YedExport;
import br.com.gvt.telefonia.ura.logic.FluxoLogic;
import br.com.gvt.telefonia.ura.report.FormReport;
import br.com.gvt.telefonia.ura.util.FactoryMethodExport;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class FluxoBussiness {
	
	public String loadFluxo(String formId) throws Exception
	{
		IExport export = FactoryMethodExport.createExport(MermaidExport.class.getSimpleName());
		FluxoLogic logic = new FluxoLogic(export);
		return logic.export(formId);
	}
	
	public String loadFluxoBack(String formId) throws Exception
	{
		IExport export = FactoryMethodExport.createExport(MermaidExport.class.getSimpleName());
		FluxoLogic logic = new FluxoLogic(export);
		return logic.export(formId);
	}
	
	public String loadFluxoYed(String formId) throws Exception
	{
		IExport export = FactoryMethodExport.createExport(YedExport.class.getSimpleName());
		FluxoLogic logic = new FluxoLogic(export);
		return logic.export(Long.parseLong(formId));
	}
	
	public String loadFluxo(long formId) throws Exception
	{
		IExport export = FactoryMethodExport.createExport(MermaidExport.class.getSimpleName());
		FluxoLogic logic = new FluxoLogic(export);
		Form form = SingletonDAO.getFormDAOInstance().findByPk(Long.toString(formId));
		FormReport.getInstance().loadFormReport(form);
		return logic.export(formId);
	}
	
	public String loadCondition(String id) throws Exception
	{
		IExport export = FactoryMethodExport.createExport(MermaidExport.class.getSimpleName());
		FluxoLogic logic = new FluxoLogic(export);
		return logic.exportCondition(id);
	}
	
	public List<Form> loadParent(String formId) throws Exception
	{
		return SingletonDAO.getFormDAOInstance().parentForms(formId);
	}
}
