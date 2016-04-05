package br.com.ims.flow.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.Element;

import br.com.ims.flow.model.FormEntity;

@SuppressWarnings("serial")
public class Node implements Serializable{
	private FormEntity form;
	private Element element;
	private Connection connection;
	
	private int positionX;
	private int positionY;
	private int indexLayerY;
	private int indexLayerX;
	
	private List<Node> listTarget;
	private List<Node> listSource;
	
	public Node(Element element) {
		if(element.getX() == null) {
			element.setX("0px");
		}
		if(element.getY() == null) {
			element.setY("0px");
		}
		this.positionX = Integer.valueOf(element.getX().replaceAll("[a-z,A-Z]", ""));
		this.positionY = Integer.valueOf(element.getY().replaceAll("[a-z,A-Z]", ""));
		this.element = element;
		this.form = (FormEntity)element.getData();
		listTarget = new ArrayList<Node>();
		listSource = new ArrayList<Node>();
	
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
		this.element.setX(String.valueOf(positionX)+"px");
		this.form.setPositionX(this.element.getX());
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
		this.element.setY(String.valueOf(positionY)+"px");
		this.form.setPositionY(element.getY());
	}
		
	public String getStrPositionY() {
		return String.valueOf(this.positionY)+"px";
	}
	public String getStrPositionX() {
		return String.valueOf(this.positionX)+"px";
	}
	
	
	
	
	public int getIndexLayerY() {
		return indexLayerY;
	}

	public void setIndexLayerY(int indexLayerY) {
		this.indexLayerY = indexLayerY;
	}

	public int getIndexLayerX() {
		return indexLayerX;
	}

	public void setIndexLayerX(int indexLayerX) {
		this.indexLayerX = indexLayerX;
	}

	public void addTarget(Node target) {
		listTarget.add(target);
	}
	public void remTarget(Node target) {
		listTarget.remove(target);
	}
	public void cleanTarget() {
		listTarget.clear();
	}
	
	public void addSource(Node target) {
		listSource.add(target);
	}
	public void remSource(Node target) {
		listSource.remove(target);
	}
	public void cleanSource() {
		listSource.clear();
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}
	
	

	public FormEntity getForm() {
		return form;
	}

	public void setForm(FormEntity form) {
		this.form = form;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public List<Node> getListTarget() {
		return listTarget;
	}

	public void setListTarget(List<Node> listTarget) {
		this.listTarget = listTarget;
	}

	public List<Node> getListSource() {
		return listSource;
	}

	public void setListSource(List<Node> listSource) {
		this.listSource = listSource;
	}
	
	

		
	
 }
