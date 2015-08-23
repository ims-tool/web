package br.com.ims.flow.common;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.Element;

import br.com.ims.flow.model.AbstractFormEntity;
import br.com.ims.flow.model.AnnounceEntity;
import br.com.ims.flow.model.FormEntity;

public class LogicalFlow {
	private int minSizeWidth = 70;
	private int minSizeHeight = 70;
	private int sizeWidth = 70;
	private int sizeHeight = 70;
	private int distanceX = 10;
	private int distanceY = 10;
	private int sizePercentage = 100;
	
	private List<Node> listNode = new ArrayList<Node>();
	private List<Node> listFirstNode = new ArrayList<Node>();

	public List<Node> getListNode() {
		return listNode;
	}

	public void setListNode(List<Node> listNode) {
		this.listNode = listNode;
	}
	
	public Node getNode(Element element) {
		for(Node node : listNode) {
			if(node.getElement() == element) {
				return node;
			}
		}
		return null;
	}
	public Node getNode(FormEntity form) {
		for(Node node : listNode) {
			if((FormEntity)node.getElement().getData() == form) {
				return node;
			}
		}
		return null;
	}
	public void addNode(Element element) {
		Node node = new Node(element);
		listNode.add(node);
		listFirstNode.add(node);
	}
	public void delNode(Element element) {
		Node node = getNode(element);
		
		disconnectAll(element);
		
		listNode.remove(node);
		listFirstNode.remove(node);
	}
	
	public void connect(Element source,Element target, Connection connection) {
		Node nodeSource = getNode(source);
    	Node nodeTarget = getNode(target);
    	nodeSource.addTarget(nodeTarget);
    	nodeTarget.addSource(nodeSource);
	    nodeSource.setConnection(connection);
	    listFirstNode.remove(nodeTarget);
	    Object formId = ((FormEntity)nodeSource.getElement().getData()).getFormId();
	    ((AbstractFormEntity)formId).setNextForm((FormEntity)target.getData());
	    
	}
	
	public void disconnectAll(Element element) {
		
		Node node = getNode(element);

		List<Node> sources = node.getListSource();
		for(Node source : sources) {
			disconnect(source.getElement());
			
		}
		disconnect(element);
		
		
	}
	public void disconnect(Element source) {
		Node nodeSource = getNode(source);

		List<Node> targets = nodeSource.getListTarget();
		for(Node target : targets) {
			target.remSource(nodeSource);
			if(target.getListSource().size() == 0) {
				listFirstNode.add(target);
			}
		}
		nodeSource.cleanTarget();
		nodeSource.setConnection(null);
		((FormEntity)nodeSource.getElement().getData()).setTag(null);
		Object formId = ((FormEntity)nodeSource.getElement().getData()).getFormId();
	    ((AbstractFormEntity)formId).setNextForm(null);
		
	}
	
	
	public void validateNodes() {
		for(Node node : listNode) {
			
			FormEntity form = (FormEntity)node.getElement().getData();
			
			form.setFormError(false);
			form.setErrorDescription("");
			
			for(Node nodeAux : listNode) {
				FormEntity formAux = (FormEntity)nodeAux.getElement().getData();
				if(!formAux.getId().equalsIgnoreCase(form.getId()) && formAux.getName().equalsIgnoreCase(form.getName())) {
					form.setFormError(true);
					form.setErrorDescription("Name "+form.getName()+" already assigned to another Element.");
					continue;
				}
			}
			
			if(node.getListTarget().size() > 1) {
				form.setFormError(true);
				form.setErrorDescription("Name "+form.getName()+" already assigned to another Element.");
			}
			
			if(validateFormByType(form)) {
				continue;
			}
			
			if(form.getFormType().getMandatoryInput() == 1 && node.getListSource().size() == 0) {
				form.setFormError(true);
				form.setErrorDescription("Input is mandatory");
				continue;
			}
			if(form.getFormType().getMandatoryOutput() == 1 && node.getListTarget().size() == 0) {
				form.setFormError(true);
				form.setErrorDescription("Output is mandatory");
				continue;
			}
				
			
			
		}
		
	}
	
