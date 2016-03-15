package br.com.ims.flow.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.service.IvrEditorService;

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
				return ServicesFactory.getInstance().getIvrEditorService().getForm(submittedValue);						
			} catch (Exception e) {

			} 		
		}
        return null;
    }

}