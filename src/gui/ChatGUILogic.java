package gui;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;

import implementations.RSACipher;
import interfaces.Communication;
import interfaces.Ciphering.ICipher;
import models.Message;
import models.Upload;
import threading.CommunicationThread;
import utils.AppContext;
import utils.Utils;

public class ChatGUILogic extends AbstractUILogic<ChatGUI> implements IChatGUI {

	private final CommunicationThread commThread;
	private int fileChooserState;
	
	public ChatGUILogic(ChatGUI ui) {
		super(ui);
		commThread = CommunicationThread.getInstance();
		commThread.getEventAdapter().setOnReceivedListener(this::onMessageReceived);
		fileChooserState = JFileChooser.CANCEL_OPTION;
	}

	@Override
	public void onWindowInit(Object... args) {
		// TODO Auto-generated method stub
		ui.setVisible(true);
	}

	@Override
	public void onWindowClose(Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSendMessageButtonClick(ActionEvent e, Object sender) {
		if (AppContext.getCurrentClient().isEmpty())
			return;
		
		byte[] bytesToEncode = ui.messageArea.getText().getBytes();
		ICipher clientEncryptor = new RSACipher(AppContext.getCurrentClient().getServerKey());
		byte[] encoedBytes = clientEncryptor.encrypt(bytesToEncode);
		
		Message msg = new Message();
		msg.setData(encoedBytes);
		msg.setPackets(Communication.F_PassedChallenge | Communication.F_SentMsg);
		commThread.sendMessage(msg);
	}

	@Override
	public void onExitButtonClick(ActionEvent e, Object sender) {
		Message msg = new Message();
		msg.setPackets(Communication.F_ShutDown);
		commThread.sendMessage(msg);
		commThread.stopClient();
		ui.setVisible(false);
		ui.dispose();
	}
	
	@Override
	public void onSendFileButtonClick(ActionEvent e, Object sender) {
		if (fileChooserState != JFileChooser.APPROVE_OPTION || AppContext.getCurrentClient().isEmpty())
			return;
		File file = ui.fileChooser.getSelectedFile();
		Upload upload = new Upload();
		upload.setPackets(Communication.F_PassedChallenge | Communication.F_SentMsg);
		upload.setData(Utils.readBytesFromFile(file));
		upload.setName(file.getName());
		commThread.sendMessage(upload);
	} 
	
	private Void onMessageReceived(Message msg){
		if (AppContext.getCurrentClient().isEmpty())
			return null;
		
		ICipher clientDecryptor = new RSACipher(AppContext.getCurrentClient().getClientKeyPair().getPrivate());	
		byte[] decodedBytes = clientDecryptor.decrypt(msg.getData());
		String decryptedMessage = new String(decodedBytes);
		
		ui.chatArea.append(String.format("%s: %s\r\n", msg.getSenderName(), decryptedMessage));
		return null;
	}

	@Override
	public void onDisposing() {
		commThread.getEventAdapter().setOnReceivedListener(null);
	}

	@Override
	public void onOpenFileChooserClick(ActionEvent e, Object sender) {
		fileChooserState =  ui.fileChooser.showOpenDialog(ui);	
		ui.lblChosenFile.setText(fileChooserState == JFileChooser.APPROVE_OPTION ? ui.fileChooser.getSelectedFile().getName() : "");
	}
}
