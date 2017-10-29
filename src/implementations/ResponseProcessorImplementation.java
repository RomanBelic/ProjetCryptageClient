package implementations;

import interfaces.Communication;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IEventAdapter;
import interfaces.Communication.IResponseProcessor;
import models.Message;

public class ResponseProcessorImplementation implements IResponseProcessor<Message> {

	private final ICommunicationProtocol<Message> commProtocol;
	
	public ResponseProcessorImplementation(ICommunicationProtocol<Message> commProtocol){
		this.commProtocol = commProtocol;
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
				msg.setPlainText("Invalid login/password");
				eventAdapter.onLoginErrorReceived(msg);
			}else if (msg.getCode() == Communication.Unauthorized){
				msg.setPlainText("Unauthorized action");
				eventAdapter.onLoginErrorReceived(msg);
			}
		}
		else if ((packets | Communication.F_AskInscription) == Communication.F_AskInscription){
			if (msg.getCode() == Communication.Created){
				eventAdapter.onSubscribed(msg);
			}else if (msg.getCode() == Communication.Conflict){
				msg.setPlainText("User already exists");
				eventAdapter.onSubscribeErrorReceived(msg);
			}else if (msg.getCode() == Communication.InternalError){
				msg.setPlainText("Internal error");
				eventAdapter.onSubscribeErrorReceived(msg);
			}
		}
		else if ((packets & (Communication.F_PassedChallenge | Communication.F_SentMsg)) == (Communication.F_PassedChallenge | Communication.F_SentMsg)){
			eventAdapter.onMessageReceived(msg);
		}	
		else if ((packets | Communication.F_ShutDown) == Communication.F_ShutDown){
			if (msg.getCode() == Communication.OK){
				commProtocol.shutDownOutputStream();
				commProtocol.shutDownInputStream();
				eventAdapter.onDisconnected(msg);
			}
		}	
	}

}
