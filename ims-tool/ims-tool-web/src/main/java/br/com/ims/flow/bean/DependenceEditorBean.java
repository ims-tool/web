package br.com.ims.flow.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.factory.ServicesFactory;
 
@SuppressWarnings("serial")
@ManagedBean(name = "dependenceEditorView")
@ViewScoped
public class DependenceEditorBean extends AbstractBean {
     
	private List<String []> objects;
	
	public String objectType;
	public String objectName;
	public String objectId;
	
	
    public DependenceEditorBean() {
    	//init();
    }
    
    public void init() {
    	super.init();
    	
    }
    
    public void setObject(String objectType,String objectId, String objectName ) {
    	this.objectType = objectType;
    	this.objectId = objectId;
    	this.objectName = objectName;
    }
    
    public List<String[]> getObjects() {
    	if(Constants.DEPENDENCE_OBJECT_TYPE_AUDIO.equals(this.objectType)) {
			this.objects = ServicesFactory.getInstance().getAudioService().getUsed(this.objectId);
		}
    	if(Constants.DEPENDENCE_OBJECT_TYPE_PROMPT.equals(this.objectType)) {
    		this.objects = ServicesFactory.getInstance().getPromptService().getUsed(this.objectId);
    	}
    	if(Constants.DEPENDENCE_OBJECT_TYPE_CONDITION_MAP.equals(this.objectType)) {
    		this.objects = ServicesFactory.getInstance().getConditionMapService().getUsed(this.objectId);
    	}
    	if(Constants.DEPENDENCE_OBJECT_TYPE_CONDITION.equals(this.objectType)) {
    		this.objects = ServicesFactory.getInstance().getConditionService().getUsed(this.objectId);
    	}
    	if(Constants.DEPENDENCE_OBJECT_TYPE_GRAMMAR.equals(this.objectType)) {
    		this.objects = ServicesFactory.getInstance().getGrammarService().getUsed(this.objectId);
    	}
    	if(Constants.DEPENDENCE_OBJECT_TYPE_NOMATCH_NOINPUT.equals(this.objectType)) {
    		this.objects = ServicesFactory.getInstance().getNoMatchInputService().getUsed(this.objectId);
    	}
    	if(Constants.DEPENDENCE_OBJECT_TYPE_OPERATION_MAP.equals(this.objectType)) {
    		this.objects = ServicesFactory.getInstance().getOperationMapService().getUsed(this.objectId);
    	}
    	return objects;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public void save(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void updateExternalsBean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
    
}