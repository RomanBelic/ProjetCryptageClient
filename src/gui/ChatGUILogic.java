package gui;

import java.awt.event.ActionEvent;

import models.Message;
import threading.CommunicationThread;

public class ChatGUILogic extends AbstractUILogic<ChatGUI> implements IChatGUI {

	private final CommunicationThread commThread;
	
	public ChatGUILogic(ChatGUI ui) {
		super(ui);
		this.commThread = CommunicationThread.getInstance();
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
		// TODO Auto-generated method stub
	
	}

	@Override
	public void onExitButtonClick(ActionEvent e, Object sender) {
		commThread.stopClient();
		ui.setVisible(false);
		ui.dispose();
	}

	@Override
	public void onSendFileButtonClick(ActionEvent e, Object sender) {
		// TODO Auto-generated method stub
		
	}
	
	private Void onMessageReceived(Message msg){
		
		
		
		return null;
	}

}
