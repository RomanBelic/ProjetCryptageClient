package gui;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import interfaces.Communication;
import models.Message;
import models.Upload;
import threading.CommunicationThread;
import utils.Context;
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
		if (Context.getCurrentClient().isEmpty())
			return;
		Message msg = new Message();
		msg.setMessage(ui.messageArea.getText());
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
		if (Context.getCurrentClient().isEmpty())
			return;
		if (fileChooserState != JFileChooser.APPROVE_OPTION)
			return;
		File file = ui.fileChooser.getSelectedFile();
		Upload upload = new Upload();
		upload.setPackets(Communication.F_PassedChallenge | Communication.F_SentMsg);
		upload.setData(Utils.readBytesFromFile(file));
		upload.setName(file.getName());
		commThread.sendMessage(upload);
	} 
	
	private Void onMessageReceived(Message msg){
		if (Context.getCurrentClient().isEmpty())
			return null;
		String txtMessage = String.format("%s: %s\r\n", msg.getSenderName(), msg.getMessage());
		ui.chatArea.append(txtMessage);
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
