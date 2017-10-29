package gui;

import java.awt.event.ActionEvent;
import java.security.Key;

import implementations.SHA256Hasher;
import implementations.PasswordCheckerImplementation;
import implementations.RSAKeyGen;
import interfaces.Ciphering.IHashable;
import interfaces.Communication;
import interfaces.Patterns.ICallback;
import interfaces.Regex.IRegexChecker;
import models.Message;
import threading.CommunicationThread;
import utils.KeyGenFileHelper;

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
		new RSAKeyGen();
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
			msg.setPlainText("Password doesn't match requirements");
			onSubscribeErrorReceived(msg);
			return;
		}
		
		KeyGenFileHelper keyGenFileHelper = new KeyGenFileHelper("ClientKeys");
		keyGenFileHelper.createKeyPairStorage();
		Key publicKeyObject = keyGenFileHelper.getPublicKeyFromStorage();
	
		IHashable hasher = new SHA256Hasher();
		String login = ui.loginJTextfield.getText();
		password = hasher.createHashString(password);
		String userName = ui.usernameJTextfield.getText();
		byte[] clientPublicKey = publicKeyObject.getEncoded();
		String messageText = String.format("%s;%s;%s", login, password, userName);
		msg.setPlainText(messageText);
		msg.setData(clientPublicKey);
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
		ui.errLabel.setText(msg.getPlainText());
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
