package implementations;

import interfaces.Communication;
import interfaces.Ciphering.IHashable;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IEventAdapter;
import interfaces.Communication.IResponseProcessor;
import models.Message;

public class ResponseProcessorImplementation implements IResponseProcessor<Message> {

	private final ICommunicationProtocol<Message> commProtocol;
	private final IHashable hasher;
	
	public ResponseProcessorImplementation(ICommunicationProtocol<Message> commProtocol){
		this.commProtocol = commProtocol;
		this.hasher = new HashImplementation();
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
	}

}
