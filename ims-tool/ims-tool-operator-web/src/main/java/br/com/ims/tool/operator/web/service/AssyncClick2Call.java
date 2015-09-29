package br.com.ims.tool.operator.web.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.telephony.CallEvent;
import javax.telephony.ConnectionEvent;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.MetaEvent;
import javax.telephony.Provider;
import javax.telephony.callcenter.CallCenterCall;
import javax.telephony.callcontrol.CallControlConnectionEvent;
import javax.telephony.callcontrol.CallControlConnectionListener;

import com.avaya.jtapi.tsapi.ITsapiCallEvent;
import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentCall;
import com.avaya.jtapi.tsapi.LucentConnection;
import com.avaya.jtapi.tsapi.LucentV5CallInfo;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallCenterTrunkEventImpl;

public class AssyncClick2Call extends Thread {
	
	private String destino;
	private String vdn;
	private String protocolo;
	
	private PredictiveCallOut vOut = new PredictiveCallOut();
	private LucentCall lCall;
	private static java.util.logging.Logger log = Logger.getLogger(AssyncClick2Call.class.getName());
	private int logID;
	private String trunkInfo;
	
	private String providerStringPredictive = "AVAYA#CM1#CSTA#SVUXPAES12;loginID=click2call;passwd=Gvt@2011;";
	
	public class PredictiveCallOut {
		int errorCode;
		String errorDescription;
		String ucid;
	}
	
	public AssyncClick2Call(String destino, String vdn, String protocolo) {
		
		this.destino = destino;
		this.vdn = vdn;
		this.protocolo = protocolo;
		
	}
	
	@Override
	public void run() {
		logID = Thread.currentThread().hashCode();
		
		log.info("[" + logID + "]Iniciando chamada Assincrona do CallState ...");
		PredictiveCallOut out = makePredictiveCall(destino, vdn, protocolo);
		log.info("[" + logID + "]Chamada assincrona finalizada. Inserindo informacoes no banco de dados.");
	}
	
