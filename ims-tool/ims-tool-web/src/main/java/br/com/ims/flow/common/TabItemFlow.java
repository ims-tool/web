package br.com.ims.flow.common;

import java.io.Serializable;
import java.util.List;

import org.primefaces.component.tabview.Tab;
import org.primefaces.model.diagram.DefaultDiagramModel;

import br.com.ims.flow.model.FormEntity;

@SuppressWarnings("serial")
public class TabItemFlow implements Serializable{
	private Tab tab;
	private int tabIndex;
	private DefaultDiagramModel model;
	private LogicalFlow logicalFlow;
	private List<FormEntity> listForm;
	private MyBoolean editing;
	
	
	public TabItemFlow(Tab tab,
						int tabIndex,
						DefaultDiagramModel model,
						LogicalFlow logicalFlow,
						List<FormEntity> listForm, 
						MyBoolean editing)
	{
		this.setTab(tab);
		this.setTabIndex(tabIndex);
		this.setModel(model);
		this.setLogicalFlow(logicalFlow);
		this.setListForm(listForm);
		this.setEditing(editing);		
		
	}
	

	public Tab getTab() {
		return tab;
	}


	public void setTab(Tab tab) {
		this.tab = tab;
		if(this.tab.getId() == null) {
			this.tab.setId("tab_"+Util.getUID());
		}
	}


	public DefaultDiagramModel getModel() {
		return model;
	}
	public void setModel(DefaultDiagramModel model) {
		this.model = model;
	}
	public LogicalFlow getLogicalFlow() {
		return logicalFlow;
	}
	public void setLogicalFlow(LogicalFlow logicalFlow) {
		this.logicalFlow = logicalFlow;
	}
	
	
	public List<FormEntity> getListForm() {
		return listForm;
	}
	public void setListForm(List<FormEntity> listForm) {
		this.listForm = listForm;
	}
	public MyBoolean getEditing() {
		return editing;
	}
	
	public void setEditing(MyBoolean editing) {
		this.editing = editing;
	}


	public int getTabIndex() {
		return tabIndex;
	}


	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	
}

