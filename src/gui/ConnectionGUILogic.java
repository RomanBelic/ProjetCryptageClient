package gui;

import java.awt.event.ActionEvent;

import interfaces.Communication;
import interfaces.Ciphering.IHashable;
import models.Message;
import threading.CommunicationThread;
import utils.Utils;

public class ConnectionGUILogic extends AbstractUILogic<ConnectionGUI> implements IConnectionGUI {

	private final RegisterGUI regUI;
	private final CommunicationThread commThread;
	
	public ConnectionGUILogic(ConnectionGUI ui) {
		super(ui);
		this.regUI = new RegisterGUI();
		this.regUI.guiLogic.setOnCloseButtonCallback(this::onReturnedFromRegisterUI);
		this.commThread = CommunicationThread.getInstance();
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
		ui.setVisible(false);
		regUI.setVisible(false);
		ui.dispose();
		regUI.dispose();
		new ChatGUI();
		return null;
	}
	
	private Void onLoginErrorReceived (Message msg){
		ui.errLabel.setText(msg.getMessage());
		return null;
	}
	
	private Void onChallengeAccepted(Message msg){
		IHashable hasher = Utils.getHasherInstance();
		String serverKey = msg.getMessage();
		String clientHash = hasher.createHashString(serverKey);
		String login = ui.userloginJTextfield.getText();
		String password = hasher.createHashString(new String(ui.passwordJPassword.getPassword()));	
		String messageText = String.format("%s;%s;%s", clientHash,login,password);
		msg.setMessage(messageText);
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
