package br.com.gvt.telefonia.ura.util;

import br.com.gvt.telefonia.ura.diagram.dao.AnnounceDAO;
import br.com.gvt.telefonia.ura.diagram.dao.AudioDAO;
import br.com.gvt.telefonia.ura.diagram.dao.AudiovarDAO;
import br.com.gvt.telefonia.ura.diagram.dao.ChoiceDAO;
import br.com.gvt.telefonia.ura.diagram.dao.ConditionDAO;
import br.com.gvt.telefonia.ura.diagram.dao.ConditionGroupDAO;
import br.com.gvt.telefonia.ura.diagram.dao.ConditionMapDAO;
import br.com.gvt.telefonia.ura.diagram.dao.ConditionParametersDAO;
import br.com.gvt.telefonia.ura.diagram.dao.ConditionValueDAO;
import br.com.gvt.telefonia.ura.diagram.dao.DecisionChanceDAO;
import br.com.gvt.telefonia.ura.diagram.dao.DecisionDAO;
import br.com.gvt.telefonia.ura.diagram.dao.DecisionGroupDAO;
import br.com.gvt.telefonia.ura.diagram.dao.DecisionMapDAO;
import br.com.gvt.telefonia.ura.diagram.dao.DecisionParametersDAO;
import br.com.gvt.telefonia.ura.diagram.dao.DisconnectDAO;
import br.com.gvt.telefonia.ura.diagram.dao.FlowDAO;
import br.com.gvt.telefonia.ura.diagram.dao.FormDAO;
import br.com.gvt.telefonia.ura.diagram.dao.GrammarDAO;
import br.com.gvt.telefonia.ura.diagram.dao.LogPontosDAO;
import br.com.gvt.telefonia.ura.diagram.dao.MenuDAO;
import br.com.gvt.telefonia.ura.diagram.dao.NoMatchInputDAO;
import br.com.gvt.telefonia.ura.diagram.dao.OperationDAO;
import br.com.gvt.telefonia.ura.diagram.dao.OperationGroupDAO;
import br.com.gvt.telefonia.ura.diagram.dao.OperationMapDAO;
import br.com.gvt.telefonia.ura.diagram.dao.OperationParametersDAO;
import br.com.gvt.telefonia.ura.diagram.dao.PromptAudioDAO;
import br.com.gvt.telefonia.ura.diagram.dao.PromptCollectDAO;
import br.com.gvt.telefonia.ura.diagram.dao.PromptDAO;
import br.com.gvt.telefonia.ura.diagram.dao.TagDAO;
import br.com.gvt.telefonia.ura.diagram.dao.TransferDAO;
import br.com.gvt.telefonia.ura.diagram.dao.VersionDAO;

public class SingletonDAO {
	
	private static SingletonDAO instance;
	private static FormDAO formDAO;
	private static FlowDAO flowDAO;
	private static MenuDAO menuDAO;
	private static AnnounceDAO announceDAO;
	private static PromptDAO promptDAO;
	private static PromptAudioDAO promptAudioDAO;
	private static AudioDAO audioDAO;
	private static AudiovarDAO audiovarDAO;
	private static DecisionDAO decisionDAO;
	private static DecisionGroupDAO decisionGroupDAO;
	private static DecisionChanceDAO decisionChanceDAO;
	private static PromptCollectDAO promptCollectDAO; 
	private static ChoiceDAO choiceDAO;
	private static NoMatchInputDAO noMatchInputDAO;
	private static DisconnectDAO disconnectDAO;
	private static TransferDAO transferDAO;
	private static OperationDAO operationDAO;
	private static OperationGroupDAO operationGroupDAO;
	private static OperationMapDAO operationMapDAO;
	private static OperationParametersDAO operationParameterDAO;
	private static DecisionMapDAO decisionMapDAO;
	private static DecisionParametersDAO decisionParametersDAO;
	private static ConditionDAO conditionDAO;
	private static ConditionGroupDAO conditionGroupDAO;
	private static ConditionMapDAO conditionMapDAO;
	private static ConditionParametersDAO conditionParametersDAO;
	private static ConditionValueDAO conditionValueDAO;
	private static TagDAO tagDAO;
	private static LogPontosDAO logPontosDAO;
	private static GrammarDAO grammarDAO;
	private static VersionDAO versionDAO;
	
	private SingletonDAO() {}
	
	public static SingletonDAO getInstance(){
		if(instance == null){
			instance = new SingletonDAO();
		}
		return instance;
	}
	
	public static FormDAO getFormDAOInstance()
	{
		if(formDAO == null){
			formDAO = new FormDAO();
		}
		return formDAO;
	}
	
	public static FlowDAO getFlowDAOInstance()
	{
		if(flowDAO == null){
			flowDAO = new FlowDAO();
		}
		return flowDAO;
	}
	
	public static MenuDAO getMenuDAOInstance()
	{
		if(menuDAO == null){
			menuDAO = new MenuDAO();
		}
		return menuDAO;
	}
	
	public static AnnounceDAO getAnnounceDAOInstance()
	{
		if(announceDAO == null){
			announceDAO = new AnnounceDAO();
		}
		return announceDAO;
	}
	
	public static PromptDAO getPromptDAOInstance()
	{
		if(promptDAO == null){
			promptDAO = new PromptDAO();
		}
		return promptDAO;
	}
	
