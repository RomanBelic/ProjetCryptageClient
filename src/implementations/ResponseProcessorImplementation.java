package implementations;

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
	public void processResponse (Message arg, IEventAdapter<Message> eventAdapter) {
		// TODO Auto-generated method stub

	
	}

}