	private PredictiveCallOut makePredictiveCall(String telefone, String origem, String protocolo) {
    	Provider provider = null;
    	CallCenterCall call = null;    
    	try {
    		log.fine("[" + logID + "]Instanciado o provider");
    		provider = JtapiPeerFactory.getJtapiPeer("com.avaya.jtapi.tsapi.TsapiPeer").getProvider(providerStringPredictive);
    		log.fine("[" + logID + "]Provider instanciado. Criando uma nova chamada");
    		call = (CallCenterCall)provider.createCall();
    		log.fine("[" + logID + "]Chamada criada");
    		lCall = (LucentCall) call;
    		
    		lCall.addCallListener(new CallControlConnectionListener() {
				
				@Override
				public void connectionUnknown(ConnectionEvent arg0) {}			
				@Override
				public void connectionInProgress(ConnectionEvent arg0) {
					if (arg0 instanceof CallCenterTrunkEventImpl) {
						trunkInfo = "Trunk: [" + ((CallCenterTrunkEventImpl)arg0).getTrunk() + "]]";
						log.info("[" + logID + "]Connection in progress " + trunkInfo);
					} else {
						trunkInfo = "";
					}
				}
				@Override
				public void connectionFailed(ConnectionEvent arg0) {}
				@Override
				public void connectionDisconnected(ConnectionEvent arg0) {}
				@Override
				public void connectionCreated(ConnectionEvent arg0) {
					if (arg0 instanceof CallCenterTrunkEventImpl) {
						trunkInfo = "Trunk: [" + ((CallCenterTrunkEventImpl)arg0).getTrunk() + "]]";
						log.info("[" + logID + "]Connection created" + trunkInfo);
					} else {
						trunkInfo = "";
					}
				}
				@Override
				public void connectionConnected(ConnectionEvent arg0) {
					if (arg0 instanceof CallCenterTrunkEventImpl) {
						trunkInfo = "Trunk: [" + ((CallCenterTrunkEventImpl)arg0).getTrunk() + "]]";
						log.info("[" + logID + "]Connection connected " + trunkInfo);
					} else {
						trunkInfo = "";
					}
				}
				@Override
				public void connectionAlerting(ConnectionEvent arg0) {}
				@Override
				public void singleCallMetaSnapshotStarted(MetaEvent arg0) {
					if (arg0 instanceof CallCenterTrunkEventImpl) {
						trunkInfo = "Trunk: [" + ((CallCenterTrunkEventImpl)arg0).getTrunk() + "]]";
						log.info("[" + logID + "]Meta snapshot started " + trunkInfo);
					} else {
						trunkInfo = "";
					}
				}
				@Override
				public void singleCallMetaSnapshotEnded(MetaEvent arg0) {}
				@Override
				public void singleCallMetaProgressStarted(MetaEvent arg0) {
	
					
					if (arg0 instanceof CallCenterTrunkEventImpl) {
						trunkInfo = "Trunk: [" + ((CallCenterTrunkEventImpl)arg0).getTrunk() + "]]";
						log.info("[" + logID + "]" + trunkInfo);
					} else {
						trunkInfo = "";
					}
					log.info("[" + logID + "]MetaEvent Cause: " + arg0.getCause() + "[" + arg0.toString() + "]");
					if(arg0.getCause() == 103) {
						log.info("[" + logID + "]" + CallState.UNASSIGNED_NUMBER + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "]" + trunkInfo);
						vOut.errorCode = 0;
						vOut.errorDescription = CallState.UNASSIGNED_NUMBER;
					}
					if(arg0.getCause() == 107) {
						log.info("[" + logID + "]" + CallState.RESOURCES_NOT_AVAILABLE + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "]" + trunkInfo);
						vOut.errorCode = 0;
						vOut.errorDescription = CallState.RESOURCES_NOT_AVAILABLE;
					}
				}
				@Override
				public void singleCallMetaProgressEnded(MetaEvent arg0) {}
				@Override
				public void multiCallMetaTransferStarted(MetaEvent arg0) {
					if (arg0 instanceof CallCenterTrunkEventImpl) {
						trunkInfo = "Trunk: [" + ((CallCenterTrunkEventImpl)arg0).getTrunk() + "]]";
						log.info("[" + logID + "]Meta transfer started " + trunkInfo);
					} else {
						trunkInfo = "";
					}
				}
				@Override
				public void multiCallMetaTransferEnded(MetaEvent arg0) {}
				@Override
				public void multiCallMetaMergeStarted(MetaEvent arg0) {
					if (arg0 instanceof CallCenterTrunkEventImpl) {
						trunkInfo = "Trunk: [" + ((CallCenterTrunkEventImpl)arg0).getTrunk() + "]]";
						log.info("[" + logID + "]Meta merge started " + trunkInfo);
					} else {
						trunkInfo = "";
					}
				}
				@Override
				public void multiCallMetaMergeEnded(MetaEvent arg0) {}
				@Override
				public void callInvalid(CallEvent arg0) {}
				@Override
				public void callEventTransmissionEnded(CallEvent arg0) {}
				@Override
				public void callActive(CallEvent arg0) {}
				@Override
				public void connectionUnknown(CallControlConnectionEvent arg0) {}
				@Override
				public void connectionQueued(CallControlConnectionEvent arg0) {}
				@Override
				public void connectionOffered(CallControlConnectionEvent arg0) {}
				@Override
				public void connectionNetworkReached(CallControlConnectionEvent arg0) {}
				@Override
				public void connectionNetworkAlerting(CallControlConnectionEvent arg0) {}
				@Override
				public void connectionInitiated(CallControlConnectionEvent arg0) {}
				@Override
				public void connectionFailed(CallControlConnectionEvent arg0) {}
				@Override
				public void connectionEstablished(CallControlConnectionEvent arg0) {}
				
				@Override
				public void connectionDisconnected(CallControlConnectionEvent arg0) {
					
					if (arg0 instanceof CallCenterTrunkEventImpl) {
						trunkInfo = "Trunk: [" + ((CallCenterTrunkEventImpl)arg0).getTrunk() + "]]";
					} else {
						trunkInfo = "";
					}
										
					if(arg0.getCallControlCause() == 205) {
						log.info("[" + logID + "]" + CallState.NOT_ANSWERED + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "] " + trunkInfo);
						vOut.errorCode = 0;
						vOut.errorDescription = CallState.NOT_ANSWERED;
						
					}
					
					if(arg0.getCallControlCause() == 203) {
						log.info("[" + logID + "]" + CallState.BUSY + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "] " + trunkInfo);
						vOut.errorCode = 0;
						vOut.errorDescription = CallState.BUSY;
						
					}
					
					if(arg0 instanceof ITsapiCallEvent) {
						//System.out.println("[CSTA Cause: " + ((ITsapiCallEvent)arg0).getCSTACause() + "]");
						log.info("[" + logID + "]CSTA Cause: " + ((ITsapiCallEvent)arg0).getCSTACause() + "");
						if (((ITsapiCallEvent)arg0).getCSTACause() == 5) {
							log.info("[" + logID + "]" + CallState.UNKNOWN_CAUSE + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "] " + trunkInfo);
							vOut.errorCode = 0;
							vOut.errorDescription = CallState.UNKNOWN_CAUSE;
							
						}
						if (((ITsapiCallEvent)arg0).getCSTACause() == 34) {
							log.info("[" + logID + "]" + CallState.MACHINE_ANSWERED + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "] " + trunkInfo);
							vOut.errorCode = 0;
							vOut.errorDescription = CallState.MACHINE_ANSWERED;
							
						}
						if (((ITsapiCallEvent)arg0).getCSTACause() == 81) {
							log.info("[" + logID + "]" + CallState.UNASSIGNED_NUMBER + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "] " + trunkInfo);
							vOut.errorCode = 0;
							vOut.errorDescription = CallState.UNASSIGNED_NUMBER;
							
						}
						if (((ITsapiCallEvent)arg0).getCSTACause() == 87) {
							log.info("[" + logID + "]" + CallState.UNKNOWN_CAUSE + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "] " + trunkInfo);
							vOut.errorCode = 0;
							vOut.errorDescription = CallState.UNKNOWN_CAUSE;
							
						}
						if (((ITsapiCallEvent)arg0).getCSTACause() == 30) {
							log.info("[" + logID + "]" + CallState.RESOURCES_NOT_AVAILABLE + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "] " + trunkInfo);
							vOut.errorCode = 0;
							vOut.errorDescription = CallState.RESOURCES_NOT_AVAILABLE;
							
						}
						if (((ITsapiCallEvent)arg0).getCSTACause() == -1) {
							if(vOut.errorDescription == null) {
								log.info("[" + logID + "]" + CallState.UNKNOWN_CAUSE + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "] " + trunkInfo);
								vOut.errorCode = 0;
								vOut.errorDescription = CallState.UNKNOWN_CAUSE;
							}
							
						}
					}

				}
				
				@Override
				public void connectionDialing(CallControlConnectionEvent arg0) {}
				
				@Override
				public void connectionAlerting(CallControlConnectionEvent arg0) {
					if (arg0 instanceof CallCenterTrunkEventImpl) {
						trunkInfo = "Trunk: [" + ((CallCenterTrunkEventImpl)arg0).getTrunk() + "]]";
					} else {
						trunkInfo = "";
					}
					log.info("[" + logID + "]Chamada alertando no cliente. " + trunkInfo);
				}
			});
    		
    		String inst = "";
    		
    		if(telefone.length() == 9) {
    			inst = "41" + telefone.substring(1, telefone.length());
    		} else {
    			inst = telefone.substring(2, telefone.length());
    		}
    		
    		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		
    		String uuiInfo = ""+inst+"@"+inst+"@" + protocolo + "@-1@"+df.format(new Date(Calendar.getInstance().getTimeInMillis()));
    		
    		UserToUserInfo uui = new UserToUserInfo(uuiInfo);
    		log.info("[" + logID + "]" + "Iniciado conexao preditiva. Parametros:[origem: " + origem + " telefone: " + telefone + " uui: " + uuiInfo + "]");
    		lCall.connectPredictive(null, (LucentAddress)provider.getAddress(origem), telefone+"#",LucentConnection.CONNECTED,5,CallCenterCall.ANSWERING_TREATMENT_PROVIDER_DEFAULT,CallCenterCall.ENDPOINT_ANY, false, uui);
    		log.info("[" + logID + "]" + "Conexao OK. UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "]");
    		
    		sleep(1000);
    		
    		vOut.errorCode = 0;
    		if(vOut.errorDescription == null) {
    			log.info("[" + logID + "]" + CallState.NORMAL_ANSWERED + " UCID=[" + ((LucentV5CallInfo)lCall).getUCID() + "]");
    			vOut.errorDescription = CallState.NORMAL_ANSWERED;
    		}

    	} catch (Exception e) {
    		log.severe("Ocoreu um erro durante o processo de chamada preditiva");
    		vOut.errorCode = -1;
    		vOut.errorDescription = e.getMessage();
			e.printStackTrace();
    	} finally {
    		vOut.ucid = ((LucentV5CallInfo)call).getUCID();
    		call = null;
    		provider.shutdown();
    		provider = null;
		}
    	
		return vOut;
	}
	
}
