	package implementations;

import interfaces.Communication.IEventAdapter;
import interfaces.Patterns.IDelegate;
import models.Message;

public class EventAdapterImplementation implements IEventAdapter<Message> {

	private IDelegate<Void, Message> recvListener;
	private IDelegate<Void, Void> cndListener;
	private IDelegate<Void, Void> dcdListener;
	
	@Override
	public void setOnReceivedListener(IDelegate<Void, Message> recvListener) {
		// TODO Auto-generated method stub
		this.recvListener = recvListener;
	}
	
	@Override
	public void setOnConnectedListener(IDelegate<Void, Void> cndListener) {
		// TODO Auto-generated method stub
		this.cndListener = cndListener;
	}
	
	@Override
	public void onDisconnectedListener(IDelegate<Void, Void> dcdListener) {
		// TODO Auto-generated method stub
		this.dcdListener = dcdListener;
	}

	@Override
	public void onMessageReceived(Message msg) {
		// TODO Auto-generated method stub
		if (recvListener == null)
			return;
		recvListener.action(msg);
	}

	@Override
	public void onConnected() {
		// TODO Auto-generated method stub
		if (cndListener == null)
			return;
		cndListener.action(null);
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		if (dcdListener == null)
			return;
		dcdListener.action(null);
	}

	

}
