package br.com.ims.flow.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.overlay.ArrowOverlay;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.common.MyBoolean;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.common.TabItemFlow;
import br.com.ims.flow.common.Util;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
import br.com.ims.flow.model.VersionEntity;
import br.com.ims.flow.service.FormTypeService;
 
@SuppressWarnings("serial")
@ManagedBean(name = "ivrEditorView")
@ViewScoped
public class IvrEditorBean extends AbstractBean {
     
	
	
	private FormTypeService formTypeService = new FormTypeService(); 
	private DefaultDiagramModel model;
	private List<FormTypeEntity> formTypes;
	
	private LogicalFlow logicalFlow;
	
	private FormEntity form;
	private Node node;
	private String formId;
	private String formPageEditor;
	private String complementPageEditor;
	private String auxiliarPageEditor;
	private String utilPageEditor;
	private String otherPageEditor;
    
	private MyBoolean editing;
	
    private TabView tabFlowView;
    private List<TabItemFlow> tabFlowList;
	private int activeTabFlowIndex = 0;
	
    private boolean suspendEvent;
    
    private TabCloseEvent tempTabCloseEvent;
 
    private VersionEntity version;
    
    private String flowId;
    private List<FormEntity> flows;
    
    private String deleteControl;
    private String cleanTabControl;
    public IvrEditorBean() {
    	init();
    }
    
    public void init() {
    	                
    	initTab0();
    	
        formTypes = formTypeService.getAll();
        
        if(version == null) {
        	
        	requestVersion(false);
        	/*RequestContext context = RequestContext.getCurrentInstance();
        	context.execute("PF('settingAdminDlg').show();");
            context.update("settingAdminDlgId");*/
            
        }
    }
    
    
    private void initTab0() {
    	this.model = new DefaultDiagramModel();
    	this.model.setMaxConnections(20);
        
    	this.model.getDefaultConnectionOverlays().add(new ArrowOverlay(10, 10, 1, 1));
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:1}");
        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");

        this.model.setDefaultConnector(connector);
        
        this.logicalFlow = new LogicalFlow();        
        this.formTypeService = new FormTypeService();         
        this.editing = new MyBoolean(false);
        
        
        this.tabFlowList = new ArrayList<TabItemFlow>();
        Tab tab = new Tab();
        tab.setTitle("New Flow");
        
        this.tabFlowList.add(new TabItemFlow(tab, 0,model,logicalFlow,editing));
		
