package br.com.gvt.telefonia.ura.logic;

import java.util.List;

import br.com.gvt.telefonia.ura.diagram.model.Form;
import br.com.gvt.telefonia.ura.export.IExport;
import br.com.gvt.telefonia.ura.util.SingletonDAO;

public class FluxoLogicBack extends FluxoLogic {

	public FluxoLogicBack(IExport output) {
		super(output);
		// TODO Auto-generated constructor stub
	}
	
	public String export(String formId) throws Exception
	{
		Form form = SingletonDAO.getInstance().getFormDAOInstance().findByName(formId);
		List<Form> forms = SingletonDAO.getInstance().getFormDAOInstance().parentForms(form.getName());
		
		/*for(Form obj : forms){
			FormLogic formLogic = new FormLogic(formId,listElements);
			formLogic.loadFormType();
		}
		
		return output.output(listElements);
		*/
		return null;
	}

}
