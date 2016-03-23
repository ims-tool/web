package br.com.ims.flow.factory;

import br.com.ims.flow.dao.AnnounceDAO;
import br.com.ims.flow.dao.AnswerDAO;
import br.com.ims.flow.dao.AudioDAO;
import br.com.ims.flow.dao.ConditionDAO;
import br.com.ims.flow.dao.ConditionMapDAO;
import br.com.ims.flow.dao.DecisionDAO;
import br.com.ims.flow.dao.DisconnectDAO;
import br.com.ims.flow.dao.FlowDAO;
import br.com.ims.flow.dao.FormDAO;
import br.com.ims.flow.dao.FormTypeDAO;
import br.com.ims.flow.dao.GrammarDAO;
import br.com.ims.flow.dao.MenuDAO;
import br.com.ims.flow.dao.NoMatchInputDAO;
import br.com.ims.flow.dao.OperationDAO;
import br.com.ims.flow.dao.OperationMapDAO;
import br.com.ims.flow.dao.PromptCollectDAO;
import br.com.ims.flow.dao.PromptDAO;
import br.com.ims.flow.dao.ReturnDAO;
import br.com.ims.flow.dao.SequenceDAO;
import br.com.ims.flow.dao.TagDAO;
import br.com.ims.flow.dao.TagTypeDAO;
import br.com.ims.flow.dao.TransferDAO;
import br.com.ims.flow.dao.VersionDAO;

public class DAOFactory {

	private static DAOFactory instance = null;

	public static DAOFactory getInstance() {
		if (instance == null) {
			instance = new DAOFactory();
		}
		return instance;
	}

	private DAOFactory() {
		//
	}

	public FormDAO getFormDAO() {
		return FormDAO.getInstance();
	}
	public FormTypeDAO getFormTypeDAO() {
		return new FormTypeDAO();
	}
	
	public PromptDAO getPromptDAO() {
		return PromptDAO.getInstance();
	}
	

	public AudioDAO getAudioDAO() {
		return AudioDAO.getInstance();
	}
	
	public TagDAO getTagDAO() {
		return TagDAO.getInstance();
	}
	
	public TagTypeDAO getTagTypeDAO() {
		return TagTypeDAO.getInstance();
	}
	
	public NoMatchInputDAO getNoMatchInputDAO() {
		return NoMatchInputDAO.getInstance(); 
	}
	
	
	public GrammarDAO getGrammarDAO() {
		return GrammarDAO.getInstance(); 
	}
	
	public ConditionDAO getConditionDAO() {
		return ConditionDAO.getInstance(); 
	}
	
	public ConditionMapDAO getConditionMapDAO() {
		return ConditionMapDAO.getInstance(); 
	}
	public OperationMapDAO getOperationMapDAO() {
		return OperationMapDAO.getInstance(); 
	}
	
	public VersionDAO getVersionDAO() {
		return VersionDAO.getInstance(); 
	}
	
	public AnnounceDAO getAnnounceDAO() {
		return AnnounceDAO.getInstance();
	}
	public AnswerDAO getAnswerDAO() {
		return AnswerDAO.getInstance();
	}
	public ReturnDAO getReturnDAO() {
		return ReturnDAO.getInstance();
	}
	public DisconnectDAO getDisconnectDAO() {
		return DisconnectDAO.getInstance();
	}
	public PromptCollectDAO getPromptCollectDAO() {
		return PromptCollectDAO.getInstance();
	}
	public MenuDAO getMenuDAO() {
		return MenuDAO.getInstance();
	}
	public DecisionDAO getDecisionDAO() {
		return DecisionDAO.getInstance();
	}
	public FlowDAO getFlowDAO() {
		return FlowDAO.getInstance();
	}
	public TransferDAO getTransferDAO() {
		return TransferDAO.getInstance();
	}
	public OperationDAO getOperationDAO() {
		return OperationDAO.getInstance();
	}
	public SequenceDAO getSequenceDAO() {
		return SequenceDAO.getInstance();
	}

}
