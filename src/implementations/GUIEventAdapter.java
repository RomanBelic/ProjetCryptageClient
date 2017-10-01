package implementations;

import interfaces.Communication.IEventAdapter;
import interfaces.Patterns.IDelegate;
import models.Message;

public class GUIEventAdapter implements IEventAdapter<Message> {
	
	private IDelegate<Void, Message> onChallengeListener;
	private IDelegate<Void, Message> onConnectedListener;
	private IDelegate<Void, Message> onLoginErrorListener;
	private IDelegate<Void, Message> onSubscribeErrorListener;
	private IDelegate<Void, Message> onReceivedListener;
	private IDelegate<Void, Message> onDisconnectedListener;
	private IDelegate<Void, Message> onSubscribedListener;
	
	public void setOnChallengeListener(IDelegate<Void, Message> onChallengeListener) {
		this.onChallengeListener = onChallengeListener;
	}
	
	public void setOnConnectedListener(IDelegate<Void, Message> onConnectedListener) {
		this.onConnectedListener = onConnectedListener;
	}

	public void setOnLoginErrorListener(IDelegate<Void, Message> onLoginErrorListener) {
		this.onLoginErrorListener = onLoginErrorListener;
	}
	
	public void setOnReceivedListener(IDelegate<Void, Message> onReceivedListener) {
		this.onReceivedListener = onReceivedListener;
	}
	
	public void setOnDisconnectedListener(IDelegate<Void, Message> onDisconnectedListener) {
		this.onDisconnectedListener = onDisconnectedListener;
	}
	
	public void setOnSubscribedListener(IDelegate<Void, Message> onSubscribedListener) {
		this.onSubscribedListener = onSubscribedListener;
	}
	
	public void setOnSubscribeErrorListener(IDelegate<Void, Message> onSubscribeErrorListener) {
		this.onSubscribeErrorListener = onSubscribeErrorListener;
	}
	
	@Override
	public void onChallengeAccepted(Message msg) {
		if (onChallengeListener == null)
			return;
		onChallengeListener.action(msg);
	}
	
	@Override
	public void onConnected(Message msg) {
		if (onConnectedListener == null)
			return;
		onConnectedListener.action(msg);
	}
	
	@Override
	public void onLoginErrorReceived(Message msg) {
		if (onLoginErrorListener == null)
			return;
		onLoginErrorListener.action(msg);
	}

	@Override
	public void onMessageReceived(Message msg) {
		if (onReceivedListener == null)
			return;
		onReceivedListener.action(msg);
	}

	@Override
	public void onDisconnected(Message msg) {
		if (onDisconnectedListener == null)
			return;
		onDisconnectedListener.action(msg);
	}

	@Override
	public void onSubscribed(Message msg) {
		if (onSubscribedListener == null)
			return;
		onSubscribedListener.action(msg);
	}

	@Override
	public void onSubscribeErrorReceived(Message msg) {
		if (onSubscribeErrorListener == null)
			return;
		onSubscribeErrorListener.action(msg);
	}

}
