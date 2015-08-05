package br.com.ims.flow.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;

import br.com.ims.flow.common.LogicalFlow;
import br.com.ims.flow.common.Node;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
import br.com.ims.flow.model.PromptEntity;
import br.com.ims.flow.service.FormTypeService;
 
@SuppressWarnings("serial")
@ManagedBean(name = "flowEditorView")
@ViewScoped
public class FlowEditorBean extends AbstractBean {
     
	
	private FormTypeService formTypeService = new FormTypeService(); 
	private DefaultDiagramModel model;
	private List<FormTypeEntity> formTypes;
	private List<FormEntity> listForm;
	private FormEntity form;
	private Node node;
	
	private LogicalFlow flow;
	private String formId;
	private String formPageEditor;
	private String complementPageEditor;
	private String auxiliarPageEditor;
    
	private List<PromptEntity> prompts;
	
	private PromptEntity prompt;
	
	private boolean editing;
	
    
    public PromptEntity getPrompt() {
		return prompt;
	}

	public void setPrompt(PromptEntity prompt) {
		this.prompt = prompt;
	}
	private boolean suspendEvent;
 
    public FlowEditorBean() {
    	init();
    }
    
    public void init() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        
        model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:1}");
        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");

        model.setDefaultConnector(connector);
                
        
        formTypes = formTypeService.getAll();
        
        flow = new LogicalFlow();        
        formTypeService = new FormTypeService();         
        listForm = new ArrayList<FormEntity>();
        this.editing = false;
        
    }
     
    
    
    public LogicalFlow getFlow() {
		return flow;
	}


	public DiagramModel getModel() {
        return model;
    }
    

	public List<FormEntity> getListForm() {
		return listForm;
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
		this.editing = true;
		if(!suspendEvent) {
        	
        	Node node = flow.getNode(event.getSourceElement());
        	if(node.getListTarget().size() > 0) {
        		//continuar
        		ServicesFactory.getInstance().getFlowEditorService().disconnectForm(this.model,this.flow, event.getSourceElement());
        	}
        	
        	ServicesFactory.getInstance().getFlowEditorService().connectForm(model, flow, event.getSourceElement(),event.getTargetElement());
        	flow.validateNodes();
            flow.align();            
            this.node = flow.getNode(event.getSourceElement());            
            ServicesFactory.getInstance().getTagEditorService().getBean().setNode(node);
            ServicesFactory.getInstance().getTagEditorService().getBean().setTagFromExternal(((FormEntity)node.getElement().getData()).getTag());
            this.auxiliarPageEditor = "/pages/auxiliar/TAG.xhtml";
        	
        	
            
        }
        else {
            suspendEvent = false;
        }
    }
     
    public void onDisconnect(DisconnectEvent event) {
    	this.editing = true;
    	this.auxiliarPageEditor = "";
    	ServicesFactory.getInstance().getFlowEditorService().disconnectForm(this.model,this.flow, event.getSourceElement());
    	flow.validateNodes();
        flow.align();
    }
     
    public void onConnectionChange(ConnectionChangeEvent event) {
    	this.editing = true;
    	
    	ServicesFactory.getInstance().getFlowEditorService().disconnectForm(this.model,this.flow, event.getOriginalSourceElement());

    	ServicesFactory.getInstance().getFlowEditorService().connectForm(model, flow, event.getNewSourceElement(),event.getNewTargetElement());
    	
    	flow.validateNodes();
        flow.align();            
        this.node = flow.getNode(event.getNewSourceElement());            
        ServicesFactory.getInstance().getTagEditorService().getBean().setNode(node);
        ServicesFactory.getInstance().getTagEditorService().getBean().setTagFromExternal(((FormEntity)node.getElement().getData()).getTag());
        this.auxiliarPageEditor = "/pages/auxiliar/TAG.xhtml";
    	
        suspendEvent = true;
    }
     
    private EndPoint createDotEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setScope("formEntity");
        endPoint.setTarget(true);
        endPoint.setSource(false);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
     
    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
        endPoint.setScope("formEntity");
        endPoint.setTarget(false);
        endPoint.setSource(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
     
    

	public List<FormTypeEntity> getFormTypes() {
		return formTypes;
	}
	
	public void onDropFormType(DragDropEvent ddEvent) {
		
		this.editing = true;
		FormTypeEntity formType = ((FormTypeEntity) ddEvent.getData());
		
		FormEntity formEntityElement = new FormEntity();
		
		formEntityElement.setDescription(formType.getDescription());
		formEntityElement.setName(formType.getName());
		formEntityElement.setFormType(formType);
		
		listForm.add(formEntityElement);
		
		Element element = new Element(formEntityElement);

		if(formType.getAllowInput() == 1) {
			EndPoint endPoint = createDotEndPoint(EndPointAnchor.TOP);
			element.addEndPoint(endPoint);
		}
		if(formType.getAllowOutput() == 1) {
			EndPoint endPoint = createRectangleEndPoint(EndPointAnchor.BOTTOM);
			element.addEndPoint(endPoint);
		}else {
			if(formType.getMandatoryOutput() == 1) {
				EndPoint endPoint = new BlankEndPoint(EndPointAnchor.BOTTOM);
				element.addEndPoint(endPoint);
				
			}
		}
		
		model.addElement(element);
		flow.addNode(element);
		flow.validateNodes();
		flow.alingElementAlone();
    }
	
	public void elementSelected() {
		
		this.formId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formFlow:elementId").toString();
		
		this.form = ServicesFactory.getInstance().getFlowEditorService().getForm(this, Integer.valueOf(this.formId)); 
	
		
		this.formPageEditor = "/pages/forms/"+form.getFormType().getName()+".xhtml";
		
		Object bean = ServicesFactory.getInstance().getFlowEditorService().getBean(form.getFormType().getName());
		((AbstractBean)bean).init();
		
		
    }
	
	
	
	public List<PromptEntity> getPrompts() {
		this.prompts = ServicesFactory.getInstance().getPromptService().getAll();
		return prompts;
	}

	public void setPrompts(List<PromptEntity> prompts) {
		this.prompts = prompts;
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

	public void save(ActionEvent event) {
    	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "FormUpdated!",
                "FormUpdated!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
 
    }   


	@Override
	public void update(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsed(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}
	
	
    
}