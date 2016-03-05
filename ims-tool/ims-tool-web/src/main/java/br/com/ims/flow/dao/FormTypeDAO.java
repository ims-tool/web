package br.com.ims.flow.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.model.FormTypeEntity;
import br.com.ims.flow.common.*;

public class FormTypeDAO extends AbstractDAO<FormTypeEntity> {
	public List<FormTypeEntity> getAll() {
		List<FormTypeEntity> result = new ArrayList<FormTypeEntity>();
		
		FormTypeEntity formType = new FormTypeEntity();
		formType.setId("0");
		formType.setName("Answer");
		formType.setDescription("Answer");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "answer.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "answer_error.png");
		formType.setAllowInput(0);
		formType.setAllowOutput(1);
		formType.setMandatoryInput(0);
		formType.setMandatoryOutput(1);
		result.add(formType);
		
		formType = new FormTypeEntity();
		formType.setId("1");
		formType.setName("Announce");
		formType.setDescription("Announce");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "announce.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "announce_error.png");
		formType.setAllowInput(1);
		formType.setAllowOutput(1);
		formType.setMandatoryInput(0);
		formType.setMandatoryOutput(1);
		result.add(formType);
		
		formType = new FormTypeEntity();
		formType.setId("2");
		formType.setName("PromptCollect");
		formType.setDescription("PromptCollect");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "promptCollect.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "promptCollect_error.png");
		formType.setAllowInput(1);
		formType.setAllowOutput(1);
		formType.setMandatoryInput(0);
		formType.setMandatoryOutput(1);
		result.add(formType);
		
		formType = new FormTypeEntity();
		formType.setId("3");
		formType.setName("Menu");
		formType.setDescription("Menu");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "menu.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "menu_error.png");
		formType.setAllowInput(1);
		formType.setAllowOutput(0);
		formType.setMandatoryInput(0);
		formType.setMandatoryOutput(1);
		result.add(formType);
		
		formType = new FormTypeEntity();
		formType.setId("4");
		formType.setName("Flow");
		formType.setDescription("Flow");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "flow.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "flow_error.png");
		formType.setAllowInput(1);
		formType.setAllowOutput(0);
		formType.setMandatoryInput(0);
		formType.setMandatoryOutput(1);
		result.add(formType);
		
		formType = new FormTypeEntity();
		formType.setId("5");
		formType.setName("Decision");
		formType.setDescription("Decision");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "decision.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "decision_error.png");
		formType.setAllowInput(1);
		formType.setAllowOutput(0);
		formType.setMandatoryInput(0);
		formType.setMandatoryOutput(1);
		result.add(formType);
		
		formType = new FormTypeEntity();
		formType.setId("6");
		formType.setName("Operation");
		formType.setDescription("Operation");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "operation.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "operation_error.png");
		formType.setAllowInput(1);
		formType.setAllowOutput(1);
		formType.setMandatoryInput(0);
		formType.setMandatoryOutput(1);
		result.add(formType);
		
		formType = new FormTypeEntity();
		formType.setId("7");
		formType.setName("Transfer");
		formType.setDescription("Transfer");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "transfer.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "transfer_error.png");
		formType.setAllowInput(1);
		formType.setAllowOutput(0);
		formType.setMandatoryInput(1);
		formType.setMandatoryOutput(0);
		result.add(formType);
		
		formType = new FormTypeEntity();
		formType.setId("8");
		formType.setName("Disconnect");
		formType.setDescription("Disconnect");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "disconnect.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "disconnect_error.png");
		formType.setAllowInput(1);
		formType.setAllowOutput(0);
		formType.setMandatoryInput(1);
		formType.setMandatoryOutput(0);
		result.add(formType);
		
		
		formType = new FormTypeEntity();
		formType.setId("9");
		formType.setName("NoMatchInput");
		formType.setDescription("NoMatchInput");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "<NOMACHINPUT>.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "<NOMACHINPUT>_error.png");
		formType.setImageSizeHeight(30);
		formType.setImageSizeWidth(30);
		formType.setAllowInput(0);
		formType.setAllowOutput(1);
		formType.setMandatoryInput(1);
		formType.setMandatoryOutput(1);
		formType.setVisible(false);
		result.add(formType);
		
				
		formType = new FormTypeEntity();
		formType.setId("10");
		formType.setName("Choice");
		formType.setDescription("Choice");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "choice_<NUMBER>.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "choice_<NUMBER>_error.png");
		formType.setImageSizeHeight(30);
		formType.setImageSizeWidth(30);
		formType.setAllowInput(0);
		formType.setAllowOutput(1);
		formType.setMandatoryInput(1);
		formType.setMandatoryOutput(1);
		formType.setVisible(false);
		result.add(formType);
		
		formType = new FormTypeEntity();
		formType.setId("11");
		formType.setName("DecisionChance");
		formType.setDescription("DecisionChance");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "decision_chance.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "decision_chance_error.png");
		formType.setImageSizeHeight(30);
		formType.setImageSizeWidth(30);
		formType.setAllowInput(0);
		formType.setAllowOutput(1);
		formType.setMandatoryInput(1);
		formType.setMandatoryOutput(1);
		formType.setVisible(false);
		result.add(formType);
		

		formType = new FormTypeEntity();
		formType.setId("12");
		formType.setName("Return");
		formType.setDescription("Return");
		formType.setImagePathSuccess(Constants.ICON_FLOW_PATH + "return.png");
		formType.setImagePathError(Constants.ICON_FLOW_PATH + "return_error.png");
		formType.setAllowInput(1);
		formType.setAllowOutput(0);
		formType.setMandatoryInput(1);
		formType.setMandatoryOutput(0);
		result.add(formType);
		
		return result;
	}

	@Override
	public FormTypeEntity get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(FormTypeEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(FormTypeEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(FormTypeEntity entity) {
		// TODO Auto-generated method stub
		
	}

}