	private boolean validateFormByType(FormEntity form) {
		if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_ANNOUNCE)) {
			return validadeFormAnnounce(form);	
		}
		return false;
	}
	private boolean validadeFormAnnounce(FormEntity form) {
		AnnounceEntity announce = (AnnounceEntity)form.getFormId();
		if(announce.getPrompt() == null) {
			form.setFormError(true);
			form.setErrorDescription("Prompt is missing");
		}
		return form.isFormError();
	}
	public void align() {
		
		
		
		alingElementAlone();
		
		int totalLayerY = 0;
		float totalLayerX = 0;
		
		for(Node node : listFirstNode) {
			totalLayerX+= countLayerX(node,0);
			int aux = countLayerY(node, 0);
			if(aux > totalLayerY) {
				totalLayerY = aux;
			}
		}
		
		if(totalLayerY * distanceY > sizeHeight ) {
			sizeHeight = (int) (totalLayerY * distanceY + distanceY);
		} else if(totalLayerY * distanceY < minSizeHeight) {
			sizeHeight = minSizeHeight;
		}
		
		if(totalLayerX * distanceX > sizeWidth ) {
			sizeWidth = (int) (totalLayerX * distanceX + distanceX);
		} else if(totalLayerX * distanceX < minSizeWidth) {
			sizeWidth = minSizeWidth;
		}
		
		for(Node node : listFirstNode) {
			alingLayerY(node,0);
		}
		
		if(totalLayerX > 0) {
			
			if(listFirstNode.size() > 1) {
				float sumX = 0;
				float middleX = totalLayerX / 2;
				float sizeWidthMidel = sizeWidth/2;
				
				for(Node node : listFirstNode) {
					float countX = countLayerX(node,0);
					if(countX > 0) {
						
						if(totalLayerX == countX) {
							alingLayerX(null,node, 0);
						} else {
							int posistionX = 0;
							
							if(countX > 1) {
								float aux = countX / 2;
								posistionX = (int) (sizeWidthMidel - middleX * distanceX + (sumX+aux)*distanceX);
								countX+= 0.5;
							} else {
								posistionX = (int) (sizeWidthMidel - middleX * distanceX  +  sumX * distanceX);
							}
											
							node.setPositionX(posistionX);
							int indexX = 0;
							for(Node subNode : node.getListTarget()) {
								alingLayerX(node,subNode, indexX++);
							}
						}
						sumX+= countX;
					}
				}
			} else {
				alingLayerX(null,listFirstNode.get(0), 0);
			}
		}
		
		
		
		
	}
	private void alingLayerY(Node node, int indexLayerY) {
		if(node.getIndexLayerY() <= indexLayerY) {
			node.setIndexLayerY(indexLayerY);
			node.setPositionY(indexLayerY * distanceY);
			
			for(Node subNode : node.getListTarget()) {
				alingLayerY(subNode, indexLayerY+1);
			}
		}
		
		
		
	}
	private int countLayerY(Node node, int countY) {
		
		for(Node subNode : node.getListTarget()) {
				alingLayerY(subNode, countY+1);
		}
		
		return countY;
	}
	
	private int countLayerX(Node node,int countX ) {
		if(node.getListTarget().size() > 0) {
			int countLayerActual = node.getListTarget().size();
			int countNextLayer = 0;
			for(Node subNode : node.getListTarget()) {
				countNextLayer+= subNode.getListTarget().size();
			}
			if (countNextLayer > countLayerActual ) {
				countX = countNextLayer;
			} else {
				countX = countLayerActual;
			}
			for(Node subNode : node.getListTarget()) {  
				countLayerX(subNode,countX);   
			}   
		}                                           
		return countX;
		
	}
	private void alingLayerX(Node before, Node node, int posX) {
		if(before == null) {
			node.setIndexLayerX(posX);
			node.setPositionX(sizeWidth/2);
		}
		if(node.getListTarget().size() > 0) {
			int index = -1;
			for(Node subNode : node.getListTarget()) {
				index++;
				subNode.setIndexLayerX(index);
				alingLayerX(node,subNode,index);
				
			}
		} 
		if(before != null) {
			int countTarget = before.getListTarget().size();
			int posBeforeX = before.getPositionX();
			int posXNode;
			if(countTarget > 1) {
				int meio = countTarget / 2;
				if(posX < meio ) {
					int aux  = meio - posX;
					if(countTarget % 2 != 0) {
						posXNode = posBeforeX - (aux * distanceX);
					} else {
						posXNode = posBeforeX - (aux * distanceX) + (distanceX/2);
					}
				} else {
					if(countTarget % 2 != 0 ) {
						if(meio == posX) {
							posXNode = posBeforeX;
						} else {
							posXNode = posBeforeX + ((posX - meio) * distanceX);
						}						
					}else {
						posXNode = posBeforeX + ((posX - meio + 1) * distanceX) - (distanceX/2);
						
					}
					
				}
				node.setPositionX(posXNode);
			} else {
				//node.setPositionX(posBeforeX);
				if(node.getListSource().size() > 1) {
					
					/*	int sumPosX = 0;
					int countSumPosX = 0;
					int layerY = 0;
					for(Node topNodes : node.getListSource()) {
						if(layerY == 0) {
							sumPosX += topNodes.getPosX();
							countSumPosX++;
							layerY = getIndexLayerY(topNodes);
						} else {
							if(layerY == getIndexLayerY(topNodes)) {
								sumPosX += topNodes.getPosX();
								countSumPosX++;
							}
						}
					}
					node.setPosX(sumPosX/countSumPosX);*/
			
					
					
					System.out.println("Layer Y:"+node.getIndexLayerX());
				} else {
					node.setPositionX(posBeforeX);
				}
			}
		}
	
		
	}
	
	public void alingElementAlone() {
		
		int countElementAlone=0;
		for(Node node: listFirstNode) {
			if(node.getListSource().size() == 0) {				
				if((countElementAlone * distanceY) > sizeWidth) {
					int totalcoluna = ((countElementAlone * distanceY) / sizeWidth)+1;  
					node.setPositionX( (countElementAlone % totalcoluna ) * this.distanceX );
					node.setPositionY((countElementAlone / totalcoluna ) * this.distanceY );
				} else {
					node.setPositionX( 0 );
					node.setPositionY(countElementAlone * this.distanceY );
				}
				countElementAlone++;
			} 
			
		}
		
		
	}
	

}
