package gui;

import java.awt.event.ActionEvent;
import java.security.Key;
import java.security.KeyPair;

import javax.crypto.Cipher;

import implementations.SHA256Hasher;
import implementations.RSAKeyGen;
import interfaces.Ciphering.IHashable;
import interfaces.Ciphering.IKeyGenerator;
import interfaces.Communication;
import models.Message;
import threading.CommunicationThread;
import utils.AppContext;
import utils.KeyGenFileHelper;

public class ConnectionGUILogic extends AbstractUILogic<ConnectionGUI> implements IConnectionGUI {

	private final RegisterGUI regUI;
	private final CommunicationThread commThread;
	private final IKeyGenerator rsaKeyGen;
	private final KeyGenFileHelper keyGenFileHelper;
	
	public ConnectionGUILogic(ConnectionGUI ui) {
		super(ui);
		this.regUI = new RegisterGUI();
		this.regUI.guiLogic.setOnCloseButtonCallback(this::onReturnedFromRegisterUI);
		this.commThread = CommunicationThread.getInstance();
		this.keyGenFileHelper = new KeyGenFileHelper("ClientKeys");
		this.rsaKeyGen = new RSAKeyGen();
		this.commThread.getEventAdapter().setOnChallengeListener(this::onChallengeAccepted);
		this.commThread.getEventAdapter().setOnLoginErrorListener(this::onLoginErrorReceived);
		this.commThread.getEventAdapter().setOnConnectedListener(this::onConnected);
	}

	@Override
	public void onConnectionButtonClick(ActionEvent e, Object sender) {
		Message msg = new Message();
		msg.setPackets(Communication.F_AskChallenge);
		commThread.sendMessage(msg);
	}

	@Override
	public void onRegisterButtonClick(ActionEvent e, Object sender) {
		regUI.setVisible(true);
		ui.setVisible(false);
	}

	@Override
	public void onCloseWindowButtonClick(ActionEvent e, Object sender) {
		ui.dispose();
		regUI.dispose();
		System.exit(0);
	}

	@Override
	public void onWindowInit(Object... args) {
		ui.setVisible(true);
	}

	@Override
	public void onWindowClose(Object... args) {
		// TODO Auto-generated method stub
	}
	
	private void onReturnedFromRegisterUI(Object arg){
		regUI.setVisible(false);
		ui.setVisible(true);
	}
	
	private Void onConnected(Message msg){		
		String[] splitStr = msg.getPlainText().split(";");
		int clientId = splitStr.length > 0 ? new Integer(splitStr[0]).intValue() : 0;
		String clientName = splitStr.length > 1 ? splitStr[1] : null;
		byte[] serverPublicKeyBytes = msg.getData().length > 0 ? msg.getData() : null;

		KeyPair keyPair = keyGenFileHelper.getKeyPairFromStorage();
		Key serverPublicKey = rsaKeyGen.getKeyFromBytes(serverPublicKeyBytes, Cipher.PUBLIC_KEY);
		AppContext.getCurrentClient().setClientKeyPair(keyPair);
		AppContext.getCurrentClient().setServerKey(serverPublicKey);
		AppContext.getCurrentClient().setId(clientId);
		AppContext.getCurrentClient().setName(clientName);
			
		ui.setVisible(false);
		regUI.setVisible(false);
		ui.dispose();
		regUI.dispose();
		new ChatGUI();
		return null;
	}
	
	private Void onLoginErrorReceived (Message msg){
		ui.errLabel.setText(msg.getPlainText());
		return null;
	}
	
	private Void onChallengeAccepted(Message msg){
		IHashable hasher = new SHA256Hasher();
		String serverSecretKey = msg.getPlainText();
		String clientHash = hasher.createHashString(serverSecretKey);
		String login = ui.userloginJTextfield.getText();
		String password = hasher.createHashString(new String(ui.passwordJPassword.getPassword()));	
		String messageText = String.format("%s;%s;%s",clientHash,login,password);
		msg.setPlainText(messageText);
		commThread.sendMessage(msg);
		return null;
	}

	@Override
	public void onDisposing() {
		commThread.getEventAdapter().setOnChallengeListener(null);
		commThread.getEventAdapter().setOnLoginErrorListener(null);
		commThread.getEventAdapter().setOnConnectedListener(null);
	}
}