        this.tabFlowView = new TabView();
        this.tabFlowView.setActiveIndex(0);
        this.tabFlowView.getChildren().add(tab);
        this.activeTabFlowIndex = 0;

    }
     
    
    
    public String getDeleteControl() {
		return deleteControl;
	}

	public void setDeleteControl(String deleteControl) {
		this.deleteControl = deleteControl;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public List<FormEntity> getFlows() {
		this.flows = ServicesFactory.getInstance().getFormService().getByFormTypeName(Constants.FORM_TYPE_ANSWER);
		return flows;
	}

	public void setFlows(List<FormEntity> flows) {
		this.flows = flows;
	}

	public VersionEntity getVersion() {
		return this.version;
	}

	public void setVersion(VersionEntity version) {
		this.version = version;
	}

	public void setLogicalFlow(LogicalFlow logicalFlow) {
		this.logicalFlow = logicalFlow;
	}

	public void addNewFlow() {
		cleanTabControl  = "NEW_FLOW";
		this.tempTabCloseEvent = null;
		if(this.isFlowEditing(null)) {
			RequestContext context = RequestContext.getCurrentInstance();
            // execute javascript and show dialog                    
			context.execute("PF('tabFlowCloseConfirmDlg').show();");			
			return;
		}  else {
			closeTabFlow();
		}
    }
	
	private boolean isFlowEditing(Tab tab) {
		for(TabItemFlow itemFlow : tabFlowList) {
    		if(tab != null) {
    			if(tab.getId().equals(itemFlow.getTab().getId()) &&
    				itemFlow.getEditing().booleanValue()) {
    				return true;
    			}
    		}else {    			
				if(itemFlow.getEditing().booleanValue()) {
	    			return true;
	    		}
    		}
    	}
		return false;
	}
	
    public void select() {
    	cleanTabControl  = "SELECT_FLOW";
    	this.tempTabCloseEvent = null;
    	this.flowId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formFlow:select_flow_input");
    	if(this.flowId == null || this.flowId.length() == 0) {
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "IVR Editor","Select one Flow!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
    	}
    	if(isFlowEditing(null)) {
    		RequestContext context = RequestContext.getCurrentInstance();
            // execute javascript and show dialog                    
			context.execute("PF('tabFlowCloseConfirmDlg').show();");			
			return;
    	} else {
    		closeTabFlow();
    	}
    }
	
	public void onTabChange(TabChangeEvent event) {
        for(TabItemFlow tab : tabFlowList) {
        	if(event.getTab().getId().equals(tab.getTab().getId())) {
        		this.model = tab.getModel();
	        	this.logicalFlow = tab.getLogicalFlow();
	        	this.editing = tab.getEditing();
        	}
        }
    }
         
    public void onTabClose(TabCloseEvent event) {
        
    	this.tempTabCloseEvent = event;
    	
    	
        for(int index = 0; index  < tabFlowList.size(); index++) {
        	TabItemFlow tab = tabFlowList.get(index);
        	if(event.getTab().getId().equals(tab.getTab().getId())) {
        		if(tab.getEditing().booleanValue()) {
        			this.tabFlowView.setActiveIndex(index);
        			RequestContext context = RequestContext.getCurrentInstance();
                    // execute javascript and show dialog                    
        			context.execute("PF('tabFlowCloseConfirmDlg').show();");                    
                    return;
        		} else {
        			closeTabFlow();
        			return;
        		}
        	}
        }
        
                        	
    }
    private void loadFlow() {
    	try {
    		//ServicesFactory.getInstance().getIvrEditorService().loadFlow(null,this.flowId);
    		ServicesFactory.getInstance().getIvrEditorService().loadFlow(this.flowId);
    		this.flowId = "";
    	} catch(Exception e ) {
    		this.logicalFlow.validateNodes();
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "IVR Editor",e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);			
    	} 
    	this.logicalFlow.validateNodes();
    	
    	
    }
    public void closeTabFlow() {
    	
        if(this.tempTabCloseEvent == null) {
        	this.tabFlowList.clear();
        	this.tabFlowView.setActiveIndex(0);
        	this.tabFlowView.getChildren().clear();
        	initTab0();
        	if(cleanTabControl.equals("SELECT_FLOW")) {
        		loadFlow();
        	}
        } else {
        	boolean find = false;
	    	
	    	for(int index = 0; index  < tabFlowList.size() && !find; index++) {
	        	TabItemFlow tab = tabFlowList.get(index);
	        	if(this.tempTabCloseEvent.getTab().getId().equals(tab.getTab().getId())) {
	        		tabFlowList.remove(index);
	        		tabFlowView.getChildren().remove(index);
	        		//ajustando os index;
	                for(int newIndex = 0; newIndex < tabFlowList.size(); newIndex++) {
	                	TabItemFlow t = tabFlowList.get(newIndex);
	                	t.setTabIndex(newIndex);
	                }
	        		
	        		this.changeTabFlow(index-1);
	        		find = true;
	        		
	        	}
	        }
        }
    }
    
    public LogicalFlow getLogicalFlow() {
		return logicalFlow;
	}


	public DefaultDiagramModel getModel() {
        return model;
    }
   

	public List<FormEntity> getListForm() {
		List<FormEntity> result = new ArrayList<FormEntity>();
		for(Node node : logicalFlow.getListNode()) {
			result.add(node.getForm());
		}
		
		return result;
	}
	

	
	public FormEntity getForm() {
		return form;
	}
		
	public void setForm(FormEntity form) {
		this.form = form;
	}
	
	
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public void onConnect(ConnectEvent event) {
		this.editing.setBooleanValue(true);
		if(!suspendEvent) {
        	
        	Node node = logicalFlow.getNode(event.getSourceElement());
        	if(node.getListTarget().size() > 0) {
        		//continuar
        		ServicesFactory.getInstance().getIvrEditorService().disconnectForm(this.model,this.logicalFlow, event.getSourceElement(),false);
        	}
        	
        	ServicesFactory.getInstance().getIvrEditorService().connectForm(model, logicalFlow, event.getSourceElement(),event.getTargetElement());
        	logicalFlow.validateNodes();
            //logicalFlow.align();            
            this.node = logicalFlow.getNode(event.getSourceElement());            
            ServicesFactory.getInstance().getTagEditorService().getBean().setNode(node);
            ServicesFactory.getInstance().getTagEditorService().getBean().setTagFromExternal(((FormEntity)node.getElement().getData()).getTag());

        	
        	
            
        }
        else {
            suspendEvent = false;
        }
    }
     
    public void onDisconnect(DisconnectEvent event) {
    	this.editing.setBooleanValue(true);
    	this.auxiliarPageEditor = "";
    	ServicesFactory.getInstance().getIvrEditorService().disconnectForm(this.model,this.logicalFlow, event.getSourceElement(),false);
    	logicalFlow.validateNodes();
        //logicalFlow.align();
    }
     
    public void onConnectionChange(ConnectionChangeEvent event) {
    	this.editing.setBooleanValue(true);
    	
    	ServicesFactory.getInstance().getIvrEditorService().disconnectForm(this.model,this.logicalFlow, event.getOriginalSourceElement(),false);

    	ServicesFactory.getInstance().getIvrEditorService().connectForm(model, logicalFlow, event.getNewSourceElement(),event.getNewTargetElement());
    	
    	logicalFlow.validateNodes();
        //logicalFlow.align();            
        this.node = logicalFlow.getNode(event.getNewSourceElement());            
        ServicesFactory.getInstance().getTagEditorService().getBean().setNode(node);
        ServicesFactory.getInstance().getTagEditorService().getBean().setTagFromExternal(((FormEntity)node.getElement().getData()).getTag());

    	
        suspendEvent = true;
    }
     
    
    

	public List<FormTypeEntity> getFormTypes() {
		return formTypes;
	}
	
	public void onDropFormType(DragDropEvent ddEvent) {
		
		this.editing.setBooleanValue(true);
		FormTypeEntity formType = ((FormTypeEntity) ddEvent.getData());
		
		FormEntity formEntityElement = new FormEntity();
		formEntityElement.setId(Util.getUID());
		if(formType.getName().equals(Constants.FORM_TYPE_ANSWER)) {
			if(logicalFlow.existsStart()) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Flow Editor","Node type 'Answer' is allowed only ONE per flow'");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				return;			
			} else {
				this.updateTabFlowName(formType.getName()+"_"+formEntityElement.getId());
			}
		}
			
		
		
		
		formEntityElement.setDescription(formType.getDescription());
		formEntityElement.setName(formType.getName()+"_"+formEntityElement.getId());
		formEntityElement.setFormType(formType);
		
		Element element = new Element(formEntityElement);
		

		ServicesFactory.getInstance().getIvrEditorService().setEndPoint(formType, element);
		
		
		model.addElement(element);
		logicalFlow.addNode(element);
		logicalFlow.validateNodes();
		logicalFlow.alingElementAlone();
		logicalFlow.resize();
    }
	
	public void elementSelected(ActionEvent param) {
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		this.formId = params.get("node_id");
		
		elementSelected(this.formId);
    }
	public void elementSelectedDbClick(ActionEvent param) {
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		this.formId = params.get("formFlow:nodeId");
		
		elementSelected(this.formId);
    }
	private void elementSelected(String formId) {
		
		this.form = ServicesFactory.getInstance().getIvrEditorService().getForm(this, formId); 
		
		
		if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_CHOICE) || 
		   form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_NOINPUT) ||
		   form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_NOMATCH) ||
		   form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_NOMATCHINPUT) ||
		   form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_DECISION_CHANCE)) {
			Node node = logicalFlow.getNode(this.form.getId());
			Node parent = node.getListSource().get(0);
			this.form = (FormEntity)parent.getElement().getData();
			this.formId = this.form.getId();
			
		}
		
		this.formPageEditor = "/pages/forms/"+form.getFormType().getName()+".xhtml";
		
		
		Object bean = ServicesFactory.getInstance().getIvrEditorService().getBean(form.getFormType().getName());
		((AbstractBean)bean).init();
		
		RequestContext context = RequestContext.getCurrentInstance();
    	context.execute("PF('settingFormDlg').show();");
        String [] forms = {"formFlow","settingFormDlgId"};
    	context.update(Arrays.asList(forms));
    }
	
	
	
	public String getFormPageEditor() {
		return formPageEditor;
	}

	public void setFormPageEditor(String formPageEditor) {
		this.formPageEditor = formPageEditor;
	}

	public String getComplementPageEditor() {
		return complementPageEditor;
	}

	public void setComplementPageEditor(String complementPageEditor) {
		this.complementPageEditor = complementPageEditor;
	}

	public String getAuxiliarPageEditor() {
		return auxiliarPageEditor;
	}

	public void setAuxiliarPageEditor(String auxiliarPageEditor) {
		this.auxiliarPageEditor = auxiliarPageEditor;
	}
	
	

	public String getOtherPageEditor() {
		return otherPageEditor;
	}

	public void setOtherPageEditor(String otherPageEditor) {
		this.otherPageEditor = otherPageEditor;
	}

	public String getUtilPageEditor() {
		return utilPageEditor;
	}

	public void setUtilPageEditor(String utilPageEditor) {
		this.utilPageEditor = utilPageEditor;
	}

	public void requestVersion(boolean save) {
		if(save) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "You have to assign the Version to save changes.",
	                "IVR Editor");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		ServicesFactory.getInstance().getVersionEditorService().getBean().setIvrEditorBean(this);
		
		RequestContext context = RequestContext.getCurrentInstance();
    	context.execute("PF('settingAdminDlg').show();");
        context.update("settingAdminDlgId");
	}
	
	public void save(ActionEvent event) {
		if(this.version == null) {
			requestVersion(true);
            return;
		}
		String nameFlow = "";
		boolean foundNodeAnswer = false;
		boolean foundErrors = false;
		for(Node node : this.logicalFlow.getListNode()) {
			FormEntity form = node.getForm();
			if(form.getFormType().getName().equals(Constants.FORM_TYPE_ANSWER)) {
				nameFlow = form.getName(); 
				foundNodeAnswer = true;
			}
			foundErrors = foundErrors | form.isFormError();
						
		}
		if(!foundNodeAnswer) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You cannot save. Node Answer is missing.",
	                "IVR Editor");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		this.editing.setBooleanValue(false);
		if(ServicesFactory.getInstance().getIvrEditorService().save(this.logicalFlow,nameFlow,this.version)) {
			
			FacesMessage msg = null;
			if(foundErrors) {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Flow ("+nameFlow+") was saved successfully, but there are Elements with error.",
		                "IVR Editor");
				
				
			} else {
				
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Flow ("+nameFlow+") was saved successfully!",
		                "IVR Editor");
			}
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error on Save '"+nameFlow+"', please contact your support.",
	                "IVR Editor");
			 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}
 
    }   


	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEditing() {
		return editing.booleanValue();
	}

	public void setEditing(boolean editing) {
		this.editing.setBooleanValue(editing);
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
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	
	public void deleteNode(ActionEvent param) {
		
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(params.get("formFlow:deleteControl") != null) {
			if(params.get("formFlow:deleteControl").equals("first")) {
				this.deleteControl = "false";
				this.formId = params.get("formFlow:nodeId");
				RequestContext context = RequestContext.getCurrentInstance();
	        	context.execute("PF('nodeDeleteConfirmDlg').show();");
				return;
				
			}
		}
		if(this.deleteControl.equals("false")) {
		
			this.form = ServicesFactory.getInstance().getIvrEditorService().getForm(this.formId);
			
			Node node = logicalFlow.getNode(this.form);
					
			if(this.form.getFormType().getName().equals(Constants.FORM_TYPE_MENU) ||
					this.form.getFormType().getName().equals(Constants.FORM_TYPE_DECISION) ||
					this.form.getFormType().getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT)) {
				for(int index = 0; index < node.getListTarget().size(); index++) {
					Node nodeTarget = node.getListTarget().get(index);
					if(nodeTarget.getForm().getFormType().getName().equals(Constants.FORM_TYPE_CHOICE) ||
							nodeTarget.getForm().getFormType().getName().equals(Constants.FORM_TYPE_DECISION_CHANCE) ||
							nodeTarget.getForm().getFormType().getName().equals(Constants.FORM_TYPE_NOMATCHINPUT) || 
							nodeTarget.getForm().getFormType().getName().equals(Constants.FORM_TYPE_NOINPUT) ||
							nodeTarget.getForm().getFormType().getName().equals(Constants.FORM_TYPE_NOMATCH)) {
						ServicesFactory.getInstance().getIvrEditorService().deleteForm(nodeTarget.getElement(),true);
						index = -1;
					}
				}
				
				ServicesFactory.getInstance().getIvrEditorService().deleteForm(node.getElement(),true);
			} else if(this.form.getFormType().getName().equals(Constants.FORM_TYPE_CHOICE) ||
					  this.form.getFormType().getName().equals(Constants.FORM_TYPE_DECISION_CHANCE) ||
					  this.form.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCHINPUT) || 
					  this.form.getFormType().getName().equals(Constants.FORM_TYPE_NOINPUT) ||
					  this.form.getFormType().getName().equals(Constants.FORM_TYPE_NOMATCH)) {
				Node aux = logicalFlow.getNode(this.form);
				if(aux.getListSource() != null && aux.getListSource().size() > 0) {
					Node source = aux.getListSource().get(0);
					this.formId = source.getForm().getId();
					deleteNode(param);
				}
			}
			else {
				ServicesFactory.getInstance().getIvrEditorService().deleteForm(node.getElement(),true);
			}
			
			logicalFlow.validateNodes();
		}
	}
	

	

	public List<TabItemFlow> getTabFlowList() {
		return tabFlowList;
	}

	public void setTabFlowList(List<TabItemFlow> tabFlowList) {
		this.tabFlowList = tabFlowList;
	}

	public int getActiveTabFlowIndex() {
		return activeTabFlowIndex;
	}

	public void setActiveTabFlowIndex(int activeTabFlowIndex) {
		this.activeTabFlowIndex = activeTabFlowIndex;
	}
	public void updateTabFlowName(String name) {
		
		TabItemFlow tab = this.tabFlowList.get(this.tabFlowView.getActiveIndex());
		if(tab != null) {
			tab.getTab().setTitle(name);
		}		
	}
	public void changeTabFlow(int index) {
		for(TabItemFlow tab : this.tabFlowList) {
			if(tab.getTabIndex() == index) {
				this.model = tab.getModel();
				this.editing = tab.getEditing();
				this.logicalFlow = tab.getLogicalFlow();
				this.tabFlowView.setActiveIndex(index);
			}
		}
	}
	
	public void addNewTabFlow() {
		DefaultDiagramModel model = new DefaultDiagramModel();
        model.setMaxConnections(20);
        
        model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:1}");
        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");

        model.setDefaultConnector(connector);
        
        LogicalFlow logicalFlow = new LogicalFlow();        
        MyBoolean editing = new MyBoolean(false);
        
        Tab tab = new Tab();
        tab.setTitle("New Flow");
        tab.setClosable(true);
        
        int index = this.tabFlowList.size();
        this.tabFlowList.add(new TabItemFlow(tab, index,model,logicalFlow,editing));
		
        this.tabFlowView.setActiveIndex(index);
        this.tabFlowView.getChildren().add(tab);
        
        this.changeTabFlow(index);

	}

	public TabView getTabFlowView() {
		return this.tabFlowView;
	}

	public void setTabFlowView(TabView tabFlowView) {
		this.tabFlowView = tabFlowView;
	}
	
	public void onNodeMove(ActionEvent param) {
	      Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	      String id = params.get("node_id");
	      String x = params.get("node_x");
	      String y = params.get("node_y");
	      int pos = id.indexOf("-"); // Remove Client ID part
	      if (pos != -1) {
	         id = id.substring(pos + 1);
	      }
	      int iX = 0;
	      int iY = 0;
	    	
	      if(x.indexOf("em") > -1) {
	    	  iX = Integer.valueOf(x.replace("em", "")) * 16; 
	      } else {
	    	  iX = Integer.valueOf(x.replace("px", ""));	     	      
	      }
	      if(y.indexOf("em") > -1) {
	    	  iY = Integer.valueOf(y.replace("em", "")) * 16; 
	      } else {
	    	  iY = Integer.valueOf(y.replace("px", ""));	     
	      }
	      Element element = model.findElement(id);
	      if (element != null) {
	    	  logicalFlow.getNode(element).setPositionX(iX);
	    	  logicalFlow.getNode(element).setPositionY(iY);	         
	      } else {
	    	  System.out.println("Didn't find element for ID " + id);	         
	      }
	      
	   }


	
    
}