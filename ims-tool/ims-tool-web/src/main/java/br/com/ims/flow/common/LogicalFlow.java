package br.com.ims.flow.common;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.Element;

import br.com.ims.flow.model.AbstractFormEntity;
import br.com.ims.flow.model.AnnounceEntity;
import br.com.ims.flow.model.ChoiceEntity;
import br.com.ims.flow.model.DecisionEntity;
import br.com.ims.flow.model.FlowEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.OperationEntity;
import br.com.ims.flow.model.PromptCollectEntity;
import br.com.ims.flow.model.TransferEntity;

public class LogicalFlow {
	private int minSizeWidth = 70;
	private int minSizeHeight = 70;
	private int sizeWidth = 70;
	private int sizeHeight = 70;
	private int distanceX = 20;
	private int distanceY = 20;
	//private int sizePercentage = 100;
	
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
			if(node.getElement().getId().equals(element.getId())) {
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
	public void delNode(FormEntity form) {
		Node node = getNode(form);
		Element element = node.getElement();
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
	
	public void disconnect(Element source, Element target) {
		Node nodeSource = getNode(source);
		Node nodeTarget = getNode(target);
		List<Node> targets = nodeSource.getListTarget();
		for(Node targetAux : targets) {
			if(targetAux.equals(nodeTarget)) {
				targetAux.remSource(nodeSource);
				if(targetAux.getListSource().size() == 0) {
					listFirstNode.add(targetAux);
				}
			}
		}
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
	public boolean existsStart() {
		for(Node nodeAux : listNode) {
			FormEntity formAux = (FormEntity)nodeAux.getElement().getData();
			if(formAux.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_ANSWER)) {
				return true;
			}
		}
		return false;
	}
	
	public void validateNodes() {
		for(Node node : listNode) {
			
			FormEntity form = (FormEntity)node.getElement().getData();
			
			form.setFormError(false);
			form.setErrorDescription("");
			
			int startFlow = 0;
			boolean stopFlow = false;
			for(Node nodeAux : listNode) {
				FormEntity formAux = (FormEntity)nodeAux.getElement().getData();
				if(!formAux.getId().equalsIgnoreCase(form.getId()) 
						&& formAux.getName().equalsIgnoreCase(form.getName())
						&& !(formAux.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_CHOICE) 
								|| formAux.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_NOINPUT) 
								|| formAux.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_NOMATCH))) {
					form.setFormError(true);
					form.setErrorDescription("Name "+form.getName()+" already assigned to another Element.");
					continue;
				}
				if(formAux.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_ANSWER)) {
					startFlow++;
				}
				if(formAux.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_DISCONNECT) ||
						formAux.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_TRANSFER) ||
						formAux.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_RETURN)) {
					stopFlow = true;
				}
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
			if(startFlow == 0) {
				form.setFormError(true);
				form.setErrorDescription("You have to start the flow with the node type 'Answer'");
				continue;
			} else if(startFlow > 1) {
				form.setFormError(true);
				form.setErrorDescription("Node type 'Answer' is allowed only ONE per flow");
				continue;
			}
			
			if(!stopFlow) {
				form.setFormError(true);
				form.setErrorDescription("You have to finish the flow with the node type 'Disconnect', 'Transfer' or 'Return'");
				continue;
			}
				
			
			
		}
		
	}
	
	private boolean validateFormByType(FormEntity form) {
		if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_ANNOUNCE)) {
			return validadeFormAnnounce(form);	
		}
		if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_PROMPT_COLLECT)) {
			return validadeFormPromptCollect(form);	
		}
		if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_MENU)) {
			return validadeFormMenu(form);
		}
		if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_DECISION)) {
			return validadeFormDecision(form);
		}
		if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_OPERATION)) {
			return validadeFormOperation(form);
		}
		if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_TRANSFER)) {
			return validadeFormTransfer(form);
		}
		if(form.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_FLOW)) {
			return validadeFormFlow(form);
		}
		return false;
	}
	private boolean validadeFormFlow(FormEntity form) {
		FlowEntity flow = (FlowEntity)form.getFormId();
		if(flow.getFlowName() == null || flow.getFlowName().length() == 0) {
			form.setFormError(true);
			form.setErrorDescription("Flow Name is missing");
		}
		return form.isFormError();
	}
	private boolean validadeFormTransfer(FormEntity form) {
		TransferEntity transfer = (TransferEntity)form.getFormId();
		if(transfer.getTransferRules() == null || transfer.getTransferRules().size() == 0) {
			form.setFormError(true);
			form.setErrorDescription("Transfer Rules is missing");
		}
		return form.isFormError();
	}
		
	private boolean validadeFormOperation(FormEntity form) {
		OperationEntity operation = (OperationEntity)form.getFormId();
		if(operation.getListOperationGroup() == null || operation.getListOperationGroup().size() == 0) {
			form.setFormError(true);
			form.setErrorDescription("Operation Group is missing");
		}
		return form.isFormError();
	}
	private boolean validadeFormDecision(FormEntity form) {
		DecisionEntity decision = (DecisionEntity)form.getFormId();
		if(decision.getListDecisionChance() == null || decision.getListDecisionChance().size() == 0) {
			form.setFormError(true);
			form.setErrorDescription("Chances is missing");
		}
		return form.isFormError();
	}
	private boolean validadeFormMenu(FormEntity form) {
		MenuEntity menu = (MenuEntity)form.getFormId();
		boolean error = false;
		String msgErro = "";
		if(menu.getPrompt() == null) {
			error = true;
			msgErro = "Prompt is missing";
			
		}
		if(menu.getNoInput() == null) {
			if(error) {
				msgErro += " / ";
			}
			error = true;
			msgErro += "NoInput is missing";
			
		}
		if(menu.getNoMatch() == null) {
			if(error) {
				msgErro += " / ";
			}
			error = true;
			msgErro += "NoMatch is missing";
			
		}
		if(menu.getChoices() == null || menu.getChoices().size() == 0) {
			if(error) {
				msgErro += " / ";
			}
			error = true;
			msgErro += "Choices is missing";
		}
		form.setFormError(error);
		form.setErrorDescription(msgErro);

		return form.isFormError();
	}
	private boolean validadeFormPromptCollect(FormEntity form) {
		PromptCollectEntity promptCollect = (PromptCollectEntity)form.getFormId();
		boolean error = false;
		String msgErro = "";
		if(promptCollect.getGrammar() == null) {
			error = true;
			msgErro = "Grammar is missing"; 
					}
		if(promptCollect.getPrompt() == null) {
			if(error) {
				msgErro += " / ";
			}
			error = true;
			msgErro += "Prompt is missing";
			
		}
		if(promptCollect.getNoInput() == null) {
			if(error) {
				msgErro += " / ";
			}
			error = true;
			msgErro += "NoInput is missing";
			
		}
		if(promptCollect.getNoMatch() == null) {
			if(error) {
				msgErro += " / ";
			}
			error = true;
			msgErro += "NoMatch is missing";
			
		}
		form.setFormError(error);
		form.setErrorDescription(msgErro);

		return form.isFormError();
	}
	private boolean validadeFormAnnounce(FormEntity form) {
		AnnounceEntity announce = (AnnounceEntity)form.getFormId();
		if(announce.getPrompt() == null) {
			form.setFormError(true);
			form.setErrorDescription("Prompt is missing");
		}
		return form.isFormError();
	}
	public void resize() {
		
		/**
		 * Aumentando dinamicamente o tamanho do painel de trabalho
		 */
		int maiorX = 0;
		int maiorY = 0;
		for(Node node : listFirstNode) {
			if(node.getPositionX() > maiorX) {
				maiorX = node.getPositionX();
			}
			if(node.getPositionY() > maiorY) {
				maiorY = node.getPositionY();
			}			
		}
		
		if((maiorY + distanceY) > sizeHeight ) {
			sizeHeight = maiorY + distanceY;
		} else if((maiorY + distanceY) < minSizeHeight) {
			sizeHeight = minSizeHeight;
		}
		
		if((maiorX + distanceX) > sizeWidth ) {
			sizeWidth = maiorX + distanceX;
		} else if((maiorX + distanceX) < minSizeWidth) {
			sizeWidth = minSizeWidth;
		}
	}
	/**
	public void align() {
				
		
		 *  ignorando o código pois só estou pegando a posição
		 *  do nó na tela, não vou ordenar automaticamente neste momento
		 * - Quando for ordenar automaticamente, o codigo deverá ser corrigido
		
		
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
		
		
		
		
	}*/
	/*private void alingLayerY(Node node, int indexLayerY) {
		if(node.getIndexLayerY() <= indexLayerY) {
			node.setIndexLayerY(indexLayerY);
			node.setPositionY(indexLayerY * distanceY);
			
			for(Node subNode : node.getListTarget()) {
				alingLayerY(subNode, indexLayerY+1);
			}
		}
		
		
		
	}*/
	/*private int countLayerY(Node node, int countY) {
		
		for(Node subNode : node.getListTarget()) {
				alingLayerY(subNode, countY+1);
		}
		
		return countY;
	}*/
	
	/*private int countLayerX(Node node,int countX ) {
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
		
	}*/
	/*private void alingLayerX(Node before, Node node, int posX) {

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
					node.setPosX(sumPosX/countSumPosX);*
			
					
					
					System.out.println("Layer Y:"+node.getIndexLayerX());
				} else {
					node.setPositionX(posBeforeX);
				}
			}
		}
	
		
	}*/
	
	public void alingElementAlone() {
		
	
		/**
		 *  ignorando o código pois só estou pegando a posição
		 *  do nó na tela, não vou ordenar automaticamente neste momento
		 * - Quando for ordenar automaticamente, o codigo deverá ser corrigido
		
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
		*/
		
	}
	public void alingMenuChoices(Element element) {
		Node menuNode = getNode(element);
    	
    	//empura no input/nomatch para o final da lista
		
		boolean first = true;
		for(int indexA = 0; indexA < menuNode.getListTarget().size()-2; indexA++) {
    		Node choiceNodeA = menuNode.getListTarget().get(indexA);
    		FormEntity formChoiceA = (FormEntity)choiceNodeA.getElement().getData();
    		
    		if(formChoiceA.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_NOMATCHINPUT)) {
    			int indexB = 0;
    			Node choiceNodeB = null;
    			if(first) {
    				first = false;
    				indexB = menuNode.getListTarget().size()-2;
    				choiceNodeB = menuNode.getListTarget().get(indexB);
    				    				
    			} else {
    				indexB = menuNode.getListTarget().size()-1;
    				choiceNodeB = menuNode.getListTarget().get(indexB);
    			}
    			menuNode.getListTarget().set(indexB, choiceNodeA);
				menuNode.getListTarget().set(indexA, choiceNodeB);
				indexA=-1;
    			
    		}
		}
		
		//ordena as choices
		for(int indexA = 0; indexA < menuNode.getListTarget().size(); indexA++) {
    		Node choiceNodeA = menuNode.getListTarget().get(indexA);
    		FormEntity formChoiceA = (FormEntity)choiceNodeA.getElement().getData();
    		
    		if(formChoiceA.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_CHOICE)) {
    			
    			for(int indexB = indexA; indexB < menuNode.getListTarget().size(); indexB++) {
        			Node choiceNodeB = menuNode.getListTarget().get(indexB);
        			FormEntity formChoiceB = (FormEntity)choiceNodeB.getElement().getData();
        			if(formChoiceB.getFormType().getName().equalsIgnoreCase(Constants.FORM_TYPE_CHOICE)) { 
        				
        				
        				if( ((ChoiceEntity)formChoiceA.getFormId()).compareTo((ChoiceEntity)formChoiceB.getFormId()) > 0) {
        					
        					menuNode.getListTarget().set(indexB, choiceNodeA);
        					menuNode.getListTarget().set(indexA, choiceNodeB);
        					
        					indexA = -1;
        					break;
        					
        				}
        			}
        			
        		}
    		}
    	}    	
	}
	

}
