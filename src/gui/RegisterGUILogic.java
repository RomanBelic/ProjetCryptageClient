package gui;

import java.awt.event.ActionEvent;

import implementations.HashImplementation;
import interfaces.Ciphering.IHashable;
import interfaces.Communication;
import interfaces.Patterns.ICallback;
import models.Message;
import threading.CommunicationThread;

public class RegisterGUILogic extends AbstractUILogic<RegisterGUI> implements IRegisterGUI{

	private ICallback<Object> callbackCloseButton;
	private final IHashable hasher;
	private final CommunicationThread commThread;
	private final RegisterGUI ui;
	
	public RegisterGUILogic(RegisterGUI ui){
		super(ui);
		this.ui = ui;
		this.hasher = new HashImplementation();
		this.commThread = CommunicationThread.getInstance();
		this.commThread.getEventAdapter().setOnSubscribeErrorListener(this::onSubscribeErrorReceived);
		this.commThread.getEventAdapter().setOnSubscribedListener(this::onSubscribed);
	}
	
	@Override
	public void setOnCloseButtonCallback(ICallback<Object> icallback){
		this.callbackCloseButton = icallback;
	}
	
	@Override
	public void onValiderButtonClick(ActionEvent e, Object sender) {
		// TODO Auto-generated method stub
		String login = ui.loginJTextfield.getText();
		String password = new String(ui.passwordJPassword.getPassword());
		password = hasher.createHashString(password);
		String userName = ui.usernameJTextfield.getText();
		String messageText = String.format("%s;%s;%s", login, password, userName);
		Message msg = new Message();
		msg.setMessage(messageText);
		msg.setPackets(Communication.F_AskInscription);
		commThread.sendMessage(msg);
	}

	@Override
	public void onExitButtonClick(ActionEvent e, Object sender) {
		if (callbackCloseButton == null)
			return;
		callbackCloseButton.onCalledBack(0);
	}

	@Override
	public void onWindowInit(Object... args) {
		
	}

	@Override
	public void onWindowClose(Object... args) {
		// TODO Auto-generated method stub
	}
	
	private Void onSubscribeErrorReceived (Message msg){
		ui.errLabel.setText(msg.getMessage());
		return null;
	}
	
	private Void onSubscribed (Message msg){
		ui.setVisible(false);
		callbackCloseButton.onCalledBack(0);
		return null;
	}

	@Override
	public void onDisposing() {
		this.commThread.getEventAdapter().setOnSubscribeErrorListener(null);
		this.commThread.getEventAdapter().setOnSubscribedListener(null);
	} 
}
