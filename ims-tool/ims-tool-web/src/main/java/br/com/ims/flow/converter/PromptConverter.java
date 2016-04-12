package br.com.ims.flow.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.PromptEntity;


@FacesConverter(value = "promptConverter")
public class PromptConverter implements Converter {

	@Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
		String code = "";
		if(object != null && object != "") {
			code = String.valueOf(((PromptEntity) object).getId());
		}
		return code;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {

        
        if ((submittedValue != null) && (!submittedValue.equals(""))) {
        	try {
				return ServicesFactory.getInstance().getPromptService().get(submittedValue);        		
			} catch (Exception e) {

			} 		
		}
        return new PromptEntity();
    }

}