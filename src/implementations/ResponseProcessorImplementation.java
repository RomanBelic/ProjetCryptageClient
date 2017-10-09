package implementations;

import interfaces.Ciphering.ICipher;
import interfaces.Ciphering.IHashable;
import interfaces.Communication;

import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IEventAdapter;
import interfaces.Communication.IResponseProcessor;
import models.Message;
import utils.Context;
import utils.Utils;

public class ResponseProcessorImplementation implements IResponseProcessor<Message> {

	private final ICommunicationProtocol<Message> commProtocol;
	private final IHashable hasher;
	private ICipher cipher;
	
	public ResponseProcessorImplementation(ICommunicationProtocol<Message> commProtocol){
		this.commProtocol = commProtocol;
		this.hasher = Utils.getHasherInstance();
	}
	
	@Override
	public void processResponse (Message msg, IEventAdapter<Message> eventAdapter) {
		// TODO Auto-generated method stub
		long packets = msg.getPackets();
		if ((packets | Communication.F_AcceptChallenge) == Communication.F_AcceptChallenge){ 
			eventAdapter.onChallengeAccepted(msg);
		}
		else if ((packets | Communication.F_PassedChallenge) == Communication.F_PassedChallenge){ 
			if (msg.getCode() == Communication.OK){
				String[] splitStr = msg.getMessage().split(";");
				int clientId = splitStr.length > 0 ? new Integer(splitStr[0]).intValue() : 0;
				String clientName = splitStr.length > 1 ? splitStr[1] : null;
				Context.getCurrentClient().setId(clientId);
				Context.getCurrentClient().setName(clientName);
				eventAdapter.onConnected(msg);
			}else if (msg.getCode() == Communication.No_Content){
				msg.setMessage("Invalid login/password");
				eventAdapter.onLoginErrorReceived(msg);
			}else if (msg.getCode() == Communication.Unauthorized){
				msg.setMessage("Unauthorized action");
				eventAdapter.onLoginErrorReceived(msg);
			}
		}
		else if ((packets | Communication.F_AskInscription) == Communication.F_AskInscription){
			if (msg.getCode() == Communication.Created){
				eventAdapter.onSubscribed(msg);
			}else if (msg.getCode() == Communication.Conflict){
				msg.setMessage("User already exists");
				eventAdapter.onSubscribeErrorReceived(msg);
			}else if (msg.getCode() == Communication.InternalError){
				msg.setMessage("Internal error");
				eventAdapter.onSubscribeErrorReceived(msg);
			}
		}
		else if ((packets | Communication.F_PassedChallenge | Communication.F_SentMsg) == (Communication.F_PassedChallenge | Communication.F_SentMsg)){
			eventAdapter.onMessageReceived(msg);
		}	
	}

}