	public static PromptAudioDAO getPromptAudioDAOInstance()
	{
		if(promptAudioDAO == null){
			promptAudioDAO = new PromptAudioDAO();
		}
		return promptAudioDAO;
	}
	
	public static AudioDAO getAudioDAOInstance()
	{
		if(audioDAO == null){
			audioDAO = new AudioDAO();
		}
		return audioDAO;
	}
	
	public static AudiovarDAO getAudiovarDAOInstance()
	{
		if(audiovarDAO == null){
			audiovarDAO = new AudiovarDAO();
		}
		return audiovarDAO;
	}
	
	public static DecisionDAO getDecisionDAOInstance()
	{
		if(decisionDAO == null){
			decisionDAO = new DecisionDAO();
		}
		return decisionDAO;
	}
	
	public static DecisionGroupDAO getDecisionGroupDAOInstance()
	{
		if(decisionGroupDAO == null){
			decisionGroupDAO = new DecisionGroupDAO();
		}
		return decisionGroupDAO;
	}
	
	public static DecisionChanceDAO getDecisionChanceDAOInstance()
	{
		if(decisionChanceDAO == null){
			decisionChanceDAO = new DecisionChanceDAO();
		}
		return decisionChanceDAO;
	}
	
	public static PromptCollectDAO getPromptCollectDAOInstance()
	{
		if(promptCollectDAO == null){
			promptCollectDAO = new PromptCollectDAO();
		}
		return promptCollectDAO;
	}
	
	public static ChoiceDAO getChoiceDAOInstance()
	{
		if(choiceDAO == null){
			choiceDAO = new ChoiceDAO();
		}
		return choiceDAO;
	}
	
	public static NoMatchInputDAO getNoMatchInputDAOInstance()
	{
		if(noMatchInputDAO == null){
			noMatchInputDAO = new NoMatchInputDAO();
		}
		return noMatchInputDAO;
	}
	
	public static DisconnectDAO getDisconnectDAOInstance()
	{
		if(disconnectDAO == null){
			disconnectDAO = new DisconnectDAO();
		}
		return disconnectDAO;
	}
	
	public static TransferDAO getTransferDAOInstance()
	{
		if(transferDAO == null){
			transferDAO = new TransferDAO();
		}
		return transferDAO;
	}
	
	public static OperationDAO getOperationDAOInstance()
	{
		if(operationDAO == null){
			operationDAO = new OperationDAO();
		}
		return operationDAO;
	}
	
	public static OperationGroupDAO getOperationGroupDAOInstance()
	{
		if(operationGroupDAO == null){
			operationGroupDAO = new OperationGroupDAO();
		}
		return operationGroupDAO;
	}
	
	public static OperationParametersDAO getOperationParameterDAOInstance()
	{
		if(operationParameterDAO == null){
			operationParameterDAO = new OperationParametersDAO();
		}
		return operationParameterDAO;
	}
	
	public static OperationMapDAO getOperationMapDAOInstance()
	{
		if(operationMapDAO == null){
			operationMapDAO = new OperationMapDAO();
		}
		return operationMapDAO;
	}
	
	public static DecisionMapDAO getDecisionMapDAOInstance()
	{
		if(decisionMapDAO == null){
			decisionMapDAO = new DecisionMapDAO();
		}
		return decisionMapDAO;
	}
	
	public static DecisionParametersDAO getDecisionParametersDAOInstance()
	{
		if(decisionParametersDAO == null){
			decisionParametersDAO = new DecisionParametersDAO();
		}
		return decisionParametersDAO;
	}
	
	public static ConditionDAO getConditionDAOInstance()
	{
		if(conditionDAO == null){
			conditionDAO = new ConditionDAO();
		}
		return conditionDAO;
	}
	
	public static ConditionGroupDAO getConditionGroupDAOInstance()
	{
		if(conditionGroupDAO == null){
			conditionGroupDAO = new ConditionGroupDAO();
		}
		return conditionGroupDAO;
	}
	
	public static ConditionMapDAO getConditionMapDAOInstance()
	{
		if(conditionMapDAO == null){
			conditionMapDAO = new ConditionMapDAO();
		}
		return conditionMapDAO;
	}
	
	
	public static ConditionParametersDAO getConditionParametersDAOInstance()
	{
		if(conditionParametersDAO == null){
			conditionParametersDAO = new ConditionParametersDAO();
		}
		return conditionParametersDAO;
	}
	
	
	public static ConditionValueDAO getConditionValueDAOInstance()
	{
		if(conditionValueDAO == null){
			conditionValueDAO = new ConditionValueDAO();
		}
		return conditionValueDAO;
	}
	
	public static TagDAO getTagDAOInstance()
	{
		if(tagDAO == null){
			tagDAO = new TagDAO();
		}
		return tagDAO;
	}
	
	public static LogPontosDAO getLogPontosDAOInstance()
	{
		if(logPontosDAO == null){
			logPontosDAO = new LogPontosDAO();
		}
		return logPontosDAO;
	}
	
	public static GrammarDAO getGrammarDAOInstance()
	{
		if(grammarDAO == null){
			grammarDAO = new GrammarDAO();
		}
		return grammarDAO;
	}
	
	public static VersionDAO getVersionDAOInstance()
	{
		if(versionDAO == null){
			versionDAO = new VersionDAO();
		}
		return versionDAO;
	}
}
