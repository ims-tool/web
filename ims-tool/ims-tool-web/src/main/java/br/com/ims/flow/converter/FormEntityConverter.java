package br.com.ims.flow.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.model.diagram.Element;

import br.com.ims.flow.service.FlowEditorService;
import br.com.ims.flow.model.FormEntity;

@FacesConverter(value = "formEntityConverter")
public class FormEntityConverter implements Converter {

	@Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String code = String.valueOf(((FormEntity) object).getId());
		return code;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {

        
        if ((submittedValue != null) && (!submittedValue.equals(""))) {
        	try {
				return new FlowEditorService().getForm(Integer.valueOf(submittedValue));
			} catch (Exception e) {

			} 		
		}
        return null;
    }

}