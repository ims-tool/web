package br.com.ims.flow.common;

import java.io.Serializable;

import org.primefaces.component.tabview.Tab;
import org.primefaces.model.diagram.DefaultDiagramModel;

@SuppressWarnings("serial")
public class TabItemFlow implements Serializable{
	private Tab tab;
	private int tabIndex;
	private DefaultDiagramModel model;
	private LogicalFlow logicalFlow;
	private MyBoolean editing;
	
	
	public TabItemFlow(Tab tab,
						int tabIndex,
						DefaultDiagramModel model,
						LogicalFlow logicalFlow,
						MyBoolean editing)
	{
		this.setTab(tab);
		this.setTabIndex(tabIndex);
		this.setModel(model);
		this.setLogicalFlow(logicalFlow);
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

