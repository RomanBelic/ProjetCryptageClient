package gui;

import java.awt.event.ActionEvent;

import implementations.PasswordCheckerImplementation;
import interfaces.Ciphering.IHashable;
import interfaces.Communication;
import interfaces.Patterns.ICallback;
import interfaces.Regex.IRegexChecker;
import models.Message;
import threading.CommunicationThread;
import utils.Utils;

public class RegisterGUILogic extends AbstractUILogic<RegisterGUI> implements IRegisterGUI{

	private ICallback<Object> callbackCloseButton;
	private final CommunicationThread commThread;
	private final RegisterGUI ui;
	private final IRegexChecker passwordRegex;
	
	public RegisterGUILogic(RegisterGUI ui){
		super(ui);
		this.ui = ui;
		this.commThread = CommunicationThread.getInstance();
		this.commThread.getEventAdapter().setOnSubscribeErrorListener(this::onSubscribeErrorReceived);
		this.commThread.getEventAdapter().setOnSubscribedListener(this::onSubscribed);
		this.passwordRegex = new PasswordCheckerImplementation();
	}
	
	@Override
	public void setOnCloseButtonCallback(ICallback<Object> icallback){
		this.callbackCloseButton = icallback;
	}
	
	@Override
	public void onValiderButtonClick(ActionEvent e, Object sender) {	
		String password = new String(ui.passwordJPassword.getPassword());
		Message msg = new Message();
		if (!passwordRegex.hasMatch(password)){
			msg.setMessage("Password doesn't match requirements");
			onSubscribeErrorReceived(msg);
			return;
		}
		IHashable hasher = Utils.getHasherInstance();
		String login = ui.loginJTextfield.getText();
		password = hasher.createHashString(password);
		String userName = ui.usernameJTextfield.getText();
		String messageText = String.format("%s;%s;%s", login, password, userName);
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
		this.callbackCloseButton = null;
		this.commThread.getEventAdapter().setOnSubscribeErrorListener(null);
		this.commThread.getEventAdapter().setOnSubscribedListener(null);
	} 
}
