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
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;

import br.com.ims.businessDelegate.FlowEditorBusinessDelegate;
import br.com.ims.flow.common.LogicalFlow;
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
	private LogicalFlow flow;
	private String formId;
	private String formPageEditor;
	private String complementPageEditor;
	private String auxiliarPageEditor;
    
	private List<PromptEntity> prompts;
	
	private PromptEntity prompt;
	
    
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
        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:2}");
        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");

        model.setDefaultConnector(connector);
        
        formTypes = formTypeService.getAll();
        flow = new LogicalFlow();        
        formTypeService = new FormTypeService();         
        listForm = new ArrayList<FormEntity>();
        
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
        if(!suspendEvent) {
        	FlowEditorBusinessDelegate.connectForm(model, flow, event);
        	flow.validateNodes();
            flow.align();
            
        }
        else {
            suspendEvent = false;
        }
    }
     
    public void onDisconnect(DisconnectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Disconnected", 
                    "From " + event.getSourceElement().getData()+ " To " + event.getTargetElement().getData());
        
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
         
        RequestContext.getCurrentInstance().update("form:msgs");
    }
     
    public void onConnectionChange(ConnectionChangeEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connection Changed", 
                    "Original Source:" + event.getOriginalSourceElement().getData() + 
                    ", New Source: " + event.getNewSourceElement().getData() + 
                    ", Original Target: " + event.getOriginalTargetElement().getData() + 
                    ", New Target: " + event.getNewTargetElement().getData());
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
         
        RequestContext.getCurrentInstance().update("form:msgs");
        suspendEvent = true;
    }
     
    private EndPoint createDotEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setScope("formEntity");
        endPoint.setTarget(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
     
    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
        endPoint.setScope("formEntity");
        endPoint.setSource(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
     
    

	public List<FormTypeEntity> getFormTypes() {
		return formTypes;
	}
	
	public void onSelect(SelectEvent selectEvent ) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "onSelect", 
                "onSelect " );
    
     
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	     
	    RequestContext.getCurrentInstance().update("formFlow:msgs");
	}
	
	public void onDropFormType(DragDropEvent ddEvent) {
		FormTypeEntity formType = ((FormTypeEntity) ddEvent.getData());
		
		FormEntity formEntityElement = new FormEntity();
		
		formEntityElement.setDescription(formType.getDescription());
		formEntityElement.setName(formType.getName());
		formEntityElement.setFormType(formType);
		
		listForm.add(formEntityElement);
		
		Element element = new Element(formEntityElement);
		if(formType.getAllowInput() == 1) {
			EndPoint endPoint = createDotEndPoint(EndPointAnchor.TOP);
			endPoint.setTarget(true);
			element.addEndPoint(endPoint);
		}
		if(formType.getAllowOutput() == 1) {
			EndPoint endPoint = createDotEndPoint(EndPointAnchor.BOTTOM);
			endPoint.setTarget(false);
			endPoint.setSource(true);
			element.addEndPoint(endPoint);
		}
		
		model.addElement(element);
		flow.addNode(element);
		flow.validateNodes();
		flow.alingElementAlone();
    }
	
	private void OrderDisconnectElement(Element element) {
		int countElementAlone=0;
		List<Element> elements = model.getElements();
		for (Element element2 : elements) {
			boolean connected = false;
			if(element2.getEndPoints() != null) {
				for(EndPoint endPoint : element2.getEndPoints()) {
					List<Connection> connections = model.getConnections();
					for (Connection connection : connections) {
						if(connection.getSource() == endPoint ||
						   connection.getTarget() == endPoint) {
							connected = true;
						}
					}
				}
			}
			if(!connected) {
				countElementAlone++;
			}
		}
		
		element.setX(String.valueOf(((countElementAlone % 3) * 5))+"em"); 
		element.setY(String.valueOf(((countElementAlone / 3) * 7))+"em");
	}
	
	
	public void elementSelected() {
		
		this.formId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formFlow:elementId").toString();
		
		this.form = ServicesFactory.getInstance().getFlowEditorService().getForm(this, Integer.valueOf(this.formId)); 
	
		
		this.formPageEditor = "/pages/forms/"+form.getFormType().getName()+".xhtml";
		
		Object bean = ServicesFactory.getInstance().getFlowEditorService().getBean(form.getFormType().getName());
		((AbstractBean)bean).init();
		
		
    }
	
	
	
	public List<PromptEntity> getPrompts() {
		this.prompts = FlowEditorBusinessDelegate.getAllPrompt();
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
	public void save1() {
    	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "FormUpdated!",
                "FormUpdated!");
		 
		FacesContext.getCurrentInstance().addMessage(null, msg);
 
    }
    
}